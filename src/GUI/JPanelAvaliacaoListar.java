/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import org.joda.time.DateTime;
import org.joda.time.Days;
import p5_negocio.entidades.Avaliacao;
import p5_negocio.entidades.Avaliador;
import p5_negocio.entidades.Processo;
import p6_excecoes.BancoDeDadosException;

/**
 *
 * @author o
 */
public class JPanelAvaliacaoListar extends javax.swing.JPanel {

    /**
     * Creates new form JPanelVenda_listar_selecionado
     */
    TelasProjetoFinalPrincipal telaprincipal;
    JOptionPaneGui exibe;
    double total;
    DefaultTableModel model;
    JPanelAvaliacaoListar telaListar = this;

    List<Date> dates = new ArrayList<Date>();

    public JPanelAvaliacaoListar(TelasProjetoFinalPrincipal telaprincipal) {
        initComponents();
        exibe = new JOptionPaneGui();
        this.telaprincipal = telaprincipal;
        try {
            ListarAvaliacoes(telaprincipal.getFachada().AvaliacaoListar());
        } catch (BancoDeDadosException | SQLException ex) {
            exibe.fazTelaErro("erro ao acessar banco de dados", "erro ao acessar banco de dados");
            Logger.getLogger(JPanelAvaliacaoListar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Object[][] carregaDados(ArrayList<Avaliacao> lista) throws BancoDeDadosException, SQLException {

        // remove todos os processos sem data de termino
        ArrayList<Avaliacao> remover = new ArrayList<Avaliacao>();
        if (!jCheckBox1.isSelected()) {
            for (int i = 0; i < lista.size(); i++) {
                int id = lista.get(i).getIdProcesso();
                if (this.telaprincipal.getFachada().procurarProcessoPorId(id).getConclusaoProcesso() != null) {
                    remover.add(lista.get(i));
                }
            }
        }

        lista.removeAll(remover);

        Object[][] dados = new Object[lista.size()][7];

        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getIdProcesso();
            Processo p = this.telaprincipal.getFachada().procurarProcessoPorId(id);
            // carrega todas as datsa do problema
            carregaDatasLista(lista.get(i));
            //verifica se tem data para evita erro de lista vazia
            Date latest;
            String tempoParado = "";
            if (!dates.isEmpty()) {
                // seleciona maior data
                latest = Collections.max(dates);
                DateTime startDate = new DateTime(new Date());
                DateTime endDate = new DateTime(latest); //current date
                int diff = Integer.parseInt(Days.daysBetween(endDate, startDate).getDays() + "");

                if (diff < 0) {
                    diff = 0;
                }

                tempoParado = String.format("%02d", diff) + " dias.";
            } else {
                tempoParado = "sem data de inicio";
            }
            //zerando para o proximo registro
            dates.removeAll(dates);
            //tempo parado

            String tipo = "erro na leitura";
            if (lista.get(i).getTipo() == 1) {
                tipo = "Interno";
            } else if (lista.get(i).getTipo() == 2) {
                tipo = "Externo 1";
            } else if (lista.get(i).getTipo() == 3) {
                tipo = "Externo 2";
            } else {
                exibe.fazTelaErro("erro no tipo", "erro na leitura do tipoavaliador");
            }

            dados[i][0] = id;
            dados[i][1] = lista.get(i).getAvaliador().getNome();
            dados[i][2] = tipo;
            dados[i][3] = lista.get(i).getParecer();
            dados[i][4] = tempoParado;
            dados[i][5] = p.getNome();
            dados[i][6] = "SELECIONAR";

        }
        return dados;

    }

    private void carregaDatasLista(Avaliacao a) {
        carregaDatasListaChecarDataNaoNula(a.getEnvioAceite());
        carregaDatasListaChecarDataNaoNula(a.getRespostaAceite());
        carregaDatasListaChecarDataNaoNula(a.getEnvioDocumentos());
        carregaDatasListaChecarDataNaoNula(a.getRespostaDocumentos());
    }

    // evita erro de ponteiro nulo na funcao Collections.max(dates);
    private void carregaDatasListaChecarDataNaoNula(Date d) {
        //dates=null;
        if (d != null) {
            dates.add(d);
        }
    }

    private void ListarAvaliacoes(ArrayList<Avaliacao> lista) throws BancoDeDadosException, SQLException {

        if (!lista.isEmpty()) {
            String[] colunas = {"Código processo", "Nome avaliador", "Tipo", "Estado", "Tempo sem alteracao", "Professor avaliado", "Abrir"};
            // limpar pesquisa
            Object[][] dados = carregaDados(lista);

            model = new javax.swing.table.DefaultTableModel(dados, colunas) {
                boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, true
                };

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };
            jTableResultadoPesquisa.setModel(model);
            //cria botao editar na lista

            ButtonColumn bt = new ButtonColumn(jTableResultadoPesquisa, 6) { // para alterar o numero é necessario tambem alterar canEdit em model
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    try {

                        // desse jeito abre o primeiro item da lista inicial, dando erro quando para abrir quando eu sorteio por tempo sem acesso
                        //int id = Integer.parseInt("" + table.getModel().getValueAt(table.getSelectedRow(), 0));
                        int convertedRowSelected = jTableResultadoPesquisa.convertRowIndexToModel(jTableResultadoPesquisa.getSelectedRow());
                        int id = Integer.parseInt(jTableResultadoPesquisa.getModel().getValueAt(convertedRowSelected, 0).toString());
                        Processo p = telaprincipal.getFachada().procurarProcessoPorId(id);

                        if ("Interno".equals(jTableResultadoPesquisa.getModel().getValueAt(convertedRowSelected, 2).toString())) {
                            JPanelProcessoCadastrar z = new JPanelProcessoCadastrar(telaListar, p, 0);
                            telaprincipal.setJpanelTelaPrincipal(z);
                            telaprincipal.mudarTela();
                        } else if ("Externo 1".equals(jTableResultadoPesquisa.getModel().getValueAt(convertedRowSelected, 2).toString())) {
                            JPanelProcessoCadastrar z = new JPanelProcessoCadastrar(telaListar, p, 1);
                            telaprincipal.setJpanelTelaPrincipal(z);
                            telaprincipal.mudarTela();
                        } else if ("Externo 2".equals(jTableResultadoPesquisa.getModel().getValueAt(convertedRowSelected, 2).toString())) {
                            JPanelProcessoCadastrar z = new JPanelProcessoCadastrar(telaListar, p, 2);
                            telaprincipal.setJpanelTelaPrincipal(z);
                            telaprincipal.mudarTela();
                        }

                    } catch (BancoDeDadosException | SQLException ex) {
                        exibe.fazTelaErro("Erro", ex.getMessage() + "");

                    }

                }
            };

            jTable_Usuarios_PopupMenu();
        } else {
            String[] colunas = {"Código processo", "Nome avaliador", "Tipo", "parecer", "Tempo sem alteracao", "Professor avaliado", "Abrir"};
            // limpar pesquisa
            Object[][] dados = {{"", "", "", "", "", "", ""}};
            model = new javax.swing.table.DefaultTableModel(dados, colunas) {
                boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, true
                };

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };
            jTableResultadoPesquisa.setModel(model);
        }
    }

    private void jTable_Usuarios_PopupMenu() {
        final JPopupMenu popupMenu = new JPopupMenu();

        // cria item de menu com nome editar
        JMenuItem editItem = new JMenuItem("abrir");
        // atribui a editar um evento
        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // editar ira usar o evento que e stiver listado aqui, no caso a funcao jTable_UsuarioEditar
                JPanelAvaliacaoListar.this.jTable_ProfessorAbrir();
            }
        });
        popupMenu.add(editItem);

        // parte responsavel para que o clique com o botao direito do mause selecione a linha onde o clique ocorreu
        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int rowAtPoint = jTableResultadoPesquisa.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), jTableResultadoPesquisa));
                        if (rowAtPoint > -1) {
                            jTableResultadoPesquisa.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                // TODO Auto-generated method stub

            }
        }); // fim popupMenu.addPopupMenuListener(new PopupMenuListener()

        this.jTableResultadoPesquisa.setComponentPopupMenu(popupMenu);
    }

    private void voltarTelaAnterior() {
        telaprincipal.RetornarTelaPrincipal();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableResultadoPesquisa = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnPesquisar = new javax.swing.JButton();
        RadioBtnNome = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPesquisa = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        RadioBtnProfessorAvaliado = new javax.swing.JRadioButton();
        jLabel17 = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();

        jTableResultadoPesquisa.setAutoCreateRowSorter(true);
        jTableResultadoPesquisa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Código processo", "Nome avaliador", "Tipo", "Estado", "Tempo sem alteração", "Professor avaliado", "Abrir"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableResultadoPesquisa.setToolTipText("");
        jTableResultadoPesquisa.setMinimumSize(new java.awt.Dimension(300, 64));
        jScrollPane1.setViewportView(jTableResultadoPesquisa);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar cliente"));

        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        buttonGroup1.add(RadioBtnNome);
        RadioBtnNome.setSelected(true);
        RadioBtnNome.setText("Nome avaliador");

        jLabel5.setText("Selecione o critério de pesquisa");

        jLabel6.setText("Texto a pesquisar");

        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyPressed(evt);
            }
        });

        jCheckBox1.setText("Incluir processos concluidos");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(RadioBtnProfessorAvaliado);
        RadioBtnProfessorAvaliado.setText("Nome professor avaliado");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addGap(50, 50, 50)
                        .addComponent(btnPesquisar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(RadioBtnNome)
                        .addGap(28, 28, 28)
                        .addComponent(RadioBtnProfessorAvaliado)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RadioBtnNome)
                    .addComponent(RadioBtnProfessorAvaliado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addComponent(btnPesquisar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Lista de Avaliações");

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnVoltar))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVoltar)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        voltarTelaAnterior();

    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed

        if (RadioBtnNome.isSelected()) {
            try {
                ArrayList<Avaliacao> AvaliacaoLista = telaprincipal.getFachada().procurarAvalaicaoProfessorPorNomeAvaliador(txtPesquisa.getText());
                ListarAvaliacoes(AvaliacaoLista);
            } catch (BancoDeDadosException ex) {
                Logger.getLogger(JPanelAvaliacaoListar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(JPanelAvaliacaoListar.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (RadioBtnProfessorAvaliado.isSelected()) {
            try {
                ArrayList<Avaliacao> AvaliacaoLista = telaprincipal.getFachada().procurarAvalaicaoProfessorPorNomeProfessor(txtPesquisa.getText());
                ListarAvaliacoes(AvaliacaoLista);
            } catch (BancoDeDadosException ex) {
                Logger.getLogger(JPanelAvaliacaoListar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(JPanelAvaliacaoListar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ListarAvaliacoes(telaprincipal.getFachada().AvaliacaoListar());
            } catch (BancoDeDadosException | SQLException ex) {
                exibe.fazTelaErro("erro ao acessar banco de dados", "erro ao acessar banco de dados");
                Logger.getLogger(JPanelAvaliacaoListar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnPesquisar.doClick();
        }
    }//GEN-LAST:event_txtPesquisaKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton RadioBtnNome;
    private javax.swing.JRadioButton RadioBtnProfessorAvaliado;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableResultadoPesquisa;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables

    private void jTable_ProfessorAbrir() {

//        int rowSelected = this.jTableResultadoPesquisa.getSelectedRow();
//        int ColumnSelected = this.jTableResultadoPesquisa.getSelectedColumn();
        int convertedRowSelected = this.jTableResultadoPesquisa.convertRowIndexToModel(this.jTableResultadoPesquisa.getSelectedRow());
        try {
            int id = Integer.parseInt(this.jTableResultadoPesquisa.getModel().getValueAt(convertedRowSelected, 0).toString());
            Processo p = this.telaprincipal.getFachada().procurarProcessoPorId(id);

            if ("Interno".equals(jTableResultadoPesquisa.getModel().getValueAt(convertedRowSelected, 2).toString())) {
                JPanelProcessoCadastrar z = new JPanelProcessoCadastrar(telaListar, p, 0);
                telaprincipal.setJpanelTelaPrincipal(z);
                telaprincipal.mudarTela();
            } else if ("Externo 1".equals(jTableResultadoPesquisa.getModel().getValueAt(convertedRowSelected, 2).toString())) {
                JPanelProcessoCadastrar z = new JPanelProcessoCadastrar(telaListar, p, 1);
                telaprincipal.setJpanelTelaPrincipal(z);
                telaprincipal.mudarTela();
            } else if ("Externo 2".equals(jTableResultadoPesquisa.getModel().getValueAt(convertedRowSelected, 2).toString())) {
                JPanelProcessoCadastrar z = new JPanelProcessoCadastrar(telaListar, p, 2);
                telaprincipal.setJpanelTelaPrincipal(z);
                telaprincipal.mudarTela();
            }

        } catch (NumberFormatException | BancoDeDadosException | SQLException ex) {
            exibe.fazTelaErro("Erro", "Erro ao abrir!\n" + ex);
        }
    }

}

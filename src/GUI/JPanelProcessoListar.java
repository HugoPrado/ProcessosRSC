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
import p5_negocio.entidades.Processo;
import p6_excecoes.BancoDeDadosException;

/**
 *
 * @author o
 */
public class JPanelProcessoListar extends javax.swing.JPanel {

    /**
     * Creates new form JPanelVenda_listar_selecionado
     */
    TelasProjetoFinalPrincipal telaprincipal;
    JOptionPaneGui exibe;
    double total;
    DefaultTableModel model;

    List<Date> dates = new ArrayList<Date>();

    public JPanelProcessoListar(TelasProjetoFinalPrincipal telaprincipal) {
        initComponents();
        exibe = new JOptionPaneGui();
        this.telaprincipal = telaprincipal;
        try {
            ListarProcesso(telaprincipal.getFachada().ProcessoListar());
        } catch (BancoDeDadosException | SQLException ex) {
            exibe.fazTelaErro("erro ao acessar banco de dados", "erro ao acessar banco de dados");
            Logger.getLogger(JPanelProcessoListar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Object[][] carregaDados(ArrayList<Processo> lista) throws BancoDeDadosException, SQLException {

        // remove todos os processos sem data de termino
        ArrayList<Processo> remover = new ArrayList<Processo>();
        if (!jCheckBox1.isSelected()) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getConclusaoProcesso() != null) {
                    remover.add(lista.get(i));
                }
            }
        }

        lista.removeAll(remover);

        for (int i = 0; i < lista.size(); i++) {
            ArrayList<Avaliacao> avaliadores = this.telaprincipal.getFachada().AvaliacaoListarPorIdProcesso(lista.get(i).getId());
            if (avaliadores.isEmpty()) {
                exibe.fazTelaErro("erro ao carregar avaliadores", "erro ao carregar avaliadores, lista vazia");
                break;
            } else if (avaliadores.size() < 3) {
                exibe.fazTelaErro("erro ao carregar avaliadores", "Numero de avaliadores encontrado insuficiente");
                break;
            } else {
                lista.get(i).setAvaliacao1(avaliadores.get(0));
                lista.get(i).setAvaliacao2(avaliadores.get(1));
                lista.get(i).setAvaliacao3(avaliadores.get(2));
            }
            avaliadores.removeAll(avaliadores);
        }

        Object[][] dados = new Object[lista.size()][7];
        for (int i = 0; i < lista.size(); i++) {

            // carrega todas as datsa do problema
            carregaDatasLista(lista.get(i));
            //verifica se tem data para evita erro de lista vazia
            Date latest;
            if (!dates.isEmpty()) {
                // seleciona maior data
                latest = Collections.max(dates);
            } else {
                latest = null;
            }
            //zerando para o proximo registro
            dates.removeAll(dates);

            dados[i][0] = lista.get(i).getId();
            dados[i][1] = lista.get(i).getNome();
            dados[i][2] = lista.get(i).getNivelSrc();
            dados[i][3] = lista.get(i).getSituacao();
            dados[i][4] = lista.get(i).getInicioProcesso();

//            metodo 1
//            Date now = new Date();
//            if (now.compareTo(latest) == 1) {
//                //(1000 * 60 * 60 * 24)); -> milissegundo para segundo -> para minunto -> para hora -> para dias
//                dados[i][5] = ((now.getTime() - latest.getTime()) / (1000 * 60 * 60 * 24)) + " dias.";
//            } else {
//                dados[i][5] = 0;
//            }
            DateTime startDate = new DateTime(new Date());
            DateTime endDate = new DateTime(latest); //current date
            int diff = Integer.parseInt(Days.daysBetween(endDate, startDate).getDays() + "");
            if (diff < 0) {
                diff = 00;
            }
            dados[i][5] = diff + " dias.";
            dados[i][6] = "SELECIONAR";

        }
        return dados;

    }

    private void carregaDatasLista(Processo p) {
        // carrega todas as datsa do problema
        carregaDatasListaChecarDataNaoNula(p.getInicioProcesso());
        carregaDatasListaChecarDataNaoNula(p.getConclusaoProcesso());
        if (p.getAvaliacao1() != null) {
            carregaDatasListaChecarDataNaoNula(p.getAvaliacao1().getEnvioAceite());
            carregaDatasListaChecarDataNaoNula(p.getAvaliacao1().getRespostaAceite());
            carregaDatasListaChecarDataNaoNula(p.getAvaliacao1().getEnvioDocumentos());
            carregaDatasListaChecarDataNaoNula(p.getAvaliacao1().getRespostaDocumentos());
        }
        if (p.getAvaliacao2() != null) {
            carregaDatasListaChecarDataNaoNula(p.getAvaliacao2().getEnvioAceite());
            carregaDatasListaChecarDataNaoNula(p.getAvaliacao2().getRespostaAceite());
            carregaDatasListaChecarDataNaoNula(p.getAvaliacao2().getEnvioDocumentos());
            carregaDatasListaChecarDataNaoNula(p.getAvaliacao2().getRespostaDocumentos());
        }
        if (p.getAvaliacao3() != null) {
            carregaDatasListaChecarDataNaoNula(p.getAvaliacao3().getEnvioAceite());
            carregaDatasListaChecarDataNaoNula(p.getAvaliacao3().getRespostaAceite());
            carregaDatasListaChecarDataNaoNula(p.getAvaliacao3().getEnvioDocumentos());
            carregaDatasListaChecarDataNaoNula(p.getAvaliacao3().getRespostaDocumentos());
        }
    }

    // evita erro de ponteiro nulo na funcao Collections.max(dates);
    private void carregaDatasListaChecarDataNaoNula(Date d) {
        //dates=null;
        if (d != null) {
            dates.add(d);
        }
    }

    private void ListarProcesso(ArrayList<Processo> lista) throws BancoDeDadosException, SQLException {

        String[] colunas = {"Código", "Nome", "nivel", "situacao", "inicio", "Tempo sem alteracao", "Abrir"};
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
        JPanelProcessoListar telaListar = this;

        ButtonColumn bt = new ButtonColumn(jTableResultadoPesquisa, 6) { // para alterar o numero é necessario tambem alterar canEdit em model
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                try {

                    // desse jeito abre o primeiro item da lista inicial, dando erro quando para abrir quando eu sorteio por tempo sem acesso
                    //int id = Integer.parseInt("" + table.getModel().getValueAt(table.getSelectedRow(), 0));
                    
                    // aparentimente pega a linha que foi selecionada e retorna a posiçao que ela estaria se nao tivesse ocorrido sorteio
                    int convertedRowSelected = jTableResultadoPesquisa.convertRowIndexToModel(jTableResultadoPesquisa.getSelectedRow());
                    
                    int id = Integer.parseInt(jTableResultadoPesquisa.getModel().getValueAt(convertedRowSelected, 0).toString());

                    Processo p = telaprincipal.getFachada().procurarProcessoPorId(id);

                    JPanelProcessoCadastrar z = new JPanelProcessoCadastrar(telaListar, p);

                    telaprincipal.setJpanelTelaPrincipal(z);
                    telaprincipal.mudarTela();

                } catch (BancoDeDadosException | SQLException ex) {
                    exibe.fazTelaErro("Erro", ex.getMessage() + "");

                }

            }
        };

        jTable_Usuarios_PopupMenu();

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
                JPanelProcessoListar.this.jTable_ProfessorAbrir();
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
        RadioBtnSituacao = new javax.swing.JRadioButton();
        RadioBtnComentario = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();

        jTableResultadoPesquisa.setAutoCreateRowSorter(true);
        jTableResultadoPesquisa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Nome", "nivel", "situacao", "inicio", "Ultima alteracao", "Abrir"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

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
        RadioBtnNome.setText("Nome");

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

        buttonGroup1.add(RadioBtnSituacao);
        RadioBtnSituacao.setText("situacao");

        buttonGroup1.add(RadioBtnComentario);
        RadioBtnComentario.setText("comentario");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(177, 177, 177)
                                .addComponent(RadioBtnComentario))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(RadioBtnNome)
                                .addGap(28, 28, 28)
                                .addComponent(RadioBtnSituacao))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPesquisar))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPesquisa)))))
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RadioBtnNome)
                    .addComponent(RadioBtnSituacao)
                    .addComponent(RadioBtnComentario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(btnPesquisar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Cadastrar Processo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Lista de Processos");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVoltar)
                .addContainerGap())
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

        if (RadioBtnSituacao.isSelected()) {
            try {
                ArrayList<Processo> professoresLista = telaprincipal.getFachada().procurarProcessoPorSituacao(txtPesquisa.getText());
                ListarProcesso(professoresLista);
            } catch (BancoDeDadosException ex) {
                Logger.getLogger(JPanelProcessoListar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(JPanelProcessoListar.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (RadioBtnNome.isSelected()) {
            try {
                ArrayList<Processo> professoresLista = telaprincipal.getFachada().procurarProcessoPorNomeProfessor(txtPesquisa.getText());
                ListarProcesso(professoresLista);
            } catch (BancoDeDadosException ex) {
                Logger.getLogger(JPanelProcessoListar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(JPanelProcessoListar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (RadioBtnComentario.isSelected()) {
            try {
                ArrayList<Processo> professoresLista = telaprincipal.getFachada().procurarProcessoPorComentario(txtPesquisa.getText());
                ListarProcesso(professoresLista);
            } catch (BancoDeDadosException ex) {
                Logger.getLogger(JPanelProcessoListar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(JPanelProcessoListar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ListarProcesso(telaprincipal.getFachada().ProcessoListar());
            } catch (BancoDeDadosException | SQLException ex) {
                exibe.fazTelaErro("erro ao acessar banco de dados", "erro ao acessar banco de dados");
                Logger.getLogger(JPanelProcessoListar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        telaprincipal.setJpanelTelaPrincipal(new JPanelProcessoCadastrar(this));
        telaprincipal.mudarTela();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnPesquisar.doClick();
        }
    }//GEN-LAST:event_txtPesquisaKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton RadioBtnComentario;
    private javax.swing.JRadioButton RadioBtnNome;
    private javax.swing.JRadioButton RadioBtnSituacao;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
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

            JPanelProcessoCadastrar z = new JPanelProcessoCadastrar(this, p);

            telaprincipal.setJpanelTelaPrincipal(z);
            telaprincipal.mudarTela();

        } catch (NumberFormatException | BancoDeDadosException | SQLException ex) {
            exibe.fazTelaErro("Erro", "Erro ao abrir!" + ex);
        }
    }

}

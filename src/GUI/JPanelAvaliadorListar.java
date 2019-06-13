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
import p5_negocio.entidades.Avaliador;
import p5_negocio.entidades.Processo;
import p6_excecoes.BancoDeDadosException;

/**
 *
 * @author o
 */
public class JPanelAvaliadorListar extends javax.swing.JPanel {

    /**
     * Creates new form JPanelVenda_listar_selecionado
     */
    TelasProjetoFinalPrincipal telaprincipal;
    JOptionPaneGui exibe;
    DefaultTableModel model;
    // para poder selecionar o avaliador
    JPanelProcessoCadastrar telaProcessoCadastrar;
    JPanelAvaliadorListar telaListar;
    int posicao;

    public JPanelAvaliadorListar(TelasProjetoFinalPrincipal telaprincipal) {
        initComponents();
        exibe = new JOptionPaneGui();
        this.telaprincipal = telaprincipal;
        try {
            ListarAvaliadores(telaprincipal.getFachada().AvaliadorListar());
        } catch (BancoDeDadosException | SQLException ex) {
            exibe.fazTelaErro("erro ao acessar banco de dados", "erro ao acessar banco de dados");
            Logger.getLogger(JPanelAvaliadorListar.class.getName()).log(Level.SEVERE, null, ex);
        }
        telaListar = this;
    }

    public JPanelAvaliadorListar(JPanelProcessoCadastrar telaProcessoCadastrar, int i) {
        initComponents();
        exibe = new JOptionPaneGui();
        this.telaprincipal = telaProcessoCadastrar.getTelaprincipal();
        this.telaProcessoCadastrar = telaProcessoCadastrar;
        try {
            ListarAvaliadores(telaprincipal.getFachada().AvaliadorListar());
        } catch (BancoDeDadosException | SQLException ex) {
            exibe.fazTelaErro("erro ao acessar banco de dados", "erro ao acessar banco de dados");
            Logger.getLogger(JPanelAvaliadorListar.class.getName()).log(Level.SEVERE, null, ex);
        }
        telaListar = this;
        posicao = i;
    }

    private Object[][] carregaDados(ArrayList<Avaliador> lista) {

        if (telaProcessoCadastrar != null) {
            Object[][] dados = new Object[lista.size()][7];

            for (int i = 0; i < lista.size(); i++) {

                dados[i][0] = lista.get(i).getId();
                dados[i][1] = lista.get(i).getNome();
                dados[i][2] = lista.get(i).getEmail();
                dados[i][3] = lista.get(i).getTelefone();
                dados[i][4] = lista.get(i).getInstituicao();
                dados[i][5] = "EDITAR";
                dados[i][6] = "SELECIONAR";

            }
            return dados;
        } else {

            // remove da lista o avaliador nignguem
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId() == 1) {
                    lista.remove(i);
                    break;
                }
            }

            Object[][] dados = new Object[lista.size()][6];

            for (int i = 0; i < lista.size(); i++) {

                dados[i][0] = lista.get(i).getId();
                dados[i][1] = lista.get(i).getNome();
                dados[i][2] = lista.get(i).getEmail();
                dados[i][3] = lista.get(i).getTelefone();
                dados[i][4] = lista.get(i).getInstituicao();
                dados[i][5] = "EDITAR";

            }
            return dados;
        }

    }

    private void ListarAvaliadores(ArrayList<Avaliador> lista) throws BancoDeDadosException, SQLException {

        if (!lista.isEmpty()) {
            if (telaProcessoCadastrar == null) {
                String[] colunas = {"Id", "Nome avaliador", "Email", "Telefone", "Instituição", "Abrir"};
                // limpar pesquisa
                Object[][] dados = carregaDados(lista);

                model = new javax.swing.table.DefaultTableModel(dados, colunas) {
                    boolean[] canEdit = new boolean[]{
                        false, false, false, false, false, true, true
                    };

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                };
                jTableResultadoPesquisa.setModel(model);
                //cria botao editar na lista

                ButtonColumn btEditar = new ButtonColumn(jTableResultadoPesquisa, 5) { // para alterar o numero é necessario tambem alterar canEdit em model
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fireEditingStopped();
                        try {

                            // desse jeito abre o primeiro item da lista inicial, dando erro quando para abrir quando eu sorteio por tempo sem acesso
                            //int id = Integer.parseInt("" + table.getModel().getValueAt(table.getSelectedRow(), 0));
                            int convertedRowSelected = jTableResultadoPesquisa.convertRowIndexToModel(jTableResultadoPesquisa.getSelectedRow());
                            int id = Integer.parseInt(jTableResultadoPesquisa.getModel().getValueAt(convertedRowSelected, 0).toString());
                            Avaliador av = telaprincipal.getFachada().procurarAvaliadorPorId(id);

                            JPanelAvaliadorCadastrar z = new JPanelAvaliadorCadastrar(telaListar, av);

                            telaprincipal.setJpanelTelaPrincipal(z);
                            telaprincipal.mudarTela();

                        } catch (BancoDeDadosException | SQLException ex) {
                            exibe.fazTelaErro("Erro", ex.getMessage() + "");

                        }

                    }
                };//Fim btn editar
            } // cria opçao para selecionar e retornar para tela de cadastro de processo
            else if (telaProcessoCadastrar != null) {
                String[] colunas = {"Id", "Nome avaliador", "Email", "Telefone", "Instituição", "Abrir", "Selecionar"};
                // limpar pesquisa
                Object[][] dados = carregaDados(lista);

                model = new javax.swing.table.DefaultTableModel(dados, colunas) {
                    boolean[] canEdit = new boolean[]{
                        false, false, false, false, false, true, true
                    };

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                };
                jTableResultadoPesquisa.setModel(model);
                //cria botao editar na lista

                ButtonColumn btEditar = new ButtonColumn(jTableResultadoPesquisa, 5) { // para alterar o numero é necessario tambem alterar canEdit em model
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fireEditingStopped();
                        try {

                            // desse jeito abre o primeiro item da lista inicial, dando erro quando para abrir quando eu sorteio por tempo sem acesso
                            //int id = Integer.parseInt("" + table.getModel().getValueAt(table.getSelectedRow(), 0));
                            int convertedRowSelected = jTableResultadoPesquisa.convertRowIndexToModel(jTableResultadoPesquisa.getSelectedRow());
                            int id = Integer.parseInt(jTableResultadoPesquisa.getModel().getValueAt(convertedRowSelected, 0).toString());
                            Avaliador av = telaprincipal.getFachada().procurarAvaliadorPorId(id);

                            JPanelAvaliadorCadastrar z = new JPanelAvaliadorCadastrar(telaListar, av);

                            telaprincipal.setJpanelTelaPrincipal(z);
                            telaprincipal.mudarTela();

                        } catch (BancoDeDadosException | SQLException ex) {
                            exibe.fazTelaErro("Erro", ex.getMessage() + "");

                        }

                    }
                };//Fim btn editar

                ButtonColumn btSeleciona = new ButtonColumn(jTableResultadoPesquisa, 6) { // para alterar o numero é necessario tambem alterar canEdit em model
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fireEditingStopped();
                        try {

                            // desse jeito abre o primeiro item da lista inicial, dando erro quando para abrir quando eu sorteio por tempo sem acesso
                            //int id = Integer.parseInt("" + table.getModel().getValueAt(table.getSelectedRow(), 0));
                            int convertedRowSelected = jTableResultadoPesquisa.convertRowIndexToModel(jTableResultadoPesquisa.getSelectedRow());
                            int id = Integer.parseInt(jTableResultadoPesquisa.getModel().getValueAt(convertedRowSelected, 0).toString());
                            Avaliador av = telaprincipal.getFachada().procurarAvaliadorPorId(id);

                            // passa variavel temporaria para o avaliador selecionado
                            if (posicao == 1) {
                                telaProcessoCadastrar.setAv1(av);
                                telaProcessoCadastrar.carregaDadosAvaliadorSelecioando(1);
                            } else if (posicao == 2) {
                                telaProcessoCadastrar.setAv2(av);
                                telaProcessoCadastrar.carregaDadosAvaliadorSelecioando(2);
                            } else if (posicao == 3) {
                                telaProcessoCadastrar.setAv3(av);
                                telaProcessoCadastrar.carregaDadosAvaliadorSelecioando(3);
                            } else {
                                exibe.fazTelaErro("erro", "erro a determinar posiçao do avaliador");
                            }

                            //retorna para tela de cadastro
                            telaprincipal.setJpanelTelaPrincipal(telaProcessoCadastrar);
                            telaprincipal.mudarTela();

                        } catch (BancoDeDadosException | SQLException ex) {
                            exibe.fazTelaErro("Erro", ex.getMessage() + "");

                        }

                    }
                };//Fim btn selecionar
            }
            jTable_Usuarios_PopupMenu();
        } else {
            String[] colunas = {"Id", "Nome avaliador", "Email", "Telefone", "Instituição", "Abrir", "selecionar"};
            // limpar pesquisa
            Object[][] dados = {{"", "", "", "", "", "", ""}};
            model = new javax.swing.table.DefaultTableModel(dados, colunas) {
                boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, true, true
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
                JPanelAvaliadorListar.this.jTable_ProfessorAbrir();
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
        RadioBtnProfessorAvaliado = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();

        setPreferredSize(null);

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

        buttonGroup1.add(RadioBtnProfessorAvaliado);
        RadioBtnProfessorAvaliado.setText("instituição");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(RadioBtnNome)
                                .addGap(28, 28, 28)
                                .addComponent(RadioBtnProfessorAvaliado))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jLabel5)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPesquisa))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnPesquisar)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RadioBtnNome)
                    .addComponent(RadioBtnProfessorAvaliado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(btnPesquisar))
        );

        jButton1.setText("cadastrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Lista de Avaliadores");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVoltar)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        voltarTelaAnterior();

    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed

        if (RadioBtnNome.isSelected()) {
            try {
                ArrayList<Avaliador> AvaliadorLista = telaprincipal.getFachada().procurarAvaliadorPorNome(txtPesquisa.getText());
                ListarAvaliadores(AvaliadorLista);
            } catch (BancoDeDadosException ex) {
                Logger.getLogger(JPanelAvaliadorListar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(JPanelAvaliadorListar.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (RadioBtnProfessorAvaliado.isSelected()) {
            try {
                ArrayList<Avaliador> AvaliadorLista = telaprincipal.getFachada().procurarAvaliadorPorInstituicao(txtPesquisa.getText());
                ListarAvaliadores(AvaliadorLista);
            } catch (BancoDeDadosException ex) {
                Logger.getLogger(JPanelAvaliadorListar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(JPanelAvaliadorListar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ListarAvaliadores(telaprincipal.getFachada().AvaliadorListar());
            } catch (BancoDeDadosException | SQLException ex) {
                exibe.fazTelaErro("erro ao acessar banco de dados", "erro ao acessar banco de dados");
                Logger.getLogger(JPanelAvaliadorListar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnPesquisar.doClick();
        }
    }//GEN-LAST:event_txtPesquisaKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        telaprincipal.setJpanelTelaPrincipal(new JPanelAvaliadorCadastrar(this));
        telaprincipal.mudarTela();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton RadioBtnNome;
    private javax.swing.JRadioButton RadioBtnProfessorAvaliado;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
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

            Avaliador av = telaprincipal.getFachada().procurarAvaliadorPorId(id);

            JPanelAvaliadorCadastrar z = new JPanelAvaliadorCadastrar(telaListar, av);

            telaprincipal.setJpanelTelaPrincipal(z);
            telaprincipal.mudarTela();

        } catch (NumberFormatException | BancoDeDadosException | SQLException ex) {
            exibe.fazTelaErro("Erro", "Erro ao abrir!\n" + ex);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.sql.SQLException;
import p5_negocio.entidades.Avaliador;
import p6_excecoes.BancoDeDadosException;

/**
 *
 * @author o
 */
public class JPanelAvaliadorCadastrar extends javax.swing.JPanel {

    TelasProjetoFinalPrincipal telaprincipal;
    JPanelAvaliadorListar telaAvaliadorListar;
    JOptionPaneGui exibe;
    Avaliador av;

    /**
     * Creates new form JPanelAvaliadorCadastrar
     */
    public JPanelAvaliadorCadastrar(TelasProjetoFinalPrincipal telaprincipal) {
        initComponents();
        exibe = new JOptionPaneGui();
        this.telaprincipal = telaprincipal;
    }

    public JPanelAvaliadorCadastrar(JPanelAvaliadorListar telaAvaliadorListar) {
        initComponents();
        exibe = new JOptionPaneGui();
        this.telaprincipal = telaAvaliadorListar.telaprincipal;
        this.telaAvaliadorListar = telaAvaliadorListar;
    }

    public JPanelAvaliadorCadastrar(JPanelAvaliadorListar telaAvaliadorListar, Avaliador av) {
        initComponents();
        exibe = new JOptionPaneGui();
        this.telaprincipal = telaAvaliadorListar.telaprincipal;
        this.telaAvaliadorListar = telaAvaliadorListar;
        this.av = av;

        txtID.setText(av.getId() + "");
        txtNome.setText(av.getNome());
        txtInstituicao.setText(av.getInstituicao());
        txtEmail.setText(av.getEmail());
        txtTelefone.setText(av.getTelefone());
        txtComentarios.setText(av.getComentarios());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTelefone = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtComentarios = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtInstituicao = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();

        txtComentarios.setColumns(20);
        txtComentarios.setRows(5);
        jScrollPane2.setViewportView(txtComentarios);

        jLabel1.setText("Nome");

        jButton1.setText("salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Instituição");

        btnVoltar.setText("voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        jLabel3.setText("Email");

        jLabel6.setText("ID");

        jLabel4.setText("Telefone");

        txtID.setEditable(false);
        txtID.setEnabled(false);

        jLabel5.setText("Comentarios");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVoltar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(txtTelefone)
                            .addComponent(txtEmail)
                            .addComponent(txtInstituicao)
                            .addComponent(txtID)
                            .addComponent(txtNome))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtInstituicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar)
                    .addComponent(jButton1))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        av = new Avaliador(
                txtNome.getText(),
                txtInstituicao.getText(),
                txtEmail.getText(),
                txtTelefone.getText(),
                txtComentarios.getText()
        );

        // se nao tem id entao é cadastro
        if (txtID.getText().equals("")) {
            System.out.println("cadastro");
            try {

                this.telaprincipal.getFachada().adicionarAvaliador(av);
                exibe.fazTelaInformacao("Cadastro realizado", "Cadastro realizado com sucesso!");
                btnVoltar.doClick();
            } catch (BancoDeDadosException | SQLException ex) {
                exibe.fazTelaErro("erro no Cadastro", "erro ao acessar banco de dados!");
                System.out.println(ex);
            }
        } else {
            av.setId(Integer.parseInt(txtID.getText()));
            // se ja tem o ID é atualizaçao, nesse caso vou usar o processo que ja esta carregado
            System.out.println("atualizacao");
            try {
                this.telaprincipal.getFachada().editarAvaliador(av);
                exibe.fazTelaInformacao("Cadastro atualizado", "Cadastro atualizado com sucesso!");
                btnVoltar.doClick();
            } catch (BancoDeDadosException | SQLException ex) {
                exibe.fazTelaErro("erro de Banco de dados", "erro ao acessar banco de dados!");
                System.out.println(ex);
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        if (telaAvaliadorListar != null) {
            telaprincipal.setJpanelTelaPrincipal(telaAvaliadorListar);
            telaprincipal.mudarTela();
        } else {
            telaprincipal.RetornarTelaPrincipal();
            telaprincipal.mudarTela();
        }
    }//GEN-LAST:event_btnVoltarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtComentarios;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtInstituicao;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}

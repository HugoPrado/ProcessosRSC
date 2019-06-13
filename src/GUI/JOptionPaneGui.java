/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.JOptionPane;

/**
 *
 * @author M
 */
public class JOptionPaneGui {
    
    public int fazTelaMenu(String titulo,String texto, String[] botoes){

        int opcao = JOptionPane.showOptionDialog(null, texto, titulo, 0, JOptionPane.PLAIN_MESSAGE, null, botoes, botoes[0]);
        return opcao;
    }
    
    public String fazTelaGetString(String titulo,String texto){
        String busca;
        boolean repetir;
        do{
            busca = JOptionPane.showInputDialog(null, texto,titulo,JOptionPane.QUESTION_MESSAGE );
            if (busca == null)
                break;
            repetir = busca.equals("");
            if (repetir)
                fazTelaInformacao("Aviso!","Não serão aceitos valores em branco!");
        } while (repetir);
        return busca;
    }
    
    public void fazTelaInformacao(String titulo, String texto){
        JOptionPane.showMessageDialog(null, texto, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void fazTelaErro(String titulo, String texto){
        JOptionPane.showMessageDialog(null, texto, titulo, JOptionPane.ERROR_MESSAGE);
    }
    
}

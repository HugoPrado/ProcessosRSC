/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p4_dados;

import GUI.JOptionPaneGui;
import java.sql.*;
import p6_excecoes.BancoDeDadosException;

/**
 *
 * @author ALUNO
 */
public abstract class GenericAccessDAO {
  
    
    
 private JOptionPaneGui exibe;
        
    public Connection getConnection() throws BancoDeDadosException{
        exibe = new JOptionPaneGui();
        try{
            // pegando a conexao com o DB
            Connection cx = DriverManager.getConnection("jdbc:ucanaccess://Processos.accdb");
            
            return cx;
        }
        catch (SQLException ex ){
            exibe.fazTelaErro("erro ao acessar banco de dados", ex+"");
            throw new BancoDeDadosException("erro ao conectar ao mysql");            
        }

    }//getConnection
    
    public PreparedStatement prepareStatement(String query) throws BancoDeDadosException {
        try{
            return getConnection().prepareStatement(query);
        }
        catch(SQLException ex){
            throw new BancoDeDadosException("erro no statement: " + ex);
        }
    }
    
    
    /*public static void main(String args[]) throws BancoDeDadosException{
    
        GenericAccessDAO gd = new GenericAccessDAO();
       
        gd.getConnection();

    }*/
    
    
    
}

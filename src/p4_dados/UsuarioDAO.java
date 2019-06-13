package p4_dados;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import p6_excecoes.BancoDeDadosException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import p5_negocio.entidades.Usuario;

/**
 *
 * @author ALUNO
 */
public class UsuarioDAO extends GenericAccessDAO {

    public void adicionarUsuario(Usuario u) throws SQLException, BancoDeDadosException {

        String query = "insert into usuario ( login , senha ) values ( ? , ? )";

        PreparedStatement ps = getConnection().prepareStatement(query);

        try {
            //pegando o login
            ps.setString(1, u.getLogin());
            //pegando a senha
            ps.setString(2, u.getSenha());
            // executar o comando
            ps.executeUpdate();

            //fechar conexao
            ps.close();

        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao inserir");
        }

    }

    public Usuario procurarUsuario(Usuario u) throws BancoDeDadosException {

        String query = "select * from usuario where login=? and senha = ?";

        PreparedStatement ps = prepareStatement(query);

        try {
            ps.setString(1, u.getLogin());
            ps.setString(2, u.getSenha());

            //faz consulta e retorna o valor
            ResultSet rs = ps.executeQuery();
            
            //se tem resultado
            if (rs.next()){
                Usuario user = new Usuario(
                    rs.getInt("id"),
                    rs.getString("login"),
                    rs.getString("senha")
                );
                
                //fechar conexoes
                rs.close();
                ps.close();
                
                return user;
            }//if
            return null;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao procurar" + ex);
        }
    }

    public ArrayList<Usuario> procurarUsuarioLogin(String login) throws BancoDeDadosException {

        ArrayList<Usuario> lista = new ArrayList();
        
        String query = "select * from usuario where login like ?";

        PreparedStatement ps = prepareStatement(query);

        try {
            ps.setString(1, "%"+login+"%");

            //faz consulta e retorna o valor
            ResultSet rs = ps.executeQuery();
            
            //se tem resultado
            while (rs.next()){
                Usuario u = new Usuario(
                    rs.getInt("id"),
                    rs.getString("login"),
                    rs.getString("senha")
                );
                lista.add(u);
            }
                //fechar conexoes
                rs.close();
                ps.close();
                
                return lista;
            
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao procurar" + ex);
        }
    }
    
    public ArrayList<Usuario> listarUsuarios() throws BancoDeDadosException {

        ArrayList<Usuario> lista = new ArrayList();
        
        String query = "select * from usuario ";

        PreparedStatement ps = prepareStatement(query);

        try {
            //faz consulta e retorna o valor
            ResultSet rs = ps.executeQuery();
            
            //se tem resultado
            while (rs.next()){
                Usuario u = new Usuario(
                    rs.getInt("id"),
                    rs.getString("login"),
                    rs.getString("senha")
                );
                lista.add(u);
            }    
                //fechar conexoes
                rs.close();
                ps.close();
            
                return lista;
           
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao procurar" + ex);
        }
    }
   
    public boolean AtualizarUsuarioSenha(int id, String senha) throws BancoDeDadosException{
             
        String query = "update usuario set senha = ? where id = ?";

        PreparedStatement ps = prepareStatement(query);
        
        try {
            
            ps.setString(1, senha);
            
            ps.setString(2, Integer.toString(id));
            
            //faz consulta e retorna se obteve sucesso ou nao
            int rs = ps.executeUpdate();
            //se tem resultado
            if (rs == 1)
                System.out.println("alteraçao realizada com sucesso");
            else System.out.println("alteraçao realizada com sucesso"); 
            
            //fechar conexoes
            ps.close();
            return true; // retorna que nao ocorreu problema
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao procurar" + ex);
        } 
    }
    
    public boolean ApagarUsuario(int codusuario)throws BancoDeDadosException, SQLException{
        String query = "delete from usuario where id = ?";

        PreparedStatement ps = prepareStatement(query);
        
        try {            
            ps.setString(1, Integer.toString(codusuario));

            //faz consulta e retorna se obteve sucesso ou nao
            int rs = ps.executeUpdate();
            //se tem resultado
            if (rs == 1)
                System.out.println("alteraçao realizada com sucesso");
            else System.out.println("alteraçao nao realizada"); 
            
            //fechar conexoes
            ps.close();

            return true; // retorna verdadeiro para funçao apagar
            
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao procurar" + ex);
        }
        
    }
    
    public static void main(String[] args) throws BancoDeDadosException, SQLException {
        UsuarioDAO a = new UsuarioDAO();

        //adiciona usuario
        Usuario u = new Usuario(0,"usuario2","123");
        a.adicionarUsuario(u);
        u = new Usuario("usuario2","123");
        a.procurarUsuario(u);
        
        a.AtualizarUsuarioSenha(1, "123456789");
        
        ArrayList<Usuario> x = a.listarUsuarios();
        String resultado = "";
        for (Usuario z : x )
        {
                String texto =
                        "Login: "+z.getLogin()+"\n"+
                        "Senha: "+z.getSenha()+"\n\n";
                resultado = resultado.concat(texto);
        }
        System.out.println(resultado);
        
        ArrayList<Usuario> p = a.procurarUsuarioLogin("usuario2");
        String resultado2 = "";
        for (Usuario z : p )
        {
                String texto =
                        "Login: "+z.getLogin()+"\n"+
                        "Senha: "+z.getSenha()+"\n\n";
                resultado2 = resultado2.concat(texto);
        }
        System.out.println(resultado2);
        
        a.ApagarUsuario(1);
        
    }//adicionar usuario

    
    
}
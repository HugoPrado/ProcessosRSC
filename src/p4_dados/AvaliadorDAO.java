/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p4_dados;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import p5_negocio.entidades.Avaliador;
import p6_excecoes.BancoDeDadosException;

/**
 *
 * @author o
 */
public class AvaliadorDAO extends GenericAccessDAO {

    // o opjetivo da funçao abaixo é nao dar erro de pontero nulo
    private java.sql.Date transformaDataJava_DataSQL(java.util.Date d) {
//            // convertendo as datas de java.util.Date para java.sql.Date;
//            java.util.Date dataUtil = new java.util.Date();
//            java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

        java.sql.Date dataSql = null;// inicialisando com valor nulo
        // o if é necessario para quando o valor nao for preenchido
        // se nao chegou como sendo nulo, entao transformar, caso contrario gravar como nulo
        if (d != null) {
            dataSql = new java.sql.Date(d.getTime());
        }
        return dataSql;
    }

    public boolean adicionarAvaliador(Avaliador a) throws BancoDeDadosException, SQLException {

        String query = "insert into [avaliador]"
                + " ( Nome, Instituicao, email, telefone, comentarios) "
                + " values ( ?, ?, ?, ?, ? );";

        PreparedStatement ps = getConnection().prepareStatement(query);

        try {
            //pegando a Quantidade
            ps.setString(1, a.getNome());
            ps.setString(2, a.getInstituicao());
            ps.setString(3, a.getEmail());
            ps.setString(4, a.getTelefone());
            ps.setString(5, a.getComentarios());

            // executar o comando
            ps.executeUpdate();

            //fechar conexao
            ps.close();

            return true;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao inserir" + ex);
        }

    }// fim adicionar professor

    public ArrayList<Avaliador> AvaliadorListar() throws BancoDeDadosException, SQLException {

        ArrayList<Avaliador> lista = new ArrayList();

        String query = "SELECT * FROM [Avaliador]";

        PreparedStatement ps = getConnection().prepareStatement(query);

        Avaliador a;
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                a = new Avaliador(
                        rs.getInt("id"),
                        rs.getString("Nome"),
                        rs.getString("Instituicao"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("comentarios")
                );
                lista.add(a);
            }//fim do while

            return lista;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }

    } //fim listar

    public ArrayList<Avaliador> AvaliadorListarPorIdProcesso(int id) throws BancoDeDadosException, SQLException {

        ArrayList<Avaliador> lista = new ArrayList();

        String query = "SELECT * FROM [Avaliador] where idProcesso = ?";

        PreparedStatement ps = getConnection().prepareStatement(query);

        ps.setInt(1, id);

        Avaliador a;
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                a = new Avaliador(
                        rs.getInt("id"),
                        rs.getString("Nome"),
                        rs.getString("Instituicao"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("comentarios")
                );
                lista.add(a);
            }//fim do while

            return lista;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }

    } //fim listar

    public Avaliador procurarAvaliadorPorId(int id) throws BancoDeDadosException, SQLException {

        String query = "SELECT * FROM [Avaliador] WHERE ID = ?";

        ArrayList<Avaliador> lista = new ArrayList();

        PreparedStatement ps = getConnection().prepareStatement(query);

        ps.setInt(1, id);

        Avaliador a;
        try {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                a = new Avaliador(
                        rs.getInt("id"),
                        rs.getString("Nome"),
                        rs.getString("Instituicao"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("comentarios")
                );
                return a;
            }//fim do while
            return null;

        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }
    } //fim procurarPorId

    public boolean editarAvaliador(Avaliador a) throws BancoDeDadosException, SQLException {

        String query = "update [avaliador] "
                + "set Nome = ?, Instituicao = ?, email = ?, telefone = ?, comentarios = ? "
                + "  where id = ?";

        PreparedStatement ps = getConnection().prepareStatement(query);

        try {

            //pegando a Quantidade
            ps.setString(1, a.getNome());
            ps.setString(2, a.getInstituicao());
            ps.setString(3, a.getEmail());
            ps.setString(4, a.getTelefone());
            ps.setString(5, a.getComentarios());
            ps.setInt(6, a.getId());

            //faz consulta e retorna se obteve sucesso ou nao
            int rs = ps.executeUpdate();
            //se tem resultado
            if (rs == 1) {
                System.out.println("Avaliador - alteraçao realizada com sucesso");
            } else {
                System.out.println("Avaliador - alteraçao nao realizada");
            }

            //fechar conexoes
            ps.close();
            return true; // retorna que nao ocorreu problema
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao atualisar" + ex);
        }
    }

    public ArrayList<Avaliador> procurarAvaliadorPorNome(String nomeAval) throws BancoDeDadosException, SQLException {

        ArrayList<Avaliador> lista = new ArrayList();

        String query = "SELECT * FROM [avaliador] WHERE nome Like ?";
        PreparedStatement ps = getConnection().prepareStatement(query);
        ps.setString(1, "%" + nomeAval + "%");

        Avaliador a;
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                a = new Avaliador(
                        rs.getInt("id"),
                        rs.getString("Nome"),
                        rs.getString("Instituicao"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("comentarios")
                );
                lista.add(a);
            }//fim do while

            return lista;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }

    } //fim pesquisarPornome
    
        public ArrayList<Avaliador> procurarAvaliadorPorInstituicao(String nomeAval) throws BancoDeDadosException, SQLException {

        ArrayList<Avaliador> lista = new ArrayList();

        String query = "SELECT * FROM [avaliador] WHERE Instituicao Like ?";
        PreparedStatement ps = getConnection().prepareStatement(query);
        ps.setString(1, "%" + nomeAval + "%");

        Avaliador a;
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                a = new Avaliador(
                        rs.getInt("id"),
                        rs.getString("Nome"),
                        rs.getString("Instituicao"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("comentarios")
                );
                lista.add(a);
            }//fim do while

            return lista;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }

    } //fim pesquisarPornome

    public static void main(String[] Args) throws BancoDeDadosException, SQLException {
        //AvaliadorDAO pdao = new AvaliadorDAO();

    }

}// fim classe AccesDao

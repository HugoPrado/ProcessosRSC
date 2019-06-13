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
import p5_negocio.entidades.Processo;
import p6_excecoes.BancoDeDadosException;

/**
 *
 * @author o
 */
public class ProcessoDAO extends GenericAccessDAO {

    private int idProcesso = -1;

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

    public int adicionarProcesso(Processo p) throws BancoDeDadosException, SQLException {

        String query = "INSERT into [Processo] "
                + "( nome, nivelRSC, situacao, inicioProcesso, conclusaoProcesso, comentarios) "
                + " values (?,?,?,?,?,?); ";

        PreparedStatement ps = getConnection().prepareStatement(query);

        try {
            ps.setString(1, p.getNome());
            ps.setInt(2, p.getNivelSrc());
            ps.setString(3, p.getSituacao());

            //lembrando que tem que transformar dataJAva em dataSQL
            ps.setDate(4, transformaDataJava_DataSQL(p.getInicioProcesso()));
            ps.setDate(5, transformaDataJava_DataSQL(p.getConclusaoProcesso()));

            ps.setString(6, p.getComentarios());

            // executar o comando
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    this.setIdProcessoInserir(rs.getInt(1));
                    return this.getIdProcessoInserir();
                    //System.out.println(idProcesso);
                }
                rs.close();
            } catch (SQLException ex) {
                throw new BancoDeDadosException("erro ao retornar chave primaria do processo" + ex);
            }

            //fechar conexao
            ps.close();

            return -1;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao inserir" + ex);
        }

    }// fim adicionar Processo

    public ArrayList<Processo> ProcessoListar() throws BancoDeDadosException, SQLException {

        ArrayList<Processo> lista = new ArrayList();

        String query = "SELECT * FROM [Processo]";

        PreparedStatement ps = getConnection().prepareStatement(query);

        Processo p;
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                p = new Processo(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("nivelRSC"),
                        rs.getString("situacao"),
                        rs.getDate("inicioProcesso"),
                        rs.getDate("conclusaoProcesso"),
                        rs.getString("comentarios")
                );
                lista.add(p);
            }//fim do while

            return lista;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }

    } //fim listar

    public Processo procurarProcessoPorId(int id) throws BancoDeDadosException, SQLException {

        String query = "SELECT * FROM [Processo] WHERE ID = ?";

        PreparedStatement ps = getConnection().prepareStatement(query);

        ps.setInt(1, id);

        Processo p;
        try {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Processo(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("nivelRSC"),
                        rs.getString("situacao"),
                        rs.getDate("inicioProcesso"),
                        rs.getDate("conclusaoProcesso"),
                        rs.getString("comentarios")
                );
                return p;
            }//fim do while           
            return null;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }

    } //fim procurarPorId

    public boolean editarProcesso(Processo p) throws BancoDeDadosException, SQLException {

        String query = "update Processo set nome = ?, nivelRSC = ?, situacao = ?, inicioProcesso = ?, conclusaoProcesso = ?, comentarios = ?"
                + "  where id = ?";

        PreparedStatement ps = getConnection().prepareStatement(query);

        try {

            ps.setString(1, p.getNome());
            ps.setInt(2, p.getNivelSrc());
            ps.setString(3, p.getSituacao());

            //lembrando que tem que transformar dataJAva em dataSQL
            ps.setDate(4, transformaDataJava_DataSQL(p.getInicioProcesso()));
            ps.setDate(5, transformaDataJava_DataSQL(p.getConclusaoProcesso()));

            ps.setString(6, p.getComentarios());

            ps.setInt(7, p.getId());

            //faz consulta e retorna se obteve sucesso ou nao
            int rs = ps.executeUpdate();
            //se tem resultado
            if (rs == 1) {
                System.out.println("Processo - alteraçao realizada com sucesso");
            } else {
                System.out.println("Processo - alteraçao nao realizada");
            }

            //fechar conexoes
            ps.close();
            return true; // retorna que nao ocorreu problema
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao atualisar" + ex);
        }
    }

    public ArrayList<Processo> procurarProcessoPorNomeProfessor(String nomeProf) throws BancoDeDadosException, SQLException {

        ArrayList<Processo> lista = new ArrayList();

        String query = "SELECT * FROM Processo WHERE nome Like ?";
        PreparedStatement ps = getConnection().prepareStatement(query);
        ps.setString(1, "%" + nomeProf + "%");

        Processo p;
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                p = new Processo(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("nivelRSC"),
                        rs.getString("situacao"),
                        rs.getDate("inicioProcesso"),
                        rs.getDate("conclusaoProcesso"),
                        rs.getString("comentarios")
                );
                lista.add(p);
            }//fim do while

            return lista;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }

    } //fim pesquisarPornome

    public ArrayList<Processo> procurarProcessoPorSituacao(String situacao) throws BancoDeDadosException, SQLException {

        ArrayList<Processo> lista = new ArrayList();

        String query = "SELECT * FROM Processo where situacao Like ?;";
        PreparedStatement ps = getConnection().prepareStatement(query);
        ps.setString(1, "%" + situacao + "%");

        Processo p;
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                p = new Processo(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("nivelRSC"),
                        rs.getString("situacao"),
                        rs.getDate("inicioProcesso"),
                        rs.getDate("conclusaoProcesso"),
                        rs.getString("comentarios")
                );
                lista.add(p);
            }//fim do while

            return lista;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }

    } //fim pesquisarPornome  

    
    
     public ArrayList<Processo> procurarProcessoPorComentario(String comen) throws BancoDeDadosException, SQLException {

        ArrayList<Processo> lista = new ArrayList();

        String query = "SELECT * FROM Processo where comentarios Like ?;";
        PreparedStatement ps = getConnection().prepareStatement(query);
        ps.setString(1, "%" + comen + "%");

        Processo p;
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                p = new Processo(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("nivelRSC"),
                        rs.getString("situacao"),
                        rs.getDate("inicioProcesso"),
                        rs.getDate("conclusaoProcesso"),
                        rs.getString("comentarios")
                );
                lista.add(p);
            }//fim do while

            return lista;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }

    } //fim pesquisarPornome 
    public static void main(String[] Args) throws BancoDeDadosException, SQLException {
        ProcessoDAO pdao = new ProcessoDAO();
//       //criando uma variavel no formato date-time para testar
//       String data = "2137-01-08 12:12:12";
//       Avaliacao av = new Avaliacao("","","","",data,data,data,data,"");
//       Processo novo = new Processo( "novo Teste",2,"",data,data,
//               av,av,av,
//               "");
//       pdao.adicionarProcesso(novo);

//        for(Processo x:pdao.ProcessoListar()){
//            System.out.println(x.getId());
//            System.out.println(x.getNome());
//            System.out.println(x.getInicioProcesso());
//            System.out.println(x.getAvaliador1().getNome());
//            System.out.println(x.getAvaliador2().getNome());
//            System.out.println(x.getAvaliador3().getNome());
//        }
//       System.out.println(pdao.pesquisarPorId(1).getNome());
//       System.out.println(pdao.pesquisarPorId(2).getNome());
//       if (pdao.pesquisarPorId(3)!=null){
//            System.out.println(pdao.pesquisarPorId(3).getNome());
//       }
//        //criando uma variavel no formato date-time para testar
//        String data = "3587-01-08 10:10:10";
//        Avaliacao av = new Avaliacao("", "", "", "", null, null, null, null, "");
//        Processo novo = new Processo(1, " Teste novao", 3, "", null, null,
//                av, av, av,
//                "");
//        pdao.editarProcesso(novo);
    }

    /**
     * @return the idProcesso
     */
    public int getIdProcessoInserir() {
        return idProcesso;
    }

    /**
     * @param idProcesso the idProcesso to set
     */
    public void setIdProcessoInserir(int idProcesso) {
        this.idProcesso = idProcesso;
    }

}// fim classe AccesDao

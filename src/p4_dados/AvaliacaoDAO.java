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
import p5_negocio.entidades.Avaliacao;
import p6_excecoes.BancoDeDadosException;

/**
 *
 * @author o
 */
public class AvaliacaoDAO extends GenericAccessDAO {

    private AvaliadorDAO avaliadorDao;

    public AvaliacaoDAO() {
        avaliadorDao = new AvaliadorDAO();
    }

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

    public boolean adicionarAvaliacao(Avaliacao a) throws BancoDeDadosException, SQLException {

        System.out.println(a.getAvaliador().getNome());
        System.out.println(a.getAvaliador().getId());

        String query = "insert into [avaliacao]"
                + " (idProcesso, idAvaliador, envioAceite, respostaAceite, envioDocumento, respostaDocumento, parecer, tipo) "
                + " values ( ?, ?, ?, ?, ?, ?, ?,? );";

        PreparedStatement ps = getConnection().prepareStatement(query);

        try {
            //pegando a Quantidade
            ps.setInt(1, a.getIdProcesso());
            ps.setInt(2, a.getAvaliador().getId());
            //lembrando que tem que transformar dataJAva em dataSQL
            ps.setDate(3, transformaDataJava_DataSQL(a.getEnvioAceite()));
            ps.setDate(4, transformaDataJava_DataSQL(a.getRespostaAceite()));
            ps.setDate(5, transformaDataJava_DataSQL(a.getEnvioDocumentos()));
            ps.setDate(6, transformaDataJava_DataSQL(a.getRespostaDocumentos()));
            ps.setString(7, a.getParecer());
            ps.setInt(8, a.getTipo());

            // executar o comando
            ps.executeUpdate();

            //fechar conexao
            ps.close();

            return true;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao inserir" + ex);
        }

    }// fim adicionar professor

    public ArrayList<Avaliacao> AvaliacaoListar() throws BancoDeDadosException, SQLException {

        ArrayList<Avaliacao> lista = new ArrayList();

        String query = "SELECT * FROM [Avaliacao]";

        PreparedStatement ps = getConnection().prepareStatement(query);

        Avaliacao a;
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                a = new Avaliacao(
                        rs.getInt("id"),
                        rs.getInt("idProcesso"),
                        avaliadorDao.procurarAvaliadorPorId(rs.getInt("idAvaliador")),
                        rs.getDate("envioAceite"),
                        rs.getDate("respostaAceite"),
                        rs.getDate("envioDocumento"),
                        rs.getDate("respostaDocumento"),
                        rs.getString("parecer"),
                        rs.getInt("tipo")
                );
                lista.add(a);
            }//fim do while

            return lista;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }

    } //fim listar

    public ArrayList<Avaliacao> AvaliacaoListarPorIdProcesso(int id) throws BancoDeDadosException, SQLException {

        ArrayList<Avaliacao> lista = new ArrayList();

        String query = "SELECT * FROM [Avaliacao] where idProcesso = ?";

        PreparedStatement ps = getConnection().prepareStatement(query);

        ps.setInt(1, id);

        Avaliacao a;
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                a = new Avaliacao(
                        rs.getInt("id"),
                        rs.getInt("idProcesso"),
                        avaliadorDao.procurarAvaliadorPorId(rs.getInt("idAvaliador")),
                        rs.getDate("envioAceite"),
                        rs.getDate("respostaAceite"),
                        rs.getDate("envioDocumento"),
                        rs.getDate("respostaDocumento"),
                        rs.getString("parecer"),
                        rs.getInt("tipo")
                );
                lista.add(a);

            }//fim do while

            return lista;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }

    } //fim listar

    public Avaliacao procurarAvaliacaoPorId(int id) throws BancoDeDadosException, SQLException {

        String query = "SELECT * FROM [Avaliacao] WHERE ID = ?";

        ArrayList<Avaliacao> lista = new ArrayList();

        PreparedStatement ps = getConnection().prepareStatement(query);

        ps.setInt(1, id);

        Avaliacao a;
        try {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                a = new Avaliacao(
                        rs.getInt("id"),
                        rs.getInt("idProcesso"),
                        avaliadorDao.procurarAvaliadorPorId(rs.getInt("idAvaliador")),
                        rs.getDate("envioAceite"),
                        rs.getDate("respostaAceite"),
                        rs.getDate("envioDocumento"),
                        rs.getDate("respostaDocumento"),
                        rs.getString("parecer"),
                        rs.getInt("tipo")
                );
                return a;
            }//fim do while
            return null;

        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }
    } //fim procurarPorId

    public boolean editarAvaliacao(Avaliacao a) throws BancoDeDadosException, SQLException {

        System.out.println(a.getAvaliador().getNome());
        System.out.println(a.getAvaliador().getId());

        String query = "update [avaliacao] "
                + "set idProcesso = ? , idAvaliador = ?, envioAceite = ?,  respostaAceite = ?,  envioDocumento = ?,  respostaDocumento = ?,  parecer = ? "
                + "  where id = ? and tipo = ?;";

        PreparedStatement ps = getConnection().prepareStatement(query);

        try {




            //pegando a Quantidade
            ps.setInt(1, a.getIdProcesso());
            ps.setInt(2, a.getAvaliador().getId());
            //lembrando que tem que transformar dataJAva em dataSQL
            ps.setDate(3, transformaDataJava_DataSQL(a.getEnvioAceite()));
            ps.setDate(4, transformaDataJava_DataSQL(a.getRespostaAceite()));
            ps.setDate(5, transformaDataJava_DataSQL(a.getEnvioDocumentos()));
            ps.setDate(6, transformaDataJava_DataSQL(a.getRespostaDocumentos()));
            ps.setString(7, a.getParecer());
            ps.setInt(8, a.getId());
            ps.setInt(9, a.getTipo());

            //faz consulta e retorna se obteve sucesso ou nao
            int rs = ps.executeUpdate();
            //se tem resultado
            if (rs == 1) {
                System.out.println("Avaliacao - alteraçao realizada com sucesso");
            } else {
                System.out.println("Avaliacao - alteraçao nao realizada");
            }

            //fechar conexoes
            ps.close();
            return true; // retorna que nao ocorreu problema
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao atualisar" + ex);
        }
    }

    public ArrayList<Avaliacao> procurarAvalaicaoProfessorPorNomeAvaliador(String nomeAval) throws BancoDeDadosException, SQLException {

        ArrayList<Avaliacao> lista = new ArrayList();

        String query = "SELECT Avaliacao.id AS Avaliacao_id, Avaliacao.idProcesso, Avaliacao.idAvaliador, Avaliacao.envioAceite, Avaliacao.respostaAceite, Avaliacao.envioDocumento, Avaliacao.respostaDocumento, Avaliacao.parecer, Avaliacao.tipo\n"
                + "FROM Avaliador INNER JOIN Avaliacao ON Avaliador.[id] = Avaliacao.[idAvaliador]\n"
                + " WHERE nome Like ?";
        PreparedStatement ps = getConnection().prepareStatement(query);
        ps.setString(1, "%" + nomeAval + "%");

        Avaliacao a;
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                a = new Avaliacao(
                        rs.getInt("Avaliacao_id"),
                        rs.getInt("idProcesso"),
                        avaliadorDao.procurarAvaliadorPorId(rs.getInt("idAvaliador")),
                        rs.getDate("envioAceite"),
                        rs.getDate("respostaAceite"),
                        rs.getDate("envioDocumento"),
                        rs.getDate("respostaDocumento"),
                        rs.getString("parecer"),
                        rs.getInt("tipo")
                );
                lista.add(a);
            }//fim do while

            return lista;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }

    } //fim pesquisarPornome

    public ArrayList<Avaliacao> procurarAvalaicaoProfessorPorNomeProfessor(String nomeAval) throws BancoDeDadosException, SQLException {

        ArrayList<Avaliacao> lista = new ArrayList();

        String query = "SELECT PROCESSO.nome AS nome , Avaliacao.[id] AS id FROM PROCESSO INNER JOIN Avaliacao ON PROCESSO.[ID] = Avaliacao.[idProcesso]  WHERE PROCESSO.nome Like ?;";

        PreparedStatement ps = getConnection().prepareStatement(query);
        ps.setString(1, "%" + nomeAval + "%");

        try {
            ResultSet rs = ps.executeQuery();
            ArrayList<Integer> listaId = new ArrayList<Integer>();

            while (rs.next()) {
                listaId.add(rs.getInt("id"));
            }//fim do while
//
//          System.out.println(listaId + "\n\n\n");
            for (int i : listaId) {
                lista.add(procurarAvaliacaoPorId(i));
            }

            return lista;
        } catch (SQLException ex) {
            throw new BancoDeDadosException("erro ao conectar ao mysql" + ex);
        }

    } //fim pesquisarPornome

    public static void main(String[] Args) throws BancoDeDadosException, SQLException {
        AvaliacaoDAO pdao = new AvaliacaoDAO();
//       //criando uma variavel no formato date-time para testar
//       String data = "2137-01-08 12:12:12";
//       Avaliacao av = new Avaliacao("","","","",data,data,data,data,"");
//       Avaliacao novo = new Avaliacao( "novo Teste",2,"",data,data,
//               av,av,av,
//               "");
//       pdao.adicionarProfessor(novo);

//        for(Avaliacao x:pdao.AvaliacaoListar()){
//            System.out.println(x.getId());
//            System.out.println(x.getNome());
//            System.out.println(x.getInicioAvaliacao());
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
//        Avaliacao novo = new Avaliacao(1, " Teste novao", 3, "", null, null,
//                av, av, av,
//                "");
//        pdao.editarProfessor(novo);
    }

}// fim classe AccesDao

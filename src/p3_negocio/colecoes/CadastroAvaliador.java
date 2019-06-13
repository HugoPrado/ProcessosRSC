package p3_negocio.colecoes;

import java.sql.SQLException;
import java.util.ArrayList;
import p4_dados.AvaliadorDAO;
import p5_negocio.entidades.Avaliador;
import p5_negocio.entidades.Processo;
import p6_excecoes.BancoDeDadosException;

/**
 * Classe que representa a colecao de negocio do cliente
 */
public class CadastroAvaliador {

    private AvaliadorDAO avaliador;

    public CadastroAvaliador() {
        avaliador = new AvaliadorDAO();
    }

    public boolean adicionarAvaliador(Avaliador a) throws BancoDeDadosException, SQLException {
        return avaliador.adicionarAvaliador(a);
    }// fim adicionar professor

    public ArrayList<Avaliador> AvaliadorListar() throws BancoDeDadosException, SQLException {
        return avaliador.AvaliadorListar();
    } //fim listar

    public ArrayList<Avaliador> AvaliadorListarPorIdProcesso(int id) throws BancoDeDadosException, SQLException {
        return avaliador.AvaliadorListarPorIdProcesso(id);
    } //fim listar

    public Avaliador procurarAvaliadorPorId(int id) throws BancoDeDadosException, SQLException {
        return avaliador.procurarAvaliadorPorId(id);
    } //fim procurarPorId

    public boolean editarAvaliador(Avaliador a) throws BancoDeDadosException, SQLException {
        return avaliador.editarAvaliador(a);
    }

    public ArrayList<Avaliador> procurarAvaliadorPorNome(String nomeAval) throws BancoDeDadosException, SQLException {
        return avaliador.procurarAvaliadorPorNome(nomeAval);
    } //fim pesquisarPornome

    public ArrayList<Avaliador> procurarAvaliadorPorInstituicao(String nomeAval) throws BancoDeDadosException, SQLException {
        return avaliador.procurarAvaliadorPorNome(nomeAval);
    } //fim pesquisarPorinstituicao

} // enc class

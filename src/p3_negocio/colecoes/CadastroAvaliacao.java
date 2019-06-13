package p3_negocio.colecoes;

import java.sql.SQLException;
import java.util.ArrayList;
import p4_dados.AvaliacaoDAO;
import p5_negocio.entidades.Avaliacao;
import p5_negocio.entidades.Processo;
import p6_excecoes.BancoDeDadosException;

/**
 * Classe que representa a colecao de negocio do cliente
 */
public class CadastroAvaliacao {

    private AvaliacaoDAO avaliacao;

    public CadastroAvaliacao() {
        avaliacao = new AvaliacaoDAO();
    }

    public boolean adicionarAvaliacao(Avaliacao a) throws BancoDeDadosException, SQLException {
        return avaliacao.adicionarAvaliacao(a);
    }// fim adicionar professor

    public ArrayList<Avaliacao> AvaliacaoListar() throws BancoDeDadosException, SQLException {
        return avaliacao.AvaliacaoListar();
    } //fim listar

    public ArrayList<Avaliacao> AvaliacaoListarPorIdProcesso(int id) throws BancoDeDadosException, SQLException {
        return avaliacao.AvaliacaoListarPorIdProcesso(id);
    } //fim listar

    public Avaliacao procurarAvaliacaoPorId(int id) throws BancoDeDadosException, SQLException {
        return avaliacao.procurarAvaliacaoPorId(id);
    } //fim procurarPorId

    public boolean editarAvaliacao(Avaliacao a) throws BancoDeDadosException, SQLException {
        return avaliacao.editarAvaliacao(a);
    }

    public ArrayList<Avaliacao> procurarAvalaicaoProfessorPorNomeAvaliador(String nomeAval) throws BancoDeDadosException, SQLException {
        return avaliacao.procurarAvalaicaoProfessorPorNomeAvaliador(nomeAval);
    } //fim pesquisarPornome
    
    public ArrayList<Avaliacao> procurarAvalaicaoProfessorPorNomeProfessor(String nomeAval) throws BancoDeDadosException, SQLException {
        return avaliacao.procurarAvalaicaoProfessorPorNomeProfessor(nomeAval);
    } //fim pesquisarPornome

} // enc class

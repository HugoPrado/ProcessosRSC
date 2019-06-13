package negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import p3_negocio.colecoes.CadastroProcesso;
import p3_negocio.colecoes.CadastroUsuario;
import p3_negocio.colecoes.CadastroAvaliacao;
import p4_dados.AvaliadorDAO;
import p5_negocio.entidades.Processo;
import p5_negocio.entidades.Usuario;
import p5_negocio.entidades.Avaliacao;
import p5_negocio.entidades.Avaliador;
import p6_excecoes.BancoDeDadosException;

/**
 * Classe que representa a fachada de acesso ao sistema Ela deve ter como
 * atributo todos as colecoes de negocio
 */
public class Fachada {

    private CadastroProcesso processo;
    private CadastroUsuario usuarios;
    private CadastroAvaliacao avaliacao;
    private AvaliadorDAO avaliador;

    //No construtor da fachada o ideal e inicalizar todos os atributos
    public Fachada() {
        processo = new CadastroProcesso();
        avaliacao = new CadastroAvaliacao();
        usuarios = new CadastroUsuario();
        avaliador = new AvaliadorDAO();
    }

    //////////////////////////////////////////////////////////
    //###############   Processo   #####################
    //////////////////////////////////////////////////////////
    public int adicionarProcesso(Processo p) throws BancoDeDadosException, SQLException {
        return processo.adicionarProcesso(p);
    }// fim adicionar Processo

    public ArrayList<Processo> ProcessoListar() throws BancoDeDadosException, SQLException {
        return processo.ProcessoListar();
    } //fim listar

    public Processo procurarProcessoPorId(int id) throws BancoDeDadosException, SQLException {
        return processo.procurarProcessoPorId(id);
    } //fim procurarPorId

    public boolean editarProcesso(Processo p) throws BancoDeDadosException, SQLException {
        return processo.editarProcesso(p);
    }

    public ArrayList<Processo> procurarProcessoPorNomeProfessor(String nomeProf) throws BancoDeDadosException, SQLException {
        return processo.procurarProcessoPorNomeProfessor(nomeProf);
    } //fim pesquisarPornome

    public ArrayList<Processo> procurarProcessoPorComentario(String comen) throws BancoDeDadosException, SQLException {
        return processo.procurarProcessoPorComentario(comen);
    } //fim pesquisarPornome

    public ArrayList<Processo> procurarProcessoPorSituacao(String situacao) throws BancoDeDadosException, SQLException {
        return processo.procurarProcessoPorSituacao(situacao);
    }

    public int getIdProcessoInserir() {
        return processo.getIdProcessoInserir();
    }

    public void setIdProcessoInserir(int idProcesso) {
        processo.setIdProcessoInserir(idProcesso);
    }

    //////////////////////////////////////////////////////////
    //###############   Avaliacao   #####################
    //////////////////////////////////////////////////////////
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

    //////////////////////////////////////////////////////////
    //###############   Avaliador #####################
    //////////////////////////////////////////////////////////
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

    //////////////////////////////////////////////////////////
    //###############   usuarios   #####################
    //////////////////////////////////////////////////////////
    public void adicionarUsuario(Usuario u) throws BancoDeDadosException, SQLException {
        usuarios.adicionarUsuario(u);
    }

    public Usuario procurarUsuario(Usuario u) throws BancoDeDadosException {
        return usuarios.procurarUsuario(u);
    }

    public ArrayList<Usuario> procurarUsuarioLogin(String login) throws BancoDeDadosException {
        return usuarios.procurarUsuarioLogin(login);
    }

    public ArrayList<Usuario> listarUsuarios() throws BancoDeDadosException {
        return usuarios.listarUsuarios();

    }

    public boolean AtualizarUsuarioSenha(int id, String senha) throws BancoDeDadosException {
        return usuarios.AtualizarUsuarioSenha(id, senha);
    }

    public boolean ApagarUsuario(int codusuario) throws BancoDeDadosException, SQLException {
        return usuarios.ApagarUsuario(codusuario);
    }

}

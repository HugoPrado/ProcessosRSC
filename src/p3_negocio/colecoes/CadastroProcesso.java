package p3_negocio.colecoes;

import java.sql.SQLException;
import java.util.ArrayList;
import p4_dados.ProcessoDAO;
import p5_negocio.entidades.Processo;
import p6_excecoes.BancoDeDadosException;

/**
 * Classe que representa a colecao de negocio do cliente
 */
public class CadastroProcesso {

    private ProcessoDAO processo;

    public CadastroProcesso() {
        processo = new ProcessoDAO();
    }

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

    public ArrayList<Processo> procurarProcessoPorSituacao(String situacao) throws BancoDeDadosException, SQLException {
        return processo.procurarProcessoPorSituacao(situacao);
    }

    public ArrayList<Processo> procurarProcessoPorComentario(String comen) throws BancoDeDadosException, SQLException {
        return processo.procurarProcessoPorComentario(comen);
    } //fim pesquisarPornome

    public int getIdProcessoInserir() {
        return processo.getIdProcessoInserir();
    }

    public void setIdProcessoInserir(int idProcesso) {
        processo.setIdProcessoInserir(idProcesso);
    }

}// end class

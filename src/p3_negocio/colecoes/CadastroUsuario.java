package p3_negocio.colecoes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.SQLException;
import java.util.ArrayList;
import p4_dados.UsuarioDAO;
import p5_negocio.entidades.Usuario;
import p6_excecoes.BancoDeDadosException;

/**
 *
 * @author o
 */
public class CadastroUsuario {

    private UsuarioDAO usuarios;

    public CadastroUsuario() {
        usuarios = new UsuarioDAO();
    }

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

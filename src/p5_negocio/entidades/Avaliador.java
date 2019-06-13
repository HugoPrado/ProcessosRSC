/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p5_negocio.entidades;

import java.util.Date;

/**
 *
 * @author o
 */
public class Avaliador {

    private int id;
    private String nome;
    private String instituicao;
    private String email;
    private String telefone;
    private String comentarios;

    public Avaliador(int id, String nome, String instituicao, String email, String telefone, String comentarios) {
        this.id = id;
        this.nome = nome;
        this.instituicao = instituicao;
        this.email = email;
        this.telefone = telefone;
        this.comentarios = comentarios;
    }
    public Avaliador( String nome, String instituicao, String email, String telefone, String comentarios) {
        this.nome = nome;
        this.instituicao = instituicao;
        this.email = email;
        this.telefone = telefone;
        this.comentarios = comentarios;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the instituicao
     */
    public String getInstituicao() {
        return instituicao;
    }

    /**
     * @param instituicao the instituicao to set
     */
    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * @param telefone the telefone to set
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * @return the comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    
    
} // end class

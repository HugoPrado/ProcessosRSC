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
public class Avaliacao {

    private int id;
    private int idProcesso;
    private Avaliador avaliador;
    private Date envioAceite;
    private Date respostaAceite;
    private Date envioDocumentos;
    private Date respostaDocumentos;
    private String parecer;
    private int tipo;

    public Avaliacao(int id, int idProcesso, Avaliador avaliador, Date envioAceite, Date respostaAceite, Date envioDocumentos, Date respostaDocumentos, String parecer, int tipo) {
        this.id = id;
        this.idProcesso = idProcesso;
        this.avaliador = avaliador;
        this.envioAceite = envioAceite;
        this.respostaAceite = respostaAceite;
        this.envioDocumentos = envioDocumentos;
        this.respostaDocumentos = respostaDocumentos;
        this.parecer = parecer;
        this.tipo = tipo;
    }

    public Avaliacao(int idProcesso, Avaliador avaliador, Date envioAceite, Date respostaAceite, Date envioDocumentos, Date respostaDocumentos, String parecer, int tipo) {
        this.idProcesso = idProcesso;
        this.avaliador = avaliador;
        this.envioAceite = envioAceite;
        this.respostaAceite = respostaAceite;
        this.envioDocumentos = envioDocumentos;
        this.respostaDocumentos = respostaDocumentos;
        this.parecer = parecer;
        this.tipo = tipo;
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
     * @return the idProcesso
     */
    public int getIdProcesso() {
        return idProcesso;
    }

    /**
     * @param idProcesso the idProcesso to set
     */
    public void setIdProcesso(int idProcesso) {
        this.idProcesso = idProcesso;
    }

    /**
     * @return the avaliador
     */
    public Avaliador getAvaliador() {
        return avaliador;
    }

    /**
     * @param avaliador the avaliador to set
     */
    public void setAvaliador(Avaliador avaliador) {
        this.avaliador = avaliador;
    }

    /**
     * @return the envioAceite
     */
    public Date getEnvioAceite() {
        return envioAceite;
    }

    /**
     * @param envioAceite the envioAceite to set
     */
    public void setEnvioAceite(Date envioAceite) {
        this.envioAceite = envioAceite;
    }

    /**
     * @return the respostaAceite
     */
    public Date getRespostaAceite() {
        return respostaAceite;
    }

    /**
     * @param respostaAceite the respostaAceite to set
     */
    public void setRespostaAceite(Date respostaAceite) {
        this.respostaAceite = respostaAceite;
    }

    /**
     * @return the envioDocumentos
     */
    public Date getEnvioDocumentos() {
        return envioDocumentos;
    }

    /**
     * @param envioDocumentos the envioDocumentos to set
     */
    public void setEnvioDocumentos(Date envioDocumentos) {
        this.envioDocumentos = envioDocumentos;
    }

    /**
     * @return the respostaDocumentos
     */
    public Date getRespostaDocumentos() {
        return respostaDocumentos;
    }

    /**
     * @param respostaDocumentos the respostaDocumentos to set
     */
    public void setRespostaDocumentos(Date respostaDocumentos) {
        this.respostaDocumentos = respostaDocumentos;
    }

    /**
     * @return the parecer
     */
    public String getParecer() {
        return parecer;
    }

    /**
     * @param parecer the parecer to set
     */
    public void setParecer(String parecer) {
        this.parecer = parecer;
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

} // end class

package p5_negocio.entidades;

import java.util.Date;

public class Processo {

    //dados do professor
    private int id;
    private String nome;
    private int nivelSrc;
    private String situacao;
    private Date inicioProcesso;
    private Date conclusaoProcesso;
    //avaliadores do professor
    private Avaliacao avaliacao1;
    private Avaliacao avaliacao2;
    private Avaliacao avaliacao3;
    // espaço para comentarios, informaçoes uteis que nao foram previstas neste momento
    private String comentarios;
    
    
    public Processo(int i, String n, int niv, String sit, Date ini, Date fin, Avaliacao av1, Avaliacao av2, Avaliacao av3, String com){
        this.setId(i);
        this.setNome(n);
        this.setNivelSrc(niv);
        this.setSituacao(sit);
        this.setInicioProcesso(ini);
        this.setConclusaoProcesso(fin);
        this.setAvaliacao1(av1);
        this.setAvaliacao2(av2);
        this.setAvaliacao3(av3);
        this.setComentarios(com);        
    }
    public Processo( String n, int niv, String sit, Date ini, Date fin, Avaliacao av1, Avaliacao av2, Avaliacao av3, String com){
        this.setNome(n);
        this.setNivelSrc(niv);
        this.setSituacao(sit);
        this.setInicioProcesso(ini);
        this.setConclusaoProcesso(fin);
        this.setAvaliacao1(av1);
        this.setAvaliacao2(av2);
        this.setAvaliacao3(av3);
        this.setComentarios(com);        
    }

    public Processo(int i, String n, int niv, String sit, Date ini, Date fin, String com){
        this.setId(i);
        this.setNome(n);
        this.setNivelSrc(niv);
        this.setSituacao(sit);
        this.setInicioProcesso(ini);
        this.setConclusaoProcesso(fin);
        this.setComentarios(com);        
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
     * @return the nivelSrc
     */
    public int getNivelSrc() {
        return nivelSrc;
    }

    /**
     * @param nivelSrc the nivelSrc to set
     */
    public void setNivelSrc(int nivelSrc) {
        this.nivelSrc = nivelSrc;
    }

    /**
     * @return the situacao
     */
    public String getSituacao() {
        return situacao;
    }

    /**
     * @param situacao the situacao to set
     */
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    /**
     * @return the inicioProcesso
     */
    public Date getInicioProcesso() {
        return inicioProcesso;
    }

    /**
     * @param inicioProcesso the inicioProcesso to set
     */
    public void setInicioProcesso(Date inicioProcesso) {
        this.inicioProcesso = inicioProcesso;
    }

    /**
     * @return the conclusaoProcesso
     */
    public Date getConclusaoProcesso() {
        return conclusaoProcesso;
    }

    /**
     * @param conclusaoProcesso the conclusaoProcesso to set
     */
    public void setConclusaoProcesso(Date conclusaoProcesso) {
        this.conclusaoProcesso = conclusaoProcesso;
    }

    /**
     * @return the avaliacao1
     */
    public Avaliacao getAvaliacao1() {
        return avaliacao1;
    }

    /**
     * @param avaliacao1 the avaliacao1 to set
     */
    public void setAvaliacao1(Avaliacao avaliacao1) {
        this.avaliacao1 = avaliacao1;
    }

    /**
     * @return the avaliacao2
     */
    public Avaliacao getAvaliacao2() {
        return avaliacao2;
    }

    /**
     * @param avaliacao2 the avaliacao2 to set
     */
    public void setAvaliacao2(Avaliacao avaliacao2) {
        this.avaliacao2 = avaliacao2;
    }

    /**
     * @return the avaliacao3
     */
    public Avaliacao getAvaliacao3() {
        return avaliacao3;
    }

    /**
     * @param avaliacao3 the avaliacao3 to set
     */
    public void setAvaliacao3(Avaliacao avaliacao3) {
        this.avaliacao3 = avaliacao3;
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
    
}

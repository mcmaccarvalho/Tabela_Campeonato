package com.ponto.ideal.solucoes.tabelacampeonato.model;

public class Ligas {


    private int    id          ;
    private String keyliga     ;
    private String nomeliga    ;
    private String admin       ;
    private Long   datacadliga ;
    private int    tipoliga    ;
    private int    statusliga  ;


    /*
    status
    status = 0 = ativa
    status = 1 = bloqueada para o usuario
    status = 10 = bloqueada total
    tipoliga:
    comum = 0
    compartilhada = 1
     */
    public Ligas(){

    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyliga() {
        return keyliga;
    }

    public void setKeyliga(String keyliga) {
        this.keyliga = keyliga;
    }

    public String getNomeliga() {
        return nomeliga;
    }

    public void setNomeliga(String nomeliga) {
        this.nomeliga = nomeliga;
    }

    public Long getDatacadliga() {
        return datacadliga;
    }

    public void setDatacadliga(Long datacadliga) {
        this.datacadliga = datacadliga;
    }

    public int getTipoliga() {
        return tipoliga;
    }

    public void setTipoliga(int tipoliga) {
        this.tipoliga = tipoliga;
    }

    public int getStatusliga() {
        return statusliga;
    }

    public void setStatusliga(int statusliga) {
        this.statusliga = statusliga;
    }

}

package com.ponto.ideal.solucoes.tabelacampeonato.model;

public class Jogador  {

    private int        id            ;
    private String     keyjog      ;
    private String     nomejog    ;
    private String     emailjog;
    private String     idliga;

    public Jogador(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyjog() {
        return keyjog;
    }

    public void setKeyjog(String keyjog) {
        this.keyjog = keyjog;
    }

    public String getNomejog() {
        return nomejog;
    }

    public void setNomejog(String nomejog) {
        this.nomejog = nomejog;
    }

    public String getEmailjog() {
        return emailjog;
    }

    public void setEmailjog(String emailjog) {
        this.emailjog = emailjog;
    }

    public String getIdliga() {
        return idliga;
    }

    public void setIdliga(String idliga) {
        this.idliga = idliga;
    }
}

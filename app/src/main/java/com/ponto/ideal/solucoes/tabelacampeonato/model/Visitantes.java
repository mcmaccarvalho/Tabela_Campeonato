package com.ponto.ideal.solucoes.tabelacampeonato.model;

import java.io.Serializable;

public class Visitantes implements Serializable {

    private int        id            ;
    private String     keyvisit      ;
    private String     nomevisit     ;
    private int        avatarvisit   ;


    public Visitantes(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyvisit() {
        return keyvisit;
    }

    public void setKeyvisit(String keyvisit) {
        this.keyvisit = keyvisit;
    }

    public String getNomevisit() {
        return nomevisit;
    }

    public void setNomevisit(String nomevisit) {
        this.nomevisit = nomevisit;
    }

    public int getAvatarvisit() {
        return avatarvisit;
    }

    public void setAvatarvisit(int avatarvisit) {
        this.avatarvisit = avatarvisit;
    }
}

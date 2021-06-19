package com.ponto.ideal.solucoes.tabelacampeonato.model;

import java.io.Serializable;

public class Imgavat implements Serializable {


    private int    id          ;
    private int    imgavatar  ;

    public Imgavat(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgavatar() {
        return imgavatar;
    }

    public void setImgavatar(int imgavatar) {
        this.imgavatar = imgavatar;
    }
}

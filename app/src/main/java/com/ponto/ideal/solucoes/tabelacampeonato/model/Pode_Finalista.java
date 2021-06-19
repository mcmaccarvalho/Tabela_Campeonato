package com.ponto.ideal.solucoes.tabelacampeonato.model;

public class Pode_Finalista {


    private String PodeId;
    private int PodeMaxPt;
    private int PodePT;
    private int PodeClass;

    public Pode_Finalista(){

    }

    public int getPodeClass() {
        return PodeClass;
    }

    public void setPodeClass(int podeClass) {
        PodeClass = podeClass;
    }

    public String getPodeId() {
        return PodeId;
    }

    public void setPodeId(String podeId) {
        PodeId = podeId;
    }

    public int getPodeMaxPt() {
        return PodeMaxPt;
    }

    public void setPodeMaxPt(int podeMaxPt) {
        PodeMaxPt = podeMaxPt;
    }

    public int getPodePT() {
        return PodePT;
    }

    public void setPodePT(int podePT) {
        PodePT = podePT;
    }
}


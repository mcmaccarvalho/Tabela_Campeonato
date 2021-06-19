package com.ponto.ideal.solucoes.tabelacampeonato.model;

public class Jogadores_Inscritos {

    private String nomeins;
    private String idins;
    private boolean isCkecked;
    private int position;

    public String getNomeins() {
        return nomeins;
    }

    public void setNomeins(String nomeins) {
        this.nomeins = nomeins;
    }

    public String getIdins() {
        return idins;
    }

    public void setIdins(String idins) {
        this.idins = idins;
    }

    public boolean isCkecked() {
        return isCkecked;
    }

    public void setCkecked(boolean ckecked) {
        isCkecked = ckecked;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

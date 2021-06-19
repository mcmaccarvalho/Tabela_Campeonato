package com.ponto.ideal.solucoes.tabelacampeonato.model;

public class Confrontos_Jogador {



    private boolean temrepet;
    private String confadversario;
    private int confosição;


    public Confrontos_Jogador(){

    }


    public String getConfadversario() {
        return confadversario;
    }

    public void setConfadversario(String confadversario) {
        this.confadversario = confadversario;
    }

    public int getConfosição() {
        return confosição;
    }

    public void setConfosição(int confosição) {
        this.confosição = confosição;
    }

    public boolean isTemrepet() {
        return temrepet;
    }

    public void setTemrepet(boolean temrepet) {
        this.temrepet = temrepet;
    }
}

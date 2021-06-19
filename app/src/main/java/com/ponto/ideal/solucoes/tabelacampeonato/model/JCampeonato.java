package com.ponto.ideal.solucoes.tabelacampeonato.model;

public class JCampeonato {

    private int    id;
    private String idcampeonato;
    private String idliga;
    private int    numpartcamp;
    private int    tipoturnocamp;
    private int    statuscamp;
    private int    numerocamp;
    private long   timestamp;
    private String participantesCamp;

    public JCampeonato(){

    }

    public String getParticipantesCamp() {
        return participantesCamp;
    }

    public void setParticipantesCamp(String participantesCamp) {
        this.participantesCamp = participantesCamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdcampeonato() {
        return idcampeonato;
    }

    public void setIdcampeonato(String idcampeonato) {
        this.idcampeonato = idcampeonato;
    }

    public String getIdliga() {
        return idliga;
    }

    public void setIdliga(String idliga) {
        this.idliga = idliga;
    }

    public int getNumpartcamp() {
        return numpartcamp;
    }

    public void setNumpartcamp(int numpartcamp) {
        this.numpartcamp = numpartcamp;
    }

    public int getTipoturnocamp() {
        return tipoturnocamp;
    }

    public void setTipoturnocamp(int tipoturnocamp) {
        this.tipoturnocamp = tipoturnocamp;
    }

    public int getStatuscamp() {
        return statuscamp;
    }

    public void setStatuscamp(int statuscamp) {
        this.statuscamp = statuscamp;
    }

    public int getNumerocamp() {
        return numerocamp;
    }

    public void setNumerocamp(int numerocamp) {
        this.numerocamp = numerocamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

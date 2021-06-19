package com.ponto.ideal.solucoes.tabelacampeonato.model;

public class Notificacoes {

    private String text;
    private String fromid;
    private String toid;
    private long timestamp;
    private String fromname;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getToid() {
        return toid;
    }

    public void setToid(String toid) {
        this.toid = toid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }
}

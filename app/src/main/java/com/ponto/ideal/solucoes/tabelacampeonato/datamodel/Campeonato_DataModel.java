package com.ponto.ideal.solucoes.tabelacampeonato.datamodel;

public class Campeonato_DataModel {

    private final static String TABELA = "campeonatos";

    private final static String   id           = "id";
    private final static String   idcampeonato = "idcampeonato";
    private final static String   idliga       = "idliga";
    private final static String   numpartcamp  = "numpartcamp";
    private final static String   tipoturnocamp  = "tipoturnocamp";
    private final static String   statuscamp   = "statuscamp";
    private final static String   numerocamp   = "numerocamp";
    private final static String timestamp = "timestamp";
    private final static String participantesCamp = "participantesCamp";

    private static String queryCriarTabela = "";

    public static String criarTabela() {

        setQueryCriarTabela("CREATE TABLE IF NOT EXISTS " + getTABELA());
        setQueryCriarTabela(getQueryCriarTabela() + "(");
        setQueryCriarTabela(getQueryCriarTabela() + getId() + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getIdcampeonato() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getIdliga() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getNumpartcamp() +  " INTEGER, ");
        setQueryCriarTabela(getQueryCriarTabela() + getTipoturnocamp() +  " INTEGER, ");
        setQueryCriarTabela(getQueryCriarTabela() + getStatuscamp() +  " INTEGER, ");
        setQueryCriarTabela(getQueryCriarTabela() + getNumerocamp() +  " INTEGER, ");
        setQueryCriarTabela(getQueryCriarTabela() + getTimestamp() + " REAL, ");
        setQueryCriarTabela(getQueryCriarTabela() + getParticipantesCamp() +  " TEXT ");

        setQueryCriarTabela(getQueryCriarTabela() + ")");

        return getQueryCriarTabela();
    }


    public static String getTABELA() {
        return TABELA;
    }

    public static String getId() {
        return id;
    }

    public static String getIdcampeonato() {
        return idcampeonato;
    }

    public static String getIdliga() {
        return idliga;
    }

    public static String getNumpartcamp() {
        return numpartcamp;
    }

    public static String getTipoturnocamp() {
        return tipoturnocamp;
    }

    public static String getStatuscamp() {
        return statuscamp;
    }

    public static String getNumerocamp() {
        return numerocamp;
    }

    public static String getTimestamp() {
        return timestamp;
    }

    public static String getParticipantesCamp() {
        return participantesCamp;
    }

    public static String getQueryCriarTabela() {
        return queryCriarTabela;
    }

    public static void setQueryCriarTabela(String queryCriarTabela) {
        Campeonato_DataModel.queryCriarTabela = queryCriarTabela;
    }
}

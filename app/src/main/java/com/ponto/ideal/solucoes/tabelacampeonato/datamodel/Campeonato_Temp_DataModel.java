package com.ponto.ideal.solucoes.tabelacampeonato.datamodel;

public class Campeonato_Temp_DataModel {

    private final static String TABELA = "campeonatos_temp";

    private final static String   id           = "id";
    private final static String   idcampeonato = "idcampeonato";
    private final static String   idliga       = "idliga";
    private final static String   numpartcamp  = "numpartcamp";
    private final static String   tipoturnocamp  = "tipoturnocamp";
    private final static String   jogoatualcamp  = "jogoatualcamp";
    private final static String   datacamp     = "datacamp";
    private final static String   horainicamp     = "horainicamp";
    private final static String   horafimcamp     = "horafimcamp";
    private final static String   tempocamp    = "tempocamp";
    private final static String   statuscamp   = "statuscamp";
    private final static String   numerocamp   = "numerocamp";


    private static String queryCriarTabela = "";

    public static String criarTabela() {

        setQueryCriarTabela("CREATE TABLE IF NOT EXISTS " + getTABELA());
        setQueryCriarTabela(getQueryCriarTabela() + "(");
        setQueryCriarTabela(getQueryCriarTabela() + getId() + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getIdcampeonato() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getIdliga() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getNumpartcamp() +  " INTEGER, ");
        setQueryCriarTabela(getQueryCriarTabela() + getTipoturnocamp() +  " INTEGER, ");
        setQueryCriarTabela(getQueryCriarTabela() + getJogoatualcamp() +  " INTEGER, ");
        setQueryCriarTabela(getQueryCriarTabela() + getDatacamp() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getHorainicamp() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getHorafimcamp() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getTempocamp() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getStatuscamp() +  " INTEGER, ");
        setQueryCriarTabela(getQueryCriarTabela() + getNumerocamp() +  " INTEGER ");

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

    public static String getJogoatualcamp() {
        return jogoatualcamp;
    }

    public static String getDatacamp() {
        return datacamp;
    }

    public static String getHorainicamp() {
        return horainicamp;
    }

    public static String getHorafimcamp() {
        return horafimcamp;
    }

    public static String getTempocamp() {
        return tempocamp;
    }

    public static String getStatuscamp() {
        return statuscamp;
    }

    public static String getNumerocamp() {
        return numerocamp;
    }

    public static String getQueryCriarTabela() {
        return queryCriarTabela;
    }

    public static void setQueryCriarTabela(String queryCriarTabela) {
        Campeonato_Temp_DataModel.queryCriarTabela = queryCriarTabela;
    }
}

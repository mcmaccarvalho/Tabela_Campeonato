package com.ponto.ideal.solucoes.tabelacampeonato.datamodel;

public class Jogos_Dataset_DataModel {

    private final static String TABELA = "dataset_camp";

    private final static String id             = "id";
    private final static String idfire         = "idfire";
    private final static String idjogo         = "idjogo";
    private final static String numjog         = "numjog";
    private final static String keycampeonato  = "keycampeonato";
    private final static String keyliga        = "keyliga";
    private final static String idjogador1     = "idjogador1";
    private final static String idjogador2     = "idjogador2";
    private final static String placar1        = "placar1";
    private final static String placar2        = "placar2";
    private final static String statusjogo     = "statusjogo";
    private final static String penalti1       = "penalti1";
    private final static String penalti2       = "penalti2";
    private final static String turno          = "turno";



    private static String queryCriarTabela = "";

    public static String criarTabela() {

        setQueryCriarTabela("CREATE TABLE IF NOT EXISTS " + getTABELA());
        setQueryCriarTabela(getQueryCriarTabela() + "(");
        setQueryCriarTabela(getQueryCriarTabela() + getId() +  " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getIdfire() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getIdjogo() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getNumjog() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getKeycampeonato() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getKeyliga() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getIdjogador1() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getIdjogador2() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getPlacar1() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getPlacar2() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getStatusjogo() +  " INTEGER,  ");
        setQueryCriarTabela(getQueryCriarTabela() + getPenalti1() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getPenalti2() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getTurno() +  " INTEGER  ");
        setQueryCriarTabela(getQueryCriarTabela() + ")");

        return getQueryCriarTabela();
    }


    public static String getTABELA() {
        return TABELA;
    }

    public static String getId() {
        return id;
    }

    public static String getIdfire() {
        return idfire;
    }

    public static String getTurno() {
        return turno;
    }

    public static String getNumjog() {
        return numjog;
    }

    public static String getKeycampeonato() {
        return keycampeonato;
    }

    public static String getKeyliga() {
        return keyliga;
    }

    public static String getIdjogador1() {
        return idjogador1;
    }

    public static String getIdjogador2() {
        return idjogador2;
    }

    public static String getPlacar1() {
        return placar1;
    }

    public static String getPlacar2() {
        return placar2;
    }

    public static String getStatusjogo() {
        return statusjogo;
    }

    public static String getPenalti1() {
        return penalti1;
    }

    public static String getPenalti2() {
        return penalti2;
    }

    public static String getIdjogo() {
        return idjogo;
    }

    public static String getQueryCriarTabela() {
        return queryCriarTabela;
    }

    public static void setQueryCriarTabela(String queryCriarTabela) {
        Jogos_Dataset_DataModel.queryCriarTabela = queryCriarTabela;
    }
}

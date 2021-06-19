package com.ponto.ideal.solucoes.tabelacampeonato.datamodel;

import android.util.Log;

public class Jogos_Dataset_Temp_DataModel {

    private final static String TABELA = "dataset_temp";

    private final static String id             = "id";
    private final static String ctrljog         = "ctrljog";
    private final static String numjog         = "numjog";
    private final static String keycampeonato  = "keycampeonato";
    private final static String keyliga        = "keyliga";
    private final static String idjogador1     = "idjogador1";
    private final static String idjogador2     = "idjogador2";
    private final static String nomejogador1   = "nomejogador1";
    private final static String nomejogador2   = "nomejogador2";
    private final static String imgjogador1    = "imgjogador1";
    private final static String imgjogador2    = "imgjogador2";
    private final static String placar1        = "placar1";
    private final static String placar2        = "placar2";
    private final static String ganhadorjogo   = "ganhadorjogo";
    private final static String perdedorjogo   = "perdedorjogo";
    private final static String jogadorvaijogo = "jogadorvaijogo";
    private final static String statusjogo     = "statusjogo";
    private final static String penalti1        = "penalti1";
    private final static String penalti2        = "penalti2";
    private final static String idjogo        = "idjogo";
    private static String queryCriarTabela = "";

    public static String criarTabela() {

        setQueryCriarTabela("CREATE TABLE IF NOT EXISTS " + getTABELA());
        setQueryCriarTabela(getQueryCriarTabela() + "(");
        setQueryCriarTabela(getQueryCriarTabela() + getId() +  " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getCtrljog() +  " INTEGER,  ");
        setQueryCriarTabela(getQueryCriarTabela() + getNumjog() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getKeycampeonato() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getKeyliga() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getIdjogador1() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getIdjogador2() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getNomejogador1() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getNomejogador2() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getImgjogador1() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getImgjogador2() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getPlacar1() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getPlacar2() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getGanhadorjogo() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getPerdedorjogo() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getJogadorvaijogo() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getStatusjogo() +  " INTEGER,  ");
        setQueryCriarTabela(getQueryCriarTabela() + getPenalti1() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getPenalti2() +  " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getIdjogo() +  " TEXT ");
        setQueryCriarTabela(getQueryCriarTabela() + ")");

        Log.i("criartabela",": " + getQueryCriarTabela());

        return getQueryCriarTabela();
    }


    public static String getTABELA() {
        return TABELA;
    }

    public static String getId() {
        return id;
    }

    public static String getCtrljog() {
        return ctrljog;
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

    public static String getNomejogador1() {
        return nomejogador1;
    }

    public static String getNomejogador2() {
        return nomejogador2;
    }

    public static String getImgjogador1() {
        return imgjogador1;
    }

    public static String getImgjogador2() {
        return imgjogador2;
    }

    public static String getPlacar1() {
        return placar1;
    }

    public static String getPlacar2() {
        return placar2;
    }

    public static String getGanhadorjogo() {
        return ganhadorjogo;
    }

    public static String getPerdedorjogo() {
        return perdedorjogo;
    }

    public static String getJogadorvaijogo() {
        return jogadorvaijogo;
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
        Jogos_Dataset_Temp_DataModel.queryCriarTabela = queryCriarTabela;
    }
}

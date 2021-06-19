package com.ponto.ideal.solucoes.tabelacampeonato.datamodel;

import android.util.Log;

public class    Ligas_DataModel {

    private final static String TABELA = "ligas";

    private final static String        id                =      "id";
    private final static String        keyliga           =      "keyliga";
    private final static String        nomeliga          =      "nomeliga";
    private final static String        admin          =      "admin";
    private final static String        datacadliga       =      "datacadliga";
    private final static String        tipoliga        =      "tipoliga";
    private final static String        statusliga        =      "statusliga";

    private static String queryCriarTabela = "";

    public static String criarTabela() {

        queryCriarTabela = "CREATE TABLE IF NOT EXISTS " + TABELA;
        queryCriarTabela += "(";
        queryCriarTabela += id + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        queryCriarTabela += keyliga + " TEXT, ";
        queryCriarTabela += nomeliga + " TEXT, ";
        queryCriarTabela += admin + " TEXT, ";
        queryCriarTabela += datacadliga + " REAL, ";
        queryCriarTabela += tipoliga + " INTEGER, ";
        queryCriarTabela += statusliga + " INTEGER ";

        queryCriarTabela += ")";

        Log.i("criartabela",": " + queryCriarTabela);
        return queryCriarTabela;
    }

    public static String getTABELA() {
        return TABELA;
    }

    public static String getId() {
        return id;
    }

    public static String getKeyliga() {
        return keyliga;
    }

    public static String getNomeliga() {
        return nomeliga;
    }

    public static String getAdmin() {
        return admin;
    }

    public static String getDatacadliga() {
        return datacadliga;
    }

    public static String getTipoliga() {
        return tipoliga;
    }

    public static String getStatusliga() {
        return statusliga;
    }

    public static String getQueryCriarTabela() {
        return queryCriarTabela;
    }

    public static void setQueryCriarTabela(String queryCriarTabela) {
        Ligas_DataModel.queryCriarTabela = queryCriarTabela;
    }
}

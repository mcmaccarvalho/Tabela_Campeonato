package com.ponto.ideal.solucoes.tabelacampeonato.datamodel;

public class Jogador_DataModel {

    private final static String TABELA = "jogadores";

    private final static String        id              =      "id";
    private final static String        keyjog          =      "keyjog";
    private final static String        nomejog         =      "nomejog";
    private final static String        emailjog        =      "emailjog";
    private final static String        idliga       =         "idliga";

    private static String queryCriarTabela = "";

    public static String criarTabela() {

        queryCriarTabela = "CREATE TABLE IF NOT EXISTS " + TABELA;
        queryCriarTabela += "(";
        queryCriarTabela += id + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        queryCriarTabela += keyjog    +  " TEXT, ";
        queryCriarTabela += nomejog   +  " TEXT, ";
        queryCriarTabela += emailjog  +  " TEXT, ";
        queryCriarTabela += idliga    +  " TEXT  ";
        queryCriarTabela += ")";

        return queryCriarTabela;
    }

    public static String getTABELA() {
        return TABELA;
    }

    public static String getId() {
        return id;
    }

    public static String getKeyjog() {
        return keyjog;
    }

    public static String getNomejog() {
        return nomejog;
    }

    public static String getEmailjog() {
        return emailjog;
    }

    public static String getIdliga() {
        return idliga;
    }

    public static String getQueryCriarTabela() {
        return queryCriarTabela;
    }

    public static void setQueryCriarTabela(String queryCriarTabela) {
        Jogador_DataModel.queryCriarTabela = queryCriarTabela;
    }
}

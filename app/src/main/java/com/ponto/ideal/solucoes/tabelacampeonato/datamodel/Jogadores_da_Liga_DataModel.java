package com.ponto.ideal.solucoes.tabelacampeonato.datamodel;

public class Jogadores_da_Liga_DataModel {

    private final static String TABELA = "jogadores_liga";

    private final static String   id            =  "id";
    private final static String   idjogador     =  "idjogador";
    private final static String   nomejogador   =  "nomejogador";
    private final static String   emailjogador  =  "emailjogador";
    private final static String   tipojogador   =  "tipojogador";
    private final static String   idliga        =  "idliga";
    private final static String   qtdecamp      =  "qtdecamp";
    private final static String   jogos         =  "jogos";
    private final static String   vitorias      =  "vitorias";
    private final static String   derrotas      =  "derrotas";
    private final static String   empates       =  "empates";
    private final static String   lugar_1       =  "lugar_1";
    private final static String   lugar_2       =  "lugar_2";
    private final static String   lugar_3       =  "lugar_3";
    private final static String   lugar_4       =  "lugar_4";
    private final static String   lugar_5       =  "lugar_5";
    private final static String   golspro       =  "golspro";
    private final static String   golscontra    =  "golscontra";
    private final static String   permissao     =  "permissao";


    private static String queryCriarTabela = "";

    public static String criarTabela() {

        queryCriarTabela = "CREATE TABLE IF NOT EXISTS " + TABELA;
        queryCriarTabela += "(";
        queryCriarTabela += id + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        queryCriarTabela += idjogador      + " TEXT, ";
        queryCriarTabela += nomejogador    + " TEXT, ";
        queryCriarTabela += emailjogador   + " TEXT, ";
        queryCriarTabela += tipojogador    + " TEXT, ";
        queryCriarTabela += idliga         + " TEXT, ";
        queryCriarTabela += qtdecamp       + " INTEGER, ";
        queryCriarTabela += jogos          + " INTEGER, ";
        queryCriarTabela += vitorias       + " INTEGER, ";
        queryCriarTabela += derrotas       + " INTEGER, ";
        queryCriarTabela += empates        + " INTEGER, ";
        queryCriarTabela += lugar_1        + " INTEGER, ";
        queryCriarTabela += lugar_2        + " INTEGER, ";
        queryCriarTabela += lugar_3        + " INTEGER, ";
        queryCriarTabela += lugar_4        + " INTEGER, ";
        queryCriarTabela += lugar_5        + " INTEGER, ";
        queryCriarTabela += golspro        + " INTEGER, ";
        queryCriarTabela += golscontra     + " INTEGER, ";
        queryCriarTabela += permissao      + " INTEGER ";
        queryCriarTabela += ")";

        return getQueryCriarTabela();
    }

    public static String getTABELA() {
        return TABELA;
    }

    public static String getId() {
        return id;
    }

    public static String getIdjogador() {
        return idjogador;
    }

    public static String getNomejogador() {
        return nomejogador;
    }

    public static String getEmailjogador() {
        return emailjogador;
    }

    public static String getTipojogador() {
        return tipojogador;
    }

    public static String getIdliga() {
        return idliga;
    }

    public static String getQtdecamp() {
        return qtdecamp;
    }

    public static String getJogos() {
        return jogos;
    }

    public static String getVitorias() {
        return vitorias;
    }

    public static String getDerrotas() {
        return derrotas;
    }

    public static String getEmpates() {
        return empates;
    }

    public static String getLugar_1() {
        return lugar_1;
    }

    public static String getLugar_2() {
        return lugar_2;
    }

    public static String getLugar_3() {
        return lugar_3;
    }

    public static String getLugar_4() {
        return lugar_4;
    }

    public static String getLugar_5() {
        return lugar_5;
    }

    public static String getGolspro() {
        return golspro;
    }

    public static String getGolscontra() {
        return golscontra;
    }

    public static String getPermissao() {
        return permissao;
    }

    public static String getQueryCriarTabela() {
        return queryCriarTabela;
    }

    public static void setQueryCriarTabela(String queryCriarTabela) {
        Jogadores_da_Liga_DataModel.queryCriarTabela = queryCriarTabela;
    }
}

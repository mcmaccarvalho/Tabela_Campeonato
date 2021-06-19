package com.ponto.ideal.solucoes.tabelacampeonato.datamodel;

public class Resultados_Temp_DataModel {

    private final static String TABELA = "resultados_temp";

    private final static String   id           = "id";
    private final static String   idjogador    = "idjogador";
    private final static String   nomejogador  = "nomejogador";
    private final static String   imgjogador   = "imgjogador";
    private final static String   pontos       = "pontos";
    private final static String   saldogol     = "saldogol";
    private final static String   golpro       = "golpro";
    private final static String   golcontra    = "golcontra";
    private final static String   jogos        = "jogos";
    private final static String   vitorias     = "vitorias";
    private final static String   derrotas     = "derrotas";
    private final static String   empates      = "empates";
    private final static String   maxpontos    = "maxpontos";
    private final static String   idcampeonato = "idcampeonato";
    private final static String   idliga       = "idliga";
    private final static String   status       = "status";



    private static String queryCriarTabela = "";

    public static String criarTabela() {

        queryCriarTabela = "CREATE TABLE IF NOT EXISTS " + TABELA;
        queryCriarTabela += "(";
        queryCriarTabela += id + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        queryCriarTabela += idjogador        +  " TEXT, ";
        queryCriarTabela += nomejogador      +  " TEXT, ";
        queryCriarTabela += imgjogador       +  " TEXT,  ";
        queryCriarTabela += pontos           +  " INTEGER,  ";
        queryCriarTabela += saldogol         +  " INTEGER, ";
        queryCriarTabela += golpro           +  " INTEGER, ";
        queryCriarTabela += golcontra        +  " INTEGER, ";
        queryCriarTabela += jogos            +  " INTEGER,  ";
        queryCriarTabela += vitorias         +  " INTEGER,  ";
        queryCriarTabela += derrotas         +  " INTEGER, ";
        queryCriarTabela += empates          +  " INTEGER, ";
        queryCriarTabela += maxpontos        +  " INTEGER, ";
        queryCriarTabela += idcampeonato     +  " TEXT,  ";
        queryCriarTabela += idliga           +  " TEXT,  ";
        queryCriarTabela += status           +  " INTEGER ";
        queryCriarTabela += ")";

        return queryCriarTabela;
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

    public static String getImgjogador() {
        return imgjogador;
    }

    public static String getPontos() {
        return pontos;
    }

    public static String getSaldogol() {
        return saldogol;
    }

    public static String getGolpro() {
        return golpro;
    }

    public static String getGolcontra() {
        return golcontra;
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

    public static String getMaxpontos() {
        return maxpontos;
    }

    public static String getIdcampeonato() {
        return idcampeonato;
    }

    public static String getIdliga() {
        return idliga;
    }

    public static String getStatus() {
        return status;
    }

    public static String getQueryCriarTabela() {
        return queryCriarTabela;
    }

    public static void setQueryCriarTabela(String queryCriarTabela) {
        Resultados_Temp_DataModel.queryCriarTabela = queryCriarTabela;
    }
}

package com.ponto.ideal.solucoes.tabelacampeonato.model;

public class Participantes  {


    private int    id;
    private int    idimg;
    private String id_jogador  ;
    private String nome_jogador;
    private String id_liga  ;
    private String id_campeonato  ;
    private int    jogos;
    private int    vitorias;
    private int    derrotas;
    private int    empates;
    private int    class_jogador;
    private int    golspro;
    private int    golscontra;
    private int    saldogols;
    private int    pontos;

    public Participantes(){

    }

    public int getSaldogols() {
        return saldogols;
    }

    public void setSaldogols(int saldogols) {
        this.saldogols = saldogols;
    }

    public int getIdimg() {
        return idimg;
    }

    public void setIdimg(int idimg) {
        this.idimg = idimg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_jogador() {
        return id_jogador;
    }

    public void setId_jogador(String id_jogador) {
        this.id_jogador = id_jogador;
    }

    public String getNome_jogador() {
        return nome_jogador;
    }

    public void setNome_jogador(String nome_jogador) {
        this.nome_jogador = nome_jogador;
    }

    public String getId_liga() {
        return id_liga;
    }

    public void setId_liga(String id_liga) {
        this.id_liga = id_liga;
    }

    public String getId_campeonato() {
        return id_campeonato;
    }

    public void setId_campeonato(String id_campeonato) {
        this.id_campeonato = id_campeonato;
    }

    public int getJogos() {
        return jogos;
    }

    public void setJogos(int jogos) {
        this.jogos = jogos;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public int getEmpates() {
        return empates;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }

    public int getClass_jogador() {
        return class_jogador;
    }

    public void setClass_jogador(int class_jogador) {
        this.class_jogador = class_jogador;
    }

    public int getGolspro() {
        return golspro;
    }

    public void setGolspro(int golspro) {
        this.golspro = golspro;
    }

    public int getGolscontra() {
        return golscontra;
    }

    public void setGolscontra(int golscontra) {
        this.golscontra = golscontra;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}

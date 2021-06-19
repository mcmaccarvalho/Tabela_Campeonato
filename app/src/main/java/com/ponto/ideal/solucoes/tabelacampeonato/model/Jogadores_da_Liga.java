package com.ponto.ideal.solucoes.tabelacampeonato.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Jogadores_da_Liga implements Parcelable {

    private int    id;
    private String idjogador;
    private String nomejogador;
    private String emailjogador;
    private String tipojogador;
    private String imgjogador;
    private int    tipojog;
    private String idliga;
    private int    qtdecamp;
    private int    jogos;
    private int    vitorias;
    private int    derrotas;
    private int    empates;
    private int    lugar_1;
    private int    lugar_2;
    private int    lugar_3;
    private int    lugar_4;
    private int    lugar_5;
    private int    golspro;
    private int    golscontra;
    private int    permissao;



    /*
    tipojogador
    tipojogador = local = 0
    tipojogador = Gold = 10
    tipojogador = Player = 1

    permissao = 0: "Boqueado";break;
    permissao =1:  "Assistir";break;
    permissao =2:  "Inserir/Alterar resultados";br
    permissao =3:  "Iniciar/Encerrar campeonatos";
    permissao =10: "Admnistrador";break;
    permissao =100:"Visitante";break;
     */


    public Jogadores_da_Liga(){

    }

    protected Jogadores_da_Liga(Parcel in) {
        id = in.readInt();
        idjogador = in.readString();
        nomejogador = in.readString();
        emailjogador = in.readString();
        tipojogador = in.readString();
        imgjogador = in.readString();
        tipojog = in.readInt();
        idliga = in.readString();
        qtdecamp = in.readInt();
        jogos = in.readInt();
        vitorias = in.readInt();
        derrotas = in.readInt();
        empates = in.readInt();
        lugar_1 = in.readInt();
        lugar_2 = in.readInt();
        lugar_3 = in.readInt();
        lugar_4 = in.readInt();
        lugar_5 = in.readInt();
        golspro = in.readInt();
        golscontra = in.readInt();
        permissao = in.readInt();
    }

    public static final Creator<Jogadores_da_Liga> CREATOR = new Creator<Jogadores_da_Liga>() {
        @Override
        public Jogadores_da_Liga createFromParcel(Parcel in) {
            return new Jogadores_da_Liga(in);
        }

        @Override
        public Jogadores_da_Liga[] newArray(int size) {
            return new Jogadores_da_Liga[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdjogador() {
        return idjogador;
    }

    public void setIdjogador(String idjogador) {
        this.idjogador = idjogador;
    }

    public String getNomejogador() {
        return nomejogador;
    }

    public void setNomejogador(String nomejogador) {
        this.nomejogador = nomejogador;
    }

    public String getEmailjogador() {
        return emailjogador;
    }

    public void setEmailjogador(String emailjogador) {
        this.emailjogador = emailjogador;
    }

    public String getTipojogador() {
        return tipojogador;
    }

    public void setTipojogador(String tipojogador) {
        this.tipojogador = tipojogador;
    }

    public String getImgjogador() {
        return imgjogador;
    }

    public void setImgjogador(String imgjogador) {
        this.imgjogador = imgjogador;
    }

    public int getTipojog() {
        return tipojog;
    }

    public void setTipojog(int tipojog) {
        this.tipojog = tipojog;
    }

    public String getIdliga() {
        return idliga;
    }

    public void setIdliga(String idliga) {
        this.idliga = idliga;
    }

    public int getQtdecamp() {
        return qtdecamp;
    }

    public void setQtdecamp(int qtdecamp) {
        this.qtdecamp = qtdecamp;
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

    public int getLugar_1() {
        return lugar_1;
    }

    public void setLugar_1(int lugar_1) {
        this.lugar_1 = lugar_1;
    }

    public int getLugar_2() {
        return lugar_2;
    }

    public void setLugar_2(int lugar_2) {
        this.lugar_2 = lugar_2;
    }

    public int getLugar_3() {
        return lugar_3;
    }

    public void setLugar_3(int lugar_3) {
        this.lugar_3 = lugar_3;
    }

    public int getLugar_4() {
        return lugar_4;
    }

    public void setLugar_4(int lugar_4) {
        this.lugar_4 = lugar_4;
    }

    public int getLugar_5() {
        return lugar_5;
    }

    public void setLugar_5(int lugar_5) {
        this.lugar_5 = lugar_5;
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

    public int getPermissao() {
        return permissao;
    }

    public void setPermissao(int permissao) {
        this.permissao = permissao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(idjogador);
        dest.writeString(nomejogador);
        dest.writeString(emailjogador);
        dest.writeString(tipojogador);
        dest.writeString(imgjogador);
        dest.writeInt(tipojog);
        dest.writeString(idliga);
        dest.writeInt(qtdecamp);
        dest.writeInt(jogos);
        dest.writeInt(vitorias);
        dest.writeInt(derrotas);
        dest.writeInt(empates);
        dest.writeInt(lugar_1);
        dest.writeInt(lugar_2);
        dest.writeInt(lugar_3);
        dest.writeInt(lugar_4);
        dest.writeInt(lugar_5);
        dest.writeInt(golspro);
        dest.writeInt(golscontra);
        dest.writeInt(permissao);
    }
}

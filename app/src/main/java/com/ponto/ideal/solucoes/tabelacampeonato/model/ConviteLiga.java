package com.ponto.ideal.solucoes.tabelacampeonato.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ConviteLiga implements Parcelable {

    private String idconvite;
    private String idliga;
    private String nomeliga;
    private String nomefrom;
    private String idfrom;
    private String nometo;
    private String idto;
    private int status;
    private long timestamp;
    private int permissao;

    /*
    Status para quem manda
    0 = enviado
    1 = recebido
    2 = pendente
    3 = aceito
    4 = recusado

      Status para quem recebe
    0 = pendente
    1 = pendente
    2 = pendente
    3 = aceito
    4 = recusado
    */

    public ConviteLiga(Parcel in) {
        idconvite = in.readString();
        idliga = in.readString();
        nomeliga = in.readString();
        nomefrom = in.readString();
        idfrom = in.readString();
        nometo = in.readString();
        idto = in.readString();
        status = in.readInt();
        timestamp = in.readLong();
        permissao = in.readInt();
    }

    public static final Creator<ConviteLiga> CREATOR = new Creator<ConviteLiga>() {
        @Override
        public ConviteLiga createFromParcel(Parcel in) {
            return new ConviteLiga(in);
        }

        @Override
        public ConviteLiga[] newArray(int size) {
            return new ConviteLiga[size];
        }
    };

    public ConviteLiga() {

    }

    public int getPermissao() {
        return permissao;
    }

    public void setPermissao(int permissao) {
        this.permissao = permissao;
    }

    public String getIdconvite() {
        return idconvite;
    }

    public void setIdconvite(String idconvite) {
        this.idconvite = idconvite;
    }

    public String getIdliga() {
        return idliga;
    }

    public void setIdliga(String idliga) {
        this.idliga = idliga;
    }

    public String getNomeliga() {
        return nomeliga;
    }

    public void setNomeliga(String nomeliga) {
        this.nomeliga = nomeliga;
    }

    public String getNomefrom() {
        return nomefrom;
    }

    public void setNomefrom(String nomefrom) {
        this.nomefrom = nomefrom;
    }

    public String getIdfrom() {
        return idfrom;
    }

    public void setIdfrom(String idfrom) {
        this.idfrom = idfrom;
    }

    public String getNometo() {
        return nometo;
    }

    public void setNometo(String nometo) {
        this.nometo = nometo;
    }

    public String getIdto() {
        return idto;
    }

    public void setIdto(String idto) {
        this.idto = idto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idconvite);
        dest.writeString(idliga);
        dest.writeString(nomeliga);
        dest.writeString(nomefrom);
        dest.writeString(idfrom);
        dest.writeString(nometo);
        dest.writeString(idto);
        dest.writeInt(status);
        dest.writeLong(timestamp);
        dest.writeInt(permissao);
    }
}

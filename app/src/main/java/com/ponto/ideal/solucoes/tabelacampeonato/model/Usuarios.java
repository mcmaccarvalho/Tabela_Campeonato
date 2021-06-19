package com.ponto.ideal.solucoes.tabelacampeonato.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuarios implements Parcelable {

    private int id;
    private String keyusu;
    private String nomeusu;
    private String apelidousu;
    private String emailusu;
    private String imagemusuario;
    private long timestamp;
    private int status;
    private String token;
    private int online;
    private String permicao;

    /*
    Bloqueado = status 0
    Player  cadastrado com email = status = 1
    Prime assinante = status 2
     */

    public Usuarios(){

    }

    protected Usuarios(Parcel in) {
        id = in.readInt();
        keyusu = in.readString();
        nomeusu = in.readString();
        apelidousu = in.readString();
        emailusu = in.readString();
        imagemusuario = in.readString();
        timestamp = in.readLong();
        status = in.readInt();
        token = in.readString();
        online = in.readInt();
        permicao = in.readString();
    }

    public static final Creator<Usuarios> CREATOR = new Creator<Usuarios>() {
        @Override
        public Usuarios createFromParcel(Parcel in) {
            return new Usuarios(in);
        }

        @Override
        public Usuarios[] newArray(int size) {
            return new Usuarios[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyusu() {
        return keyusu;
    }

    public void setKeyusu(String keyusu) {
        this.keyusu = keyusu;
    }

    public String getNomeusu() {
        return nomeusu;
    }

    public void setNomeusu(String nomeusu) {
        this.nomeusu = nomeusu;
    }

    public String getApelidousu() {
        return apelidousu;
    }

    public void setApelidousu(String apelidousu) {
        this.apelidousu = apelidousu;
    }

    public String getEmailusu() {
        return emailusu;
    }

    public void setEmailusu(String emailusu) {
        this.emailusu = emailusu;
    }

    public String getImagemusuario() {
        return imagemusuario;
    }

    public void setImagemusuario(String imagemusuario) {
        this.imagemusuario = imagemusuario;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getPermicao() {
        return permicao;
    }

    public void setPermicao(String permicao) {
        this.permicao = permicao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(keyusu);
        dest.writeString(nomeusu);
        dest.writeString(apelidousu);
        dest.writeString(emailusu);
        dest.writeString(imagemusuario);
        dest.writeLong(timestamp);
        dest.writeInt(status);
        dest.writeString(token);
        dest.writeInt(online);
        dest.writeString(permicao);
    }
}
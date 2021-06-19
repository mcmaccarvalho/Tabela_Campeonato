package com.ponto.ideal.solucoes.tabelacampeonato.controller;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Campeonato_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datasource.DataSource;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JCampeonato;

import java.util.ArrayList;

public class Campeonatos_Controller extends DataSource {

    ContentValues dados;

    public Campeonatos_Controller(@Nullable Context context) {
        super(context);
    }


    //**************** campeonato fixo  ***********************//

    public boolean salvarCampeonatofixo(JCampeonato obj){

        boolean sucesso = true;

        dados = new ContentValues();
        dados.put(Campeonato_DataModel.getIdcampeonato  (),obj.getIdcampeonato ());
        dados.put(Campeonato_DataModel.getIdliga        (),obj.getIdliga       ());
        dados.put(Campeonato_DataModel.getNumpartcamp   (),obj.getNumpartcamp  ());
        dados.put(Campeonato_DataModel.getTipoturnocamp (),obj.getTipoturnocamp());
        dados.put(Campeonato_DataModel.getStatuscamp    (),obj.getStatuscamp   ());
        dados.put(Campeonato_DataModel.getNumerocamp    (),obj.getNumerocamp   ());
        dados.put(Campeonato_DataModel.getTimestamp    (),obj.getTimestamp   ());
        dados.put(Campeonato_DataModel.getParticipantesCamp    (),obj.getParticipantesCamp   ());
        sucesso = insert(Campeonato_DataModel.getTABELA(), dados);

        return sucesso;

    }

    public ArrayList<JCampeonato> listarCampeonatosLiga(String idliga){
        return getAllCampeonatosLiga(idliga);
    }

    public boolean updateCampTemp(JCampeonato obj, String idliga, String keycamp) {

        boolean sucesso = true;

        dados = new ContentValues();
        dados.put(Campeonato_DataModel.getIdcampeonato  (),obj.getIdcampeonato ());
        dados.put(Campeonato_DataModel.getIdliga        (),obj.getIdliga       ());
        dados.put(Campeonato_DataModel.getNumpartcamp   (),obj.getNumpartcamp  ());
        dados.put(Campeonato_DataModel.getTipoturnocamp (),obj.getTipoturnocamp());
        dados.put(Campeonato_DataModel.getStatuscamp    (),obj.getStatuscamp   ());
        dados.put(Campeonato_DataModel.getNumerocamp    (),obj.getNumerocamp   ());
        dados.put(Campeonato_DataModel.getTimestamp    (),obj.getTimestamp   ());
        dados.put(Campeonato_DataModel.getParticipantesCamp    (),obj.getParticipantesCamp   ());

        sucesso = updateCampTemp(Campeonato_DataModel.getTABELA(), idliga,keycamp, dados);

        return sucesso;
    }

    public JCampeonato buscarCampeonatoAtivo(String liga){

        JCampeonato tem = getCampeonatoAtivo(liga);

        return tem;
    }











//    public ArrayList<Campeonato> listarCampeonatosfixoLiga(String idliga){
//        return getAllCampeonatosfixoLiga(idliga);
//    }
//    public boolean verificaCampTempLiga(String liga){
//
//        boolean tem= true;
//
//        tem = getVerificaTemp(liga);
//
//        return tem;
//    }
//    public boolean salvarCampeonatoTemp(Campeonato obj){
//
//        boolean sucesso = true;
//
//        dados = new ContentValues();
//        dados.put(Campeonato_DataModel.getIdcampeonato  (),obj.getIdcampeonato ());
//        dados.put(Campeonato_DataModel.getIdliga        (),obj.getIdliga       ());
//        dados.put(Campeonato_DataModel.getNumpartcamp   (),obj.getNumpartcamp  ());
//        dados.put(Campeonato_DataModel.getTipoturnocamp (),obj.getTipoturnocamp());
//        dados.put(Campeonato_DataModel.getStatuscamp    (),obj.getStatuscamp   ());
//        dados.put(Campeonato_DataModel.getNumerocamp    (),obj.getNumerocamp   ());
//        dados.put(Campeonato_DataModel.getTimestamp    (),obj.getTimestamp   ());
//        sucesso = insert(Campeonato_Temp_DataModel.getTABELA(), dados);
//
//        return sucesso;
//
//    }
//    public Campeonato pegarCampeonatosTempLiga(String idliga){
//        return getCampeonatoTempLiga(idliga);
//    }




}



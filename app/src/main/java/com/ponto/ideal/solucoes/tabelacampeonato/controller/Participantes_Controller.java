package com.ponto.ideal.solucoes.tabelacampeonato.controller;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Participantes_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datasource.DataSource;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Participantes;

public class Participantes_Controller extends DataSource {

    ContentValues dados;

    public Participantes_Controller(@Nullable Context context) {
        super(context);
    }



    //********************  participantes fixo *********************//

    public boolean salvarParticipantesCamp(Participantes obj){

        boolean sucesso = true;

        dados = new ContentValues();
        dados.put(Participantes_DataModel.getId_jogador       (),obj.getId_jogador      ());
        dados.put(Participantes_DataModel.getNome_jogador     (),obj.getNome_jogador    ());
        dados.put(Participantes_DataModel.getId_liga          (),obj.getId_liga         ());
        dados.put(Participantes_DataModel.getId_campeonato    (),obj.getId_campeonato   ());
        dados.put(Participantes_DataModel.getJogos            (),obj.getJogos           ());
        dados.put(Participantes_DataModel.getVitorias         (),obj.getVitorias        ());
        dados.put(Participantes_DataModel.getDerrotas         (),obj.getDerrotas        ());
        dados.put(Participantes_DataModel.getEmpates          (),obj.getEmpates         ());
        dados.put(Participantes_DataModel.getClass_jogador    (),obj.getClass_jogador   ());
        dados.put(Participantes_DataModel.getGolspro          (),obj.getGolspro         ());
        dados.put(Participantes_DataModel.getGolscontra       (),obj.getGolscontra      ());
        dados.put(Participantes_DataModel.getPontos           (),obj.getPontos          ());


        sucesso = insert(Participantes_DataModel.getTABELA(), dados);

        return sucesso;

    }

//    public ArrayList<Participantes> listarParticipantesCamp(String idcamp){
//        return getAllParticipantesCamp(idcamp);
//    }



    //********************  participantes temp *********************//

//    public boolean salvarParticipantesTemp(Participantes obj){
//
//        boolean sucesso = true;
//
//        dados = new ContentValues();
//        dados.put(Participantes_Temp_DataModel.getId_jogador       (),obj.getId_jogador      ());
//        dados.put(Participantes_Temp_DataModel.getNome_jogador     (),obj.getNome_jogador    ());
//        dados.put(Participantes_Temp_DataModel.getEmail_jogador    (),obj.getEmail_jogador   ());
//        dados.put(Participantes_Temp_DataModel.getImg_jogador      (),obj.getImg_jogador     ());
//        dados.put(Participantes_Temp_DataModel.getId_firejogador   (),obj.getId_firejogador  ());
//        dados.put(Participantes_Temp_DataModel.getId_liga          (),obj.getId_liga         ());
//        dados.put(Participantes_Temp_DataModel.getId_campeonato    (),obj.getId_campeonato   ());
//        dados.put(Participantes_Temp_DataModel.getJogos            (),obj.getJogos           ());
//        dados.put(Participantes_Temp_DataModel.getVitorias         (),obj.getVitorias        ());
//        dados.put(Participantes_Temp_DataModel.getDerrotas         (),obj.getDerrotas        ());
//        dados.put(Participantes_Temp_DataModel.getEmpates          (),obj.getEmpates         ());
//        dados.put(Participantes_Temp_DataModel.getClass_jogador    (),obj.getClass_jogador   ());
//        dados.put(Participantes_Temp_DataModel.getGolspro          (),obj.getGolspro         ());
//        dados.put(Participantes_Temp_DataModel.getGolscontra       (),obj.getGolscontra      ());
//        dados.put(Participantes_Temp_DataModel.getPontos           (),obj.getPontos          ());
//
//
//        sucesso = insert(Participantes_Temp_DataModel.getTABELA(), dados);
//
//        return sucesso;
//
//    }

//    public ArrayList<Participantes> listarParticipantesTemp(){
//        return getAllParticipantesTemp();
//    }
//
//    public ArrayList<Participantes> listarParticipantesTempLiga(String idliga){
//        return getAllParticipantesTempLiga(idliga);
//    }






}



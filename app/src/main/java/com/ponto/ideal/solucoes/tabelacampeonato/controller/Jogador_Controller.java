package com.ponto.ideal.solucoes.tabelacampeonato.controller;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Jogador_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datasource.DataSource;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogador;

import java.util.ArrayList;

public class Jogador_Controller extends DataSource {

    ContentValues dados;

    public Jogador_Controller(@Nullable Context context) {
        super(context);
    }

    public boolean salvarJogador(Jogador obj){

        boolean sucesso = true;

        dados = new ContentValues();
        dados.put(Jogador_DataModel.getKeyjog   (),obj.getKeyjog   ());
        dados.put(Jogador_DataModel.getNomejog  (),obj.getNomejog  ());
        dados.put(Jogador_DataModel.getEmailjog (),obj.getEmailjog ());
        dados.put(Jogador_DataModel.getIdliga   (),obj.getIdliga   ());

        sucesso = insert(Jogador_DataModel.getTABELA(), dados);

        return sucesso;

    }

    public ArrayList<Jogador> listarJogadores(){
        return getAllJogadores();
    }

    public ArrayList<Jogador> listarJogLiga(String idliga){
        return getAllJogLiga(idliga);
    }

    public Jogador jogsel(String idfirejog){
        return jogadorselecionado(idfirejog);
    }

}



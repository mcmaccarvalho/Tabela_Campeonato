package com.ponto.ideal.solucoes.tabelacampeonato.controller;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Jogos_Dataset_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datasource.DataSource;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JogosTeste;

import java.util.ArrayList;

public class Jogos_Dataset_Controller extends DataSource {

    ContentValues dados;

    public Jogos_Dataset_Controller(@Nullable Context context) {
        super(context);
    }


    //********************** dataset fixo  **********************//

    public boolean salvarDataset(JogosTeste obj){

        boolean sucesso = true;

        dados = new ContentValues();
        dados.put(Jogos_Dataset_DataModel.getIdfire         (),obj.getIdfire       ());
        dados.put(Jogos_Dataset_DataModel.getIdjogo         (),obj.getIdjogo       ());
        dados.put(Jogos_Dataset_DataModel.getNumjog         (),obj.getNumjog        ());
        dados.put(Jogos_Dataset_DataModel.getKeycampeonato  (),obj.getKeycampeonato ());
        dados.put(Jogos_Dataset_DataModel.getKeyliga        (),obj.getKeyliga       ());
        dados.put(Jogos_Dataset_DataModel.getIdjogador1     (),obj.getIdjogador1    ());
        dados.put(Jogos_Dataset_DataModel.getIdjogador2     (),obj.getIdjogador2    ());
        dados.put(Jogos_Dataset_DataModel.getPlacar1        (),obj.getPlacar1       ());
        dados.put(Jogos_Dataset_DataModel.getPlacar2        (),obj.getPlacar2       ());
        dados.put(Jogos_Dataset_DataModel.getStatusjogo     (),obj.getStatusjogo    ());
        dados.put(Jogos_Dataset_DataModel.getPenalti1       (),obj.getPenalti1      ());
        dados.put(Jogos_Dataset_DataModel.getPenalti2       (),obj.getPenalti2      ());
        dados.put(Jogos_Dataset_DataModel.getTurno          (),obj.getTurno        ());

        sucesso = insert(Jogos_Dataset_DataModel.getTABELA(), dados);

        return sucesso;

    }

    public ArrayList<JogosTeste> listarDatasetCamp(String idcamp,String idliga){
        return getDatasetCamp(idcamp, idliga);
    }

    public boolean updateJogo_Dataset(JogosTeste obj, String idbanco, String keycamp) {

        boolean sucesso = true;

        dados = new ContentValues();
        dados.put(Jogos_Dataset_DataModel.getIdfire         (),obj.getIdfire       ());
        dados.put(Jogos_Dataset_DataModel.getIdjogo         (),obj.getIdjogo       ());
        dados.put(Jogos_Dataset_DataModel.getNumjog         (),obj.getNumjog        ());
        dados.put(Jogos_Dataset_DataModel.getKeycampeonato  (),obj.getKeycampeonato ());
        dados.put(Jogos_Dataset_DataModel.getKeyliga        (),obj.getKeyliga       ());
        dados.put(Jogos_Dataset_DataModel.getIdjogador1     (),obj.getIdjogador1    ());
        dados.put(Jogos_Dataset_DataModel.getIdjogador2     (),obj.getIdjogador2    ());
        dados.put(Jogos_Dataset_DataModel.getPlacar1        (),obj.getPlacar1       ());
        dados.put(Jogos_Dataset_DataModel.getPlacar2        (),obj.getPlacar2       ());
        dados.put(Jogos_Dataset_DataModel.getStatusjogo     (),obj.getStatusjogo    ());
        dados.put(Jogos_Dataset_DataModel.getPenalti1       (),obj.getPenalti1      ());
        dados.put(Jogos_Dataset_DataModel.getPenalti2       (),obj.getPenalti2      ());
        dados.put(Jogos_Dataset_DataModel.getTurno          (),obj.getTurno        ());
        sucesso = update_Dataset(Jogos_Dataset_DataModel.getTABELA(), idbanco,keycamp, dados);

        return sucesso;
    }


}



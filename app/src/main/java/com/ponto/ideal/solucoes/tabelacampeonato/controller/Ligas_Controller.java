package com.ponto.ideal.solucoes.tabelacampeonato.controller;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Ligas_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datasource.DataSource;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Ligas;

import java.util.ArrayList;

public class Ligas_Controller extends DataSource {

    ContentValues dados;

    public Ligas_Controller(@Nullable Context context) {
        super(context);
    }

    public boolean salvarliga(Ligas obj){

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(Ligas_DataModel.getKeyliga(),obj.getKeyliga());
        dados.put(Ligas_DataModel.getNomeliga(),obj.getNomeliga());
        dados.put(Ligas_DataModel.getAdmin(),obj.getAdmin());
        dados.put(Ligas_DataModel.getDatacadliga(),obj.getDatacadliga());
        dados.put(Ligas_DataModel.getTipoliga(),obj.getTipoliga());
        dados.put(Ligas_DataModel.getStatusliga(),obj.getStatusliga());

        sucesso = insert(Ligas_DataModel.getTABELA(), dados);

        return sucesso;

    }

    public boolean deletar(Ligas obj){
        boolean sucesso = true;
        sucesso = deletar(Ligas_DataModel.getTABELA(), obj.getId());
        return sucesso;
    }

    public ArrayList<Ligas> listarligas(){
        return getAllLigas();
    }


    public Ligas getLiga(String idliga) {
        return buscaLiga(idliga);

    }


    public boolean updateLiga(int status, String keyliga){
        boolean sucesso = true;
        dados = new ContentValues();
        dados.put(Ligas_DataModel.getStatusliga(),status);
        sucesso = atualizaLigas(Ligas_DataModel.getTABELA(), keyliga, dados);

        return sucesso;
    }
}



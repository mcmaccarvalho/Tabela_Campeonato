package com.ponto.ideal.solucoes.tabelacampeonato.controller;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Jogadores_da_Liga_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datasource.DataSource;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_da_Liga;

import java.util.ArrayList;

public class Jogadores_da_Liga_Controller extends DataSource {

    ContentValues dados;

    public Jogadores_da_Liga_Controller(@Nullable Context context) {
        super(context);
    }

    public boolean salvar_Jogadores_da_Liga(Jogadores_da_Liga obj){

        boolean sucesso = true;

        dados = new ContentValues();
        dados.put(Jogadores_da_Liga_DataModel.getIdjogador        (),obj.getIdjogador      ());
        dados.put(Jogadores_da_Liga_DataModel.getNomejogador      (),obj.getNomejogador    ());
        dados.put(Jogadores_da_Liga_DataModel.getEmailjogador     (),obj.getEmailjogador   ());
        dados.put(Jogadores_da_Liga_DataModel.getTipojogador      (),obj.getTipojogador    ());
        dados.put(Jogadores_da_Liga_DataModel.getIdliga           (),obj.getIdliga         ());
        dados.put(Jogadores_da_Liga_DataModel.getQtdecamp         (),obj.getQtdecamp       ());
        dados.put(Jogadores_da_Liga_DataModel.getJogos            (),obj.getJogos          ());
        dados.put(Jogadores_da_Liga_DataModel.getVitorias         (),obj.getVitorias       ());
        dados.put(Jogadores_da_Liga_DataModel.getDerrotas         (),obj.getDerrotas       ());
        dados.put(Jogadores_da_Liga_DataModel.getEmpates          (),obj.getEmpates        ());
        dados.put(Jogadores_da_Liga_DataModel.getLugar_1          (),obj.getLugar_1        ());
        dados.put(Jogadores_da_Liga_DataModel.getLugar_2          (),obj.getLugar_2        ());
        dados.put(Jogadores_da_Liga_DataModel.getLugar_3          (),obj.getLugar_3        ());
        dados.put(Jogadores_da_Liga_DataModel.getLugar_4          (),obj.getLugar_4        ());
        dados.put(Jogadores_da_Liga_DataModel.getLugar_5          (),obj.getLugar_5        ());
        dados.put(Jogadores_da_Liga_DataModel.getGolspro          (),obj.getGolspro        ());
        dados.put(Jogadores_da_Liga_DataModel.getGolscontra       (),obj.getGolscontra     ());
        dados.put(Jogadores_da_Liga_DataModel.getPermissao        (),obj.getPermissao      ());

        sucesso = insert(Jogadores_da_Liga_DataModel.getTABELA(), dados);

        return sucesso;

    }

    public ArrayList<Jogadores_da_Liga> listar_Jogadore_da_Liga(String idliga){
        return getAll_Jogadores_da_Liga(idliga);
    }

    public ArrayList<Jogadores_da_Liga> todosjogadores(){
        return getAll_Jogadores();
    }


    public boolean atualizaCampPart(Jogadores_da_Liga obj, String idjog, String idliga) {
        boolean sucesso = true;
        dados = new ContentValues();
        dados.put(Jogadores_da_Liga_DataModel.getIdjogador        (),obj.getIdjogador      ());
        dados.put(Jogadores_da_Liga_DataModel.getNomejogador      (),obj.getNomejogador    ());
        dados.put(Jogadores_da_Liga_DataModel.getEmailjogador     (),obj.getEmailjogador   ());
        dados.put(Jogadores_da_Liga_DataModel.getTipojogador      (),obj.getTipojogador    ());
        dados.put(Jogadores_da_Liga_DataModel.getIdliga           (),obj.getIdliga         ());
        dados.put(Jogadores_da_Liga_DataModel.getQtdecamp         (),obj.getQtdecamp       ());
        dados.put(Jogadores_da_Liga_DataModel.getJogos            (),obj.getJogos          ());
        dados.put(Jogadores_da_Liga_DataModel.getVitorias         (),obj.getVitorias       ());
        dados.put(Jogadores_da_Liga_DataModel.getDerrotas         (),obj.getDerrotas       ());
        dados.put(Jogadores_da_Liga_DataModel.getEmpates          (),obj.getEmpates        ());
        dados.put(Jogadores_da_Liga_DataModel.getLugar_1          (),obj.getLugar_1        ());
        dados.put(Jogadores_da_Liga_DataModel.getLugar_2          (),obj.getLugar_2        ());
        dados.put(Jogadores_da_Liga_DataModel.getLugar_3          (),obj.getLugar_3        ());
        dados.put(Jogadores_da_Liga_DataModel.getLugar_4          (),obj.getLugar_4        ());
        dados.put(Jogadores_da_Liga_DataModel.getLugar_5          (),obj.getLugar_5        ());
        dados.put(Jogadores_da_Liga_DataModel.getGolspro          (),obj.getGolspro        ());
        dados.put(Jogadores_da_Liga_DataModel.getGolscontra       (),obj.getGolscontra     ());
        dados.put(Jogadores_da_Liga_DataModel.getPermissao        (),obj.getPermissao      ());

        sucesso = updateCampPart(Jogadores_da_Liga_DataModel.getTABELA(), idjog,idliga, dados);
        return sucesso;
    }


    public boolean alteraApelidoJogLiga(String idaltera, String apel){

        boolean altera = false;
        dados = new ContentValues();
        dados.put(Jogadores_da_Liga_DataModel.getNomejogador(),apel);
        altera = alteraJogLigaApel(Jogadores_da_Liga_DataModel.getTABELA(),idaltera,dados);

        return altera;
    }

    public boolean updateJogador(int permissao, String keyjogador, String keyliga){
        boolean sucesso = true;
        dados = new ContentValues();
        dados.put(Jogadores_da_Liga_DataModel.getPermissao(),permissao);
        sucesso = atualizaJogador(Jogadores_da_Liga_DataModel.getTABELA(), keyjogador,keyliga, dados);

        return sucesso;
    }


}



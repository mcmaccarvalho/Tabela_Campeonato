package com.ponto.ideal.solucoes.tabelacampeonato.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ponto.ideal.solucoes.tabelacampeonato.model.Usuarios;

public class HomeViewModel extends ViewModel {



    public HomeViewModel() {
    }


    private static Usuarios usuLogado;

    public static Usuarios getUsuLogado(){
        return usuLogado;
    }
    public static void setUsuLogado(Usuarios ulog) {
        HomeViewModel.usuLogado = ulog;
    }



    private static MutableLiveData<Usuarios> usulog = new MutableLiveData<>();

    public static void setUsulog(Usuarios newusu) {
        usulog.setValue(newusu);
    }
    public static LiveData<Usuarios> getUsulog() {
        return usulog;
    }

}
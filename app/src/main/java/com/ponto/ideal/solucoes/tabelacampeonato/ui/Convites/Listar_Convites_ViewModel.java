package com.ponto.ideal.solucoes.tabelacampeonato.ui.Convites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ponto.ideal.solucoes.tabelacampeonato.model.ConviteLiga;

public class Listar_Convites_ViewModel extends ViewModel {


    private MutableLiveData<Integer> resultLigas;
    private static ConviteLiga lcConviteLiga;

    public Listar_Convites_ViewModel() {
        resultLigas = new MutableLiveData<>();
        resultLigas.setValue(0);

    }

    public LiveData<Integer> getNliga() {
        return resultLigas;
    }
    public void setResultLigas(Integer s) {
        resultLigas.setValue(s);
    }


    public static ConviteLiga getLcConviteLiga() {
        return lcConviteLiga();
    }

    private static ConviteLiga lcConviteLiga() {
        return lcConviteLiga;
    }

    public static void setLcConviteLiga(ConviteLiga conviteLiga) {
        Listar_Convites_ViewModel.lcConviteLiga = conviteLiga;
    }



}
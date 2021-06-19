package com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_Inscritos;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JogosTeste;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Participantes;

import java.util.ArrayList;

public class NovoCampeonatoViewModel extends ViewModel {


    private ArrayList<JogosTeste> datasets;

    public NovoCampeonatoViewModel() {

    }

    private static int numpart  ;
    private static int tipoturno;
    private static int numjogos ;

    private static String A  ;
    private static String B  ;
    private static String C  ;
    private static String D  ;
    private static String E  ;
    private static String F  ;

    public static String getA(){
        return A;
    }
    public static void setA(String AS) {
        NovoCampeonatoViewModel.A = AS;
    }

    public static String getB(){
        return B;
    }
    public static void setB(String BS) {
        NovoCampeonatoViewModel.B = BS;
    }

    public static String getC(){
        return C;
    }
    public static void setC(String CS) {
        NovoCampeonatoViewModel.C = CS;
    }

    public static String getD(){
        return D;
    }
    public static void setD(String DS) {
        NovoCampeonatoViewModel.D = DS;
    }

    public static String getE(){
        return E;
    }
    public static void setE(String ES) {
        NovoCampeonatoViewModel.E = ES;
    }

    public static String getF(){
        return F;
    }
    public static void setF(String FS) {
        NovoCampeonatoViewModel.F = FS;
    }
    private static ArrayList<Jogadores_Inscritos> jogadores_inscritos;

    public static ArrayList<Jogadores_Inscritos> getJogadores_inscritos(){
        return jogadores_inscritos;
    }
    public static void setJogadores_inscritos(ArrayList<Jogadores_Inscritos> jogins) {
        NovoCampeonatoViewModel.jogadores_inscritos = jogins;
    }

    public static Integer getNumpart(){
        return numpart;
    }
    public static void setNumpart(Integer nnumpart) {
        NovoCampeonatoViewModel.numpart = nnumpart;
    }

    public static Integer getNumjogos(){
        return numjogos;
    }
    public static void setNumjogos(Integer nnumjogos) {
        NovoCampeonatoViewModel.numjogos = nnumjogos;
    }

    public static Integer getTipoturno(){
        return tipoturno;
    }
    public static void setTipoturno(Integer ntipoturno) {
        NovoCampeonatoViewModel.tipoturno = ntipoturno;
    }


    private MutableLiveData<Boolean> Bolaltins = new MutableLiveData<>();
    public void setBolaltins(Boolean boll) {
        Bolaltins.setValue(boll);
    }
    public LiveData<Boolean> getBolaltins() {
        return Bolaltins;
    }



    private static MutableLiveData<Boolean> Booleanfinal = new MutableLiveData<>();
    public static void setBooleanfinal(Boolean bollfin) {
        Booleanfinal.setValue(bollfin);
    }
    public static LiveData<Boolean> getBooleanfinal() {
        return Booleanfinal;
    }

    private static MutableLiveData<Boolean> BooleanSalvarCamp = new MutableLiveData<>();
    public static void setBooleanSalvarCamp(Boolean bollsalva) {
        BooleanSalvarCamp.setValue(bollsalva);
    }
    public static LiveData<Boolean> getBooleanSalvarCamp() {
        return BooleanSalvarCamp;
    }

    private static MutableLiveData<ArrayList<Participantes>> tempPart = new MutableLiveData<ArrayList<Participantes>>();
    public static void setTempPart(ArrayList<Participantes> datasets) {
        tempPart.setValue(datasets);
    }
    public static LiveData<ArrayList<Participantes>> getTempPart() {
        return tempPart;
    }




    private static MutableLiveData<ArrayList<Participantes>> campPart = new MutableLiveData<ArrayList<Participantes>>();
    public static void setCampPart(ArrayList<Participantes> datasets) {
        campPart.setValue(datasets);
    }
    public static LiveData<ArrayList<Participantes>> getCampPart() {
        return campPart;
    }


    public static ArrayList<Bitmap> bitimgjog = new ArrayList<>();
    public static ArrayList<Bitmap> getBitimgjog(){
        return bitimgjog;
    }
    public static void setBitimgjog(ArrayList<Bitmap> bitimgjogs) {
        NovoCampeonatoViewModel.bitimgjog = bitimgjogs;
    }

    public static ArrayList<JogosTeste> listajogos = new ArrayList<>();
    public static ArrayList<JogosTeste> getListajogos(){
        return listajogos;
    }
    public static void setListajogos(ArrayList<JogosTeste> listajogoss) {
        NovoCampeonatoViewModel.listajogos = listajogoss;
    }

    private static MutableLiveData<ArrayList<JogosTeste>> dataset = new MutableLiveData<ArrayList<JogosTeste>>();
    public static void setDataset(ArrayList<JogosTeste> datasets) {
        dataset.setValue(datasets);
    }
    public static LiveData<ArrayList<JogosTeste>> getDataset() {
        return dataset;
    }

    private static MutableLiveData<ArrayList<JogosTeste>> primeiroturno = new MutableLiveData<ArrayList<JogosTeste>>();
    public static void setPrimeiroturno(ArrayList<JogosTeste> datasets) {
        primeiroturno.setValue(datasets);
    }
    public static LiveData<ArrayList<JogosTeste>> getPrimeiroturno() {
        return primeiroturno;
    }

    private static MutableLiveData<ArrayList<JogosTeste>> segundoturno = new MutableLiveData<ArrayList<JogosTeste>>();
    public static void setSegundoturno(ArrayList<JogosTeste> datasets) {
        segundoturno.setValue(datasets);
    }
    public static LiveData<ArrayList<JogosTeste>> getSegundoturno() {
        return segundoturno;
    }


    private static MutableLiveData<String[]> finalistas = new MutableLiveData<String[]>();

    public static void setFinalistas(String[] sFinal) {
        finalistas.setValue(sFinal);
    }
    public static LiveData<String[]> getFinalistas() {
        return finalistas;
    }


}
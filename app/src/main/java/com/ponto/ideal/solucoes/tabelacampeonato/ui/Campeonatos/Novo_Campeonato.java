package com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JogosTeste;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;

import java.util.ArrayList;

public class Novo_Campeonato extends Fragment implements View.OnClickListener{

    private NovoCampeonatoViewModel ncViewModel;

    private TextView txtnumjogos;
    private RadioButton rb1,rb2,rb3,rb4,rbt1,rbt2,rbt3,rbt4;
    private RadioGroup rgnumpart,rgnumturno;
    private Button btavancar;
    private ScrollView svnovocamp;

    private int numpart = 0;
    private int tipoturno = 0;
    private int numjogos = 0;
    private int njj;


    public static Novo_Campeonato newInstance() {
        return new Novo_Campeonato();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.novo_campeonato_fragment, container, false);

        initViews(v);

        return v;
    }

    private void initViews(View v) {

        rb1  = v.findViewById(R.id.rb1);
        rb2  = v.findViewById(R.id.rb2);
        rb3  = v.findViewById(R.id.rb3);
        rb4  = v.findViewById(R.id.rb4);
        rbt1 = v.findViewById(R.id.rbt1);
        rbt2 = v.findViewById(R.id.rbt2);
        rbt3 = v.findViewById(R.id.rbt3);
        rbt4 = v.findViewById(R.id.rbt4);
        rgnumpart = v.findViewById(R.id.rgnumpart);
        rgnumturno = v.findViewById(R.id.rgnumturno);
        btavancar = v.findViewById(R.id.btavancar);
        svnovocamp = v.findViewById(R.id.svnovocamp);
        txtnumjogos = v.findViewById(R.id.txtnumjogos);
        rb1 .setOnClickListener(this);
        rb2 .setOnClickListener(this);
        rb3 .setOnClickListener(this);
        rb4 .setOnClickListener(this);
        rbt1.setOnClickListener(this);
        rbt2.setOnClickListener(this);
        rbt3.setOnClickListener(this);
        rbt4.setOnClickListener(this);
        btavancar.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ncViewModel = ViewModelProviders.of(this).get(NovoCampeonatoViewModel.class);
    }

    @Override
    public void onClick(View v) {

        int idview = v.getId();
        util.vibratePhone(getContext(), (short) 30);
        switch (idview){
            case R.id.rb1  : numpart=3;    break;
            case R.id.rb2  : numpart=4;    break;
            case R.id.rb3  : numpart=5;    break;
            case R.id.rb4  : numpart=6;    break;
            case R.id.rbt1 : tipoturno=1;    break;
            case R.id.rbt2 : tipoturno=2;    break;
            case R.id.rbt3 : tipoturno=3;    break;
            case R.id.rbt4 : tipoturno=4;    break;
           case R.id.btavancar : avancar() ; break;
        }
        calculajogos();
    }

    private void avancar() {

        NovoCampeonatoViewModel.getDataset().removeObserver(new Observer<ArrayList<JogosTeste>>() {
            @Override
            public void onChanged(ArrayList<JogosTeste> jogosTestes) {

            }
        });
        if(numpart == 0 ){
            util.showSnackCampo(svnovocamp,"Selecione número de participantes.");
        }else if(tipoturno ==0){
            util.showSnackCampo(svnovocamp,"Selecione tipo de Campeonato.");
        }else {
            NovoCampeonatoViewModel.setNumjogos(njj);
            NovoCampeonatoViewModel.setNumpart(numpart);
            NovoCampeonatoViewModel.setTipoturno(tipoturno);

            MainActivity.navController.navigate(R.id.action_novo_Campeonato_to_inscrever_Jogadores);
        }
    }

    public void calculajogos(){
        int x = numpart-1;
        int f = x;

        while (x > 1) {
            f = f + (x - 1);
            x--;
        }
        int sf =0;
        switch (tipoturno){
            case 2 : f = (f * 2)     ; break;
            case 3 : sf=1            ; break;
            case 4 : f = (f * 2);sf=1; break;
        }
        if(tipoturno ==0 || numpart ==0){
            f=0;
        }
        njj = (int) f;
        txtnumjogos.setText(String.valueOf(njj+sf));

    }

}
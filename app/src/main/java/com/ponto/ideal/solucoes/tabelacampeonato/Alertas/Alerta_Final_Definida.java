package com.ponto.ideal.solucoes.tabelacampeonato.Alertas;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DatabaseReference;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JogosTeste;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Participantes;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.Campeonato;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.Classificacao;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.NovoCampeonatoViewModel;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;

import java.util.ArrayList;


public class Alerta_Final_Definida extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    DatabaseReference databaseReference;


    NovoCampeonatoViewModel ncViewModel;
    String jog1,jog2,id1,id2;
    private int tipoturno;

    ArrayList<Participantes> partCamp = new ArrayList<>();

    private TextView  txtjog1, txtjog2,txttx,txttitle;
    private ImageView imgjog1, imgjog2;
    private Button btencerrar, bttdsjogos;
    private LinearLayout llsalvar,llimg,llnome;
    private ConstraintLayout clmainresult,clbotao;
    private Switch swpenalti;
    ArrayList<JogosTeste> datatemp = new ArrayList<>();



    public Alerta_Final_Definida(String jog1, String jog2,String id1, String id2,int tipoturno) {
        this.jog1 = jog1;
        this.jog2 = jog2;
        this.id1 = id1;
        this.id2 = id2;
        this.tipoturno = tipoturno;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_final_definida, container, false);


        initViews(v);



        return v;
    }

    public void initViews(View v){

        setCancelable(false);

        ncViewModel = ViewModelProviders.of(requireActivity()).get(NovoCampeonatoViewModel.class);

        ncViewModel.getCampPart().observe(requireActivity(), new Observer<ArrayList<Participantes>>() {
            @Override
            public void onChanged(ArrayList<Participantes> participantes) {
                partCamp = participantes;
            }
        });
        Log.i("Atualizajogos","getListajogos: " + NovoCampeonatoViewModel.getListajogos().size());
        datatemp=NovoCampeonatoViewModel.getListajogos();

        btencerrar = v.findViewById(R.id.btencerrar);
        bttdsjogos = v.findViewById(R.id.bttdsjogos);
        clmainresult = v.findViewById(R.id.clmainresult);
        txtjog1 = v.findViewById(R.id.txtjog1);
        txtjog2 = v.findViewById(R.id.txtjog2);
        imgjog1 = v.findViewById(R.id.imgjog1);
        imgjog2 = v.findViewById(R.id.imgjog2);
        txttx = v.findViewById(R.id.txttx);
        txttitle = v.findViewById(R.id.txttitle);
        llimg = v.findViewById(R.id.llimg);
        llnome = v.findViewById(R.id.llnome);


        if(tipoturno==1 || tipoturno==2){
            llnome.removeView(txtjog2);
            llimg.removeView(imgjog2);
            llimg.removeView(txttx);
            txttitle.setText("Campeão Definido");
            btencerrar.setText("Encerrar");
        }

            txtjog1.setText(jog1);
            txtjog2.setText(jog2);
            Bitmap bitmap1 = util.loadImageBitmap(getContext(), id1);
            Bitmap bitmap2 = util.loadImageBitmap(getContext(), id2);
            imgjog1.setImageBitmap(bitmap1);
            imgjog2.setImageBitmap(bitmap2);


        bttdsjogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                util.vibratePhone(getContext(), (short) 30);
                btencerrar.setClickable(false);
                btencerrar.setEnabled(false);
                Classificacao.tdsjogos=true;
                getDialog().dismiss();
            }
        });

        btencerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 30);
                bttdsjogos.setClickable(false);
                bttdsjogos.setEnabled(false);
                salvar();
            }
        });


    }

    public void salvar(){
        Classificacao.tdsjogos=true;
        Classificacao.ctrlJogo=0;

        Log.i("Atualizajogos",": " + datatemp.size());
        for (int i=0;i<datatemp.size();i++){
            if (datatemp.get(i).getStatusjogo()==0){
                datatemp.get(i).setStatusjogo(1);
                datatemp.get(i).setPlacar1("0");
                datatemp.get(i).setPlacar2("0");
            }
        }

        ArrayList<JogosTeste> pri2 = new ArrayList<>();
        for(int i=0;i<datatemp.size();i++){
            if(datatemp.get(i).getTurno()==1){
                pri2.add(datatemp.get(i));
            }
        }
        NovoCampeonatoViewModel.setPrimeiroturno(pri2);

        ArrayList<JogosTeste> seg2 = new ArrayList<>();
        for(int i=0;i<datatemp.size();i++){
            if(datatemp.get(i).getTurno()==2){
                seg2.add(datatemp.get(i));
            }
        }
        NovoCampeonatoViewModel.setSegundoturno(seg2);


        NovoCampeonatoViewModel.setDataset(datatemp);
        NovoCampeonatoViewModel.setListajogos(datatemp);
        Classificacao.listajogos=datatemp;

        if(tipoturno==2 || tipoturno==4) {
            Campeonato.vpclass.setCurrentItem(1);
        }else{
            Campeonato.vpclass.setCurrentItem(0);
        }
        getDialog().dismiss();
    }

}

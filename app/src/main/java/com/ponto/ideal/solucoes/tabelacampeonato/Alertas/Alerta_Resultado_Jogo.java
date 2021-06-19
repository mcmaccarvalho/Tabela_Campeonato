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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Jogos_Dataset_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Jogos_Dataset_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JogosTeste;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Participantes;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.Campeonato;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.Classificacao;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.NovoCampeonatoViewModel;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.home.HomeFragment;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;

import java.util.ArrayList;
import java.util.Objects;


public class Alerta_Resultado_Jogo extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    DatabaseReference databaseReference;


    NovoCampeonatoViewModel ncViewModel;
    JogosTeste jogosTeste = new JogosTeste();
    String jog1,jog2;
    private int tipoturno;
    private int numpart;
    public  int intdataset;


    ArrayList<JogosTeste> datatemp = new ArrayList<>();
    ArrayList<Participantes> partCamp = new ArrayList<>();
    private ArrayList<JogosTeste> jt;

    private TextView txtnumjogo, txtjog1, txtjog2, placar1, placar2;
    private ImageView imgjog1, imgjog2;
    private Button btsalvar, btcancelar,btreset;
    private LinearLayout llmais1,llmenos1,llmais2,llmenos2;
    private ConstraintLayout clmainresult;

    private String part0,part1,part2,part3,part4,part5;

    int gols1=0;
    int gols2=0;

    boolean bcancel=false;
    ArrayList<JogosTeste> jtTurno1 = new ArrayList<>();
    ArrayList<JogosTeste> jtTurno2 = new ArrayList<>();
    private ArrayList<JogosTeste> jt5;
    private String fix3;
    private ArrayList<JogosTeste> jtnew;
    private ArrayList<JogosTeste> dataarray;
    private int fazturno;
    private boolean doisTurnos = false;
    private String passat1 = "";
    private String passat2 = "";
    private String passat3 = "";
    private String passat4 = "";
    private String passat5 = "";
    private String passat6 = "";
    private ArrayList<JogosTeste> jgfim = new ArrayList<>();

    private ArrayList<String> ganjog = new ArrayList<>();
    private ArrayList<String> perjog = new ArrayList<>();
    public Alerta_Resultado_Jogo(JogosTeste jogosTeste, String jog1, String jog2) {
        this.jogosTeste = jogosTeste;
        this.jog1 = jog1;
        this.jog2 = jog2;
    }

    private boolean tdsjogos=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_res_jog, container, false);


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

        clmainresult = v.findViewById(R.id.clmainresult);
        llmais1 = v.findViewById(R.id. llmais1 );
        llmais2 = v.findViewById(R.id. llmais2 );
        llmenos1 = v.findViewById(R.id. llmenos1);
        llmenos2 = v.findViewById(R.id. llmenos2);
        txtnumjogo = v.findViewById(R.id.txtnumjogo);
        txtjog1 = v.findViewById(R.id.txtjog1);
        txtjog2 = v.findViewById(R.id.txtjog2);
        placar1 = v.findViewById(R.id.placar1);
        placar2 = v.findViewById(R.id.placar2);
        imgjog1 = v.findViewById(R.id.imgjog1);
        imgjog2 = v.findViewById(R.id.imgjog2);
        btsalvar = v.findViewById(R.id.btsalvar);
        btcancelar = v.findViewById(R.id.btcancelar);
        btreset = v.findViewById(R.id.btreset);





        datatemp=NovoCampeonatoViewModel.getListajogos();
        tipoturno= NovoCampeonatoViewModel.getTipoturno();
        numpart= NovoCampeonatoViewModel.getNumpart();
        intdataset =(numpart*10)+tipoturno;

        jtTurno1.clear();
        jtTurno2.clear();

        for(int i=0;i<datatemp.size();i++){
            if(datatemp.get(i).getTurno()==1){
                jtTurno1.add(datatemp.get(i));
            }
            if(datatemp.get(i).getTurno()==2){
                jtTurno2.add(datatemp.get(i));
            }
        }

        for(int i=0;i<jtTurno1.size();i++){
            Log.i("inialerta","jtTurno1:"+nomejog(jtTurno1.get(i).getIdjogador1())+":"+nomejog(jtTurno1.get(i).getIdjogador2()));
        }
        for(int i=0;i<jtTurno2.size();i++){
            Log.i("inialerta","jtTurno2:"+nomejog(jtTurno2.get(i).getIdjogador1())+":"+nomejog(jtTurno2.get(i).getIdjogador2()));
        }




        part0=NovoCampeonatoViewModel.getA();
        part1=NovoCampeonatoViewModel.getB();
        part2=NovoCampeonatoViewModel.getC();
        part3=NovoCampeonatoViewModel.getD();
        part4=NovoCampeonatoViewModel.getE();
        part5=NovoCampeonatoViewModel.getF();

        llmais1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 30);
                gols1++;
                placar1.setText(String.valueOf(gols1));

//                if(gols1==6){
//                    util.showSnackError(clmainresult,"Será que o "+jogosres.getNomejogador2()+
//                            " sabe realmente jogar ? ");
//                }
            }
        });

        llmais2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 30);
                gols2++;
                placar2.setText(String.valueOf(gols2));
//                if(gols2==6){
//                    util.showSnackError(clmainresult,"Será que o "+jogosres.getNomejogador1()+
//                            " sabe realmente jogar ? ");
//                }
            }
        });

        llmenos1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 30);
                if(gols1>0){
                   gols1--;
                }

                placar1.setText(String.valueOf(gols1));
            }
        });


        llmenos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 30);
                if(gols2>0){
                    gols2--;
                }

                placar2.setText(String.valueOf(gols2));
            }
        });


        int proxjog=1;
//        for(int i=0;i<datatemp.size();i++){
//            if(datatemp.get(i).getStatusjogo()==1){
//                proxjog++;
//            }
//        }


            proxjog=Integer.parseInt(jogosTeste.getNumjog());


        if(jogosTeste!=null){

            if(jogosTeste.getPlacar1().equals("_")){jogosTeste.setPlacar1("0");}
            if(jogosTeste.getPlacar2().equals("_")){jogosTeste.setPlacar2("0");}
            txtnumjogo.setText(String.valueOf(proxjog));
            txtjog1.setText(jog1);
            txtjog2.setText(jog2);
            placar1.setText(jogosTeste.getPlacar1());
            placar2.setText(jogosTeste.getPlacar2());
            gols1=Integer.parseInt(jogosTeste.getPlacar1());
            gols2=Integer.parseInt(jogosTeste.getPlacar2());
            Bitmap bitmap1 = util.loadImageBitmap(getContext(), jogosTeste.getIdjogador1());
            Bitmap bitmap2 = util.loadImageBitmap(getContext(), jogosTeste.getIdjogador2());
            imgjog1.setImageBitmap(bitmap1);
            imgjog2.setImageBitmap(bitmap2);

            if(jogosTeste.getStatusjogo()==0){
                btreset.setVisibility(View.INVISIBLE);
            }else{
                Classificacao.tdsjogos=false;
                btreset.setVisibility(View.VISIBLE);
            }
        }




        btcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                util.vibratePhone(getContext(), (short) 30);
                btsalvar.setClickable(false);
                btsalvar.setEnabled(false);
                btreset.setEnabled(false);
                btreset.setClickable(false);
                if(getShowsDialog()==true) {
                    getDialog().dismiss();
                }
            }
        });

        btsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 30);
                btcancelar.setClickable(false);
                btcancelar.setEnabled(false);
                btreset.setEnabled(false);
                btreset.setClickable(false);
                salvar();
            }
        });

        btreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 30);
                btcancelar.setClickable(false);
                btcancelar.setEnabled(false);
                btsalvar.setEnabled(false);
                btsalvar.setClickable(false);
                reset();
            }
        });


    }

    public void salvar(){
        datatemp.get(jogosTeste.getIdjogo()).setPlacar1(placar1.getText().toString());
        datatemp.get(jogosTeste.getIdjogo()).setPlacar2(placar2.getText().toString());
        datatemp.get(jogosTeste.getIdjogo()).setStatusjogo(1);
        remontaListadeJogos();


    }

    public void reset(){
        datatemp.get(jogosTeste.getIdjogo()).setPlacar1("_");
        datatemp.get(jogosTeste.getIdjogo()).setPlacar2("_");
        datatemp.get(jogosTeste.getIdjogo()).setStatusjogo(0);
        remontaListadeJogos();
    }

    public void remontaListadeJogos(){

        switch (intdataset){
            case 31:remonta31();break;
            case 32:remonta32();break;
            case 33:remonta31();break;
            case 34:remonta32();break;
            case 41:remonta41();break;
            case 42:remonta42();break;
            case 43:remonta41();break;
            case 54:remonta52();break;
            case 51:remonta51();break;
            case 52:remonta52();break;
            case 53:remonta51();break;
            case 44:remonta42();break;
            case 61:fazturno=1;remonta61();break;
            case 62:fazturno=1;doisTurnos=true;remonta61();break;
            case 63:fazturno=1;remonta61();break;
            case 64:fazturno=1;doisTurnos=true;remonta61();break;
        }
    }

    private void remonta31() {

        JogosTeste passa;
        jt=new ArrayList<>();
        int cont = 0;
        int retid=-1;
        jt.clear();
        ganjog.clear();
        perjog.clear();

        jt.add(datatemp.get(0));
        String res0 [] = winnerJog(datatemp.get(0));

        cont = 1;
        retid = achalado(1, res0[0],datatemp.get(1).getIdjogador2());
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res1[] = winnerJog(jt.get(cont));

        cont = 2;
        retid = achalado(1, res0[1],datatemp.get(1).getIdjogador2());
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res2[] = winnerJog(jt.get(cont));

        datatemp=jt;
        fechaDialog();
    }
    private void remonta32() {

        JogosTeste passa;
        jt=new ArrayList<>();
        int cont = 0;
        int retid=-1;
        jt.clear();
        ganjog.clear();
        perjog.clear();

        jt.add(datatemp.get(0));
        String res0 [] = winnerJog(datatemp.get(0));

        cont = 1;
        retid = achalado(1, res0[0],datatemp.get(1).getIdjogador2());
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res1[] = winnerJog(jt.get(cont));

        cont = 2;
        retid = achalado(1, res0[1],datatemp.get(1).getIdjogador2());
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res2[] = winnerJog(jt.get(cont));

        jt.add(datatemp.get(3));
        String res3 [] = winnerJog(datatemp.get(3));

        cont = 4;
        retid = achalado(2, res3[0],datatemp.get(4).getIdjogador1());
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res4[] = winnerJog(jt.get(cont));

        cont = 5;
        retid = achalado(2, res3[1],datatemp.get(4).getIdjogador1());
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res5[] = winnerJog(jt.get(cont));

        datatemp=jt;
        fechaDialog();



    }
    private void remonta41() {

        JogosTeste passa;
        jt=new ArrayList<>();
        int cont = 0;
        int retid=-1;
        jt.clear();
        ganjog.clear();
        perjog.clear();

        jt.add(datatemp.get(0));
        jt.add(datatemp.get(1));
        String res0 [] = winnerJog(datatemp.get(0));
        String res1 [] = winnerJog(datatemp.get(1));

        cont = 2;
        retid = achalado(1, res0[0],res1[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res2[] = winnerJog(jt.get(cont));

        cont = 3;
        retid = achalado(1, res0[1],res1[1]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res3[] = winnerJog(jt.get(cont));


        String prox1 = "";
        String prox2 = "";

        if(res2[0].equals(res0[0])){
            prox1=res1[1];
            prox2=res0[1];
        }else{
            prox1=res0[1];
            prox2=res1[1];
        }

        cont = 4;
        retid = achalado(1, res2[0],prox1);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res4[] = winnerJog(jt.get(cont));

        cont = 5;
        retid = achalado(1, res2[1],prox2);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res5[] = winnerJog(jt.get(cont));




        int kk = 0;
        while (kk < jt.size()) {
            Log.i("resultcamp", "jt" + jt.get(kk).getIdjogo() + ":" + nomejog(jt.get(kk).getIdjogador1()) + ":" +
                    nomejog(jt.get(kk).getIdjogador2()));
            Log.i("resultcamp","gan"+(kk)+":"+nomejog(ganjog.get(kk))+":"+nomejog(perjog.get(kk)));
            kk++;
        }


        datatemp=jt;
        fechaDialog();


    }
    private void remonta42() {



        JogosTeste passa;
        jt=new ArrayList<>();
        int cont = 0;
        int retid=-1;
        jt.clear();
        ganjog.clear();
        perjog.clear();

        jt.add(datatemp.get(0));
        jt.add(datatemp.get(1));
        String res0 [] = winnerJog(datatemp.get(0));
        String res1 [] = winnerJog(datatemp.get(1));

        cont = 2;
        retid = achalado(1, res0[0],res1[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res2[] = winnerJog(jt.get(cont));

        cont = 3;
        retid = achalado(1, res0[1],res1[1]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res3[] = winnerJog(jt.get(cont));


        String prox1 = "";
        String prox2 = "";

        if(res2[0].equals(res0[0])){
            prox1=res1[1];
            prox2=res0[1];
        }else{
            prox1=res0[1];
            prox2=res1[1];
        }

        cont = 4;
        retid = achalado(1, res2[0],prox1);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res4[] = winnerJog(jt.get(cont));

        cont = 5;
        retid = achalado(1, res2[1],prox2);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res5[] = winnerJog(jt.get(cont));

        jt.add(datatemp.get(6));
        jt.add(datatemp.get(7));
        String res6 [] = winnerJog(datatemp.get(6));
        String res7 [] = winnerJog(datatemp.get(7));

        cont = 8;
        retid = achalado(2, res6[0],res7[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res8[] = winnerJog(jt.get(cont));

        cont = 9;
        retid = achalado(2, res6[1],res7[1]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res9[] = winnerJog(jt.get(cont));


        prox1 = "";
        prox2 = "";

        if(res8[0].equals(res6[0])){
            prox1=res7[1];
            prox2=res6[1];
        }else{
            prox1=res6[1];
            prox2=res7[1];
        }

        cont = 10;
        retid = achalado(2, res8[0],prox1);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res10[] = winnerJog(jt.get(cont));

        cont = 11;
        retid = achalado(2, res8[1],prox2);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res11[] = winnerJog(jt.get(cont));


        int kk = 0;
        while (kk < jt.size()) {
            Log.i("resultcamp", "jt" + jt.get(kk).getIdjogo() + ":" + nomejog(jt.get(kk).getIdjogador1()) + ":" +
                    nomejog(jt.get(kk).getIdjogador2()));
            Log.i("resultcamp","gan"+(kk)+":"+nomejog(ganjog.get(kk))+":"+nomejog(perjog.get(kk)));
            kk++;
        }


        datatemp=jt;
        fechaDialog();

    }
    private void remonta51() {

        JogosTeste passa;
        jt=new ArrayList<>();
        int cont = 0;
        int retid=-1;
        jt.clear();
        ganjog.clear();
        perjog.clear();

        jt.add(datatemp.get(0));
        jt.add(datatemp.get(1));

        String res0 [] = winnerJog(datatemp.get(0));
        String res1 [] = winnerJog(datatemp.get(1));

        cont = 2;
        retid = achalado(1, part4, res0[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res2[] = winnerJog(jt.get(cont));

        cont = 3;
        retid = achalado(1, res0[1], res1[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res3[] = winnerJog(jt.get(cont));

        cont = 4;
        retid = achalado(1, res1[1], part4);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res4[] = winnerJog(jt.get(cont));

        cont = 5;
        retid = achalado(1, res0[0], res1[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res5[] = winnerJog(jt.get(cont));

        String prox = "";
        for(int i=0;i<partCamp.size();i++){
            if(!partCamp.get(i).getId_jogador().equals(res4[0])&&
                    !partCamp.get(i).getId_jogador().equals(res4[1])&&
                    !partCamp.get(i).getId_jogador().equals(res5[0])&&
                    !partCamp.get(i).getId_jogador().equals(res5[1])){
                prox=partCamp.get(i).getId_jogador();
            }
        }

        cont = 6;
        retid = achalado(1, prox, res4[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res6[] = winnerJog(jt.get(cont));

        cont = 7;
        //jogo 7 = per4 + nj contra do jogo 5
        String [] ncj5 = naoJogouContra(1,7,5,res4[1]);
        retid=achalado(1,res4[1],ncj5[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res7[] = winnerJog(jt.get(cont));

        cont = 8;
        String [] ncj6 = naoJogouContra(1,8,6,ncj5[1]);
        retid=achalado(1,ncj5[1],ncj6[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res8[] = winnerJog(jt.get(cont));

        cont = 9;
        String [] ncj7 = naoJogouContra(1,9,7,ncj6[1]);
        retid=achalado(1,ncj6[1],ncj7[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont);
        passa.setNumjog(String.valueOf(cont + 1 ));
        jt.add(passa);
        String res9[] = winnerJog(jt.get(cont));


        //logs


        int kk = 0;
        while (kk < jt.size()) {
            Log.i("resultcamp", "jt" + jt.get(kk).getIdjogo() + ":" + nomejog(jt.get(kk).getIdjogador1()) + ":" +
                    nomejog(jt.get(kk).getIdjogador2()));
            Log.i("resultcamp","gan"+(kk)+":"+nomejog(ganjog.get(kk))+":"+nomejog(perjog.get(kk)));
            kk++;
        }

        //logs

        datatemp=jt;
        fechaDialog();
    }
    private void remonta52() {

        jt = new ArrayList<>();
        jt.clear();
       // jt=datatemp;

        int ctrl = -1;
        ganjog.clear();
        perjog.clear();

        //jogo 0 = datatemp
        jt.add(datatemp.get(0));
        String res0 [] = winnerJog(jt.get(0));

        //jogo 1= datatemp
        jt.add(datatemp.get(1));
        String res1 [] = winnerJog(jt.get(1));

        Log.i("buscajogo",0+" " + nomejog(jt.get(0).getIdjogador1()) + " x " + nomejog(jt.get(0).getIdjogador2())+" idjogo: " + jt.get(0).getIdjogo());
        Log.i("buscajogo",1+" " + nomejog(jt.get(1).getIdjogador1()) + " x " + nomejog(jt.get(1).getIdjogador2())+" idjogo: " + jt.get(1).getIdjogo());

        //jogo 2 = fixo + gan0

        achalado(1,jt.get(0).getIdjogador1(),jt.get(0).getIdjogador2());
        achalado(1,jt.get(1).getIdjogador1(),jt.get(1).getIdjogador2());

        int retid = achalado(1,res0[0],part4);
        //JogosTeste passa = achaJogo(1,2,res0[0],part4);
        JogosTeste passa = datatemp.get(retid);
        passa.setIdjogo(2);
        passa.setNumjog("3");
        jt.add(passa);
        Log.i("buscajogo",2 +" " + nomejog(res0[0]) + " x " + nomejog(part4)
        + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res2 [] = winnerJog(jt.get(2));

        //jogo 3 = per0 + gan1'4
        retid = achalado(1,res0[1],res1[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(3);
        passa.setNumjog("4");
        jt.add(passa);
        Log.i("buscajogo",3 +" "+ nomejog(res0[1]) + " x " + nomejog(res1[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res3 [] = winnerJog(jt.get(3));
        //jogo 4 = part4 + per1
        retid=achalado(1,part4,res1[1]);
        passa = datatemp.get(retid);
        passa.setIdjogo(4);
        passa.setNumjog("5");
        jt.add(passa);
        Log.i("buscajogo",4+" " + nomejog(part4) + " x " + nomejog(res1[1])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res4 [] = winnerJog(jt.get(4));
        //jogo 5 gan0 + gan1
        retid=achalado(1,res0[0],res1[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(5);
        passa.setNumjog("6");
        jt.add(passa);
        Log.i("buscajogo",5+" " + nomejog(res0[0]) + " x " + nomejog(res1[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res5 [] = winnerJog(jt.get(5));
        //jogo 6 = gan4 + não jogou
        String nj = naojogou5P(6);
        retid = achalado(1,nj,res4[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(6);
        passa.setNumjog("7");
        jt.add(passa);
        Log.i("buscajogo",6+" " + nomejog(nj) + " x " + nomejog(res4[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res6 [] = winnerJog(jt.get(6));
       //jogo 7 = per4 + nj contra do jogo 5
        String [] ncj5 = naoJogouContra(1,7,5,res4[1]);
        retid=achalado(1,res4[1],ncj5[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(7);
        passa.setNumjog("8");
        jt.add(passa);
        Log.i("buscajogo",7+" " + nomejog(res4[1]) + " x " + nomejog(ncj5[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res7 [] = winnerJog(jt.get(7));
        //jogo 8 = inverte ncj5 + ncj6
        String [] ncj6 = naoJogouContra(1,8,6,ncj5[1]);
        retid=achalado(1,ncj5[1],ncj6[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(8);
        passa.setNumjog("9");
        jt.add(passa);
        Log.i("buscajogo",8 +" "+ nomejog(ncj5[1]) + " x " + nomejog(ncj6[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res8 [] = winnerJog(jt.get(8));

        //jogo 9 = inverte ncj6 + ncj7
        String [] ncj7 = naoJogouContra(1,9,7,ncj6[1]);
        retid=achalado(1,ncj6[1],ncj7[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(9);
        passa.setNumjog("10");
        jt.add(passa);
        Log.i("buscajogo",9+" " + nomejog(ncj6[1]) + " x " + nomejog(ncj7[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res9 [] = winnerJog(jt.get(9));
        //segundo turno

        //jogo 10 = nao jogou5p = = gan8

        String nj10 = naojogou5P(10);
        retid=achalado(2,nj10,res8[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(10);
        passa.setNumjog("11");
        jt.add(passa);
        Log.i("buscajogo",10+" " + nomejog(nj10) + " x " + nomejog(res8[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());

        String res10 [] = winnerJog(jt.get(10));
        //jogo 11 = per8 + gan9

        retid=achalado(2,res8[1],res9[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(11);
        passa.setNumjog("12");
        jt.add(passa);
        Log.i("buscajogo",11+" " + nomejog(res8[1]) + " x " + nomejog(res9[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res11 [] = winnerJog(jt.get(11));
        //jogo 12 = per9 + gan10

        retid=achalado(2,res9[1],res10[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(12);
        passa.setNumjog("13");
        jt.add(passa);
        Log.i("buscajogo",12+" " + nomejog(res9[1]) + " x " + nomejog(res10[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res12 [] = winnerJog(jt.get(12));
        //jogo 13 = per10 + gan11

       retid= achalado(2,res10[1],res11[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(13);
        passa.setNumjog("14");
        jt.add(passa);
        Log.i("buscajogo",13+" " + nomejog(res10[1]) + " x " + nomejog(res11[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res13 [] = winnerJog(jt.get(13));
        //jogo 14 = per11 + gan12????per9

        retid=achalado(2,res11[1],res9[1]);
        passa = datatemp.get(retid);
        passa.setIdjogo(14);
        passa.setNumjog("15");
        jt.add(passa);
        Log.i("buscajogo",14+" " + nomejog(res11[1]) + " x " + nomejog(res9[1])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res14 [] = winnerJog(jt.get(14));
        //jogo 15 = per12 + nao jogou contra 13 ?? gan10 gan11
       // String [] ncj13 = naoJogouContra(2,15,13,res12[1]);
        retid=achalado(2,res10[0],res11[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(15);
        passa.setNumjog("16");
        jt.add(passa);
        Log.i("buscajogo",15+" " + nomejog(res10[0]) + " x " + nomejog(res11[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res15 [] = winnerJog(jt.get(15));
        //jogo 16 =  nj5p + gan14


        String nj16 = naojogou5P(16);
        retid= achalado(2,nj16,res14[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(16);
        passa.setNumjog("17");
        jt.add(passa);
        Log.i("buscajogo",16+" " + nomejog(nj16) + " x " + nomejog(res14[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res16 [] = winnerJog(jt.get(16));
        //jogo 17 = per14 + nao jogou contra 15
        String [] ncj15 = naoJogouContra(2,17,15,res14[1]);
        retid=achalado(2,res14[1],ncj15[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(17);
        passa.setNumjog("18");
        jt.add(passa);
        Log.i("buscajogo",17+" " + nomejog(res14[1]) + " x " + nomejog(ncj15[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res17 [] = winnerJog(jt.get(17));

        //jogo 18 = invert nj15 + nao jogou contra 16
        String [] ncj16 = naoJogouContra(2,18,16,ncj15[1]);
        retid=achalado(2,ncj15[1],ncj16[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(18);
        passa.setNumjog("19");
        jt.add(passa);
        Log.i("buscajogo",18+" " + nomejog(ncj15[1]) + " x " + nomejog(ncj16[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res18 [] = winnerJog(jt.get(18));
        //jogo 19 = invert nj16 + nao jogou contra 17
        String [] ncj17 = naoJogouContra(2,19,17,ncj16[1]);
        retid= achalado(2,ncj16[1],ncj17[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(19);
        passa.setNumjog("20");
        jt.add(passa);
        Log.i("buscajogo",19+" " + nomejog(ncj16[1]) + " x " + nomejog(ncj17[0])
                + " encontrou: " + nomejog(passa.getIdjogador1())+" x "+nomejog(passa.getIdjogador2())+" idjogo: " + passa.getIdjogo());
        String res19 [] = winnerJog(jt.get(19));
        int kk = 0;
        while (kk < jt.size()) {
            Log.i("resultcamp", "jt" + jt.get(kk).getIdjogo() + ":" + nomejog(jt.get(kk).getIdjogador1()) + ":" +
                    nomejog(jt.get(kk).getIdjogador2()));
            Log.i("resultcamp","gan"+(kk)+":"+nomejog(ganjog.get(kk))+":"+nomejog(perjog.get(kk)));
            kk++;
        }


//        for (int i=0;i<jt.size();i++){
//            Log.i("resultdata",
//                    " new:" + jt.get(i).getIdjogo()
//                            +":" + nomejog(jt.get(i).getIdjogador1())
//                            +":" + nomejog(jt.get(i).getIdjogador2())
//                            +":" +jt.get(i).getPlacar1()
//                            +":" +jt.get(i).getPlacar2());
//        }

        datatemp=jt;
        fechaDialog();
    }
    private void remonta61() {



//        dataarray = new ArrayList<>();
//        dataarray.clear();

        Log.i("inialerta","passat1:"+nomejog(passat1));
        Log.i("inialerta","passat2:"+nomejog(passat2));
        Log.i("inialerta","passat3:"+nomejog(passat3));
        Log.i("inialerta","passat4:"+nomejog(passat4));
        Log.i("inialerta","passat5:"+nomejog(passat5));
        Log.i("inialerta","passat6:"+nomejog(passat6));

        Log.i("inialerta","fazturno: " + fazturno);
        Log.i("inialerta","doisTurnos: " + doisTurnos);


        JogosTeste passa=new JogosTeste();
        jt = new ArrayList<>();
        jt.clear();
        int cont = 0;
        int retid=-1;
        int nturno=0;

        if(fazturno==1) {
            ganjog.clear();
            perjog.clear();
            nturno=0;
            jt.add(datatemp.get(0));
            jt.add(datatemp.get(1));
            jt.add(datatemp.get(2));
        }
        if(fazturno==2){
            nturno=15;
            retid = achalado(2, passat1, passat2);
            passa = datatemp.get(retid);
            passa.setIdjogo(cont+nturno);
            passa.setNumjog(String.valueOf(cont+1+nturno));
            jt.add(passa);
            cont++;

            retid = achalado(2, passat3, passat4);
            passa = datatemp.get(retid);
            passa.setIdjogo(cont+nturno);
            passa.setNumjog(String.valueOf(cont+1+nturno));
            jt.add(passa);
            cont++;
            retid = achalado(2, passat5, passat6);
            passa = datatemp.get(retid);
            passa.setIdjogo(cont+nturno);
            passa.setNumjog(String.valueOf(cont+1+nturno));
            jt.add(passa);
        }

        String res0[] = winnerJog(jt.get(0));
        String res1[] = winnerJog(jt.get(1));
        String res2[] = winnerJog(jt.get(2));

        cont=0;
        Log.i("buscajogo",(cont+nturno) +":" + nomejog( jt.get(cont).getIdjogador1()) + ":" + nomejog(jt.get(cont).getIdjogador2() )
                + ":encontrou:" + nomejog(jt.get(cont).getIdjogador1())+":"+nomejog(jt.get(cont).getIdjogador2())+":idjogo:" + cont);
       // Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res0[0])+":"+nomejog(res0[1]));
        cont++;
        Log.i("buscajogo",(cont+nturno)+":" + nomejog( jt.get(cont).getIdjogador1()) + ":" + nomejog(jt.get(cont).getIdjogador2() )
                + ":encontrou:" + nomejog(jt.get(cont).getIdjogador1())+":"+nomejog(jt.get(cont).getIdjogador2())+":idjogo:" + cont);
       // Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res1[0])+":"+nomejog(res1[1]));
        cont++;
        Log.i("buscajogo",(cont+nturno)+":" + nomejog( jt.get(cont).getIdjogador1()) + ":" + nomejog(jt.get(cont).getIdjogador2() )
                + ":encontrou:" + nomejog(jt.get(cont).getIdjogador1())+":"+nomejog(jt.get(cont).getIdjogador2())+":idjogo:" + cont);
       // Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res2[0])+":"+nomejog(res2[1]));

        cont = 3;
        retid = achalado(fazturno, res0[0], res1[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont+nturno);
        passa.setNumjog(String.valueOf(cont + 1 + nturno));
        jt.add(passa);
        Log.i("buscajogo",(cont+nturno) +":" + nomejog( res0[0]) + ":" + nomejog(res1[0] )
                + ":encontrou:" + nomejog(passa.getIdjogador1())+":"+nomejog(passa.getIdjogador2())+":idjogo:" + passa.getIdjogo());
        String res3[] = winnerJog(jt.get(3));
       // Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res3[0])+":"+nomejog(res3[1]));



        cont = 4;
        retid = achalado(fazturno, res2[0], res0[1]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont+nturno);
        passa.setNumjog(String.valueOf(cont + 1 + nturno));
        jt.add(passa);
        Log.i("buscajogo",(cont+nturno) +":" + nomejog( res2[0]) + ":" + nomejog(res0[1] )
                + ":encontrou:" + nomejog(passa.getIdjogador1())+":"+nomejog(passa.getIdjogador2())+":idjogo:" + passa.getIdjogo());
        String res4[] = winnerJog(jt.get(4));
       // Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res4[0])+":"+nomejog(res4[1]));

        cont = 5;
        retid = achalado(fazturno, res1[1], res2[1]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont+nturno);
        passa.setNumjog(String.valueOf(cont + 1 + nturno));
        jt.add(passa);

        Log.i("buscajogo",(cont+nturno) +":" + nomejog( res1[1]) + ":" + nomejog(res2[1] )
                + ":encontrou:" + nomejog(passa.getIdjogador1())+":"+nomejog(passa.getIdjogador2())+":idjogo:" + passa.getIdjogo());

        String res5[] = winnerJog(jt.get(5));
      //  Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res5[0])+":"+nomejog(res5[1]));

        cont = 6;
        retid = achalado(fazturno, res3[0], res2[0]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont+nturno);
        passa.setNumjog(String.valueOf(cont + 1 + nturno));
        jt.add(passa);
        Log.i("buscajogo",(cont+nturno) +":" + nomejog( res3[0]) + ":" + nomejog(res2[0] )
                + ":encontrou:" + nomejog(passa.getIdjogador1())+":"+nomejog(passa.getIdjogador2())+":idjogo:" + passa.getIdjogo());
        String res6[] = winnerJog(jt.get(6));
      //  Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res6[0])+":"+nomejog(res6[1]));

        cont = 7;
        retid = achalado(fazturno, res3[1], res2[1]);
        passa = datatemp.get(retid);
        passa.setIdjogo(cont+nturno);
        passa.setNumjog(String.valueOf(cont + 1 + nturno));
        jt.add(passa);
        Log.i("buscajogo",(cont+nturno) +":" + nomejog( res3[1]) + ":" + nomejog(res2[1] )
                + ":encontrou:" + nomejog(passa.getIdjogador1())+":"+nomejog(passa.getIdjogador2())+":idjogo:" + passa.getIdjogo());
        String res7[] = winnerJog(jt.get(7));
       // Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res7[0])+":"+nomejog(res7[1]));

        ArrayList<String> falta = new ArrayList();
        for (int i = 0; i < partCamp.size(); i++) {
            if (!partCamp.get(i).getId_jogador().equals(res3[0]) &&
                    !partCamp.get(i).getId_jogador().equals(res2[0]) &&
                    !partCamp.get(i).getId_jogador().equals(res3[1]) &&
                    !partCamp.get(i).getId_jogador().equals(res2[1])) {
                falta.add(partCamp.get(i).getId_jogador());
            }
        }

        cont = 8;
        retid = achalado(fazturno, falta.get(0), falta.get(1));
        passa = datatemp.get(retid);
        passa.setIdjogo(cont+nturno);
        passa.setNumjog(String.valueOf(cont + 1 + nturno));
        jt.add(passa);

        Log.i("buscajogo",(cont+nturno) +":" + nomejog( falta.get(0)) + ":" + nomejog( falta.get(1))
                + ":encontrou:" + nomejog(passa.getIdjogador1())+":"+nomejog(passa.getIdjogador2())+":idjogo:" + passa.getIdjogo());

       // Log.i("resultcamp","seleção"+(cont+nturno)+":"+nomejog(falta.get(0))+":"+nomejog(falta.get(1)));

        String res8[] = winnerJog(jt.get(8));
       // Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res8[0])+":"+nomejog(res8[1]));


        for (int i = 0; i < jt.size(); i++) {
            Log.i("jogosfaltaT","jt: "+nomejog(jt.get(i).getIdjogador1())+":"+nomejog(jt.get(i).getIdjogador2()));

        }



        ArrayList<String> feitosJT = new ArrayList<>();

        for (int i = 0; i < jt.size(); i++) {
            feitosJT.add(jt.get(i).getIdjogador1() + jt.get(i).getIdjogador2());
        }

        for (int i = 0; i < feitosJT.size(); i++) {
            Log.i("jogosfaltaT", "feitosJT: "+fazturno+" : " + feitosJT.get(i));
        }

        ArrayList<JogosTeste> jgfalta = new ArrayList<>();
        for (int i = 0; i < datatemp.size(); i++) {

                String jjog = datatemp.get(i).getIdjogador1() + datatemp.get(i).getIdjogador2();
                if (!feitosJT.contains(jjog)) {
                    if(datatemp.get(i).getTurno()==fazturno) {
                        jgfalta.add(datatemp.get(i));
                    }
                }

        }
        for (int i = 0; i < jgfalta.size(); i++) {
            Log.i("jogosfaltaT", "jgfalta Turno: "+fazturno+" : " + nomejog(jgfalta.get(i).getIdjogador1()) + ":" + nomejog(jgfalta.get(i).getIdjogador2()));
        }

        ArrayList<JogosTeste> faltajogargan6 = new ArrayList<>();
        for (int i = 0; i < jgfalta.size(); i++) {
            if (jgfalta.get(i).getIdjogador1().equals(res6[0]) || jgfalta.get(i).getIdjogador2().equals(res6[0])) {
                faltajogargan6.add(jgfalta.get(i));
            }
        }

        for(int i=0;i<faltajogargan6.size();i++){
            Log.i("faltajogart","faltajogarjog6gan: "+nomejog(faltajogargan6.get(i).getIdjogador1())+":"+nomejog(faltajogargan6.get(i).getIdjogador2()));
        }

        ArrayList<JogosTeste> faltajogarper6 = new ArrayList<>();
        for (int i = 0; i < jgfalta.size(); i++) {
            if (jgfalta.get(i).getIdjogador1().equals(res6[1]) || jgfalta.get(i).getIdjogador2().equals(res6[1])) {
                faltajogarper6.add(jgfalta.get(i));
            }
        }

        for(int i=0;i<faltajogarper6.size();i++){
            Log.i("faltajogart","faltajogarjog6per: "+nomejog(faltajogarper6.get(i).getIdjogador1())+":"+nomejog(faltajogarper6.get(i).getIdjogador2()));
        }

        String idvai="";
        JogosTeste jogofalta = new JogosTeste();
        String ct6="";
        boolean jogo9 = false;
        Log.i("jogo9","checou jogo gan1");
        if(faltajogargan6.get(0).getIdjogador1().equals(res6[0])){
            idvai=faltajogargan6.get(0).getIdjogador2();
        }else{
            idvai=faltajogargan6.get(0).getIdjogador1();
        }
        boolean pode9 = podejogo(idvai,jt.get(8));

        if(pode9){
            jogo9=true;
            jogofalta=faltajogargan6.get(0);
            ct6=res6[1];
        }


        if(!jogo9){
            Log.i("jogo9","checou jogo gan2");
            if(faltajogargan6.get(1).getIdjogador1().equals(res6[0])){
                idvai=faltajogargan6.get(1).getIdjogador2();
            }else{
                idvai=faltajogargan6.get(1).getIdjogador1();
            }
            pode9 = podejogo(idvai,jt.get(8));
            if(pode9){
                jogo9=true;
                jogofalta=faltajogargan6.get(1);
                ct6=res6[1];
            }
        }

        if(!jogo9){
            Log.i("jogo9","checou jogo per1");
            if(faltajogarper6.get(0).getIdjogador1().equals(res6[1])){
                idvai=faltajogarper6.get(0).getIdjogador2();
            }else{
                idvai=faltajogarper6.get(0).getIdjogador1();
            }
            pode9 = podejogo(idvai,jt.get(8));
            if(pode9){
                jogo9=true;
                jogofalta=faltajogarper6.get(0);
                ct6=res6[0];
            }
        }

        if(!jogo9){
            Log.i("jogo9","checou jogo per2");
            if(faltajogarper6.get(1).getIdjogador1().equals(res6[1])){
                idvai=faltajogarper6.get(1).getIdjogador2();
            }else{
                idvai=faltajogarper6.get(1).getIdjogador1();
            }
            pode9 = podejogo(idvai,jt.get(8));
            if(pode9){
                jogo9=true;
                jogofalta=faltajogarper6.get(1);
                ct6=res6[0];
            }
        }


//        if(jogo9){
//            Log.i("jogo9","ok");
//            //Log.i("resultcamp","seleção"+(cont+nturno)+":"+nomejog(jogofalta.getIdjogador1())+":"+nomejog(jogofalta.getIdjogador2()));
//        }else{
//            Log.i("jogo9","problema");
//        }

        cont = 9;
        passa = jogofalta;
        passa.setIdjogo(cont+nturno);
        passa.setNumjog(String.valueOf(cont + 1 + nturno));
        jt.add(passa);
        Log.i("buscajogo", (cont+nturno) +":" + nomejog( jogofalta.getIdjogador1()) + ":" + nomejog( jogofalta.getIdjogador2())
                + ":encontrou:" + nomejog(passa.getIdjogador1())+":"+nomejog(passa.getIdjogador2())+":idjogo:" + passa.getIdjogo());

        String res9[] = winnerJog(jt.get(9));
        //Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res9[0])+":"+nomejog(res9[1]));

        ArrayList<JogosTeste> jogosFaltajog = new ArrayList<>();
        jogosFaltajog.clear();

        String njj = "";
        for (int i = 0; i < partCamp.size(); i++) {
            if (!partCamp.get(i).getId_jogador().equals(ct6) &&
                    !partCamp.get(i).getId_jogador().equals(jt.get(8).getIdjogador1()) &&
                    !partCamp.get(i).getId_jogador().equals(jt.get(8).getIdjogador2()) &&
                    !partCamp.get(i).getId_jogador().equals(jt.get(9).getIdjogador1()) &&
                    !partCamp.get(i).getId_jogador().equals(jt.get(9).getIdjogador2())) {
                njj = partCamp.get(i).getId_jogador();
            }
        }
        Log.i("njj","n jogou: " + nomejog(njj));
        Log.i("njj","ct6: " + nomejog(ct6));
        njj=ct6;
       for (int i = 0; i < jgfalta.size(); i++) {
            if (jgfalta.get(i).getIdjogador1().equals(njj) || jgfalta.get(i).getIdjogador2().equals(njj)) {
                jogosFaltajog.add(jgfalta.get(i));
            }
        }

        for(int i=0;i<jogosFaltajog.size();i++){
            Log.i("njj","jogosFaltajog i: " +i+" : "+ nomejog(jogosFaltajog.get(i).getIdjogador1())+
                    " x "+nomejog(jogosFaltajog.get(i).getIdjogador2()));

        }


        Log.i("jogo10","checou jogo ct61");
        if(jogosFaltajog.get(0).getIdjogador1().equals(ct6)){
            idvai=jogosFaltajog.get(0).getIdjogador2();
        }else{
            idvai=jogosFaltajog.get(0).getIdjogador1();
        }
        boolean pode10x1 = podejogo(idvai,jt.get(9));

        String idvai2="";
        Log.i("jogo10","checou jogo ct62");
        if(jogosFaltajog.get(1).getIdjogador1().equals(ct6)){
            idvai2=jogosFaltajog.get(1).getIdjogador2();
        }else{
            idvai2=jogosFaltajog.get(1).getIdjogador1();
        }
        boolean pode10x2 = podejogo(idvai2,jt.get(9));
        JogosTeste jogoADV = new JogosTeste();


        if(pode10x2 && pode10x1){
            if(jt.get(7).getIdjogador1().equals(idvai) || jt.get(7).getIdjogador2().equals(idvai)){
                jogoADV=jogosFaltajog.get(0);
                Log.i("jogo10","ok os 2 mandou get0");
            }else{
                Log.i("jogo10","ok os 2 mandou get1");
                jogoADV=jogosFaltajog.get(1);
            }
        }else if(pode10x1){
            jogoADV=jogosFaltajog.get(0);
            Log.i("jogo10","ok get 0");
        }else if(pode10x2){
            jogoADV=jogosFaltajog.get(0);
            Log.i("jogo10","ok get 1");
        }else{
            Log.i("jogo10","prolema nenhum pode");
        }

        if(jogoADV.getIdjogador1()!=null){
          //  Log.i("resultcamp","seleção"+(cont+nturno)+":"+nomejog(jogoADV.getIdjogador1())+":"+nomejog(jogoADV.getIdjogador2()));
        }




        cont = 10;
        passa = jogoADV;
        passa.setIdjogo(cont+nturno);
        passa.setNumjog(String.valueOf(cont + 1 + nturno));
        jt.add(passa);
        Log.i("buscajogo", (cont+nturno) +":" + nomejog( jogoADV.getIdjogador1()) + ":" + nomejog( jogoADV.getIdjogador2())
                + ":encontrou:" + nomejog(passa.getIdjogador1())+":"+nomejog(passa.getIdjogador2())+":idjogo:" + passa.getIdjogo());

        String res10[] = winnerJog(jt.get(10));
       // Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res10[0])+":"+nomejog(res10[1]));


        feitosJT.clear();
        for (int i = 0; i < jt.size(); i++) {
            feitosJT.add(jt.get(i).getIdjogador1() + jt.get(i).getIdjogador2());
        }

        jgfalta.clear();
        for (int i = 0; i < datatemp.size(); i++) {

            String jjog = datatemp.get(i).getIdjogador1() + datatemp.get(i).getIdjogador2();
            if (!feitosJT.contains(jjog)) {
                if(datatemp.get(i).getTurno()==fazturno) {
                    jgfalta.add(datatemp.get(i));
                }
            }

        }
        for (int i = 0; i < jgfalta.size(); i++) {
            Log.i("jogosfalta2", "falta i : " + nomejog(jgfalta.get(i).getIdjogador1()) + ":" + nomejog(jgfalta.get(i).getIdjogador2()));
        }

        ArrayList<JogosTeste> faltagan8 = new ArrayList<>();
        ArrayList<JogosTeste> faltaper8 = new ArrayList<>();
        faltagan8.clear();
        faltaper8.clear();

        for(int i=0;i<jgfalta.size();i++){
            if(jgfalta.get(i).getIdjogador1().equals(res8[0]) || jgfalta.get(i).getIdjogador2().equals(res8[0])){
                faltagan8.add(jgfalta.get(i));
            }else{
                faltaper8.add(jgfalta.get(i));
            }
        }
        for (int i = 0; i < faltagan8.size(); i++) {
            Log.i("faltagan8", "faltagan8 : " + nomejog(faltagan8.get(i).getIdjogador1()) + ":" + nomejog(faltagan8.get(i).getIdjogador2()));
        }
        for (int i = 0; i < faltaper8.size(); i++) {
            Log.i("faltaper8", "faltaper8 : " + nomejog(faltaper8.get(i).getIdjogador1()) + ":" + nomejog(faltaper8.get(i).getIdjogador2()));
        }


        boolean jogo11 =false;
        boolean pode11 =false;

       idvai="";
       jogofalta = new JogosTeste();
       String ct8="";

        pode11 = false;
        Log.i("jogo11","checou jogo gan8 0");
        if(faltagan8.get(0).getIdjogador1().equals(res8[0])){
            idvai=faltagan8.get(0).getIdjogador2();
        }else{
            idvai=faltagan8.get(0).getIdjogador1();
        }
        pode11 = podejogo(idvai,jt.get(10));

        if(pode11){
            jogo11=true;
            jogofalta=faltagan8.get(0);
            ct8=res8[1];
        }


        if(!jogo11){
            Log.i("jogo11","checou jogo gan8 1");
            if(faltagan8.get(1).getIdjogador1().equals(res8[0])){
                idvai=faltagan8.get(1).getIdjogador2();
            }else{
                idvai=faltagan8.get(1).getIdjogador1();
            }
            pode11 = podejogo(idvai,jt.get(10));
            if(pode11){
                jogo11=true;
                jogofalta=faltagan8.get(1);
                ct8=res8[1];
            }
        }

        if(!jogo11){
            Log.i("jogo11","checou jogo per8 0");
            if(faltaper8.get(0).getIdjogador1().equals(res8[1])){
                idvai=faltaper8.get(0).getIdjogador2();
            }else{
                idvai=faltaper8.get(0).getIdjogador1();
            }
            pode11 = podejogo(idvai,jt.get(10));
            if(pode11){
                jogo11=true;
                jogofalta=faltaper8.get(0);
                ct8=res8[0];
            }
        }

        if(!jogo11){
            Log.i("jogo11","checou jogo per8 1");
            if(faltaper8.get(1).getIdjogador1().equals(res8[1])){
                idvai=faltaper8.get(1).getIdjogador2();
            }else{
                idvai=faltaper8.get(1).getIdjogador1();
            }
            pode11 = podejogo(idvai,jt.get(10));
            if(pode11){
                jogo11=true;
                jogofalta=faltaper8.get(1);
                ct8=res8[0];
            }
        }

        cont = 11;
        passa = jogofalta;
        passa.setIdjogo(cont+nturno);
        passa.setNumjog(String.valueOf(cont + 1 + nturno));
        jt.add(passa);
        Log.i("buscajogo",(cont+nturno) +":" + nomejog( jogofalta.getIdjogador1()) + ":" + nomejog( jogofalta.getIdjogador2())
                + ":encontrou:" + nomejog(passa.getIdjogador1())+":"+nomejog(passa.getIdjogador2())+":idjogo:" + passa.getIdjogo());

        if(jogo11){
            Log.i("jogo11","ok");
           // Log.i("resultcamp","seleção"+(cont+nturno)+":"+nomejog(jogofalta.getIdjogador1())+":"+nomejog(jogofalta.getIdjogador2()));
        }else{
            Log.i("jogo11","problema");
        }
        String res11[] = winnerJog(jt.get(11));
       // Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res11[0])+":"+nomejog(res11[1]));
        Log.i("jogo12","ct8 "+ nomejog(ct8));
        ArrayList<JogosTeste> jgct8 = new ArrayList<>();
        if(ct8.equals(res8[1])){
            jgct8=faltaper8;
        }else{
            jgct8=faltagan8;
        }

        for (int i = 0; i < jgct8.size(); i++) {
            Log.i("jogo12", "jgct8 : " + nomejog(jgct8.get(i).getIdjogador1()) + ":" + nomejog(jgct8.get(i).getIdjogador2()));
        }

        JogosTeste ttx1 = new JogosTeste();
        JogosTeste ttx2 = new JogosTeste();

        String adv ="";

        if(jgct8.get(0).getIdjogador1().equals(ct8)){
            adv=jgct8.get(0).getIdjogador2();
        }else {
            adv = jgct8.get(0).getIdjogador1();
        }

        if(adv.equals(res9[0]) || adv.equals(res9[1])) {
            ttx1=jgct8.get(0);
            ttx2=jgct8.get(1);

        }else{
            ttx1=jgct8.get(1);
            ttx2=jgct8.get(0);
        }

        boolean jogo12 =false;
        boolean pode12 =false;

        idvai="";
        jogofalta = new JogosTeste();



        Log.i("jogo12","checou jogo ttx1");
        if(ttx1.getIdjogador1().equals(ct8)){
            idvai=ttx1.getIdjogador2();
        }else{
            idvai=ttx1.getIdjogador1();
        }
        pode12 = podejogo(idvai,jt.get(11));

        if(pode12){
            jogo12=true;
            jogofalta=ttx1;
        }

        Log.i("jogo12","ttx1:"+nomejog(ttx1.getIdjogador1())+":"+nomejog(ttx1.getIdjogador2()));
        Log.i("jogo12","ttx2:"+nomejog(ttx2.getIdjogador1())+":"+nomejog(ttx2.getIdjogador2()));
        if(!jogo12){
            Log.i("jogo12","checou jogo ct8 1");
            if(ttx2.getIdjogador1().equals(ct8)){
                idvai=ttx2.getIdjogador2();
            }else{
                idvai=ttx2.getIdjogador1();
            }
            pode12 = podejogo(idvai,jt.get(11));
            if(pode12){
                jogo12=true;
                jogofalta=ttx2;
            }
        }



        cont = 12;
        passa = jogofalta;
        passa.setIdjogo(cont+nturno);
        passa.setNumjog(String.valueOf(cont + 1 + nturno));
        jt.add(passa);
        Log.i("buscajogo",(cont+nturno) +":" + nomejog( jogofalta.getIdjogador1()) + ":" + nomejog( jogofalta.getIdjogador2())
                + ":encontrou:" + nomejog(passa.getIdjogador1())+":"+nomejog(passa.getIdjogador2())+":idjogo:" + passa.getIdjogo());

        String res12[] = winnerJog(jt.get(12));
       // Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res12[0])+":"+nomejog(res12[1]));
//        if(jogo12){
//            Log.i("jogo12","ok");
//         //   Log.i("resultcamp","seleção"+(cont+nturno)+":"+nomejog(jogofalta.getIdjogador1())+":"+nomejog(jogofalta.getIdjogador2()));
//        }else{
//            Log.i("jogo12","problema");
//        }


        ArrayList<String> falta13 = new ArrayList<>();
        falta13.add(res10[0]);
        falta13.add(res10[1]);
        falta13.add(res11[0]);
        falta13.add(res11[1]);
        falta13.add(res12[0]);
        falta13.add(res12[1]);

        String  ult= "";
        for (int i = 0; i < partCamp.size(); i++) {
            if (!falta13.contains(partCamp.get(i).getId_jogador())) {
                ult = partCamp.get(i).getId_jogador();
            }
        }

        Log.i("jogo13"," faltou: "+ult);

        feitosJT.clear();
        for (int i = 0; i < jt.size(); i++) {
            feitosJT.add(jt.get(i).getIdjogador1() + jt.get(i).getIdjogador2());
        }

        jgfalta.clear();
        for (int i = 0; i < datatemp.size(); i++) {

            String jjog = datatemp.get(i).getIdjogador1() + datatemp.get(i).getIdjogador2();
            if (!feitosJT.contains(jjog)) {
                if(datatemp.get(i).getTurno()==fazturno) {
                    jgfalta.add(datatemp.get(i));
                }
            }

        }
        for (int i = 0; i < jgfalta.size(); i++) {
            Log.i("jogosfalta3", "falta i : " + nomejog(jgfalta.get(i).getIdjogador1()) + ":" + nomejog(jgfalta.get(i).getIdjogador2()));
        }

        JogosTeste jogosobra = new JogosTeste();

        if(jgfalta.get(0).getIdjogador1().equals(ct8) || jgfalta.get(0).getIdjogador2().equals(ct8) ){
            jogofalta=jgfalta.get(1);
            jogosobra=jgfalta.get(0);
        }else{
            jogofalta=jgfalta.get(0);
            jogosobra=jgfalta.get(1);
        }



        cont = 13;
        passa = jogofalta;
        passa.setIdjogo(cont+nturno);
        passa.setNumjog(String.valueOf(cont + 1 + nturno));
        jt.add(passa);
        Log.i("buscajogo",(cont+nturno) +":" + nomejog( jogofalta.getIdjogador1()) + ":" + nomejog( jogofalta.getIdjogador2())
                + ":encontrou:" + nomejog(passa.getIdjogador1())+":"+nomejog(passa.getIdjogador2())+":idjogo:" + passa.getIdjogo());
       // Log.i("resultcamp","seleção"+(cont+nturno)+":"+nomejog(jogofalta.getIdjogador1())+":"+nomejog(jogofalta.getIdjogador2()));
        String res13[] = winnerJog(jt.get(13));
       // Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res13[0])+":"+nomejog(res13[1]));

        cont = 14;
        passa = jogosobra;
        passa.setIdjogo(cont+nturno);
        passa.setNumjog(String.valueOf(cont + 1 + nturno));
        jt.add(passa);
        Log.i("buscajogo", (cont+nturno) +":" + nomejog( jogosobra.getIdjogador1()) + ":" + nomejog( jogosobra.getIdjogador2())
                + ":encontrou:" + nomejog(passa.getIdjogador1())+":"+nomejog(passa.getIdjogador2())+":idjogo:" + passa.getIdjogo());
       // Log.i("resultcamp","seleção"+(cont+nturno)+":"+nomejog(jogosobra.getIdjogador1())+":"+nomejog(jogosobra.getIdjogador2()));
        String res14[] = winnerJog(jt.get(14));
        //Log.i("resultcamp","gan"+(cont+nturno)+":"+nomejog(res14[0])+":"+nomejog(res14[1]));


        if(fazturno==1){
            jgfim.clear();
            jgfim.addAll(jt);
        }
        if(fazturno==2){
//            jgfim.addAll(jtTurno1);
            for(int i=0;i<jt.size();i++){
                jt.get(i).setNumjog(String.valueOf(i+15+1));
                jt.get(i).setIdjogo(i+15);
                jgfim.add(jt.get(i));
            }

        }

        ArrayList<String> jjfim=new ArrayList<>();
        jjfim.clear();

        for(int i=0;i<partCamp.size();i++){
            if(!partCamp.get(i).getId_jogador().equals(res12[0]) &&
                    !partCamp.get(i).getId_jogador().equals(res12[1]) &&
                    !partCamp.get(i).getId_jogador().equals(res13[0]) &&
                    !partCamp.get(i).getId_jogador().equals(res13[1]) &&
                    !partCamp.get(i).getId_jogador().equals(res14[0]) &&
                    !partCamp.get(i).getId_jogador().equals(res14[1])
            ){
                jjfim.add(partCamp.get(i).getId_jogador());
            }
        }
        Log.i("jjfim","jjfimsize: " + jjfim.size());
        for(int i=0;i<jjfim.size();i++){
        Log.i("jjfim","jjfim: i "+i+" : " + nomejog(jjfim.get(i)));
        }


        if(doisTurnos){

            switch (jjfim.size()){
                case 1:
                    passat1 = jjfim.get(0);
                    if(!res12[0].equals(res14[0])&&!res12[0].equals(res14[1])) {
                        passat2 = res12[0];
                        passat3 = res12[1];
                    }else if(!res12[1].equals(res14[0])&&!res12[1].equals(res14[1])){
                        passat2 = res12[1];
                        passat3 = res12[0];
                    }

                    passat4 = res13[0];
                    passat5 = res13[1];

                    if(jt.get(12).getIdjogador1().equals(res14[0])||jt.get(12).getIdjogador2().equals(res14[0])){
                        passat6 = res14[1];
                    }else{
                        passat6 = res14[0];
                    }
                    break;
                case 0:
                    passat1 = res12[0];
                    passat2 = res13[0];

                    passat3 = res14[0];
                    passat4 = res12[1];

                    passat5 = res13[1];
                    passat6 = res14[1];
                    break;
            }

            remonta62();
        }


        if(!doisTurnos) {
            int kk = 0;
            while (kk < jgfim.size()) {
                Log.i("resultcamp", "jt" + jgfim.get(kk).getIdjogo() + ":" + nomejog(jgfim.get(kk).getIdjogador1()) + ":" +
                        nomejog(jgfim.get(kk).getIdjogador2()));
                Log.i("resultcamp","gan"+(kk)+":"+nomejog(ganjog.get(kk))+":"+nomejog(perjog.get(kk)));
                kk++;
            }
            datatemp=jgfim;
            fechaDialog();
        }


    }
    private void remonta62() {
        fazturno=2;
        doisTurnos=false;
        remonta61();
    }

    public boolean podejogo(String idJog,JogosTeste verify){
        boolean podej = false;
            if(!idJog.equals(verify.getIdjogador1()) &&
                    !idJog.equals(verify.getIdjogador2())){
                podej=true;
            }
        return podej;
    }

    public String [] winnerJog(JogosTeste jt){
        String winner="AAA";
        String loser="AAA";
        int pl1 = jt.getPlacar1().equals("_")? 0:Integer.parseInt(jt.getPlacar1());
        int pl2 = jt.getPlacar2().equals("_")? 0:Integer.parseInt(jt.getPlacar2());
        if (pl1 == pl2) {
            int sorteio2 = util.sorteia();
            if(sorteio2==-1){
                winner = jt.getIdjogador1();
                loser = jt.getIdjogador2();
            }else{
                winner = jt.getIdjogador2();
                loser = jt.getIdjogador1();
            }
        } else if (pl1 > pl2) {
            winner = jt.getIdjogador1();
            loser = jt.getIdjogador2();
        } else {
            winner = jt.getIdjogador2();
            loser = jt.getIdjogador1();
        }

        ganjog.add(winner);
        perjog.add(loser);
        String[] retwin = new String[]{winner,loser};
        return retwin;
    }

    public int achalado(int turno, String jogA, String jogB){
        String ladoA="ladoab";
        String ladoB="ladoab";
        String achouA="achouAB";
        String achouB="achouAB";
        String jogoFeito = jogA+jogB;
        String jogoInvert =jogB+jogA;
        int idjogo =-1;

        for(int i=0;i<datatemp.size();i++){

            if(datatemp.get(i).getTurno()==turno){

                String play = datatemp.get(i).getIdjogador1() + datatemp.get(i).getIdjogador2();

                if (play.equals(jogoFeito)){
                    achouA=datatemp.get(i).getIdjogador1();
                    achouB=datatemp.get(i).getIdjogador2();
                    ladoA=datatemp.get(i).getIdjogador1();
                    ladoB=datatemp.get(i).getIdjogador2();
                    idjogo=i;
                }

                if (play.equals(jogoInvert)){
                    achouA=datatemp.get(i).getIdjogador1();
                    achouB=datatemp.get(i).getIdjogador2();
                    ladoA=datatemp.get(i).getIdjogador2();
                    ladoB=datatemp.get(i).getIdjogador1();
                    idjogo=i;
                }
            }
        }
        Log.i("achalado","procurou turno: "+turno+":"+ nomejog(jogA) +":"+nomejog(jogB)+":achou idjogo: " + idjogo +":" +
                nomejog(achouA)+":"+nomejog(achouB)+":"+nomejog(ladoA) + ":" + nomejog(ladoB));
                String[] retlado = new String[]{ladoA,ladoB};
        return idjogo;
    }

    public JogosTeste verijog (int turno,int idjogo, String jogA,String jogB){

        JogosTeste vjog = new JogosTeste();
//        String idliga = datatemp.get(0).getKeyliga();
//        String idcamp = datatemp.get(0).getKeycampeonato();
//        vjog.setKeycampeonato(idcamp);
//        vjog.setKeyliga(idliga);
//        vjog.setTurno(turno);
//        vjog.setIdjogo(idjogo);
//        vjog.setNumjog(String.valueOf(idjogo+1));
//        vjog.setIdjogador1(jogA);
//        vjog.setIdjogador2(jogB);
//        vjog.setStatusjogo(0);

        for(int i=0;i<jt.size();i++) {
            String play = jt.get(i).getIdjogador1() + jt.get(i).getIdjogador2();
            if (play.equals(jogA+jogB)){
//                vjog.setPlacar1(datatemp.get(i).getPlacar1());
//                vjog.setPlacar2(datatemp.get(i).getPlacar2());
//                vjog.setPenalti1(datatemp.get(i).getPenalti1());
//                vjog.setPenalti2(datatemp.get(i).getPenalti2());
//                vjog.setStatusjogo(datatemp.get(i).getStatusjogo());
                vjog=jt.get(i);
            }

        }

        return vjog;
    }

    public JogosTeste achaJogo(int turno,int idjogo, String jogA,String jogB){
        JogosTeste ajog = new JogosTeste();
        String jogoFeito = jogA+jogB;
        String jogoInvert =jogB+jogA;

        for(int i=0;i<datatemp.size();i++) {
            if(datatemp.get(i).getTurno()==turno){
                String play = datatemp.get(i).getIdjogador1() + datatemp.get(i).getIdjogador2();
                if (play.equals(jogoFeito) ){
                    ajog=datatemp.get(i);
                    ajog.setNumjog(String.valueOf(idjogo+1));
                    ajog.setIdjogo(idjogo);
                }
                if (play.equals(jogoInvert)){
                    ajog=datatemp.get(i);
                    ajog.setNumjog(String.valueOf(idjogo+1));
                    ajog.setIdjogo(idjogo);
                }
            }
        }
        return ajog;
    }

    public String [] naoJogouContra(int turno, int jogosolic,int jogoverif,String idjog){

        String jogou="";
        String nãojogou="";
        boolean jogou1 = false;
        boolean jogou2 = false;
        String jogoFeito = idjog+jt.get(jogoverif).getIdjogador1();
        String jogoInvert =jt.get(jogoverif).getIdjogador1()+idjog;

        for(int i=0;i<jogosolic;i++){
            if(jt.get(i).getTurno()==turno){
                String play=jt.get(i).getIdjogador1()+jt.get(i).getIdjogador2();
                if(play.equals(jogoFeito)){
                    jogou1 = true;
                }
                if(play.equals(jogoInvert)){
                    jogou1=true;
                }
            }
        }

        jogoFeito = idjog+jt.get(jogoverif).getIdjogador2();
        jogoInvert =jt.get(jogoverif).getIdjogador2()+idjog;
        for(int i=0;i<jogosolic;i++){
            if(jt.get(i).getTurno()==turno){
                String play=jt.get(i).getIdjogador1()+jt.get(i).getIdjogador2();
                if(play.equals(jogoFeito)){
                    jogou2 = true;
                }
                if(play.equals(jogoInvert)){
                    jogou2=true;
                }
            }
        }

        if(jogou1 && !jogou2){
            nãojogou=jt.get(jogoverif).getIdjogador2();
            jogou=jt.get(jogoverif).getIdjogador1();
            Log.i("winnerjog",jogosolic+" nao jogou jog2: " + nomejog(jt.get(jogoverif).getIdjogador2()));
        }
        if(!jogou1 && jogou2){
            nãojogou=jt.get(jogoverif).getIdjogador1();
            jogou=jt.get(jogoverif).getIdjogador2();
            Log.i("winnerjog",jogosolic+" nao jogou jog1: " + nomejog(jt.get(jogoverif).getIdjogador1()));
        }

        if(jogou1 && jogou2){
            Log.i("winnerjog",jogosolic+" problema os dois:");
        }
        if(!jogou1 && !jogou2){
            int sorteio2 = util.sorteia();
            if(sorteio2==-1){
                nãojogou=jt.get(jogoverif).getIdjogador1();
                jogou=jt.get(jogoverif).getIdjogador2();
            }else{
                nãojogou=jt.get(jogoverif).getIdjogador2();
                jogou=jt.get(jogoverif).getIdjogador1();
            }
            Log.i("winnerjog",jogosolic+" problema nenhum:sorteio: " + nomejog(nãojogou)+" jogou: "+nomejog(jogou));
        }



        String[] retNjogou = new String[]{nãojogou,jogou};
        return retNjogou;
    }

    private String [] naoJogoudojogo(int idjogo,String jogjog,int turno) {

        int inibusca = 0;
        int fimbusca = 0;

        switch (tipoturno) {
            case 1:
                inibusca = 0;
                fimbusca = jt.size();
                break;
            case 3:
                inibusca = 0;
                fimbusca = jt.size();
                break;
            case 2:
                if (turno == 1) {
                    inibusca = 0;
                    fimbusca = jt.size()/2;
                } else {
                    inibusca = jt.size()/2;
                    fimbusca = jt.size();
                }

                break;
            case 4:
                if (turno == 1) {
                    inibusca = 0;
                    fimbusca = jt.size()/2;
                } else {
                    inibusca = jt.size()/2;
                    fimbusca = jt.size();
                }

                break;
        }

        String jogou="";
        String nãojogou="";
        //chico & sergio

        boolean jogou1 = false;
        boolean jogou2 = false;

        String jogoFeito = jogjog+jt.get(idjogo).getIdjogador1();
        String jogoInvert =jt.get(idjogo).getIdjogador1()+jogjog;
        for(int i=inibusca;i<fimbusca;i++){
            if(jt.get(i).getTurno()==turno){
                String play=jt.get(i).getIdjogador1()+jt.get(i).getIdjogador2();
                if(play.equals(jogoFeito)){
                    jogou1 = true;
                }
                if(play.equals(jogoInvert)){
                   jogou1=true;
                }
            }
        }
        jogoFeito = jogjog+jt.get(idjogo).getIdjogador2();
        jogoInvert =jt.get(idjogo).getIdjogador2()+jogjog;
        for(int i=inibusca;i<idjogo;i++){
            if(jt.get(i).getTurno()==turno){
                String play=jt.get(i).getIdjogador1()+jt.get(i).getIdjogador2();
                if(play.equals(jogoFeito)){
                    jogou2 = true;
                }
                if(play.equals(jogoInvert)){
                    jogou2=true;
                }
            }
        }
        if(jogou1 && !jogou2){
            nãojogou=jt.get(idjogo).getIdjogador2();
            jogou=jt.get(idjogo).getIdjogador1();
        }
        if(!jogou1 && jogou2){
            nãojogou=jt.get(idjogo).getIdjogador1();
            jogou=jt.get(idjogo).getIdjogador2();
        }

        if(jogou1 && jogou2){
            Log.i("dataset","problema os dois:");
        }
        if(!jogou1 && !jogou2){
            int sorteio2 = util.sorteia();
            if(sorteio2==-1){
                nãojogou=jt.get(idjogo).getIdjogador1();
                jogou=jt.get(idjogo).getIdjogador2();
            }else{
                nãojogou=jt.get(idjogo).getIdjogador1();
                jogou=jt.get(idjogo).getIdjogador2();
            }
            Log.i("dataset","problema nenhum:");
        }

        String[] retwin = new String[]{nãojogou,jogou};
        return retwin;
    }

    private String naojogou5P(int idjogo) {
        String njog="";
        ArrayList<String> jogs = new ArrayList<>();
        jogs.add(jt.get(idjogo-2).getIdjogador1());
        jogs.add(jt.get(idjogo-2).getIdjogador2());
        jogs.add(jt.get(idjogo-1).getIdjogador1());
        jogs.add(jt.get(idjogo-1).getIdjogador2());

        for (int i=0;i<partCamp.size();i++){
            if(!jogs.contains(partCamp.get(i).getId_jogador())){
                njog=partCamp.get(i).getId_jogador();
            }

        }
        return njog;
    }

    public String nomejog(String id){
        String nome= "www";
        Log.i("partcamp",": " + partCamp.size());
        for(int i=0;i<partCamp.size();i++){
            if(partCamp.get(i).getId_jogador().equals(id)){
                nome=partCamp.get(i).getNome_jogador();
            }
        }
        return nome;
    }

    private void fechaDialog() {

        Jogos_Dataset_Controller jc = new Jogos_Dataset_Controller(getContext());
        jc.criarTabela(Jogos_Dataset_DataModel.criarTabela());
            for (int i = 0; i < datatemp.size(); i++) {
                Log.i("jogosbanco","getId "+datatemp.get(i).getId() + " Numjog: " + datatemp.get(i).getIdjogo()
                        + " : " + nomejog(datatemp.get(i).getIdjogador1()) + " X " +nomejog(datatemp.get(i).getIdjogador2()));
                jc.updateJogo_Dataset(datatemp.get(i),String.valueOf(datatemp.get(i).getId()),datatemp.get(i).getKeycampeonato());
            }
        Log.i("jogosbanco"," ");

            if(HomeFragment.LIGA_ON.getTipoliga()==1){

                for(int i=0;i<datatemp.size();i++) {
                    FirebaseFirestore.getInstance().collection("/ligas")
                            .document(HomeFragment.LIGA_ON.getKeyliga())
                            .collection("campeonatoativo")
                            .document(datatemp.get(0).getKeycampeonato())
                            .collection("dataset")
                            .document(
                                    String.valueOf(datatemp.get(i).getIdjogo())
                            )
                            .set(datatemp.get(i))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                }

            }





        Classificacao.ctrlJogo=0;
        String[] vai = {"", ""};
       // Classificacao.verifin=true;
        ncViewModel.setFinalistas(vai);

        int idjogoatual=0;
        if(idjogoatual<datatemp.size()){
            for(int i=0;i<datatemp.size();i++){
                if(datatemp.get(i).getStatusjogo()==0){
                    Classificacao.idjogoatual=datatemp.get(i).getIdjogo();
                    break;
                }
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
        if(getShowsDialog()) {
            Objects.requireNonNull(getDialog()).dismiss();
        }
    }

}

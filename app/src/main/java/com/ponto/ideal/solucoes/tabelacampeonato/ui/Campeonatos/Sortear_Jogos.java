package com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Campeonatos_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Jogos_Dataset_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Campeonato_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Jogos_Dataset_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JCampeonato;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_Inscritos;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JogosTeste;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Participantes;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.home.HomeFragment;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;

import java.util.ArrayList;
import java.util.Collections;


public class Sortear_Jogos extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private NovoCampeonatoViewModel ncViewModel;
    private ArrayList<Jogadores_Inscritos> jogadores_inscritos = new ArrayList<>();
    private CardView cvj1,cvj2,cvj3,cvmanual;
    private ImageView imgj1p1,imgj1p2,imgj2p1,imgj2p2,imgj3p1,imgj3p2;
    private TextView txtj1p1,txtj1p2,txtj2p1,txtj2p2,txtj3p1,txtj3p2,txttitle,txtmanual;
    private Button btavancar,btsortear;
    private ConstraintLayout clsortear;
    private LinearLayout lljog1,lljog2,lljog3,lljog4,lljog5,lljog6;
    private Switch swmanual;
    private ProgressBar mprogressBar;
    private ArrayList<Bitmap> listimg;
    private ArrayList<String> listnome;
    private ArrayList<String> listid;
    private ArrayList<Integer> listnum;
    private int mili;
    private int cont;
    private int taxa;
    private int tipoturno;

    private boolean bolBotao=false;
    private boolean bolManual=false;
    private boolean jogsel=false;
    private Integer destino;
    private Integer origem;
    private Integer indexorigem;
    private Integer indexdestino;

    private ObjectAnimator abreCv;

    public static boolean sorteia=false;
    private boolean anima=false;
    private ArrayList<Participantes> partCamp;

    public Sortear_Jogos() {

    }

    public static Sortear_Jogos newInstance(String param1, String param2) {
        Sortear_Jogos fragment = new Sortear_Jogos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sortear__jogos, container, false);
        initViews(v);

        return v;
    }

    private void initViews(View v) {

        ncViewModel = ViewModelProviders.of(this).get(NovoCampeonatoViewModel.class);

        tipoturno=NovoCampeonatoViewModel.getTipoturno();
        jogadores_inscritos= NovoCampeonatoViewModel.getJogadores_inscritos();
        abreCv= ObjectAnimator.ofFloat(cvmanual, "scaleX", 0f,1f);
        abreCv.setDuration(1500);



        listimg = new ArrayList<>();
        listnome = new ArrayList<>();
        listid = new ArrayList<>();
        listnum = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            listid.add("123456");
            listnome.add("Jogador");
            Drawable drawable = getResources().getDrawable(R.drawable.fotoblank);
            Bitmap bitmap = util.drawableToBitmap(drawable);
            listimg.add(bitmap);
        }



        if(jogadores_inscritos!=null) {

            for (int i = 0; i < jogadores_inscritos.size(); i++) {
                Log.i("listnum"," abertura: "+ jogadores_inscritos.get(i).getIdins());
                listnum.add(i);
                listid.set(i,jogadores_inscritos.get(i).getIdins());
                listnome.set(i,jogadores_inscritos.get(i).getNomeins());
                Bitmap bitmap = util.loadImageBitmap(getContext(), jogadores_inscritos.get(i).getIdins());
                listimg.set(i,bitmap);
            }
            for(int i=0;i<listid.size();i++) {
                Log.i("listnum", " listid: " + listid.get(i));
            }
            Log.i("listnum"," ");


        }
            mprogressBar = v.findViewById(R.id.mprogressBar);
        clsortear = v.findViewById(R.id.clsortear);
        cvj1 = v.findViewById(R.id.cvj1);
        cvj2 = v.findViewById(R.id.cvj2);
        cvj3 = v.findViewById(R.id.cvj3);
        cvmanual = v.findViewById(R.id.cvmanual);
        imgj1p1 = v.findViewById(R.id.imgj1p1);
        imgj1p2 = v.findViewById(R.id.imgj1p2);
        imgj2p1 = v.findViewById(R.id.imgj2p1);
        imgj2p2 = v.findViewById(R.id.imgj2p2);
        imgj3p1 = v.findViewById(R.id.imgj3p1);
        imgj3p2 = v.findViewById(R.id.imgj3p2);;
        txtj1p1 = v.findViewById(R.id.txtj1p1);
        txtj1p2 = v.findViewById(R.id.txtj1p2);
        txtj2p1 = v.findViewById(R.id.txtj2p1);
        txtj2p2 = v.findViewById(R.id.txtj2p2);
        txtj3p1 = v.findViewById(R.id.txtj3p1);
        txtj3p2 = v.findViewById(R.id.txtj3p2);
        lljog1 = v.findViewById(R.id.lljog1);
        lljog2 = v.findViewById(R.id.lljog2);
        lljog3 = v.findViewById(R.id.lljog3);
        lljog4 = v.findViewById(R.id.lljog4);
        lljog5 = v.findViewById(R.id.lljog5);
        lljog6 = v.findViewById(R.id.lljog6);
        btavancar = v.findViewById(R.id.btavancar);
        btsortear = v.findViewById(R.id.btsortear);
        swmanual = v.findViewById(R.id.swmanual);
        txttitle = v.findViewById(R.id.txttitle);
        txtmanual = v.findViewById(R.id.txtmanual);

        if(jogadores_inscritos.size()<5)cvj3.setVisibility(View.INVISIBLE);


        btavancar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                util.vibratePhone(getContext(), (short) 30);

                partCamp = new ArrayList<>();
                partCamp.clear();
                ArrayList<Bitmap> fimg = new ArrayList<>();
                fimg.clear();
                long timecamp = System.currentTimeMillis();
                String keycamp = String.valueOf(timecamp);


                for (int i = 0; i < jogadores_inscritos.size(); i++) {
                    Participantes part = new Participantes();
                    part.setId_campeonato(keycamp);
                    part.setId_liga(HomeFragment.LIGA_ON.getKeyliga());
                    part.setId_jogador(listid.get(i));
                    part.setNome_jogador(listnome.get(i));
                    part.setIdimg(i);
                    part.setClass_jogador(0);
                    part.setJogos(0);
                    part.setVitorias(0);
                    part.setDerrotas(0);
                    part.setEmpates(0);
                    part.setGolspro(0);
                    part.setGolscontra(0);
                    part.setPontos(0);
                    partCamp.add(part);

                    fimg.add(listimg.get(i));

                }
                NovoCampeonatoViewModel.setCampPart(partCamp);
                NovoCampeonatoViewModel.setBitimgjog(fimg);

                NovoCampeonatoViewModel.setA(listid.get(listnum.get(0)));
                NovoCampeonatoViewModel.setB(listid.get(listnum.get(1)));
                NovoCampeonatoViewModel.setC(listid.get(listnum.get(2)));
                if (jogadores_inscritos.size() > 3) {
                    NovoCampeonatoViewModel.setD(listid.get(listnum.get(3)));
                }
                if (jogadores_inscritos.size() > 4) {
                    NovoCampeonatoViewModel.setE(listid.get(listnum.get(4)));
                }
                if (jogadores_inscritos.size() > 5) {
                    NovoCampeonatoViewModel.setF(listid.get(listnum.get(5)));
                }

                ncViewModel.setTempPart(null);
                Classificacao.listajogos.clear();
                Classificacao.ctrlJogo = 0;
                Classificacao.idjogoatual = 0;
                Classificacao.jogoFinal = null;
                NovoCampeonatoViewModel.setBooleanfinal(false);
                NovoCampeonatoViewModel.setBooleanSalvarCamp(false);

                ArrayList<JogosTeste> jt = new MontaJogos(keycamp, ncViewModel).jogos();
                NovoCampeonatoViewModel.setDataset(null);

                Jogos_Dataset_Controller jc = new Jogos_Dataset_Controller(getContext());
                jc.criarTabela(Jogos_Dataset_DataModel.criarTabela());
                for (int i = 0; i < jt.size(); i++) {
                    Log.i("jogosbanco", "salvarDataset: " + jt.get(i).getIdjogo());
                    jc.salvarDataset(jt.get(i));
                }
                Log.i("jogosbanco", " ");
                for (int i = 0; i < jt.size(); i++) {
                    Log.i("jogosbanco", "jt getId " + jt.get(i).getId() + " Numjog: " + jt.get(i).getIdjogo()
                            + " : " + nomejog(jt.get(i).getIdjogador1()) + " X " + nomejog(jt.get(i).getIdjogador2()));
                }
                Log.i("jogosbanco", ": " + jt.size());


                ArrayList<JogosTeste> jogosbanco = jc.listarDatasetCamp(keycamp, HomeFragment.LIGA_ON.getKeyliga());
                for (int i = 0; i < jogosbanco.size(); i++) {
                    jt.get(i).setId(jogosbanco.get(i).getId());
                }
                NovoCampeonatoViewModel.setDataset(jt);

                ArrayList<JogosTeste> pri2 = new ArrayList<>();
                for (int i = 0; i < jt.size(); i++) {
                    if (jt.get(i).getTurno() == 1) {
                        pri2.add(jt.get(i));
                    }
                }
                NovoCampeonatoViewModel.setPrimeiroturno(pri2);
                ArrayList<JogosTeste> seg2 = new ArrayList<>();
                for (int i = 0; i < jt.size(); i++) {
                    if (jt.get(i).getTurno() == 2) {
                        seg2.add(jt.get(i));
                    }
                }
                NovoCampeonatoViewModel.setSegundoturno(seg2);


                StringBuilder str = null;
                str = new StringBuilder();
                for (int i = 0; i < listnum.size(); i++) {

                    Log.i("campativo", "nomesort: " + nomejog(listid.get(listnum.get(i))));
                    str.append(listid.get(listnum.get(i)) + "#");
                }
                String result = str.toString();
                JCampeonato ncamp = new JCampeonato();
                ncamp.setIdcampeonato(keycamp);
                ncamp.setIdliga(HomeFragment.LIGA_ON.getKeyliga());
                ncamp.setNumpartcamp(NovoCampeonatoViewModel.getNumpart());
                ncamp.setTipoturnocamp(tipoturno);
                ncamp.setNumerocamp(3);
                ncamp.setStatuscamp(1);
                ncamp.setTimestamp(timecamp);
                ncamp.setParticipantesCamp(result);

                Campeonatos_Controller cc = new Campeonatos_Controller(getContext());
                cc.criarTabela(Campeonato_DataModel.criarTabela());
                boolean scamp = cc.salvarCampeonatofixo(ncamp);


               // if (HomeFragment.LIGA_ON.getTipoliga() == 1) {
                    FirebaseFirestore.getInstance().collection("/ligas")
                            .document(HomeFragment.LIGA_ON.getKeyliga())
                            .collection("campeonatoativo")
                            .document(keycamp)
                            .set(ncamp)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mprogressBar.setVisibility(View.INVISIBLE);
                            util.showmessage(getContext(), "Problemas ao salvar camp");
                        }
                    });


                    for(int i=0;i<jt.size();i++) {
                        FirebaseFirestore.getInstance().collection("/ligas")
                                .document(HomeFragment.LIGA_ON.getKeyliga())
                                .collection("campeonatoativo")
                                .document(keycamp)
                                .collection("dataset")
                                .document(String.valueOf(jt.get(i).getIdjogo()))
                                .set(jt.get(i))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mprogressBar.setVisibility(View.INVISIBLE);
                                util.showmessage(getContext(), "Problemas ao salvar dataset");
                            }
                        });
                    }


                Log.i("campativo"," : " + scamp);
                //todo montar nemuro campeonato

                MainActivity.navController.navigate(R.id.action_sortear_Jogos_to_campeonato);

            }
        });

        swmanual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 30);
                if(swmanual.isChecked()){
                    bolManual=true;
                    txtmanual.setText(getResources().getString(R.string.jogador_alterar));
                    cvmanual.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abre_manual));

                    abreCv.start();

                }
                if(!swmanual.isChecked()){
                    bolManual=false;
                    lljog1.setBackgroundColor(Color.TRANSPARENT);
                    lljog2.setBackgroundColor(Color.TRANSPARENT);
                    lljog3.setBackgroundColor(Color.TRANSPARENT);
                    lljog4.setBackgroundColor(Color.TRANSPARENT);
                    lljog5.setBackgroundColor(Color.TRANSPARENT);
                    lljog6.setBackgroundColor(Color.TRANSPARENT);
                    jogsel=false;
                    cvmanual.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fecha_manual));
                }

            }
        });
        btsortear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 30);
                if(bolManual) {
                    util.showSnackError(clsortear,"Desative o Modo Manual para realizar um sorteio.");
                    return;
                }
                travaBotao(true);

                txttitle.setTextColor(Color.parseColor("#FF0000"));
                txttitle.setBackgroundColor(Color.parseColor("#FFEB3B"));
                cont=0;
                taxa=10;
                mili = 250;
                sorteia=true;
                anima=true;
                blimpTexto();
                sorteLista();
            }
        });

        lljog1.setOnClickListener(this);
        lljog2.setOnClickListener(this);
        lljog3.setOnClickListener(this);
        lljog4.setOnClickListener(this);
        lljog5.setOnClickListener(this);
        lljog6.setOnClickListener(this);


            btsortear.callOnClick();


//        cont=0;
//        taxa=10;
//        mili = 300;
//        travaBotao(true);
//        montaVisao();
    }

    private void sorteLista() {

        txttitle.setText(getResources().getString(R.string.sorteando_partidas));
        txttitle.setBackgroundColor(Color.parseColor("#C5E1A5"));


         if(cont>=2 && cont<5){
             mili=125;
         }else if(cont>5 && cont<38){
            mili=1;
         }else if(cont>38 && cont<44) {
             mili = 125;
         }else if(cont>44 && cont<50) {
             mili = 250;
         }


       if(cont>=50){
           txttitle.setText(getResources().getString(R.string.sortear_partidas_iniciais));
           txttitle.setTextColor(Color.parseColor("#FF1F6612"));
           txttitle.setBackgroundColor(Color.TRANSPARENT);
           travaBotao(false);

       }else {
           Handler rol = new Handler();
           rol.postDelayed(new Runnable() {
               @Override
               public void run() {
                   Collections.shuffle(listnum);
                   montaVisao();
               }
           }, mili);
       }

    }

    private void montaVisao() {

        int A = listnum.get(0);
        int B = listnum.get(1);
        int C = listnum.get(2);
        int D = 3;
        int E = 4;
        int F = 5;

        if (listnum.size() > 3) D = listnum.get(3);
        if (listnum.size() > 4) E = listnum.get(4);
        if (listnum.size() > 5) F = listnum.get(5);

        imgj1p1.setImageBitmap(listimg.get(A));
        txtj1p1.setText(listnome.get(A));
        imgj1p2.setImageBitmap(listimg.get(B));
        txtj1p2.setText(listnome.get(B));
        imgj2p1.setImageBitmap(listimg.get(C));
        txtj2p1.setText(listnome.get(C));
        imgj2p2.setImageBitmap(listimg.get(D));
        txtj2p2.setText(listnome.get(D));
        imgj3p1.setImageBitmap(listimg.get(E));
        txtj3p1.setText(listnome.get(E));
        imgj3p2.setImageBitmap(listimg.get(F));
        txtj3p2.setText(listnome.get(F));

        if (bolManual) return;
        cont++;
        if (cont < 8) {//15
            taxa++;
        }
        if (cont > 30) {//45
            taxa = taxa - 1;
        }

        if (MainActivity.sortear) {
            sorteLista();
        } else {
            txttitle.setText(getResources().getString(R.string.sortear_partidas_iniciais));
            txttitle.setTextColor(Color.parseColor("#FF1F6612"));
            txttitle.setBackgroundColor(Color.TRANSPARENT);
            travaBotao(false);
            MainActivity.sortear=true;
        }
    }

    public void travaBotao(boolean b){
        if(b){
            btsortear.setClickable(false);
            btsortear.setEnabled(false);
            btsortear.setTextColor(Color.GRAY);
            btavancar.setClickable(false);
            btavancar.setEnabled(false);
            btavancar.setTextColor(Color.GRAY);
            swmanual.setClickable(false);
            swmanual.setEnabled(false);
            swmanual.setTextColor(Color.GRAY);
            sorteia=true;
        }else{
            sorteia=false;
            btsortear.setClickable(true);
            btsortear.setEnabled(true);
            btsortear.setTextColor(Color.parseColor("#1F6612"));
            btavancar.setClickable(true);
            btavancar.setEnabled(true);
            btavancar.setTextColor(Color.parseColor("#1F6612"));
            swmanual.setClickable(true);
            swmanual.setEnabled(true);
            swmanual.setTextColor(Color.parseColor("#1F6612"));
        }
    }

    @Override
    public void onClick(View v) {
        int item = v.getId();

        if(!bolManual)return;



        util.vibratePhone(getContext(),(short) 30);
        switch (item){
            case R.id.lljog1:
                if(!jogsel){
                    txtmanual.setText(getResources().getString(R.string.jogador_destino));
                    cvmanual.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abre_manual));
                    lljog1.setBackgroundColor(Color.parseColor("#87C9EC"));
                    origem = listnum.get(0);
                    indexorigem=0;
                    jogsel=true;
                }else{
                    cvmanual.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abre_manual));
                    lljog1.setBackgroundColor(Color.parseColor("#87C9EC"));
                    destino = listnum.get(0);
                    indexdestino=0;
                    limpa();
                }
                break;

            case R.id.lljog2:
                if(!jogsel){
                    txtmanual.setText(getResources().getString(R.string.jogador_destino));
                    cvmanual.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abre_manual));
                    lljog2.setBackgroundColor(Color.parseColor("#87C9EC"));
                    indexorigem = 1;
                    origem = listnum.get(1);
                    jogsel=true;
                }else{
                    lljog2.setBackgroundColor(Color.parseColor("#87C9EC"));
                    destino = listnum.get(1);
                    indexdestino=1;
                    limpa();
                }
                break;
            case R.id.lljog3:
                if(!jogsel){
                    txtmanual.setText(getResources().getString(R.string.jogador_destino));
                    cvmanual.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abre_manual));
                    lljog3.setBackgroundColor(Color.parseColor("#87C9EC"));
                    indexorigem = 2;
                    origem = listnum.get(2);
                    jogsel=true;
                }else{
                    lljog3.setBackgroundColor(Color.parseColor("#87C9EC"));
                    destino = listnum.get(2);
                    indexdestino=2;
                    limpa();
                }
                break;
            case R.id.lljog4:
                if(jogadores_inscritos.size()<4)return;
                if(!jogsel){
                    txtmanual.setText(getResources().getString(R.string.jogador_destino));
                    cvmanual.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abre_manual));
                    lljog4.setBackgroundColor(Color.parseColor("#87C9EC"));
                    indexorigem = 3;
                    origem = listnum.get(3);
                    jogsel=true;
                }else{
                    lljog4.setBackgroundColor(Color.parseColor("#87C9EC"));
                    destino = listnum.get(3);
                    indexdestino=3;
                    limpa();
                }
                break;
            case R.id.lljog5:
                if(jogadores_inscritos.size()<5)return;
                if(!jogsel){
                    txtmanual.setText(getResources().getString(R.string.jogador_destino));
                    cvmanual.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abre_manual));
                    lljog5.setBackgroundColor(Color.parseColor("#87C9EC"));
                    indexorigem = 4;
                    origem = listnum.get(4);
                    jogsel=true;
                }else{
                    lljog5.setBackgroundColor(Color.parseColor("#87C9EC"));
                    destino = listnum.get(4);
                    indexdestino=4;
                    limpa();
                }
                break;
            case R.id.lljog6:
                if(jogadores_inscritos.size()<6)return;
                if(!jogsel){
                    txtmanual.setText(getResources().getString(R.string.jogador_destino));
                    cvmanual.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abre_manual));
                    lljog6.setBackgroundColor(Color.parseColor("#87C9EC"));
                    indexorigem = 5;
                    origem = listnum.get(5);
                    jogsel=true;
                }else{
                    lljog6.setBackgroundColor(Color.parseColor("#87C9EC"));
                    destino = listnum.get(5);
                    indexdestino=5;
                    limpa();
                }
                break;
        }


    }

    private void limpa() {
        cvmanual.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fecha_manual));

        Handler rol = new Handler();
        rol.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(jogsel){
                    lljog1.setBackgroundColor(Color.TRANSPARENT);
                    lljog2.setBackgroundColor(Color.TRANSPARENT);
                    lljog3.setBackgroundColor(Color.TRANSPARENT);
                    lljog4.setBackgroundColor(Color.TRANSPARENT);
                    lljog5.setBackgroundColor(Color.TRANSPARENT);
                    lljog6.setBackgroundColor(Color.TRANSPARENT);
                    jogsel=false;
                }
                listnum.set(indexdestino,origem);
                listnum.set(indexorigem,destino);
                for(int i=0;i<listnum.size();i++){
                    Log.i("listnum","pos i : "+i+" : " + listnum.get(i)+" : " + jogadores_inscritos.get(i).getNomeins());
                    //   Log.i("listnum","array i : "+i+" : " + listnome.get(i));
                }
                montaVisao();
            }
        }, 400);
    }

    public void blimpTexto(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 800;

                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(anima){

                            txttitle.setTextColor(Color.parseColor("#FF0000"));


                        }else{
                            txttitle.setTextColor(Color.parseColor("#FF1F6612"));

                        }
                        anima=!anima;
                        if(sorteia){
                            blimpTexto();
                        }else{
                            txttitle.setTextColor(Color.parseColor("#FF1F6612"));
                        }
                    }
                });
            }
        }).start();
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



}
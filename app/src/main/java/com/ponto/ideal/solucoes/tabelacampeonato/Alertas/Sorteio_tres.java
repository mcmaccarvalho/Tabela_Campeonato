package com.ponto.ideal.solucoes.tabelacampeonato.Alertas;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Participantes;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.Classificacao;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.NovoCampeonatoViewModel;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;


public class Sorteio_tres extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    NovoCampeonatoViewModel ncViewModel;

    String jog1, jog2, jog3, nome1, nome2, nome3;


    private TextView txttitle,txtfin1,txtfin2;
    private ImageView  imgfin1, imgfin2;
    private Button btavancar, btcancelar, btsortear;
    private ConstraintLayout clmainresult;
    private int mili;
    private int cont;
    public static boolean sorteia=false;
    private boolean anima=false;

    private RecyclerView rvsort;
    private GroupAdapter adapter;
    private ArrayList<Participantes> partCamp = new ArrayList<>();

    private boolean sorteioOK=false;
    private ArrayList<Participantes> partSort = new ArrayList<>();

    private int campP=0;
    private int campS=0;
    private ObjectAnimator animation1;
    private ObjectAnimator animation2;
    public Sorteio_tres(ArrayList<Participantes> participantes) {
        this.partCamp = participantes;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_sorteio_tres, container, false);


        initViews(v);


        return v;
    }

    public void initViews(View v) {

        setCancelable(false);

        clmainresult = v.findViewById(R.id.clmainresult);
        rvsort = v.findViewById(R.id.rvsort);
        imgfin1 = v.findViewById(R.id.imgfin1);
        imgfin2 = v.findViewById(R.id.imgfin2);
        txtfin1 = v.findViewById(R.id.txtfin1);
        txtfin2 = v.findViewById(R.id.txtfin2);
        btavancar = v.findViewById(R.id.btavancar);
        btcancelar = v.findViewById(R.id.btcancelar);
        btsortear = v.findViewById(R.id.btsortear);
        txttitle = v.findViewById(R.id.txttitle);

        animation1 = ObjectAnimator.ofFloat(imgfin1, "scaleX", 0);
        animation1.setDuration(200);

        animation2 = ObjectAnimator.ofFloat(imgfin2, "scaleX", 0);
        animation2.setDuration(200);

        adapter = new GroupAdapter();
        adapter.clear();
        partSort.clear();
        campP =0;
        campS =0;
        for(int i=0;i<partCamp.size();i++){
            if(partCamp.get(i).getClass_jogador()==1){
                campP++;
            }
            if(partCamp.get(i).getClass_jogador()==2){
                campS++;
            }
        }


        if(campP==1) {
            imgfin1.setImageBitmap(ncViewModel.getBitimgjog().get(partCamp.get(0).getIdimg()));
            txtfin1.setText(nomejog(partCamp.get(0).getId_jogador()));
        }

        for(int i=0;i<partCamp.size();i++){
            if(campP==1){
                if(partCamp.get(i).getClass_jogador()>1 &&
                partCamp.get(i).getClass_jogador()<=2){
                    adapter.add(new ParticipantesItem(partCamp.get(i)));
                    partSort.add(partCamp.get(i));
                }
            }else {
                if(partCamp.get(i).getClass_jogador()<=2){
                    adapter.add(new ParticipantesItem(partCamp.get(i)));
                    partSort.add(partCamp.get(i));
                }
            }
        }
        rvsort.setLayoutManager(new LinearLayoutManager(getContext()));
        rvsort.setAdapter(adapter);

        btavancar.setClickable(false);
        btavancar.setEnabled(false);

        btcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDialog().dismiss();

            }
        });

        btavancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sorteioOK){
                    util.showmessage(getContext(),"Realize o sorteio antes de avançar.");
                }else {
                    avancar();
                }
            }
        });

        btsortear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 30);
                btsortear.setClickable(false);
                btsortear.setEnabled(false);
                travaBotao(true);
                sorteioOK=false;
                txttitle.setTextColor(Color.parseColor("#FF0000"));
                txttitle.setBackgroundColor(Color.parseColor("#FFEB3B"));
                cont=0;
                mili = 200;
                sorteia=true;
                anima=true;
                blimpTexto();
                aplicaanin();
            }
        });



    }

    private void contasorteio(){
        if(cont<=50) {
            Handler rol = new Handler();
            rol.postDelayed(new Runnable() {
                @Override
                public void run() {
                    aplicaanin();
                }
            }, 30);
        }
        if(cont==51) {
            txttitle.setText("Finalistas");
            txttitle.setTextColor(Color.parseColor("#FF1F6612"));
            txttitle.setBackgroundColor(Color.TRANSPARENT);
            travaBotao(false);
            sorteioOK=true;

            if (MainActivity.sortear) {
               // sorteLista();
            } else {
                txttitle.setText("Finalistas");
                txttitle.setTextColor(Color.parseColor("#FF1F6612"));
                txttitle.setBackgroundColor(Color.TRANSPARENT);
                travaBotao(false);
                MainActivity.sortear=true;
            }
        }


    }

    private void aplicaanin(){

        Collections.reverse(partSort);
        Collections.shuffle(partSort);
        Collections.reverse(partSort);
        animation1.setDuration(mili);
        animation2.setDuration(mili);
        if(campP>1) animation1.start();
        animation2.start();
        Handler rol = new Handler();
        rol.postDelayed(new Runnable() {
            @Override
            public void run() {
                //  Collections.shuffle(listnum);
                reverteanin();
            }
        }, mili);
    }

    private void reverteanin(){

        String id1  = partSort.get(0).getId_jogador();
        String id2  = partSort.get(1).getId_jogador();

        Collections.reverse(partSort);
        Collections.shuffle(partSort);
        Collections.reverse(partSort);

        if(campP==1){
            imgfin1.setImageBitmap( imgjog(partCamp.get(0).getId_jogador()));
            txtfin1.setText(partCamp.get(0).getNome_jogador());
            imgfin2.setImageBitmap(imgjog(partSort.get(0).getId_jogador()));
            txtfin2.setText(partSort.get(0).getNome_jogador());
        }else {

            imgfin1.setImageBitmap(imgjog(partSort.get(0).getId_jogador()));
            txtfin1.setText(partSort.get(0).getNome_jogador());
            imgfin2.setImageBitmap(imgjog(partSort.get(1).getId_jogador()));
            txtfin2.setText(partSort.get(1).getNome_jogador());
        }

        if(campP>1)animation1.reverse();
        animation2.reverse();

        txttitle.setText("Sorteando Finalistas");
        txttitle.setBackgroundColor(Color.parseColor("#C5E1A5"));

        cont++;
        if(cont>=2 && cont<5){
            mili=10;
        }else if(cont>5 && cont<38){
            mili=1;
        }else if(cont>38 && cont<44) {
            mili = 125;
        }else if(cont>44 && cont<50) {
            mili = 250;
        }
        contasorteio();
    }

    private class ParticipantesItem extends Item<ViewHolder> {

        private final Participantes participantes;

        public ParticipantesItem(Participantes participantes) {
            this.participantes = participantes;
        }



        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtnome = viewHolder.itemView.findViewById(R.id.txtnome);
            TextView txtclass = viewHolder.itemView.findViewById(R.id.txtclass);
            final ImageView imgjog = viewHolder.itemView.findViewById(R.id.imgjog);
            txtnome.setText(participantes.getNome_jogador());
            txtclass.setText("");
            imgjog.setImageBitmap(ncViewModel.getBitimgjog().get(participantes.getIdimg()));
        }

        @Override
        public int getLayout() {
            return R.layout.classificacao_final;
        }
    }

    public void avancar() {

        ArrayList<Participantes> tempPart = new ArrayList<>();
        tempPart.clear();

        if(partCamp.size()==partSort.size()){
            tempPart=partSort;
        }else if(campP==1 && (partCamp.size()-1)==partSort.size()){
            tempPart.add(partCamp.get(0));
            for(int i=0;i<partSort.size();i++){
                tempPart.add(partSort.get(i));
            }
        }else{
            if(campP==1)tempPart.add(partCamp.get(0));
            for(int i=0;i<partSort.size();i++){
                tempPart.add(partSort.get(i));
            }

            for(int i=tempPart.size();i<partCamp.size();i++){
                tempPart.add(partCamp.get(i));
            }
        }

        Classificacao.ctrlJogo=4;
        ncViewModel.setTempPart(tempPart);
        getDialog().dismiss();
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

    public void travaBotao(boolean b){
        if(b){
            btsortear.setClickable(false);
            btsortear.setEnabled(false);
          //  btsortear.setTextColor(Color.GRAY);
            btavancar.setClickable(false);
            btavancar.setEnabled(false);
          //  btavancar.setTextColor(Color.GRAY);
            btcancelar.setClickable(false);
            btcancelar.setEnabled(false);
        //    btcancelar.setTextColor(Color.GRAY);

            sorteia=true;
        }else{
            sorteia=false;
            btsortear.setClickable(true);
            btsortear.setEnabled(true);
          //  btsortear.setTextColor(Color.parseColor("#1F6612"));
            btavancar.setClickable(true);
            btavancar.setEnabled(true);
       //     btavancar.setTextColor(Color.parseColor("#1F6612"));
            btcancelar.setClickable(true);
            btcancelar.setEnabled(true);
        //    btcancelar.setTextColor(Color.parseColor("#1F6612"));


        }
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

    public Bitmap imgjog(String id){
        Bitmap img= null;
        Log.i("partcamp",": " + partCamp.size());
        for(int i=0;i<partCamp.size();i++){
            if(partCamp.get(i).getId_jogador().equals(id)){
                img=NovoCampeonatoViewModel.getBitimgjog().get(partCamp.get(i).getIdimg());
            }
        }
        return img;
    }

}


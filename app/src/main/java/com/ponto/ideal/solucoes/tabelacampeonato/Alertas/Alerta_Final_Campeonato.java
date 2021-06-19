package com.ponto.ideal.solucoes.tabelacampeonato.Alertas;

import android.os.Bundle;
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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JogosTeste;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Participantes;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.Classificacao;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.NovoCampeonatoViewModel;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;


public class Alerta_Final_Campeonato extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    NovoCampeonatoViewModel ncViewModel;

    ArrayList<Participantes> partCamp = new ArrayList<>();
    private int tipoturno;

    private TextView  txtjog1;
    private ImageView imgjog1;
    private Button btsalvar, btcancelar,btreset;
    private ConstraintLayout clmainresult;
    private RecyclerView rvfinal;
    private GroupAdapter adapter;

    public Alerta_Final_Campeonato(ArrayList<Participantes> participantes, int tipoturno) {
        this.partCamp = participantes;
        this.tipoturno = tipoturno;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_final_campeonato, container, false);


        initViews(v);



        return v;
    }

    public void initViews(View v){

        setCancelable(false);

        ncViewModel = ViewModelProviders.of(requireActivity()).get(NovoCampeonatoViewModel.class);



        clmainresult = v.findViewById(R.id.clmainresult);
        txtjog1 = v.findViewById(R.id.txtjog1);
        imgjog1 = v.findViewById(R.id.imgjog1);
        btsalvar = v.findViewById(R.id.btsalvar);
        btcancelar = v.findViewById(R.id.btcancelar);
        btreset = v.findViewById(R.id.btreset);
        rvfinal = v.findViewById(R.id.rvfinal);


        if(tipoturno<=2){
            btreset.setVisibility(View.INVISIBLE);
            int camp =0;
            for(int i=0;i<partCamp.size();i++){
                if(partCamp.get(i).getClass_jogador()==1){
                    camp++;
                }
            }

            if(camp==1){
                txtjog1.setText(partCamp.get(0).getNome_jogador());
                imgjog1.setImageBitmap(ncViewModel.getBitimgjog().get(partCamp.get(0).getIdimg()));
            }else{
                txtjog1.setText(camp+" Campeões");
                imgjog1.setImageResource(R.drawable.trofeudourado);
            }


        }else {

            txtjog1.setText(partCamp.get(0).getNome_jogador());
            imgjog1.setImageBitmap(ncViewModel.getBitimgjog().get(partCamp.get(0).getIdimg()));
        }

        adapter = new GroupAdapter();
        adapter.clear();
        for(int i=0;i<partCamp.size();i++){
            adapter.add(new ParticipantesItem(partCamp.get(i)));
        }
        rvfinal.setLayoutManager(new LinearLayoutManager(getContext()));
        rvfinal.setAdapter(adapter);
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

    private class ParticipantesItem extends Item<ViewHolder> {

        private final Participantes participantes;

        public ParticipantesItem(Participantes participantes) {
            this.participantes = participantes;
        }



        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtnome = viewHolder.itemView.findViewById(R.id.txtnome);
//            TextView txtpt = viewHolder.itemView.findViewById(R.id.txtpt);
//            TextView txtsg = viewHolder.itemView.findViewById(R.id.txtsg);
            TextView txtclass = viewHolder.itemView.findViewById(R.id.txtclass);


            final ImageView imgjog = viewHolder.itemView.findViewById(R.id.imgjog);

//            int saldo = participantes.getGolspro()-participantes.getGolscontra();
//            txtpt.setText(String.valueOf(participantes.getPontos()));
//            txtsg.setText(String.valueOf(saldo));
            txtnome.setText(participantes.getNome_jogador());
            txtclass.setText(String.valueOf(participantes.getClass_jogador()));
            imgjog.setImageBitmap(ncViewModel.getBitimgjog().get(participantes.getIdimg()));

        }

        @Override
        public int getLayout() {
            return R.layout.classificacao_final;
        }
    }


    public void salvar(){


        NovoCampeonatoViewModel.setBooleanSalvarCamp(true);
        getDialog().dismiss();

    }

    public void reset(){

        JogosTeste jjf = Classificacao.jogoFinal;

        jjf.setPlacar1("_");
        jjf.setPlacar2("_");
        jjf.setPenalti1("");
        jjf.setPenalti2("");
        jjf.setStatusjogo(3);

        Classificacao.jogoFinal=jjf;
        ncViewModel.setBooleanfinal(true);
        getDialog().dismiss();
    }

    public String nomejog(String id){
        String nome= "www";

        for(int i=0;i<partCamp.size();i++){
            if(partCamp.get(i).getId_jogador().equals(id)){
                nome=partCamp.get(i).getNome_jogador();
            }
        }
        return nome;
    }

}

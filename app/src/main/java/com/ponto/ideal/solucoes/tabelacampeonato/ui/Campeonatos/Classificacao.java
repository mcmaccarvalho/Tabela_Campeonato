package com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ponto.ideal.solucoes.tabelacampeonato.Alertas.Alerta_Final_Campeonato;
import com.ponto.ideal.solucoes.tabelacampeonato.Alertas.Alerta_Final_Definida;
import com.ponto.ideal.solucoes.tabelacampeonato.Alertas.Alerta_Jogo_Final;
import com.ponto.ideal.solucoes.tabelacampeonato.Alertas.Alerta_Jogos_X_Jogador;
import com.ponto.ideal.solucoes.tabelacampeonato.Alertas.Alerta_Resultado_Jogo;
import com.ponto.ideal.solucoes.tabelacampeonato.Alertas.Sorteio_tres;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Jogos_Dataset_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Jogos_Dataset_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JogosTeste;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Participantes;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Pode_Finalista;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Classificacao extends Fragment {

    private NovoCampeonatoViewModel ncViewModel;

    public static ArrayList<JogosTeste> listajogos = new ArrayList<>();
    private  ArrayList<Participantes> partCamp = new ArrayList<>();
    public static ArrayList<JogosTeste> dataset = new ArrayList<>();

    private TextView txtjog1,txtjog2,placar1,placar2,txtp1,txtp2,txtnumjogo,txttitlejogo;
    private ImageView imgjog1,imgjog2;
    private ConstraintLayout cljogoativo,clclass;
    private CardView cvjogoativo,cvlistajogo,cvlegenda;

    private RecyclerView rvresult;
    private GroupAdapter adapter;

    private int anima = 0;
    private boolean banima = true;

    public static boolean verifin = true;

    public static int idjogoatual;

    private int tipoturno;
    private int numpart;

    public static JogosTeste jogoFinal = new JogosTeste();

    public static int ctrlJogo=0;


    private boolean blimp = false;
    private int nfin;

//    String final1;
//    String final2;
//    int entrou=0;
//    private boolean fdef;
//    private boolean finalista1 = false;
//    private boolean finalista2 = false;
//    private ArrayList<JogosTeste> datatemp = new ArrayList<>();
//    private boolean remonta=false;

    private boolean legenda = false;

    public static boolean tdsjogos=false;

    public static boolean alterajogo =false;
    public static int jogoalterar=-1;


    private ArrayList<Participantes> partTempFinal= new ArrayList<>();

    private ArrayList<Pode_Finalista> pode_finalistas = new ArrayList<>();

    public Classificacao() {

    }

    public static Classificacao newInstance(String param1, String param2) {
        Classificacao fragment = new Classificacao();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ncViewModel= Campeonato.ncViewModel;

        View v =inflater.inflate(R.layout.fragment_classificacao, container, false);
        initViews(v);
        return v;
    }

    private void initViews(final View v) {

        listajogos=NovoCampeonatoViewModel.getListajogos();
        tipoturno= NovoCampeonatoViewModel.getTipoturno();
        numpart= NovoCampeonatoViewModel.getNumpart();

        rvresult = v.findViewById(R.id.rvresult);
        txtjog1 = v.findViewById(R.id.txtjog1);
        txtjog2 = v.findViewById(R.id.txtjog2);
        placar1 = v.findViewById(R.id.placar1);
        placar2 = v.findViewById(R.id.placar2);
        txtp1 =v.findViewById (R.id.txtp1 );
        txtp2 =v.findViewById (R.id.txtp2 );
        txtnumjogo = v.findViewById (R.id.txtnumjogo );
        txttitlejogo= v.findViewById (R.id.txttitlejogo );
        imgjog1 = v.findViewById(R.id.imgjog1);
        imgjog2 = v.findViewById(R.id.imgjog2);
        cljogoativo = v.findViewById(R.id.cljogoativo);
        cvjogoativo = v.findViewById(R.id.cvjogoativo);
        cvlistajogo = v.findViewById(R.id.cvlistajogo);
        cvlegenda = v.findViewById(R.id.cvlegenda);
        clclass = v.findViewById(R.id.clclass);

        adapter = new GroupAdapter();
        adapter.clear();
        for(int i=0;i<partCamp.size();i++){
            adapter.add(new ParticipantesItem(partCamp.get(i)));
        }
        rvresult.setLayoutManager(new LinearLayoutManager(getContext()));
        rvresult.setAdapter(adapter);

        clclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(legenda) {
                    legenda = false;
                    cvlegenda.setVisibility(View.GONE);
                }
            }
        });

        cvlistajogo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(legenda){
                    legenda=false;
                    cvlegenda.setVisibility(View.GONE);
                }else {
                    legenda = true;
                    cvlegenda.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        cvlegenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(legenda){
                    legenda=false;
                    cvlegenda.setVisibility(View.GONE);
                }
            }
        });

        cljogoativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(legenda){
                    legenda=false;
                    cvlegenda.setVisibility(View.GONE);
                    return;
                }
                switch (ctrlJogo) {
                    case 0:
                        Alerta_Resultado_Jogo alerta_resultado_jogo = new Alerta_Resultado_Jogo(dataset.get(idjogoatual),
                                nomejog(dataset.get(idjogoatual).getIdjogador1()),
                                nomejog(dataset.get(idjogoatual).getIdjogador2()));
                        alerta_resultado_jogo.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
                        break;
                    case 2:
                        Sorteio_tres sorteio_tres = new Sorteio_tres(partCamp);
                        sorteio_tres.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
                        break;
                    case 4:
                        Log.i("jogofinalalerta"," ctrl4t: " + nomejog(jogoFinal.getIdjogador1())+
                                " x " + nomejog(jogoFinal.getIdjogador2()));
                        Alerta_Jogo_Final alerta_jogo_final = new Alerta_Jogo_Final(jogoFinal,
                                nomejog(jogoFinal.getIdjogador1()),
                                nomejog(jogoFinal.getIdjogador2()));
                        alerta_jogo_final.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
                        break;
                    case 5:
                        Alerta_Final_Campeonato alerta_final_campeonato = new Alerta_Final_Campeonato(partTempFinal, tipoturno);
                        alerta_final_campeonato.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
                        break;
                }
    }
});


        ncViewModel.getBooleanfinal().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                if(b){
                    atuaizajogofinal();
                    ncViewModel.setBooleanfinal(false);
                }
            }
        });

        ncViewModel.getBooleanSalvarCamp().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                if(b){

                    ncViewModel.setBooleanSalvarCamp(false);
                    MainActivity.navController.navigate(R.id.action_campeonato_to_nav_home);
                }

            }
        });

        ncViewModel.getCampPart().observe(this,new Observer<ArrayList<Participantes>>() {
           @Override
           public void onChanged(ArrayList<Participantes> pp) {
               partCamp=pp;
           }
       });


        ncViewModel.getTempPart().observe(this, new Observer<ArrayList<Participantes>>() {
            @Override
            public void onChanged(ArrayList<Participantes> p) {
                if(legenda) {
                    legenda = false;
                    cvlegenda.setVisibility(View.GONE);
                }

                if(p!=null){
                    montajogoFinal(p.get(0).getId_jogador(),p.get(1).getId_jogador());
                  //  verifin=false;
                    p.get(0).setClass_jogador(1);
                    p.get(1).setClass_jogador(1);
                    p.get(2).setClass_jogador(3);
                    int contclass =1;
                    boolean emp = false;

                    contclass=4;

                    for(int i=3;i<p.size();i++){
                        emp = verificaEmpate(p.get(i),p.get(i-1));
                        p.get(i).setClass_jogador(emp==true?p.get(i-1).getClass_jogador():contclass);
                        contclass++;
                    }
                    nfin=2;
                    adapter.clear();
                    for(int i=0;i<p.size();i++){
                        adapter.add(new ParticipantesItem(p.get(i)));
                    }
                }
            }
        });


        ncViewModel.getDataset().observe(this, new Observer<ArrayList<JogosTeste>>() {
            @Override
            public void onChanged(ArrayList<JogosTeste> jogosTestes) {

                if(legenda) {
                    legenda = false;
                    cvlegenda.setVisibility(View.GONE);
                }
                dataset=jogosTestes;
                NovoCampeonatoViewModel.setListajogos(jogosTestes);
                classificaPart();
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {

                Jogos_Dataset_Controller jc = new Jogos_Dataset_Controller(getContext());
                jc.criarTabela(Jogos_Dataset_DataModel.criarTabela());
                ArrayList<JogosTeste> jogosbanco = jc.listarDatasetCamp(dataset.get(0).getKeycampeonato(),dataset.get(0).getKeyliga());
                for (int i=0;i<jogosbanco.size();i++) {
                    Log.i("jogosbanco",": " +nomejog(jogosbanco.get(i).getIdjogador1()) +" "+jogosbanco.get(i).getPlacar1()+ " X "+jogosbanco.get(i).getPlacar2()+" " + nomejog(jogosbanco.get(i).getIdjogador2()));
                }

                if(legenda){
                    legenda=false;
                    cvlegenda.setVisibility(View.GONE);
                    return;
                }
                ParticipantesItem participantesItem = (ParticipantesItem) item;

                Participantes pp = participantesItem.participantes;

//                int lugar=0;
//                for(int i=0;i<partCamp.size();i++){
//                    if(partCamp.get(i).getId_jogador().equals(pp.getId_jogador()))lugar=i;
//                }

                Alerta_Jogos_X_Jogador alerta_jogos_x_jogador = new Alerta_Jogos_X_Jogador(pp,jogoFinal);
                alerta_jogos_x_jogador.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
            }
        });


//        if(alterajogo){
//            util.showmessage(getContext(),"alterar jogo" + jogoalterar +":"+
//                    nomejog(dataset.get(jogoalterar).getIdjogador1()) + nomejog(dataset.get(jogoalterar).getIdjogador2()));
//
//            Alerta_Resultado_Jogo alerta_resultado_jogo = new Alerta_Resultado_Jogo(dataset.get(jogoalterar),
//                    nomejog(dataset.get(jogoalterar).getIdjogador1()),
//                    nomejog(dataset.get(jogoalterar).getIdjogador2()));
//            alerta_resultado_jogo.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
//            jogoalterar=-1;
//            alterajogo=false;
//        }

    }

    private class ParticipantesItem extends Item<ViewHolder> {

        private final Participantes participantes;

        public ParticipantesItem(Participantes participantes) {
            this.participantes = participantes;
        }



        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtpt = viewHolder.itemView.findViewById(R.id.txtpt);
            TextView txtsg = viewHolder.itemView.findViewById(R.id.txtsg);
            TextView txtgp = viewHolder.itemView.findViewById(R.id.txtgp);
            TextView txtgc = viewHolder.itemView.findViewById(R.id.txtgc);
            TextView txtj = viewHolder.itemView.findViewById (R.id.txtj );
            TextView txtv = viewHolder.itemView.findViewById (R.id.txtv );
            TextView txtd = viewHolder.itemView.findViewById (R.id.txtd );
            TextView txte = viewHolder.itemView.findViewById (R.id.txte );
            TextView txtmp = viewHolder.itemView.findViewById(R.id.txtmp);
            TextView txtnome = viewHolder.itemView.findViewById(R.id.txtnome);
            ImageView imgstar = viewHolder.itemView.findViewById(R.id.imgstar);
            final ImageView imgjog = viewHolder.itemView.findViewById(R.id.imgjog);

            int maxptgeral=0;
            switch (tipoturno){
                case 1:maxptgeral=(numpart-1)*3;break;
                case 2:maxptgeral=((numpart-1)*3)*2;break;
                case 3:maxptgeral=(numpart-1)*3;break;
                case 4:maxptgeral=((numpart-1)*3)*2;break;
            }
            maxptgeral=(maxptgeral-(participantes.getJogos()*3)+participantes.getPontos());

            txtmp.setText((ctrlJogo==4 || ctrlJogo==5)?"":String.valueOf(maxptgeral));
            int saldo = participantes.getGolspro()-participantes.getGolscontra();
            txtpt.setText(String.valueOf(participantes.getPontos()));
            txtsg.setText(String.valueOf(saldo));
            txtgp.setText(String.valueOf(participantes.getGolspro()));
            txtgc.setText(String.valueOf(participantes.getGolscontra()));
            txtj .setText(String.valueOf(participantes.getJogos()));
            txtv .setText(String.valueOf(participantes.getVitorias()));
            txtd .setText(String.valueOf(participantes.getDerrotas()));
            txte .setText(String.valueOf(participantes.getEmpates()));
            txtnome.setText(participantes.getNome_jogador());
            imgjog.setImageBitmap(ncViewModel.getBitimgjog().get(participantes.getIdimg()));

            imgstar.setVisibility(position<=(nfin-1)?View.VISIBLE:View.INVISIBLE);

        }

        @Override
        public int getLayout() {
            return R.layout.classificacao_participantes;
        }
    }

    public void classificaPart(){

        for (int i=0;i<partCamp.size();i++){
            partCamp.get(i).setVitorias(0);
            partCamp.get(i).setDerrotas(0);
            partCamp.get(i).setEmpates(0);
            partCamp.get(i).setGolspro(0);
            partCamp.get(i).setGolscontra(0);
            partCamp.get(i).setClass_jogador(0);
            partCamp.get(i).setJogos(0);
            partCamp.get(i).setPontos(0);
            partCamp.get(i).setSaldogols(0);

        }
        //insere resultados
        for (int i=0;i<dataset.size();i++){

            if(dataset.get(i).getStatusjogo()==0){
                continue;
            }else {

                int res = -1;
                int pt1 = 0;
                int pt2 = 0;
                int pl1 = Integer.parseInt(dataset.get(i).getPlacar1());
                int pl2 = Integer.parseInt(dataset.get(i).getPlacar2());
                if (pl1 == pl2) {
                    res = 0;
                    pt1 = 1;
                    pt2 = 1;
                } else if (pl1 > pl2) {
                    res = 1;
                    pt1 = 3;
                    pt2 = 0;
                } else {
                    res = 2;
                    pt1 = 0;
                    pt2 = 3;
                }

                for (int j = 0; j < partCamp.size(); j++) {
                    switch (res) {
                        case 0:
                            if (partCamp.get(j).getId_jogador().equals(dataset.get(i).getIdjogador1())) {
                                int emp = partCamp.get(j).getEmpates() + 1;
                                int pro = partCamp.get(j).getGolspro() + pl1;
                                int con = partCamp.get(j).getGolscontra() + pl2;
                                int jog = partCamp.get(j).getJogos() + 1;
                                int pon = partCamp.get(j).getPontos() + pt1;
                                partCamp.get(j).setEmpates(emp);
                                partCamp.get(j).setGolspro(pro);
                                partCamp.get(j).setGolscontra(con);
                                partCamp.get(j).setJogos(jog);
                                partCamp.get(j).setPontos(pon);
                                partCamp.get(j).setSaldogols(pro-con);
                            }
                            if (partCamp.get(j).getId_jogador().equals(dataset.get(i).getIdjogador2())) {
                                int emp = partCamp.get(j).getEmpates() + 1;
                                int pro = partCamp.get(j).getGolspro() + pl2;
                                int con = partCamp.get(j).getGolscontra() + pl1;
                                int jog = partCamp.get(j).getJogos() + 1;
                                int pon = partCamp.get(j).getPontos() + pt2;
                                partCamp.get(j).setEmpates(emp);
                                partCamp.get(j).setGolspro(pro);
                                partCamp.get(j).setGolscontra(con);
                                partCamp.get(j).setJogos(jog);
                                partCamp.get(j).setPontos(pon);
                                partCamp.get(j).setSaldogols(pro-con);
                            }
                            break;

                        case 1:
                            if (partCamp.get(j).getId_jogador().equals(dataset.get(i).getIdjogador1())) {
                                int vit = partCamp.get(j).getVitorias() + 1;
                                int pro = partCamp.get(j).getGolspro() + pl1;
                                int con = partCamp.get(j).getGolscontra() + pl2;
                                int jog = partCamp.get(j).getJogos() + 1;
                                int pon = partCamp.get(j).getPontos() + pt1;
                                partCamp.get(j).setVitorias(vit);
                                partCamp.get(j).setGolspro(pro);
                                partCamp.get(j).setGolscontra(con);
                                partCamp.get(j).setJogos(jog);
                                partCamp.get(j).setPontos(pon);
                                partCamp.get(j).setSaldogols(pro-con);
                            }
                            if (partCamp.get(j).getId_jogador().equals(dataset.get(i).getIdjogador2())) {
                                int der = partCamp.get(j).getDerrotas() + 1;
                                int pro = partCamp.get(j).getGolspro() + pl2;
                                int con = partCamp.get(j).getGolscontra() + pl1;
                                int jog = partCamp.get(j).getJogos() + 1;
                                partCamp.get(j).setDerrotas(der);
                                partCamp.get(j).setGolspro(pro);
                                partCamp.get(j).setGolscontra(con);
                                partCamp.get(j).setJogos(jog);
                                partCamp.get(j).setSaldogols(pro-con);
                            }
                            break;
                        case 2:
                            if (partCamp.get(j).getId_jogador().equals(dataset.get(i).getIdjogador2())) {
                                int vit = partCamp.get(j).getVitorias() + 1;
                                int pro = partCamp.get(j).getGolspro() + pl2;
                                int con = partCamp.get(j).getGolscontra() + pl1;
                                int jog = partCamp.get(j).getJogos() + 1;
                                int pon = partCamp.get(j).getPontos() + pt2;
                                partCamp.get(j).setVitorias(vit);
                                partCamp.get(j).setGolspro(pro);
                                partCamp.get(j).setGolscontra(con);
                                partCamp.get(j).setJogos(jog);
                                partCamp.get(j).setPontos(pon);
                                partCamp.get(j).setSaldogols(pro-con);
                            }
                            if (partCamp.get(j).getId_jogador().equals(dataset.get(i).getIdjogador1())) {
                                int der = partCamp.get(j).getDerrotas() + 1;
                                int pro = partCamp.get(j).getGolspro() + pl1;
                                int con = partCamp.get(j).getGolscontra() + pl2;
                                int jog = partCamp.get(j).getJogos() + 1;
                                partCamp.get(j).setDerrotas(der);
                                partCamp.get(j).setGolspro(pro);
                                partCamp.get(j).setGolscontra(con);
                                partCamp.get(j).setJogos(jog);
                                partCamp.get(j).setSaldogols(pro-con);
                            }

                            break;
                    }
                }
            }
        }


        Collections.sort(partCamp, new Comparator() {
            public int compare(Object o1, Object o2) {
                Participantes p1 = (Participantes) o1;
                Participantes p2 = (Participantes) o2;

                if (p1.getPontos() > p2.getPontos()) {
                    return -1;
                } else if (p1.getPontos() < p2.getPontos()) {
                    return 1;
                } else if (p1.getSaldogols() > p2.getSaldogols()) {
                    return -1;
                } else if (p1.getSaldogols() < p2.getSaldogols()) {
                    return 1;

                } else if (p1.getGolspro() > p2.getGolspro()) {
                    return -1;
                } else if (p1.getGolspro() < p2.getGolspro()) {
                    return 1;
                } else if (p1.getGolscontra() < p2.getGolscontra()) {
                    return -1;
                } else if (p1.getGolscontra() > p2.getGolscontra()) {
                    return 1;
                } else if (p1.getJogos() > p2.getJogos()) {
                    return -1;
                } else if (p1.getJogos() < p2.getJogos()) {
                    return 1;
                } else if (p1.getVitorias() > p2.getVitorias()) {
                    return -1;
                } else if (p1.getVitorias() < p2.getVitorias()) {
                    return 1;
                } else if (p1.getDerrotas() < p2.getDerrotas()) {
                    return -1;
                } else if (p1.getDerrotas() > p2.getDerrotas()) {
                    return 1;
                } else if (p1.getEmpates() > p2.getEmpates()) {
                    return -1;
                } else if (p1.getEmpates() < p2.getEmpates()) {
                    return 1;
                } else {
                    return util.sorteia();
                }
            }
        });


        int contclass =1;
        boolean emp = verificaEmpate(partCamp.get(0),partCamp.get(1));

        partCamp.get(0).setClass_jogador(1);
        if(emp){
            partCamp.get(1).setClass_jogador(1);
        }else{
            partCamp.get(1).setClass_jogador(partCamp.get(0).getClass_jogador()+1);
        }

        contclass=3;

        for(int i=2;i<partCamp.size();i++){
            emp = verificaEmpate(partCamp.get(i),partCamp.get(i-1));
            partCamp.get(i).setClass_jogador(emp==true?partCamp.get(i-1).getClass_jogador():contclass);
            contclass++;
        }

        for( int i=0;i<partCamp.size();i++){
            Log.i("partclass","pos: " + i + " class: " + partCamp.get(i).getClass_jogador());
        }

        verificafinal();

    }

    public void verificafinal(){
        boolean f = true;

        if( listajogos.size()==0)f=false;
        for(int i=0;i<listajogos.size();i++){
            if(listajogos.get(i).getStatusjogo()==0){
                f=false;
            }
        }

        if(!f){
            pode_ser_finalista();
        }else{
            if(tipoturno==1 || tipoturno==2){
                montafinalpontos();
            }else {
                montafinal();
            }
        }
    }

    public void pode_ser_finalista(){


        pode_finalistas.clear();
        int maxptgeral=0;
        switch (tipoturno){
            case 1:maxptgeral=(numpart-1)*3;break;
            case 2:maxptgeral=((numpart-1)*3)*2;break;
            case 3:maxptgeral=(numpart-1)*3;break;
            case 4:maxptgeral=((numpart-1)*3)*2;break;
        }

        for (int i=0;i<partCamp.size();i++){
            int maxjogr=0;
            Pode_Finalista ppfin = new Pode_Finalista();
            maxjogr = (maxptgeral - (partCamp.get(i).getJogos() * 3) + partCamp.get(i).getPontos());
            ppfin.setPodeId(partCamp.get(i).getId_jogador());
            ppfin.setPodeMaxPt(maxjogr);
            ppfin.setPodePT(partCamp.get(i).getPontos());
            pode_finalistas.add(ppfin);
        }


        ArrayList<Pode_Finalista> pptemp = pode_finalistas;

        for(int k=0;k<pptemp.size();k++) {
            int ppmax =0;
            for (int i = 0; i < pode_finalistas.size(); i++) {
                if (pptemp.get(k).getPodePT() <= pode_finalistas.get(i).getPodeMaxPt()) {
                    ppmax++;
                }
                pptemp.get(k).setPodeClass(ppmax);
            }

        }


        for(int i=0;i<pode_finalistas.size();i++) {
            Log.i("pode_finalistas", ":"+nomejog(pode_finalistas.get(i).getPodeId())+ " getPodeMaxPt: " +pode_finalistas.get(i).getPodeMaxPt()+" getPodePT: "+pode_finalistas.get(i).getPodePT()+
                    " getPodeClass:" + pptemp.get(i).getPodeClass());
        }
        Log.i("pode_finalistas", " ");

        if(tipoturno==1||tipoturno==2){
            nfin=0;

            if(pptemp.get(0).getPodeClass()==1) {
                Log.i("pode_final", " alerta tdsos jogos");

                nfin=1;

                    util.showmessage(getContext(), "Campeão definido: " + nomejog(pptemp.get(0).getPodeId()));
                    Alerta_Final_Definida alerta_final_definida = new Alerta_Final_Definida(nomejog(pptemp.get(0).getPodeId()), "AA", pptemp.get(0).getPodeId(), "AA", tipoturno);
                    alerta_final_definida.show(getActivity().getSupportFragmentManager(), "DialogTeclado");

            }

            adapter.clear();
            for(int i=0;i<partCamp.size();i++){
                adapter.add(new ParticipantesItem(partCamp.get(i)));
            }
            proximojogo();

        }

        if(tipoturno==3||tipoturno==4) {
            nfin = 0;
            if (pptemp.get(0).getPodeClass() <= 2) nfin++;
            if (pptemp.get(1).getPodeClass()<= 2) nfin++;


            switch (nfin){

                case 1:
                    Log.i("pode_final"," Finalista definido");
                    util.showmessage(getContext(), "Finalista definido: " + nomejog(pptemp.get(0).getPodeId()));
                    break;

                case 2:
                    Log.i("pode_final"," Final definida");
                        util.showmessage(getContext(), "Final definida: " + nomejog(pptemp.get(0).getPodeId()) + " x " + nomejog(pptemp.get(1).getPodeId()));
                        Alerta_Final_Definida alerta_final_definida = new Alerta_Final_Definida(nomejog(pptemp.get(0).getPodeId()), nomejog(pptemp.get(1).getPodeId()), pptemp.get(0).getPodeId(), pptemp.get(1).getPodeId(), tipoturno);
                        alerta_final_definida.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
                    break;
            }
            adapter.clear();
            for(int i=0;i<partCamp.size();i++){
                adapter.add(new ParticipantesItem(partCamp.get(i)));
            }
            proximojogo();

        }
    }

    private void atuaizajogofinal() {

        anima=0;
        if(!blimp){
            blimp=true;
            blimpJogo();
        }

        int statjog = jogoFinal.getStatusjogo();

        cvjogoativo.setBackgroundColor(Color.parseColor("#FF1F6612"));
        txtjog1.setText(nomejog(jogoFinal.getIdjogador1()));
        txtjog2.setText(nomejog(jogoFinal.getIdjogador2()));
        imgjog1.setImageBitmap(imgjog(jogoFinal.getIdjogador1()));
        imgjog2.setImageBitmap(imgjog(jogoFinal.getIdjogador2()));
        txtnumjogo.setText("");

        for(int i=0;i<partCamp.size();i++){
            Log.i("partcampalert","partCamp antes: "+ nomejog(partCamp.get(i).getId_jogador())+" pts: " + partCamp.get(i).getPontos());
        }
        Log.i("partcampalert"," ");

        ArrayList<Participantes> partFinal = new ArrayList<>();
        partFinal.clear();

        for(int i=0;i<partCamp.size();i++){
            Participantes ppnew = new Participantes();

          //  ppnew=partCamp.get(i);
           ppnew.setIdimg(partCamp.get(i).getIdimg()) ;
           ppnew.setId_jogador  (partCamp.get(i).getId_jogador()) ;
           ppnew.setNome_jogador(partCamp.get(i).getNome_jogador());
           ppnew.setId_liga  (partCamp.get(i).getId_liga()) ;
           ppnew.setId_campeonato  (partCamp.get(i).getId_campeonato());
           ppnew.setVitorias     (partCamp.get(i).getVitorias   ()  );
           ppnew.setDerrotas     (partCamp.get(i).getDerrotas   () );
           ppnew.setEmpates      (partCamp.get(i).getEmpates    () );
           ppnew.setGolspro      (partCamp.get(i).getGolspro    () );
           ppnew.setGolscontra   (partCamp.get(i).getGolscontra () );
           ppnew.setClass_jogador(partCamp.get(i).getClass_jogador());
           ppnew.setJogos        (partCamp.get(i).getJogos      () );
           ppnew.setPontos       (partCamp.get(i).getPontos     ()  );
           ppnew.setSaldogols    (partCamp.get(i).getSaldogols  ()  );
           if(!partFinal.contains(ppnew)) {
               partFinal.add(ppnew);
           }
        }


        switch (statjog){

            case 1:
                txttitlejogo.setText("ENCERRADO");
                placar1.setText(jogoFinal.getPlacar1());
                placar2.setText(jogoFinal.getPlacar2());
                if (jogoFinal.getPenalti1().equals("")) {
                    txtp1.setText("");
                    txtp2.setText("");
                } else {
                    txtp1.setText(jogoFinal.getPenalti1());
                    txtp2.setText(jogoFinal.getPenalti2());
                }



                String campeao="";
                String vice="";
                int plcam = 0;
                int plvic = 0;
                int pl1 = Integer.parseInt(jogoFinal.getPlacar1());
                int pl2 = Integer.parseInt(jogoFinal.getPlacar2());

                if(pl1==pl2){

                    int pn1 = Integer.parseInt(jogoFinal.getPenalti1());
                    int pn2 = Integer.parseInt(jogoFinal.getPenalti2());
                    plcam=pl1;
                    plvic=pl2;
                    if(pn1>pn2){
                        campeao=jogoFinal.getIdjogador1();
                        vice=jogoFinal.getIdjogador2();
                    }else{
                        campeao=jogoFinal.getIdjogador2();
                        vice=jogoFinal.getIdjogador1();
                    }

                }else if(pl1>pl2){

                    campeao=jogoFinal.getIdjogador1();
                    vice=jogoFinal.getIdjogador2();
                    plcam=pl1;
                    plvic=pl2;

                }else{

                    campeao=jogoFinal.getIdjogador2();
                    vice=jogoFinal.getIdjogador1();
                    plcam=pl2;
                    plvic=pl1;

                }

                for(int j=0;j<partFinal.size();j++){

                    if(partFinal.get(j).getId_jogador().equals(campeao)){
                        int vit = partFinal.get(j).getVitorias() + 1;
                        int pro = partFinal.get(j).getGolspro() + plcam;
                        int con = partFinal.get(j).getGolscontra() + plvic;
                        int jog = partFinal.get(j).getJogos() + 1;
                        int pon = partFinal.get(j).getPontos() + 3;
                        partFinal.get(j).setVitorias(vit);
                        partFinal.get(j).setGolspro(pro);
                        partFinal.get(j).setGolscontra(con);
                        partFinal.get(j).setJogos(jog);
                        partFinal.get(j).setPontos(pon);
                        partFinal.get(j).setSaldogols(pro - con);
                    }

                    if(partFinal.get(j).getId_jogador().equals(vice)){
                        int der = partFinal.get(j).getDerrotas() + 1;
                        int pro = partFinal.get(j).getGolspro() + plvic;
                        int con = partFinal.get(j).getGolscontra() + plcam;
                        int jog = partFinal.get(j).getJogos() + 1;
                        int pon = partFinal.get(j).getPontos() ;
                        partFinal.get(j).setDerrotas(der);
                        partFinal.get(j).setGolspro(pro);
                        partFinal.get(j).setGolscontra(con);
                        partFinal.get(j).setJogos(jog);
                        partFinal.get(j).setPontos(pon);
                        partFinal.get(j).setSaldogols(pro - con);
                    }
                }

                partTempFinal.clear();

                for (int i=0;i<partFinal.size();i++){
                    if(partFinal.get(i).getId_jogador().equals(campeao)){
                        partFinal.get(i).setClass_jogador(1);
                        partTempFinal.add(partFinal.get(i));
                        break;
                    }
                }

                for (int i=0;i<partFinal.size();i++){
                    if(partFinal.get(i).getId_jogador().equals(vice)){
                        partFinal.get(i).setClass_jogador(2);
                        partTempFinal.add(partFinal.get(i));
                        break;
                    }
                }

                ArrayList<Participantes> ttpart = new ArrayList<>();
                for(int i=0;i<partFinal.size();i++){
                    if(!partFinal.get(i).getId_jogador().equals(campeao) &&
                            !partFinal.get(i).getId_jogador().equals(vice)){
                        ttpart.add(partFinal.get(i));

                    }
                }

                ttpart.get(0).setClass_jogador(3);
                partTempFinal.add(ttpart.get(0));
                boolean emp = false;
                int contclass=4;

                for(int i=1;i<ttpart.size();i++){
                    emp = verificaEmpate(ttpart.get(i), ttpart.get(i - 1));
                    ttpart.get(i).setClass_jogador(emp == true ? ttpart.get(i - 1).getClass_jogador() : contclass);
                    partTempFinal.add(ttpart.get(i));
                    contclass++;
                }

                adapter.clear();
                for(int i=0;i<partTempFinal.size();i++){
                    adapter.add(new ParticipantesItem(partTempFinal.get(i)));
                }


                ctrlJogo=5;
                Alerta_Final_Campeonato alerta_final_campeonato = new Alerta_Final_Campeonato(partTempFinal,tipoturno);
                alerta_final_campeonato.show(getActivity().getSupportFragmentManager(), "DialogTeclado");

                break;

            case 3:
                String id1 = jogoFinal.getIdjogador1();
                String id2 = jogoFinal.getIdjogador2();
                nfin=2;
                ctrlJogo=4;
                montajogoFinal(id1,id2);

                for(int i=0;i<partTempFinal.size();i++){
                    for(int j=0;j<partCamp.size();j++){
                        if(partCamp.get(j).getId_jogador().equals(partTempFinal.get(i).getId_jogador())){
                            partTempFinal.get(i).setPontos(partCamp.get(j).getPontos());
                            partTempFinal.get(i).setVitorias     (partCamp.get(j).getVitorias   ()  );
                            partTempFinal.get(i).setDerrotas     (partCamp.get(j).getDerrotas   () );
                            partTempFinal.get(i).setEmpates      (partCamp.get(j).getEmpates    () );
                            partTempFinal.get(i).setGolspro      (partCamp.get(j).getGolspro    () );
                            partTempFinal.get(i).setGolscontra   (partCamp.get(j).getGolscontra () );
                            partTempFinal.get(i).setJogos        (partCamp.get(j).getJogos      () );
                            partTempFinal.get(i).setPontos       (partCamp.get(j).getPontos     ()  );
                            partTempFinal.get(i).setSaldogols    (partCamp.get(j).getSaldogols  ()  );
                        }
                    }
                }

                adapter.clear();
                for(int i=0;i<partCamp.size();i++){
                    adapter.add(new ParticipantesItem(partFinal.get(i)));
                }
                break;

        }

    }

    private boolean verificaEmpate(Participantes p1, Participantes p2){
        boolean emp =false;

            if(p1.getPontos()==p2.getPontos() &&
                    p1.getSaldogols()==p2.getSaldogols() &&
                    p1.getGolspro()==p2.getGolspro() &&
                    p1.getGolscontra()==p2.getGolscontra() &&
                    p1.getJogos()==p2.getJogos() &&
                    p1.getVitorias()==p2.getVitorias() &&
                    p1.getDerrotas()==p2.getDerrotas() &&
                    p1.getEmpates()==p2.getEmpates()){
                emp=true;
            }


        return emp;
    }

    public void montafinalpontos() {


        int camp =0;
        for(int i=0;i<partCamp.size();i++){
            if(partCamp.get(i).getClass_jogador()==1){
                camp++;
            }
        }

        anima=0;
        if(!blimp){
            blimp=true;
            blimpJogo();
        }

        cvjogoativo.setBackgroundColor(Color.parseColor("#FF1F6612"));



        ctrlJogo=5;
        if(camp==1){

            nfin=1;
            txtjog1.setText(nomejog(partCamp.get(0).getId_jogador()));
            txtjog2.setText(nomejog(partCamp.get(0).getId_jogador()));
            imgjog1.setImageBitmap(imgjog(partCamp.get(0).getId_jogador()));
            imgjog2.setImageBitmap(imgjog(partCamp.get(0).getId_jogador()));
            txtnumjogo.setText("");
            txttitlejogo.setText("ENCERRADO CAMPEÃO");
            placar1.setText("");
            placar2.setText("");
            txtp1.setText("");
            txtp2.setText("");

        }else if (camp == 2) {

            nfin=2;
            txtjog1.setText(nomejog(partCamp.get(0).getId_jogador()));
            txtjog2.setText(nomejog(partCamp.get(1).getId_jogador()));
            imgjog1.setImageBitmap(imgjog(partCamp.get(0).getId_jogador()));
            imgjog2.setImageBitmap(imgjog(partCamp.get(1).getId_jogador()));
            txtnumjogo.setText("");
            txttitlejogo.setText("ENCERRADO CAMPEÕES");
            placar1.setText("");
            placar2.setText("");
            txtp1.setText("");
            txtp2.setText("");
        }else if (camp > 2) {

            nfin=camp;
            txtjog1.setText("Campeões");
            txtjog2.setText("Campeões");
            imgjog1.setImageResource(R.drawable.trofeupretopq);
            imgjog2.setImageResource(R.drawable.trofeupretopq);
            txtnumjogo.setText("");
            txttitlejogo.setText("ENCERRADO " +camp+ " CAMPEÕES");
            placar1.setText("");
            placar2.setText("");
            txtp1.setText("");
            txtp2.setText("");
            String cps ="";
            for (int i=0;i<camp;i++){

                cps=cps+nomejog(partCamp.get(i).getId_jogador())+" ";

            }
            util.showmessage(getContext(),camp+" Campeões: "+cps);
        }

        partTempFinal=partCamp;
        adapter.clear();
        for(int i=0;i<partCamp.size();i++){
            adapter.add(new ParticipantesItem(partCamp.get(i)));
        }
        Alerta_Final_Campeonato alerta_final_campeonato = new Alerta_Final_Campeonato(partCamp,tipoturno);
        alerta_final_campeonato.show(getActivity().getSupportFragmentManager(), "DialogTeclado");

    }

    public void montafinal() {

        int campP =0;
        int campS =0;
        for(int i=0;i<partCamp.size();i++){
            if(partCamp.get(i).getClass_jogador()==1){
                campP++;
            }

            if(partCamp.get(i).getClass_jogador()==2){
                campS++;
            }
        }

        nfin=0;
        String jogfinA = "";
        String jogfinB = "";

        if((campP==1 && campS==1)||(campP==2)){
            ctrlJogo=4;
            nfin=2;
            jogfinA = partCamp.get(0).getId_jogador();
            jogfinB = partCamp.get(1).getId_jogador();
            montajogoFinal(jogfinA,jogfinB);
        }else{
            if((campP==1 && campS>=2)) {
                nfin = 1;
            }else {
                nfin = 0;
            }
                ctrlJogo = 2;
                util.showmessage(getContext(), "Participantes empatados.\nClique em SORTEAR");
                txttitlejogo.setText("SORTEAR");
                txtjog1.setText("SORTEAR");
                txtjog2.setText("SORTEAR");
                txtnumjogo.setText("");
                placar1.setText("_");
                placar2.setText("_");
                txtp1.setText("");
                txtp2.setText("");
                imgjog1.setImageResource(R.drawable.fotoblank);
                imgjog2.setImageResource(R.drawable.fotoblank);

        }

        anima=0;
        if(!blimp){
            blimp=true;
            blimpJogo();
        }

        adapter.clear();
        for(int i=0;i<partCamp.size();i++){
            adapter.add(new ParticipantesItem(partCamp.get(i)));
        }


    }

    public void montajogoFinal(String idf1, String idf2){

        nfin=2;
        adapter.notifyDataSetChanged();

        anima=0;
        if(!blimp){
            blimp=true;
            blimpJogo();
        }


        jogoFinal = new JogosTeste();

        jogoFinal.setIdjogador1(idf1);
        jogoFinal.setIdjogador2(idf2);
        jogoFinal.setKeycampeonato("keyCampeonato");
        jogoFinal.setKeyliga("liga1");
        jogoFinal.setNumjog("FINAL");
        jogoFinal.setPenalti1("");
        jogoFinal.setPenalti2("");
        jogoFinal.setPlacar1("_");
        jogoFinal.setPlacar2("_");
        jogoFinal.setStatusjogo(0);
        jogoFinal.setTurno(0);
        jogoFinal.setIdjogo(100);
        Log.i("jogofinalalerta"," ");
                Log.i("jogofinalalerta"," monta: " + nomejog(jogoFinal.getIdjogador1())+
                " x " + nomejog(jogoFinal.getIdjogador2())+" stat: " + jogoFinal.getStatusjogo());



        txttitlejogo.setText("FINAL");
        cvjogoativo.setBackgroundColor(Color.parseColor("#FF1F6612"));
        txtjog1.setText(nomejog(idf1));
        txtjog2.setText(nomejog(idf2));
        txtnumjogo.setText((dataset.size()+1)+"/"+(dataset.size()+1));
        placar1.setText("_");
        placar2.setText("_");
        txtp1.setText("");
        txtp2.setText("");
        imgjog1.setImageBitmap(imgjog(idf1));
        imgjog2.setImageBitmap(imgjog(idf2));

    }

    public void proximojogo(){

        if(idjogoatual<dataset.size()){
            for(int i=0;i<dataset.size();i++){
                if(dataset.get(i).getStatusjogo()==0){
                    idjogoatual=dataset.get(i).getIdjogo();
                    break;
                }
            }
            anima=0;
        }

        int proxjog=1;
        for(int i=0;i<dataset.size();i++){
            if(dataset.get(i).getStatusjogo()==1){
                proxjog++;
            }
        }

        txttitlejogo.setText("Próximo Jogo:");
        cvjogoativo.setBackgroundColor(Color.parseColor("#3F51B5"));
        JogosTeste jt = dataset.get(idjogoatual);

        if(!blimp){
            blimp=true;
            blimpJogo();
        }


        txtjog1.setText(nomejog(jt.getIdjogador1()));
        txtjog2.setText(nomejog(jt.getIdjogador2()));
        txtnumjogo.setText(proxjog+"/"+dataset.size());
        placar1.setText(jt.getPlacar1());
        placar2.setText(jt.getPlacar2());
        txtp1.setText("");
        txtp2.setText("");
        imgjog1.setImageBitmap(imgjog(jt.getIdjogador1()));
        imgjog2.setImageBitmap(imgjog(jt.getIdjogador2()));

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

    private void blimpJogo() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink =600;
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(anima<9) {
                            if (banima == true) {
                                cljogoativo.setBackgroundColor(Color.TRANSPARENT);
                            } else {
                                cljogoativo.setBackgroundResource(R.color.teste);
                            }
                            banima=!banima;
                            anima++;
                            blimpJogo();
                        }else{
                            blimp=false;
                            anima=0;
                            cljogoativo.setBackgroundColor(Color.TRANSPARENT);
                        }

                    }
                });
            }
        }).start();
    }



}
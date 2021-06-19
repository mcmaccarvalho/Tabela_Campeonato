package com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ponto.ideal.solucoes.tabelacampeonato.Adapters.Joadores_Inscritos_Adapter;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Jogadores_da_Liga_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_Inscritos;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_da_Liga;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.home.HomeFragment;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Inscrever_Jogadores extends Fragment {

    private NovoCampeonatoViewModel ncViewModel;

    private ConstraintLayout clinscricao;
    private LinearLayout llA,llB;
    private Button btvoltar,btavancar;
    private RecyclerView rvjogadores;
    private TextView txtnomejog1, txtnomejog2,txtnomejog3,txtnomejog4,txtnomejog5,txtnomejog6,
    txtnumins,txttipo,txtnumpart;


    public static ArrayList<Jogadores_Inscritos> inscritos = new ArrayList<>();

    private int numpart = 0;
    private int tipoturno = 0;
    private int numjogos = 0;

    public static String jog1;

    public Inscrever_Jogadores() {
        // Required empty public constructor
    }
   public static Inscrever_Jogadores newInstance() {
        Inscrever_Jogadores fragment = new Inscrever_Jogadores();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_inscrever__jogadores, container, false);

        initViews(v);

        return v;
    }

    private void initViews(View v) {
        ncViewModel = ViewModelProviders.of(this).get(NovoCampeonatoViewModel.class);

        numpart = NovoCampeonatoViewModel.getNumpart();
        numjogos = NovoCampeonatoViewModel.getNumjogos();
        tipoturno = NovoCampeonatoViewModel.getTipoturno();


        rvjogadores = v.findViewById(R.id.rvjogadores);

        btavancar =v.findViewById(R.id.btavancar);
        btvoltar =v.findViewById(R.id.btvoltar);
        txtnomejog1 =v.findViewById(R.id.txtnomejog1);
        txtnomejog2 =v.findViewById(R.id.txtnomejog2);
        txtnomejog3 =v.findViewById(R.id.txtnomejog3);
        txtnomejog4 =v.findViewById(R.id.txtnomejog4);
        txtnomejog5 =v.findViewById(R.id.txtnomejog5);
        txtnomejog6 =v.findViewById(R.id.txtnomejog6);
        txtnumins =v.findViewById(R.id.txtnumins);
        txtnumpart =v.findViewById(R.id.txtnumpart);
        txttipo = v.findViewById(R.id.txttipo);
        clinscricao = v.findViewById(R.id.clinscricao);


        txtnumpart.setText(String.valueOf(numpart));
        txttipo.setText(util.retTipoCampeonato(tipoturno));
        inscritos.clear();

        txtnumins.setText("("+inscritos.size()+"/"+numpart+")");
        btavancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numpart>inscritos.size()){
                    util.showSnackError(clinscricao,"Selecione Jogadores participantes");
                    return;
                }
                ncViewModel.setJogadores_inscritos(inscritos);
                MainActivity.sortear=true;
                MainActivity.navController.navigate(R.id.action_inscrever_Jogadores_to_sortear_Jogos);
            }
        });

        btvoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navController.navigate(R.id.action_inscrever_Jogadores_to_novo_Campeonato);
            }
        });

        final ArrayList<Jogadores_da_Liga> orderjog = new ArrayList<>();
        orderjog.clear();

        if(HomeFragment.LIGA_ON.getTipoliga()==0){
            Jogadores_da_Liga_Controller jlc = new Jogadores_da_Liga_Controller(getContext());
            ArrayList<Jogadores_da_Liga> orderjogcomum = jlc.listar_Jogadore_da_Liga(HomeFragment.LIGA_ON.getKeyliga());
            ordenarJogadores(orderjogcomum);
        }else {
            FirebaseFirestore.getInstance().collection("/ligas")
                    .document(HomeFragment.LIGA_ON.getKeyliga())
                    .collection("jogadores")
                    .whereGreaterThan("permissao", 0)
                    .orderBy("permissao", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document != null) {
                                        Jogadores_da_Liga jogadores_da_liga = document.toObject(Jogadores_da_Liga.class);
                                        orderjog.add(jogadores_da_liga);
//                                            adapter.add(new Jogadores_da_LigaIem(jogadores_da_liga));

                                    }
                                }
                                ordenarJogadores(orderjog);
                            } else {
                                Log.d("lista", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

    }

    private void atualizaIns() {

        String nnome[] = new String[6];
        nnome[0]= "";
        nnome[1]= "";
        nnome[2]= "";
        nnome[3]= "";
        nnome[4]= "";
        nnome[5]= "";

        Collections.sort(inscritos, new Comparator() {
            public int compare(Object o1, Object o2) {
                Jogadores_Inscritos p1 = (Jogadores_Inscritos) o1;
                Jogadores_Inscritos p2 = (Jogadores_Inscritos) o2;

                return p1.getNomeins().compareToIgnoreCase(p2.getNomeins());

            }
        });

        for(int i=0;i<inscritos.size();i++){
            nnome[i]=inscritos.get(i).getNomeins();
        }

        txtnomejog1.setText(nnome[0]);
        txtnomejog2.setText(nnome[1]);
        txtnomejog3.setText(nnome[2]);
        txtnomejog4.setText(nnome[3]);
        txtnomejog5.setText(nnome[4]);
        txtnomejog6.setText(nnome[5]);
        txtnumins.setText("("+inscritos.size()+"/"+numpart+")");
    }

    private void ordenarJogadores(ArrayList<Jogadores_da_Liga> orderjog) {

        ArrayList<Jogadores_Inscritos> jogadores_inscritos = new ArrayList<>();
        jogadores_inscritos.clear();

        for (int i=0;i<orderjog.size();i++){
            Jogadores_Inscritos ji = new Jogadores_Inscritos();
            ji.setIdins(orderjog.get(i).getIdjogador());
            ji.setNomeins(orderjog.get(i).getNomejogador());
            ji.setCkecked(false);
            ji.setPosition(i);
            jogadores_inscritos.add(ji);

        }

        Collections.sort(jogadores_inscritos, new Comparator() {
            public int compare(Object o1, Object o2) {
                Jogadores_Inscritos p1 = (Jogadores_Inscritos) o1;
                Jogadores_Inscritos p2 = (Jogadores_Inscritos) o2;

                return p1.getNomeins().compareToIgnoreCase(p2.getNomeins());

            }
        });

        ncViewModel.getBolaltins().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                if(b){
                    atualizaIns();
                    ncViewModel.setBolaltins(false);
                }
            }
        });
            LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(getContext());
        rvjogadores.setLayoutManager(recyclerLayoutManager);


        Joadores_Inscritos_Adapter recyclerViewAdapter = new
                Joadores_Inscritos_Adapter(jogadores_inscritos,getContext(),ncViewModel);
        rvjogadores.setAdapter(recyclerViewAdapter);
    }




}
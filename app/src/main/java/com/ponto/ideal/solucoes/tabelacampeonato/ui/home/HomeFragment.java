package com.ponto.ideal.solucoes.tabelacampeonato.ui.home;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ponto.ideal.solucoes.tabelacampeonato.Adapters.SP_Ligas;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Campeonatos_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Jogadores_da_Liga_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Jogos_Dataset_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Ligas_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Usuarios_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Campeonato_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Jogos_Dataset_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Ligas_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.helper.TipoPrefs;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JCampeonato;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_da_Liga;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JogosTeste;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Ligas;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Participantes;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Usuarios;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.Classificacao;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.NovoCampeonatoViewModel;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Usuarios usuLog  = new Usuarios();
    private String uidLog;

    public static ProgressBar mprogressBar;
    private DatabaseReference databaseReference;

    public static Spinner spcamp;
    private Spinner spinliga;

    private SP_Ligas sp_ligas;


    private Button btnovocamp,btcampativo;
    private TextView txtprijog,txtsegjog,txtterjog,txtprinum,txtsegnum,txtternum,txtinfo;
    private ConstraintLayout clinfoliga;

    public static Ligas LIGA_ON;
    public String ID_LIGA;
    //private NovoCampeonatoViewModel ncViewModel;

    TextView txttemp;
    ImageView imgtemp;
    int contcamp=0;



    private Ligas_Controller lc ;
    private Campeonatos_Controller cc ;
    private Usuarios_Controller uc;
    private Jogadores_da_Liga_Controller jlc;


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<Ligas> nligas;
    private ArrayList<Jogadores_da_Liga> jogadoresAtivos;
    private Ligas testeliga;
    private String testekeyliga;
    private JCampeonato campativo;
    private ArrayList<Participantes> partCamp= new ArrayList<>();
    private String[] listpart;
    private ArrayList<Bitmap> fimg = new ArrayList<>();

    public static ArrayList<Ligas> baseLigas = new ArrayList<>();
    public static ArrayList<Jogadores_da_Liga> baseJogLiga = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(v);

        return v;
    }

    private void initViews(final View v) {

        //todo reinicio 29/12 21:00


        //Jogos_Dataset_Controller jc= new Jogos_Dataset_Controller(getContext());
        //jc.deletarTabela(Jogos_Dataset_DataModel.getTABELA());
        //jc.criarTabela(Jogos_Dataset_DataModel.criarTabela());
        //Campeonatos_Controller cc =  new Campeonatos_Controller(getContext());
       // cc.deletarTabela(Campeonato_DataModel.getTABELA());
        //cc.criarTabela(Campeonato_DataModel.criarTabela());


        txttemp = v.findViewById(R.id.txttemp);
        imgtemp= v.findViewById(R.id.imgtemp);
        spinliga = v.findViewById(R.id.spliga);
        btnovocamp = v.findViewById(R.id.btnovocamp);
        btcampativo = v.findViewById(R.id.btcampativo);
        txtprijog = v.findViewById(R.id.txtprijog);
        txtsegjog = v.findViewById(R.id.txtsegjog);
        txtterjog = v.findViewById(R.id.txtterjog);
        txtprinum = v.findViewById(R.id.txtprinum);
        txtsegnum = v.findViewById(R.id.txtsegnum);
        txtternum = v.findViewById(R.id.txtternum);
        txtinfo = v.findViewById(R.id.txtinfo);
        mprogressBar  = v.findViewById(R.id.mprogressBar);
        clinfoliga = v.findViewById(R.id.clinfoliga);

        btnovocamp.setClickable(false);
        btnovocamp.setEnabled(false);
        btcampativo.setClickable(false);
        btcampativo.setEnabled(false);


        uidLog= FirebaseAuth.getInstance().getUid();
        homeViewModel=MainActivity.homeViewModel;

        homeViewModel.getUsulog().observe(getActivity(),new Observer<Usuarios>() {
            @Override
            public void onChanged(Usuarios usuarios) {
                usuLog  =usuarios;
            }
        });


        FirebaseFirestore.getInstance().collection("/usuarios/")
                .document(uidLog)
                .collection("/ligas")
                .orderBy("nomeliga", Query.Direction.ASCENDING)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            baseLigas.clear();
//                            for (QueryDocumentSnapshot doc : task.getResult()) {
//                                Ligas nnliga = doc.toObject(Ligas.class);
//                                if(nnliga.getStatusliga()!=10)baseLigas.add(nnliga);
//                            }
//                            carregaLigas();
//                        }
//                    }
//                });
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        baseLigas.clear();
                        for(QueryDocumentSnapshot doc:value){
                            Ligas nnliga = doc.toObject(Ligas.class);
                            if(nnliga.getStatusliga()!=10)baseLigas.add(nnliga);
                        }
                        carregaLigas();
                    }
                });

//        lc = new Ligas_Controller(getActivity().getBaseContext());
//        cc = new Campeonatos_Controller(getActivity().getBaseContext());
//        uc = new Usuarios_Controller(getActivity().getBaseContext());



        if(usuLog.getStatus()==100){
//            spinliga.setClickable(false);
//            spinliga.setEnabled(false);
        }


        txtprijog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btcampativo.callOnClick();
                //MainActivity.navController.navigate(R.id.action_nav_home_to_campeonato);
            }
        });



        btnovocamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 30);
                if(campativo!=null) {
                    Campeonatos_Controller cc = new Campeonatos_Controller(getContext());
                    boolean dd = cc.deletarCampTemp(Campeonato_DataModel.getTABELA(), LIGA_ON.getKeyliga(), campativo.getIdcampeonato());
                    Log.i("campativo","dd:" + dd);
                }

                NovoCampeonatoViewModel.setA("A");
                NovoCampeonatoViewModel.setB("B");
                NovoCampeonatoViewModel.setC("C");
                NovoCampeonatoViewModel.setD("D");
                NovoCampeonatoViewModel.setE("E");
                NovoCampeonatoViewModel.setF("F");
                MainActivity.navController.navigate(R.id.action_nav_home_to_novo_Campeonato);
            }
        });

        btcampativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(campativo!=null){

                    String stt = campativo.getParticipantesCamp();
                    listpart = stt.split("#");
                    fimg.clear();
                    partCamp = new ArrayList<>();
                    partCamp.clear();


                    for (int i=0;i<listpart.length;i++) {

                        for (int k = 0; k < jogadoresAtivos.size(); k++) {
                            if (jogadoresAtivos.get(k).getIdjogador().equals(listpart[i])) {
                                Participantes part = new Participantes();
                                part.setId_campeonato(campativo.getIdcampeonato());
                                part.setId_liga(HomeFragment.LIGA_ON.getKeyliga());
                                part.setId_jogador(listpart[i]);
                                part.setNome_jogador(jogadoresAtivos.get(k).getNomejogador());
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

                                Bitmap bitmap = util.loadImageBitmap(getContext(), jogadoresAtivos.get(k).getIdjogador());
                                fimg.add(bitmap);

                            }
                        }
                    }
                }

                NovoCampeonatoViewModel.setCampPart(partCamp);
                NovoCampeonatoViewModel.setBitimgjog(fimg);

                Jogos_Dataset_Controller jc= new Jogos_Dataset_Controller(getContext());
                jc.criarTabela(Jogos_Dataset_DataModel.criarTabela());
                ArrayList<JogosTeste> tdataset = jc.listarDatasetCamp(campativo.getIdcampeonato(),LIGA_ON.getKeyliga());

                int idjogoatual=0;
                if(idjogoatual<tdataset.size()){
                    for(int i=0;i<tdataset.size();i++){
                        if(tdataset.get(i).getStatusjogo()==0){
                            Classificacao.idjogoatual=tdataset.get(i).getIdjogo();
                            break;
                        }
                    }
                }
                Log.i("jogosbanco"," tdataset.size(): " + tdataset.size());


                for(int i=0;i<tdataset.size();i++){
                    Log.i("jogosbanco","getId "+tdataset.get(i).getId() + " Numjog: " + tdataset.get(i).getIdjogo()
                    + " : " + nomejog(tdataset.get(i).getIdjogador1()) + " X " +nomejog(tdataset.get(i).getIdjogador2()));
                }
                Log.i("jogosbanco"," " );
                Collections.sort(tdataset, new Comparator() {
                    public int compare(Object o1, Object o2) {
                        JogosTeste p1 = (JogosTeste) o1;
                        JogosTeste p2 = (JogosTeste) o2;
                        if (p1.getIdjogo() < p2.getIdjogo()) {
                            return -1;
                        }else{
                            return 1;
                        }

                    }
                });

                for(int i=0;i<tdataset.size();i++){
                    Log.i("jogosbanco","dep getId "+tdataset.get(i).getId() + " Numjog: " + tdataset.get(i).getIdjogo()
                            + " : " + nomejog(tdataset.get(i).getIdjogador1()) + " X " +nomejog(tdataset.get(i).getIdjogador2()));
                }

                ArrayList<JogosTeste> pri2 = new ArrayList<>();
                for(int i=0;i<tdataset.size();i++){

                    if(tdataset.get(i).getTurno()==1){
                        pri2.add(tdataset.get(i));
                    }
                }
                NovoCampeonatoViewModel.setPrimeiroturno(pri2);

                ArrayList<JogosTeste> seg2 = new ArrayList<>();
                for(int i=0;i<tdataset.size();i++){
                    if(tdataset.get(i).getTurno()==2){
                        seg2.add(tdataset.get(i));
                    }
                }
                NovoCampeonatoViewModel.setSegundoturno(seg2);

                Classificacao.listajogos=tdataset;
                NovoCampeonatoViewModel.setDataset(tdataset);
                NovoCampeonatoViewModel.setListajogos(tdataset);

                int tipoturno = campativo.getTipoturnocamp();

                NovoCampeonatoViewModel.setTipoturno(tipoturno);
                NovoCampeonatoViewModel.setNumpart(listpart.length);
                Log.i("campativo","tipoturno:" + tipoturno);
                Log.i("campativo","tdataset:" + tdataset.size());
                Log.i("campativo","partCamp:" + partCamp.size());
                Log.i("campativo","seg2:" + seg2.size());
                Log.i("campativo","pri2:" + pri2.size());

                MainActivity.navController.navigate(R.id.action_nav_home_to_campeonato);


            }

        });


        txtinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tpos = util.lerperfs(getContext(),TipoPrefs.spinLiga);


                util.vibra(getContext());

                Log.i("tpos","tpodSinfo: " + tpos );
//                util.vibratePhone(getContext(),(short) 30);
//                Bundle bundle = new Bundle();
//                bundle.putString("idliga",ID_LIGA);
                MainActivity.navController.navigate(R.id.action_nav_home_to_info_Liga);
            }
        });

       // syncLigas();
     //   carregaLigas();
        spinliga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                txtprijog.setText("Jogador");
//                txtprinum.setText("0");
//                txtsegjog.setText("Jogador");
//                txtsegnum.setText("0");
//                txtterjog.setText("Jogador");
//                txtternum.setText("0");
                LIGA_ON = (Ligas) parent.getItemAtPosition(position);
//                ID_LIGA=LIGA_ON.getKeyliga();
//                util.salvarperfs(getContext(), TipoPrefs.Liga,LIGA_ON.getKeyliga());
                util.salvarperfs(getContext(),TipoPrefs.spinLiga,String.valueOf(position));
                carregaJogLiga(LIGA_ON.getKeyliga());
//                checaCampeonato();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if(usuLog.getStatus()==100){
//            nligas = new ArrayList<>();
//            nligas.clear();
//            nligas=lc.listarligas();
//            sp_ligas = new SP_Ligas(getContext(), nligas);
//            sp_ligas.notifyDataSetChanged();
//            spinliga.setAdapter(sp_ligas);
        }else {
//            CollectionReference db = FirebaseFirestore.getInstance().collection("usuarios");
//            CollectionReference usuligas = db.document(uidLog).collection("ligas");
//            usuligas.addSnapshotListener(new EventListener<QuerySnapshot>() {
//                @Override
//                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
//                        if (doc.getType() == DocumentChange.Type.MODIFIED)
//                            Log.i("alterliga", " 1 MODIFIED mlid passou listener ");
//                        Ligas mlid = doc.getDocument().toObject(Ligas.class);
//                        updateLiga(mlid);
//                    }
//                }
//            });
        }



    }

    private void carregaJogLiga(String idliga) {

        FirebaseFirestore.getInstance().collection("/ligas")
                .document(idliga)
                .collection("jogadores")
                .orderBy("nomejogador")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        baseJogLiga.clear();
                        for(QueryDocumentSnapshot doc:value){
                            Jogadores_da_Liga nnjogliga = doc.toObject(Jogadores_da_Liga.class);
                            baseJogLiga.add(nnjogliga);
                        }

                        //TODO remover
                        for(int i=0;i<baseJogLiga.size();i++){
                            Log.i("joglig",i+": JOGLIG: "+baseJogLiga.get(i).getNomejogador());
                        }

                    }
                });


    }


    private void carregaLigas() {

        String tpos = util.lerperfs(getContext(),TipoPrefs.spinLiga);

        int posliga = 0;
        posliga=(tpos==null?posliga:Integer.parseInt(tpos));

        Log.i("tpos","tpodS: " + tpos + "posliga: " + posliga);


        sp_ligas = new SP_Ligas(getContext(), baseLigas);
     //  sp_ligas.notifyDataSetChanged();

        spinliga.setAdapter(sp_ligas);
        spinliga.setSelection(posliga);
    }

    public void checaCampeonato(){

        btnovocamp.setClickable(true);
        btnovocamp.setEnabled(true);
        btcampativo.setClickable(false);
        btcampativo.setEnabled(false);
        Campeonatos_Controller cc =  new Campeonatos_Controller(getContext());
        cc.criarTabela(Campeonato_DataModel.criarTabela());
        campativo = cc.buscarCampeonatoAtivo(LIGA_ON.getKeyliga());
        if(campativo!=null){
            Log.i("campativo"," :" + campativo.getNumerocamp());
            btcampativo.setClickable(true);
            btcampativo.setEnabled(true);
            btcampativo.setVisibility(View.VISIBLE);
            btnovocamp.setText("Descartar Campeonato\nIniciar Novo");
        }else{
            btnovocamp.setText("NOVO\nCAMPEONATO");
            btcampativo.setClickable(false);
            btcampativo.setEnabled(false);
            btcampativo.setVisibility(View.GONE);
            Log.i("campativo"," camp null" );
        }
        carregaAtivos();
    }

    private void updateLiga(Ligas mli) {
                Ligas_Controller lc = new Ligas_Controller(getActivity());
                lc.criarTabela(Ligas_DataModel.criarTabela());
                boolean muda = lc.updateLiga(mli.getStatusliga(),mli.getKeyliga());
               if(muda) {
                   Log.i("alterliga"," lindo " + muda);
                   syncLigas();
               }else{
                   Log.i("alterliga"," chora " + muda);
               }
    }



    private void carregaAtivos() {

        jogadoresAtivos = new ArrayList<>();
        jogadoresAtivos.clear();

        if(usuLog.getStatus()==100){
            jlc = new Jogadores_da_Liga_Controller(getContext());
            jogadoresAtivos = jlc.listar_Jogadore_da_Liga(LIGA_ON.getKeyliga());
            ordenaJogadores(jogadoresAtivos);

        }else {

            FirebaseFirestore.getInstance().collection("/ligas")
                    .document(ID_LIGA)
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
                                jogadoresAtivos.add(jogadores_da_liga);
                            }
                        }
                        ordenaJogadores(jogadoresAtivos);
                    } else {
                        Log.d("lista", "Error getting documents: ", task.getException());
                    }
                }
            });

        }
    }

    private void ordenaJogadores(ArrayList<Jogadores_da_Liga> jogliga) {

        for(int i=0;i<jogliga.size();i++){
            Log.i("jogliga","antes: " + jogliga.get(i).getNomejogador()+" : " + jogliga.get(i).getLugar_1());
        }

        Collections.sort(jogliga, new Comparator() {
            public int compare(Object o1, Object o2) {
                Jogadores_da_Liga p1 = (Jogadores_da_Liga) o1;
                Jogadores_da_Liga p2 = (Jogadores_da_Liga) o2;
                if (p1.getLugar_1() > p2.getLugar_1()) {
                    return -1;
                } else if (p1.getLugar_1() < p2.getLugar_1()) {
                    return 1;
                } else if (p1.getLugar_2() > p2.getLugar_2()) {
                    return -1;
                } else if (p1.getLugar_2() < p2.getLugar_2()) {
                    return 1;
                } else if (p1.getLugar_3() > p2.getLugar_3()) {
                    return -1;
                } else if (p1.getLugar_3() < p2.getLugar_3()) {
                    return 1;
                } else {
                    return p1.getNomejogador().compareToIgnoreCase(p2.getNomejogador());
                }
            }
        });

        int size = jogliga.size();
        if(size>0) {
            if (jogliga.get(0).getNomejogador() != null) {
                txtprijog.setText(jogliga.get(0).getNomejogador());
                txtprinum.setText(String.valueOf(jogliga.get(0).getLugar_1()));
            }
        }
        if(size>1) {
            if (jogliga.get(1).getNomejogador() != null) {
                txtsegjog.setText(jogliga.get(1).getNomejogador());
                txtsegnum.setText(String.valueOf(jogliga.get(1).getLugar_1()));
            }
        }

        if(size>2) {
            if (jogliga.get(2).getNomejogador() != null) {
                txtterjog.setText(jogliga.get(2).getNomejogador());
                txtternum.setText(String.valueOf(jogliga.get(2).getLugar_1()));
            }
        }

        for(int i=0;i<jogliga.size();i++){
            Log.i("jogliga","depois: " + jogliga.get(i).getNomejogador()+" : " + jogliga.get(i).getLugar_1());
        }
    }

    private void syncLigas(){

        final String result = util.lerperfs(getContext(), TipoPrefs.Liga);
        Log.i("alterliga","result : " + result);
        nligas = new ArrayList<>();
        nligas.clear();
        Ligas_Controller lc = new Ligas_Controller(getActivity());
        nligas=lc.listarligas();

        for(int i=0;i<nligas.size();i++){
                if(nligas.get(i).getStatusliga()==10){
                    nligas.remove(i);
                }
        }

       int posliga = Integer.parseInt(util.lerperfs(getContext(),TipoPrefs.spinLiga));
//        for(int i=0;i<nligas.size();i++) {
//            if(nligas.get(i).getKeyliga().equals(result))posliga=i;
//            Log.i("alterliga",i + " ligas depois: " + nligas.get(i).getNomeliga() + ": " + nligas.get(i).getKeyliga() + " : " + posliga);
//        }
//        Log.i("alterliga","posliga : " + posliga);

        sp_ligas = new SP_Ligas(getContext(), baseLigas);
        sp_ligas.notifyDataSetChanged();

        spinliga.setAdapter(sp_ligas);
        spinliga.setSelection(posliga);

    }

    public String nomejog(String id){
        String nome= "www";
        Log.i("partcamp","home: " + partCamp.size());
        for(int i=0;i<partCamp.size();i++){
            if(partCamp.get(i).getId_jogador().equals(id)){
                nome=partCamp.get(i).getNome_jogador();
            }
        }
        return nome;
    }




}

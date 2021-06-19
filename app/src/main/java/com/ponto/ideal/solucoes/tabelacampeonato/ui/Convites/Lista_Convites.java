package com.ponto.ideal.solucoes.tabelacampeonato.ui.Convites;

import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Jogadores_da_Liga_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Usuarios_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.model.ConviteLiga;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_da_Liga;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Ligas;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Notificacoes;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Usuarios;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.Listar_Convites;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;


public class Lista_Convites extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String RET_SYNC ="ret_sync";

    private String mParam1;
    private String mParam2;
    private Boolean ret_sync=false;

    private GroupAdapter adapter;
    RecyclerView rvconvites;

    private ConstraintLayout cllistaconv;

    private CardView cvfecha,cvresposta;
    private ImageView imgjog,imgliga;
    private TextView txtjog,txtliga,txtresposta,txtfinalizar;
    private Button btresponder,btcancelar,btok;
    private RadioGroup rgconvite;
    private RadioButton rbaceitar,rbrecusar;

    boolean abre=false;

    private Listar_Convites_ViewModel lcviewModel;

    private Ligas liga;
    private String uuid;
    private Usuarios usulog;
    private ConviteLiga convitetemp;
    private boolean tem;
    private String tokenresp;
    private Jogadores_da_Liga_Controller jlc;
    private Jogadores_da_Liga jjj;

    public Lista_Convites() {
        // Required empty public constructor
    }


    public static Lista_Convites newInstance(String param1, String param2,Boolean ret_sync,String ret_id_convite,String ret_id_from) {
        Lista_Convites fragment = new Lista_Convites();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putBoolean(RET_SYNC,ret_sync);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            ret_sync = getArguments().getBoolean(RET_SYNC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lista__convites, container, false);
        lcviewModel = ViewModelProviders.of(requireActivity()).get(Listar_Convites_ViewModel.class);
        jlc = new Jogadores_da_Liga_Controller(getActivity().getBaseContext());
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        NotificationManager nMgr = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();

        Listar_Convites.volta = 0;//todo remover ???

        cllistaconv = v.findViewById(R.id.cllistaconv);
        rvconvites = v.findViewById(R.id.rvconvites);
        adapter = new GroupAdapter();
        rvconvites.setLayoutManager(new LinearLayoutManager(getContext()));
        rvconvites.setAdapter(adapter);

        cvresposta = v.findViewById(R.id.cvresposta);
        cvfecha = v.findViewById(R.id.cvfecha);
        imgjog = v.findViewById(R.id.imgjog );
        imgliga = v.findViewById(R.id.imgliga);
        txtjog = v.findViewById(R.id.txtjog);
        txtliga = v.findViewById(R.id.txtliga);
        txtfinalizar = v.findViewById(R.id.txtfinalizar);
        txtresposta = v.findViewById(R.id.txtresposta);
        btresponder = v.findViewById(R.id.btresponder);
        btcancelar = v.findViewById(R.id.btcancelar);
        btok = v.findViewById(R.id.btok);
        rgconvite = v.findViewById(R.id. rgconvite);
        rbaceitar = v.findViewById(R.id. rbaceitar);
        rbrecusar = v.findViewById(R.id. rbrecusar);

        btcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lcviewModel.setLcConviteLiga(null);
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fecha_cardview);
                cvresposta.startAnimation(animation);
                cvresposta.setVisibility(View.INVISIBLE);
                abre=false;
            }
        });

        btresponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!rbaceitar.isChecked() && !rbrecusar.isChecked()) {
                    util.showSnackError(cllistaconv, "Selecione uma opção.");
                    return;
                }

                btcancelar.setClickable(false);
                btcancelar.setEnabled(false);

                if(rbaceitar.isChecked()){
                    verificaJogador();
                }else{
                    recusar();
                }
            }
        });

        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fecha_cardview);
                cvfecha.startAnimation(animation);
                cvfecha.setVisibility(View.INVISIBLE);
                abre=false;

                FirebaseFirestore.getInstance().collection("/convites")
                        .document(FirebaseAuth.getInstance().getUid())
                        .collection("convite_liga")
                        .document(convitetemp.getIdconvite())
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });

                MainActivity.navController.navigate(R.id.action_lista_Convites_to_nav_home);
            }
        });

        uuid=FirebaseAuth.getInstance().getUid();

        Usuarios_Controller uc = new Usuarios_Controller(getActivity().getBaseContext());
        usulog = uc.usuatual(uuid);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {

                if(abre)return;

                Conviteitem conviteitem = (Conviteitem) item;

                convitetemp = conviteitem.conviteLiga;

                lcviewModel.setLcConviteLiga(convitetemp);

                if(conviteitem.conviteLiga.getIdfrom().equals(uuid)){

                    abre=true;
                        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.abre_cardview);
                        cvfecha.startAnimation(animation);
                        cvfecha.setVisibility(View.VISIBLE);
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        final StorageReference storageReference = storage.getReferenceFromUrl(util.loadurl(conviteitem.conviteLiga.getIdto() ));

                        final int heigth = 300;
                        final int width = 300;

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri.toString()).resize(width, heigth).centerCrop().into(imgjog);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                        txtjog.setText(conviteitem.conviteLiga.getNometo());

                        switch (conviteitem.conviteLiga.getStatus() ){
                            case 0:
                                txtresposta.setText("enviado e esta pendente.");
                                txtfinalizar.setText("");
                                break;
                            case 1:
                                txtresposta.setText("recebido e esta pendente.");
                                txtfinalizar.setText("");
                                break;
                            case 2:
                                txtresposta.setText("lido e esta pendente.");
                                txtfinalizar.setText("");
                                break;
                            case 3:
                                txtresposta.setText("ACEITO.");
                                txtfinalizar.setText(getResources().getString(R.string.clique_em_ok_para_finalizar));
                                break;

                            case 4:
                                txtresposta.setText("RECUSADO.");
                                txtfinalizar.setText(getResources().getString(R.string.clique_em_ok_para_finalizar));
                                break;
                        }

                }else{
                    abre=true;
                    if (conviteitem.conviteLiga.getStatus() <= 1 ) {

                        FirebaseFirestore.getInstance().collection("/convites")
                                .document(conviteitem.conviteLiga.getIdfrom())
                                .collection("convite_liga")
                                .document(conviteitem.conviteLiga.getIdconvite())
                                .update("status", 2)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                        FirebaseFirestore.getInstance().collection("/convites")
                                .document(conviteitem.conviteLiga.getIdto())
                                .collection("convite_liga")
                                .document(conviteitem.conviteLiga.getIdconvite())
                                .update("status", 2)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                    }
                        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.abre_cardview);
                        cvresposta.startAnimation(animation);
                        cvresposta.setVisibility(View.VISIBLE);

                        rgconvite.clearCheck();
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        final StorageReference storageReference = storage.getReferenceFromUrl(util.loadurl(conviteitem.conviteLiga.getIdliga() ));

                        final int heigth = 300;
                        final int width = 300;

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri.toString()).resize(width, heigth).centerCrop().into(imgliga);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                        txtliga.setText(conviteitem.conviteLiga.getNomeliga());

                }
            }
        });


        FirebaseFirestore.getInstance().collection("/convites")
                .document(uuid)
                .collection("convite_liga")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();

                        if (documentChanges != null) {
                            syncConvites();
                        }
                    }
                });

    }

    private class Conviteitem extends Item<ViewHolder> {

    private final ConviteLiga conviteLiga;

    private Conviteitem(ConviteLiga conviteLiga) {
        this.conviteLiga = conviteLiga;
    }

    @Override
    public void bind(@NonNull final ViewHolder viewHolder, int position) {

        TextView txtnomeliga = viewHolder.itemView.findViewById(R.id.txtnomeliga);
        TextView txtenviado = viewHolder.itemView.findViewById(R.id.txtenviado);
        TextView txtstatus = viewHolder.itemView.findViewById(R.id.txtstatus);
        final ImageView imgconv = viewHolder.itemView.findViewById(R.id.imgconv);;
        TextView txtdata = viewHolder.itemView.findViewById(R.id.txtdata);;

        Instant it = Instant.ofEpochMilli(conviteLiga.getTimestamp());
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        LocalDateTime dataenvio = zdt.toLocalDateTime();

        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("dd-MM-uu HH:mm")
                .withResolverStyle(ResolverStyle.STRICT);
        String dia = dataenvio.format(fmt);
        String scomp = dia ;

        String stat="";
        String img="";

        if(conviteLiga.getIdfrom().equals(uuid)){

            txtnomeliga.setText(conviteLiga.getNometo());
            img = conviteLiga.getIdto();
            txtenviado.setText(conviteLiga.getNomeliga());
            switch (conviteLiga.getStatus()) {

                case 0:
                    stat = "Enviado";
                    break;
                case 1:
                    stat = "Recebido";
                    break;
                case 2:
                    stat = "Lido";
                    break;
                case 3:
                    stat = "ACEITO. Clique para atualizar.";
                    break;
                case 4:
                    stat = "RECUSADO. Clique para finalizar.";
                    break;
            }

        }else {

            txtnomeliga.setText(conviteLiga.getNomeliga());
            img = conviteLiga.getIdliga();
            txtenviado.setText(conviteLiga.getNomefrom());
            switch (conviteLiga.getStatus()) {

                case 0:
                    stat = "Recebido";
                    break;
                case 1:
                    stat = "Recebido/Pendente";
                    break;
                case 2:
                    stat = "Lido/Pendente";
                    break;
                case 3:
                    stat = "Aceito";
                    break;
                case 4:
                    stat = "Recusado";
                    break;
            }
        }

        if(conviteLiga.getStatus()==0 && !conviteLiga.getIdfrom().equals(uuid)) {

            FirebaseFirestore.getInstance().collection("/convites")
                    .document(conviteLiga.getIdfrom())
                    .collection("convite_liga")
                    .document(conviteLiga.getIdconvite())
                    .update("status", 1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

            FirebaseFirestore.getInstance().collection("/convites")
                    .document(conviteLiga.getIdto())
                    .collection("convite_liga")
                    .document(conviteLiga.getIdconvite())
                    .update("status", 1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
        }

        txtstatus.setText(stat);

        txtdata.setText(scomp);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageReference = storage.getReferenceFromUrl(util.loadurl(img));

        final int heigth = 300;
        final int width = 300;

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.i("fotourl", ": " + uri.toString());
                Picasso.get().load(uri.toString()).resize(width, heigth).centerCrop().into(imgconv);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("fotourl", ": " + e.getMessage() + ": " + e);
            }
        });

    }

    @Override
    public int getLayout() {
        return conviteLiga.getIdfrom().equals(uuid)?
                R.layout.convite_enviado :
                R.layout.convite_recebido;
    }


}

    private void syncConvites() {

        FirebaseFirestore.getInstance().collection("/convites")
                .document(uuid)
                .collection("convite_liga")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        adapter.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document!=null){
                                    ConviteLiga conviteLiga = document.toObject(ConviteLiga.class);
                                    adapter.add(new Conviteitem(conviteLiga));
                                }
                            }
                        } else {
                            Log.d("lista","Error getting documents: ", task.getException());
                        }
                    }
                });



    }

    private void verificaJogador(){
        tem=false;

        FirebaseFirestore.getInstance().collection("ligas")
                .document(convitetemp.getIdliga())
                .collection("jogadores")
                .document(convitetemp.getIdto())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                Log.i("taskres",": atualizaJogador" );
                                atualizaJogador();
                            }else{
                                Log.i("taskres",": criaJogador");
                                criaJogador();
                            }
                        }else{
                            Log.i("taskres",": " + task.getException());
                        }
                    }
                });

    }

    private void atualizaJogador(){

        FirebaseFirestore.getInstance().collection("/ligas")
                .document(convitetemp.getIdliga())
                .collection("jogadores")
                .document(convitetemp.getIdto())
                .update("permissao",convitetemp.getPermissao())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       crialigajogador();
                    }
                });
    }

    private void criaJogador(){

        final Jogadores_da_Liga jl = new Jogadores_da_Liga();
        jl.setNomejogador(usulog.getApelidousu());
        jl.setIdjogador(usulog.getKeyusu());
        jl.setEmailjogador(usulog.getEmailusu());
        jl.setTipojogador("usuario");
        jl.setIdliga(convitetemp.getIdliga());
        jl.setQtdecamp(0);
        jl.setJogos(0);
        jl.setVitorias(0);
        jl.setDerrotas(0);
        jl.setEmpates(0);
        jl.setLugar_1(0);
        jl.setLugar_2(0);
        jl.setLugar_3(0);
        jl.setLugar_4(0);
        jl.setLugar_5(0);
        jl.setGolspro(0);
        jl.setGolscontra(0);
        jl.setPermissao(convitetemp.getPermissao());

        FirebaseFirestore.getInstance().collection("/ligas")
                .document(convitetemp.getIdliga())
                .collection("jogadores")
                .document(convitetemp.getIdto())
                .set(jl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        crialigajogador();
                    }
                });


    }

    private void crialigajogador(){

        Ligas nliga = new Ligas();
        nliga.setKeyliga(convitetemp.getIdliga());
        nliga.setNomeliga(convitetemp.getNomeliga());
        nliga.setDatacadliga(System.currentTimeMillis());
        nliga.setTipoliga(1);
        nliga.setStatusliga(0);
        nliga.setAdmin(convitetemp.getIdfrom());

        FirebaseFirestore.getInstance().collection("/usuarios")
                .document(convitetemp.getIdto())
                .collection("ligas")
                .document(convitetemp.getIdliga())
                .set(nliga)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        aceitar();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        util.showmessage(getContext(), "Problemas ao salvar liga");
                    }
                });

    }

    private void aceitar() {

        FirebaseFirestore.getInstance().collection("/convites")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("convite_liga")
                .document(convitetemp.getIdconvite())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

        FirebaseFirestore.getInstance().collection("/convites")
                .document(convitetemp.getIdfrom())
                .collection("convite_liga")
                .document(convitetemp.getIdconvite())
                .update("status", 3)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getToken(3);
                    }
                });
    }

    private void recusar() {

        FirebaseFirestore.getInstance().collection("/convites")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("convite_liga")
                .document(convitetemp.getIdconvite())
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        FirebaseFirestore.getInstance().collection("/convites")
                .document(convitetemp.getIdfrom())
                .collection("convite_liga")
                .document(convitetemp.getIdconvite())
                .update("status", 4)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getToken(4);
                    }
                });

    }

    private void getToken(final int i) {

        FirebaseFirestore.getInstance().collection("usuarios")
                .document(convitetemp.getIdfrom())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        tokenresp=documentSnapshot.getString("token");

                    }


                });

        enviarNot(i);


    }

    private void enviarNot(final int resp) {

        String resul = "";
        if(resp==3){
            resul=" foi aceito.";
        }else{
            resul=" foi recusado";
        }

        Notificacoes notificacoes = new Notificacoes();
        notificacoes.setFromid(convitetemp.getIdto());
        notificacoes.setFromname(convitetemp.getNometo());
        notificacoes.setToid(convitetemp.getIdfrom());
        String not = "Convite da Liga: " + convitetemp.getNomeliga() + resul;
        notificacoes.setText(not);
        notificacoes.setTimestamp(System.currentTimeMillis());
        if(tokenresp!=null) {
            FirebaseFirestore.getInstance().collection("notificacoes")
                    .document(tokenresp)
                    .set(notificacoes)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
        }

            MainActivity.navController.navigate(R.id.action_lista_Convites_to_nav_home);

    }

}
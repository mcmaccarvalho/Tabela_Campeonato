package com.ponto.ideal.solucoes.tabelacampeonato.ui.home;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Jogadores_da_Liga_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_da_Liga;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Ligas;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.ponto.ideal.solucoes.tabelacampeonato.R.color.fundo_verde_escuro;


public class Info_Liga extends Fragment {

//
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;
    private String midliga;

    private TextView txtliga,txtjog,txtpermatual,txtjogalt;
    private ImageView imgliga,imgshare2,imgjog,imgjogalt;
    private CardView cvestatistica,cvprefs,cvvisitante,cvaltera;
    private ConstraintLayout clinfo,clconvidar;
    private CheckBox rbassistir,rbalterar,rbiniciar,rbbloquear,rbbloquearalt;
    private Button btokprefs,btcancelprefs,btedtfoto,btcancelaltera,btokaltera;

    private RecyclerView rvjogadores;
    private GroupAdapter adapter;

    ArrayList<Jogadores_da_Liga> jogadoresDaLiga = new ArrayList<>();


    private ObjectAnimator animprefs;
    private ObjectAnimator animaltera;

    private Ligas liga;

    private String imgtipo=null;
    private String uidlog;
    private int uidpermission=0;

    boolean abre=false;
    private Jogadores_da_Liga jogtemp;

    public Info_Liga() {

    }

//    public static Info_Liga newInstance(String param1, String param2, String idliga) {
//        Info_Liga fragment = new Info_Liga();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_info__liga, container, false);

        initViews(v);

        return v;
    }

    private void initViews(View v) {

        liga = HomeFragment.LIGA_ON;

        midliga=liga.getKeyliga();
        uidlog = FirebaseAuth.getInstance().getUid();
        rvjogadores = v.findViewById(R.id.rvjogadores);
        txtliga = v.findViewById(R.id.txtliga);
        txtjog = v.findViewById(R.id.txtjog);
        txtjogalt = v.findViewById(R.id.txtjogalt);
        txtpermatual = v.findViewById(R.id.txtpermatual);

        imgliga = v.findViewById(R.id.imgliga);
        imgjog = v.findViewById(R.id.imgjog);
        imgjogalt = v.findViewById(R.id.imgjogalt);
        imgshare2 = v.findViewById(R.id.imgshare2);
        cvestatistica = v.findViewById(R.id.cvestatistica);
        cvvisitante = v.findViewById(R.id.cvvisitante);
        cvaltera = v.findViewById(R.id.cvaltera);
        cvprefs = v.findViewById(R.id.cvprefs);
        clinfo = v.findViewById(R.id.clinfo);
        clconvidar = v.findViewById(R.id.clconvidar);
        rbassistir = v.findViewById(R.id.rbassistir);
        rbalterar  = v.findViewById(R.id.rbalterar );
        rbiniciar  = v.findViewById(R.id.rbiniciar );
        rbbloquear  = v.findViewById(R.id.rbbloquear );
        btcancelprefs  = v.findViewById(R.id.btcancelprefs );
        btokprefs  = v.findViewById(R.id.btokprefs );
        btedtfoto  = v.findViewById(R.id.btedtfoto );
        btcancelaltera  = v.findViewById(R.id.btcancelaltera );
        btokaltera  = v.findViewById(R.id.btokaltera );
        rbbloquearalt  = v.findViewById(R.id.rbbloquearalt );

        animprefs = ObjectAnimator.ofFloat(cvprefs, "translationX", 1000);
        animprefs.setDuration(1);
        animprefs.start();

        animaltera= ObjectAnimator.ofFloat(cvaltera, "translationX", 1000);
        animaltera.setDuration(1);
        animaltera.start();

        jogadoresDaLiga =HomeFragment.baseJogLiga;

        final Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adaptersp = ArrayAdapter.createFromResource(getContext(),
                R.array.status_jog, R.layout.sp_item);
        spinner.getBackground().setColorFilter(Color.parseColor("#1F6612"), PorterDuff.Mode.SRC_ATOP);
        adaptersp.setDropDownViewResource(R.layout.sp_item_drop);
        spinner.setAdapter(adaptersp);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if (abre) return;
                switch(position){
                    case 0:
                    syncJogadores(position);
                    break;
                    case 1:
                          ((TextView) view).setTextColor(Color.RED);
                        syncJogadores(position);
                        break;
                    case 2:
                        syncJogadores(position);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        txtliga.setText(liga.getNomeliga());
        util.showmessage(getContext(),"tipoliga: "+liga.getTipoliga());
        imgshare2.setVisibility((liga.getTipoliga()==0)?View.INVISIBLE:View.VISIBLE);

        Bitmap bitmap = util.loadImageBitmap(getContext(), midliga);
        if(bitmap!=null) {
            imgliga.setImageBitmap(bitmap);
        }else {
            final int heigth = 300;
            final int width = 300;
            FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference storageReference = storage.getReferenceFromUrl(util.loadurl(liga.getKeyliga()));
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri.toString()).resize(width, heigth).centerCrop().into(imgliga);
                    imgtipo=liga.getKeyliga();
                    new DownloadImage().execute(uri.toString());

                }
            });
        }

        if(uidlog.equals(liga.getAdmin()) && liga.getTipoliga()==1){
            clconvidar.setVisibility(View.VISIBLE);
            cvvisitante.setVisibility(View.VISIBLE);
        }

        if(uidlog.equals(liga.getAdmin()) ){
            cvvisitante.setVisibility(View.VISIBLE);
        }

        cvvisitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Log.i("orderjog",": " + ;)
                MainActivity.navController.navigate(R.id.action_info_Liga_to_inserir_Jogador);
            }
        });


        btokprefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                util.vibratePhone(getContext(),(short) 30);
                int permissao = -1;

                if(rbbloquear.isChecked()&&jogtemp.getPermissao()!=0){
                    permissao=0;
                }else if(rbiniciar.isChecked()){
                    permissao=3;
                }else if(rbalterar.isChecked()){
                    permissao=2;
                }else if(rbassistir.isChecked()){
                    permissao=1;
                }

               if(permissao==-1){
                   util.showSnackError(clinfo,"Selecione uma opção.");
                   return;
               }

               if(HomeFragment.LIGA_ON.getTipoliga()==0){
                   Jogadores_da_Liga_Controller jlc = new Jogadores_da_Liga_Controller(getContext());
                   jlc.updateJogador(permissao,jogtemp.getIdjogador(),liga.getKeyliga());
                   Log.i("permission","pasou sqlite " + MainActivity.usuLog.getStatus());
               }else {

                   FirebaseFirestore.getInstance().collection("ligas")
                           .document(liga.getKeyliga())
                           .collection("jogadores")
                           .document(jogtemp.getIdjogador())
                           .update("permissao", permissao)
                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {

                               }
                           })
                           .addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   util.showSnackError(clinfo, "Problemas ao atualizar jogador");
                               }
                           });


                   if (jogtemp.getPermissao() == 0 && permissao >= 0) {
                       FirebaseFirestore.getInstance().collection("usuarios")
                               .document(jogtemp.getIdjogador())
                               .collection("ligas")
                               .document(liga.getKeyliga())
                               .update("acessojogador", true);
                   }

                   if (jogtemp.getPermissao() > 0 && permissao == 0) {
                       FirebaseFirestore.getInstance().collection("usuarios")
                               .document(jogtemp.getIdjogador())
                               .collection("ligas")
                               .document(liga.getKeyliga())
                               .update("acessojogador", false);
                   }
               }
                animprefs.setDuration(200);
                animprefs.start();
                abre=false;



            }
        });

        btcancelprefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animprefs.setDuration(200);
                animprefs.start();
                util.vibratePhone(getContext(),(short) 30);
                jogtemp=null;
                abre=false;

            }
        });

        btcancelaltera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animaltera.setDuration(200);
                animaltera.start();
                util.vibratePhone(getContext(),(short) 30);
                abre=false;
            }
        });

        rbassistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jogtemp.getPermissao()==0 && !rbbloquear.isChecked()) {
                    rbassistir.setChecked(false);
                    return;
                }else{
                    if (!rbassistir.isChecked()) {
                        rbassistir.setChecked(true);
                    }
                }

            }
        });

        rbalterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jogtemp.getPermissao()==0 && !rbbloquear.isChecked()) {
                    rbalterar.setChecked(false);
                    return;
                }else {
                    if (rbalterar.isChecked()) {
                        // rbalterar.setChecked(true);
                    } else {
                        rbalterar.setChecked(false);
                        rbiniciar.setChecked(false);
                    }
                }

            }
        });

        rbiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jogtemp.getPermissao()==0 && !rbbloquear.isChecked()) {
                    rbiniciar.setChecked(false);
                    return;
                }else {
                    if (rbiniciar.isChecked()) {
                        rbalterar.setChecked(true);
                    } else {
                        rbiniciar.setChecked(false);
                    }
                }
            }
        });

        rbbloquear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbbloquear.isChecked() && jogtemp.getPermissao()==0) {
                    rbalterar.setChecked(false);
                    rbiniciar.setChecked(false);
                    rbassistir.setChecked(true);
                }else if(rbbloquear.isChecked()){
                        rbalterar.setChecked(false);
                        rbiniciar.setChecked(false);
                        rbassistir.setChecked(false);
                }else{
                    switch (jogtemp.getPermissao()){
                        case 0:
                            rbalterar.setChecked(false);
                            rbiniciar.setChecked(false);
                            rbassistir.setChecked(false);
                            rbbloquear.setChecked(false);
                            rbbloquear.setText(getResources().getString(R.string.liberar_jogador));
                            break;
                        case 1:
                            rbassistir.setChecked(true);
                            rbalterar.setChecked(false);
                            rbiniciar.setChecked(false);
                            rbbloquear.setChecked(false);
                            rbbloquear.setText(getResources().getString(R.string.bloquear_jogador));
                            break;
                        case 2:
                            rbassistir.setChecked(true);
                            rbalterar.setChecked(true);
                            rbiniciar.setChecked(false);
                            rbbloquear.setChecked(false);
                            rbbloquear.setText(getResources().getString(R.string.bloquear_jogador));
                            break;
                        case 3:
                            rbassistir.setChecked(true);
                            rbalterar.setChecked(true);
                            rbiniciar.setChecked(true);
                            rbbloquear.setChecked(false);
                            rbbloquear.setText(getResources().getString(R.string.bloquear_jogador));
                            break;

                    }

                }
            }
        });

        btedtfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("idjog",jogtemp.getIdjogador() );
                bundle.putString("nomejog",jogtemp.getNomejogador() );
                MainActivity.navController.navigate(R.id.action_info_Liga_to_alterar_Foto_Visitante,bundle);
            }
        });

        btokaltera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                util.vibratePhone(getContext(),(short) 30);

                        if(rbbloquearalt.isChecked()){

                            int muda = jogtemp.getPermissao()==0?100:0;


                            if(HomeFragment.LIGA_ON.getTipoliga()==0){
                                Jogadores_da_Liga_Controller jlc = new Jogadores_da_Liga_Controller(getContext());
                                boolean dd = jlc.updateJogador(muda,jogtemp.getIdjogador(),liga.getKeyliga());
                                syncJogadores(0);
                                animaltera.setDuration(200);
                                animaltera.start();
                                abre = false;
                            }else {
                                FirebaseFirestore.getInstance().collection("ligas")
                                        .document(liga.getKeyliga())
                                        .collection("jogadores")
                                        .document(jogtemp.getIdjogador())
                                        .update("permissao", muda)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                animaltera.setDuration(200);
                                                animaltera.start();
                                                abre = false;
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                util.showSnackError(clinfo, "Problemas ao atualizar jogador");
                                            }
                                        });
                            }
                        }else{
                            animaltera.setDuration(200);
                            animaltera.start();
                            abre=false;
                        }
            }
        });

        adapter = new GroupAdapter();
        rvjogadores.setLayoutManager(new LinearLayoutManager(getContext()));
        rvjogadores.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {

                if (abre) return;
                abre=true;

                Jogadores_da_LigaIem jogitem  = (Jogadores_da_LigaIem) item;
                jogtemp = jogitem.jogadores_da_liga;

                switch(liga.getTipoliga()){
                    case 0:

                        if(liga.getAdmin().equals(jogtemp.getIdjogador())){
                            util.showSnackError(clinfo,getString(R.string.administrador_so_pode_ser_editado_em_perfil));
                            return;
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString("idjog",jogtemp.getIdjogador() );
                        bundle.putString("nomejog",jogtemp.getNomejogador() );
                        MainActivity.navController.navigate(R.id.action_info_Liga_to_alterar_Foto_Visitante,bundle);
                        break;

                    case 1:

                        if(uidpermission!=10){
                            util.showSnackError(clinfo,getString(R.string.voce_nao_tem_permissao_para_editar_jogadores));
                            abre=false;
                            return;
                        }

                        if(liga.getAdmin().equals(jogtemp.getIdjogador())){
                            util.showSnackError(clinfo,getString(R.string.administrador_so_pode_ser_editado_em_perfil));
                            abre=false;
                            return;
                        }

                        util.showSnackError(clinfo,"tipo: "+jogitem.jogadores_da_liga.getTipojog());

                        if(jogitem.jogadores_da_liga.getTipojog()==0){
                            bundle = new Bundle();
                            bundle.putString("idjog",jogtemp.getIdjogador() );
                            bundle.putString("nomejog",jogtemp.getNomejogador() );
                            MainActivity.navController.navigate(R.id.action_info_Liga_to_alterar_Foto_Visitante,bundle);
                        }else{
                            abreprefs();
                        }
                        break;
                }
            }
        });

        cvestatistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 30);
                cvprefs.setVisibility(View.INVISIBLE);
                if(MainActivity.usuLog.getStatus()==100){
                    util.showmessage(getContext(),"Função disponível para usuarios cadastrados");
                    //todo fazer vantagens de ser cadastrado
                }

                abre=false;
            }
        });

        clconvidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (abre) return;
                util.vibratePhone(getContext(),(short) 30);
                Log.i("ligas", " info cv liga: " + liga.getStatusliga());
                int stat = liga.getTipoliga();
                if(stat != 1){
                    util.showSnackError(clinfo,"Só é possivel enviar convites de Ligas Compartilhadas");
                    return;
                }
                MainActivity.navController.navigate(R.id.action_info_Liga_to_convidar_para_Liga);
            }
        });
        syncJogadores(spinner.getSelectedItemPosition());
    }

    private void abreAltera() {

        util.vibratePhone(getContext(),(short) 30);

        if(jogtemp.getIdjogador().equals(uidlog)){

            util.showSnackOk(clinfo,"Você é o Administrador da Liga");

            abre=false;
            return;
        }
        abre=true;
        animaltera.setDuration(200);
        animaltera.reverse();
        Bitmap bitmap = util.loadImageBitmap(getContext(), jogtemp.getIdjogador());
        imgjogalt.setImageBitmap(bitmap);
        txtjogalt.setText(jogtemp.getNomejogador());
        if(jogtemp.getPermissao()==0){
            rbbloquearalt.setText("Desbloquear Jogador");
        }else{
            rbbloquearalt.setText("Bloquear Jogador");
        }

        rbbloquearalt.setChecked(false);
    }

    private void abreprefs() {

        util.vibra(getContext());

//        if(jogtemp.getIdjogador().equals(uidlog)){
//
//            util.showSnackOk(clinfo,"Você é o Administrador da Liga");
//
//            abre=false;
//            return;
//        }
        abre=true;
        animprefs.setDuration(200);
        animprefs.setInterpolator(new FastOutSlowInInterpolator());
        animprefs.reverse();

        Bitmap bitmap = util.loadImageBitmap(getContext(), jogtemp.getIdjogador());
        imgjog.setImageBitmap(bitmap);

        txtpermatual.setText(util.retStatus(jogtemp.getPermissao()));

        txtjog.setText(jogtemp.getNomejogador());
        switch (jogtemp.getPermissao()){
            case 0:
                rbalterar.setChecked(false);
                rbiniciar.setChecked(false);
                rbassistir.setChecked(false);
                rbbloquear.setChecked(false);
                rbbloquear.setText(getResources().getString(R.string.liberar_jogador));
                break;
            case 1:
                rbassistir.setChecked(true);
                rbalterar.setChecked(false);
                rbiniciar.setChecked(false);
                rbbloquear.setChecked(false);
                rbbloquear.setText(getResources().getString(R.string.bloquear_jogador));
                break;
            case 2:
                rbassistir.setChecked(true);
                rbalterar.setChecked(true);
                rbiniciar.setChecked(false);
                rbbloquear.setChecked(false);
                rbbloquear.setText(getResources().getString(R.string.bloquear_jogador));
                break;
            case 3:
                rbassistir.setChecked(true);
                rbalterar.setChecked(true);
                rbiniciar.setChecked(true);
                rbbloquear.setChecked(false);
                rbbloquear.setText(getResources().getString(R.string.bloquear_jogador));
                break;

        }



    }

    private void syncJogadores(int pos){

        final ArrayList<Jogadores_da_Liga> orderjog = new ArrayList<>();
        orderjog.clear();

        switch (pos) {
            case 0:
                for (int i = 0; i < jogadoresDaLiga.size(); i++) {
                    if (jogadoresDaLiga.get(i).getPermissao() > 0) {
                        orderjog.add(jogadoresDaLiga.get(i));
                    }
                }
                break;
            case 1:
                for (int i = 0; i < jogadoresDaLiga.size(); i++) {
                    if (jogadoresDaLiga.get(i).getPermissao() == 0) {
                        orderjog.add(jogadoresDaLiga.get(i));
                    }
                }
                break;
            case 2:
                for (int i = 0; i < jogadoresDaLiga.size(); i++) {
                    orderjog.add(jogadoresDaLiga.get(i));
                }
                break;
        }

        ordenarJogadores(orderjog);
    }

    private void ordenarJogadores(ArrayList<Jogadores_da_Liga> orderjog) {

        Collections.sort(orderjog, new Comparator() {
            public int compare(Object o1, Object o2) {
                Jogadores_da_Liga p1 = (Jogadores_da_Liga) o1;
                Jogadores_da_Liga p2 = (Jogadores_da_Liga) o2;
                    return p1.getNomejogador().compareToIgnoreCase(p2.getNomejogador());
            }
        });

        ArrayList<Jogadores_da_Liga> tmp = new ArrayList<>();
        for(int i=0;i<orderjog.size();i++){
            if(orderjog.get(i).getIdjogador().equals(liga.getAdmin())){
                tmp.add(orderjog.get(i));
                uidpermission=orderjog.get(i).getPermissao();
                break;
            }
        }

        for(int i=0;i<orderjog.size();i++){
            if(!orderjog.get(i).getIdjogador().equals(liga.getAdmin())){
                tmp.add(orderjog.get(i));
            }
        }

        adapter.clear();
        for(int i=0;i<orderjog.size();i++){
            adapter.add(new Jogadores_da_LigaIem(tmp.get(i)));
        }


    }

    private class Jogadores_da_LigaIem extends Item<ViewHolder> {

        private final Jogadores_da_Liga jogadores_da_liga;

        public Jogadores_da_LigaIem(Jogadores_da_Liga jogadores_da_liga) {
            this.jogadores_da_liga = jogadores_da_liga;
        }



        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtnomejog = viewHolder.itemView.findViewById(R.id.txtnomejog);
            TextView txttipo = viewHolder.itemView.findViewById(R.id.txttipo);
            TextView txtperm = viewHolder.itemView.findViewById(R.id.txtperm);
            TextView txtpermission = viewHolder.itemView.findViewById(R.id.txtpermission);
            final ImageView imgjog = viewHolder.itemView.findViewById(R.id.imgjog);
            ImageView imgstar = viewHolder.itemView.findViewById(R.id.imgstar);
            txtnomejog.setText(jogadores_da_liga.getNomejogador());
            txtpermission.setText(util.retStatus(jogadores_da_liga.getPermissao()));

            if(jogadores_da_liga.getPermissao()==0){
                txtpermission.setTextColor(Color.RED);
            }else{
                txtpermission.setTextColor(getResources().getColor(fundo_verde_escuro));
            }

            switch(jogadores_da_liga.getTipojog()){
                case 0:
                    int ii = 0;
                    try {
                        ii = Integer.parseInt(jogadores_da_liga.getImgjogador());
                    } catch (NumberFormatException e) {
                        Log.i("intimg"," erro: " + e.getMessage());
                    }

                    imgjog.setImageResource((int)ii);
                    imgstar.setVisibility(View.GONE);
                    txtpermission.setVisibility(View.GONE);
                    txtperm.setVisibility(View.GONE);
                    txttipo.setText(util.retTipoJogador(jogadores_da_liga.getTipojog()));

                    break;

                case 1:
                    Bitmap bitmap = util.loadImageBitmap(getContext(), jogadores_da_liga.getIdjogador());
                    if (bitmap != null) {
                        imgjog.setImageBitmap(bitmap);
                    } else {
                        final int heigth = 300;
                        final int width = 300;
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        final StorageReference storageReference = storage.getReferenceFromUrl(util.loadurl(jogadores_da_liga.getIdjogador()));
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri.toString()).resize(width, heigth).centerCrop().into(imgjog);
                                new DownloadImage().execute(uri.toString());
                                imgtipo = jogadores_da_liga.getIdjogador();
                            }
                        });
                    }
                    imgstar.setVisibility(View.GONE);
                    txtperm.setVisibility(View.VISIBLE);
                    txtpermission.setVisibility(View.VISIBLE);
                    txttipo.setText(util.retTipoJogador(jogadores_da_liga.getTipojog()));

                    break;

                case 10:
                    Bitmap bitmap2 = util.loadImageBitmap(getContext(), jogadores_da_liga.getIdjogador());
                    if (bitmap2 != null) {
                        imgjog.setImageBitmap(bitmap2);
                    } else {
                        final int heigth = 300;
                        final int width = 300;
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        final StorageReference storageReference = storage.getReferenceFromUrl(util.loadurl(jogadores_da_liga.getIdjogador()));
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri.toString()).resize(width, heigth).centerCrop().into(imgjog);
                                new DownloadImage().execute(uri.toString());
                                imgtipo = jogadores_da_liga.getIdjogador();
                            }
                        });
                    }
                    imgstar.setVisibility(View.VISIBLE);
                    txtperm.setVisibility(View.VISIBLE);
                    txtpermission.setVisibility(View.VISIBLE);
                    txttipo.setText(util.retTipoJogador(jogadores_da_liga.getTipojog()));
            }

        }

        @Override
        public int getLayout() {
            return R.layout.lista_jogadores;
        }
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        private String TAG = "DownloadImage";
        private Bitmap downloadImageBitmap(String sUrl) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
                inputStream.close();
            } catch (Exception e) {
                Log.d(TAG, "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0]);
        }
        protected void onPostExecute(Bitmap result) {
            if(result!=null) {
                if(imgtipo!=null) {
                    util.saveImage(getContext(), result, imgtipo);
                }
            }
        }
    }


}
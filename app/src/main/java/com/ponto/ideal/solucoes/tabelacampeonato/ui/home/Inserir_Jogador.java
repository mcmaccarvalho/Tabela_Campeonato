package com.ponto.ideal.solucoes.tabelacampeonato.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Usuarios_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.helper.TipoPermissao;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_da_Liga;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Usuarios;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;


public class Inserir_Jogador extends Fragment implements View.OnClickListener {

    private static final String TAG = "InfoUsu";

    EditText etapelido;
    Button btsalvar,btcancelar;
    ConstraintLayout clcad_visitante;
    private LinearLayout llprofile,linearLayout6;
    ImageView imgperfil,imggalery,avat1,avat2,avat3,avat4,avat5,avat6,avat7,avat8,avat9,
            avat10,avat11,avat12,avat13,avat14,avat15,avat16,avat17,avat18,avat19,avat20,
            avat21,avat22,avat23,avat24,avat25;
    TextView txtselavatxgal,txtgallery,txttitle;
    private ProgressBar mprogressBar;
    private int idavatar=-1;
    private int avat[] = new int[25];

    private StorageReference storageReference;
    private  static DatabaseReference databaseReference;
    private Usuarios_Controller uc;
    private String uidLog;

    private Usuarios usuLog;

    public Inserir_Jogador() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cad_novo_visitante, container, false);


        clcad_visitante = view.findViewById(R.id.clcad_visitante);
        btsalvar = view.findViewById(R.id.btsalvar);
        btcancelar = view.findViewById(R.id.btcancelar);
        etapelido = view.findViewById(R.id.etapelido);
        imgperfil = view.findViewById(R.id.imgperfil);
        imggalery = view.findViewById(R.id.imggalery);
        txtselavatxgal = view.findViewById(R.id.txtselavatxgal);
        txtgallery = view.findViewById(R.id.txtgallery);
        txttitle = view.findViewById(R.id.txttitle);
        mprogressBar = view.findViewById(R.id.mprogressBar);
        llprofile = view.findViewById(R.id.llprofile);
        linearLayout6 = view.findViewById(R.id.linearLayout6);

        linearLayout6.removeView(llprofile);
        txttitle.setText("Digite um nome para o jogador");
        uc = new Usuarios_Controller(getActivity().getBaseContext());

        uidLog= FirebaseAuth.getInstance().getUid();
//        usuLog  = uc.usuatual(uidLog);

        usuLog=HomeViewModel.getUsuLogado();
        txtselavatxgal.setText("Selecione Avatar");

//        if(usuLog.getStatus()==100){
//            imggalery.setVisibility(View.INVISIBLE);
//            txtgallery.setVisibility(View.INVISIBLE);
//        }


//        imggalery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 123);
//            }
//        });


        etapelido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etapelido.setSelection(etapelido.length());

                }
            }
        });

        avat1        = view.findViewById(R.id.avat1  );
        avat2        = view.findViewById(R.id.avat2  );
        avat3        = view.findViewById(R.id.avat3  );
        avat4        = view.findViewById(R.id.avat4  );
        avat5        = view.findViewById(R.id.avat5  );
        avat6        = view.findViewById(R.id.avat6  );
        avat7        = view.findViewById(R.id.avat7  );
        avat8        = view.findViewById(R.id.avat8  );
        avat9        = view.findViewById(R.id.avat9  );
        avat10       = view.findViewById(R.id.avat10 );
        avat11       = view.findViewById(R.id.avat11 );
        avat12       = view.findViewById(R.id.avat12 );
        avat13       = view.findViewById(R.id.avat13 );
        avat14       = view.findViewById(R.id.avat14 );
        avat15       = view.findViewById(R.id.avat15 );
        avat16       = view.findViewById(R.id.avat16 );
        avat17       = view.findViewById(R.id.avat17 );
        avat18       = view.findViewById(R.id.avat18 );
        avat19       = view.findViewById(R.id.avat19 );
        avat20       = view.findViewById(R.id.avat20 );
        avat21       = view.findViewById(R.id.avat21 );
        avat22       = view.findViewById(R.id.avat22 );
        avat23       = view.findViewById(R.id.avat23 );
        avat24       = view.findViewById(R.id.avat24 );
        avat25       = view.findViewById(R.id.avat25 );

        avat1  .setOnClickListener(this);
        avat2  .setOnClickListener(this);
        avat3  .setOnClickListener(this);
        avat4  .setOnClickListener(this);
        avat5  .setOnClickListener(this);
        avat6  .setOnClickListener(this);
        avat7  .setOnClickListener(this);
        avat8  .setOnClickListener(this);
        avat9  .setOnClickListener(this);
        avat10 .setOnClickListener(this);
        avat11 .setOnClickListener(this);
        avat12 .setOnClickListener(this);
        avat13 .setOnClickListener(this);
        avat14 .setOnClickListener(this);
        avat15 .setOnClickListener(this);
        avat16 .setOnClickListener(this);
        avat17 .setOnClickListener(this);
        avat18 .setOnClickListener(this);
        avat19 .setOnClickListener(this);
        avat20 .setOnClickListener(this);
        avat21 .setOnClickListener(this);
        avat22 .setOnClickListener(this);
        avat23 .setOnClickListener(this);
        avat24 .setOnClickListener(this);
        avat25 .setOnClickListener(this);



        btcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btcancelar.setClickable(true);
                btcancelar.setEnabled(true);
                btsalvar.setClickable(true);
                btsalvar.setEnabled(true);
                MainActivity.navController.navigate(R.id.action_inserir_Jogador_to_info_Liga);

            }
        });

        btsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mprogressBar.setVisibility(View.VISIBLE);
                btcancelar.setClickable(false);
                btcancelar.setEnabled(false);
                btsalvar.setClickable(false);
                btsalvar.setEnabled(false);
                salvanovovisitante();
            }
        });

        avat[0] =  R.drawable.avatar_1;
        avat[1] =  R.drawable.avatar_2;
        avat[2] =  R.drawable.avatar_3;
        avat[3] =  R.drawable.avatar_4;
        avat[4] =  R.drawable.avatar_5;
        avat[5] =  R.drawable.avatar_6;
        avat[6] =  R.drawable.avatar_21;
        avat[7] =  R.drawable.avatar_8;
        avat[8] =  R.drawable.avatar_9;
        avat[9] =  R.drawable.avatar_10;
        avat[10] =  R.drawable.avatar_11;
        avat[11] =  R.drawable.avatar_12;
        avat[12] =  R.drawable.avatar_13;
        avat[13] =  R.drawable.avatar_22;
        avat[14] =  R.drawable.avatar_15;
        avat[15] =  R.drawable.avatar_24;
        avat[16] =  R.drawable.avatar_17;
        avat[17] =  R.drawable.avatar_18;
        avat[18] =  R.drawable.avatar_19;
        avat[19] =  R.drawable.avatar_20;
        avat[20] =  R.drawable.avatar_7;
        avat[21] =  R.drawable.avatar_14;
        avat[22] =  R.drawable.avatar_23;
        avat[23] =  R.drawable.avatar_16;
        avat[24] =  R.drawable.avatar_25;
        idavatar = R.drawable.fotoblank;

        clcad_visitante.setFocusable(true);

        clcad_visitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        clcad_visitante.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.entra_right_alerta));
        return view;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        final int heigth = 300;
        final int width = 300;

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 123) {
                Uri imagemSelecionada = data.getData();
                Picasso.get().load(imagemSelecionada.toString()).resize(width, heigth).centerCrop().into(imgperfil);
                idavatar=0;
            }
        }
    }


    public void salvanovovisitante(){

        util.vibratePhone(getContext(), (short) 30);

        etapelido.setText(etapelido.getText().toString().toUpperCase().trim());
        String napel = etapelido.getText().toString().toUpperCase().trim();

        if(napel.equals(null)|| napel.equals("")){
            mprogressBar.setVisibility(View.INVISIBLE);
            util.showSnackError(clcad_visitante,"Insira um Nick para o jogador.");
            btcancelar.setClickable(true);
            btcancelar.setEnabled(true);
            btsalvar.setClickable(true);
            btsalvar.setEnabled(true);
            return;
        }

        ArrayList<String> nomes = new ArrayList<>();
        nomes.clear();

//        Jogadores_da_Liga_Controller jlc = new Jogadores_da_Liga_Controller(getContext());
//        ArrayList<Jogadores_da_Liga> tjog = jlc.listar_Jogadore_da_Liga(HomeFragment.LIGA_ON.getKeyliga());
        ArrayList<String> ntjog = new ArrayList<>();
        ntjog.clear();
        for (int i=0;i<HomeFragment.baseJogLiga.size();i++){
            ntjog.add(HomeFragment.baseJogLiga.get(i).getNomejogador());
        }

        if(ntjog.contains(napel)){
            mprogressBar.setVisibility(View.INVISIBLE);
            btcancelar.setClickable(true);
            btcancelar.setEnabled(true);
            btsalvar.setClickable(true);
            btsalvar.setEnabled(true);
            util.showSnackError(clcad_visitante, "Já existe um jogador com esse nome. ");
            return;
        }else{
            salvaVisitante();
        }

//        DocumentReference dr = FirebaseFirestore.getInstance().collection("ligas").document(HomeFragment.LIGA_ON.getKeyliga());
//        CollectionReference cr=dr.collection("jogadores");
//        Task<QuerySnapshot> dr2 = cr.whereEqualTo("nomejogador",napel).get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            QuerySnapshot querySnapshot = task.getResult();
//                            if(querySnapshot.isEmpty()){
//                                salvaVisitante(false);
//                            }else{
//                                salvaVisitante(true);
//                            }
//                        }
//                    }
//                });

    }

    private void salvaVisitante() {


            String napel = etapelido.getText().toString().toUpperCase().trim();
            String keyjog = UUID.randomUUID().toString();

            final Jogadores_da_Liga jl = new Jogadores_da_Liga();
            jl.setNomejogador(napel);
            jl.setIdjogador(keyjog);
            jl.setEmailjogador("TabelaCampeonato");
            jl.setTipojogador("local");
            jl.setImgjogador(String.valueOf(idavatar));
            jl.setIdliga(HomeFragment.LIGA_ON.getKeyliga());
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
            jl.setPermissao(TipoPermissao.Visitante);


//            imgperfil.setDrawingCacheEnabled(true);
//            imgperfil.buildDrawingCache();
//
//            final Bitmap bitmap = imgperfil.getDrawingCache();
//
//            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
//            final byte[] data = byteArray.toByteArray();
//
//            util.saveImage(getContext(), bitmap, jl.getIdjogador());
//
//            Jogadores_da_Liga_Controller jlc = new Jogadores_da_Liga_Controller(getContext());
//            jlc.criarTabela(Jogadores_da_Liga_DataModel.criarTabela());
//            jlc.salvar_Jogadores_da_Liga(jl);



                FirebaseFirestore.getInstance().collection("/ligas")
                        .document(HomeFragment.LIGA_ON.getKeyliga())
                        .collection("jogadores")
                        .document(keyjog)
                        .set(jl)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mprogressBar.setVisibility(View.INVISIBLE);
                        util.showmessage(getContext(), "Problemas ao inserir jogador");
                    }
                });

//                storageReference = ConfiguracaoFirebase.getFirebaseStorageReference();
////                StorageReference montaImagemReferencia = storageReference.child(util.saveurl(keyjog));
////
////                imgperfil.setDrawingCacheEnabled(true);
////                imgperfil.buildDrawingCache();
////
////                UploadTask uploadTask = montaImagemReferencia.putBytes(data);
////
////                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
////                    @Override
////                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
////
////                       // volta();
////                    }
////                }).addOnFailureListener(new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception e) {
////                        util.showSnackOk(clcad_visitante, "Problemas ao salvar foto.");
////                    }
////                });





    }

    private void volta() {

        MainActivity.navController.navigate(R.id.action_inserir_Jogador_to_info_Liga);
    }


    @Override
    public void onClick(View v) {

        int iditem = v.getId();

        hideSoftKeyboard();
        switch (iditem){
           case R.id.avat1:imgperfil.setImageResource (avat[0])  ; idavatar =(avat[0]) ;       break;
           case R.id.avat2:imgperfil.setImageResource (avat[1])  ; idavatar =(avat[1]) ;       break;
           case R.id.avat3:imgperfil.setImageResource (avat[2])  ; idavatar =(avat[2]) ;       break;
           case R.id.avat4:imgperfil.setImageResource (avat[3])  ; idavatar =(avat[3]) ;       break;
           case R.id.avat5:imgperfil.setImageResource (avat[4])  ; idavatar =(avat[4]) ;       break;
           case R.id.avat6:imgperfil.setImageResource (avat[5])  ; idavatar =(avat[5]) ;       break;
           case R.id.avat7:imgperfil.setImageResource (avat[6])  ; idavatar =(avat[6]) ;       break;
           case R.id.avat8:imgperfil.setImageResource (avat[7])  ; idavatar =(avat[7]) ;       break;
           case R.id.avat9:imgperfil.setImageResource (avat[8])  ; idavatar =(avat[8]) ;       break;
           case R.id.avat10:imgperfil.setImageResource(avat[9]) ; idavatar = (avat[9]) ;      break;
           case R.id.avat11:imgperfil.setImageResource(avat[10]); idavatar = (avat[10]);      break;
           case R.id.avat12:imgperfil.setImageResource(avat[11]); idavatar = (avat[11]);      break;
           case R.id.avat13:imgperfil.setImageResource(avat[12]); idavatar = (avat[12]);      break;
           case R.id.avat14:imgperfil.setImageResource(avat[13]); idavatar = (avat[13]);      break;
           case R.id.avat15:imgperfil.setImageResource(avat[14]); idavatar = (avat[14]);      break;
           case R.id.avat16:imgperfil.setImageResource(avat[15]); idavatar = (avat[15]);      break;
           case R.id.avat17:imgperfil.setImageResource(avat[16]); idavatar = (avat[16]);      break;
           case R.id.avat18:imgperfil.setImageResource(avat[17]); idavatar = (avat[17]);      break;
           case R.id.avat19:imgperfil.setImageResource(avat[18]); idavatar = (avat[18]);      break;
           case R.id.avat20:imgperfil.setImageResource(avat[19]); idavatar = (avat[19]);      break;
           case R.id.avat21:imgperfil.setImageResource(avat[20]); idavatar = (avat[20]);      break;
           case R.id.avat22:imgperfil.setImageResource(avat[21]); idavatar = (avat[21]);      break;
           case R.id.avat23:imgperfil.setImageResource(avat[22]); idavatar = (avat[22]);      break;
           case R.id.avat24:imgperfil.setImageResource(avat[23]); idavatar = (avat[23]);      break;
           case R.id.avat25:imgperfil.setImageResource(avat[24]); idavatar = (avat[24]);      break;


        }

    }

    private void hideSoftKeyboard() {
        etapelido.setText(etapelido.getText().toString().toUpperCase());
        etapelido.clearFocus();
        try {
            View windowToken = getActivity().getWindow().getDecorView().getRootView();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow( windowToken.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ex) {

        }

    }



}

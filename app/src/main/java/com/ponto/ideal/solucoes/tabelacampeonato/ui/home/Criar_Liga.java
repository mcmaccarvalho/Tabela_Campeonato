package com.ponto.ideal.solucoes.tabelacampeonato.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Jogadores_da_Liga_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Usuarios_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Jogadores_da_Liga_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Usuarios_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.helper.ConfiguracaoFirebase;
import com.ponto.ideal.solucoes.tabelacampeonato.helper.TipoPermissao;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_da_Liga;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Ligas;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Usuarios;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import static com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity.navController;


public class Criar_Liga extends Fragment implements View.OnClickListener {
    private static final String TAG = "InfoUsu";

    EditText etapelido;
    Button btsalvar,btcancelar;
    ConstraintLayout clcad_visitante;
    ImageView imgperfil,imggalery,avat1,avat2,avat3,avat4,avat5,avat6,avat7,avat8,avat9,
            avat10,avat11,avat12,avat13,avat14,avat15,avat16,avat17,avat18,avat19,avat20,imgshare;
    TextView txtselavatxgal,txtgallery,txtcriarliga;
    private ProgressBar mprogressBar;
    private Usuarios usu;

    private int idavatar=-1;
    private int avat[] = new int[20];

    private StorageReference storageReference;
    private  static DatabaseReference databaseReference;

    private int origem;
    private String CAD_KEYUSU;

    private Ligas nliga;
    private String keyliga;


    public Criar_Liga() {

    }

    public static Criar_Liga newInstance(String param1, String param2) {
        Criar_Liga fragment = new Criar_Liga();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.cad_novo_liga, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {

        clcad_visitante = view.findViewById(R.id.clcad_visitante);
        btsalvar = view.findViewById(R.id.btsalvar);
        btcancelar = view.findViewById(R.id.btcancelar);
        etapelido = view.findViewById(R.id.etapelido);
        imgperfil = view.findViewById(R.id.imgperfil);
        imggalery = view.findViewById(R.id.imggalery);
        imgshare = view.findViewById(R.id.imgshare);
        txtselavatxgal = view.findViewById(R.id.txtselavatxgal);
        txtcriarliga = view.findViewById(R.id.txtcriarliga);
        txtgallery = view.findViewById(R.id.txtgallery);

        mprogressBar = view.findViewById(R.id.mprogressBar);


        Usuarios_Controller nuc = new Usuarios_Controller(getActivity().getBaseContext());
        usu = nuc.usuariotual(Usuarios_DataModel.getTABELA(), FirebaseAuth.getInstance().getUid());


        txtcriarliga.setText("Digite um nome para sua\nLiga Compartilhada");
        imgshare.setVisibility(View.VISIBLE);

        imggalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 123);
            }
        });


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


        btcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btcancelar.setClickable(false);
                btcancelar.setEnabled(false);
                btsalvar.setClickable(false);
                btsalvar.setEnabled(false);

              navController.navigate(R.id.action_criar_Liga_to_nav_home);
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
                salvanovoliga();
            }
        });

        avat[0] =  R.drawable.escudo_30;
        avat[1] =  R.drawable.escudo_2;
        avat[2] =  R.drawable.escudo_3;
        avat[3] =  R.drawable.escudo_4;
        avat[4] =  R.drawable.escudo_5;
        avat[5] =  R.drawable.escudo_6;
        avat[6] =  R.drawable.escudo_7;
        avat[7] =  R.drawable.escudo_8;
        avat[8] =  R.drawable.escudo_9;
        avat[9] =  R.drawable.escudo_10;
        avat[10] =  R.drawable.escudo_11;
        avat[11] =  R.drawable.escudo_12;
        avat[12] =  R.drawable.escudo_13;
        avat[13] =  R.drawable.escudo_14;
        avat[14] =  R.drawable.escudo_15;
        avat[15] =  R.drawable.escudo_16;
        avat[16] =  R.drawable.escudo_17;
        avat[17] =  R.drawable.escudo_18;
        avat[18] =  R.drawable.escudo_19;
        avat[19] =  R.drawable.escudo_20;

        clcad_visitante.setFocusable(true);

        clcad_visitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

    }

    public void salvanovoliga(){

        util.vibratePhone(getContext(), (short) 30);

        etapelido.setText(etapelido.getText().toString().toUpperCase().trim());
        String napel = etapelido.getText().toString().toUpperCase().trim();

        if(napel.equals(null)|| napel.equals("")){
            util.showSnackError(clcad_visitante,"Insira um nome para a Liga.");
            mprogressBar.setVisibility(View.INVISIBLE);
            btcancelar.setClickable(true);
            btcancelar.setEnabled(true);
            btsalvar.setClickable(true);
            btsalvar.setEnabled(true);
            return;
        }else if(idavatar==-1){
            util.showSnackError(clcad_visitante,"Selecione um Escudo.");
            mprogressBar.setVisibility(View.INVISIBLE);
            btcancelar.setClickable(true);
            btcancelar.setEnabled(true);
            btsalvar.setClickable(true);
            btsalvar.setEnabled(true);
            return;
        }
        criarliga();
    }

    public void criarliga(){

        String nomeliga = etapelido.getText().toString().toUpperCase().trim();

        keyliga = UUID.randomUUID().toString();
        nliga = new Ligas();
        nliga.setKeyliga(keyliga);
        nliga.setNomeliga(nomeliga);
        nliga.setDatacadliga(System.currentTimeMillis());
        nliga.setTipoliga(1);
        nliga.setStatusliga(0);
        nliga.setAdmin(usu.getKeyusu());

        FirebaseFirestore.getInstance().collection("ligas")
                .document(keyliga)
                .set(nliga)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        storageReference = ConfiguracaoFirebase.getFirebaseStorageReference();
                        StorageReference montaImagemReferencia = storageReference.child(util.saveurl(keyliga));

                        imgperfil.setDrawingCacheEnabled(true);
                        imgperfil.buildDrawingCache();

                        final Bitmap bitmap = imgperfil.getDrawingCache();

                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                        final byte[] data = byteArray.toByteArray();

                        UploadTask uploadTask = montaImagemReferencia.putBytes(data);

                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mprogressBar.setVisibility(View.INVISIBLE);
                                util.showSnackOk(clcad_visitante, "Problemas ao salvar foto.");
                            }
                        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                boolean foto;
                                foto = util.saveImage(getContext(), bitmap, keyliga);
                                if (foto) {
                                    criatabelasVisitante();
                                } else {
                                    util.showmessage(getContext(), "Problemas ao salvar imagem.");
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mprogressBar.setVisibility(View.INVISIBLE);
                        Log.i("verfire", e.toString());
                    }
                    });




    }


    public void criatabelasVisitante(){

        Jogadores_da_Liga_Controller jlc = new Jogadores_da_Liga_Controller(getActivity().getBaseContext());
        jlc.criarTabela(Jogadores_da_Liga_DataModel.criarTabela());

        final Jogadores_da_Liga jl = new Jogadores_da_Liga();
        jl.setNomejogador(usu.getApelidousu());
        jl.setIdjogador(usu.getKeyusu());
        jl.setEmailjogador(usu.getEmailusu());
        jl.setTipojogador("usuario");
        jl.setIdliga(nliga.getKeyliga());
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
        jl.setPermissao(TipoPermissao.Admnistrador);


        boolean jog = false;
        jog = jlc.salvar_Jogadores_da_Liga(jl);

        if(jog) {

            FirebaseFirestore.getInstance().collection("/ligas")
                    .document(keyliga)
                    .collection("jogadores")
                    .document(usu.getKeyusu())
                    .set(jl)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

//                            nliga.setAcessojogador(true);
                            FirebaseFirestore.getInstance().collection("/usuarios")
                                    .document(usu.getKeyusu())
                                    .collection("ligas")
                                    .document(keyliga)
                                    .set(nliga)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            iniciar();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            mprogressBar.setVisibility(View.INVISIBLE);
                                            util.showmessage(getContext(), "Problemas ao salvar liga");
                                        }
                                    });







                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mprogressBar.setVisibility(View.INVISIBLE);
                    util.showmessage(getContext(), "Problemas ao salvar liga");
                }
            });

        }
    }

    public void iniciar(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();

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
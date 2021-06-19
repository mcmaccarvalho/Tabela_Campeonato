package com.ponto.ideal.solucoes.tabelacampeonato.ui.Frags_Login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.Cadastrar_Usuario;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class Cad_Novo_Liga extends Fragment implements View.OnClickListener {

    private static final String TAG = "Infoliga";

    EditText etapelido;
    Button btsalvar,btcancelar;
    ConstraintLayout clcad_visitante;
    ImageView imgperfil,imggalery,imggoogle,avat1,avat2,avat3,avat4,avat5,avat6,avat7,avat8,avat9,
            avat10,avat11,avat12,avat13,avat14,avat15,avat16,avat17,avat18,avat19,avat20;
    TextView txtselavatxgal,txtgallery,txtgoogle;
    private ProgressBar mprogressBar;

    private int avat[] = new int[20];

//    private Usuarios usu;
//    private StorageReference storageReference;
//    private  static DatabaseReference databaseReference;
//    private String CAD_KEYUSU;
//    private Ligas nliga;
//    private String keyliga;


    public  Cad_Novo_Liga() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        txtselavatxgal = view.findViewById(R.id.txtselavatxgal);
        txtgallery = view.findViewById(R.id.txtgallery);
        imggoogle = view.findViewById(R.id.imggoogle);
        txtgoogle = view.findViewById(R.id.txtgoogle);
        mprogressBar = view.findViewById(R.id.mprogressBar);

        switch (Cadastrar_Usuario.origem){
            case 0:
                txtgoogle.setVisibility(View.INVISIBLE);
                imggoogle.setVisibility(View.INVISIBLE);
                break;
            case 1:
                txtgoogle.setVisibility(View.VISIBLE);
                imggoogle.setVisibility(View.VISIBLE);
                break;

        }
        if(Cadastrar_Usuario.CAD_LIGA!=null)etapelido.setText(Cadastrar_Usuario.CAD_LIGA);
        if(Cadastrar_Usuario.CAD_LIGA_BITMAP!=null)imgperfil.setImageBitmap(Cadastrar_Usuario.CAD_LIGA_BITMAP);

        imggalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 1234);
            }
        });

        imggoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.get().load(Cadastrar_Usuario.CAD_URI.toString()).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Cadastrar_Usuario.CAD_LIGA_BITMAP = bitmap;
                        imgperfil.setImageBitmap(bitmap);
                        mprogressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        util.showSnackError(clcad_visitante,getString(R.string.problemas_carregar_foto));
                        mprogressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {}
                });
              //  Picasso.get().load(Cadastrar_Usuario.CAD_URI.toString()).resize(200, 200).centerCrop().into(imgperfil);
            }
        });


        etapelido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Cadastrar_Usuario.CAD_LIGA=etapelido.getText().toString().trim().toUpperCase();
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

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Cad_Novo_Visitante()).commit();


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

        clcad_visitante.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.entra_right_alerta));

    }


    public void salvanovoliga() {
        util.vibratePhone(getContext(), (short) 20);


        String napel = etapelido.getText().toString().toUpperCase().trim();
        etapelido.setText(napel);


        if(napel.equals(null)|| napel.equals("")){
            mprogressBar.setVisibility(View.INVISIBLE);
            util.showSnackError(clcad_visitante,"Insira um nome para a Liga.");
            btcancelar.setClickable(true);
            btcancelar.setEnabled(true);
            btsalvar.setClickable(true);
            btsalvar.setEnabled(true);
            return;
        }


//        imgperfil.setDrawingCacheEnabled(true);
//        imgperfil.buildDrawingCache();
//        Cadastrar_Usuario.CAD_LIGA_BITMAP = imgperfil.getDrawingCache();
        Cadastrar_Usuario.CAD_LIGA = napel;

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Salva_Usuario()).commit();

     //   util.showSnackError(clcad_visitante,"Liga ok.");
      //  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Cad_Novo_Liga(1)).commit();
    }

        //
//        util.vibratePhone(getContext(), (short) 30);
//
//        etapelido.setText(etapelido.getText().toString().toUpperCase().trim());
//        String napel = etapelido.getText().toString().toUpperCase().trim();
//
//        if(napel.equals(null)|| napel.equals("")){
//            util.showSnackError(clcad_visitante,"Insira um nome para a Liga.");
//            mprogressBar.setVisibility(View.INVISIBLE);
//            btcancelar.setClickable(true);
//            btcancelar.setEnabled(true);
//            btsalvar.setClickable(true);
//            btsalvar.setEnabled(true);
//            return;
//        }else if(idavatar==-1){
//            util.showSnackError(clcad_visitante,"Selecione um Escudo.");
//            mprogressBar.setVisibility(View.INVISIBLE);
//            btcancelar.setClickable(true);
//            btcancelar.setEnabled(true);
//            btsalvar.setClickable(true);
//            btsalvar.setEnabled(true);
//            return;
//        }
//            criarliga();
//    }
//
//    public void criarliga(){
//
//        String nomeliga = etapelido.getText().toString().toUpperCase().trim();
//        keyliga = UUID.randomUUID().toString();
//        nliga = new Ligas();
//        nliga.setKeyliga(keyliga);
//        nliga.setNomeliga(nomeliga);
//        nliga.setDatacadliga(System.currentTimeMillis());
//        nliga.setTipoliga(0);
//        nliga.setStatusliga(0);
//        nliga.setAdmin(usu.getKeyusu());
//
//        Ligas_Controller lc = new Ligas_Controller(getActivity().getBaseContext());
//        lc.criarTabela(Ligas_DataModel.criarTabela());
//        lc.salvarliga(nliga);
//
//        imgperfil.setDrawingCacheEnabled(true);
//        imgperfil.buildDrawingCache();
//
//        final Bitmap bitmap = imgperfil.getDrawingCache();
//
//        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
//        final byte[] data = byteArray.toByteArray();
//
//        util.saveImage(getContext(), bitmap, keyliga);
//
//        if(usu.getStatus()==100){
//            criatabelasVisitante();
//        }else {
//            FirebaseFirestore.getInstance().collection("ligas")
//                    .document(keyliga)
//                    .set(nliga)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//
//                            storageReference = ConfiguracaoFirebase.getFirebaseStorageReference();
//                            StorageReference montaImagemReferencia = storageReference.child(util.saveurl(keyliga));
//
//                            UploadTask uploadTask = montaImagemReferencia.putBytes(data);
//
//
//                            uploadTask.addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    mprogressBar.setVisibility(View.INVISIBLE);
//                                    util.showSnackOk(clcad_visitante, "Problemas ao salvar foto.");
//                                }
//                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//
//                                }
//                            });
//                            criatabelasVisitante();
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            mprogressBar.setVisibility(View.INVISIBLE);
//                            Log.i("verfire", e.toString());
//                        }
//
//                    });
//        }
//        }
//    public void criatabelasVisitante(){
//
//        String tjog = origem==100?"Visitante":"usuario";
//        final Jogadores_da_Liga jl = new Jogadores_da_Liga();
//        jl.setNomejogador(usu.getApelidousu());
//        jl.setIdjogador(usu.getKeyusu());
//        jl.setEmailjogador(usu.getEmailusu());
//        jl.setTipojogador(tjog);
//        jl.setIdliga(nliga.getKeyliga());
//        jl.setQtdecamp(0);
//        jl.setJogos(0);
//        jl.setVitorias(0);
//        jl.setDerrotas(0);
//        jl.setEmpates(0);
//        jl.setLugar_1(0);
//        jl.setLugar_2(0);
//        jl.setLugar_3(0);
//        jl.setLugar_4(0);
//        jl.setLugar_5(0);
//        jl.setGolspro(0);
//        jl.setGolscontra(0);
//        jl.setPermissao(TipoPermissao.Admnistrador);
//
//        Jogadores_da_Liga_Controller jlc = new Jogadores_da_Liga_Controller(getActivity());
//        jlc.criarTabela(Jogadores_da_Liga_DataModel.criarTabela());
//        jlc.salvar_Jogadores_da_Liga(jl);
//
//        if(usu.getStatus()==100){
//            iniciar();
//        }else {
//
//            FirebaseFirestore.getInstance().collection("/ligas")
//                    .document(keyliga)
//                    .collection("jogadores")
//                    .document(usu.getKeyusu())
//                    .set(jl)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//
//                            FirebaseFirestore.getInstance().collection("/usuarios")
//                                    .document(usu.getKeyusu())
//                                    .collection("ligas")
//                                    .document(keyliga)
//                                    .set(nliga)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            iniciar();
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            mprogressBar.setVisibility(View.INVISIBLE);
//                                            util.showmessage(getContext(), "Problemas ao salvar liga");
//                                        }
//                                    });
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    mprogressBar.setVisibility(View.INVISIBLE);
//                    util.showmessage(getContext(), "Problemas ao salvar liga");
//                }
//            });
//        }
//        }


    public void iniciar(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Log.i("requestCode"," requestCode: " + requestCode);
            if (requestCode == 1234) {

                mprogressBar.setVisibility(View.VISIBLE);
                Uri imagemSelecionada = data.getData();

                 Picasso.get().load(imagemSelecionada.toString()).resize(200, 200)
                         .centerCrop().into(imgperfil, new Callback() {
                     @Override
                     public void onSuccess() {
                         mprogressBar.setVisibility(View.INVISIBLE);

                         Bitmap bb = null;
                         imgperfil.setDrawingCacheEnabled(true);
                         bb= Bitmap.createBitmap(imgperfil.getDrawingCache());
                         imgperfil.setDrawingCacheEnabled(false);
                         //imgperfil.buildDrawingCache();
                         Cadastrar_Usuario.CAD_LIGA_BITMAP = bb;

//                         theViewYouWantToCapture.setDrawingCacheEnabled(true);
//
//                         viewCapture = Bitmap.createBitmap(theViewYouWantToCapture.getDrawingCache());
//
//                         theViewYouWantToCapture.setDrawingCacheEnabled(false);
                     }

                     @Override
                     public void onError(Exception e) {
                         mprogressBar.setVisibility(View.INVISIBLE);
                         util.showSnackError(clcad_visitante,getString(R.string.problemas_carregar_foto));
                     }
                 });


//                Picasso.get().load(imagemSelecionada.toString()).into(new Target() {
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                        Cadastrar_Usuario.CAD_LIGA_BITMAP = bitmap;
//                        imgperfil.setImageBitmap(bitmap);
//                        mprogressBar.setVisibility(View.INVISIBLE);
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//                        util.showSnackError(clcad_visitante,getString(R.string.problemas_carregar_foto));
//                        mprogressBar.setVisibility(View.INVISIBLE);
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {}
//                });

            }
        }
    }

    @Override
    public void onClick(View v) {

        int iditem = v.getId();
        int idavat=-1;
        hideSoftKeyboard();
        switch (iditem) {
            case R.id.avat1: imgperfil.setImageResource(avat[0]) ;idavat=0;break;
            case R.id.avat2: imgperfil.setImageResource(avat[1]) ;idavat=1;break;
            case R.id.avat3: imgperfil.setImageResource(avat[2]) ;idavat=2;break;
            case R.id.avat4: imgperfil.setImageResource(avat[3]) ;idavat=3;break;
            case R.id.avat5: imgperfil.setImageResource(avat[4]) ;idavat=4;break;
            case R.id.avat6: imgperfil.setImageResource(avat[5]) ;idavat=5;break;
            case R.id.avat7: imgperfil.setImageResource(avat[6]) ;idavat=6;break;
            case R.id.avat8: imgperfil.setImageResource(avat[7]) ;idavat=7;break;
            case R.id.avat9: imgperfil.setImageResource(avat[8]) ;idavat=8;break;
            case R.id.avat10:imgperfil.setImageResource(avat[9]) ;idavat=9;break;
            case R.id.avat11:imgperfil.setImageResource(avat[10]);idavat=10;break;
            case R.id.avat12:imgperfil.setImageResource(avat[11]);idavat=11;break;
            case R.id.avat13:imgperfil.setImageResource(avat[12]);idavat=12;break;
            case R.id.avat14:imgperfil.setImageResource(avat[13]);idavat=13;break;
            case R.id.avat15:imgperfil.setImageResource(avat[14]);idavat=14;break;
            case R.id.avat16:imgperfil.setImageResource(avat[15]);idavat=15;break;
            case R.id.avat17:imgperfil.setImageResource(avat[16]);idavat=16;break;
            case R.id.avat18:imgperfil.setImageResource(avat[17]);idavat=17;break;
            case R.id.avat19:imgperfil.setImageResource(avat[18]);idavat=18;break;
            case R.id.avat20:imgperfil.setImageResource(avat[19]);idavat=19;break;
        }
        if(idavat>=0)salvaBit(idavat);
    }

    private void salvaBit(int idavat) {
        Cadastrar_Usuario.CAD_LIGA_BITMAP = util.drawableToBitmap(getResources().getDrawable(avat[idavat]));
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

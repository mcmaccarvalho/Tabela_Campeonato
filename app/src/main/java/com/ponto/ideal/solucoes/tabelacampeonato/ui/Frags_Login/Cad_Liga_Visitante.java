package com.ponto.ideal.solucoes.tabelacampeonato.ui.Frags_Login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
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
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Ligas_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Usuarios_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Jogadores_da_Liga_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Ligas_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Usuarios_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.helper.ConfiguracaoFirebase;
import com.ponto.ideal.solucoes.tabelacampeonato.helper.TipoPermissao;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_da_Liga;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Ligas;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Usuarios;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;

import java.io.ByteArrayOutputStream;
import java.util.UUID;


public class Cad_Liga_Visitante extends Fragment implements View.OnClickListener {

    private static final String TAG = "InfoUsu";

    EditText etapelido;
    Button btsalvar,btcancelar;
    ConstraintLayout clcad_visitante;
    ImageView imgperfil,imggalery,avat1,avat2,avat3,avat4,avat5,avat6,avat7,avat8,avat9,
            avat10,avat11,avat12,avat13,avat14,avat15,avat16,avat17,avat18,avat19,avat20;
    TextView txtselavatxgal,txtgallery;
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

    private Usuarios_Controller uc;
    private Jogadores_da_Liga_Controller jlc;
    private Ligas_Controller lc;


    public Cad_Liga_Visitante(Usuarios usu) {
        this.usu = usu;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cad_novo_liga, container, false);

        jlc = new Jogadores_da_Liga_Controller(getContext());
        lc = new Ligas_Controller(getContext());
        uc = new Usuarios_Controller(getContext());

        clcad_visitante = view.findViewById(R.id.clcad_visitante);
        btsalvar = view.findViewById(R.id.btsalvar);
        btcancelar = view.findViewById(R.id.btcancelar);
        etapelido = view.findViewById(R.id.etapelido);
        imgperfil = view.findViewById(R.id.imgperfil);
        imggalery = view.findViewById(R.id.imggalery);
        txtselavatxgal = view.findViewById(R.id.txtselavatxgal);

        txtgallery = view.findViewById(R.id.txtgallery);


        mprogressBar = view.findViewById(R.id.mprogressBar);

        CAD_KEYUSU = usu.getKeyusu();

        txtselavatxgal.setText("Selecione Escudo");
        imggalery.setVisibility(View.INVISIBLE);
        txtgallery.setVisibility(View.INVISIBLE);

        etapelido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etapelido.setSelection(0);

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
//                switch (origem){
//                    case 3:
//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                        startActivity(intent);
//                        getActivity(). finish();
//                        break;
//                    default:
//                        AlertaAsk();
//                }

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


        return view;
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
        nliga.setTipoliga(0);
        nliga.setStatusliga(0);
        nliga.setAdmin(usu.getKeyusu());

        lc.criarTabela(Ligas_DataModel.criarTabela());
        lc.salvarliga(nliga);

        imgperfil.setDrawingCacheEnabled(true);
        imgperfil.buildDrawingCache();

        final Bitmap bitmap = imgperfil.getDrawingCache();

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        final byte[] data = byteArray.toByteArray();

        util.saveImage(getContext(), bitmap, nliga.getKeyliga());

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

      // criatabelasVisitante();

    }
    public void criatabelasVisitante(){

        final Jogadores_da_Liga jl = new Jogadores_da_Liga();
        jl.setNomejogador(usu.getApelidousu());
        jl.setIdjogador(usu.getKeyusu());
        jl.setEmailjogador(usu.getEmailusu());
        jl.setTipojogador("Visitante");
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

        jlc.criarTabela(Jogadores_da_Liga_DataModel.criarTabela());
        jlc.salvar_Jogadores_da_Liga(jl);

        iniciar();

    }


    public void iniciar(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
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

    public void AlertaAsk() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_ask);

        ImageView imgdialog = dialog.findViewById(R.id.imgdialog);
        TextView txttitle = dialog.findViewById(R.id.txttitle);
        TextView txtdbody = dialog.findViewById(R.id.txtbody);
        Button btsim = dialog.findViewById(R.id.btsim);
        Button btnao = dialog.findViewById(R.id.btnao);

        ConstraintLayout cldialog = dialog.findViewById(R.id.cldialog);

        imgdialog.setImageResource(R.drawable.trofeudourado);
        txttitle.setText("Tabela Campeonato");
        txtdbody.setText("Deseja cancelar a instalação do\nTabela Campeonato ?");
        txtdbody.setGravity(Gravity.CENTER_HORIZONTAL);
        btsim.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.entra_sobe_alerta));
        btnao.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.entra_sobe_alerta));
        btnao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btcancelar.setClickable(true);
                btcancelar.setEnabled(true);
                btsalvar.setClickable(true);
                btsalvar.setEnabled(true);
                dialog.dismiss();
            }
        });
        btsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                util.showmessage(getContext(), "Obrigado por utilizar o Tabela Campeonato.");
                FirebaseAuth auth = FirebaseAuth.getInstance();

                mprogressBar.setVisibility(View.VISIBLE);

                uc = new Usuarios_Controller(getActivity().getBaseContext());
                uc.deletarTabela(Usuarios_DataModel.getTABELA());

                jlc = new Jogadores_da_Liga_Controller(getActivity().getBaseContext());
                jlc.deletarTabela(Jogadores_da_Liga_DataModel.getTABELA());

                lc = new Ligas_Controller(getActivity().getBaseContext());
                lc.deletarTabela(Ligas_DataModel.getTABELA());

                String [] arrquivos = util.listafile(getContext());

                for (int i=0;i<arrquivos.length;i++){
                    getContext().deleteFile(arrquivos[i]);
                }

                if (auth.getCurrentUser() != null) {
                    auth.getCurrentUser().delete();
                }


                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        dialog.dismiss();
                        getActivity().finish();
                    }
                }, 2000);

            }
        });

        dialog.show();
    }

}

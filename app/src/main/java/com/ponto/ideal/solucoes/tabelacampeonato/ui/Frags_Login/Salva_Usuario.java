package com.ponto.ideal.solucoes.tabelacampeonato.ui.Frags_Login;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.helper.ConfiguracaoFirebase;
import com.ponto.ideal.solucoes.tabelacampeonato.helper.TipoPermissao;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_da_Liga;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Ligas;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Usuarios;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.Cadastrar_Usuario;
import com.ponto.ideal.solucoes.tabelacampeonato.view.Login;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.UUID;


public class Salva_Usuario extends Fragment implements View.OnClickListener{

    private TextView txtnome,txtemail,txtcancelavat,txtbolaperf,txtbolaliga,txtprogress,txttextoprogress;
    private EditText edtapelido,edtliga;
    private Button btsalvar,btcancelar;
    private ProgressBar mprogressBar,horizontalBar;
    private ConstraintLayout clsalva;
    private String CAD_EMAIL, CAD_NOME, CAD_SENHA,CAD_APELIDO,CAD_KEY,CAD_LIGA,KEY_LIGA;
    private Uri photoUrl;
    private int avat[] = new int[50];
    ImageView imgperfil,imgliga,imggalery,imggoogle,avat1,avat2,avat3,avat4,avat5,avat6,avat7,avat8,avat9,
            avat10,avat11,avat12,avat13,avat14,avat15,avat16,avat17,avat18,avat19,avat20,
            avat21,avat22,avat23,avat24,avat25;

    private CardView cvavatar,cvprogres;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private boolean avatesc=false;
    private boolean bperf = false;
    private boolean bliga = false;
    private boolean blok = false;

    private ObjectAnimator objperf;
    private ObjectAnimator bolaperf;
    private ObjectAnimator bolaliga;

    public Salva_Usuario() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_salva__usuario, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {

        txtnome=view.findViewById(R.id.txtnome);
        txtemail=view.findViewById(R.id.txtemail);
        edtapelido=view.findViewById(R.id.edtapelido);
        edtliga=view.findViewById(R.id.edtliga);
        imgperfil=view.findViewById(R.id.imgperfil);
        imgliga=view.findViewById(R.id.imgliga);
        imggalery=view.findViewById(R.id.imggalery);
        imggoogle=view.findViewById(R.id.imggoogle);
        btsalvar=view.findViewById(R.id.btsalvar);
        btcancelar=view.findViewById(R.id.btcancelar);
        txtbolaperf=view.findViewById(R.id.txtbolaperf);
        txtbolaliga=view.findViewById(R.id.txtbolaliga);
        cvavatar=view.findViewById(R.id.cvavatar);
        clsalva=view.findViewById(R.id.clsalva);
        txtcancelavat=view.findViewById(R.id.txtcancelavat);
        mprogressBar=view.findViewById(R.id.mprogressBar);
        horizontalBar=view.findViewById(R.id.horizontalBar);
        txtprogress=view.findViewById(R.id.txtprogress);
        txttextoprogress=view.findViewById(R.id.txttextoprogress);
        cvprogres=view.findViewById(R.id.cvprogres);

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

        objperf = ObjectAnimator.ofFloat(cvavatar, "translationX", 1200);
        objperf.setDuration((long) 0.1);
        objperf.start();
        objperf.setDuration(300);

        bolaperf = ObjectAnimator.ofFloat(txtbolaperf , "alpha", 0);
        bolaliga = ObjectAnimator.ofFloat(txtbolaliga, "alpha", 0);
        bolaperf.setDuration((long) 0.1);
        bolaliga.setDuration((long) 0.1);
        bolaperf.start();
        bolaliga.start();
        bolaperf.setDuration(500);
        bolaliga.setDuration(500);

        edtapelido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtapelido.setText(edtapelido.getText().toString().toUpperCase());
                if (!hasFocus) {
                    util.vibratePhone(getContext(),(short) 20);
                    edtapelido.setSelection(edtapelido.length());
                }
            }
        });

        edtliga.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtliga.setText(edtliga.getText().toString().toUpperCase());
                if (!hasFocus) {
                    util.vibratePhone(getContext(),(short) 20);
                    edtliga.setSelection(edtliga.length());
                }
            }
        });

        clsalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                hideSoftKeyboard();
                edtapelido.clearFocus();
                edtliga.clearFocus();
                edtapelido.setText(edtapelido.getText().toString().toUpperCase());
                edtliga.setText(edtliga.getText().toString().toUpperCase());
            }
        });

        txtcancelavat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                objperf.start();
                if(bliga)bolaliga.start();
                if(bperf)bolaperf.start();
                bliga=false;
                bperf=false;
            }
        });

        imgperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                hideSoftKeyboard();
                edtapelido.clearFocus();
                edtliga.clearFocus();
                edtapelido.setText(edtapelido.getText().toString().toUpperCase());
                edtliga.setText(edtliga.getText().toString().toUpperCase());
                avatesc = false;
                cvavatar.setVisibility(View.VISIBLE);
                txtbolaperf.setVisibility(View.VISIBLE);

                if (!bperf) {

                    bolaperf.reverse();
                    if (bliga) {

                        ObjectAnimator objsai = ObjectAnimator.ofFloat(cvavatar, "translationX", 1200);
                        objsai.setDuration(300);
                        objsai.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                objperf.reverse();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        bolaliga.start();
                        objsai.start();
                    } else {
                        objperf.reverse();
                    }
                    bliga = false;
                    bperf = !bperf;

                    avat1.setImageResource(avat[0]);
                    avat2.setImageResource(avat[1]);
                    avat3.setImageResource(avat[2]);
                    avat4.setImageResource(avat[3]);
                    avat5.setImageResource(avat[4]);
                    avat6.setImageResource(avat[5]);
                    avat7.setImageResource(avat[6]);
                    avat8.setImageResource(avat[7]);
                    avat9.setImageResource(avat[8]);
                    avat10.setImageResource(avat[9]);
                    avat11.setImageResource(avat[10]);
                    avat12.setImageResource(avat[11]);
                    avat13.setImageResource(avat[12]);
                    avat14.setImageResource(avat[13]);
                    avat15.setImageResource(avat[14]);
                    avat16.setImageResource(avat[15]);
                    avat17.setImageResource(avat[16]);
                    avat18.setImageResource(avat[17]);
                    avat19.setImageResource(avat[18]);
                    avat20.setImageResource(avat[19]);
                    avat21.setImageResource(avat[20]);
                    avat22.setImageResource(avat[21]);
                    avat23.setImageResource(avat[22]);
                    avat24.setImageResource(avat[23]);
                    avat25.setImageResource(avat[24]);
                } else {
                    bolaperf.start();
                    bperf = !bperf;
                    objperf.start();
                }
            }
        });

        imgliga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                hideSoftKeyboard();
                edtapelido.clearFocus();
                edtliga.clearFocus();
                edtapelido.setText(edtapelido.getText().toString().toUpperCase());
                edtliga.setText(edtliga.getText().toString().toUpperCase());
                avatesc=true;
                cvavatar.setVisibility(View.VISIBLE);
                txtbolaliga.setVisibility(View.VISIBLE);
                txtbolaperf.setVisibility(View.VISIBLE);

                if(!bliga) {

                    bolaliga.reverse();
                    if(bperf){

                        ObjectAnimator objsai = ObjectAnimator.ofFloat(cvavatar, "translationX", 1200);
                        objsai.setDuration(300);
                        objsai.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                objperf.reverse();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        bolaperf.start();
                        objsai.start();
                    }else {
                        objperf.reverse();
                    }
                    bperf=false;
                    bliga=!bliga;

                    avat1.setImageResource(avat[25]);
                    avat2.setImageResource(avat[26]);
                    avat3.setImageResource(avat[27]);
                    avat4.setImageResource(avat[28]);
                    avat5.setImageResource(avat[29]);
                    avat6.setImageResource(avat[30]);
                    avat7.setImageResource(avat[31]);
                    avat8.setImageResource(avat[32]);
                    avat9.setImageResource(avat[33]);
                    avat10.setImageResource(avat[34]);
                    avat11.setImageResource(avat[35]);
                    avat12.setImageResource(avat[36]);
                    avat13.setImageResource(avat[37]);
                    avat14.setImageResource(avat[38]);
                    avat15.setImageResource(avat[39]);
                    avat16.setImageResource(avat[40]);
                    avat17.setImageResource(avat[41]);
                    avat18.setImageResource(avat[42]);
                    avat19.setImageResource(avat[43]);
                    avat20.setImageResource(avat[44]);
                    avat21.setImageResource(avat[45]);
                    avat22.setImageResource(avat[46]);
                    avat23.setImageResource(avat[47]);
                    avat24.setImageResource(avat[48]);
                    avat25.setImageResource(avat[49]);

                } else {
                    bolaliga.start();
                    bliga=!bliga;
                    objperf.start();
                }
            }
        });

        imggalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 1234);
            }
        });

        imggoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.get().load(photoUrl.toString()).resize(300, 300)
                        .centerCrop().into(bperf?imgperfil:imgliga, new Callback() {
                    @Override
                    public void onSuccess() {
                       showProgressBar(false);
                        txtcancelavat.callOnClick();
                    }

                    @Override
                    public void onError(Exception e) {
                        imgperfil.setImageResource(bperf?R.drawable.fotoblank:R.drawable.cartolinha250);
                        mprogressBar.setVisibility(View.INVISIBLE);
                        txtcancelavat.callOnClick();
                        util.showSnackError(clsalva,getString(R.string.problemas_carregar_foto));
                    }
                });

            }
        });

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

        avat[25] =  R.drawable.escudo_30;
        avat[26] =  R.drawable.escudo_2;
        avat[27] =  R.drawable.escudo_3;
        avat[28] =  R.drawable.escudo_4;
        avat[29] =  R.drawable.escudo_5;
        avat[30] =  R.drawable.escudo_6;
        avat[31] =  R.drawable.escudo_7;
        avat[32] =  R.drawable.escudo_8;
        avat[33] =  R.drawable.escudo_9;
        avat[34] =  R.drawable.escudo_10;
        avat[35] =  R.drawable.escudo_11;
        avat[36] =  R.drawable.escudo_12;
        avat[37] =  R.drawable.escudo_13;
        avat[38] =  R.drawable.escudo_14;
        avat[39] =  R.drawable.escudo_15;
        avat[40] =  R.drawable.escudo_16;
        avat[41] =  R.drawable.escudo_17;
        avat[42] =  R.drawable.escudo_18;
        avat[43] =  R.drawable.escudo_19;
        avat[44] =  R.drawable.escudo_20;
        avat[45] =  R.drawable.escudo_21;
        avat[46] =  R.drawable.escudo_22;
        avat[47] =  R.drawable.escudo_1;
        avat[48] =  R.drawable.escudo_24;
        avat[49] =  R.drawable.escudo_25;

        CAD_APELIDO="";
        CAD_EMAIL="";
        CAD_KEY="";
        CAD_NOME="";
        CAD_SENHA="";

        switch (Cadastrar_Usuario.origem){
            case 0:
                btcancelar.setText(getString(R.string.anterior));
                imggoogle.setVisibility(View.GONE);
                if(Cadastrar_Usuario.CAD_NOME!=null)CAD_NOME=Cadastrar_Usuario.CAD_NOME;
                if(Cadastrar_Usuario.CAD_EMAIL!=null)CAD_EMAIL=Cadastrar_Usuario.CAD_EMAIL;
                if(Cadastrar_Usuario.CAD_SENHA!=null)CAD_SENHA=Cadastrar_Usuario.CAD_SENHA;
                String apelido ="";
                for (int i = 0; i < CAD_NOME.length(); i++) {
                    if (!CAD_NOME.substring(i,i+1).equals(" ")){
                        apelido += CAD_NOME.substring(i,i+1);
                    }else{
                        break;
                    }
                }
                CAD_APELIDO = apelido;
                break;

            case 1:

                mAuth=FirebaseAuth.getInstance();
                imggoogle.setVisibility(View.VISIBLE);
                showProgressBar(true);
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {

                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestProfile()
                            .requestEmail()
                            .build();
                    mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

                    for (UserInfo profile : user.getProviderData()) {

                        String providerId = profile.getProviderId();

                        CAD_KEY = profile.getUid();
                        CAD_NOME = profile.getDisplayName().toUpperCase();
                        CAD_EMAIL = profile.getEmail().toLowerCase();
                        photoUrl = profile.getPhotoUrl();


                        String apelido2 ="";
                        for (int i = 0; i < CAD_NOME.length(); i++) {
                            if (!CAD_NOME.substring(i,i+1).equals(" ")){
                                apelido2 += CAD_NOME.substring(i,i+1);
                            }else{
                                break;
                            }
                        }
                        CAD_APELIDO = apelido2;
                        Picasso.get().load(photoUrl.toString()).resize(200, 200)
                                .centerCrop().into(imgperfil, new Callback() {
                            @Override
                            public void onSuccess() {
                               showProgressBar(false);
//                                Bitmap bb = null;
//                                imgperfil.setDrawingCacheEnabled(true);
//                                bb= Bitmap.createBitmap(imgperfil.getDrawingCache());
//                                imgperfil.setDrawingCacheEnabled(false);
//
                            }

                            @Override
                            public void onError(Exception e) {
                                showProgressBar(false);
                                util.showSnackError(clsalva,getString(R.string.problemas_carregar_foto));
                            }
                        });

                    }

                }

                break;
        }

//        Cadastrar_Usuario.CAD_EMAIL=CAD_EMAIL;
        Cadastrar_Usuario.CAD_KEYUSU=CAD_KEY;
        txtnome.setText(CAD_NOME);
        txtemail.setText(CAD_EMAIL);
        edtapelido.setText(CAD_APELIDO);

        btcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                switch (Cadastrar_Usuario.origem){
                    case 0:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Cad_Usu_frag()).commit();
                        break;
                    case 1:
                       AlertaAsk();
                        break;

                }
             }
        });

        btsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                salvaTudo();
            }
        });

        clsalva.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.entra_right_alerta));
    }

    private void salvaTudo() {

        boolean ok = false;
        hideSoftKeyboard();

        if (edtapelido.getText().toString().trim().isEmpty() || edtapelido.getText().toString().equals("")) {
            util.showSnackError(clsalva, getString(R.string.digite_apelido));
            return;
        } else if (edtliga.getText().toString().trim().isEmpty() || edtliga.getText().toString().equals("")) {
            util.showSnackError(clsalva, getString(R.string.digite_um_nome_para_sua_liga));
            return;
        } else {
            showProgressBar(true);
            cvprogres.setVisibility(View.VISIBLE);
            ok = true;
        }

        if (ok ) {
            CAD_APELIDO = edtapelido.getText().toString().toUpperCase().trim();
            CAD_LIGA = edtliga.getText().toString().toUpperCase().trim();
             bloqueiaTD(true);
            if(Cadastrar_Usuario.origem==1){
                cadastraUser();
            }else{
                criaCredencial();
            }
        }
    }

    private void criaCredencial() {
        mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(CAD_EMAIL,CAD_SENHA).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    horizontalBar.setProgress(20);
                    txtprogress.setText("1");
                    txttextoprogress.setText(getString(R.string.pb_ativa_6) + getString(R.string.criando_credenciais));
                    cadastraUser();
                }else{
                    Log.i("cadastro", "createUserWithEmail:failure" + task.getException());
                }
            }
        });
    }

    private void cadastraUser() {

        CAD_KEY=mAuth.getUid();
        long timestamp = System.currentTimeMillis();

        Usuarios anonimo = new Usuarios();
        anonimo.setKeyusu(CAD_KEY);
        anonimo.setNomeusu(CAD_NOME);
        anonimo.setApelidousu(CAD_APELIDO);
        anonimo.setEmailusu(CAD_EMAIL);
        anonimo.setImagemusuario(CAD_KEY);
        anonimo.setTimestamp(timestamp);
        anonimo.setStatus(1);
        anonimo. setToken("");
        anonimo.setOnline(0);
        anonimo. setPermicao("1");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        DocumentReference dr = db.collection("/usuarios").document(CAD_KEY);
        batch.set(dr,anonimo);
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                horizontalBar.setProgress(40);
                txtprogress.setText("2");
                txttextoprogress.setText(getString(R.string.pb_ativa_6) + getString(R.string.criando_usuario));
                salvaImgPerf();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clsalva, "Problemas ao salvar Usuário. Informe administrador.");

            }
        });

    }

    private void salvaImgPerf(){

        StorageReference storageReference = ConfiguracaoFirebase.getFirebaseStorageReference();
        StorageReference montaImagemReferencia = storageReference.child(util.saveurl(CAD_KEY));

        String url = storageReference+ "fotoPerfilUsuario/"
                + FirebaseAuth.getInstance().getUid();

        imgperfil.setDrawingCacheEnabled(true);
        imgperfil.buildDrawingCache();

        final Bitmap bitmap = imgperfil.getDrawingCache();

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        final byte[] data = byteArray.toByteArray();

        UploadTask uploadTask = montaImagemReferencia.putBytes(data);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                horizontalBar.setProgress(60);
                txtprogress.setText("3");
                txttextoprogress.setText(getString(R.string.pb_ativa_6) + getString(R.string.salvando_imagem_perfil));
              crialiga();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showProgressBar(false);
                util.showSnackOk(clsalva, "Problemas ao salvar foto.");
            }
        });

    }

    private void crialiga() {

        String nomeliga = edtliga.getText().toString().toUpperCase().trim();
        KEY_LIGA = UUID.randomUUID().toString();
        Ligas nliga = new Ligas();
        nliga.setKeyliga(KEY_LIGA);
        nliga.setNomeliga(nomeliga);
        nliga.setDatacadliga(System.currentTimeMillis());
        nliga.setTipoliga(0);
        nliga.setStatusliga(0);
        nliga.setAdmin(CAD_KEY);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        DocumentReference dr = db.collection("/ligas")
                .document(KEY_LIGA);
        batch.set(dr, nliga);
        dr = db.collection("/usuarios").document(CAD_KEY).collection("ligas")
                .document(KEY_LIGA);
        batch.set(dr, nliga);
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                horizontalBar.setProgress(80);
                txtprogress.setText("4");
                txttextoprogress.setText(getString(R.string.pb_ativa_6) + getString(R.string.criando_liga));
               salvaImgLiga();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clsalva, "Problemas ao salvar Liga. Informe administrador.");

            }
        });



    }

    private void salvaImgLiga() {
        StorageReference storageReference = ConfiguracaoFirebase.getFirebaseStorageReference();
        StorageReference montaImagemReferencia = storageReference.child(util.saveurl(KEY_LIGA));

        String url = storageReference+ "fotoPerfilUsuario/"
                + FirebaseAuth.getInstance().getUid();

        imgliga.setDrawingCacheEnabled(true);
        imgliga.buildDrawingCache();

        final Bitmap bitmap = imgliga.getDrawingCache();

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        final byte[] data = byteArray.toByteArray();

        UploadTask uploadTask = montaImagemReferencia.putBytes(data);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                horizontalBar.setProgress(100);
                txtprogress.setText("5");
                txttextoprogress.setText(getString(R.string.pb_ativa_6) + getString(R.string.salvando_imagem_liga));
                criaJogador();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showProgressBar(false);

                util.showSnackOk(clsalva, "Problemas ao salvar foto.");
            }
        });

    }

    private void criaJogador() {

        final Jogadores_da_Liga jl = new Jogadores_da_Liga();
        jl.setNomejogador(CAD_APELIDO);
        jl.setIdjogador(CAD_KEY);
        jl.setEmailjogador(CAD_EMAIL);
        jl.setTipojogador("Player");
        jl.setImgjogador(CAD_KEY);
        jl.setTipojog(1);
        jl.setIdliga(KEY_LIGA);
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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        DocumentReference dr = db.collection("/ligas")
                .document(KEY_LIGA).collection("jogadores").document(CAD_KEY);
        batch.set(dr, jl);
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                horizontalBar.setProgress(120);
                txtprogress.setText("6");
                txttextoprogress.setText(getString(R.string.pb_ativa_6) + getString(R.string.criando_jogador));
                cvprogres.setVisibility(View.GONE);
                util.showSnackOk(clsalva, "Usuário criado com sucesso!");
                showProgressBar(false);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clsalva, "Problemas ao salvar Liga. Informe administrador.");

            }
        });


    }

    @Override
    public void onClick(View v) {
        int iditem = v.getId();
        int idavat=-1;
        util.vibratePhone(getContext(),(short) 20);
        if(!avatesc){
            switch (iditem){

                case R.id.avat1:imgperfil.setImageResource (avat[0])  ; idavat= 0 ;    break;
                case R.id.avat2:imgperfil.setImageResource (avat[1])  ; idavat= 1 ;    break;
                case R.id.avat3:imgperfil.setImageResource (avat[2])  ; idavat= 2 ;    break;
                case R.id.avat4:imgperfil.setImageResource (avat[3])  ; idavat= 3 ;    break;
                case R.id.avat5:imgperfil.setImageResource (avat[4])  ; idavat= 4 ;    break;
                case R.id.avat6:imgperfil.setImageResource (avat[5])  ; idavat= 5 ;    break;
                case R.id.avat7:imgperfil.setImageResource (avat[6])  ; idavat= 6 ;    break;
                case R.id.avat8:imgperfil.setImageResource (avat[7])  ; idavat= 7 ;    break;
                case R.id.avat9:imgperfil.setImageResource (avat[8])  ; idavat= 8 ;    break;
                case R.id.avat10:imgperfil.setImageResource(avat[9]) ;  idavat= 9 ;    break;
                case R.id.avat11:imgperfil.setImageResource(avat[10]);  idavat= 10;    break;
                case R.id.avat12:imgperfil.setImageResource(avat[11]);  idavat= 11;    break;
                case R.id.avat13:imgperfil.setImageResource(avat[12]);  idavat= 12;    break;
                case R.id.avat14:imgperfil.setImageResource(avat[13]);  idavat= 13;    break;
                case R.id.avat15:imgperfil.setImageResource(avat[14]);  idavat= 14;    break;
                case R.id.avat16:imgperfil.setImageResource(avat[15]);  idavat= 15;    break;
                case R.id.avat17:imgperfil.setImageResource(avat[16]);  idavat= 16;    break;
                case R.id.avat18:imgperfil.setImageResource(avat[17]);  idavat= 17;    break;
                case R.id.avat19:imgperfil.setImageResource(avat[18]);  idavat= 18;    break;
                case R.id.avat20:imgperfil.setImageResource(avat[19]);  idavat= 19;    break;
                case R.id.avat21:imgperfil.setImageResource(avat[20]);  idavat= 20;    break;
                case R.id.avat22:imgperfil.setImageResource(avat[21]);  idavat= 21;    break;
                case R.id.avat23:imgperfil.setImageResource(avat[22]);  idavat= 22;    break;
                case R.id.avat24:imgperfil.setImageResource(avat[23]);  idavat= 23;    break;
                case R.id.avat25:imgperfil.setImageResource(avat[24]);  idavat= 24;    break;
            }

        }else {

            switch (iditem){

                case R.id.avat1:imgliga.setImageResource (avat[25])  ; idavat= 0 ;    break;
                case R.id.avat2:imgliga.setImageResource (avat[26])  ; idavat= 1 ;    break;
                case R.id.avat3:imgliga.setImageResource (avat[27])  ; idavat= 2 ;    break;
                case R.id.avat4:imgliga.setImageResource (avat[28])  ; idavat= 3 ;    break;
                case R.id.avat5:imgliga.setImageResource (avat[29])  ; idavat= 4 ;    break;
                case R.id.avat6:imgliga.setImageResource (avat[30])  ; idavat= 5 ;    break;
                case R.id.avat7:imgliga.setImageResource (avat[31])  ; idavat= 6 ;    break;
                case R.id.avat8:imgliga.setImageResource (avat[32])  ; idavat= 7 ;    break;
                case R.id.avat9:imgliga.setImageResource (avat[33])  ; idavat= 8 ;    break;
                case R.id.avat10:imgliga.setImageResource(avat[34]) ;  idavat= 9 ;    break;
                case R.id.avat11:imgliga.setImageResource(avat[35]);  idavat= 10;    break;
                case R.id.avat12:imgliga.setImageResource(avat[36]);  idavat= 11;    break;
                case R.id.avat13:imgliga.setImageResource(avat[37]);  idavat= 12;    break;
                case R.id.avat14:imgliga.setImageResource(avat[38]);  idavat= 13;    break;
                case R.id.avat15:imgliga.setImageResource(avat[39]);  idavat= 14;    break;
                case R.id.avat16:imgliga.setImageResource(avat[40]);  idavat= 15;    break;
                case R.id.avat17:imgliga.setImageResource(avat[41]);  idavat= 16;    break;
                case R.id.avat18:imgliga.setImageResource(avat[42]);  idavat= 17;    break;
                case R.id.avat19:imgliga.setImageResource(avat[43]);  idavat= 18;    break;
                case R.id.avat20:imgliga.setImageResource(avat[44]);  idavat= 19;    break;
                case R.id.avat21:imgliga.setImageResource(avat[45]);  idavat= 20;    break;
                case R.id.avat22:imgliga.setImageResource(avat[46]);  idavat= 21;    break;
                case R.id.avat23:imgliga.setImageResource(avat[47]);  idavat= 22;    break;
                case R.id.avat24:imgliga.setImageResource(avat[48]);  idavat= 23;    break;
                case R.id.avat25:imgliga.setImageResource(avat[49]);  idavat= 24;    break;
            }

        }

        objperf.start();
        if(bliga)bolaliga.start();
        if(bperf)bolaperf.start();
        bliga=false;
        bperf=false;

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 1234) {
               showProgressBar(true);
               bloqueiaTD(true);
                Uri imagemSelecionada = data.getData();
                if(bperf) {
                    Picasso.get().load(imagemSelecionada.toString()).resize(200, 200)
                            .centerCrop().into(imgperfil, new Callback() {
                        @Override
                        public void onSuccess() {
                            bloqueiaTD(false);
                            txtcancelavat.callOnClick();
                            showProgressBar(false);
                        }

                        @Override
                        public void onError(Exception e) {
                            bloqueiaTD(false);
                            showProgressBar(false);
                            imgperfil.setImageResource(R.drawable.fotoblank);
                            txtcancelavat.callOnClick();
                            util.showSnackError(clsalva,getString(R.string.problemas_carregar_foto));
                        }
                    });
                }
                if(bliga) {
                    Picasso.get().load(imagemSelecionada.toString()).resize(200, 200)
                            .centerCrop().into(imgliga,new Callback() {
                        @Override
                        public void onSuccess() {
                            bloqueiaTD(false);
                            txtcancelavat.callOnClick();
                            showProgressBar(false);
                        }

                        @Override
                        public void onError(Exception e) {
                            bloqueiaTD(false);
                            showProgressBar(false);
                            imgliga.setImageResource(R.drawable.cartolinha250);
                            txtcancelavat.callOnClick();
                            util.showSnackError(clsalva,getString(R.string.problemas_carregar_foto));
                        }
                    });
                }
            }
        }
    }

    private void bloqueiaTD(boolean b) {
        btsalvar.setClickable(!b);
        btsalvar.setEnabled(!b);
        btcancelar.setClickable(!b);
        btcancelar.setEnabled(!b);
        edtapelido.setClickable(!b);
        edtapelido.setEnabled(!b);
        edtliga.setClickable(!b);
        edtliga.setEnabled(!b);
        imggalery.setClickable(!b);
        imggalery.setEnabled(!b);
        imggoogle.setClickable(!b);
        imggoogle.setEnabled(!b);
        imgperfil.setClickable(!b);
        imgperfil.setEnabled(!b);
        imgliga.setClickable(!b);
        imgliga.setEnabled(!b);
        blok=b;
    }

    private void showProgressBar(boolean b) {
        if(b){
            mprogressBar.setVisibility(View.VISIBLE);
        }else{
            mprogressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard() {
        edtapelido.setText(edtapelido.getText().toString().toUpperCase());
        edtliga.setText(edtliga.getText().toString().toUpperCase());
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void AlertaAsk() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_ask);

        ImageView imgdialog = dialog.findViewById(R.id.imgdialog);
        TextView txttitle = dialog.findViewById(R.id.txttitle);
        TextView txtdbody = dialog.findViewById(R.id.txtbody);
        Button btsim = dialog.findViewById(R.id.btsim);
        Button btnao = dialog.findViewById(R.id.btnao);

        dialog.setCancelable(false);
        imgdialog.setImageResource(R.drawable.trofeudourado);
        txttitle.setText(getString(R.string.app_name));
        txtdbody.setText(getString(R.string.cancelar_cadastro) );
        txtdbody.setGravity(Gravity.CENTER_HORIZONTAL);
        btsim.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.entra_sobe_alerta));
        btnao.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.entra_sobe_alerta));
        btnao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                if(mGoogleSignInClient!=null)mGoogleSignInClient.revokeAccess();
                Intent intent2 = new Intent(getActivity(), Login.class);
                startActivity(intent2);
                getActivity().finish();
            }
        });
        dialog.show();
    }


}
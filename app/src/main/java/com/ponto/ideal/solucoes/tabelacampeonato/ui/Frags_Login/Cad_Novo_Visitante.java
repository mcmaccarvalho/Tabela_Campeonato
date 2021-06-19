package com.ponto.ideal.solucoes.tabelacampeonato.ui.Frags_Login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.Cadastrar_Usuario;
import com.ponto.ideal.solucoes.tabelacampeonato.view.Login;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class Cad_Novo_Visitante extends Fragment implements View.OnClickListener {

    EditText etapelido;
    Button btsalvar,btcancelar;
    ConstraintLayout clcad_visitante;
    ImageView imgperfil,imggalery,imggoogle,avat1,avat2,avat3,avat4,avat5,avat6,avat7,avat8,avat9,
            avat10,avat11,avat12,avat13,avat14,avat15,avat16,avat17,avat18,avat19,avat20,
            avat21,avat22,avat23,avat24,avat25;
    TextView txtselavatxgal,txtgallery,txtgoogle;
    private ProgressBar mprogressBar;

    private int avat[] = new int[25];

    private String name;
    private String email;
    private Uri photoUrl;


    //    private FirebaseAuth autenticacao;
    // private StorageReference storageReference;
//    private Usuarios usu;
//    private String CAD_KEYUSU;
//    private int origem;
//    private String CAD_NOME;


//    private GoogleSignInClient mGoogleSignInClient;


    public Cad_Novo_Visitante(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cad_novo_visitante, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View view){

        Login.blok=false;
        clcad_visitante = view.findViewById(R.id.clcad_visitante);
        btsalvar = view.findViewById(R.id.btsalvar);
        btcancelar = view.findViewById(R.id.btcancelar);
        etapelido = view.findViewById(R.id.etapelido);
        imgperfil = view.findViewById(R.id.imgperfil);
        imggalery = view.findViewById(R.id.imggalery);
        txtselavatxgal = view.findViewById(R.id.txtselavatxgal);
        txtgallery = view.findViewById(R.id.txtgallery);
        mprogressBar = view.findViewById(R.id.mprogressBar);
        txtgoogle = view.findViewById(R.id.txtgoogle);
        imggoogle = view.findViewById(R.id.imggoogle);


//        autenticacao=ConfiguracaoFirebase.getFirebaseAuth();
//        CAD_KEYUSU = autenticacao.getUid();

        switch (Cadastrar_Usuario.origem){
            case 0:

                txtgoogle.setVisibility(View.INVISIBLE);
                imggoogle.setVisibility(View.INVISIBLE);
                String apelido ="";
                for (int i = 0; i < Cadastrar_Usuario.CAD_NOME.length(); i++) {
                    if (!Cadastrar_Usuario.CAD_NOME.substring(i,i+1).equals(" ")){
                        apelido += Cadastrar_Usuario.CAD_NOME.substring(i,i+1);
                    }else{
                        break;
                    }
                }
                etapelido.setText(Cadastrar_Usuario.CAD_APELIDO!=null ?Cadastrar_Usuario.CAD_APELIDO:apelido);
                if(Cadastrar_Usuario.CAD_BITMAP!=null)imgperfil.setImageBitmap(Cadastrar_Usuario.CAD_BITMAP);
                break;

            case 1:
                txtgoogle.setVisibility(View.VISIBLE);
                imggoogle.setVisibility(View.VISIBLE);
                              final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {

                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestProfile()
                            .requestEmail()
                            .build();
                    // GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

                    for (UserInfo profile : user.getProviderData()) {

                        String providerId = profile.getProviderId();

                        final String uid = profile.getUid();
                        name = profile.getDisplayName().toUpperCase();
                        email = profile.getEmail().toLowerCase();
                        photoUrl = profile.getPhotoUrl();
                        Cadastrar_Usuario.CAD_EMAIL=email.toLowerCase();
                        Cadastrar_Usuario.CAD_NOME=name.toUpperCase();
                        Cadastrar_Usuario.CAD_URI = photoUrl;

                        apelido = "";

                        for (int i = 0; i < name.length(); i++) {
                            if (!name.substring(i, i + 1).equals(" ")) {
                                apelido += name.substring(i, i + 1);
                            } else {
                                break;
                            }
                        }
                        Picasso.get().load(photoUrl.toString()).resize(200, 200)
                                .centerCrop().into(imgperfil, new Callback() {
                            @Override
                            public void onSuccess() {
                                mprogressBar.setVisibility(View.INVISIBLE);
                                Bitmap bb = null;
                                imgperfil.setDrawingCacheEnabled(true);
                                bb= Bitmap.createBitmap(imgperfil.getDrawingCache());
                                imgperfil.setDrawingCacheEnabled(false);
                                Cadastrar_Usuario.CAD_BITMAP = bb;
                            }

                            @Override
                            public void onError(Exception e) {
                                mprogressBar.setVisibility(View.INVISIBLE);
                                util.showSnackError(clcad_visitante,getString(R.string.problemas_carregar_foto));
                            }
                        });

                        etapelido.setText(apelido);

                    }

                }

                break;

        }

        imggoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.get().load(photoUrl.toString()).resize(200, 200)
                        .centerCrop().into(imgperfil, new Callback() {
                    @Override
                    public void onSuccess() {
                        mprogressBar.setVisibility(View.INVISIBLE);
                        Bitmap bb = null;
                        imgperfil.setDrawingCacheEnabled(true);
                        bb= Bitmap.createBitmap(imgperfil.getDrawingCache());
                        imgperfil.setDrawingCacheEnabled(false);
                        Cadastrar_Usuario.CAD_BITMAP = bb;
                    }

                    @Override
                    public void onError(Exception e) {
                        mprogressBar.setVisibility(View.INVISIBLE);
                        util.showSnackError(clcad_visitante,getString(R.string.problemas_carregar_foto));
                    }
                });

            }
        });

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
                Cadastrar_Usuario.CAD_APELIDO=etapelido.getText().toString().trim().toUpperCase();
                if (!hasFocus) {
                    etapelido.setSelection(etapelido.length());
                }else{

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

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Cad_Usu_frag()).commit();

              //  AlertaAsk();

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

        clcad_visitante.setFocusable(true);

        clcad_visitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });
        clcad_visitante.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.entra_right_alerta));
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 123) {

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
                        Cadastrar_Usuario.CAD_BITMAP = bb;
                    }

                    @Override
                    public void onError(Exception e) {
                        mprogressBar.setVisibility(View.INVISIBLE);
                        util.showSnackError(clcad_visitante,getString(R.string.problemas_carregar_foto));
                    }
                });

            }
        }
    }

    public void salvanovovisitante(){

        util.vibratePhone(getContext(), (short) 20);

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


       // imgperfil.setDrawingCacheEnabled(true);
       // imgperfil.buildDrawingCache();
       // Cadastrar_Usuario.CAD_BITMAP = imgperfil.getDrawingCache();
        Cadastrar_Usuario.CAD_APELIDO = napel;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Cad_Novo_Liga()).commit();

//        storageReference = ConfiguracaoFirebase.getFirebaseStorageReference();
//        StorageReference montaImagemReferencia = storageReference.child(util.saveurl(CAD_KEYUSU));
//
//        String url = storageReference+ "fotoPerfilUsuario/"
//                + FirebaseAuth.getInstance().getUid();
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
//        UploadTask uploadTask = montaImagemReferencia.putBytes(data);
//
//        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                util.showSnackOk(clcad_visitante, "Problemas ao salvar foto.");
//            }
//        });
//
//        util.saveImage(getContext(), bitmap, CAD_KEYUSU);
//
//
//        final Usuarios anonimo = new Usuarios();
//        anonimo.setKeyusu(CAD_KEYUSU);
//        anonimo.setApelidousu(napel);
//        anonimo.setTimestamp(System.currentTimeMillis());
//        anonimo.setImagemusuario(CAD_KEYUSU);
//        anonimo.setOnline(0);
//
//        if(origem==100){
//            anonimo.setNomeusu(napel);
//            anonimo.setEmailusu(CAD_KEYUSU);
//            anonimo.setStatus(100);
//        }else if(origem==1){
//            anonimo.setNomeusu(napel);
//            anonimo.setEmailusu(autenticacao.getCurrentUser().getEmail());
//            anonimo.setStatus(1);
//        }
//
//
//
//        FirebaseFirestore.getInstance().collection("usuarios")
//                .document(autenticacao.getUid())
//                .set(anonimo)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        final Usuarios_Controller uc = new Usuarios_Controller(getActivity().getBaseContext());
//                        uc.salvarusuario(anonimo);
//                   }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        mprogressBar.setVisibility(View.INVISIBLE);
//                        Log.i("verfire", e.toString());
//                    }
//                });

    }

    @Override
    public void onClick(View v) {

        int iditem = v.getId();
        int idavat=-1;
        hideSoftKeyboard();
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
        if(idavat>=0)salvaBit(idavat);
    }

    private void salvaBit(int idavat){
        Cadastrar_Usuario.CAD_BITMAP = util.drawableToBitmap(getResources().getDrawable(avat[idavat]));
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
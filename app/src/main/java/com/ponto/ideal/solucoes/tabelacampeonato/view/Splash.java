package com.ponto.ideal.solucoes.tabelacampeonato.view;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.helper.CheckApp;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;

import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity {

    public static final int APP_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    public static final int APP_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGEE = 1;
    private FirebaseAuth autenticacao;
    private ProgressBar mprogressBar;
    private CardView cvcancelar;
    private ConstraintLayout clsplash;
    private TextView txtcancelar,txtok;


    private GoogleSignInClient mGoogleSignInClient;//todo verificar remover
    boolean passa = true;//todo remover
    TextView textView2;//todo remover

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mprogressBar  = findViewById(R.id.mprogressBar);
        mprogressBar.setVisibility(View.VISIBLE);

        if (CheckApp.verificarGooglePlayServices(Splash.this)) {
            checar_permissoes();
        } else {
            mprogressBar.setVisibility(View.INVISIBLE);
            util.showmessage(getApplicationContext(), "Google Play Service não instalado");
        }

        textView2 = findViewById(R.id.textView2);

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo remover bloco
                passa = false;
                mprogressBar.setVisibility(View.INVISIBLE);
            }
        });

        clsplash = findViewById(R.id.clsplash);
        cvcancelar = findViewById(R.id.cvcancelar);
        txtcancelar = findViewById(R.id.txtcancelar);
        txtok = findViewById(R.id.txtok);
        clsplash.removeView(cvcancelar);

        txtok.setClickable(false);
        txtok.setEnabled(false);
        txtcancelar.setClickable(false);
        txtcancelar.setEnabled(false);

        txtcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.showmessage(getApplicationContext(), "Obrigado por utilizar o Tabela Campeonato");
                finish();
            }
        });

        txtok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtok.setClickable(false);
                txtok.setEnabled(false);
                txtcancelar.setClickable(false);
                txtcancelar.setEnabled(false);
                clsplash.removeView(cvcancelar);
                mprogressBar.setVisibility(View.VISIBLE);
                checar_permissoes();
            }
        });
    }

    private void checar_permissoes() {

        if (Build.VERSION.SDK_INT < 23) {

            Log.i("permissoes <23", "premissões ok");
            carregausu();

        } else if (checkAndRequestPermissions()) {

            Log.i("permissoes 1>23", "premissões dadas");

            carregausu();

        } else {


            Log.i("permissoes 2>23", "sem premissões");
        }

    }

    public boolean checkAndRequestPermissions() {

        boolean retorno = true;

        List<String> permissoesnecessarias = new ArrayList<>();

        int permissaoWrite =
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int permissaoRead =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissaoWrite != PackageManager.PERMISSION_GRANTED) {
            permissoesnecessarias.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (permissaoRead != PackageManager.PERMISSION_GRANTED) {
            permissoesnecessarias.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }




        if (!permissoesnecessarias.isEmpty()) {

            ActivityCompat.requestPermissions(this,
                    permissoesnecessarias.toArray(new String[permissoesnecessarias.size()]),
                    APP_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGEE);
            retorno = false;



        }

        return retorno;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case APP_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGEE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    carregausu();
                    Log.i("permissoes 1", "premissões liberadas");
                } else {
                    mprogressBar.setVisibility(View.INVISIBLE);
                    clsplash.addView(cvcancelar);
                    txtok.setClickable(true);
                    txtok.setEnabled(true);
                    txtcancelar.setClickable(true);
                    txtcancelar.setEnabled(true);
                }
            }

            case APP_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {

                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    carregausu();
                    Log.i("permissoes 2", "premissões liberadas");
                } else {
                    Log.i("permissoes 2", "premissões parciais");
                }
            }
        }
    }

    public void carregausu() {


        final int SPLASH_TIME_OUT = 5000;

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {

                if (passa) {
                    if (temusuarioLogado()) {
                        mprogressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(Splash.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        mprogressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(Splash.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    alertaDeslogar();
                }
            }
        }, SPLASH_TIME_OUT);


    }


    public Boolean temusuarioLogado() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        autenticacao = FirebaseAuth.getInstance();
//todo verificar remover
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestProfile()
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(Splash.this, gso);
//todo verificar remover

        if (user != null) {
            return true;
        } else {
            return false;
        }

    }

    private void alertaDeslogar(){

        mprogressBar.setVisibility(View.INVISIBLE);

        final Dialog dialog = new Dialog(Splash.this);
        dialog.setContentView(R.layout.dialog_ask);

        ImageView imgdialog = dialog.findViewById(R.id.imgdialog);
        TextView txttitle = dialog.findViewById(R.id.txttitle);
        TextView txtdbody = dialog.findViewById(R.id.txtbody);
        Button btsim = dialog.findViewById(R.id.btsim);
        Button btnao = dialog.findViewById(R.id.btnao);

        ConstraintLayout cldialog = dialog.findViewById(R.id.cldialog);

        imgdialog.setImageResource(R.drawable.footbalpp);
        txttitle.setText("Deslogar user  = " + temusuarioLogado());
        txtdbody.setText("Deseja deslogar?");
        txtdbody.setGravity(Gravity.CENTER_HORIZONTAL);
        dialog.setCancelable(false);
        btsim.startAnimation(AnimationUtils.loadAnimation(Splash.this, R.anim.entra_sobe_alerta));
        btnao.startAnimation(AnimationUtils.loadAnimation(Splash.this, R.anim.entra_sobe_alerta));

        btnao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mprogressBar.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();

//                        Intent intent = new Intent(Splash.this, Login.class);
//                        startActivity(intent);
//                        finish();

            }
        });
        btsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticacao = FirebaseAuth.getInstance();
                autenticacao.signOut();
                if (mGoogleSignInClient == null) {
                    Log.i("telamain", " goole null");
                } else {
                    mGoogleSignInClient.signOut();
                    Log.i("telamain", " goole deslogado");
                }
                mprogressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
        mprogressBar.setVisibility(View.INVISIBLE);

    }
}
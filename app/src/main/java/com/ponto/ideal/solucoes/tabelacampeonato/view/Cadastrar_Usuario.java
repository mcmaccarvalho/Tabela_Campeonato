package com.ponto.ideal.solucoes.tabelacampeonato.view;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Frags_Login.Cad_Usu_frag;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Frags_Login.Salva_Usuario;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;

public class Cadastrar_Usuario extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce=false;
    private FragmentManager fragmentManager;
    private Context context;

    public static String CAD_EMAIL, CAD_NOME, CAD_SENHA, CAD_KEYUSU,CAD_LIGA,CAD_APELIDO;
    public static Bitmap CAD_BITMAP,CAD_LIGA_BITMAP;
    public static Uri CAD_URI;
    public static boolean blok;

    public static int volta=0;

    public static int origem;

    //TODO remover origem 0 cad email
    //TODO remover origem 1 cad google

    public Cadastrar_Usuario() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar__usuario);

        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
        this.setTitle(getString(R.string.cadastro_email));
        fragmentManager = getSupportFragmentManager();
        context = this;

        Intent intent = getIntent();
        origem=intent.getIntExtra("origem",0);

        switch (origem){
            case 0:fragmentManager.beginTransaction().replace(R.id.llcad, new Cad_Usu_frag()).commit();break;
            case 1:fragmentManager.beginTransaction().replace(R.id.llcad, new Salva_Usuario()).commit();break;
        }

        CAD_EMAIL="";
        CAD_NOME="";
        CAD_SENHA="";
        CAD_KEYUSU="";
    }

    @Override
    public void onBackPressed() {
        if (!blok) {
            if (doubleBackToExitPressedOnce) {
                AlertaAsk();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(Cadastrar_Usuario.this, R.string.preesione_novamente, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public void AlertaAsk() {

        final Dialog dialog = new Dialog(Cadastrar_Usuario.this);
        dialog.setContentView(R.layout.dialog_ask);

        ImageView imgdialog = dialog.findViewById(R.id.imgdialog);
        TextView txttitle = dialog.findViewById(R.id.txttitle);
        TextView txtdbody = dialog.findViewById(R.id.txtbody);
        Button btsim = dialog.findViewById(R.id.btsim);
        Button btnao = dialog.findViewById(R.id.btnao);

        dialog.setCancelable(false);
        imgdialog.setImageResource(R.drawable.trofeudourado);
        txttitle.setText(getString(R.string.app_name));
        txtdbody.setText(getString(R.string.cancelar_cadastro));
        txtdbody.setGravity(Gravity.CENTER_HORIZONTAL);
        btsim.startAnimation(AnimationUtils.loadAnimation(Cadastrar_Usuario.this, R.anim.entra_sobe_alerta));
        btnao.startAnimation(AnimationUtils.loadAnimation(Cadastrar_Usuario.this, R.anim.entra_sobe_alerta));
        btnao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(origem==1){
                    FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestProfile()
                            .requestEmail()
                            .build();
                    GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(Cadastrar_Usuario.this, gso);
//                    user.delete();
                    if(mGoogleSignInClient!=null)mGoogleSignInClient.revokeAccess();
                    util.showmessage(Cadastrar_Usuario.this, getString(R.string.obrigado_tab_camp));
                    dialog.dismiss();
                    finish();
                }else {
                    util.showmessage(Cadastrar_Usuario.this, getString(R.string.obrigado_tab_camp));
                    dialog.dismiss();
                    finish();
                }
            }
        });

        dialog.show();
    }

}


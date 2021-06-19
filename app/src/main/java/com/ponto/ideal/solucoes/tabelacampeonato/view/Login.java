package com.ponto.ideal.solucoes.tabelacampeonato.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.helper.ConfiguracaoFirebase;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Usuarios;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;

public class Login extends AppCompatActivity {

    private EditText edtemail,edtsenha;
    private TextView txtesquecisenha;
    private Button btlogar,btcad;
    private SignInButton sign_in_button;
    private ImageView eyesenhablack;
    private ConstraintLayout cllogin;
    private ProgressBar mprogressBar;

    private Context context;
    public static int ctrl;

    public static boolean blok=false;
    boolean doubleBackToExitPressedOnce=false;
    private boolean olho1=true;

    private static final String TAG = "Logintela";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    GoogleSignInAccount account;
    private Credential credential;
    private GoogleAuthProvider googleAuthProvider;

    private Usuarios usu = new Usuarios();
    private Usuarios usuLog = new Usuarios();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");

        context=getBaseContext();
        mAuth = FirebaseAuth.getInstance();
        initViews();
    }

    private void initViews() {

        btcad = findViewById(R.id.btcad);
        btlogar = findViewById(R.id.btlogar);
        sign_in_button= findViewById(R.id.sign_in_button);
        txtesquecisenha= findViewById(R.id.txtesquecisenha);
        edtemail= findViewById(R.id.edtemail);
        edtsenha= findViewById(R.id.edtsenha);
        eyesenhablack= findViewById(R.id.eyesenhablack);
        cllogin = findViewById(R.id.cllogin);
        mprogressBar = findViewById(R.id.mprogressBar);


        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(Login.this,(short)20);
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestProfile()
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
                bloqueiaTD(true);
                signIn();
            }
        });

        eyesenhablack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(Login.this,(short)20);
                if (olho1) {
                    edtsenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyesenhablack.setImageResource(R.drawable.ic_eye_red);
                } else {
                    edtsenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyesenhablack.setImageResource(R.drawable.ic_noeye_black);
                }
                olho1 = !olho1;
            }
        });

        edtemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtemail.setSelection(edtemail.length());
                }else{
                    edtemail.setText(edtemail.getText().toString().toLowerCase());
                }
            }
        });

        //TODO remover btlogar long click
        btlogar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mGoogleSignInClient == null) {
                    Log.i("telamain", " goole null");
                } else {
                    mGoogleSignInClient.signOut();
                    Log.i("telamain", " goole deslogado");
                }
                return false;
            }
        });

        btlogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bloqueiaTD(true);
                util.vibratePhone(Login.this,(short)20);
                String uemail = edtemail.getText().toString();
                String usenha = edtsenha.getText().toString();
                if(uemail.equals("") || uemail.isEmpty()){
                    bloqueiaTD(false);
                    util.showSnackError(cllogin,getString(R.string.informe_email));
                }else if(usenha.equals("") || usenha.isEmpty()){
                    bloqueiaTD(false);
                    util.showSnackError(cllogin,getString(R.string.informe_senha));
                }else {
                    bloqueiaTD(true);
                    showProgressBar(true);
                    edtemail.setText(edtemail.getText().toString().toLowerCase());
                    validarLogin();
                }
            }
        });

        cllogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });
        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloqueiaTD(true);
                util.vibratePhone(Login.this,(short)20);
                Intent intent = new Intent(Login.this, Cadastrar_Usuario.class);
                intent.putExtra("origem",0);
                startActivity(intent);
                finish();
            }
        });

        //TODO remover esqueci on longclick
        txtesquecisenha.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getProviderData();
                return false;
            }
        });


        txtesquecisenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bloqueiaTD(true);
                util.vibratePhone(Login.this,(short)20);
                if(edtemail.getText().toString().equals("")){
                    util.showSnackError(cllogin,getString(R.string.informe_email_senha));
                    return;
                }
                FirebaseAuth auth = FirebaseAuth.getInstance();
                final String emailAddress = edtemail.getText().toString();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    bloqueiaTD(false);
                                    util.showmessage(Login.this,getString(R.string.recuperar_senha)+emailAddress);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        bloqueiaTD(false);
                        util.showmessage(Login.this,getString(R.string.problemas_recuperação_de_senha)+emailAddress);

                    }
                });
            }
        });
    }

    //Log Google
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                bloqueiaTD(false);
                updateUI(null);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        showProgressBar(true);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            checacad(mAuth.getUid().toString());
                        } else {
                            showProgressBar(false);
                            Log.i(TAG,"Authentication Failed.");
                            updateUI(null);
                        }
                    }
                });
    }

    private void checacad(final String uid) {

        FirebaseFirestore.getInstance().collection("/usuarios")
                .document(uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot != null) {
                            usuLog = documentSnapshot.toObject(Usuarios.class);
                        }
                        if(usuLog!=null){
                            updateUI(mAuth.getCurrentUser());
                        }else{
                            util.showSnackError(cllogin,"Usuario nao existe cad");//TODO remover
                            Intent intent = new Intent(Login.this, Cadastrar_Usuario.class);
                            intent.putExtra("origem",1);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "e " + e.getMessage() + ": " + e);
            }
        });

    }

    //log email
    private void validarLogin() {
        mAuth = FirebaseAuth.getInstance();
        final String uemail = edtemail.getText().toString();
        final String usenha = edtsenha.getText().toString();
        mAuth = ConfiguracaoFirebase.getFirebaseAuth();
        mAuth.signInWithEmailAndPassword(uemail, usenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    updateUI(mAuth.getCurrentUser());
                }else{
                    util.showSnackError(cllogin,getString(R.string.usuario_invalido));
                    bloqueiaTD(false);
                    showProgressBar(false);
                    updateUI(null);
                }

            }
        });
    }

    private void updateUI(FirebaseUser user) {
        showProgressBar(false);


        if (user != null) {
            util.showSnackError(cllogin,"Usuario existe vai main " + user.getEmail());//TODO remover e liberar main
            bloqueiaTD(false);//TODO remover
            Intent intent2 = new Intent(Login.this, MainActivity.class);
            startActivity(intent2);
            Login.this.finish();
        }else{
            util.showSnackError(cllogin,"Não logou  user null updateui" );//TODO remover
        }
    }


    //TODO remover getprovider;
    // recria usu google
    public void getProviderData() {

        int ddd =0;
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {

                String providerId = profile.getProviderId();
                Log.i(TAG, "providerId: " + providerId);
                final String uid = profile.getUid();
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
                Log.i(TAG, "Nome: " + name);
                Log.i(TAG, "email: " + email);
                Log.i(TAG, "uri: " + photoUrl.toString());

                String apelido = "";

                for (int i = 0; i < name.length(); i++) {
                    if (!name.substring(i, i + 1).equals(" ")) {
                        apelido += name.substring(i, i + 1);
                    } else {
                        break;
                    }
                }


                if(user.getUid().equals(uid)) {

                    usu = new Usuarios();
                    usu.setNomeusu(name);
                    usu.setKeyusu(uid);
                    usu.setEmailusu(email);
                    usu.setImagemusuario(photoUrl.toString());
                    usu.setTimestamp(System.currentTimeMillis());
                    usu.setOnline(1);
                    usu.setToken("");
                    usu.setApelidousu(apelido);



                    FirebaseFirestore.getInstance().collection("/usuarios")
                            .document(usu.getKeyusu())
                            .set(usu)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //updateUI(mAuth.getCurrentUser());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
               //     checacad(usu);

                }

            }

        }

    }

//    private void cadastraUsuario(Usuarios nusu) {
//        if(nusu!=null){
//            usu.setTimestamp(nusu.getTimestamp());
//        }
//        Log.i(TAG, "usu time alterado " + usu.getTimestamp());
//        FirebaseFirestore.getInstance().collection("/usuarios")
//                .document(usu.getKeyusu())
//                .set(usu)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        updateUI(mAuth.getCurrentUser());
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//
//    }

    @Override
    public void onBackPressed() {
        if (!blok) {
            if (doubleBackToExitPressedOnce) {
                sair();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(Login.this, R.string.preesione_novamente, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    private void sair() {
        util.showmessage(Login.this,getString(R.string.obrigado_tab_camp));
        finish();
    }

    private void bloqueiaTD(boolean b) {
        btlogar.setClickable(!b);
        btlogar.setEnabled(!b);
        sign_in_button.setClickable(!b);
        sign_in_button.setEnabled(!b);
        btcad.setClickable(!b);
        btcad.setEnabled(!b);
        edtemail.setClickable(!b);
        edtemail.setEnabled(!b);
        edtsenha.setClickable(!b);
        edtsenha.setEnabled(!b);
        txtesquecisenha.setClickable(!b);
        txtesquecisenha.setEnabled(!b);
        eyesenhablack.setClickable(!b);
        eyesenhablack.setEnabled(!b);
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
        edtemail.setText(edtemail.getText().toString().toLowerCase());
        if (Login.this.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) Login.this.getSystemService(Login.this.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(Login.this.getCurrentFocus().getWindowToken(), 0);
        }
    }

}
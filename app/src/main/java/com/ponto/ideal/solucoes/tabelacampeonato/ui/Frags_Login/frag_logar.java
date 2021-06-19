package com.ponto.ideal.solucoes.tabelacampeonato.ui.Frags_Login;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;

public class frag_logar extends Fragment {

    private Button btvisitante, btlogar, btcad;
    private ConstraintLayout cllogin;
    private ImageView imgvisitante,imglog,imgcad;
    private ProgressBar mprogressBar;
    private boolean blokclick = false;
    private ObjectAnimator animator1;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth autenticacao;
    private SignInButton sign_in_button;

    private int RC_SIGN_IN;

    public frag_logar() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_logar, container, false);


        initViews(v);
        return v;
    }

    public void initViews(View v){


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        updateUI(account);
        //Login.ctrl =0;




        btvisitante = v.findViewById(R.id.btvisitante);
        btcad = v.findViewById(R.id.btcad);
        btlogar = v.findViewById(R.id.btlogar);
        imgvisitante = v.findViewById(R.id.imgvisitante);
        imglog = v.findViewById(R.id.imglog);
        imgcad = v.findViewById(R.id.imgcad);
        cllogin = v.findViewById(R.id.cllogin);
        sign_in_button = v.findViewById(R.id.sign_in_button);
        mprogressBar  = v.findViewById(R.id.mprogressBar);

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });



        cllogin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                bloqueiaTD(false);
                return false;
            }
        });
        btvisitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloqueiaTD(true);
                animator1 = ObjectAnimator.ofFloat(imgvisitante, "rotation", 0f,720f);
                animator1.setDuration(1500);
                animator1.start();
                animator1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mprogressBar.setVisibility(View.VISIBLE);
                      //  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Cad_Novo_Visitante(1,"TEste")).commit();

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });

        imgvisitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btvisitante.callOnClick();
            }
        });

        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloqueiaTD(true);
                animator1 = ObjectAnimator.ofFloat(imgcad, "rotation", 0f,720f);
                animator1.setDuration(1500);
                animator1.start();
                animator1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mprogressBar.setVisibility(View.VISIBLE);
                      //  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Cad_Usu_frag(1)).commit();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                util.showmessage(getContext(),"vai para cadastrar");

            }
        });

        imgcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btcad.callOnClick();
            }
        });

        btlogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloqueiaTD(true);
                animator1 = ObjectAnimator.ofFloat(imglog, "rotation", 0f,720f);
                animator1.setDuration(1500);
                animator1.start();
                animator1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        util.showmessage(getContext(),"vai para visitante");
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                util.showmessage(getContext(),"vai para logar");
            }
        });

        imglog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btlogar.callOnClick();
            }
        });

    }

    private void updateUI(GoogleSignInAccount account) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        autenticacao = FirebaseAuth.getInstance();

        if (user != null) {
          util.showmessage(getContext(),"usuario logado abre");
        } else {
            util.showmessage(getContext(),"usuario nao logado loga");
        }

    }

    private void bloqueiaTD(boolean b){
        btlogar.setClickable(!b);
        btlogar.setEnabled(!b);
        btvisitante.setClickable(!b);
        btvisitante.setEnabled(!b);
        btcad.setClickable(!b);
        btcad.setEnabled(!b);
        imgcad.setClickable(!b);
        imgcad.setEnabled(!b);
        imglog.setClickable(!b);
        imglog.setEnabled(!b);
        imgvisitante.setClickable(!b);
        imgvisitante.setEnabled(!b);
       // Login.blok=b;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
        Log.w("logargoogle", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

//        String [] arrquivos = util.listafile(getContext());
//
//        for (int i=0;i<arrquivos.length;i++){
//            getContext().deleteFile(arrquivos[i]);
//        }
//
//
//        Drawable drawable = getResources().getDrawable(R.drawable.trofeupq);
//        final Bitmap bitmap2 = util.drawableToBitmap(drawable);
//
//        util.saveImage(getActivity(),bitmap2, "img_trofeu");
//
//        Drawable drawable1 = getResources().getDrawable(R.drawable.trofeudourado);
//        final Bitmap bitmap3 = util.drawableToBitmap(drawable1);
//
//        util.saveImage(getActivity(),bitmap3, "img_trofeu_dourado");




//    public void LogarVisitante() {
//        mprogressBar.setVisibility(View.VISIBLE);
//        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
//        autenticacao.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Cad_Novo_Visitante(100,null)).commit();
//            }
//        });
//    }


//    private void validarLogin() {
//        Log.i("logando",  "entrou validar");
//        mprogressBar.setVisibility(View.VISIBLE);
//        autenticacao = FirebaseAuth.getInstance();
//        final String uemail = etemail.getText().toString();
//        final String usenha = etsenha.getText().toString();
//        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
//        autenticacao.signInWithEmailAndPassword(uemail, usenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Log.i("retligas", "usuario logado " + autenticacao.getCurrentUser().getEmail() );
//                    get_usuario();
//                }else{
//                    mprogressBar.setVisibility(View.INVISIBLE);
//                    util.showSnackError(cllogin,"Usuario ou Senha Invalido");
//                }
//
//            }
//        });
//    }

//    public void get_usuario(){
//        txtaviso.setText("Carregando Usuario ...");
//        cvaviso.setVisibility(View.VISIBLE);
//        final String uid = autenticacao.getCurrentUser().getUid();
//        FirebaseFirestore . getInstance () . collection ( "/usuarios" )
//                .document ( uid)
//                .get()
//                .addOnSuccessListener ( new  OnSuccessListener < DocumentSnapshot > () {
//                    @Override
//                    public  void  onSuccess ( DocumentSnapshot  documentSnapshot ) {
//                        usuLog = documentSnapshot . toObject ( Usuarios . class);
//                        Log.i("retligas", ":perfil carregado " + usuLog.getNomeusu() );
//                        checa_status_usuario();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.i("retligas", "e " + e.getMessage() + ": " + e );
//            }
//        });
//    }

//    public void fotousuario(){
//
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReferenceFromUrl(util.loadurl(usuLog.getKeyusu()));
//
//        final int heigth = 300;
//        final int width = 300;
//
//        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Log.i("retligas", ":DownloadImage " + uri.toString());
//                new DownloadImage().execute(uri.toString());
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Drawable drawable = getResources().getDrawable(R.drawable.fotoblank);
//                final Bitmap bitmap = util.drawableToBitmap(drawable);
//                boolean foto;
//                foto = util.saveImage(getContext(), bitmap, usuLog.getKeyusu());
//                Log.i("retligas", ":DownloadImage Fotoblanc");
//                if(foto)
//                    inicia();
//            }
//        });
//    }

//    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
//
//        private String TAG = "DownloadImage";
//        private Bitmap downloadImageBitmap(String sUrl) {
//            Bitmap bitmap = null;
//            try {
//                InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
//                bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
//                inputStream.close();
//            } catch (Exception e) {
//                Log.d(TAG, "Exception 1, Something went wrong!");
//                e.printStackTrace();
//            }
//            return bitmap;
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... params) {
//            return downloadImageBitmap(params[0]);
//        }
//
//        protected void onPostExecute(Bitmap result) {
//
//            boolean sf = util.saveImage(getContext(),result, usuLog.getKeyusu());
//            if(sf){
////                inicia();
//                preenchetabelaLigas();
//            }else{
//                util.showSnackError(cllogin,"Problemas ao salvar perfil.\nEntre em contato com administrador");
//            }
//        }
//    }
}

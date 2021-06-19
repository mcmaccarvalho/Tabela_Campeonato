package com.ponto.ideal.solucoes.tabelacampeonato.ui.Frags_Login;

import android.app.Dialog;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Usuarios;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.Cadastrar_Usuario;

import java.util.ArrayList;

public class Cad_Usu_frag extends Fragment {

    boolean olho1 = true;
    private EditText edtnome, edtemail, edtsenha, edtrepsenha;
    private ImageView eyesenhablack, eyerepsenhablack;
    private Button btcad, btcancel;
    private  static ConstraintLayout clcad;
    private  static ProgressBar mprogressBar;
    private boolean blok;
    public static String CAD_EMAIL, CAD_NOME, CAD_SENHA,CAD_APELIDO;

    private FirebaseAuth autenticacao;

    int contateste = 0;

    public Cad_Usu_frag() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cad_usu, container, false);
        initViews(view);
        return view;
    }

    public void initViews(View view) {

//        if (Cadastrar_Usuario.origem == 1) {
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Cad_Novo_Visitante()).commit();
//        }

     //TODO fazer on backpressed cadastrar usuarrio

        mprogressBar = view.findViewById(R.id.mprogressBar);
        edtnome = view.findViewById(R.id.edtnome);
        edtemail = view.findViewById(R.id.edtemail);
        edtsenha = view.findViewById(R.id.edtsenha);
        edtrepsenha = view.findViewById(R.id.edtrepsenha);
        btcad = view.findViewById(R.id.btcad);
        btcancel = view.findViewById(R.id.btcancel);
        eyesenhablack = view.findViewById(R.id.eyesenhablack);
        eyerepsenhablack = view.findViewById(R.id.eyerepsenhablack);
        clcad = view.findViewById(R.id.clcad);

        eyesenhablack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (olho1) {
                    edtsenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyesenhablack.setImageResource(R.drawable.ic_eye_red);
                    edtrepsenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyerepsenhablack.setImageResource(R.drawable.ic_eye_red);
                } else {
                    edtsenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyesenhablack.setImageResource(R.drawable.ic_noeye_black);
                    edtrepsenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyerepsenhablack.setImageResource(R.drawable.ic_noeye_black);
                }
                olho1 = !olho1;
            }
        });

        eyerepsenhablack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eyesenhablack.callOnClick();
            }
        });


        btcad.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                edtnome.setText(edtnome.getText().toString().toUpperCase());
                edtemail.setText(edtemail.getText().toString().toLowerCase());
                cadastrarUsuario();

            }
        });


        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!blok)AlertaAsk();
            }
        });


        clcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                limpaFoco();
            }
        });


        edtnome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtnome.setText(edtnome.getText().toString().toUpperCase());
                if (!hasFocus)edtnome.setSelection(edtnome.length());
            }
        });


        edtemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtemail.setText(edtemail.getText().toString().toLowerCase());
                if (!hasFocus)edtemail.setSelection(edtemail.length());
            }
        });

        edtsenha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)edtsenha.setSelection(edtsenha.length());
                letranum();
            }
        });

        edtrepsenha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)edtrepsenha.setSelection(edtrepsenha.length());
            }
        });





        //TODO remover btcancel on longclik
        btcancel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                edtemail.setText("mac@masd.com");
                edtnome.setText("MARCO TESTE");
                edtsenha.setText("mac111111");
                edtrepsenha.setText("mac111111");
                return true;
            }
        });

//        if(!Cadastrar_Usuario.CAD_NOME.equals("") || !Cadastrar_Usuario.CAD_NOME.isEmpty()){

            edtnome.setText(Cadastrar_Usuario.CAD_NOME.isEmpty()? "" : Cadastrar_Usuario.CAD_NOME);
            edtemail.setText(Cadastrar_Usuario.CAD_EMAIL.isEmpty()? "" : Cadastrar_Usuario.CAD_EMAIL);
            edtsenha.setText(Cadastrar_Usuario.CAD_SENHA.isEmpty()? "" : Cadastrar_Usuario.CAD_SENHA);
            edtrepsenha.setText(Cadastrar_Usuario.CAD_SENHA.isEmpty()? "" : Cadastrar_Usuario.CAD_SENHA);

//            edtnome.setText(Cadastrar_Usuario.CAD_NOME);
//            edtemail.setText(Cadastrar_Usuario.CAD_EMAIL);
//            edtsenha.setText(Cadastrar_Usuario.CAD_SENHA );
//            edtrepsenha.setText(Cadastrar_Usuario.CAD_SENHA);
//        }

        clcad.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.entra_right_alerta));

    }

    public void cadastrarUsuario() {

        util.vibratePhone(getContext(), (short) 20);
        Boolean ok = false;
        hideSoftKeyboard();

        if (edtnome.getText().toString().trim().isEmpty() || edtnome.getText().toString().length() < 3) {
            util.showSnackError(clcad, getString(R.string.preencha_nome));
        } else if (!edtemail.getText().toString().contains("@") || !edtemail.getText().toString().contains(".")) {
            util.showSnackError(clcad, getString(R.string.preencha_email));
        } else if (!letranum()) {
            util.showSnackError(clcad, getString(R.string.senha_forte));
            edtsenha.requestFocus();
        } else if (!edtsenha.getText().toString().trim().equals(edtrepsenha.getText().toString().trim())
                || edtsenha.getText().toString().trim().isEmpty()) {
            util.showSnackError(clcad, getString(R.string.senhas_diferentes));
            edtsenha.requestFocus();
        } else {
            bloqueiaTD(true);
            showProgressBar(true);
            ok = true;
        }


        if (ok == true) {


            CAD_EMAIL = edtemail.getText().toString().toLowerCase().trim();
            CAD_NOME = edtnome.getText().toString().trim();
            CAD_SENHA = edtsenha.getText().toString().trim();
            CAD_EMAIL = edtemail.getText().toString().trim().toLowerCase();

            String apelido ="";
            for (int i = 0; i < CAD_NOME.length(); i++) {
                if (!CAD_NOME.substring(i,i+1).equals(" ")){
                    apelido += CAD_NOME.substring(i,i+1);
                }else{
                    break;
                }
            }
            CAD_APELIDO = apelido;


            CAD_EMAIL = edtemail.getText().toString().toLowerCase().trim();
            Cadastrar_Usuario.CAD_NOME = edtnome.getText().toString().trim();
            Cadastrar_Usuario.CAD_SENHA = edtsenha.getText().toString().trim();
            Cadastrar_Usuario.CAD_EMAIL = edtemail.getText().toString().trim().toLowerCase();

            FirebaseFirestore.getInstance().collection("/usuarios")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            ArrayList<Usuarios> usuariosArrayList = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document != null) {
                                        usuariosArrayList.add(document.toObject(Usuarios.class));
                                    }
                                }

                                boolean temem = false;
                                for (int i=0;i<usuariosArrayList.size();i++){
                                    if(usuariosArrayList.get(i).getEmailusu()!=null &&  usuariosArrayList.get(i).getEmailusu().equals(CAD_EMAIL)){
                                        temem=true;
                                        break;
                                    }

                                }
                                if(temem){
                                    showProgressBar(false);
                                    bloqueiaTD(false);
                                    util.showSnackError(clcad,getString(R.string.email_ja_cadastrado));
                                }else{
                                    Cadastrar_Usuario.CAD_NOME    = CAD_NOME    ;
                                    Cadastrar_Usuario.CAD_SENHA   = CAD_SENHA   ;
                                    Cadastrar_Usuario.CAD_EMAIL   = CAD_EMAIL   ;
                                  //  Cadastrar_Usuario.CAD_APELIDO = CAD_APELIDO ;
                                    bloqueiaTD(false);;
                                    showProgressBar(false);
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Salva_Usuario()).commit();
                                }

                            } else {
                                Log.d("lista", "Error getting documents: ", task.getException());

                            }


                        }
                    });
        }
    }

    private void showProgressBar(boolean b) {
        if(b){
            mprogressBar.setVisibility(View.VISIBLE);
        }else{
            mprogressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void bloqueiaTD(boolean b) {
        btcancel.setClickable(!b);
        btcancel.setEnabled(!b);
        btcad.setClickable(!b);
        btcad.setEnabled(!b);
        edtnome.setClickable(!b);
        edtnome.setEnabled(!b);
        edtemail.setClickable(!b);
        edtemail.setEnabled(!b);
        edtsenha.setClickable(!b);
        edtsenha.setEnabled(!b);
        edtrepsenha.setClickable(!b);
        edtrepsenha.setEnabled(!b);
        eyesenhablack.setClickable(!b);
        eyesenhablack.setEnabled(!b);
        eyerepsenhablack.setClickable(!b);
        eyerepsenhablack.setEnabled(!b);
        Cadastrar_Usuario.blok=b;
    }

    private void limpaFoco() {
        edtemail.clearFocus();
        edtnome.clearFocus();
        edtrepsenha.clearFocus();
        edtsenha.clearFocus();
    }

    private void hideSoftKeyboard() {
        edtnome.setText(edtnome.getText().toString().toUpperCase());
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public boolean letranum() {
        boolean letranum = false;
        boolean letra = false;
        boolean num = false;

        String letras = edtsenha.getText().toString();

        for (int i = 0; i < letras.length(); i++) {
            if (Character.isDigit(letras.charAt(i)) == true) {
                num = true;
                break;
            }
        }
        for (int i = 0; i < letras.length(); i++) {
            if (!Character.isDigit(letras.charAt(i)) == true) {
                letra = true;
                break;
            }
        }
        if (letra && num && letras.length() > 7) {
            letranum = true;
        }
        return letranum;
    }

    public void AlertaAsk() {

        final Dialog dialog = new Dialog(getContext());
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
        btsim.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.entra_sobe_alerta));
        btnao.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.entra_sobe_alerta));
        btnao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloqueiaTD(false);
                dialog.dismiss();
            }
        });
        btsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.showmessage(getContext(), getString(R.string.obrigado_tab_camp));
                dialog.dismiss();
                getActivity().finish();
            }
        });

        dialog.show();
    }


    public void criaCredenciais(){

//        String senha = CAD_SENHA;
//        final String email = CAD_EMAIL;
//
//
//        autenticacao = FirebaseAuth.getInstance();
//        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
//        autenticacao.createUserWithEmailAndPassword(
//                email,
//                senha
//        ).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    CAD_KEYUSU = autenticacao.getUid();
//
//                    contateste=0;
//           //         getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Cad_Novo_Visitante(1,CAD_NOME)).commit();
//
//
//                } else {
//
//                    String erroExcecao = "";
//                    try {
//                        throw task.getException();
//                    } catch (FirebaseAuthUserCollisionException e) {
//                        Log.e("login 1",": " + e.getMessage());
//                        erroExcecao = "Esse e-mail já está cadastrado!";
//                    } catch (FirebaseAuthWeakPasswordException e) {
//                        Log.e("login 2",": " + e.getMessage());
//                        erroExcecao = "Digite uma senha mais forte, contendo no mínimo 8 caracteres e que contenha letras e números!";
//                    } catch (FirebaseAuthInvalidCredentialsException e) {
//                        Log.e("login 3",": " + e.getMessage());
//                        erroExcecao = "O e-mail digitado é invalido, digite um novo e-mail";
//                    } catch (Exception e) {
//                        Log.e("login 4",": " + e.getMessage());
//                        erroExcecao = "Erro ao efetuar o cadastro!\nVerfique sua conexão.";
//                        e.printStackTrace();
//                    }
//                    mprogressBar.setVisibility(View.INVISIBLE);
//                    util.showSnackOk(clcad,erroExcecao);
//
//                }
//
//            }
//        });

    }

    public void migrarcredenciais(){

        String senha = CAD_SENHA;
        final String email = CAD_EMAIL;

        AuthCredential credential = EmailAuthProvider.getCredential(email, senha);

        autenticacao = FirebaseAuth.getInstance();
        autenticacao.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

//                Cad_Usu.CAD_EMAIL =  CAD_EMAIL ;
//                Cad_Usu.CAD_NOME  =  CAD_NOME  ;
//                Cad_Usu.CAD_SENHA =  CAD_SENHA ;
//                Cad_Usu.CAD_KEYUSU = autenticacao.getUid();
                // criarPerfil();

            }
        });
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        if (task.isSuccessful()) {
//
//                            FirebaseUser user = task.getResult().getUser();
//
//
//
//                            //updateUI(user);
//                        }
//
//                        // ...
//                    }
//                });

    }

    public void criarPerfil(){
//
//        if(CAD_KEYUSU==null){
//
//            contateste=contateste+1;
//            fcontateste();
//
//        }else {
//
//            FirebaseUser user = autenticacao.getCurrentUser();
//
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//            SimpleDateFormat simpleHoraFormat = new SimpleDateFormat("HH:mm:ss");
//            Calendar calendar = Calendar.getInstance();
//            Date now = calendar.getTime();
//            String datastamp = simpleDateFormat.format(now);
//            String horastamp = simpleHoraFormat.format(now);
//
//            Usuarios anonimo = new Usuarios();
//            anonimo.setKeyusu(autenticacao.getUid());
//            anonimo.setNomeusu(CAD_NOME);
//            anonimo.setApelidousu(CAD_NOME);
//            anonimo.setEmailusu(CAD_EMAIL);
//            anonimo.setDatacadusu(datastamp);
//            anonimo.setHoracadusu(horastamp);
//            anonimo.setStatus(1);
//
//            Usuarios_Controller uc = new Usuarios_Controller(getActivity().getBaseContext());
//
//            boolean usu = false;
//            usu = uc.salvarusuario(anonimo);
//
//            if (usu) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Cad_Novo_Visitante(2, autenticacao.getUid())).commit();
//            }else{
//                util.showmessage(getContext(), "Problemas ao salvar usuário.");
//            }
//        }
//
    }

    public void fcontateste() {
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                Log.i("cadkey", "run: " + contateste);
//
//                if(contateste<15){
//                    criarPerfil();
//                }
//                mprogressBar.setVisibility(View.INVISIBLE);
//                util.showSnackError(clcad,"Problemas ao criar cadastro.\nVerifique conexão.");
//
//            }
//        }, 2000);
//
    }

}


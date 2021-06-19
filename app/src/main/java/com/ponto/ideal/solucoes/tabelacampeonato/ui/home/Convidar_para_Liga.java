package com.ponto.ideal.solucoes.tabelacampeonato.ui.home;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.controller.Usuarios_Controller;
import com.ponto.ideal.solucoes.tabelacampeonato.model.ConviteLiga;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_da_Liga;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Ligas;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Notificacoes;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Usuarios;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;

import java.util.UUID;

//import com.ponto.ideal.solucoes.tabelacampeonato.ui.campeonatos.NovoCampeonatoViewModel;


public class Convidar_para_Liga extends Fragment {

    private ImageView imgliga;
    private TextView txtjogador, txtnomeliga;
    private Button btbuscar, btenviar;
    private EditText etapelido;
    private ConstraintLayout clconvite;
    private CheckBox rbassistir,rbalterar,rbiniciar;

    private Usuarios userTo;
    private Usuarios userFrom;

    private Ligas LIGA;
    private String keyconvite;
    private int permissao;
    private Jogadores_da_Liga checkusu;

    public Convidar_para_Liga() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_convite__liga, container, false);

        initViews(v);
        return v;
    }

    public void initViews(View v){

        LIGA=HomeFragment.LIGA_ON;

        Usuarios_Controller uc = new Usuarios_Controller(getActivity().getBaseContext());
        userFrom = uc.usuatual(FirebaseAuth.getInstance().getUid());

        imgliga = v.findViewById(R.id.imgliga);
        txtjogador = v.findViewById(R.id.txtjogador);
        txtnomeliga = v.findViewById(R.id.txtnomeliga);
        btbuscar = v.findViewById(R.id.btbuscar);
        btenviar = v.findViewById(R.id.btenviar);
        etapelido = v.findViewById(R.id.etapelido);
        clconvite = v.findViewById(R.id.clconvite);
        rbassistir = v.findViewById(R.id.rbassistir);
        rbalterar  = v.findViewById(R.id.rbalterar );
        rbiniciar  = v.findViewById(R.id.rbiniciar );

        Bitmap bitmap = util.loadImageBitmap(getContext(),LIGA.getKeyliga());
        imgliga.setImageBitmap(bitmap);
        txtnomeliga.setText(LIGA.getNomeliga());
        etapelido.addTextChangedListener(textWatcher);

        etapelido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userTo=null;
                btenviar.setTextColor(Color.parseColor("#C6CCC6"));
                btenviar.setClickable(false);
                btenviar.setEnabled(false);
            }
        });
        etapelido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                etapelido.setText(etapelido.getText().toString().toLowerCase());
                if (!hasFocus) { userTo=null;
                    btenviar.setTextColor(Color.parseColor("#C6CCC6"));
                    btenviar.setClickable(false);
                    btenviar.setEnabled(false);
                    etapelido.setSelection(0);
                } else {

                }
            }
        });

        clconvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        btenviar.setClickable(false);
        btenviar.setEnabled(false);

        btenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtjogador.getText().toString().equals("Jogador"))
                    return;

                permissao = 0;

                if(rbiniciar.isChecked()){
                    permissao=3;
                }else if(rbalterar.isChecked()){
                    permissao=2;
                }else{
                    permissao=1;
                }
                enviarNot();
            }
        });

        btbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                userTo=null;
                txtjogador.setText("Jogador");
                String email = etapelido.getText().toString().trim().toLowerCase();

                FirebaseFirestore.getInstance().collection("/usuarios")
                    .whereEqualTo("emailusu", email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    userTo = doc.toObject(Usuarios.class);
                                }
                            }
                                if (userTo==null) {
                                    util.showmessage(getContext(), "E-mail não encontrado.");
                                }else{
                                    verify_userTo();
                                }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }

        });

        rbassistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rbassistir.isChecked()){
                    rbassistir.setChecked(true);
                }

            }
        });

        rbalterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rbalterar.isChecked() ){
                   // rbalterar.setChecked(true);
                }else{
                    rbalterar.setChecked(false);
                    rbiniciar.setChecked(false);
                }

            }
        });

        rbiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbiniciar.isChecked()){
                    rbalterar.setChecked(true);
                }else{
                    rbiniciar.setChecked(false);
                }
            }
        });
    }

    private void enviarMSG() {

        keyconvite = UUID.randomUUID().toString();
        ConviteLiga convite = new ConviteLiga();
        convite.setIdconvite(keyconvite);
        convite.setIdfrom(userFrom.getKeyusu());
        convite.setIdliga(LIGA.getKeyliga());
        convite.setNomeliga(LIGA.getNomeliga());
        convite.setNomefrom(userFrom.getApelidousu());
        convite.setIdto(userTo.getKeyusu());
        convite.setNometo(userTo.getApelidousu());
        convite.setTimestamp(System.currentTimeMillis());
        convite.setPermissao(permissao);
        convite.setStatus(0);

        FirebaseFirestore.getInstance().collection("convites")
                .document(convite.getIdto())
                .collection("convite_liga")
                .document(keyconvite)
                .set(convite)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                     
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        FirebaseFirestore.getInstance().collection("convites")
                .document(convite.getIdfrom())
                .collection("convite_liga")
                .document(keyconvite)
                .set(convite)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        util.showmessage(getContext(),"Mensagem enviada com sucesso!");
                        MainActivity.navController.navigate(R.id.action_convidar_para_Liga_to_info_Liga);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    private void enviarNot() {

        Notificacoes notificacoes = new Notificacoes();
        notificacoes.setFromid(userFrom.getKeyusu());
        notificacoes.setFromname(userFrom.getApelidousu());
        notificacoes.setToid(userTo.getKeyusu());
        String not = "Você recebeu um Convite para a liga " + LIGA.getNomeliga();
        notificacoes.setText(not);
        notificacoes.setTimestamp(System.currentTimeMillis());

        if(userTo.getToken()!=null) {
            FirebaseFirestore.getInstance().collection("notificacoes")
                    .document(userTo.getToken())
                    .set(notificacoes).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    enviarMSG();
                }
            });
        }else{
            enviarMSG();
        }
    }

    private void verify_userTo() {
        checkusu=new Jogadores_da_Liga();
        Log.i("documetsnap","LIGA.getKeyliga() documentSnapshot " + LIGA.getKeyliga());
        Log.i("documetsnap","userTo.getKeyusu() documentSnapshot " + userTo.getKeyusu());
        FirebaseFirestore.getInstance().collection("/ligas")
                .document(LIGA.getKeyliga())
                .collection("jogadores")
                .document(userTo.getKeyusu())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if(documentSnapshot!=null) {
                            Log.i("documetsnap","documentSnapshot ok");

                            Jogadores_da_Liga jl = documentSnapshot.toObject(Jogadores_da_Liga.class);
                            if(jl==null){
                                btenviar.setTextColor(Color.parseColor("#FF1F6612"));
                                btenviar.setClickable(true);
                                btenviar.setEnabled(true);
                                txtjogador.setText(userTo.getApelidousu());
                                Log.i("documetsnap","usuario nao tem na liga, convida: " );
                            }else{
                                if (jl.getPermissao() == 0 || jl.getPermissao() == 5) {
                                    Log.i("documetsnap","usuario bloqueado, convida");
                                btenviar.setTextColor(Color.parseColor("#FF1F6612"));
                                btenviar.setClickable(true);
                                btenviar.setEnabled(true);
                                txtjogador.setText(userTo.getApelidousu());

                                } else {
                                util.showmessage(getContext(), "Usuario ja cadastrado na liga.\nNome: " + jl.getNomejogador());
                                }
                            }


                        }else{
                            Log.i("documetsnap","documentSnapshot null");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            Log.i("documetsnap","Failure " + e.getMessage().toString() + " : " + e );            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence c, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private void hideSoftKeyboard() {
        etapelido.setText(etapelido.getText().toString().toLowerCase());

        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        etapelido.clearFocus();
    }

}

package com.ponto.ideal.solucoes.tabelacampeonato.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_da_Liga;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.ponto.ideal.solucoes.tabelacampeonato.view.MainActivity;

import java.util.ArrayList;


public class Alterar_Foto_Visitante extends Fragment implements View.OnClickListener {

    private static final String TAG = "InfoUsu";

    private static final String ID_JOG = "idjog";
    private static final String NOME_JOG = "nomejog";

    private String midjog;
    private String mnomejog;
    private LinearLayout llprofile,linearLayout6;
    private Jogadores_da_Liga jogador = new Jogadores_da_Liga();

    EditText etapelido;
    Button btsalvar,btcancelar;
    ConstraintLayout clcad_visitante;
    ImageView imgrefresh,imgperfil,imggalery,avat1,avat2,avat3,avat4,avat5,avat6,avat7,avat8,avat9,
            avat10,avat11,avat12,avat13,avat14,avat15,avat16,avat17,avat18,avat19,avat20,
            avat21,avat22,avat23,avat24,avat25;
    TextView txtselavatxgal,txtgallery,txttitle;
    private ProgressBar mprogressBar;
    private int idavatar=-1;
    private int avat[] = new int[25];

    private StorageReference storageReference;

    public Alterar_Foto_Visitante() {

    }

    public static Alterar_Foto_Visitante newInstance(String idjog, String nomejog) {
        Alterar_Foto_Visitante fragment = new Alterar_Foto_Visitante();
        Bundle args = new Bundle();
        args.putString(ID_JOG, idjog);
        args.putString(NOME_JOG, nomejog);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            midjog = getArguments().getString(ID_JOG);
            mnomejog = getArguments().getString(NOME_JOG);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cad_novo_visitante, container, false);


        clcad_visitante = view.findViewById(R.id.clcad_visitante);
        btsalvar = view.findViewById(R.id.btsalvar);
        btcancelar = view.findViewById(R.id.btcancelar);
        etapelido = view.findViewById(R.id.etapelido);
        imgperfil = view.findViewById(R.id.imgperfil);
        imggalery = view.findViewById(R.id.imggalery);
        imgrefresh = view.findViewById(R.id.imgrefresh);
        txtselavatxgal = view.findViewById(R.id.txtselavatxgal);
        txtgallery = view.findViewById(R.id.txtgallery);
        txttitle = view.findViewById(R.id.txttitle);
        mprogressBar = view.findViewById(R.id.mprogressBar);
        llprofile = view.findViewById(R.id.llprofile);
        linearLayout6 = view.findViewById(R.id.linearLayout6);

        txttitle.setText(getString(R.string.digite_apelido));
        btcancelar.setText(getString(R.string.cancelar));
        btsalvar.setText(getString(R.string.salvar));

        for(int i=0;i<HomeFragment.baseJogLiga.size();i++) {
            if (HomeFragment.baseJogLiga.get(i).getIdjogador().equals(midjog))
                jogador = HomeFragment.baseJogLiga.get(i);
        }

        switch (jogador.getTipojog()){
            case 0:

                int ii = 0;
                try {
                    ii = Integer.parseInt(jogador.getImgjogador());
                } catch (NumberFormatException e) {
                    Log.i("intimg"," erro: " + e.getMessage());
                }

                imgperfil.setImageResource((int)ii);
                linearLayout6.removeView(llprofile);
                txtselavatxgal.setText("Selecione Avatar");
                break;
        }

//
//        Bitmap bitmap = util.loadImageBitmap(getContext(),midjog);
//        if(bitmap==null){
//            idavatar=-1;
//        }else{
//            idavatar=-2;
//        }
//        Log.i("idavatar",": " + idavatar);
//        imgperfil.setImageBitmap(bitmap);
//
//        imggalery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 123);
//            }
//        });

        imgrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (jogador.getTipojog()){
                    case 0:

                        int ii = 0;
                        try {
                            ii = Integer.parseInt(jogador.getImgjogador());
                        } catch (NumberFormatException e) {
                            Log.i("intimg"," erro: " + e.getMessage());
                        }

                        imgperfil.setImageResource((int)ii);
                        linearLayout6.removeView(llprofile);
                        txtselavatxgal.setText("Selecione Avatar");
                        etapelido.setText(jogador.getNomejogador());

                        break;
                }

            }
        });

        etapelido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etapelido.setSelection(etapelido.length());

                }
            }
        });

        etapelido.setText(jogador.getNomejogador());
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
                MainActivity.navController.navigate(R.id.action_alterar_Foto_Visitante_to_info_Liga);

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

        if(HomeFragment.LIGA_ON.getTipoliga()==0) idavatar = Integer.parseInt(jogador.getImgjogador());
        clcad_visitante.setFocusable(true);

        clcad_visitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        clcad_visitante.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.entra_right_alerta));
        return view;
    }

//    @SuppressLint("MissingSuperCall")
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        final int heigth = 300;
//        final int width = 300;
//
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == 123) {
//                Uri imagemSelecionada = data.getData();
//                Picasso.get().load(imagemSelecionada.toString()).resize(width, heigth).centerCrop().into(imgperfil);
//                idavatar=0;
//            }
//        }
//    }


    public void salvanovovisitante(){

        util.vibra(getContext());
        etapelido.setText(etapelido.getText().toString().toUpperCase().trim());
        String napel = etapelido.getText().toString().toUpperCase().trim();

        if(napel.equals(null)|| napel.equals("")) {
            mprogressBar.setVisibility(View.INVISIBLE);
            util.showSnackError(clcad_visitante, "Insira um Nick para o jogador.");
            btcancelar.setClickable(true);
            btcancelar.setEnabled(true);
            btsalvar.setClickable(true);
            btsalvar.setEnabled(true);
            return;
        }

        if(!napel.equals(mnomejog)) {

            ArrayList<String> ntjog = new ArrayList<>();
            ntjog.clear();
            for (int i = 0; i < HomeFragment.baseJogLiga.size(); i++) {
                ntjog.add(HomeFragment.baseJogLiga.get(i).getNomejogador());
            }

            if (ntjog.contains(napel)) {
                mprogressBar.setVisibility(View.INVISIBLE);
                btcancelar.setClickable(true);
                btcancelar.setEnabled(true);
                btsalvar.setClickable(true);
                btsalvar.setEnabled(true);
                util.showSnackError(clcad_visitante, getString(R.string.ja_existe_jogador_com_este_nome));
                return;
            } else {
                salvaVisitante();
            }
        }else {

            salvaVisitante();
        }
    }

    private void salvaVisitante() {

        String napel = etapelido.getText().toString().toUpperCase().trim();

        jogador.setNomejogador(napel);
        jogador.setImgjogador(String.valueOf(idavatar));
        FirebaseFirestore.getInstance().collection("/ligas")
                .document(HomeFragment.LIGA_ON.getKeyliga())
                .collection("jogadores")
                .document(midjog)
                .set(jogador)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showmessage(getContext(), "Problemas ao inserir jogador");
            }
        });


                MainActivity.navController.navigate(R.id.action_alterar_Foto_Visitante_to_info_Liga);


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
            case R.id.avat21:imgperfil.setImageResource(avat[20]); idavatar = (avat[20]);      break;
            case R.id.avat22:imgperfil.setImageResource(avat[21]); idavatar = (avat[21]);      break;
            case R.id.avat23:imgperfil.setImageResource(avat[22]); idavatar = (avat[22]);      break;
            case R.id.avat24:imgperfil.setImageResource(avat[23]); idavatar = (avat[23]);      break;
            case R.id.avat25:imgperfil.setImageResource(avat[24]); idavatar = (avat[24]);      break;

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


}

package com.ponto.ideal.solucoes.tabelacampeonato.Alertas;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DatabaseReference;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JogosTeste;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Participantes;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.Classificacao;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.NovoCampeonatoViewModel;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;

import java.util.ArrayList;


public class Alerta_Jogo_Final extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    DatabaseReference databaseReference;


    NovoCampeonatoViewModel ncViewModel;
    JogosTeste jogofinal = new JogosTeste();
    String jog1,jog2;
    private int tipoturno;
    private int numpart;

    ArrayList<Participantes> partCamp = new ArrayList<>();

    private TextView txtnumjogo, txtjog1, txtjog2, placar1, placar2,penalti1,penalti2,txtx2,txtx1;
    private ImageView imgjog1, imgjog2;
    private Button btsalvar, btcancelar;
    private LinearLayout llmais1,llmenos1,llmais2,llmenos2,llsalvar;
    private ConstraintLayout clmainresult,clbotao;
    private Switch swpenalti;

    boolean penalti =false;
    int gols1=0;
    int gols2=0;
    int penal1=0;
    int penal2=0;
    float sobe=0f;
    float sobe2=0f;

    boolean bcancel=false;


    ObjectAnimator animation1;
    ObjectAnimator animation2;
    ObjectAnimator animation3;

    public Alerta_Jogo_Final(JogosTeste vjogofinal, String jog1, String jog2) {
        this.jogofinal = vjogofinal;
        this.jog1 = jog1;
        this.jog2 = jog2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_res_final, container, false);


        initViews(v);



        return v;
    }

    public void initViews(View v){

        setCancelable(false);

        ncViewModel = ViewModelProviders.of(requireActivity()).get(NovoCampeonatoViewModel.class);

        ncViewModel.getCampPart().observe(requireActivity(), new Observer<ArrayList<Participantes>>() {
            @Override
            public void onChanged(ArrayList<Participantes> participantes) {
                partCamp = participantes;
            }
        });

        llmais1 = v.findViewById(R.id. llmais1 );
        llmais2 = v.findViewById(R.id. llmais2 );
        llmenos1 = v.findViewById(R.id. llmenos1);
        llmenos2 = v.findViewById(R.id. llmenos2);

        btsalvar = v.findViewById(R.id.btsalvar);
        btcancelar = v.findViewById(R.id.btcancelar);
        clmainresult = v.findViewById(R.id.clmainresult);
        txtnumjogo = v.findViewById(R.id.txtnumjogo);
        txtjog1 = v.findViewById(R.id.txtjog1);
        txtjog2 = v.findViewById(R.id.txtjog2);
        placar1 = v.findViewById(R.id.placar1);
        placar2 = v.findViewById(R.id.placar2);
        penalti1 = v.findViewById(R.id.penalti1);
        penalti2 = v.findViewById(R.id.penalti2);
        imgjog1 = v.findViewById(R.id.imgjog1);
        imgjog2 = v.findViewById(R.id.imgjog2);
        swpenalti = v.findViewById(R.id.swpenalti);
        txtx2 = v.findViewById(R.id.txtx2);
        txtx1 = v.findViewById(R.id.txtx1);
        clbotao = v.findViewById(R.id.clbotao);
        llsalvar = v.findViewById(R.id.llsalvar);


        sobe2 = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 40,
                getContext().getResources().getDisplayMetrics() );
        //sobe2=penalti1.getHeight();


        animation1 = ObjectAnimator.ofFloat(penalti1, "scaleY", 0);
        animation1.setDuration(200);
        animation1.start();

        animation2 = ObjectAnimator.ofFloat(penalti2, "scaleY", 0);
        animation2.setDuration(200);
        animation2.start();

        animation3 = ObjectAnimator.ofFloat(txtx2, "scaleY", 0);
        animation3.setDuration(200);
        animation3.start();



        if(jogofinal!=null){

            gols1=jogofinal.getPlacar1().equals("_")?0:Integer.parseInt(jogofinal.getPlacar1());
            gols2=jogofinal.getPlacar2().equals("_")?0:Integer.parseInt(jogofinal.getPlacar2());
            penal1=jogofinal.getPenalti1().equals("")?0:Integer.parseInt(jogofinal.getPenalti1());
            penal2=jogofinal.getPenalti2().equals("")?0:Integer.parseInt(jogofinal.getPenalti2());

            if(!jogofinal.getPenalti1().equals("")) {



                placar1.setAlpha(0.2f);
                placar2.setAlpha(0.2f);
                txtx1.setAlpha(0.2f);



                ObjectAnimator animation = ObjectAnimator.ofFloat(clbotao, "translationY", sobe2);
                animation.setDuration(200);
                animation.start();
                ObjectAnimator animation4 = ObjectAnimator.ofFloat(llsalvar, "translationY", sobe2);
                animation4.setDuration(200);
                animation4.start();

                animation1.reverse();
                animation2.reverse();
                animation3.reverse();


                swpenalti.setChecked(true);
            }

            if(!jogofinal.getPenalti1().equals(""))swpenalti.setChecked(true);
            txtnumjogo.setText("Final");
            txtjog1.setText(jog1);
            txtjog2.setText(jog2);
            placar1.setText(String.valueOf(gols1));
            placar2.setText(String.valueOf(gols2));
            penalti1.setText(String.valueOf(penal1));
            penalti2.setText(String.valueOf(penal2));
            Bitmap bitmap1 = util.loadImageBitmap(getContext(), jogofinal.getIdjogador1());
            Bitmap bitmap2 = util.loadImageBitmap(getContext(), jogofinal.getIdjogador2());
            imgjog1.setImageBitmap(bitmap1);
            imgjog2.setImageBitmap(bitmap2);
        }




        swpenalti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 30);
                sobe=penalti1.getHeight();

                if(!placar1.getText().equals(placar2.getText())){
                    util.showSnackError(clmainresult,"partida não esta empatada.");
                    swpenalti.setChecked(false);
                }else {
                    if (swpenalti.isChecked()) {

                        Log.i("entrousw","is checed");
                        placar1.setAlpha(0.2f);
                        placar2.setAlpha(0.2f);
                        txtx1.setAlpha(0.2f);

                        ObjectAnimator animation = ObjectAnimator.ofFloat(clbotao, "translationY", sobe);
                        animation.setDuration(200);
                        animation.start();
                        ObjectAnimator animation4 = ObjectAnimator.ofFloat(llsalvar, "translationY", sobe);
                        animation4.setDuration(200);
                        animation4.start();

                        animation1.reverse();
                        animation2.reverse();
                        animation3.reverse();

                    } else {
                        Log.i("entrousw","else is checed");
                        penalti1.setText("0");
                        penalti2.setText("0");
                        placar1.setAlpha(1f);
                        placar2.setAlpha(1f);
                        txtx1.setAlpha(1f);
                        ObjectAnimator animation = ObjectAnimator.ofFloat(clbotao, "translationY", -sobe + penalti1.getHeight());
                        animation.setDuration(200);
                        animation.start();
                        ObjectAnimator animation4 = ObjectAnimator.ofFloat(llsalvar, "translationY", -sobe + penalti1.getHeight());
                        animation4.setDuration(200);
                        animation4.start();
                        animation1.start();
                        animation2.start();
                        animation3.start();
                    }
                }
            }
        });




        llmais1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 30);
                if(!swpenalti.isChecked()) {
                    gols1++;
                    placar1.setText(String.valueOf(gols1));
                    penal1=0;
                    penal2=0;
                    if (gols1 == 6) {
                        util.showSnackError(clmainresult, "Será que o " + jog2 +
                                " sabe realmente jogar ? ");
                    }
                }else {
                    penal1++;
                    penalti1.setText(String.valueOf(penal1));
                }
            }
        });

        llmais2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 30);
                if(!swpenalti.isChecked()) {
                    gols2++;
                    placar2.setText(String.valueOf(gols2));
                    penal1=0;
                    penal2=0;
                    if (gols2 == 6) {
                        util.showSnackError(clmainresult, "Será que o " + jog1 +
                                " sabe realmente jogar ? ");
                    }
                }else {
                    penal2++;
                    penalti2.setText(String.valueOf(penal2));
                }
            }
        });

        llmenos1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 30);
                if(!swpenalti.isChecked()) {
                    if (gols1 > 0) {
                        gols1--;
                    }
                    placar1.setText(String.valueOf(gols1));
                    penal1=0;
                    penal2=0;
                }else {
                    if (penal1 > 0) {
                        penal1--;
                        penalti1.setText(String.valueOf(penal1));
                    }
                }
            }
        });


        llmenos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 30);
                if(!swpenalti.isChecked()) {
                    if (gols2 > 0) {
                        gols2--;
                    }
                    placar2.setText(String.valueOf(gols2));
                    penal1=0;
                    penal2=0;
                }else {
                    if (penal2 > 0) {
                        penal2--;
                        penalti2.setText(String.valueOf(penal2));
                    }
                }
            }

        });



        btcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bcancel=true;
                util.vibratePhone(getContext(), (short) 30);
                btsalvar.setClickable(false);
                btsalvar.setEnabled(false);
                if(getShowsDialog()==true) {
                    getDialog().dismiss();
                }
            }
        });

        btsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 30);
                btcancelar.setClickable(false);
                btcancelar.setEnabled(false);
                salvar();
            }
        });


    }

    public void salvar(){

        if (placar1.getText().equals(placar2.getText())) {

            if((penalti1.getText().equals(penalti2.getText()))){
                btcancelar.setClickable(true);
                btcancelar.setEnabled(true);
                util.showmessage(getContext(),"É preciso definir um ganhador nos penaltis");
            }else {

                jogofinal.setPlacar1(placar1.getText().toString());
                jogofinal.setPlacar2(placar2.getText().toString());
                jogofinal.setPenalti1(penalti1.getText().toString());
                jogofinal.setPenalti2(penalti2.getText().toString());
                jogofinal.setStatusjogo(1);

                Classificacao.jogoFinal=jogofinal;
                ncViewModel.setBooleanfinal(true);
                getDialog().dismiss();
            }

        }else {

            jogofinal.setPlacar1(placar1.getText().toString());
            jogofinal.setPlacar2(placar2.getText().toString());
            jogofinal.setPenalti1("");
            jogofinal.setPenalti2("");
            jogofinal.setStatusjogo(1);

            Classificacao.jogoFinal=jogofinal;
            ncViewModel.setBooleanfinal(true);
            getDialog().dismiss();
        }

    }

}

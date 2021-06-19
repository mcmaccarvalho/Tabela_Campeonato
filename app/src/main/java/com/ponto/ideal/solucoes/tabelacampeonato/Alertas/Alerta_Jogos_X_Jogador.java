package com.ponto.ideal.solucoes.tabelacampeonato.Alertas;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JogosTeste;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Participantes;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.Classificacao;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.NovoCampeonatoViewModel;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;

public class Alerta_Jogos_X_Jogador extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    private TextView txtpt,txtsg,txtgp,txtgc,txtj,txtv,txtd,txte,txtmp,txtjog,txtclass;
    private ImageView imgjog;
    private RecyclerView rvresult;
    private GroupAdapter adapter;
    private ArrayList<JogosTeste> datajogos = new ArrayList<>();
    private  ArrayList<Participantes> partCamp = new ArrayList<>();
    private NovoCampeonatoViewModel ncViewModel;
    private Participantes partjog;
    private JogosTeste jfinal;
    private String idpart;

    private int tipoturno;
    private int numpart;

    public Alerta_Jogos_X_Jogador(Participantes partjog, JogosTeste jogosTeste) {
        this.partjog=partjog;
        this.jfinal=jogosTeste;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.jogos_jogador, container, false);


        initViews(v);



        return v;
    }

    private void initViews(View v) {

        txtpt = v.findViewById(R.id.txtpt);
        txtsg = v.findViewById(R.id.txtsg);
        txtgp = v.findViewById(R.id.txtgp);
        txtgc = v.findViewById(R.id.txtgc);
        txtj  = v.findViewById(R.id.txtj );
        txtv  = v.findViewById(R.id.txtv );
        txtd  = v.findViewById(R.id.txtd );
        txte  = v.findViewById(R.id.txte );
        txtmp = v.findViewById(R.id.txtmp);
        txtjog = v.findViewById(R.id.txtjog);
        txtclass = v.findViewById(R.id.txtclass);
        imgjog = v.findViewById(R.id.imgjog);
        rvresult = v.findViewById(R.id.rvresult);



        ncViewModel.getCampPart().observe(this,new Observer<ArrayList<Participantes>>() {
            @Override
            public void onChanged(ArrayList<Participantes> p) {
                partCamp=p;
            }
        });

        datajogos=NovoCampeonatoViewModel.getListajogos();
        tipoturno= NovoCampeonatoViewModel.getTipoturno();
        numpart= NovoCampeonatoViewModel.getNumpart();

        int maxptgeral=0;
        switch (tipoturno){
            case 1:maxptgeral=(numpart-1)*3;break;
            case 2:maxptgeral=((numpart-1)*3)*2;break;
            case 3:maxptgeral=(numpart-1)*3;break;
            case 4:maxptgeral=((numpart-1)*3)*2;break;
        }
        maxptgeral=(maxptgeral-(partjog.getJogos()*3)+partjog.getPontos());

        idpart=partjog.getId_jogador();

        int saldo = partjog.getGolspro()-partjog.getGolscontra();
        txtpt.setText(String.valueOf(partjog.getPontos()));
        txtsg.setText(String.valueOf(saldo));
        txtgp.setText(String.valueOf(partjog.getGolspro()));
        txtgc.setText(String.valueOf(partjog.getGolscontra()));
        txtj .setText(String.valueOf(partjog.getJogos()));
        txtv .setText(String.valueOf(partjog.getVitorias()));
        txtd .setText(String.valueOf(partjog.getDerrotas()));
        txte .setText(String.valueOf(partjog.getEmpates()));
        txtmp.setText(String.valueOf(maxptgeral));
        txtjog.setText(partjog.getNome_jogador());
        Bitmap bitmap1 = util.loadImageBitmap(getContext(), idpart);
        imgjog.setImageBitmap(bitmap1);
        txtclass.setText(String.valueOf(partjog.getClass_jogador()));

        adapter = new GroupAdapter();
        adapter.clear();

        carregaAdapter();
    }

    public void carregaAdapter(){
        adapter.clear();
        for(int i=0;i<datajogos.size();i++){

            if(datajogos.get(i).getIdjogador1().equals(idpart) ||
            datajogos.get(i).getIdjogador2().equals(idpart)){
                adapter.add(new ListaJogosItem(datajogos.get(i)));
            }

        }

        if(jfinal!=null
                &&jfinal.getIdjogador1()!=null
                && jfinal.getIdjogador2()!=null
                && tipoturno>2){
            if(jfinal.getIdjogador1().equals(idpart) ||
                    jfinal.getIdjogador2().equals(idpart)){
                adapter.add(new ListaJogosItem(jfinal));
            }
        }
        rvresult.setLayoutManager(new LinearLayoutManager(getContext()));
        rvresult.setAdapter(adapter);

    }

    private class ListaJogosItem extends Item<ViewHolder> {

        private final JogosTeste jogosTeste;

        public ListaJogosItem(JogosTeste jogosTeste) {
            this.jogosTeste =jogosTeste;
        }



        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtjog1 = viewHolder.itemView.findViewById(R.id.txtjog1);
            TextView txtjog2 = viewHolder.itemView.findViewById(R.id.txtjog2);
            TextView placar1 = viewHolder.itemView.findViewById(R.id.placar1);
            TextView placar2 = viewHolder.itemView.findViewById(R.id.placar2);
            TextView txtp1 = viewHolder.itemView.findViewById(R.id.txtp1);
            TextView txtx1 = viewHolder.itemView.findViewById(R.id.txtx1);
            TextView txtp2 = viewHolder.itemView.findViewById(R.id.txtp2);
            ConstraintLayout clexibirjogos = viewHolder.itemView.findViewById(R.id.clexibir_jogos);
            final ImageView imgjog1 = viewHolder.itemView.findViewById(R.id.imgjog1);
            final ImageView imgjog2 = viewHolder.itemView.findViewById(R.id.imgjog2);

            txtp1.setText("");
            txtp2.setText("");
            txtx1 .setText("X");
            if(jogosTeste.getStatusjogo()>0 ){
                clexibirjogos.setBackgroundColor(Color.RED);
            }else{
                clexibirjogos.setBackgroundResource(R.drawable.grama);
            }

            if(jogosTeste.getIdjogo()==100){
                clexibirjogos.setBackgroundColor(Color.parseColor("#FF1F6612"));
                txtx1 .setText("F");
                if(!jogosTeste.getPenalti1().equals("")){
                    txtp1.setText(String.valueOf(jogosTeste.getPenalti1()));
                    txtp2.setText(String.valueOf(jogosTeste.getPenalti2()));

                }
            }
            if(jogosTeste.getIdjogo()== Classificacao.idjogoatual &&
                    jogosTeste.getStatusjogo()==0){
                clexibirjogos.setBackgroundColor(Color.parseColor("#3F51B5"));
            }
            txtjog1.setText(nomejog(jogosTeste.getIdjogador1()));
            txtjog2.setText(nomejog(jogosTeste.getIdjogador2()));
            placar1.setText(jogosTeste.getPlacar1());
            placar2.setText(jogosTeste.getPlacar2());
            imgjog1.setImageBitmap(imgjog(jogosTeste.getIdjogador1()));
            imgjog2.setImageBitmap(imgjog(jogosTeste.getIdjogador2()));
            Log.i("idimg","Idjo: " +jogosTeste.getIdjogador1());

        }

        @Override
        public int getLayout() {
            return R.layout.lista_jogos_jogador;
        }
    }

    public String nomejog(String id){
        String nome= "www";
        Log.i("partcamp",": " + partCamp.size());
        for(int i=0;i<partCamp.size();i++){
            if(partCamp.get(i).getId_jogador().equals(id)){
                nome=partCamp.get(i).getNome_jogador();
            }
        }
        return nome;
    }

    public Bitmap imgjog(String id){
        Bitmap img= null;
        for(int i=0;i<partCamp.size();i++){
            if(partCamp.get(i).getId_jogador().equals(id)){
                img= NovoCampeonatoViewModel.getBitimgjog().get(partCamp.get(i).getIdimg());
            }
        }
        return img;
    }

}

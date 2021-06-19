package com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ponto.ideal.solucoes.tabelacampeonato.Alertas.Alerta_Resultado_Jogo;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JogosTeste;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Participantes;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tabela_de_Jogos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tabela_de_Jogos extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private NovoCampeonatoViewModel ncViewModel;


    private ArrayList<JogosTeste> listajogos = new ArrayList<>();
    private ArrayList<Participantes> part = new ArrayList<>();
    private ArrayList<JogosTeste> dataset = new ArrayList<>();
    private RecyclerView rvjogos;
    private GroupAdapter adapter;
    public Tabela_de_Jogos() {
        // Required empty public constructor
    }


    public static Tabela_de_Jogos newInstance(String param1, String param2) {
        Tabela_de_Jogos fragment = new Tabela_de_Jogos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tabela_de__jogos, container, false);

        initViews(v);

        return v;
    }

    private void initViews(View v) {

        listajogos=NovoCampeonatoViewModel.getListajogos();
       // part=NovoCampeonatoViewModel.getCampPart();
        rvjogos = v.findViewById(R.id.rvjogos);

        ncViewModel= Campeonato.ncViewModel;

        adapter = new GroupAdapter();


        ncViewModel.getDataset().observe(requireActivity(), new Observer<ArrayList<JogosTeste>>() {
            @Override
            public void onChanged(ArrayList<JogosTeste> jogosTestes) {
                dataset=jogosTestes;
                carregaAdapter();
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                ListaJogosItem listaJogosItem = (ListaJogosItem) item;

                JogosTeste jt = listaJogosItem.jogosTeste;
                Classificacao.idjogoatual=jt.getIdjogo();
                Alerta_Resultado_Jogo alerta_resultado_jogo = new Alerta_Resultado_Jogo(dataset.get(jt.getIdjogo()),
                nomejog(dataset.get(jt.getIdjogo()).getIdjogador1()),
                nomejog(dataset.get(jt.getIdjogo()).getIdjogador2()));
                alerta_resultado_jogo.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
            }
        });

       carregaAdapter();
    }

    public void carregaAdapter(){
        adapter.clear();
        for(int i=0;i<listajogos.size();i++){
            if(dataset!=null && dataset.size()>0){ adapter.add(new ListaJogosItem(dataset.get(i)));}
        }
        rvjogos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvjogos.setAdapter(adapter);

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
            TextView txtp1 = viewHolder.itemView.findViewById (R.id.txtp1 );
            TextView txtp2 = viewHolder.itemView.findViewById (R.id.txtp2 );
            TextView txtnumjogo = viewHolder.itemView.findViewById (R.id.txtnumjogo );
            ConstraintLayout clexibirjogos = viewHolder.itemView.findViewById(R.id.clexibir_jogos);
            final ImageView imgjog1 = viewHolder.itemView.findViewById(R.id.imgjog1);
            final ImageView imgjog2 = viewHolder.itemView.findViewById(R.id.imgjog2);


            Log.i("reesultado",":numjog:"+jogosTeste.getNumjog()+
                    ":stat:"+jogosTeste.getStatusjogo()+
                    ":" + nomejog(jogosTeste.getIdjogador1())+
                    ":" + jogosTeste.getPlacar1()+
                    ":" + nomejog(jogosTeste.getIdjogador2())+
                    ":" + jogosTeste.getPlacar2());
            if(jogosTeste.getStatusjogo()>0){
                clexibirjogos.setBackgroundColor(Color.RED);
            }else{
                clexibirjogos.setBackgroundResource(R.drawable.grama);
            }

            if(jogosTeste.getIdjogo()==Classificacao.idjogoatual &&
            jogosTeste.getStatusjogo()==0){
                clexibirjogos.setBackgroundColor(getResources().getColor(R.color.teste));
            }
            txtjog1.setText(nomejog(jogosTeste.getIdjogador1()));
            txtjog2.setText(nomejog(jogosTeste.getIdjogador2()));
            txtnumjogo.setText(jogosTeste.getNumjog());
            placar1.setText(jogosTeste.getPlacar1());
            placar2.setText(jogosTeste.getPlacar2());
            imgjog1.setImageBitmap(imgjog(jogosTeste.getIdjogador1()));
            imgjog2.setImageBitmap(imgjog(jogosTeste.getIdjogador2()));
            Log.i("idimg","Idjo: " +jogosTeste.getIdjogador1());

        }

        @Override
        public int getLayout() {
            return R.layout.lista_jogos;
        }
    }

    public String nomejog(String id){
        String nome= null;
        for(int i=0;i<part.size();i++){
//            if(NovoCampeonatoViewModel.getCampPart().get(i).getId_jogador().equals(id)){
//                nome=part.get(i).getNome_jogador();
//            }
        }
        return nome;
    }

    public Bitmap imgjog(String id){
        Bitmap img= null;
        for(int i=0;i<part.size();i++){
            if(part.get(i).getId_jogador().equals(id)){
                img=NovoCampeonatoViewModel.getBitimgjog().get(part.get(i).getIdimg());
            }
        }
        return img;
    }

}
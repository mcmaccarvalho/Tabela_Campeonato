package com.ponto.ideal.solucoes.tabelacampeonato.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_Inscritos;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.Inscrever_Jogadores;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.NovoCampeonatoViewModel;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;

import java.util.ArrayList;
import java.util.List;

public class Joadores_Inscritos_Adapter extends RecyclerView.Adapter<Joadores_Inscritos_Adapter.ViewHolder> {

        private List<Jogadores_Inscritos>  joglist = new ArrayList() ;
        SparseBooleanArray itemStateArray = new SparseBooleanArray();
        NovoCampeonatoViewModel ncviewModel;

        private Context context;

        public Joadores_Inscritos_Adapter(List<Jogadores_Inscritos> listajog
                , Context ctx,NovoCampeonatoViewModel ncviewModel1) {
            joglist = listajog;
            ncviewModel=ncviewModel1;
            context = ctx;
        }

        @Override
        public Joadores_Inscritos_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                              int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.jogadores_incricao, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.bind(position);
//            Jogadores_Inscritos jogadoresins = joglist.get(position);
//            holder.txtnomeliga.setText(jogadoresins.getNomeins());
//            Bitmap bitmap = util.loadImageBitmap(context,jogadoresins.getIdins());
//            holder.imgshare.setImageBitmap(bitmap);
//
//
//            if (jogadoresins.getSelected()) {
//                holder.selectionState.setChecked(true);
//            } else {
//                holder.selectionState.setChecked(false);
//            }

        }



        @Override
            public int getItemCount() {
                return joglist.size();
            }

            void loaditem(List<Jogadores_Inscritos> jogins){
                this.joglist =jogins;
            }

            public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

                public TextView txtnomeliga;
                public CheckBox selectionState;
                private ImageView imgshare;
                private NovoCampeonatoViewModel vm = ncviewModel;



                public ViewHolder(View view) {
                    super(view);
                    txtnomeliga =  view.findViewById(R.id.txtnomeliga);

                    selectionState =  view.findViewById(R.id.cbjog);
                    imgshare = view.findViewById(R.id.imgshare);

                    selectionState.setClickable(false);
                    //item click event listener
                    view.setOnClickListener(this);

                    //checkbox click event handling
    //                selectionState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    //                    @Override
    //                    public void onCheckedChanged(CompoundButton buttonView,
    //                                                 boolean isChecked) {
    //                        if (isChecked) {
    //
    //
    //                            Inscrever_Jogadores.jog1=txtnomeliga.getText().toString();
    //                            util.showmessage(context,"esta marcado");
    //                        } else {
    //                            util.showmessage(context,"nao esta marcado");
    //                        }
    //                    }
    //                });
                }

                void bind(int position) {

                    txtnomeliga.setText(joglist.get(position).getNomeins());
                    Bitmap bitmap = util.loadImageBitmap(context,joglist.get(position).getIdins());
                    imgshare.setImageBitmap(bitmap);
                    // use the sparse boolean array to check
                    if (!itemStateArray.get(position, false)) {

                        selectionState.setChecked(false);}
                    else {

                        selectionState.setChecked(true);
                    }
                 //   selectionState.setText(String.valueOf(joglist.get(position).getPosition()));
                }


                @Override
                public void onClick(View v) {

                    int sizeins = Inscrever_Jogadores.inscritos.size();
                    int numpart = ncviewModel.getNumpart();

                    int adapterPosition = getAdapterPosition();
                    if (!itemStateArray.get(adapterPosition, false)) {
                        if(sizeins>=numpart){
                            util.showmessage(context,"Voce já selecionou os jogadores para esse campeonato.");
                            return;
                        }
                        selectionState.setChecked(true);
                        itemStateArray.put(adapterPosition, true);
                        Inscrever_Jogadores.inscritos.add(joglist.get(adapterPosition));
                    }
                    else  {
                        selectionState.setChecked(false);
                        itemStateArray.put(adapterPosition, false);
                                            for(int i=0;i<Inscrever_Jogadores.inscritos.size();i++){
                            if(Inscrever_Jogadores.inscritos.get(i).getIdins().equals(joglist.get(adapterPosition).getIdins())){
                                Inscrever_Jogadores.inscritos.remove(i);
                            }
                        }
                    }
                    vm.setBolaltins(true);

                }
            }
        }

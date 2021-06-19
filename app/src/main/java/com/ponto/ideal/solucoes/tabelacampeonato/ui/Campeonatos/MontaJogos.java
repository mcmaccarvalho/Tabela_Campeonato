package com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos;

import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_Inscritos;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JogosTeste;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.home.HomeFragment;

import java.util.ArrayList;

public class MontaJogos {

    private String keyCampeonato;
    private int numpart;
    private int tipoturno;
    private int numjogos;
    private ArrayList<Jogadores_Inscritos> jogins = new ArrayList<>();
    private NovoCampeonatoViewModel ncViewModel;
    private ArrayList<JogosTeste> jogosArray = new ArrayList<>();

    private String A ="A";
    private String B ="B";
    private String C ="C";
    private String D ="D";
    private String E ="E";
    private String F ="F";
    private String[] todos = new String[6];


    public MontaJogos(String keyCamp,NovoCampeonatoViewModel ncView){
        this.keyCampeonato=keyCamp;
        this.ncViewModel=ncView;
    }

    public ArrayList<JogosTeste> jogos (){

        numpart=NovoCampeonatoViewModel.getNumpart();
        tipoturno=NovoCampeonatoViewModel.getTipoturno();
        numjogos=NovoCampeonatoViewModel.getNumjogos();
        jogins=NovoCampeonatoViewModel.getJogadores_inscritos();

       A=NovoCampeonatoViewModel.getA() ;
       B=NovoCampeonatoViewModel.getB() ;
       C=NovoCampeonatoViewModel.getC() ;
       D=NovoCampeonatoViewModel.getD() ;
       E=NovoCampeonatoViewModel.getE() ;
       F=NovoCampeonatoViewModel.getF() ;


        todos[0]=A;
        todos[1]=B;
        todos[2]=C;
        todos[3]=D;
        todos[4]=E;
        todos[5]=F;


        int intdataset =(numpart*10)+tipoturno;


        int nturno=1;



       jogosArray.clear();

        for(int i=0;i<numjogos;i++){
            if(tipoturno==2||tipoturno==4){
                if(i>=(numjogos/2)){
                    nturno=2;
                }
            }
            JogosTeste jj = new JogosTeste();
            jj.setIdjogador1("id1 "+ i);
            jj.setIdjogador2("id2 "+ i);
            jj.setKeycampeonato(keyCampeonato);
            jj.setKeyliga(HomeFragment.LIGA_ON.getKeyliga());
            jj.setNumjog(String.valueOf(i));
            jj.setPenalti1("_");
            jj.setPenalti2("_");
            jj.setPlacar1("_");
            jj.setPlacar2("_");
            jj.setStatusjogo(0);
            jj.setNumjog(String.valueOf(i+1));
            jj.setTurno(nturno);
            jj.setIdjogo(i);
            jogosArray.add(jj);
        }



        switch (intdataset){


            case 31:monta31();break;
            case 32:monta32();break;
            case 33:monta31();break;
            case 34:monta32();break;
            case 41:monta41();break;
            case 42:monta42();break;
            case 43:monta41();break;
            case 44:monta42();break;
            case 51:monta51();break;
            case 52:monta52();break;
            case 53:monta51();break;
            case 54:monta52();break;
            case 61:monta61();break;
            case 62:monta62();break;
            case 63:monta61();break;
            case 64:monta62();break;

        }




        return jogosArray;

    }


    private void monta31() {
        jogosArray.get(0).setIdjogador1(A); jogosArray.get(0).setIdjogador2(B);
        jogosArray.get(1).setIdjogador1(A); jogosArray.get(1).setIdjogador2(C);
        jogosArray.get(2).setIdjogador1(B); jogosArray.get(2).setIdjogador2(C);
    }

    private void monta32() {
        jogosArray.get(0).setIdjogador1(A); jogosArray.get(0).setIdjogador2(B);
        jogosArray.get(1).setIdjogador1(A); jogosArray.get(1).setIdjogador2(C);
        jogosArray.get(2).setIdjogador1(B); jogosArray.get(2).setIdjogador2(C);
        jogosArray.get(3).setIdjogador1(B); jogosArray.get(3).setIdjogador2(A);
        jogosArray.get(4).setIdjogador1(C); jogosArray.get(4).setIdjogador2(A);
        jogosArray.get(5).setIdjogador1(C); jogosArray.get(5).setIdjogador2(B);
    }

    private void monta41() {
        jogosArray.get(0).setIdjogador1(A); jogosArray.get(0).setIdjogador2(B);
        jogosArray.get(1).setIdjogador1(C); jogosArray.get(1).setIdjogador2(D);
        jogosArray.get(2).setIdjogador1(A); jogosArray.get(2).setIdjogador2(C);
        jogosArray.get(3).setIdjogador1(B); jogosArray.get(3).setIdjogador2(D);
        jogosArray.get(4).setIdjogador1(C); jogosArray.get(4).setIdjogador2(B);
        jogosArray.get(5).setIdjogador1(A); jogosArray.get(5).setIdjogador2(D);
    }

    private void monta42() {
        jogosArray.get(0).setIdjogador1 (A); jogosArray.get(0) .setIdjogador2(B);
        jogosArray.get(1).setIdjogador1 (C); jogosArray.get(1) .setIdjogador2(D);
        jogosArray.get(2).setIdjogador1 (A); jogosArray.get(2) .setIdjogador2(C);
        jogosArray.get(3).setIdjogador1 (B); jogosArray.get(3) .setIdjogador2(D);
        jogosArray.get(4).setIdjogador1 (C); jogosArray.get(4) .setIdjogador2(B);
        jogosArray.get(5).setIdjogador1 (A); jogosArray.get(5) .setIdjogador2(D);
        jogosArray.get(6).setIdjogador1 (D); jogosArray.get(6) .setIdjogador2(C);
        jogosArray.get(7).setIdjogador1 (B); jogosArray.get(7) .setIdjogador2(A);
        jogosArray.get(8).setIdjogador1 (C); jogosArray.get(8) .setIdjogador2(A);
        jogosArray.get(9).setIdjogador1 (D); jogosArray.get(9) .setIdjogador2(B);
        jogosArray.get(10).setIdjogador1(B); jogosArray.get(10).setIdjogador2(C);
        jogosArray.get(11).setIdjogador1(D); jogosArray.get(11).setIdjogador2(A);
    }

    private void monta51() {
        jogosArray.get(0).setIdjogador1 (A); jogosArray.get(0) .setIdjogador2(B);
        jogosArray.get(1).setIdjogador1 (C); jogosArray.get(1) .setIdjogador2(D);
        jogosArray.get(2).setIdjogador1 (E); jogosArray.get(2) .setIdjogador2(A);
        jogosArray.get(3).setIdjogador1 (D); jogosArray.get(3) .setIdjogador2(B);
        jogosArray.get(4).setIdjogador1 (E); jogosArray.get(4) .setIdjogador2(C);
        jogosArray.get(5).setIdjogador1 (A); jogosArray.get(5) .setIdjogador2(D);
        jogosArray.get(6).setIdjogador1 (C); jogosArray.get(6) .setIdjogador2(B);
        jogosArray.get(7).setIdjogador1 (E); jogosArray.get(7) .setIdjogador2(D);
        jogosArray.get(8).setIdjogador1 (C); jogosArray.get(8) .setIdjogador2(A);
        jogosArray.get(9).setIdjogador1 (B); jogosArray.get(9) .setIdjogador2(E);
    }

    private void monta52() {
        jogosArray.get(0).setIdjogador1 (A); jogosArray.get(0) .setIdjogador2(B);
        jogosArray.get(1).setIdjogador1 (C); jogosArray.get(1) .setIdjogador2(D);
        jogosArray.get(2).setIdjogador1 (E); jogosArray.get(2) .setIdjogador2(A);
        jogosArray.get(3).setIdjogador1 (D); jogosArray.get(3) .setIdjogador2(B);
        jogosArray.get(4).setIdjogador1 (E); jogosArray.get(4) .setIdjogador2(C);
        jogosArray.get(5).setIdjogador1 (A); jogosArray.get(5) .setIdjogador2(D);
        jogosArray.get(6).setIdjogador1 (C); jogosArray.get(6) .setIdjogador2(B);
        jogosArray.get(7).setIdjogador1 (E); jogosArray.get(7) .setIdjogador2(D);
        jogosArray.get(8).setIdjogador1 (C); jogosArray.get(8) .setIdjogador2(A);
        jogosArray.get(9).setIdjogador1 (B); jogosArray.get(9) .setIdjogador2(E);
        jogosArray.get(10).setIdjogador1 (D); jogosArray.get(10) .setIdjogador2(C);
        jogosArray.get(11).setIdjogador1 (B); jogosArray.get(11) .setIdjogador2(A);
        jogosArray.get(12).setIdjogador1 (D); jogosArray.get(12) .setIdjogador2(E);
        jogosArray.get(13).setIdjogador1 (B); jogosArray.get(13) .setIdjogador2(C);
        jogosArray.get(14).setIdjogador1 (A); jogosArray.get(14) .setIdjogador2(E);
        jogosArray.get(15).setIdjogador1 (B); jogosArray.get(15) .setIdjogador2(D);
        jogosArray.get(16).setIdjogador1 (A); jogosArray.get(16) .setIdjogador2(C);
        jogosArray.get(17).setIdjogador1 (E); jogosArray.get(17) .setIdjogador2(B);
        jogosArray.get(18).setIdjogador1 (D); jogosArray.get(18) .setIdjogador2(A);
        jogosArray.get(19).setIdjogador1 (C); jogosArray.get(19) .setIdjogador2(E);
    }

    private void monta61() {
        jogosArray.get(0).setIdjogador1 (A); jogosArray.get(0)  .setIdjogador2(B);
        jogosArray.get(1).setIdjogador1 (C); jogosArray.get(1)  .setIdjogador2(D);
        jogosArray.get(2).setIdjogador1 (E); jogosArray.get(2)  .setIdjogador2(F);
        jogosArray.get(3).setIdjogador1 (A); jogosArray.get(3)  .setIdjogador2(D);
        jogosArray.get(4).setIdjogador1 (E); jogosArray.get(4)  .setIdjogador2(B);
        jogosArray.get(5).setIdjogador1 (C); jogosArray.get(5)  .setIdjogador2(F);
        jogosArray.get(6).setIdjogador1 (E); jogosArray.get(6)  .setIdjogador2(D);
        jogosArray.get(7).setIdjogador1 (A); jogosArray.get(7)  .setIdjogador2(F);
        jogosArray.get(8).setIdjogador1 (C); jogosArray.get(8)  .setIdjogador2(B);
        jogosArray.get(9).setIdjogador1 (F); jogosArray.get(9)  .setIdjogador2(D);
        jogosArray.get(10).setIdjogador1(A); jogosArray.get(10) .setIdjogador2(E);
        jogosArray.get(11).setIdjogador1(D); jogosArray.get(11) .setIdjogador2(B);
        jogosArray.get(12).setIdjogador1(E); jogosArray.get(12) .setIdjogador2(C);
        jogosArray.get(13).setIdjogador1(B); jogosArray.get(13) .setIdjogador2(F);
        jogosArray.get(14).setIdjogador1(A); jogosArray.get(14) .setIdjogador2(C);

    }

    private void monta62() {

        jogosArray.get(0).setIdjogador1 (A); jogosArray.get(0)  .setIdjogador2(B);
        jogosArray.get(1).setIdjogador1 (C); jogosArray.get(1)  .setIdjogador2(D);
        jogosArray.get(2).setIdjogador1 (E); jogosArray.get(2)  .setIdjogador2(F);
        jogosArray.get(3).setIdjogador1 (A); jogosArray.get(3)  .setIdjogador2(D);
        jogosArray.get(4).setIdjogador1 (E); jogosArray.get(4)  .setIdjogador2(B);
        jogosArray.get(5).setIdjogador1 (C); jogosArray.get(5)  .setIdjogador2(F);
        jogosArray.get(6).setIdjogador1 (E); jogosArray.get(6)  .setIdjogador2(D);
        jogosArray.get(7).setIdjogador1 (A); jogosArray.get(7)  .setIdjogador2(F);
        jogosArray.get(8).setIdjogador1 (C); jogosArray.get(8)  .setIdjogador2(B);
        jogosArray.get(9).setIdjogador1 (F); jogosArray.get(9)  .setIdjogador2(D);
        jogosArray.get(10).setIdjogador1(A); jogosArray.get(10) .setIdjogador2(E);
        jogosArray.get(11).setIdjogador1(D); jogosArray.get(11) .setIdjogador2(B);
        jogosArray.get(12).setIdjogador1(E); jogosArray.get(12) .setIdjogador2(C);
        jogosArray.get(13).setIdjogador1(B); jogosArray.get(13) .setIdjogador2(F);
        jogosArray.get(14).setIdjogador1(A); jogosArray.get(14) .setIdjogador2(C);
        jogosArray.get(15).setIdjogador1(D); jogosArray.get(15) .setIdjogador2(F);
        jogosArray.get(16).setIdjogador1(B); jogosArray.get(16) .setIdjogador2(A);
        jogosArray.get(17).setIdjogador1(F); jogosArray.get(17) .setIdjogador2(E);
        jogosArray.get(18).setIdjogador1(D); jogosArray.get(18) .setIdjogador2(C);
        jogosArray.get(19).setIdjogador1(B); jogosArray.get(19) .setIdjogador2(E);
        jogosArray.get(20).setIdjogador1(D); jogosArray.get(20) .setIdjogador2(A);
        jogosArray.get(21).setIdjogador1(F); jogosArray.get(21) .setIdjogador2(C);
        jogosArray.get(22).setIdjogador1(D); jogosArray.get(22) .setIdjogador2(E);
        jogosArray.get(23).setIdjogador1(F); jogosArray.get(23) .setIdjogador2(A);
        jogosArray.get(24).setIdjogador1(B); jogosArray.get(24) .setIdjogador2(C);
        jogosArray.get(25).setIdjogador1(E); jogosArray.get(25) .setIdjogador2(A);
        jogosArray.get(26).setIdjogador1(B); jogosArray.get(26) .setIdjogador2(D);
        jogosArray.get(27).setIdjogador1(C); jogosArray.get(27) .setIdjogador2(E);
        jogosArray.get(28).setIdjogador1(F); jogosArray.get(28) .setIdjogador2(B);
        jogosArray.get(29).setIdjogador1(C); jogosArray.get(29) .setIdjogador2(A);
    }



}

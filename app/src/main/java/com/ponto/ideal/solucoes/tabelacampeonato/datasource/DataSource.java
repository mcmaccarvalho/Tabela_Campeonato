package com.ponto.ideal.solucoes.tabelacampeonato.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Campeonato_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Jogador_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Jogadores_da_Liga_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Jogos_Dataset_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Ligas_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.datamodel.Usuarios_DataModel;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JCampeonato;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogador;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Jogadores_da_Liga;
import com.ponto.ideal.solucoes.tabelacampeonato.model.JogosTeste;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Ligas;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Usuarios;

import java.util.ArrayList;

public class DataSource extends SQLiteOpenHelper {

    private static final String DB_NAME = "tabela_campeonato.sqlite";
    private static final int DB_VERSION = 1;

    SQLiteDatabase db;
    Cursor cursor;

    public DataSource(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Usuarios_DataModel.criarTabela());
        } catch (Exception e) {
            Log.e("sqlite error: ", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean insert(String tabela, ContentValues dados) {

        boolean sucesso = true;
        try {
            sucesso = db.insert(tabela, null, dados) > 0;

        } catch (Exception e) {
            sucesso = false;
        }
        return sucesso;
    }

    public boolean deletar(String tabela, int id) {

        boolean sucesso = true;

        sucesso = db.delete(tabela, "id=?",

                new String[]{Integer.toString(id)}) > 0;

        return sucesso;
    }

    public void deletarTabela(String tabela) {

        try {
            db.execSQL("DROP TABLE IF EXISTS " + tabela);
        } catch (Exception e) {

        }

    }

    public void criarTabela(String queryCriarTabela) {

        try {
                db.execSQL(queryCriarTabela);
        } catch (SQLiteCantOpenDatabaseException e) {

        }

    }

    /*   **********************     Usuarios      ************************* */



    public Usuarios usuariotual(String tabela, String keyusu) {

        String[] columns = {
                "id",
                "keyusu",
                "nomeusu",
                "apelidousu",
                "emailusu",
                "imagemusuario",
                "timestamp",
                "status",
                "token",
                "online"
        };

        String[] args = new String[]{keyusu};
        Cursor cursor = db.query(Usuarios_DataModel.getTABELA(), columns, "keyusu = ? ", args, null, null, null);

        if (cursor.getCount() > 0) {

            Usuarios obj;

            if (cursor.moveToFirst()) {

                obj = new Usuarios();

                obj.setId(cursor.getInt(cursor.getColumnIndex(Usuarios_DataModel.getId())));
                obj.setEmailusu(cursor.getString(cursor.getColumnIndex(Usuarios_DataModel.getEmailusu())));
                obj.setImagemusuario(cursor.getString(cursor.getColumnIndex(Usuarios_DataModel.getImagemusuario())));
                obj.setNomeusu(cursor.getString(cursor.getColumnIndex(Usuarios_DataModel.getNomeusu())));
                obj.setKeyusu(cursor.getString(cursor.getColumnIndex(Usuarios_DataModel.getKeyusu())));
                obj.setTimestamp(cursor.getLong(cursor.getColumnIndex(Usuarios_DataModel.getTimestamp())));
                obj.setApelidousu(cursor.getString(cursor.getColumnIndex(Usuarios_DataModel.getApelidousu())));
                obj.setStatus(cursor.getInt(cursor.getColumnIndex(Usuarios_DataModel.getStatus())));
                obj.setOnline(cursor.getInt(cursor.getColumnIndex(Usuarios_DataModel.getOnline())));
                obj.setToken(cursor.getString(cursor.getColumnIndex(Usuarios_DataModel.getToken())));

                return obj;
            }
            cursor.close();
        }

        return null;
    }

    public boolean updateapelido(String tabela, String uid, ContentValues dados) {
        db.update(tabela, dados, "keyusu=?", new String[]{uid});
        return true;
    }




    /*   **********************     LIgas      ************************* */

    public boolean atualizaLigas(String tabela, String keyliga, ContentValues dados) {
        db.update(tabela, dados, "keyliga=?", new String[]{keyliga});
        return true;
    }

    public ArrayList<Ligas> getAllLigas() {
        Ligas obj;
        ArrayList<Ligas> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + Ligas_DataModel.getTABELA() + " ORDER BY id";
        cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                obj = new Ligas();
                obj.setId          (cursor.getInt   (cursor.getColumnIndex(Ligas_DataModel      .getId         ())));
                obj.setKeyliga     (cursor.getString(cursor.getColumnIndex(Ligas_DataModel      .getKeyliga    ())));
                obj.setNomeliga    (cursor.getString(cursor.getColumnIndex(Ligas_DataModel      .getNomeliga   ())));
                obj.setAdmin    (cursor.getString(cursor.getColumnIndex(Ligas_DataModel      .getAdmin   ())));
                obj.setDatacadliga    (cursor.getLong(cursor.getColumnIndex(Ligas_DataModel      .getDatacadliga   ())));
                obj.setTipoliga    (cursor.getInt(cursor.getColumnIndex(Ligas_DataModel      .getTipoliga   ())));
                obj.setStatusliga  (cursor.getInt   (cursor.getColumnIndex(Ligas_DataModel      .getStatusliga ())));


                lista.add(obj);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    public Ligas buscaLiga(String idliga){
        String[] columns = {
                "id",
                "keyliga",
                "nomeliga",
                "admin",
                "datacadliga",
                "tipoliga",
                "statusliga"
        };

        String[] args = new String[]{idliga};
        Cursor cursor = db.query(Ligas_DataModel.getTABELA(), columns, "keyliga = ? ", args, null, null, null);



        if (cursor.getCount() > 0) {
            Ligas obj;
            if (cursor.moveToFirst()) {
                obj = new Ligas();
                obj.setId(cursor.getInt(cursor.getColumnIndex(Ligas_DataModel.getId())));
                obj.setKeyliga(cursor.getString(cursor.getColumnIndex(Ligas_DataModel.getKeyliga())));
                obj.setNomeliga(cursor.getString(cursor.getColumnIndex(Ligas_DataModel.getNomeliga())));
                obj.setAdmin(cursor.getString(cursor.getColumnIndex(Ligas_DataModel.getAdmin())));
                obj.setDatacadliga(cursor.getLong(cursor.getColumnIndex(Ligas_DataModel.getDatacadliga())));
                obj.setTipoliga(cursor.getInt(cursor.getColumnIndex(Ligas_DataModel.getTipoliga())));
                obj.setStatusliga(cursor.getInt(cursor.getColumnIndex(Ligas_DataModel.getStatusliga())));

                return obj;
            }
        }
            cursor.close();

        return null;
    }

    /*   **********************     Jogador      ************************* */

    public ArrayList<Jogador> getAllJogadores() {

        Jogador obj;
        ArrayList<Jogador> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + Jogador_DataModel.getTABELA() + " ORDER BY id";
        cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                obj = new Jogador();
                obj.setId       (cursor.getInt   (cursor.getColumnIndex(Jogador_DataModel  .getId       ())));
                obj.setKeyjog   (cursor.getString(cursor.getColumnIndex(Jogador_DataModel  .getKeyjog   ())));
                obj.setNomejog  (cursor.getString(cursor.getColumnIndex(Jogador_DataModel  .getNomejog  ())));
                obj.setEmailjog (cursor.getString(cursor.getColumnIndex(Jogador_DataModel  .getEmailjog ())));
                obj.setIdliga   (cursor.getString(cursor.getColumnIndex(Jogador_DataModel  .getIdliga   ())));
                lista.add(obj);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    public Jogador jogadorselecionado (String idfirejog) {

        String[] columns = {
                "id",
                "keyjog",
                "nomejog",
                "emailjog",
                "idliga"
        };

        String[] args = new String[]{idfirejog};
        Cursor cursor = db.query(Jogador_DataModel.getTABELA(), columns, "keyjog = ? ", args, null, null, null);


        if (cursor.getCount() > 0) {

            Jogador obj;

            if (cursor.moveToFirst()) {

                    obj = new Jogador();
                    obj.setId       (cursor.getInt   (cursor.getColumnIndex(Jogador_DataModel  .getId       ())));
                    obj.setKeyjog   (cursor.getString(cursor.getColumnIndex(Jogador_DataModel  .getKeyjog   ())));
                    obj.setNomejog  (cursor.getString(cursor.getColumnIndex(Jogador_DataModel  .getNomejog  ())));
                    obj.setEmailjog (cursor.getString(cursor.getColumnIndex(Jogador_DataModel  .getEmailjog ())));
                    obj.setIdliga   (cursor.getString(cursor.getColumnIndex(Jogador_DataModel  .getIdliga   ())));

                    return obj;
            }
        }
        cursor.close();

        return null;

    }

    public ArrayList<Jogador> getAllJogLiga(String idliga) {

        String[] columns = {
                "id",
                "keyjog",
                "nomejog",
                "emailjog",
                "idliga"
        };

        String[] args = new String[]{idliga};
        Cursor cursor = db.query(Jogador_DataModel.getTABELA(), columns, "idliga = ? ", args, null, null, null);

        Jogador obj;
        ArrayList<Jogador> lista = new ArrayList<>();

        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                do {
                    obj = new Jogador();
                    obj.setId       (cursor.getInt   (cursor.getColumnIndex(Jogador_DataModel  .getId       ())));
                    obj.setKeyjog   (cursor.getString(cursor.getColumnIndex(Jogador_DataModel  .getKeyjog   ())));
                    obj.setNomejog  (cursor.getString(cursor.getColumnIndex(Jogador_DataModel  .getNomejog  ())));
                    obj.setEmailjog (cursor.getString(cursor.getColumnIndex(Jogador_DataModel  .getEmailjog ())));
                    obj.setIdliga   (cursor.getString(cursor.getColumnIndex(Jogador_DataModel  .getIdliga   ())));

                    lista.add(obj);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return lista;

    }




    /*   **********************     Campeonatos  fixo    ************************* */

    public ArrayList<JCampeonato> getAllCampeonatosLiga(String idliga) {


        String[] columns = {
                "id",
                "idcampeonato",
                "idliga",
                "numpartcamp",
                "tipoturnocamp",
                "statuscamp",
                "numerocamp",
                "timestamp",
                "participantesCamp"
        };

        String[] args = new String[]{idliga};
        Cursor cursor = db.query(Campeonato_DataModel.getTABELA(), columns, "idliga = ? ", args, null, null, null);

        JCampeonato obj;
        ArrayList<JCampeonato> lista = new ArrayList<>();


        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                do {
                    obj = new JCampeonato();
                    obj.setId              (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getId            ())));
                    obj.setIdcampeonato    (cursor.getString(cursor.getColumnIndex(Campeonato_DataModel.getIdcampeonato  ())));
                    obj.setIdliga          (cursor.getString(cursor.getColumnIndex(Campeonato_DataModel.getIdliga        ())));
                    obj.setNumpartcamp     (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getNumpartcamp   ())));
                    obj.setTipoturnocamp   (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getTipoturnocamp ())));
                    obj.setStatuscamp      (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getStatuscamp    ())));
                    obj.setNumerocamp      (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getNumerocamp    ())));
                    obj.setTimestamp      (cursor.getLong   (cursor.getColumnIndex(Campeonato_DataModel.getTimestamp    ())));
                    obj.setParticipantesCamp          (cursor.getString(cursor.getColumnIndex(Campeonato_DataModel.getParticipantesCamp        ())));
                    lista.add(obj);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return lista;

    }

    public boolean updateCampTemp(String tabela, String liga, String keycamp, ContentValues dados) {
        db.update(tabela, dados, "idliga=? AND idcampeonato=?", new String[]{liga,keycamp});
        return true;
    }

    public JCampeonato getCampeonatoAtivo(String idliga) {

        String[] columns = {
                "id",
                "idcampeonato",
                "idliga",
                "numpartcamp",
                "tipoturnocamp",
                "statuscamp",
                "numerocamp",
                "timestamp",
                "participantesCamp"
        };

        String[] args = new String[]{idliga,"1"};
        Cursor cursor = db.query(Campeonato_DataModel.getTABELA(), columns, "idliga = ? AND statuscamp = ?", args, null, null, null);

        if (cursor.getCount() > 0) {

            JCampeonato obj;

            if (cursor.moveToFirst()) {

                obj = new JCampeonato();
                obj.setId              (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getId            ())));
                obj.setIdcampeonato    (cursor.getString(cursor.getColumnIndex(Campeonato_DataModel.getIdcampeonato  ())));
                obj.setIdliga          (cursor.getString(cursor.getColumnIndex(Campeonato_DataModel.getIdliga        ())));
                obj.setNumpartcamp     (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getNumpartcamp   ())));
                obj.setTipoturnocamp   (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getTipoturnocamp ())));
                obj.setStatuscamp      (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getStatuscamp    ())));
                obj.setNumerocamp      (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getNumerocamp    ())));
                obj.setTimestamp      (cursor.getLong   (cursor.getColumnIndex(Campeonato_DataModel.getTimestamp    ())));
                obj.setParticipantesCamp          (cursor.getString(cursor.getColumnIndex(Campeonato_DataModel.getParticipantesCamp        ())));



                return obj;
            }
            cursor.close();
        }
        return null;

    }

    public boolean deletarCampTemp(String tabela, String liga,String keycamp) {

        boolean sucesso = true;

        sucesso = db.delete(tabela, "idliga=? AND idcampeonato = ?",

                new String[]{liga,keycamp}) > 0;

        return sucesso;
    }





    public ArrayList<JCampeonato> getAllCampeonatosfixoLiga(String idliga) {

        String[] columns = {
                "id",
                "idcampeonato",
                "idliga",
                "numpartcamp",
                "tipoturnocamp",
                "jogoatualcamp",
                "datacamp",
                "horainicamp",
                "horafimcamp",
                "tempocamp",
                "statuscamp",
                "numerocamp"
        };

        String[] args = new String[]{idliga};
        Cursor cursor = db.query(Campeonato_DataModel.getTABELA(), columns, "idliga = ? ", args, null, null, null);

        JCampeonato obj;
        ArrayList<JCampeonato> lista = new ArrayList<>();


        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                do {
                    obj = new JCampeonato();
                    obj.setId              (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getId            ())));
                    obj.setIdcampeonato    (cursor.getString(cursor.getColumnIndex(Campeonato_DataModel.getIdcampeonato  ())));
                    obj.setIdliga          (cursor.getString(cursor.getColumnIndex(Campeonato_DataModel.getIdliga        ())));
                    obj.setNumpartcamp     (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getNumpartcamp   ())));
                    obj.setTipoturnocamp   (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getTipoturnocamp ())));
                    obj.setStatuscamp      (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getStatuscamp    ())));
                    obj.setNumerocamp      (cursor.getInt   (cursor.getColumnIndex(Campeonato_DataModel.getNumerocamp    ())));
                    lista.add(obj);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return lista;

    }





    //*   **********************     Dataset  fixo    **************************//

    public ArrayList<JogosTeste> getDatasetCamp(String idcamp, String idliga) {

        String[] columns = {
                "id",
                "idfire",
                "idjogo",
                "numjog",
                "keycampeonato",
                "keyliga",
                "idjogador1",
                "idjogador2",
                "placar1",
                "placar2",
                "statusjogo",
                "penalti1",
                "penalti2",
                "turno"
        };

        String[] args = new String[]{idcamp, idliga};
        Cursor cursor = db.query(Jogos_Dataset_DataModel.getTABELA(), columns, "keycampeonato = ? AND keyliga = ? ", args, null, null, null);
        JogosTeste obj;
        ArrayList<JogosTeste> lista = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                obj = new JogosTeste();
                obj.setId               (cursor.getInt   (cursor.getColumnIndex(Jogos_Dataset_DataModel.getId            ())));
                obj.setIdfire          (cursor.getString(cursor.getColumnIndex(Jogos_Dataset_DataModel.getIdfire       ())));
                obj.setIdjogo           (cursor.getInt(cursor.getColumnIndex(Jogos_Dataset_DataModel.getIdjogo           ())));
                obj.setNumjog           (cursor.getString(cursor.getColumnIndex(Jogos_Dataset_DataModel.getNumjog        ())));
                obj.setKeycampeonato    (cursor.getString(cursor.getColumnIndex(Jogos_Dataset_DataModel.getKeycampeonato ())));
                obj.setKeyliga          (cursor.getString(cursor.getColumnIndex(Jogos_Dataset_DataModel.getKeyliga       ())));
                obj.setIdjogador1       (cursor.getString(cursor.getColumnIndex(Jogos_Dataset_DataModel.getIdjogador1    ())));
                obj.setIdjogador2       (cursor.getString(cursor.getColumnIndex(Jogos_Dataset_DataModel.getIdjogador2    ())));
                obj.setPlacar1          (cursor.getString(cursor.getColumnIndex(Jogos_Dataset_DataModel.getPlacar1       ())));
                obj.setPlacar2          (cursor.getString(cursor.getColumnIndex(Jogos_Dataset_DataModel.getPlacar2       ())));
                obj.setStatusjogo       (cursor.getInt(cursor.getColumnIndex   (Jogos_Dataset_DataModel.getStatusjogo    ())));
                obj.setPenalti1         (cursor.getString(cursor.getColumnIndex(Jogos_Dataset_DataModel.getPenalti1      ())));
                obj.setPenalti2         (cursor.getString(cursor.getColumnIndex(Jogos_Dataset_DataModel.getPenalti2      ())));
                obj.setTurno            (cursor.getInt(cursor.getColumnIndex(Jogos_Dataset_DataModel.getTurno            ())));

                lista.add(obj);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;

    }

    public boolean update_Dataset(String tabela, String idbanco, String keycamp, ContentValues dados) {
        db.update(tabela, dados, "id=? AND keycampeonato=?", new String[]{idbanco,keycamp});
        return true;
    }



    public boolean deletarDatasetTempLiga(String tabela, String liga) {

        boolean sucesso = true;

        sucesso = db.delete(tabela, "keyliga=?",

                new String[]{liga}) > 0;

        return sucesso;
    }






    /*   **********************     Participantes Camp Temp      ************************* */

//    public ArrayList<Participantes> getAllParticipantesTemp() {
//
//        Participantes obj;
//        ArrayList<Participantes> lista = new ArrayList<>();
//        String sql = "SELECT * FROM " + Participantes_Temp_DataModel.getTABELA() + " ORDER BY id";
//        cursor = db.rawQuery(sql, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                obj = new Participantes();
//                obj.setId             (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getId             ())));
//                obj.setId_jogador     (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getId_jogador     ())));
//                obj.setNome_jogador   (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getNome_jogador   ())));
//                obj.setEmail_jogador  (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getEmail_jogador  ())));
//                obj.setImg_jogador    (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getImg_jogador    ())));
//                obj.setId_firejogador (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getId_firejogador ())));
//                obj.setId_liga        (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getId_liga        ())));
//                obj.setId_campeonato  (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getId_campeonato  ())));
//                obj.setJogos          (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getJogos          ())));
//                obj.setVitorias       (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getVitorias       ())));
//                obj.setDerrotas       (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getDerrotas       ())));
//                obj.setEmpates        (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getEmpates        ())));
//                obj.setClass_jogador  (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getClass_jogador  ())));
//                obj.setGolspro        (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getGolspro        ())));
//                obj.setGolscontra     (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getGolscontra     ())));
//                obj.setPontos         (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getPontos         ())));
//
//                lista.add(obj);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return lista;
//    }

//    public ArrayList<Participantes> getAllParticipantesTempLiga(String idliga) {
//
//        String[] columns = {
//                "id",
//                "id_jogador",
//                "nome_jogador",
//                "email_jogador",
//                "img_jogador",
//                "id_firejogador",
//                "id_liga",
//                "id_campeonato",
//                "jogos",
//                "vitorias",
//                "derrotas",
//                "empates",
//                "class_jogador",
//                "golspro",
//                "golscontra",
//                "pontos"
//        };
//
//        String[] args = new String[]{idliga};
//        Cursor cursor = db.query(Participantes_Temp_DataModel.getTABELA() , columns, "id_liga = ? ", args, null, null, null);
//
//        Participantes obj;
//        ArrayList<Participantes> lista = new ArrayList<>();
//
//
//
//
//        if (cursor.moveToFirst()) {
//            do {
//                obj = new Participantes();
//                obj.setId             (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getId             ())));
//                obj.setId_jogador     (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getId_jogador     ())));
//                obj.setNome_jogador   (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getNome_jogador   ())));
//                obj.setEmail_jogador  (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getEmail_jogador  ())));
//                obj.setImg_jogador    (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getImg_jogador    ())));
//                obj.setId_firejogador (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getId_firejogador ())));
//                obj.setId_liga        (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getId_liga        ())));
//                obj.setId_campeonato  (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getId_campeonato  ())));
//                obj.setJogos          (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getJogos          ())));
//                obj.setVitorias       (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getVitorias       ())));
//                obj.setDerrotas       (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getDerrotas       ())));
//                obj.setEmpates        (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getEmpates        ())));
//                obj.setClass_jogador  (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getClass_jogador  ())));
//                obj.setGolspro        (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getGolspro        ())));
//                obj.setGolscontra     (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getGolscontra     ())));
//                obj.setPontos         (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getPontos         ())));
//
//                lista.add(obj);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return lista;
//    }

    public boolean deletarPart(String tabela, String liga) {

        boolean sucesso = true;

        sucesso = db.delete(tabela, "id_liga=?",

                new String[]{liga}) > 0;

        return sucesso;
    }


    /*   **********************     Participantes Camp   fixo    ************************* */

//    public ArrayList<Participantes> getAllParticipantesCamp(String idcamp) {
//
//        String[] columns = {
//                "id",
//                "id_jogador",
//                "nome_jogador",
//                "email_jogador",
//                "img_jogador",
//                "id_firejogador",
//                "id_liga",
//                "id_campeonato",
//                "jogos",
//                "vitorias",
//                "derrotas",
//                "empates",
//                "class_jogador",
//                "golspro",
//                "golscontra",
//                "pontos"
//        };
//
//        String[] args = new String[]{idcamp};
//        Cursor cursor = db.query(Participantes_DataModel.getTABELA() , columns, "id_campeonato = ? ", args, null, null, null);
//
//        Participantes obj;
//        ArrayList<Participantes> lista = new ArrayList<>();
//
//        if (cursor.moveToFirst()) {
//            do {
//                obj = new Participantes();
//                obj.setId             (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getId             ())));
//                obj.setId_jogador     (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getId_jogador     ())));
//                obj.setNome_jogador   (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getNome_jogador   ())));
//                obj.setEmail_jogador  (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getEmail_jogador  ())));
//                obj.setImg_jogador    (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getImg_jogador    ())));
//                obj.setId_firejogador (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getId_firejogador ())));
//                obj.setId_liga        (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getId_liga        ())));
//                obj.setId_campeonato  (cursor.getString(cursor.getColumnIndex(Participantes_Temp_DataModel.getId_campeonato  ())));
//                obj.setJogos          (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getJogos          ())));
//                obj.setVitorias       (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getVitorias       ())));
//                obj.setDerrotas       (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getDerrotas       ())));
//                obj.setEmpates        (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getEmpates        ())));
//                obj.setClass_jogador  (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getClass_jogador  ())));
//                obj.setGolspro        (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getGolspro        ())));
//                obj.setGolscontra     (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getGolscontra     ())));
//                obj.setPontos         (cursor.getInt   (cursor.getColumnIndex(Participantes_Temp_DataModel.getPontos         ())));
//
//                lista.add(obj);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return lista;
//    }






    /*   **********************     Jogadores_da_Liga      ************************* */


    public ArrayList<Jogadores_da_Liga> getAll_Jogadores() {

        Jogadores_da_Liga obj;
        ArrayList<Jogadores_da_Liga> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + Jogadores_da_Liga_DataModel.getTABELA() + " ORDER BY id";
        cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                obj = new Jogadores_da_Liga();
                obj.setId           (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getId           ())));
                obj.setIdjogador    (cursor.getString(cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getIdjogador    ())));
                obj.setNomejogador  (cursor.getString(cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getNomejogador  ())));
                obj.setEmailjogador (cursor.getString(cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getEmailjogador ())));
                obj.setTipojogador  (cursor.getString(cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getTipojogador  ())));
                obj.setIdliga       (cursor.getString(cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getIdliga       ())));
                obj.setQtdecamp     (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getQtdecamp     ())));
                obj.setJogos        (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getJogos        ())));
                obj.setVitorias     (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getVitorias     ())));
                obj.setDerrotas     (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getDerrotas     ())));
                obj.setEmpates      (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getEmpates      ())));
                obj.setLugar_1      (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getLugar_1      ())));
                obj.setLugar_2      (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getLugar_2      ())));
                obj.setLugar_3      (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getLugar_3      ())));
                obj.setLugar_4      (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getLugar_4      ())));
                obj.setLugar_5      (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getLugar_5      ())));
                obj.setGolspro      (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getGolspro      ())));
                obj.setGolscontra   (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getGolscontra   ())));
                obj.setPermissao    (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getPermissao    ())));
                lista.add(obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    public ArrayList<Jogadores_da_Liga> getAll_Jogadores_da_Liga(String idliga) {

        String[] columns = {
                "id",
                "idjogador",
                "nomejogador",
                "emailjogador",
                "tipojogador",
                "idliga",
                "qtdecamp",
                "jogos",
                "vitorias",
                "derrotas",
                "empates",
                "lugar_1",
                "lugar_2",
                "lugar_3",
                "lugar_4",
                "lugar_5",
                "golspro",
                "golscontra",
                "permissao"
        };

        String[] args = new String[]{idliga};
        Cursor cursor = db.query(Jogadores_da_Liga_DataModel.getTABELA() , columns, "idliga = ? ", args, null, null, null);

        Jogadores_da_Liga obj;
        ArrayList<Jogadores_da_Liga> lista = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                obj = new Jogadores_da_Liga();
                obj.setId           (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getId           ())));
                obj.setIdjogador    (cursor.getString(cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getIdjogador    ())));
                obj.setNomejogador  (cursor.getString(cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getNomejogador  ())));
                obj.setEmailjogador (cursor.getString(cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getEmailjogador ())));
                obj.setTipojogador  (cursor.getString(cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getTipojogador  ())));
                obj.setIdliga       (cursor.getString(cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getIdliga       ())));
                obj.setQtdecamp     (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getQtdecamp     ())));
                obj.setJogos        (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getJogos        ())));
                obj.setVitorias     (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getVitorias     ())));
                obj.setDerrotas     (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getDerrotas     ())));
                obj.setEmpates      (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getEmpates      ())));
                obj.setLugar_1      (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getLugar_1      ())));
                obj.setLugar_2      (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getLugar_2      ())));
                obj.setLugar_3      (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getLugar_3      ())));
                obj.setLugar_4      (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getLugar_4      ())));
                obj.setLugar_5      (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getLugar_5      ())));
                obj.setGolspro      (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getGolspro      ())));
                obj.setGolscontra   (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getGolscontra   ())));
                obj.setPermissao    (cursor.getInt   (cursor.getColumnIndex(Jogadores_da_Liga_DataModel.getPermissao    ())));

                lista.add(obj);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    public boolean alteraJogLigaApel(String tabela, String idjog, ContentValues dados) {
        db.update(tabela, dados, "idjogador=? ", new String[]{idjog});
        return true;
    }

    public boolean atualizaJogador(String tabela, String idjogador, String keyliga, ContentValues dados) {
        db.update(tabela, dados, "idjogador=? AND idliga=?", new String[]{idjogador, keyliga});
        return true;
    }

    public boolean updateCampPart(String tabela, String idjog,String idliga, ContentValues dados) {
        db.update(tabela, dados, "idjogador=? AND idliga=?", new String[]{idjog,idliga});
        return true;
    }


}
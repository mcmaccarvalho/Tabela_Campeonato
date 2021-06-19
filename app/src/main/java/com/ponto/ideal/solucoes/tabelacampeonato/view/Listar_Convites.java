package com.ponto.ideal.solucoes.tabelacampeonato.view;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Convites.Lista_Convites;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;

public class Listar_Convites extends AppCompatActivity {

   private boolean doubleBackToExitPressedOnce=false;
   private FragmentManager fragmentManager;
   private Context context;


    public static int volta=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar__convites);
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
        this.setTitle("Convites");
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.llconv, new Lista_Convites()).commit();
        context = this;
       // isrunning=MainActivity.isrunning;

    }

    @Override
    public void onStop() {
        super.onStop();
       // status = false;
    }

    @Override
    public void onStart() {
        super.onStart();
      //  status = true;
    }

    @Override
    protected void onPostResume() {
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
        super.onPostResume();
    }

    @Override
    public void onBackPressed(){
        switch (volta){
            case 1:
                fragmentManager.beginTransaction().replace(R.id.llconv, new Lista_Convites()).commit();
                break;
            case 0:
                    Intent intent = new Intent(Listar_Convites.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                break;
            case 3:
                util.showmessage(getBaseContext(),"Aguarde o final da sincronização");
                break;
        }

    }
}
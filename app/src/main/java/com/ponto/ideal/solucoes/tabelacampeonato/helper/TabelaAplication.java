package com.ponto.ideal.solucoes.tabelacampeonato.helper;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class TabelaAplication extends Application implements Application.ActivityLifecycleCallbacks{


    private void setOnline(int online){
        String uid = FirebaseAuth.getInstance().getUid();
        if(uid!=null){
            FirebaseFirestore.getInstance().collection("usuarios")
                    .document(uid)
                    .update("online",online);
        }
    }


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {


    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

        setOnline(1);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {


        setOnline(0);
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {


    }
}

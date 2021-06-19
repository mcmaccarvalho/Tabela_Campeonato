package com.ponto.ideal.solucoes.tabelacampeonato.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Usuarios;

import java.util.Map;


public class FCMService extends FirebaseMessagingService {

    Boolean online;
    PendingIntent pendingIntent;
    Context context;
    private Intent ii;
    boolean isrunning=false;


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        context = getBaseContext();
        final Map<String, String> data = remoteMessage.getData();

            if( data == null || data.get("sender") == null){
                return;
            }


               ii = new Intent(context, RecebeNotitifação.class);

             //  ii = new Intent(context, Listar_Convites.class);

            FirebaseFirestore.getInstance().collection("/usuarios")
                    .document(data.get("sender"))
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Usuarios ususend = documentSnapshot.toObject(Usuarios.class);

                            ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                            ii.putExtra("usuarios", ususend);
                            ii.putExtra("notificacao", true);
                            pendingIntent = PendingIntent.getActivity(
                                    context,0,ii,
                                    PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationManager notificationManager = (NotificationManager)
                            getSystemService(Context.NOTIFICATION_SERVICE);

                    String notificationChannelId = "my_channel_id_01";

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        NotificationChannel notificationChannel =
                                new NotificationChannel(notificationChannelId,"My_Notifications",
                                        NotificationManager.IMPORTANCE_DEFAULT);
                        notificationChannel.setDescription("Channel Description");
                        notificationChannel.enableLights(true);
                        notificationChannel.setLightColor(Color.RED);
                        notificationManager.createNotificationChannel(notificationChannel);
                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, notificationChannelId);
                    builder.setAutoCancel(true);
                    builder.setContentTitle(data.get("title"));
                    builder.setContentText(data.get("body"));
                    builder.setSmallIcon(R.drawable.trofeu_bco);
                    builder.setContentIntent(pendingIntent);
                    notificationManager.notify(1, builder.build());
                        }
                    });

    }
}
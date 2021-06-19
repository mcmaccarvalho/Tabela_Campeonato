package com.ponto.ideal.solucoes.tabelacampeonato.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;

import java.io.InputStream;
import java.net.URL;

public class DownloadListImage {
    static int cont = 0;
    static int  tam =0;
    private static String [] nids;
    private static Context CONTEXT;
    public DownloadListImage( String [] ids, Context CONTEXT) {

        this.CONTEXT=CONTEXT;

        nids = ids;
        tam=nids.length;

        passaid();
    }

    private static void passaid() {

        Log.i("listaid", " ListImagcont: " + cont);

        if(cont==tam ) return;
        Log.i("listaid", " ListImagdep: " + cont);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl(util.loadurl(nids[cont]));
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                salvaimagem task = new salvaimagem();
                task.execute(uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public static class salvaimagem extends AsyncTask<String, Void, Bitmap> {

        private String TAG = "DownloadImage";
        private Bitmap downloadImageBitmap(String sUrl) {

            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
                inputStream.close();
            } catch (Exception e) {
                Log.d(TAG, "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0]);
        }

        protected void onPostExecute(Bitmap result) {


            boolean sf = util.saveImage(CONTEXT,result,nids[cont]);
            Log.i("testeimg", ": " + sf);
            cont++;
            passaid();
            return;
        }
    }

}

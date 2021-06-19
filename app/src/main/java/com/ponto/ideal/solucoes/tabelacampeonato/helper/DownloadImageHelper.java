package com.ponto.ideal.solucoes.tabelacampeonato.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.ponto.ideal.solucoes.tabelacampeonato.util.util;

import java.io.InputStream;
import java.net.URL;

public class DownloadImageHelper extends AsyncTask<String, Void, Bitmap> {

    public static Context context;
    public static Bitmap bitmap;
    public static String strsalva;

    public DownloadImageHelper(Context context, String  strsalva) {
        this.context = context;
        this.strsalva = strsalva;
    }




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

        boolean sf = util.saveImage(context,result,strsalva);

        return;
    }
}

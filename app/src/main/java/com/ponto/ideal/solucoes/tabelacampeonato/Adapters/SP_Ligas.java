package com.ponto.ideal.solucoes.tabelacampeonato.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Ligas;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class SP_Ligas extends ArrayAdapter<Ligas> {

    private Context ctx;
    private String salvaid;

    public SP_Ligas(Context context, ArrayList<Ligas> spligas) {
        super(context, 0, spligas);

    }

//    public void atualizarLista(ArrayList<Ligas> novosDados) {
//        spligas.clear();
//        spligas.addAll(novosDados);
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return iniView(position, convertView, parent);


    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return iniView(position, convertView, parent);
    }

    public View iniView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sp_liga, parent, false);
        }


        TextView txtnomeliga = convertView.findViewById(R.id.txtnomeliga);
        ConstraintLayout llliga  = convertView.findViewById(R.id.llliga);
        final ImageView imgliga = convertView.findViewById(R.id.imgliga);
        ImageView imgshare = convertView.findViewById(R.id.imgshare);

        Ligas currentItem = getItem(position);

        if(currentItem!=null) {
            llliga.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.sobe_texto));

            txtnomeliga.setText(currentItem.getNomeliga());
            salvaid = currentItem.getKeyliga();
            Bitmap bitmap = util.loadImageBitmap(getContext(), currentItem.getKeyliga());
            if(bitmap!=null) {
                imgliga.setImageBitmap(bitmap);
                Log.i("origemimg", " adapter sqlite");
            }else {
                final int heigth = 300;
                final int width = 300;
                FirebaseStorage storage = FirebaseStorage.getInstance();
                final StorageReference storageReference = storage.getReferenceFromUrl(util.loadurl(currentItem.getKeyliga()));
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i("origemimg", " adapter banco");
                        Picasso.get().load(uri.toString()).resize(width, heigth).centerCrop().into(imgliga);
                        new DownloadImage().execute(uri.toString());
                    }
                });
            }

            imgshare.setVisibility((currentItem.getTipoliga()==0)?View.INVISIBLE:View.VISIBLE);

        }
        return convertView;

    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

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
            if(result!=null) {

                    util.saveImage(getContext(), result, salvaid);

            }
        }
    }



}

package tfg_manualvaro.androidmotogp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import tfg_manualvaro.androidmotogp.MainActivityDocumentacion;
import tfg_manualvaro.androidmotogp.R;
import tfg_manualvaro.androidmotogp.models.Url;
import tfg_manualvaro.androidmotogp.models.DocumentacionModelo;


/**
 * Created by Abhi on 03 Sep 2017 008.
 */

public class DocumentacionAdapter extends ArrayAdapter<Url> {

    private DocumentacionModelo dataSet;

    public DocumentacionAdapter(DocumentacionModelo dataSet, Context mContext) {
        super(mContext, R.layout.posicion_row, dataSet.getUrls());
        this.dataSet = dataSet;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.documentacion_row, null);
        }
        final Url pos = dataSet.getUrls().get(position);
        if (pos != null) {
            //Text View references
            TextView urlTextView = (TextView) v.findViewById(R.id.documentacionID);



            //Updating the text views
            //cambiamos lo que se muestra de url para no poner el enlace entero.
            //Nos vamos a quedar con la parte desde la última "/" hasta el interrogante

            String url = pos.getUrl();
            String urlAcortada= url.split("\\/")[url.split("\\/").length-1];
            String urlAcortadaSinInterrogante= urlAcortada.split("\\?")[0];
            urlTextView.setText(urlAcortadaSinInterrogante);

            urlTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewIn) {
                    try {
                        TextView tv= (TextView) viewIn.findViewById(R.id.documentacionID);

                        //alter text of textview widget
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pos.getUrl()));
                        MainActivityDocumentacion.startUrl(intent);



                        //assign the textview forecolor
                        tv.setTextColor(Color.RED);
                    } catch (Exception except) {
                        Log.e("Error","OHa ocurrido un error"+except.getMessage());
                    }
                }
            });


        }

        return v;


    }

}
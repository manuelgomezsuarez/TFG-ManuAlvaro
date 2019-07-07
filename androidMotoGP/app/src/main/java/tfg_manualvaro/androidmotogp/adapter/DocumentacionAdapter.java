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
import android.support.v7.app.AppCompatActivity;
import tfg_manualvaro.androidmotogp.MainActivityCampeonatoDisplay;
import tfg_manualvaro.androidmotogp.MainActivityCarrera;
import tfg_manualvaro.androidmotogp.MainActivityCarreraDisplay;
import tfg_manualvaro.androidmotogp.MainActivityDocumentacion;
import tfg_manualvaro.androidmotogp.R;
import tfg_manualvaro.androidmotogp.models.CampeonatoModelo;
import tfg_manualvaro.androidmotogp.models.Url;
import tfg_manualvaro.androidmotogp.models.DocumentacionModelo;
import tfg_manualvaro.androidmotogp.models.PosicionCampeonato;
import tfg_manualvaro.androidmotogp.models.PosicionCarrera;


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
            urlTextView.setText(pos.getUrl());

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
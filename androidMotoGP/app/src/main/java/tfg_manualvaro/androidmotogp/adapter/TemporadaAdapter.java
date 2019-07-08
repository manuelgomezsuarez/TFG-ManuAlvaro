package tfg_manualvaro.androidmotogp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import tfg_manualvaro.androidmotogp.MainActivity;
import tfg_manualvaro.androidmotogp.MainActivityCategoria;
import tfg_manualvaro.androidmotogp.R;
import tfg_manualvaro.androidmotogp.models.EmployeeDetails;
import tfg_manualvaro.androidmotogp.models.Temporada;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;
import static butterknife.internal.Finder.ACTIVITY;


/**
 * Created by Abhi on 03 Sep 2017 008.
 */

public class TemporadaAdapter extends ArrayAdapter<Temporada> {
    private static final String KEY_TEMPORADA = "";

    private List<Temporada> dataSet;

    public TemporadaAdapter(List<Temporada> dataSet, Context mContext) {
        super(mContext, R.layout.temporada_row, dataSet);
        this.dataSet = dataSet;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.temporada_row, null);
        }
        final Temporada temporada = dataSet.get(position);
        if (temporada != null) {
            //Text View references
            TextView temporadaTextView = (TextView) v.findViewById(R.id.temporadaID);

            //Updating the text views
            temporadaTextView.setText(KEY_TEMPORADA + temporada.getTemporada());
            temporadaTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewIn) {
                    try {
                        TextView tv= (TextView) viewIn.findViewById(R.id.temporadaID);

                        //alter text of textview widget


                        MainActivity.changeToCategoriaActivity(temporada);

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
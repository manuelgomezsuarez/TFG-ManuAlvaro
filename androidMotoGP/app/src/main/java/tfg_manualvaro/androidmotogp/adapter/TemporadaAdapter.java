package tfg_manualvaro.androidmotogp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import tfg_manualvaro.androidmotogp.R;
import tfg_manualvaro.androidmotogp.models.EmployeeDetails;
import tfg_manualvaro.androidmotogp.models.Temporada;

import java.util.List;


/**
 * Created by Abhi on 03 Sep 2017 008.
 */

public class TemporadaAdapter extends ArrayAdapter<Temporada> {
    private static final String KEY_TEMPORADA = "Temporada: ";

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
        Temporada temporada = dataSet.get(position);
        if (temporada != null) {
            //Text View references
            TextView temporadaTextView = (TextView) v.findViewById(R.id.temporadaID);

            //Updating the text views
            temporadaTextView.setText(KEY_TEMPORADA + temporada.getTemporada());

        }

        return v;
    }
}
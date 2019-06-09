package tfg_manualvaro.androidmotogp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import tfg_manualvaro.androidmotogp.R;
import tfg_manualvaro.androidmotogp.models.CampeonatoModelo;
import tfg_manualvaro.androidmotogp.models.CarreraModelo;
import tfg_manualvaro.androidmotogp.models.PosicionCampeonato;
import tfg_manualvaro.androidmotogp.models.PosicionCarrera;


/**
 * Created by Abhi on 03 Sep 2017 008.
 */

public class PosicionCarreraAdapter extends ArrayAdapter<PosicionCarrera> {

    private CarreraModelo dataSet;

    public PosicionCarreraAdapter(CarreraModelo dataSet, Context mContext) {
        super(mContext, R.layout.posicion_row, dataSet.getPosiciones());
        this.dataSet = dataSet;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.posicion_carrera_row, null);
        }
        final PosicionCarrera pos = dataSet.getPosiciones().get(position);
        if (pos != null) {
            //Text View references
            TextView clasificacionID = (TextView) v.findViewById(R.id.clasificacionID);
            TextView pilotoID = (TextView) v.findViewById(R.id.pilotoID);
            TextView motoID = (TextView) v.findViewById(R.id.motoID);
            TextView paisID = (TextView) v.findViewById(R.id.paisID);
            TextView puntosID = (TextView) v.findViewById(R.id.puntosID);
            TextView kmhID = (TextView) v.findViewById(R.id.kmhID);
            TextView diferenciaID = (TextView) v.findViewById(R.id.diferenciaID);


            //Updating the text views
            Log.i("print30", clasificacionID.getText().toString());
            clasificacionID.setText(pos.getPosicion().toString());
            if(pos.getPosicion()<=3 && pos.getPosicion()>0){
                clasificacionID.setTextColor(Color.parseColor("#009B2B"));
            }

            pilotoID.setText(pos.getPiloto().toString());
            motoID.setText(pos.getMoto().toString());
            paisID.setText(pos.getPais().toString());
            puntosID.setText(pos.getPuntos().toString());
            kmhID.setText(pos.getKmh().toString());
            diferenciaID.setText(pos.getDiferencia().toString());

        }

        return v;


    }

}
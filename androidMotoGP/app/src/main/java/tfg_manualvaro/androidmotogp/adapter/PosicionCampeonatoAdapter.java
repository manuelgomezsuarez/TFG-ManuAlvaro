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

import java.util.List;

import tfg_manualvaro.androidmotogp.R;
import tfg_manualvaro.androidmotogp.models.CampeonatoModelo;
import tfg_manualvaro.androidmotogp.models.Carrera;
import tfg_manualvaro.androidmotogp.models.PosicionCampeonato;


/**
 * Created by Abhi on 03 Sep 2017 008.
 */

public class PosicionCampeonatoAdapter extends ArrayAdapter<PosicionCampeonato> {

    private CampeonatoModelo dataSet;

    public PosicionCampeonatoAdapter(CampeonatoModelo dataSet, Context mContext) {
        super(mContext, R.layout.posicion_row, dataSet.getPosiciones());
        this.dataSet = dataSet;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.posicion_row, null);
        }
        final PosicionCampeonato pos = dataSet.getPosiciones().get(position);
        if (pos != null) {
            //Text View references
            //TextView posicionTextView = (TextView) v.findViewById(R.id.posicionID);
            TextView clasificacionID = (TextView) v.findViewById(R.id.clasificacionID);
            TextView pilotoID = (TextView) v.findViewById(R.id.pilotoID);
            TextView motoID = (TextView) v.findViewById(R.id.motoID);
            TextView paisID = (TextView) v.findViewById(R.id.paisID);
            TextView puntosID = (TextView) v.findViewById(R.id.puntosID);



            //Updating the text views
            Log.i("print30", clasificacionID.getText().toString());
            clasificacionID.setText(pos.getPosicion().toString());
            if(pos.getPosicion()<=10){
                clasificacionID.setTextColor(Color.parseColor("#009B2B"));
            }

            pilotoID.setText(pos.getPiloto().toString());
            motoID.setText(pos.getMoto().toString());
            paisID.setText(pos.getPais().toString());
            puntosID.setText(pos.getPuntos().toString());
            /*
            posicionTextView.setText(pos.getPosicion() +". "+
                     pos.getPiloto()+"\n"+
                    "Moto: "+ pos.getMoto()+"\n"+
                    "Pais: "+ pos.getPais()+"\n"+
                    "Puntos: "+ pos.getPuntos()
            );
            posicionTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewIn) {
                    try {
                        TextView tv= (TextView) viewIn.findViewById(R.id.posicionID);

                        //alter text of textview widget


                        //MainActivity.changeToCarreraActivity(carrera);

                        //assign the textview forecolor
                        tv.setTextColor(Color.RED);
                    } catch (Exception except) {
                        Log.e("Error","OHa ocurrido un error"+except.getMessage());
                    }
                }
            });
            */

        }

        return v;


    }

}
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
            v = inflater.inflate(R.layout.posicion_row, null);
        }
        final PosicionCarrera pos = dataSet.getPosiciones().get(position);
        if (pos != null) {
            //Text View references
            //TextView posicionTextView = (TextView) v.findViewById(R.id.posicionID);


            //Updating the text views
            /*
            posicionTextView.setText(pos.getPosicion() +". "+

                     pos.getPiloto()+"\n"+
                    "Puntos: "+ pos.getPuntos()+"\n"+
                    "Num: "+ pos.getNumero()+"\n"+
                    "Pais: "+ pos.getPais()+"\n"+
                    "Equipo: "+ pos.getEquipo()+"\n"+
                    "Moto: "+ pos.getMoto()+"\n"+
                    "KM/H: "+ pos.getKmh()+"\n"+
                    "Diferencia: "+ pos.getDiferencia()

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
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
import tfg_manualvaro.androidmotogp.models.Carrera;
import tfg_manualvaro.androidmotogp.models.PosicionCampeonato;


/**
 * Created by Abhi on 03 Sep 2017 008.
 */

public class PosicionCampeonatoAdapter extends ArrayAdapter<PosicionCampeonato> {

    private List<PosicionCampeonato> dataSet;

    public PosicionCampeonatoAdapter(List<PosicionCampeonato> dataSet, Context mContext) {
        super(mContext, R.layout.carrera_row, dataSet);
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
        final PosicionCampeonato pos = dataSet.get(position);
        if (pos != null) {
            //Text View references
            TextView posicionTextView = (TextView) v.findViewById(R.id.posicionID);

            //Updating the text views
            posicionTextView.setText("Posicion: " + pos.getPosicion() +"/n"+
                                        "Piloto: "+ pos.getPiloto()+"/n"+
                    "Piloto: "+ pos.getMoto()+"/n"+
                    "Piloto: "+ pos.getPais()+"/n"+
                    "Piloto: "+ pos.getPuntos()+"/n"
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

        }

        return v;


    }

}
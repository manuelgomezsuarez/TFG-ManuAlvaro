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

import tfg_manualvaro.androidmotogp.MainActivityCarreraDisplay;
import tfg_manualvaro.androidmotogp.R;
import tfg_manualvaro.androidmotogp.models.CarreraModelo;
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
            TextView clasificacionIDTextView = (TextView) v.findViewById(R.id.clasificacionID);
            TextView pilotoIDTextView = (TextView) v.findViewById(R.id.pilotoID);
            TextView motoIDTextView = (TextView) v.findViewById(R.id.motoID);
            TextView paisIDTextView = (TextView) v.findViewById(R.id.paisID);
            TextView puntosIDTextView = (TextView) v.findViewById(R.id.puntosID);
            TextView kmhIDTextView = (TextView) v.findViewById(R.id.kmhID);
            TextView diferenciaIDTextView = (TextView) v.findViewById(R.id.diferenciaID);


            //Updating the text views
            clasificacionIDTextView.setText(pos.getPosicion().toString());
            if(pos.getPosicion()<=3 && pos.getPosicion()>0){
                clasificacionIDTextView.setTextColor(Color.parseColor("#009B2B"));
            }

            pilotoIDTextView.setText(pos.getPiloto().toString());
            motoIDTextView.setText(pos.getMoto().toString());
            paisIDTextView.setText(pos.getPais().toString());
            puntosIDTextView.setText(pos.getPuntos().toString());
            kmhIDTextView.setText(pos.getKmh().toString());
            diferenciaIDTextView.setText(pos.getDiferencia().toString());

            pilotoIDTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewIn) {
                    try {
                        TextView tv= (TextView) viewIn.findViewById(R.id.pilotoID);

                        //alter text of textview widget
                        MainActivityCarreraDisplay.changeToDisplayPiloto(tv.getText().toString());



                        //assign the textview forecolor
                        tv.setTextColor(Color.RED);
                    } catch (Exception except) {
                        Log.e("Error","Ha ocurrido un error"+except.getMessage());
                    }
                }
            });

        }

        return v;

    }

}
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

import tfg_manualvaro.androidmotogp.MainActivityCampeonatoDisplay;
import tfg_manualvaro.androidmotogp.R;
import tfg_manualvaro.androidmotogp.models.CampeonatoModelo;
import tfg_manualvaro.androidmotogp.models.PosicionCampeonato;


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
            TextView clasificacionIDTextView = (TextView) v.findViewById(R.id.clasificacionID);
            TextView pilotoIDTextView = (TextView) v.findViewById(R.id.pilotoID);
            TextView motoIDTextView = (TextView) v.findViewById(R.id.motoID);
            TextView paisIDTextView = (TextView) v.findViewById(R.id.paisID);
            TextView puntosIDTextView = (TextView) v.findViewById(R.id.puntosID);



            //Updating the text views
            clasificacionIDTextView.setText(pos.getPosicion().toString());
            if(pos.getPosicion()<=3 && pos.getPosicion()>0){
                clasificacionIDTextView.setTextColor(Color.parseColor("#009B2B"));
            }

            pilotoIDTextView.setText(pos.getPiloto().toString());
            motoIDTextView.setText(pos.getMoto().toString());
            paisIDTextView.setText(pos.getPais().toString());
            puntosIDTextView.setText(pos.getPuntos().toString());

            pilotoIDTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewIn) {
                    try {
                        TextView tv= (TextView) viewIn.findViewById(R.id.pilotoID);

                        //alter text of textview widget
                        MainActivityCampeonatoDisplay.changeToDisplayPiloto(tv.getText().toString());



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
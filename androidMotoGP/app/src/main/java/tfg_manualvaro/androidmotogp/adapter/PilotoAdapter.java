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

import tfg_manualvaro.androidmotogp.MainActivityPiloto;
import tfg_manualvaro.androidmotogp.R;
import tfg_manualvaro.androidmotogp.models.Piloto;


/**
 * Created by Abhi on 03 Sep 2017 008.
 */

public class PilotoAdapter extends ArrayAdapter<Piloto> {
    private static final String KEY_PILOTO = "Piloto: ";

    private List<Piloto> dataSet;

    public PilotoAdapter(List<Piloto> dataSet, Context mContext) {
        super(mContext, R.layout.piloto_row, dataSet);
        this.dataSet = dataSet;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.piloto_row, null);
        }
        final Piloto piloto = dataSet.get(position);
        if (piloto != null) {
            //Text View references
            TextView pilotoTextView = (TextView) v.findViewById(R.id.pilotoID);

            //Updating the text views
            pilotoTextView.setText(piloto.getNombre());
            //Updating the text views

            pilotoTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewIn) {
                    try {
                        TextView tv= (TextView) viewIn.findViewById(R.id.pilotoID);

                        //alter text of textview widget
                        MainActivityPiloto.changeToMainActivityPilotoDisplay(piloto);


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
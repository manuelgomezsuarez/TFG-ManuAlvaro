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
import tfg_manualvaro.androidmotogp.MainActivityCarrera;
import tfg_manualvaro.androidmotogp.MainActivityCategoria;
import tfg_manualvaro.androidmotogp.R;
import tfg_manualvaro.androidmotogp.models.EmployeeDetails;
import tfg_manualvaro.androidmotogp.models.Carrera;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;
import static butterknife.internal.Finder.ACTIVITY;


/**
 * Created by Abhi on 03 Sep 2017 008.
 */

public class CarreraAdapter extends ArrayAdapter<Carrera> {
    private static final String KEY_CARRERA = "Carrera: ";

    private List<Carrera> dataSet;

    public CarreraAdapter(List<Carrera> dataSet, Context mContext) {
        super(mContext, R.layout.carrera_row, dataSet);
        this.dataSet = dataSet;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.carrera_row, null);
        }
        final Carrera carrera = dataSet.get(position);
        if (carrera != null) {
            //Text View references
            TextView carreraTextView = (TextView) v.findViewById(R.id.carreraID);

            //Updating the text views
            carreraTextView.setText(KEY_CARRERA + carrera.getTitulo());
            carreraTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewIn) {
                    try {
                        TextView tv= (TextView) viewIn.findViewById(R.id.carreraID);

                        //alter text of textview widget


                        MainActivityCarrera.changeToCarreraActivityDisplay(carrera);

                        //assign the textview forecolor
                        tv.setTextColor(Color.RED);
                    } catch (Exception except) {
                        Log.e("Error","OH a ocurrido un error"+except.getMessage());
                    }
                }
            });

        }

        return v;


    }

}
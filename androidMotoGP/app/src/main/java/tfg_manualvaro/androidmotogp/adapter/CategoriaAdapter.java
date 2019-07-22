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

import tfg_manualvaro.androidmotogp.MainActivityCategoria;
import tfg_manualvaro.androidmotogp.R;
import tfg_manualvaro.androidmotogp.models.Categoria;


/**
 * Created by Abhi on 03 Sep 2017 008.
 */

public class CategoriaAdapter extends ArrayAdapter<Categoria> {
    private static final String KEY_CATEGORIA = "";

    private List<Categoria> dataSet;

    public CategoriaAdapter(List<Categoria> dataSet, Context mContext) {
        super(mContext, R.layout.categoria_row, dataSet);
        this.dataSet = dataSet;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.categoria_row, null);
        }
        final Categoria categoria = dataSet.get(position);
        if (categoria != null) {
            //Text View references
            TextView categoriaTextView = (TextView) v.findViewById(R.id.categoriaID);

            //Updating the text views
            categoriaTextView.setText(KEY_CATEGORIA+ categoria.getCategoria());
            //Updating the text views

            categoriaTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewIn) {
                    try {
                        TextView tv= (TextView) viewIn.findViewById(R.id.categoriaID);

                        //alter text of textview widget
                        MainActivityCategoria.changeToSelectorActivity(categoria);


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
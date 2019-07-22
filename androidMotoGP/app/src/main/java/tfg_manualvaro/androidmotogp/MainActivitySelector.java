package tfg_manualvaro.androidmotogp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import tfg_manualvaro.androidmotogp.adapter.CategoriaAdapter;
import tfg_manualvaro.androidmotogp.models.Temporada;

public class MainActivitySelector extends AppCompatActivity{

    private static Context mContext;

    private static Temporada temporadaMainActivityCategoria;
    private static String categoriaMainActivityCategoria;
    private static String tituloUltimaCarrera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_selector);

        mContext = this;
        Intent intentMainActivityCategoria = getIntent();
        temporadaMainActivityCategoria = intentMainActivityCategoria.getParcelableExtra("Temporada");
        categoriaMainActivityCategoria = intentMainActivityCategoria.getStringExtra("CategoriaString");
        tituloUltimaCarrera= intentMainActivityCategoria.getStringExtra("Titulo");
        if(tituloUltimaCarrera!=null){
            Button btn = (Button) findViewById(R.id.carreraButton);
            btn.setText("Carrera");
        }

        Log.d("print7", "estamos en metodo selector");

        Log.d("print8", temporadaMainActivityCategoria.getTemporada().toString());
        Log.d("print9", categoriaMainActivityCategoria);


    }

    public  void BuscarCarreras(View view){
        if(tituloUltimaCarrera!=null){
            Log.d("printUltima", "Caso Ultima Carrera");

            Intent intentMainActivityCarrera = new Intent(mContext, MainActivityCarreraDisplay.class);
            intentMainActivityCarrera.putExtra("Temporada",temporadaMainActivityCategoria);
            intentMainActivityCarrera.putExtra("CategoriaString",categoriaMainActivityCategoria);
            intentMainActivityCarrera.putExtra("tituloString",tituloUltimaCarrera);

            mContext.startActivity(intentMainActivityCarrera);
        }
        else {

            Log.d("print8", "vamos a cambiar a mainActivityCarrera");
            Log.d("print9", temporadaMainActivityCategoria.getTemporada().toString());
            Log.d("print10", categoriaMainActivityCategoria);
            Intent intentMainActivityCarrera = new Intent(mContext, MainActivityCarrera.class);
            intentMainActivityCarrera.putExtra("Temporada", temporadaMainActivityCategoria);
            intentMainActivityCarrera.putExtra("CategoriaString", categoriaMainActivityCategoria);
            mContext.startActivity(intentMainActivityCarrera);
        }
    }

    public  void BuscarCampeonato(View view){
        Log.d("print8", "vamos a cambiar a mainActivityCampeonatoDisplay");
        Log.d("print9", temporadaMainActivityCategoria.getTemporada().toString());
        Log.d("print10", categoriaMainActivityCategoria);
        Intent intentMainActivityCampeonato = new Intent(mContext, MainActivityCampeonatoDisplay.class);
        intentMainActivityCampeonato.putExtra("Temporada",temporadaMainActivityCategoria);
        intentMainActivityCampeonato.putExtra("CategoriaString",categoriaMainActivityCategoria);
        mContext.startActivity(intentMainActivityCampeonato);
    }




}
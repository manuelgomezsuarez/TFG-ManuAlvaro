package tfg_manualvaro.androidmotogp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tfg_manualvaro.androidmotogp.adapter.CategoriaAdapter;
import tfg_manualvaro.androidmotogp.models.Categoria;
import tfg_manualvaro.androidmotogp.models.Temporada;
import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

public class MainActivitySelector extends AppCompatActivity{
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_CATEGORIA = "categoria";
    private static final String KEY_NEXT = "next";

    //private String url = "http://hr8jeljvudseiccl8kzsu4.webrelay.io/campeonato/";
    private String url = "https://motogp-api.herokuapp.com/campeonato/";
    private Map<String,String> urlParams= new HashMap<>();

    private ProgressDialog pDialog;
    private int success;
    private String next;
    private Integer pagination=1;
    private CategoriaAdapter adapter;
    private JSONArray categoriasJSONArray=null;
    private static Context mContext;

    private static Temporada temporadaMainActivityCategoria;
    private static String categoriaMainActivityCategoria;
    private static String tituloUltimaCarrera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_selector);
        //Call the AsyncTask
        mContext = this;
        Intent intentMainActivityCategoria = getIntent();
        temporadaMainActivityCategoria = intentMainActivityCategoria.getParcelableExtra("Temporada");
        categoriaMainActivityCategoria = intentMainActivityCategoria.getStringExtra("CategoriaString");
        tituloUltimaCarrera= intentMainActivityCategoria.getStringExtra("Titulo");
        //Intent intentRecuperado = getIntent();
        Log.i("print7", "estamos en metodo selector");

        Log.i("print8", temporadaMainActivityCategoria.getTemporada().toString());
        Log.i("print9", categoriaMainActivityCategoria);

        //new FetchCategoria().execute();

    }

    public  void BuscarCarreras(View view){
        if(tituloUltimaCarrera!=null){
            Log.i("printUltima", "Caso Ultima Carrera");
            Intent intentMainActivityCarrera = new Intent(mContext, MainActivityCarreraDisplay.class);
            intentMainActivityCarrera.putExtra("Temporada",temporadaMainActivityCategoria);
            intentMainActivityCarrera.putExtra("CategoriaString",categoriaMainActivityCategoria);
            intentMainActivityCarrera.putExtra("tituloString",tituloUltimaCarrera);
            mContext.startActivity(intentMainActivityCarrera);
        }
        else {

            Log.i("print8", "vamos a cambiar a mainActivityCarrera");
            Log.i("print9", temporadaMainActivityCategoria.getTemporada().toString());
            Log.i("print10", categoriaMainActivityCategoria);
            Intent intentMainActivityCarrera = new Intent(mContext, MainActivityCarrera.class);
            intentMainActivityCarrera.putExtra("Temporada", temporadaMainActivityCategoria);
            intentMainActivityCarrera.putExtra("CategoriaString", categoriaMainActivityCategoria);
            mContext.startActivity(intentMainActivityCarrera);
        }
    }

    public  void BuscarCampeonato(View view){
        Log.i("print8", "vamos a cambiar a mainActivityCampeonatoDisplay");
        Log.i("print9", temporadaMainActivityCategoria.getTemporada().toString());
        Log.i("print10", categoriaMainActivityCategoria);
        Intent intentMainActivityCampeonato = new Intent(mContext, MainActivityCampeonatoDisplay.class);
        intentMainActivityCampeonato.putExtra("Temporada",temporadaMainActivityCategoria);
        intentMainActivityCampeonato.putExtra("CategoriaString",categoriaMainActivityCategoria);
        mContext.startActivity(intentMainActivityCampeonato);
    }




}
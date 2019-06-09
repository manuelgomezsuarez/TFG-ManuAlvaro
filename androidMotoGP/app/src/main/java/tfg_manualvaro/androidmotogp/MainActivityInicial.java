package tfg_manualvaro.androidmotogp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import tfg_manualvaro.androidmotogp.adapter.CategoriaAdapter;
import tfg_manualvaro.androidmotogp.models.Temporada;

public class MainActivityInicial extends AppCompatActivity{
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        //Call the AsyncTask
        mContext = this;


        //Intent intentRecuperado = getIntent();
        Log.i("print7", "estamos en metodo inicial");



        //new FetchCategoria().execute();

    }

    public  void FiltroBasico(View view){
        Log.i("print8", "vamos a cambiar a mainActivity");
        Intent intentMainActivity= new Intent(mContext, MainActivity.class);
        mContext.startActivity(intentMainActivity);

    }

    public  void BuscarCampeonato(View view){
        Log.i("print8", "vamos a cambiar a mainActivityCampeonatoDisplay");
        Intent intentMainActivityCampeonato = new Intent(mContext, MainActivityCampeonatoDisplay.class);
        mContext.startActivity(intentMainActivityCampeonato);
    }




}
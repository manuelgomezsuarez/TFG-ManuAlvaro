package tfg_manualvaro.androidmotogp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        final EditText nombrePiloto = (EditText) findViewById(R.id.textoPiloto);
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


    public  void UltimaCarrera(View view){
        Log.i("print8", "vamos a cambiar a UltimaCarrera");
        Intent intentMainActivity= new Intent(mContext, MainActivityCategoria.class);
        intentMainActivity.putExtra("entrada","ultimaCarrera");
        mContext.startActivity(intentMainActivity);

    }

    public  void BuscarCampeonato(View view){
        Log.i("print8", "vamos a cambiar a mainActivityCampeonatoDisplay");
        Intent intentMainActivityCampeonato = new Intent(mContext, MainActivityCampeonatoDisplay.class);
        mContext.startActivity(intentMainActivityCampeonato);
    }

    public  void BuscarDashboard(View view){
        Log.i("print8", "vamos a cambiar a mainActivityDasboardDisplay");
        Intent intentMainActivityInicial = new Intent(mContext, MainActivityDashboardDisplay.class);
        mContext.startActivity(intentMainActivityInicial);
    }

    public  void BuscarPiloto(View view){
        final EditText nombrePiloto = (EditText) findViewById(R.id.textoPiloto);


        String nombre= nombrePiloto.getText().toString();
        Log.i("print30", nombre);
        if (nombre.length()>0){
            Log.i("print8", "vamos a cambiar a mainActivityCampeonatoDisplay");
            Intent intentMainActivityInicial = new Intent(mContext, MainActivityPiloto.class);
            intentMainActivityInicial.putExtra("nombrePiloto",nombre);
            mContext.startActivity(intentMainActivityInicial);
        }
        else{
            nombrePiloto.setVisibility(View.VISIBLE);
        }

    }




}
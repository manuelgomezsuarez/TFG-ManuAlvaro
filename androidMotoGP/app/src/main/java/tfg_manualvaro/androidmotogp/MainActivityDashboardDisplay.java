package tfg_manualvaro.androidmotogp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

public class MainActivityDashboardDisplay extends AppCompatActivity{
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_CARRERA = "titulo";
    private static final String KEY_NEXT = "next";
    private static Context mContext;


    private String url = "https://motogp-api.herokuapp.com/dashboard/";
    private Map<String,String> urlParams= new HashMap<>();

    private ProgressDialog pDialog;
    private int success;
    private String next;
    private Integer pagination=1;
    private JSONArray dashboardJSONArray=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("print13","hemos llegado el MainActivityDashboardDisplay");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard_display);
        //Call the AsyncTask
        mContext = this;
        new FetchDashboard().execute();

    }


    private class FetchDashboard extends AsyncTask<String, String, String> {
        JSONObject response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivityDashboardDisplay.this);
            pDialog.setMessage("Loading Data.. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser jsonParser = new HttpJsonParser();


            urlParams.put("format","json");
            urlParams.put("page",pagination.toString());
            Log.i("print19",urlParams.toString());
            response = jsonParser.makeHttpRequest(url,"GET",urlParams);
            Log.i("print18",response.toString());

            try {
                success = response.getInt(KEY_SUCCESS);
                next=response.getString(KEY_NEXT);
                dashboardJSONArray =  response.getJSONArray(KEY_DATA);
                while(next!="null"){
                    pagination=pagination+1;
                    urlParams.put("page",pagination.toString());
                    Log.i("next",pagination.toString());
                    response = jsonParser.makeHttpRequest(url,"GET",urlParams);
                    next=response.getString(KEY_NEXT);
                    Log.i("next",next);
                    JSONArray posicionesDashboardJSONArrayNext =  response.getJSONArray(KEY_DATA);

                    for (int i = 0; i < posicionesDashboardJSONArrayNext.length(); i++) {

                        JSONObject jsonObject = posicionesDashboardJSONArrayNext.getJSONObject(i);
                        dashboardJSONArray.put(jsonObject);
                    }
                }
                Log.i("next", dashboardJSONArray.toString());
                pagination=1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    TextView puntuacionPiloto1TextView = (TextView) findViewById(R.id.puntuacionPiloto1);
                    TextView puntuacionPiloto2TextView = (TextView) findViewById(R.id.puntuacionPiloto2);
                    TextView puntuacionPiloto3TextView = (TextView) findViewById(R.id.puntuacionPiloto3);
                    TextView puntuacionPiloto4TextView = (TextView) findViewById(R.id.puntuacionPiloto4);
                    TextView puntuacionPiloto5TextView = (TextView) findViewById(R.id.puntuacionPiloto5);
                    TextView[] arrayPuntuacionesPilotoTextView= new TextView[]{puntuacionPiloto1TextView,
                            puntuacionPiloto2TextView,puntuacionPiloto3TextView,puntuacionPiloto4TextView,
                            puntuacionPiloto5TextView};

                    TextView nombrePiloto1TextView = (TextView) findViewById(R.id.nombrePiloto1);
                    TextView nombrePiloto2TextView = (TextView) findViewById(R.id.nombrePiloto2);
                    TextView nombrePiloto3TextView = (TextView) findViewById(R.id.nombrePiloto3);
                    TextView nombrePiloto4TextView = (TextView) findViewById(R.id.nombrePiloto4);
                    TextView nombrePiloto5TextView = (TextView) findViewById(R.id.nombrePiloto5);
                    TextView[] arrayNombresPilotoTextView= new TextView[]{nombrePiloto1TextView,
                            nombrePiloto2TextView,nombrePiloto3TextView,nombrePiloto4TextView,
                            nombrePiloto5TextView};

                    if (success >0) {
                        try {
                            System.out.println(KEY_DATA);
                            JSONObject dashboard= dashboardJSONArray.getJSONObject(0);
                            JSONArray datosHistoricos=dashboard.getJSONArray("datos_historicos");

                            JSONArray top5VictoriasPorCarrera = datosHistoricos.getJSONObject(0).getJSONArray("top5_victorias_carreras");
                            String pilotoNombre;
                            for (int i = 0; i<top5VictoriasPorCarrera.length();i++){
                                JSONObject piloto=top5VictoriasPorCarrera.getJSONObject(i);
                                pilotoNombre=piloto.names().get(0).toString();
                                arrayPuntuacionesPilotoTextView[i].setText(piloto.getString(pilotoNombre));
                                arrayNombresPilotoTextView[i].setText(pilotoNombre);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(MainActivityDashboardDisplay.this,
                                "Some error occurred while loading data",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


}
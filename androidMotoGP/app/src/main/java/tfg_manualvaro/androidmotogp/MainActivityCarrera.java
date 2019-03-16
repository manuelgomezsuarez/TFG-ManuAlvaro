package tfg_manualvaro.androidmotogp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tfg_manualvaro.androidmotogp.adapter.CarreraAdapter;
import tfg_manualvaro.androidmotogp.models.Carrera;
import tfg_manualvaro.androidmotogp.models.Temporada;
import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

public class MainActivityCarrera extends AppCompatActivity{
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_CARRERA = "titulo";
    private static final String KEY_NEXT = "next";
    private static Context mContext;
    private static Temporada temporadaMainActivitySelector;
    private static String categoriaMainActivitySelector;


    private String url = "http://hr8jeljvudseiccl8kzsu4.webrelay.io/carrera/";
    //private String url = "http://10.0.2.2:44541/carrera/";
    private Map<String,String> urlParams= new HashMap<>();

    private ProgressDialog pDialog;
    private int success;
    private String next;
    private Integer pagination=1;
    private CarreraAdapter adapter;
    private JSONArray carrerasJSONArray=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("print13","hemos llegado el MainActivityCarrera");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_carrera);
        //Call the AsyncTask
        mContext = this;
        Intent intentMainActivitySelector = getIntent();
        temporadaMainActivitySelector = intentMainActivitySelector.getParcelableExtra("Temporada");
        categoriaMainActivitySelector = intentMainActivitySelector.getStringExtra("CategoriaString");
        Log.i("print1666",temporadaMainActivitySelector.getTemporada().toString());
        Log.i("print1777",categoriaMainActivitySelector);
        new FetchCarrera().execute();

    }




    private class FetchCarrera extends AsyncTask<String, String, String> {
        JSONObject response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivityCarrera.this);
            pDialog.setMessage("Loading Data.. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser jsonParser = new HttpJsonParser();


            urlParams.put("format","json");
            urlParams.put("temporada",temporadaMainActivitySelector.getTemporada().toString());
            urlParams.put("distinct","titulo");
            urlParams.put("categoria",categoriaMainActivitySelector);
            urlParams.put("page",pagination.toString());
            Log.i("print19",urlParams.toString());
            response = jsonParser.makeHttpRequest(url,"GET",urlParams);
            Log.i("print18",response.toString());

            try {
                success = response.getInt(KEY_SUCCESS);
                next=response.getString(KEY_NEXT);
                carrerasJSONArray =  response.getJSONArray(KEY_DATA);
                while(next!="null"){
                    pagination=pagination+1;
                    urlParams.put("page",pagination.toString());
                    Log.i("next",pagination.toString());
                    response = jsonParser.makeHttpRequest(url,"GET",urlParams);
                    next=response.getString(KEY_NEXT);
                    Log.i("next",next);
                    JSONArray carrerasJSONArrayNext =  response.getJSONArray(KEY_DATA);

                    for (int i = 0; i < carrerasJSONArrayNext.length(); i++) {

                        JSONObject jsonObject = carrerasJSONArrayNext.getJSONObject(i);
                        carrerasJSONArray.put(jsonObject);
                    }
                }
                Log.i("next", carrerasJSONArray.toString());
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

                    ListView listView =(ListView)findViewById(R.id.objectList);
                    if (success >0) {
                        try {
                            System.out.println(KEY_DATA);
                            List<Carrera> carrerasList = new ArrayList<>();
                            //Populate the EmployeeDetails list from response
                            for (int i = 0; i<carrerasJSONArray.length();i++){
                                Carrera carrera = new Carrera();
                                JSONObject carreraJSON = carrerasJSONArray.getJSONObject(i);
                                carrera.setCarrera(carreraJSON.getString(KEY_CARRERA));
                                carrerasList.add(carrera);
                            }
                            //Create an adapter with the EmployeeDetails List and set it to the LstView
                            adapter = new CarreraAdapter(carrerasList,getApplicationContext());
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(MainActivityCarrera.this,
                                "Some error occurred while loading data",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    public static void changeToCarreraActivityDisplay(Carrera carrera){
        Log.i("print8", "vamos a cambiar a mainActivityCarreraDisplay");
        Log.i("print9", temporadaMainActivitySelector.getTemporada().toString());
        //Intent intentMainActivityCarrera = new Intent(mContext, MainActivityCarreraDisplay.class);
        Intent intentMainActivityCarrera = new Intent(mContext, MainActivityCarreraDisplay.class);
        intentMainActivityCarrera.putExtra("Temporada",temporadaMainActivitySelector);
        intentMainActivityCarrera.putExtra("CategoriaString",categoriaMainActivitySelector);
        intentMainActivityCarrera.putExtra("tituloString",carrera.getTitulo());

        mContext.startActivity(intentMainActivityCarrera);
    }

}
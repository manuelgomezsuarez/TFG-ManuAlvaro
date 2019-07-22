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

import tfg_manualvaro.androidmotogp.adapter.TemporadaAdapter;
import tfg_manualvaro.androidmotogp.models.Temporada;
import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_TEMPORADA = "temporada";
    private static final String KEY_NEXT = "next";
    private static Context mContext;

    private String url = "https://motogp-api.herokuapp.com/campeonato/";
    private Map<String,String> urlParams= new HashMap<>();

    private ProgressDialog pDialog;
    private int success;

    private String next;
    private Integer pagination=1;
    private TemporadaAdapter adapter;
    private JSONArray temporadasJSONArray=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        //Call the AsyncTask
        new FetchTemporada().execute();

    }






    private class FetchTemporada extends AsyncTask<String, String, String> {
        JSONObject response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Barra de progreso de carga
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando datos... Por favor, espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser jsonParser = new HttpJsonParser();

            urlParams.put("format","json");
            urlParams.put("distinct","temporada");
            urlParams.put("page",pagination.toString());

            response = jsonParser.makeHttpRequest(url,"GET",urlParams);
            Log.d("print6", response.toString());
            try {
                success = response.getInt(KEY_SUCCESS);
                next=response.getString(KEY_NEXT);
                temporadasJSONArray =  response.getJSONArray(KEY_DATA);
                while(next!="null"){
                    pagination=pagination+1;
                    urlParams.put("page",pagination.toString());
                    Log.d("next",pagination.toString());
                    response = jsonParser.makeHttpRequest(url,"GET",urlParams);
                    next=response.getString(KEY_NEXT);
                    Log.d("next",next);
                    JSONArray temporadasJSONArrayNext =  response.getJSONArray(KEY_DATA);

                    for (int i = 0; i < temporadasJSONArrayNext.length(); i++) {

                        JSONObject jsonObject = temporadasJSONArrayNext.getJSONObject(i);
                        temporadasJSONArray.put(jsonObject);
                    }
                }
                Log.d("next", temporadasJSONArray.toString());
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
                            List<Temporada> temporadasList = new ArrayList<>();
                            //Populate the EmployeeDetails list from response
                            for (int i = 0; i<temporadasJSONArray.length();i++){
                                Temporada temporada = new Temporada();
                                JSONObject temporadaJSON = temporadasJSONArray.getJSONObject(i);
                                temporada.setTemporada(temporadaJSON.getInt(KEY_TEMPORADA));
                                temporadasList.add(temporada);
                            }
                            Collections.reverse(temporadasList);
                            adapter = new TemporadaAdapter(temporadasList,getApplicationContext());
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(MainActivity.this,
                                "Some error occurred while loading data",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    public class FetchCategoria extends AsyncTask<String, String, String> {
        JSONObject response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Barra de progreso de carga
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando datos... Por favor, espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser jsonParser = new HttpJsonParser();
            urlParams.put("format","json");
            urlParams.put("distinct","temporada");
            urlParams.put("page",pagination.toString());

            response = jsonParser.makeHttpRequest(url,"GET",urlParams);
            try {
                success = response.getInt(KEY_SUCCESS);
                next=response.getString(KEY_NEXT);
                temporadasJSONArray =  response.getJSONArray(KEY_DATA);
                while(next!="null"){
                    pagination=pagination+1;
                    urlParams.put("page",pagination.toString());
                    Log.d("next",pagination.toString());
                    response = jsonParser.makeHttpRequest(url,"GET",urlParams);
                    next=response.getString(KEY_NEXT);
                    Log.d("next",next);
                    JSONArray temporadasJSONArrayNext =  response.getJSONArray(KEY_DATA);

                    for (int i = 0; i < temporadasJSONArrayNext.length(); i++) {

                        JSONObject jsonObject = temporadasJSONArrayNext.getJSONObject(i);
                        temporadasJSONArray.put(jsonObject);
                    }
                }
                Log.d("next", temporadasJSONArray.toString());
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
                            List<Temporada> temporadasList = new ArrayList<>();
                            //Populate the EmployeeDetails list from response
                            for (int i = 0; i<temporadasJSONArray.length();i++){
                                Temporada temporada = new Temporada();
                                JSONObject temporadaJSON = temporadasJSONArray.getJSONObject(i);
                                temporada.setTemporada(temporadaJSON.getInt(KEY_TEMPORADA));
                                temporadasList.add(temporada);
                            }
                            //Create an adapter with the EmployeeDetails List and set it to the LstView
                            adapter = new TemporadaAdapter(temporadasList,getApplicationContext());
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(MainActivity.this,
                                "Some error occurred while loading data",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    public static void changeToCategoriaActivity(Temporada temporada){
        Intent intentMainActivity = new Intent(mContext, MainActivityCategoria.class);
        intentMainActivity.putExtra("Temporada",temporada);
        mContext.startActivity(intentMainActivity);
    }

}
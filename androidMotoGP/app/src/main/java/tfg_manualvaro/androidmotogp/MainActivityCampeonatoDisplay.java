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
import tfg_manualvaro.androidmotogp.adapter.PosicionCampeonatoAdapter;
import tfg_manualvaro.androidmotogp.models.Carrera;
import tfg_manualvaro.androidmotogp.models.PosicionCampeonato;
import tfg_manualvaro.androidmotogp.models.Temporada;
import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

public class MainActivityCampeonatoDisplay extends AppCompatActivity{
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_CARRERA = "titulo";
    private static final String KEY_NEXT = "next";
    private static Context mContext;
    private static Temporada temporadaMainActivitySelector;
    private static String categoriaMainActivitySelector;


    private String url = "http://hr8jeljvudseiccl8kzsu4.webrelay.io/campeonato/";
    //private String url = "http://10.0.2.2:44541/carrera/";
    private Map<String,String> urlParams= new HashMap<>();

    private ProgressDialog pDialog;
    private int success;
    private String next;
    private Integer pagination=1;
    private PosicionCampeonatoAdapter adapter;
    private JSONArray posicionesCampeonatoJSONArray=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("print13","hemos llegado el MainActivityCampeonatoDisplay");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_campeonato_display);
        //Call the AsyncTask
        mContext = this;
        Intent intentMainActivitySelector = getIntent();
        temporadaMainActivitySelector = intentMainActivitySelector.getParcelableExtra("Temporada");
        categoriaMainActivitySelector = intentMainActivitySelector.getStringExtra("CategoriaString");
        Log.i("print16",temporadaMainActivitySelector.getTemporada().toString());
        Log.i("print17",categoriaMainActivitySelector);
        new FetchCampeonato().execute();

    }




    private class FetchCampeonato extends AsyncTask<String, String, String> {
        JSONObject response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivityCampeonatoDisplay.this);
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
            urlParams.put("categoria",categoriaMainActivitySelector);
            urlParams.put("page",pagination.toString());
            Log.i("print19",urlParams.toString());
            response = jsonParser.makeHttpRequest(url,"GET",urlParams);
            Log.i("print18",response.toString());

            try {
                success = response.getInt(KEY_SUCCESS);
                next=response.getString(KEY_NEXT);
                posicionesCampeonatoJSONArray =  response.getJSONArray(KEY_DATA);
                while(next!="null"){
                    pagination=pagination+1;
                    urlParams.put("page",pagination.toString());
                    Log.i("next",pagination.toString());
                    response = jsonParser.makeHttpRequest(url,"GET",urlParams);
                    next=response.getString(KEY_NEXT);
                    Log.i("next",next);
                    JSONArray posicionesCampeonatoJSONArrayNext =  response.getJSONArray(KEY_DATA);

                    for (int i = 0; i < posicionesCampeonatoJSONArrayNext.length(); i++) {

                        JSONObject jsonObject = posicionesCampeonatoJSONArrayNext.getJSONObject(i);
                        //carrerasJSONArray.put(jsonObject);
                    }
                }
                Log.i("next", posicionesCampeonatoJSONArray.toString());
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
                            List<PosicionCampeonato> posicionesList = new ArrayList<>();
                            //Populate the EmployeeDetails list from response
                            for (int i = 0; i<posicionesCampeonatoJSONArray.length();i++){
                                PosicionCampeonato pos = new PosicionCampeonato();
                                JSONObject posicionJSON = posicionesCampeonatoJSONArray.getJSONObject(i);
                                pos.setMoto(posicionJSON.getString("moto"));
                                pos.setPais(posicionJSON.getString("pais"));
                                pos.setPiloto(posicionJSON.getString("piloto"));
                                pos.setPosicion(posicionJSON.getInt("posicion"));
                                pos.setPuntos(posicionJSON.getInt("puntos"));
                                posicionesList.add(pos);
                            }
                            //Create an adapter with the EmployeeDetails List and set it to the LstView
                            adapter = new PosicionCampeonatoAdapter(posicionesList,getApplicationContext());
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(MainActivityCampeonatoDisplay.this,
                                "Some error occurred while loading data",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


}
package tfg_manualvaro.androidmotogp;

import android.app.ProgressDialog;
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

import tfg_manualvaro.androidmotogp.adapter.TemporadaAdapter;
import tfg_manualvaro.androidmotogp.models.Temporada;
import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

public class MainActivityCategoria extends AppCompatActivity{
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_TEMPORADA = "temporada";
    private static final String KEY_NEXT = "next";

    private String url = "http://hr8jeljvudseiccl8kzsu4.webrelay.io/campeonato/";
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
        //Call the AsyncTask
        new FetchCategoria().execute();

    }




    private class FetchCategoria extends AsyncTask<String, String, String> {
        JSONObject response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivityCategoria.this);
            pDialog.setMessage("Loading Data.. Please wait...");
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
                    Log.i("next",pagination.toString());
                    response = jsonParser.makeHttpRequest(url,"GET",urlParams);
                    next=response.getString(KEY_NEXT);
                    Log.i("next",next);
                    JSONArray temporadasJSONArrayNext =  response.getJSONArray(KEY_DATA);

                    for (int i = 0; i < temporadasJSONArrayNext.length(); i++) {

                        JSONObject jsonObject = temporadasJSONArrayNext.getJSONObject(i);
                        //temporadasJSONArray.put(jsonObject);
                    }
                }
                Log.i("next", temporadasJSONArray.toString());
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

                    ListView listView =(ListView)findViewById(R.id.temporadaList);
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
                        Toast.makeText(MainActivityCategoria.this,
                                "Some error occurred while loading data",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

}
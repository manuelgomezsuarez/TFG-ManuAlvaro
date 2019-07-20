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

import tfg_manualvaro.androidmotogp.adapter.PilotoAdapter;
import tfg_manualvaro.androidmotogp.models.Piloto;
import tfg_manualvaro.androidmotogp.models.Temporada;
import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

public class MainActivityPiloto extends AppCompatActivity{
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_PILOTO = "piloto";
    private static final String KEY_NEXT = "next";
    private static Context mContext;
    private static String nombrePilotoMainActivityInicial;


    //private String url = "http://hr8jeljvudseiccl8kzsu4.webrelay.io/campeonato/";
    private String url = "https://motogp-api.herokuapp.com/campeonato/";
    private Map<String,String> urlParams= new HashMap<>();

    private ProgressDialog pDialog;
    private int success;
    private String next;
    private Integer pagination=1;
    private PilotoAdapter adapter;
    private JSONArray pilotosJSONArray=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_piloto);
        //Call the AsyncTask
        mContext = this;
        Intent intentMainActivity = getIntent();
        nombrePilotoMainActivityInicial = intentMainActivity.getStringExtra("nombrePiloto");
        new FetchPiloto().execute();

    }




    private class FetchPiloto extends AsyncTask<String, String, String> {
        JSONObject response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivityPiloto.this);
            pDialog.setMessage("Loading Data.. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser jsonParser = new HttpJsonParser();

            Log.d("print10",nombrePilotoMainActivityInicial);
            urlParams.put("format","json");
            urlParams.put("distinct","piloto");
            urlParams.put("piloto__icontains",nombrePilotoMainActivityInicial);
            urlParams.put("page",pagination.toString());

            response = jsonParser.makeHttpRequest(url,"GET",urlParams);
            try {
                success = response.getInt(KEY_SUCCESS);
                next=response.getString(KEY_NEXT);
                pilotosJSONArray =  response.getJSONArray(KEY_DATA);
                while(next!="null"){
                    pagination=pagination+1;
                    urlParams.put("page",pagination.toString());
                    Log.i("next",pagination.toString());
                    response = jsonParser.makeHttpRequest(url,"GET",urlParams);
                    next=response.getString(KEY_NEXT);
                    Log.i("next",next);
                    JSONArray pilotosJSONArrayNext =  response.getJSONArray(KEY_DATA);

                    for (int i = 0; i < pilotosJSONArrayNext.length(); i++) {

                        JSONObject jsonObject = pilotosJSONArrayNext.getJSONObject(i);
                        pilotosJSONArray.put(jsonObject);
                    }
                }
                Log.i("next", pilotosJSONArray.toString());
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
                            List<Piloto> pilotosList = new ArrayList<>();
                            //Populate the EmployeeDetails list from response
                            for (int i = 0; i<pilotosJSONArray.length();i++){
                                Piloto piloto = new Piloto();
                                JSONObject pilotoJSON = pilotosJSONArray.getJSONObject(i);
                                piloto.setNombre(pilotoJSON.getString(KEY_PILOTO));
                                pilotosList.add(piloto);
                            }
                            //Create an adapter with the EmployeeDetails List and set it to the LstView
                            adapter = new PilotoAdapter(pilotosList,getApplicationContext());
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(MainActivityPiloto.this,
                                "Lo sentimos, no se ha encontrado ningun piloto con el nombre introducido",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    public static void changeToMainActivityPilotoDisplay(Piloto piloto){
        Log.i("print8", "vamos a cambiar a mainActivityPilotoDisplay");
        Log.i("print9", nombrePilotoMainActivityInicial);
        //cambiar esta linea a MainActivityPilotoDisplay.class cuando hayas terminado esa clase
        Intent intentMainActivityPiloto = new Intent(mContext, MainActivityPilotoDisplay.class);
        intentMainActivityPiloto.putExtra("nombrePiloto",piloto.getNombre());
        mContext.startActivity(intentMainActivityPiloto);
    }

}
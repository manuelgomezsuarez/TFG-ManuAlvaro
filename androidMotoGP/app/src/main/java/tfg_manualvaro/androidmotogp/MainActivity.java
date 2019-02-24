package tfg_manualvaro.androidmotogp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import tfg_manualvaro.androidmotogp.adapter.EmployeeAdapter;
import tfg_manualvaro.androidmotogp.adapter.TemporadaAdapter;
import tfg_manualvaro.androidmotogp.models.EmployeeDetails;
import tfg_manualvaro.androidmotogp.models.Temporada;
import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_TEMPORADA = "temporada";

    private String url = "http://hr8jeljvudseiccl8kzsu4.webrelay.io/campeonato/?distinct=temporada&format=json";
    private ProgressDialog pDialog;
    private int success;
    private TemporadaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Call the AsyncTask
        new FetchTemporada().execute();

    }

    private class FetchTemporada extends AsyncTask<String, String, String> {
        JSONObject response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Data.. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser jsonParser = new HttpJsonParser();
            response = jsonParser.makeHttpRequest(url,"GET",null);
            try {
                success = response.getInt(KEY_SUCCESS);
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
                    if (success == 1) {
                        try {
                            System.out.println(KEY_DATA);
                            JSONArray temporadasJSONArray =  response.getJSONArray(KEY_DATA);
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

}
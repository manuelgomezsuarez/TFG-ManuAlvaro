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

import tfg_manualvaro.androidmotogp.adapter.CategoriaAdapter;
import tfg_manualvaro.androidmotogp.models.Categoria;
import tfg_manualvaro.androidmotogp.models.Temporada;
import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

public class MainActivityCategoria extends AppCompatActivity{
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_CATEGORIA = "categoria";
    private static final String KEY_NEXT = "next";
    private static Context mContext;
    private static Temporada temporadaMainActivity;
    private static String tituloUltima;
    private static String entrada;


    private String url = "https://motogp-api.herokuapp.com/carrera/";
    private Map<String,String> urlParams= new HashMap<>();
    private String url2 = "https://motogp-api.herokuapp.com/dashboard/";
    private Map<String,String> urlParams2= new HashMap<>();
    private ProgressDialog pDialog;
    private int success;
    private String next;
    private Integer pagination=1;
    private CategoriaAdapter adapter;
    private JSONArray categoriasJSONArray=null;
    private JSONArray ultimaJSONArray=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_categoria);
        //Call the AsyncTask
        mContext = this;
        Intent intentMainActivity = getIntent();
        temporadaMainActivity=intentMainActivity.getParcelableExtra("Temporada");
        tituloUltima=intentMainActivity.getStringExtra("Titulo");
        entrada=intentMainActivity.getStringExtra("entrada");
        new FetchCategoria().execute();

    }




    private class FetchCategoria extends AsyncTask<String, String, String> {
        JSONObject response;
        JSONObject response2;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Barra de progreso de carga
            pDialog = new ProgressDialog(MainActivityCategoria.this);
            pDialog.setMessage("Cargando datos... Por favor, espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser jsonParser = new HttpJsonParser();
            HttpJsonParser jsonParser2 = new HttpJsonParser();
            if(entrada!=null){
                urlParams2.put("format","json");
                urlParams2.put("page",pagination.toString());
                Log.d("print19",urlParams2.toString());
                response2 = jsonParser.makeHttpRequest(url2,"GET",urlParams);

                try {
                    success = response2.getInt(KEY_SUCCESS);
                    ultimaJSONArray =  response2.getJSONArray(KEY_DATA);
                    Log.d("printUltima",ultimaJSONArray.toString());
                    JSONObject dashboard= ultimaJSONArray.getJSONObject(0);
                    JSONArray datosHistoricos=dashboard.getJSONArray("datos_ultima_temporada");

                    String ultimaCarrera = datosHistoricos.getJSONObject(0).getString("ultima_carrera");
                    String tituloCarrera=ultimaCarrera.split(" - ")[1];
                    String temporadaCarrera=ultimaCarrera.split(" - ")[0].split("/")[2];
                    Log.d("printUltima",temporadaCarrera);
                    Temporada t= new Temporada();
                    t.setTemporada(Integer.valueOf(temporadaCarrera));
                    temporadaMainActivity=t;
                    tituloUltima=tituloCarrera;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Log.d("print10",temporadaMainActivity.getTemporada().toString());
            urlParams.put("format","json");
            urlParams.put("temporada",temporadaMainActivity.getTemporada().toString());
            if (tituloUltima!= null) {
                urlParams.put("titulo", tituloUltima);
            }
            urlParams.put("distinct","categoria");
            urlParams.put("page",pagination.toString());

            response = jsonParser.makeHttpRequest(url,"GET",urlParams);
            try {
                success = response.getInt(KEY_SUCCESS);
                next=response.getString(KEY_NEXT);
                categoriasJSONArray =  response.getJSONArray(KEY_DATA);
                while(next!="null"){
                    pagination=pagination+1;
                    urlParams.put("page",pagination.toString());
                    Log.d("next",pagination.toString());
                    response = jsonParser.makeHttpRequest(url,"GET",urlParams);
                    next=response.getString(KEY_NEXT);
                    Log.d("next",next);
                    JSONArray categoriasJSONArrayNext =  response.getJSONArray(KEY_DATA);

                    for (int i = 0; i < categoriasJSONArrayNext.length(); i++) {

                        JSONObject jsonObject = categoriasJSONArrayNext.getJSONObject(i);
                        categoriasJSONArray.put(jsonObject);
                    }
                }
                Log.d("next", categoriasJSONArray.toString());
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
                            List<Categoria> categoriasList = new ArrayList<>();
                            for (int i = 0; i<categoriasJSONArray.length();i++){
                                Categoria categoria = new Categoria();
                                JSONObject categoriaJSON = categoriasJSONArray.getJSONObject(i);
                                categoria.setCategoria(categoriaJSON.getString(KEY_CATEGORIA));
                                categoriasList.add(categoria);
                            }
                            adapter = new CategoriaAdapter(categoriasList,getApplicationContext());
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(MainActivityCategoria.this,
                                "Ha ocurrido un error mientras se cargaban los datos",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    public static void changeToSelectorActivity(Categoria categoria){
        Log.d("print8", "vamos a cambiar a mainActivitySelector");
        Log.d("print9", temporadaMainActivity.getTemporada().toString());
        Temporada temporadaPrueba=new Temporada(temporadaMainActivity.getTemporada());
        Intent intentMainActivityCategoria = new Intent(mContext, MainActivitySelector.class);

        intentMainActivityCategoria.putExtra("Temporada",temporadaPrueba);
        intentMainActivityCategoria.putExtra("CategoriaString",categoria.getCategoria());
        intentMainActivityCategoria.putExtra("Titulo",tituloUltima);
        mContext.startActivity(intentMainActivityCategoria);
    }

}
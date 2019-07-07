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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tfg_manualvaro.androidmotogp.adapter.PosicionCarreraAdapter;
import tfg_manualvaro.androidmotogp.models.CampeonatoModelo;
import tfg_manualvaro.androidmotogp.models.CarreraModelo;
import tfg_manualvaro.androidmotogp.models.PosicionCampeonato;
import tfg_manualvaro.androidmotogp.models.PosicionCarrera;
import tfg_manualvaro.androidmotogp.models.Temporada;
import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

public class MainActivityCarreraDisplay extends AppCompatActivity{
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_NEXT = "next";
    private static Context mContext;
    private static Temporada temporadaMainActivitySelector;
    private static String categoriaMainActivitySelector;
    private static String tituloCarrera;

    //private String url = "http://hr8jeljvudseiccl8kzsu4.webrelay.io/carrera/";
    private String url = "https://motogp-api.herokuapp.com/carrera/";
    private Map<String,String> urlParams= new HashMap<>();

    private ProgressDialog pDialog;
    private int success;
    private String next;
    private Integer pagination=1;
    private PosicionCarreraAdapter adapter;
    private JSONArray posicionesCarreraJSONArray=null;
//Propiedades para Documentación
    private String temporada;
    private String categoria;
    private String titulo;
    private String lugar;
    private String fecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("print13","hemos llegado el MainActivityCarreraDisplay");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_carrera_display);
        //Call the AsyncTask
        mContext = this;
        Intent intentMainActivitySelector = getIntent();
        temporadaMainActivitySelector = intentMainActivitySelector.getParcelableExtra("Temporada");
        categoriaMainActivitySelector = intentMainActivitySelector.getStringExtra("CategoriaString");
        tituloCarrera=intentMainActivitySelector.getStringExtra("tituloString");
        Log.i("print16",temporadaMainActivitySelector.getTemporada().toString());
        Log.i("print17",categoriaMainActivitySelector);



        new FetchCarrera().execute();

    }




    private class FetchCarrera extends AsyncTask<String, String, String> {
        JSONObject response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivityCarreraDisplay.this);
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
            urlParams.put("titulo",tituloCarrera);
            urlParams.put("page",pagination.toString());
            Log.i("print19",urlParams.toString());
            response = jsonParser.makeHttpRequest(url,"GET",urlParams);
            Log.i("print18",response.toString());

            try {
                success = response.getInt(KEY_SUCCESS);
                next=response.getString(KEY_NEXT);
                posicionesCarreraJSONArray =  response.getJSONArray(KEY_DATA);
                while(next!="null"){
                    pagination=pagination+1;
                    urlParams.put("page",pagination.toString());
                    Log.i("next",pagination.toString());
                    response = jsonParser.makeHttpRequest(url,"GET",urlParams);
                    next=response.getString(KEY_NEXT);
                    Log.i("next",next);
                    JSONArray posicionesCarreraJSONArrayNext =  response.getJSONArray(KEY_DATA);

                    for (int i = 0; i < posicionesCarreraJSONArrayNext.length(); i++) {

                        JSONObject jsonObject = posicionesCarreraJSONArrayNext.getJSONObject(i);
                        posicionesCarreraJSONArray.put(jsonObject);
                    }
                }
                Log.i("next", posicionesCarreraJSONArray.toString());
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
                    TextView categoriaTextView = (TextView) findViewById(R.id.textoCategoria);

                    categoriaTextView.setText(categoriaMainActivitySelector);
                    ListView listView =(ListView)findViewById(R.id.objectList);
                    if (success >0) {
                        try {
                            System.out.println(KEY_DATA);
                            CarreraModelo carrera=new CarreraModelo();
                            List<PosicionCarrera> posicionesList = new ArrayList<>();
                            //Populate the EmployeeDetails list from response
                            for (int i = 0; i<posicionesCarreraJSONArray.length();i++){
                                PosicionCarrera pos = new PosicionCarrera();
                                JSONObject posicionJSON = posicionesCarreraJSONArray.getJSONObject(i);
                                pos.setMoto(posicionJSON.getString("moto"));
                                pos.setPais(posicionJSON.getString("pais"));
                                pos.setPiloto(posicionJSON.getString("piloto"));
                                pos.setPosicion(posicionJSON.getInt("pos"));
                                pos.setPuntos(posicionJSON.getInt("puntos"));
                                pos.setDiferencia(posicionJSON.getString("diferencia"));
                                pos.setEquipo(posicionJSON.getString("equipo"));
                                pos.setKmh(posicionJSON.getInt("kmh"));
                                pos.setNumero(posicionJSON.getInt("num"));
                                carrera.setFecha(posicionJSON.getString("fecha"));
                                carrera.setLugar(posicionJSON.getString("lugar"));
                                carrera.setTitulo(posicionJSON.getString("titulo"));
                                carrera.setCategoria(posicionJSON.getString("categoria"));
                                carrera.setTemporada(posicionJSON.getString("temporada"));
                                posicionesList.add(pos);
                            }

                            TextView lugarTextView = (TextView) findViewById(R.id.textoLugar);
                            TextView fechaTextView = (TextView) findViewById(R.id.textoFecha);
                            //TextView tituloTextView = (TextView) findViewById(R.id.textoTitulo);
                            TextView abreviaturaTextView = (TextView) findViewById(R.id.textoAbreviatura);
                            lugarTextView.setText(carrera.getLugar());
                            fechaTextView.setText(carrera.getFecha().split("T")[0]);
                            //tituloTextView.setText(carrera.getTitulo());
                            abreviaturaTextView.setText(carrera.getTitulo());
                            categoriaTextView.setText(categoriaMainActivitySelector);


                            //Seteamos propiedades para documentación.
                            temporada=carrera.getTemporada().toString();
                            fecha=carrera.getFecha();
                            lugar=carrera.getLugar();
                            categoria=carrera.getCategoria();
                            titulo=carrera.getTitulo();


                            carrera.setPosiciones(posicionesList);
                            carrera.setCategoria(categoriaMainActivitySelector);
                            carrera.setTemporada(carrera.getTemporada());
                            //Create an adapter with the EmployeeDetails List and set it to the LstView
                            adapter = new PosicionCarreraAdapter(carrera,getApplicationContext());
                            listView.setAdapter(adapter);
                            MainActivityCarreraDisplay.Utility.setListViewHeightBasedOnChildren(listView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(MainActivityCarreraDisplay.this,
                                "Some error occurred while loading data",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


    public static class Utility {
        public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }


    public static void changeToDisplayPiloto(String piloto){
        Log.i("print13", "vamos a cambiar a mainActivityPilotoDisplay");
        Intent intentMainActivityNombrewPilotoPosicion= new Intent(mContext, MainActivityPilotoDisplay.class);
        intentMainActivityNombrewPilotoPosicion.putExtra("nombrePiloto",piloto);
        mContext.startActivity(intentMainActivityNombrewPilotoPosicion);

    }

    public void changeToDocumentacionActivity(View view){
        Log.i("print13", "vamos a cambiar a mainActivityDocumentacion");
        Intent intentMainActivityDocumentacion= new Intent(mContext, MainActivityDocumentacion.class);
        intentMainActivityDocumentacion.putExtra("temporada",temporada);
        intentMainActivityDocumentacion.putExtra("categoria",categoria);
        intentMainActivityDocumentacion.putExtra("titulo",titulo);
        intentMainActivityDocumentacion.putExtra("lugar",lugar);
        intentMainActivityDocumentacion.putExtra("fecha",fecha);


        mContext.startActivity(intentMainActivityDocumentacion);
    }

}
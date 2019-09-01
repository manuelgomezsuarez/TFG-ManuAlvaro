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
import android.widget.LinearLayout;
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

import tfg_manualvaro.androidmotogp.adapter.PosicionCampeonatoAdapter;
import tfg_manualvaro.androidmotogp.models.CampeonatoModelo;
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


    private String url = "https://motogp-api.herokuapp.com/campeonato/";
    private Map<String,String> urlParams= new HashMap<>();

    private ProgressDialog pDialog;
    private int success;
    private String next;
    private Integer pagination=1;
    private PosicionCampeonatoAdapter adapter;
    private JSONArray posicionesCampeonatoJSONArray=null;
    private LinearLayout LayoutDeCarga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("print13","hemos llegado el MainActivityCampeonatoDisplay");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_campeonato_display);
        //Call the AsyncTask
        mContext = this;
        Intent intentMainActivitySelector = getIntent();
        temporadaMainActivitySelector = intentMainActivitySelector.getParcelableExtra("Temporada");
        categoriaMainActivitySelector = intentMainActivitySelector.getStringExtra("CategoriaString");
        Log.d("print16",temporadaMainActivitySelector.getTemporada().toString());
        Log.d("print17",categoriaMainActivitySelector);
        LayoutDeCarga=(LinearLayout) findViewById(R.id.LayoutDeCarga);
        LayoutDeCarga.setVisibility(View.INVISIBLE);
        new FetchCampeonato().execute();

    }




    private class FetchCampeonato extends AsyncTask<String, String, String> {
        JSONObject response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Barra de progreso de carga
            pDialog = new ProgressDialog(MainActivityCampeonatoDisplay.this);
            pDialog.setMessage("Cargando datos... Por favor, espere...");
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
            Log.d("print19",urlParams.toString());
            response = jsonParser.makeHttpRequest(url,"GET",urlParams);
            Log.d("print18",response.toString());

            try {
                success = response.getInt(KEY_SUCCESS);
                next=response.getString(KEY_NEXT);
                posicionesCampeonatoJSONArray =  response.getJSONArray(KEY_DATA);
                while(next!="null"){
                    pagination=pagination+1;
                    urlParams.put("page",pagination.toString());
                    Log.d("next",pagination.toString());
                    response = jsonParser.makeHttpRequest(url,"GET",urlParams);
                    next=response.getString(KEY_NEXT);
                    Log.d("next",next);
                    JSONArray posicionesCampeonatoJSONArrayNext =  response.getJSONArray(KEY_DATA);

                    for (int i = 0; i < posicionesCampeonatoJSONArrayNext.length(); i++) {

                        JSONObject jsonObject = posicionesCampeonatoJSONArrayNext.getJSONObject(i);
                        posicionesCampeonatoJSONArray.put(jsonObject);
                    }
                }
                Log.d("next", posicionesCampeonatoJSONArray.toString());
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
                    TextView temporadaTextView = (TextView) findViewById(R.id.textoTemporada);
                    TextView categoriaTextView = (TextView) findViewById(R.id.textoCategoria);
                    temporadaTextView.setText(temporadaMainActivitySelector.getTemporada().toString());
                    categoriaTextView.setText(categoriaMainActivitySelector);
                    ListView listView =(ListView)findViewById(R.id.objectList);
                    if (success >0) {
                        try {
                            System.out.println(KEY_DATA);
                            CampeonatoModelo campeonato=new CampeonatoModelo();
                            List<PosicionCampeonato> posicionesList = new ArrayList<>();
                            for (int i = 0; i<posicionesCampeonatoJSONArray.length();i++){
                                PosicionCampeonato pos = new PosicionCampeonato();
                                JSONObject posicionJSON = posicionesCampeonatoJSONArray.getJSONObject(i);
                                pos.setMoto(posicionJSON.getString("moto"));
                                pos.setPais(posicionJSON.getString("pais"));
                                pos.setPiloto(posicionJSON.getString("piloto"));
                                pos.setPosicion(posicionJSON.getInt("pos"));
                                pos.setPuntos(posicionJSON.getInt("puntos"));
                                posicionesList.add(pos);
                            }

                            campeonato.setPosiciones(posicionesList);
                            campeonato.setCategoria(categoriaMainActivitySelector);
                            campeonato.setTemporada(temporadaMainActivitySelector.getTemporada());
                            adapter = new PosicionCampeonatoAdapter(campeonato,getApplicationContext());
                            LayoutDeCarga.setVisibility(View.VISIBLE);
                            listView.setAdapter(adapter);
                            Utility.setListViewHeightBasedOnChildren(listView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(MainActivityCampeonatoDisplay.this,
                                "No se han podido encontrar datos para este campeonato",
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
        Log.d("print13", "vamos a cambiar a mainActivityPilotoDisplay");
        Intent intentMainActivityNombrewPilotoPosicion= new Intent(mContext, MainActivityPilotoDisplay.class);
        intentMainActivityNombrewPilotoPosicion.putExtra("nombrePiloto",piloto);
        mContext.startActivity(intentMainActivityNombrewPilotoPosicion);

    }

    public  void GoHome(View view){
        Log.d("print8", "go home");
        Intent intentMainActivityInicial = new Intent(mContext, MainActivityInicial.class);
        mContext.startActivity(intentMainActivityInicial);
    }


}
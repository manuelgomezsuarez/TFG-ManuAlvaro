package tfg_manualvaro.androidmotogp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tfg_manualvaro.androidmotogp.models.Carrera;
import tfg_manualvaro.androidmotogp.models.Piloto;
import tfg_manualvaro.androidmotogp.models.Temporada;
import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

public class MainActivityDashboardUltimaTemporadaDisplay extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_NEXT = "next";
    private static Context mContext;
    private static String nombrePilotoMainActivityInicial;
    private String filtro;

    private String url = "https://motogp-api.herokuapp.com/dashboard/";
    private Map<String,String> urlParams= new HashMap<>();

    private ProgressDialog pDialog;
    private int success;
    private String next;
    private Integer pagination=1;
    private JSONArray dashboardUltimaTemporadaJSONArray=null;
    private HashMap<String,String[]> filtros = new HashMap<String, String[]>();

    private BarChart chart;

    private PieChart piechartVictoriasPorCategoria;
    private PieChart piechartVictoriasPorMoto;
    private PieChart piechartVictoriasPorPodios;
    private BarData dataBarchar;
    private JSONArray top3Victorias;
    private JSONArray top3VictoriasMarca;
    private JSONArray nacionalidadPilotos;

    private Spinner spin;

    private TextView nombrePiloto1TextView;
    private TextView nombrePiloto2TextView;
    private TextView nombrePiloto3TextView;

    private TextView victoriasPiloto1TextView;
    private TextView victoriasPiloto2TextView;
    private TextView victoriasPiloto3TextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("print13","hemos llegado el MainActivityDashboardUltimaTemporada");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard_ultima_temporada_display);
        //Call the AsyncTask
        mContext = this;


        nombrePiloto1TextView = (TextView) findViewById(R.id.nombrePiloto1);
        nombrePiloto2TextView = (TextView) findViewById(R.id.nombrePiloto2);
        nombrePiloto3TextView = (TextView) findViewById(R.id.nombrePiloto3);

        victoriasPiloto1TextView = (TextView) findViewById(R.id.victoriasPiloto1);
        victoriasPiloto2TextView = (TextView) findViewById(R.id.victoriasPiloto2);
        victoriasPiloto3TextView = (TextView) findViewById(R.id.victoriasPiloto3);


        chart = findViewById(R.id.barchart);
        chart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
        chart.getAxisLeft().setTextSize(15);
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setTextSize(15);
        chart.getLegend().setTextColor(Color.WHITE);
        chart.getLegend().setTextSize(12);
        chart.animateY(1500);
        chart.getAxisRight().setDrawLabels(false);
        chart.setDescriptionColor(Color.GREEN);
        chart.setDescriptionTextSize(15);

        chart.getLegend().setTextSize(15);
        spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);





        new FetchPiloto().execute();
    }


    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        ((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) arg0.getChildAt(0)).setTextSize(16);
        filtro = arg0.getSelectedItem().toString();
        Log.d("print18",arg0.getSelectedItem().toString());
        if (top3Victorias!=null) {
            try {

                chart.setDescription("");
                BarDataSet dataSet;
                List<BarEntry> entries = new ArrayList<>();

                ArrayList<String> labels = new ArrayList<String>();
                Log.d("print20","JSON recuperado");
                for (int i = 0; i < top3Victorias.length(); i++) {

                    JSONObject jsonObject;
                    jsonObject = top3Victorias.getJSONObject(i);
                    Iterator<String> keys = jsonObject.keys();


                    while( keys.hasNext() ) {
                        String key = keys.next();
                        Log.d("print55",key);
                        if(filtro.equals(key)){
                            JSONArray datos = jsonObject.getJSONArray(key);
                            nombrePiloto1TextView.setText(datos.getJSONObject(0).getString("piloto"));
                            victoriasPiloto1TextView.setText(datos.getJSONObject(0).getString("victorias"));
                            nombrePiloto2TextView.setText(datos.getJSONObject(1).getString("piloto"));
                            victoriasPiloto2TextView.setText(datos.getJSONObject(1).getString("victorias"));
                            nombrePiloto3TextView.setText(datos.getJSONObject(2).getString("piloto"));
                            victoriasPiloto3TextView.setText(datos.getJSONObject(2).getString("victorias"));


                            Log.d("print57",datos.toString());
                        }
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            arg0.setSelection(1);
            filtro = arg0.getSelectedItem().toString();
        }
        Log.d("print18",arg0.getSelectedItem().toString());


    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }






    private class FetchPiloto extends AsyncTask<String, String, String> {
        JSONObject response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Barra de progreso de carga
            pDialog = new ProgressDialog(MainActivityDashboardUltimaTemporadaDisplay.this);
            pDialog.setMessage("Cargando datos... Por favor, espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser jsonParser = new HttpJsonParser();
            urlParams.put("format","json");
            urlParams.put("page",pagination.toString());
            Log.d("print19",urlParams.toString());
            response = jsonParser.makeHttpRequest(url,"GET",urlParams);
            Log.d("print18",response.toString());

            try {
                success = response.getInt(KEY_SUCCESS);
                next=response.getString(KEY_NEXT);
                dashboardUltimaTemporadaJSONArray =  response.getJSONArray(KEY_DATA);
                while(next!="null"){
                    pagination=pagination+1;
                    urlParams.put("page",pagination.toString());
                    Log.d("next",pagination.toString());
                    response = jsonParser.makeHttpRequest(url,"GET",urlParams);
                    next=response.getString(KEY_NEXT);
                    Log.d("next",next);
                    JSONArray dashboardUltimaTemporadaJSONArrayNext =  response.getJSONArray(KEY_DATA);

                    for (int i = 0; i < dashboardUltimaTemporadaJSONArrayNext.length(); i++) {

                        JSONObject jsonObject = dashboardUltimaTemporadaJSONArrayNext.getJSONObject(i);
                        dashboardUltimaTemporadaJSONArray.put(jsonObject);
                    }
                }
                Log.d("next", dashboardUltimaTemporadaJSONArray.toString());
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

                            final JSONObject dashboardUltimaTemporadaJSON = dashboardUltimaTemporadaJSONArray.getJSONObject(0);

                            Log.d("print40",dashboardUltimaTemporadaJSON.toString());
                            JSONArray datosUltimaTemporada=dashboardUltimaTemporadaJSON.getJSONArray("datos_ultima_temporada");
                            Log.d("print43",datosUltimaTemporada.toString());
                            String ultimaCarrera = datosUltimaTemporada.getJSONObject(0).getString("ultima_carrera");
                            String numCarrerasDisputadas = datosUltimaTemporada.getJSONObject(1).getString("num_carreras_disputadas");

                            String fechaCarrera=ultimaCarrera.split(" - ")[0];
                            final Temporada temporada = new Temporada();
                            temporada.setTemporada(Integer.parseInt(fechaCarrera.split("/")[2]));
                            final String tituloCarrera=ultimaCarrera.split(" - ")[1];
                            Log.d("print41",ultimaCarrera);
                            Log.d("print42",numCarrerasDisputadas);
                            TextView textoDashboardUltimaTemporadaTextView = (TextView) findViewById(R.id.textoDashboardUltimaTemporada);
                            textoDashboardUltimaTemporadaTextView.setText("Temporada "+ temporada.getTemporada().toString());

                            TextView textoCarrerasDisputadasTextView = (TextView) findViewById(R.id.textoCarrerasDisputadas);
                            textoCarrerasDisputadasTextView.setText(numCarrerasDisputadas+" carreras disputadas");

                            final TextView textNombreCarreraTextView = (TextView) findViewById(R.id.textoNombreCarrera);
                            textNombreCarreraTextView.setText(tituloCarrera);

                            textNombreCarreraTextView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View viewIn) {
                                    try {
                                        Carrera carrera = new Carrera();
                                        carrera.setCarrera(tituloCarrera);

                                        Intent intentMainActivityCarrera = new Intent(mContext, MainActivityCarreraDisplay.class);
                                        intentMainActivityCarrera.putExtra("Temporada",temporada);
                                        intentMainActivityCarrera.putExtra("CategoriaString","MotoE");
                                        intentMainActivityCarrera.putExtra("tituloString",carrera.getTitulo());
                                        mContext.startActivity(intentMainActivityCarrera);
                                        //assign the textview forecolor
                                        textNombreCarreraTextView.setTextColor(Color.RED);
                                    } catch (Exception except) {
                                        Log.e("Error","OHa ocurrido un error"+except.getMessage());
                                    }
                                }
                            });

                            TextView textoFechaCarreraTextView = (TextView) findViewById(R.id.textoFechaCarrera);
                            textoFechaCarreraTextView.setText(fechaCarrera);

                            top3Victorias = datosUltimaTemporada.getJSONObject(2).getJSONArray("top3_victorias");
                            top3VictoriasMarca = datosUltimaTemporada.getJSONObject(3).getJSONArray("top3_victorias_marca");
                            nacionalidadPilotos = datosUltimaTemporada.getJSONObject(4).getJSONArray("nacionalidad_pilotos");

                            for (int i = 0; i < top3Victorias.length(); i++) {

                                JSONObject jsonObject = top3Victorias.getJSONObject(i);
                                Iterator<String> keys = jsonObject.keys();

                                while( keys.hasNext() ) {
                                    String key = keys.next();
                                    filtros.put(key, new String[]{key, "Categoria"});

                                    Log.d("print53",top3Victorias.toString());
                                }


                            }
                            Log.d("print48",top3Victorias.toString());
                            Log.d("print49",top3VictoriasMarca.toString());
                            Log.d("print50",nacionalidadPilotos.toString());


                            //nombre spinner,nombreJSON, descripcion grafica
                            //Creating the ArrayAdapter instance having the country list
                            ArrayAdapter aa = new ArrayAdapter(mContext,android.R.layout.simple_spinner_item,filtros.keySet().toArray());
                            int spinnerPosition = aa.getPosition(1);
                            spin.setSelection(spinnerPosition);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            spin.setAdapter(aa);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(MainActivityDashboardUltimaTemporadaDisplay.this,
                                "Some error occurred while loading data",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    public  void GoHome(View view){
        Log.d("print8", "go home");
        Intent intentMainActivityInicial = new Intent(mContext, MainActivityInicial.class);
        mContext.startActivity(intentMainActivityInicial);
    }



}
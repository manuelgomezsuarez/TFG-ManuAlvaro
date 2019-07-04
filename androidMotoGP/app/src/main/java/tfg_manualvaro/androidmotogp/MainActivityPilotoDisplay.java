package tfg_manualvaro.androidmotogp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import tfg_manualvaro.androidmotogp.adapter.PosicionCarreraAdapter;
import tfg_manualvaro.androidmotogp.models.Piloto;
import tfg_manualvaro.androidmotogp.models.PosicionCarrera;
import tfg_manualvaro.androidmotogp.models.Temporada;
import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

public class MainActivityPilotoDisplay extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_NEXT = "next";
    private static Context mContext;
    private static String nombrePilotoMainActivityInicial;
    private String filtro;
    private JSONArray temporadasPilotoJSONArray;

    //private String url = "http://hr8jeljvudseiccl8kzsu4.webrelay.io/piloto/";
    private String url = "https://motogp-api.herokuapp.com/piloto/info/";
    private Map<String,String> urlParams= new HashMap<>();

    private ProgressDialog pDialog;
    private int success;
    private String next;
    private Integer pagination=1;
    private JSONArray posicionesPilotoJSONArray=null;
    private String[] country = { "Número de Victorias", "Número de Podios", "Categoría", "Velocidad Media", "Moto","Posicion"};
    private HashMap<String,String[]> filtros = new HashMap<String, String[]>();

    private BarChart chart;

    private PieChart piechart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("print13","hemos llegado el MainActivityPilotoDisplay");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_piloto_display);
        //Call the AsyncTask
        mContext = this;
        Intent intentMainActivitySelector = getIntent();
        nombrePilotoMainActivityInicial = intentMainActivitySelector.getStringExtra("nombrePiloto");
        Log.i("print17",nombrePilotoMainActivityInicial);

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



        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //nombre spinner,nombreJSON, descripcion grafica
        filtros.put("Posicion en el campeonato", new String[]{"posicion_campeonato", "Puesto que ha quedado en el campeonato"});
        filtros.put("Número de Victorias", new String[]{"num_victorias", "Veces que ha llegado al podio"});
        filtros.put("Velocidad Media", new String[]{"vel_media", "Velocidad media que ha conseguido en las carreras"});
        filtros.put("Moto", new String[]{"moto", "Moto que ha utilizado"});
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,filtros.keySet().toArray());
        int spinnerPosition = aa.getPosition(1);
        spin.setSelection(spinnerPosition);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);



        new FetchPiloto().execute();
    }





    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        ((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) arg0.getChildAt(0)).setTextSize(16);
        filtro = arg0.getSelectedItem().toString();

        if (temporadasPilotoJSONArray!=null) {
            try {

                chart.setDescription(filtros.get(filtro)[1]);
                BarDataSet dataSet;
                List<BarEntry> entries = new ArrayList<>();

                ArrayList<String> labels = new ArrayList<String>();
                Log.i("print20","JSON recuperado");
                for (int i = 0; i < temporadasPilotoJSONArray.length(); i++) {

                    JSONObject temporadaPiloto = null;
                    temporadaPiloto = temporadasPilotoJSONArray.getJSONObject(i);
                    String temporada = temporadaPiloto.keys().next();
                    labels.add(temporada);
                    JSONObject datosTemporadaPiloto = temporadaPiloto.getJSONObject(temporada);

                    //primer valor es altura de la y, segundo valor es el indice de la tabla

                    if(filtro.equals("Número de Victorias")){
                        Integer numeroVictorias=datosTemporadaPiloto.getInt("num_victorias");
                        Integer numeroPodios=datosTemporadaPiloto.getInt("num_podios")-numeroVictorias;
                        entries.add(new BarEntry(new float[]{numeroPodios,numeroVictorias},i));
                        dataSet = new BarDataSet(entries, filtro);
                        int[] colores= new int[]{Color.RED,Color.GREEN};
                        dataSet.setColors(colores);
                        dataSet.setLabel("");
                        dataSet.setStackLabels(new String[]{"Posiciones 2ª o 3ª","Número Victorias"});

                    }else{
                        entries.add(new BarEntry(datosTemporadaPiloto.getInt(filtros.get(filtro)[0]), i));
                        dataSet = new BarDataSet(entries, filtro);
                        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);

                    }

                    BarData data = new BarData(labels, dataSet);
                    chart.setData(data);
                    chart.invalidate(); // refresh
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            arg0.setSelection(1);
            filtro = arg0.getSelectedItem().toString();
        }
        Log.i("print18",arg0.getSelectedItem().toString());


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
            //Display progress bar
            pDialog = new ProgressDialog(MainActivityPilotoDisplay.this);
            pDialog.setMessage("Loading Data.. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser jsonParser = new HttpJsonParser();
            urlParams.put("format","json");
            urlParams.put("piloto",nombrePilotoMainActivityInicial);
            urlParams.put("page",pagination.toString());
            Log.i("print19",urlParams.toString());
            response = jsonParser.makeHttpRequest(url,"GET",urlParams);
            Log.i("print18",response.toString());

            try {
                success = response.getInt(KEY_SUCCESS);
                next=response.getString(KEY_NEXT);
                posicionesPilotoJSONArray =  response.getJSONArray(KEY_DATA);
                while(next!="null"){
                    pagination=pagination+1;
                    urlParams.put("page",pagination.toString());
                    Log.i("next",pagination.toString());
                    response = jsonParser.makeHttpRequest(url,"GET",urlParams);
                    next=response.getString(KEY_NEXT);
                    Log.i("next",next);
                    JSONArray posicionesPilotoJSONArrayNext =  response.getJSONArray(KEY_DATA);

                    for (int i = 0; i < posicionesPilotoJSONArrayNext.length(); i++) {

                        JSONObject jsonObject = posicionesPilotoJSONArrayNext.getJSONObject(i);
                        posicionesPilotoJSONArray.put(jsonObject);
                    }
                }
                Log.i("next", posicionesPilotoJSONArray.toString());
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
                            Piloto piloto=new Piloto();
                            final JSONObject pilotoJSON = posicionesPilotoJSONArray.getJSONObject(0);
                            TextView nombreTextView = (TextView) findViewById(R.id.textoNombre);
                            nombreTextView.setText(pilotoJSON.getString("nombre"));

                            TextView numCapeonatosGanadosTextView = (TextView) findViewById(R.id.textoNumCampeonatosGanados);
                            numCapeonatosGanadosTextView.setText(pilotoJSON.getString("num_campeonatos_ganados"));

                            TextView nacionalidadTextView = (TextView) findViewById(R.id.textoNacionalidad);
                            nacionalidadTextView.setText(pilotoJSON.getString("pais"));

                            String imageUri = pilotoJSON.getString("foto_piloto");
                            ImageView imagenPiloto=(ImageView)findViewById(R.id.imagenPiloto);
                            Picasso.with(mContext).load(imageUri).resize(300, 300).into(imagenPiloto);

                            ImageView entry = (ImageView) findViewById(R.id.infoPiloto);




                            Log.i("print18","metodo pilotofech");
                            temporadasPilotoJSONArray= pilotoJSON.getJSONArray("datos_anuales");
                            if (temporadasPilotoJSONArray!=null) {
                                chart.setDescription(filtros.get(filtro)[1]);
                                List<BarEntry> entries = new ArrayList<>();
                                BarDataSet dataSetBarchart = new BarDataSet(entries, filtro);
                                ArrayList<String> labels = new ArrayList<String>();
                                Log.i("print20", "JSON recuperado");

                                String categoria;
                                Integer victorias;
                                final HashMap<String,Integer>victoriasPorCategoria=new HashMap<>();
                                for (int i = 0; i < temporadasPilotoJSONArray.length(); i++) {

                                    JSONObject temporadaPiloto = null;
                                    temporadaPiloto = temporadasPilotoJSONArray.getJSONObject(i);
                                    String temporada = temporadaPiloto.keys().next();
                                    JSONObject datosTemporadaPiloto = temporadaPiloto.getJSONObject(temporada);

                                    //barchart******************************************
                                    //primer valor es altura de la y, segundo valor es el indice de la tabla
                                    entries.add(new BarEntry(datosTemporadaPiloto.getInt(filtros.get(filtro)[0]), i));
                                    labels.add(temporada);
                                    //*************************************************barchar


                                    //piechar*************************************************************
                                    categoria=datosTemporadaPiloto.getString("categoria");
                                    victorias=datosTemporadaPiloto.getInt("num_victorias");
                                    //si el map no contiene la categoria la añadimos con las victorias
                                    if(!victoriasPorCategoria.containsKey(categoria)){
                                        victoriasPorCategoria.put(categoria,victorias);
                                    }
                                    //si ya la contiene le sumamos los puntos
                                    else{
                                        victoriasPorCategoria.put(categoria,victoriasPorCategoria.get(categoria) +victorias);
                                    }

                                    //************************************************************piechart





                                }
                                dataSetBarchart.setColors(ColorTemplate.LIBERTY_COLORS);
                                BarData dataBarchar = new BarData(labels, dataSetBarchart);
                                chart.setData(dataBarchar);
                                chart.invalidate(); // refresh


                                //piechart****************


                                piechart = (PieChart) findViewById(R.id.piechart);

                                //   mChart.setUsePercentValues(true);
                                piechart.setDescription("Historico Victorias");
                                piechart.setDescriptionColor(Color.GREEN);
                                piechart.setDescriptionTextSize(15);

                                piechart.setRotationEnabled(true);

                                int[] yValues = {21, 2, 2};
                                final String[] xValues = {"Present Days", "Absents", "Leaves"};

                                // colors for different sections in pieChart
                                int[] MY_COLORS = {Color.RED,Color.GREEN,Color.BLUE};


                                piechart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

                                    @Override
                                    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                                        // display msg when value selected
                                        if (e == null)
                                            return;

                                        Toast.makeText(MainActivityPilotoDisplay.this,
                                                 e.getVal()+ " victorias en esa categoría", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected() {

                                    }
                                });

                                ArrayList<Entry> entriesPiechart = new ArrayList<Entry>();
                                ArrayList<String> labelsPiechart = new ArrayList<String>();

                                Integer contadorVictoriasPorCategoriaLoop=0;
                                for (Map.Entry<String, Integer> relacionCategoriaVictorias : victoriasPorCategoria.entrySet()) {
                                    entriesPiechart.add(new Entry(relacionCategoriaVictorias.getValue(), contadorVictoriasPorCategoriaLoop));
                                    labelsPiechart.add(relacionCategoriaVictorias.getKey());
                                    contadorVictoriasPorCategoriaLoop++;
                                }

                                // create pieDataSet
                                PieDataSet dataSetPiechart = new PieDataSet(entriesPiechart, "");
                                dataSetPiechart.setSliceSpace(1);
                                dataSetPiechart.setSelectionShift(10);

                                // adding colors
                                ArrayList<Integer> coloresPiechart = new ArrayList<Integer>();
                                // Added My Own colors
                                for (int c : MY_COLORS)
                                    coloresPiechart.add(c);
                                dataSetPiechart.setColors(ColorTemplate.PASTEL_COLORS);

                                //  create pie data object and set xValues and yValues and set it to the pieChart
                                PieData dataPiechart = new PieData(labelsPiechart, dataSetPiechart);
                                //   data.setValueFormatter(new DefaultValueFormatter());
                                //   data.setValueFormatter(new PercentFormatter());

                                dataPiechart.setValueTextSize(12);
                                dataPiechart.setValueTextColor(Color.WHITE);

                                // Legends to show on bottom of the graph
                                Legend l = piechart.getLegend();
                                l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
                                l.setXEntrySpace(7);
                                l.setYEntrySpace(5);
                                l.setTextColor(Color.WHITE);
                                l.setTextSize(12);

                                piechart.setData(dataPiechart);
                                // undo all highlights
                                piechart.highlightValues(null);
                                // refresh/update pie chart
                                piechart.invalidate();
                                // animate piechart
                                piechart.animateXY(1500, 3000);

                                //*************************piechart




                            }

                            entry.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Uri uri = null;
                                    try {
                                        uri = Uri.parse(pilotoJSON.getString("wiki_piloto"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(MainActivityPilotoDisplay.this,
                                "Some error occurred while loading data",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }



}
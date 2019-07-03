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
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;

import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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

public class MainActivityPilotoDisplay extends AppCompatActivity{
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_NEXT = "next";
    private static Context mContext;
    private static String nombrePilotoMainActivityInicial;



    //private String url = "http://hr8jeljvudseiccl8kzsu4.webrelay.io/piloto/";
    private String url = "https://motogp-api.herokuapp.com/piloto/";
    private Map<String,String> urlParams= new HashMap<>();

    private ProgressDialog pDialog;
    private int success;
    private String next;
    private Integer pagination=1;
    private JSONArray posicionesPilotoJSONArray=null;



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
        new FetchPiloto().execute();

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
                            numCapeonatosGanadosTextView.setText(pilotoJSON.getString("numCampeonatosGanados"));

                            TextView nacionalidadTextView = (TextView) findViewById(R.id.textoNacionalidad);
                            nacionalidadTextView.setText(pilotoJSON.getString("pais"));

                            String imageUri = pilotoJSON.getString("fotoPiloto");
                            ImageView imagenPiloto=(ImageView)findViewById(R.id.imagenPiloto);
                            Picasso.with(mContext).load(imageUri).resize(300, 300).into(imagenPiloto);

                            ImageView entry = (ImageView) findViewById(R.id.infoPiloto);

                            BarChart chart = findViewById(R.id.barchart);



                            chart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
                            chart.getAxisLeft().setTextSize(15);
                            chart.getXAxis().setTextColor(Color.WHITE);
                            chart.getXAxis().setTextSize(15);
                            chart.getLegend().setTextColor(Color.WHITE);
                            chart.getLegend().setTextSize(12);
                            chart.animateY(1500);
                            chart.getAxisRight().setDrawLabels(false);
                            chart.setDescriptionColor(Color.WHITE);
                            chart.setDescriptionTextSize(15);
                            chart.setDescription("grafico de ejemplo");

                            List<BarEntry> entries = new ArrayList<>();

                            BarDataSet dataSet = new BarDataSet(entries, "Numero de victorias");
                            ArrayList<String> labels = new ArrayList<String>();

                            JSONArray temporadasPilotoJSONArray= pilotoJSON.getJSONArray("datosAnuales");

                            for (int i = 0; i < temporadasPilotoJSONArray.length(); i++) {

                                JSONObject temporadaPiloto = temporadasPilotoJSONArray.getJSONObject(i);
                                String temporada=temporadaPiloto.keys().next();
                                JSONObject datosTemporadaPiloto=temporadaPiloto.getJSONObject(temporada);
                                //primer valor es altura de la y, segundo valor es el indice de la tabla
                                entries.add(new BarEntry(datosTemporadaPiloto.getInt("num_victorias"),i));
                                labels.add(temporada);
                            }

                            dataSet.setColors(ColorTemplate.LIBERTY_COLORS);

                            BarData data = new BarData(labels,dataSet);
                            chart.setData(data);
                            chart.invalidate(); // refresh





                            entry.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Uri uri = null;
                                    try {
                                        uri = Uri.parse(pilotoJSON.getString("infoPiloto"));
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
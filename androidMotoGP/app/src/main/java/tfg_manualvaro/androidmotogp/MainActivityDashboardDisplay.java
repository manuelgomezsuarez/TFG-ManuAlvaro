package tfg_manualvaro.androidmotogp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

public class MainActivityDashboardDisplay extends AppCompatActivity{
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_CARRERA = "titulo";
    private static final String KEY_NEXT = "next";
    private static Context mContext;


    private String url = "https://motogp-api.herokuapp.com/dashboard/";
    private Map<String,String> urlParams= new HashMap<>();

    private ProgressDialog pDialog;
    private int success;
    private String next;
    private Integer pagination=1;


    private JSONArray dashboardJSONArray=null;

    //Carreras****************************
    private String nombrePilotosBusquedaWiki;
    //clave nombrePilotoWikipedia
    //valores {puntos,nombrePilotoMotoGP, foto}
    private HashMap<String,ArrayList<String>> datosPilotos= new HashMap<String, ArrayList<String>>();
    private TextView puntuacionPiloto1TextView;
    private TextView puntuacionPiloto2TextView;
    private TextView puntuacionPiloto3TextView;
    private TextView puntuacionPiloto4TextView;
    private TextView puntuacionPiloto5TextView;
    private TextView nombrePiloto1TextView;
    private TextView nombrePiloto2TextView;
    private TextView nombrePiloto3TextView;
    private TextView nombrePiloto4TextView;
    private TextView nombrePiloto5TextView;
    private ImageView fotoPiloto1ImageView;
    private ImageView fotoPiloto2ImageView;
    private ImageView fotoPiloto3ImageView;
    private ImageView fotoPiloto4ImageView;
    private ImageView fotoPiloto5ImageView;
    private ImageView[] arrayFotosPilotoImageView;
    private TextView[] arrayPuntuacionesPilotoTextView;
    private TextView[] arrayNombresPilotoTextView;
    private LinearLayout pilotosMasVictoriasCarrerasLinearLayout;
    //****************************Carreras


    //Campeonatos*****************************
    private String nombrePilotosBusquedaWikiCampeonatos;
    //clave nombrePilotoWikipedia
    //valores {puntos,nombrePilotoMotoGP, foto}
    private HashMap<String,ArrayList<String>> datosPilotosCampeonatos= new HashMap<String, ArrayList<String>>();
    private TextView puntuacionPiloto1CampeonatosTextView;
    private TextView puntuacionPiloto2CampeonatosTextView;
    private TextView puntuacionPiloto3CampeonatosTextView;
    private TextView puntuacionPiloto4CampeonatosTextView;
    private TextView puntuacionPiloto5CampeonatosTextView;
    private TextView nombrePiloto1CampeonatosTextView;
    private TextView nombrePiloto2CampeonatosTextView;
    private TextView nombrePiloto3CampeonatosTextView;
    private TextView nombrePiloto4CampeonatosTextView;
    private TextView nombrePiloto5CampeonatosTextView;
    private ImageView fotoPiloto1CampeonatosImageView;
    private ImageView fotoPiloto2CampeonatosImageView;
    private ImageView fotoPiloto3CampeonatosImageView;
    private ImageView fotoPiloto4CampeonatosImageView;
    private ImageView fotoPiloto5CampeonatosImageView;
    private ImageView[] arrayFotosPilotoCampeonatosImageView;
    private TextView[] arrayPuntuacionesPilotoCampeonatosTextView;
    private TextView[] arrayNombresPilotoCampeonatosTextView;
    private LinearLayout pilotosMasVictoriasCampeonatosLinearLayout;
    //*****************************Campeonatos

    //PuntosGlobales*****************************
    private String nombrePilotosBusquedaWikiPuntosGlobales;
    //clave nombrePilotoWikipedia
    //valores {puntos,nombrePilotoMotoGP, foto}
    private HashMap<String,ArrayList<String>> datosPilotosPuntosGlobales= new HashMap<String, ArrayList<String>>();
    private TextView puntuacionPiloto1PuntosGlobalesTextView;
    private TextView puntuacionPiloto2PuntosGlobalesTextView;
    private TextView puntuacionPiloto3PuntosGlobalesTextView;
    private TextView puntuacionPiloto4PuntosGlobalesTextView;
    private TextView puntuacionPiloto5PuntosGlobalesTextView;
    private TextView nombrePiloto1PuntosGlobalesTextView;
    private TextView nombrePiloto2PuntosGlobalesTextView;
    private TextView nombrePiloto3PuntosGlobalesTextView;
    private TextView nombrePiloto4PuntosGlobalesTextView;
    private TextView nombrePiloto5PuntosGlobalesTextView;
    private ImageView fotoPiloto1PuntosGlobalesImageView;
    private ImageView fotoPiloto2PuntosGlobalesImageView;
    private ImageView fotoPiloto3PuntosGlobalesImageView;
    private ImageView fotoPiloto4PuntosGlobalesImageView;
    private ImageView fotoPiloto5PuntosGlobalesImageView;
    private ImageView[] arrayFotosPilotoPuntosGlobalesImageView;
    private TextView[] arrayPuntuacionesPilotoPuntosGlobalesTextView;
    private TextView[] arrayNombresPilotoPuntosGlobalesTextView;
    private LinearLayout pilotosMasVictoriasPuntosGlobalesLinearLayout;
    //*****************************PuntosGlobales


    //PuntosCampeonatosDisputados*****************************
    private String nombrePilotosBusquedaWikiPuntosCampeonatosDisputados;
    //clave nombrePilotoWikipedia
    //valores {puntos,nombrePilotoMotoGP, foto}
    private HashMap<String,ArrayList<String>> datosPilotosPuntosCampeonatosDisputados= new HashMap<String, ArrayList<String>>();
    private TextView puntuacionPiloto1PuntosCampeonatosDisputadosTextView;
    private TextView puntuacionPiloto2PuntosCampeonatosDisputadosTextView;
    private TextView puntuacionPiloto3PuntosCampeonatosDisputadosTextView;
    private TextView puntuacionPiloto4PuntosCampeonatosDisputadosTextView;
    private TextView puntuacionPiloto5PuntosCampeonatosDisputadosTextView;
    private TextView nombrePiloto1PuntosCampeonatosDisputadosTextView;
    private TextView nombrePiloto2PuntosCampeonatosDisputadosTextView;
    private TextView nombrePiloto3PuntosCampeonatosDisputadosTextView;
    private TextView nombrePiloto4PuntosCampeonatosDisputadosTextView;
    private TextView nombrePiloto5PuntosCampeonatosDisputadosTextView;
    private ImageView fotoPiloto1PuntosCampeonatosDisputadosImageView;
    private ImageView fotoPiloto2PuntosCampeonatosDisputadosImageView;
    private ImageView fotoPiloto3PuntosCampeonatosDisputadosImageView;
    private ImageView fotoPiloto4PuntosCampeonatosDisputadosImageView;
    private ImageView fotoPiloto5PuntosCampeonatosDisputadosImageView;
    private ImageView[] arrayFotosPilotoPuntosCampeonatosDisputadosImageView;
    private TextView[] arrayPuntuacionesPilotoPuntosCampeonatosDisputadosTextView;
    private TextView[] arrayNombresPilotoPuntosCampeonatosDisputadosTextView;
    private LinearLayout pilotosMasVictoriasPuntosCampeonatosDisputadosLinearLayout;
    //*****************************PuntosCampeonatosDisputados


    private RadarChart radarchartVictoriasPorMoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("print13","hemos llegado el MainActivityDashboardDisplay");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard_display);
        //Call the AsyncTask
        mContext = this;

        //top5_victorias_carreras**************************************************
        puntuacionPiloto1TextView = (TextView) findViewById(R.id.puntuacionPiloto1);
        puntuacionPiloto2TextView = (TextView) findViewById(R.id.puntuacionPiloto2);
        puntuacionPiloto3TextView = (TextView) findViewById(R.id.puntuacionPiloto3);
        puntuacionPiloto4TextView = (TextView) findViewById(R.id.puntuacionPiloto4);
        puntuacionPiloto5TextView = (TextView) findViewById(R.id.puntuacionPiloto5);

        nombrePiloto1TextView = (TextView) findViewById(R.id.nombrePiloto1);
        nombrePiloto2TextView = (TextView) findViewById(R.id.nombrePiloto2);
        nombrePiloto3TextView = (TextView) findViewById(R.id.nombrePiloto3);
        nombrePiloto4TextView = (TextView) findViewById(R.id.nombrePiloto4);
        nombrePiloto5TextView = (TextView) findViewById(R.id.nombrePiloto5);

        fotoPiloto1ImageView = (ImageView) findViewById(R.id.fotoPiloto1);
        fotoPiloto2ImageView = (ImageView) findViewById(R.id.fotoPiloto2);
        fotoPiloto3ImageView = (ImageView) findViewById(R.id.fotoPiloto3);
        fotoPiloto4ImageView = (ImageView) findViewById(R.id.fotoPiloto4);
        fotoPiloto5ImageView = (ImageView) findViewById(R.id.fotoPiloto5);


        arrayPuntuacionesPilotoTextView= new TextView[]{puntuacionPiloto1TextView,
                puntuacionPiloto2TextView,puntuacionPiloto3TextView,puntuacionPiloto4TextView,
                puntuacionPiloto5TextView};

        arrayNombresPilotoTextView= new TextView[]{nombrePiloto1TextView,
                nombrePiloto2TextView,nombrePiloto3TextView,nombrePiloto4TextView,
                nombrePiloto5TextView};

        arrayFotosPilotoImageView= new ImageView[]{fotoPiloto1ImageView,
                fotoPiloto2ImageView,fotoPiloto3ImageView,fotoPiloto4ImageView,
                fotoPiloto5ImageView};

        pilotosMasVictoriasCarrerasLinearLayout=(LinearLayout) findViewById(R.id.layerPilotosMasVictoriasCarreras);
        pilotosMasVictoriasCarrerasLinearLayout.setVisibility(View.INVISIBLE);


        //**************************************************top5_victorias_carreras

        //top5_victorias_campeonatos***********************************************

        puntuacionPiloto1CampeonatosTextView = (TextView) findViewById(R.id.puntuacionPiloto1Campeonatos);
        puntuacionPiloto2CampeonatosTextView = (TextView) findViewById(R.id.puntuacionPiloto2Campeonatos);
        puntuacionPiloto3CampeonatosTextView = (TextView) findViewById(R.id.puntuacionPiloto3Campeonatos);
        puntuacionPiloto4CampeonatosTextView = (TextView) findViewById(R.id.puntuacionPiloto4Campeonatos);
        puntuacionPiloto5CampeonatosTextView = (TextView) findViewById(R.id.puntuacionPiloto5Campeonatos);

        nombrePiloto1CampeonatosTextView = (TextView) findViewById(R.id.nombrePiloto1Campeonatos);
        nombrePiloto2CampeonatosTextView = (TextView) findViewById(R.id.nombrePiloto2Campeonatos);
        nombrePiloto3CampeonatosTextView = (TextView) findViewById(R.id.nombrePiloto3Campeonatos);
        nombrePiloto4CampeonatosTextView = (TextView) findViewById(R.id.nombrePiloto4Campeonatos);
        nombrePiloto5CampeonatosTextView = (TextView) findViewById(R.id.nombrePiloto5Campeonatos);

        fotoPiloto1CampeonatosImageView = (ImageView) findViewById(R.id.fotoPiloto1Campeonatos);
        fotoPiloto2CampeonatosImageView = (ImageView) findViewById(R.id.fotoPiloto2Campeonatos);
        fotoPiloto3CampeonatosImageView = (ImageView) findViewById(R.id.fotoPiloto3Campeonatos);
        fotoPiloto4CampeonatosImageView = (ImageView) findViewById(R.id.fotoPiloto4Campeonatos);
        fotoPiloto5CampeonatosImageView = (ImageView) findViewById(R.id.fotoPiloto5Campeonatos);


        arrayPuntuacionesPilotoCampeonatosTextView= new TextView[]{puntuacionPiloto1CampeonatosTextView,
                puntuacionPiloto2CampeonatosTextView,puntuacionPiloto3CampeonatosTextView,puntuacionPiloto4CampeonatosTextView,
                puntuacionPiloto5CampeonatosTextView};

        arrayNombresPilotoCampeonatosTextView= new TextView[]{nombrePiloto1CampeonatosTextView,
                nombrePiloto2CampeonatosTextView,nombrePiloto3CampeonatosTextView,nombrePiloto4CampeonatosTextView,
                nombrePiloto5CampeonatosTextView};

        arrayFotosPilotoCampeonatosImageView= new ImageView[]{fotoPiloto1CampeonatosImageView,
                fotoPiloto2CampeonatosImageView,fotoPiloto3CampeonatosImageView,fotoPiloto4CampeonatosImageView,
                fotoPiloto5CampeonatosImageView};

        pilotosMasVictoriasCampeonatosLinearLayout=(LinearLayout) findViewById(R.id.layerPilotosMasVictoriasCampeonatos);
        pilotosMasVictoriasCampeonatosLinearLayout.setVisibility(View.INVISIBLE);


        //***********************************************top5_victorias_campeonatos


        //top5_puntos_global***********************************************

        puntuacionPiloto1PuntosGlobalesTextView = (TextView) findViewById(R.id.puntuacionPiloto1PuntosGlobales);
        puntuacionPiloto2PuntosGlobalesTextView = (TextView) findViewById(R.id.puntuacionPiloto2PuntosGlobales);
        puntuacionPiloto3PuntosGlobalesTextView = (TextView) findViewById(R.id.puntuacionPiloto3PuntosGlobales);
        puntuacionPiloto4PuntosGlobalesTextView = (TextView) findViewById(R.id.puntuacionPiloto4PuntosGlobales);
        puntuacionPiloto5PuntosGlobalesTextView = (TextView) findViewById(R.id.puntuacionPiloto5PuntosGlobales);

        nombrePiloto1PuntosGlobalesTextView = (TextView) findViewById(R.id.nombrePiloto1PuntosGlobales);
        nombrePiloto2PuntosGlobalesTextView = (TextView) findViewById(R.id.nombrePiloto2PuntosGlobales);
        nombrePiloto3PuntosGlobalesTextView = (TextView) findViewById(R.id.nombrePiloto3PuntosGlobales);
        nombrePiloto4PuntosGlobalesTextView = (TextView) findViewById(R.id.nombrePiloto4PuntosGlobales);
        nombrePiloto5PuntosGlobalesTextView = (TextView) findViewById(R.id.nombrePiloto5PuntosGlobales);

        fotoPiloto1PuntosGlobalesImageView = (ImageView) findViewById(R.id.fotoPiloto1PuntosGlobales);
        fotoPiloto2PuntosGlobalesImageView = (ImageView) findViewById(R.id.fotoPiloto2PuntosGlobales);
        fotoPiloto3PuntosGlobalesImageView = (ImageView) findViewById(R.id.fotoPiloto3PuntosGlobales);
        fotoPiloto4PuntosGlobalesImageView = (ImageView) findViewById(R.id.fotoPiloto4PuntosGlobales);
        fotoPiloto5PuntosGlobalesImageView = (ImageView) findViewById(R.id.fotoPiloto5PuntosGlobales);


        arrayPuntuacionesPilotoPuntosGlobalesTextView= new TextView[]{puntuacionPiloto1PuntosGlobalesTextView,
                puntuacionPiloto2PuntosGlobalesTextView,puntuacionPiloto3PuntosGlobalesTextView,puntuacionPiloto4PuntosGlobalesTextView,
                puntuacionPiloto5PuntosGlobalesTextView};

        arrayNombresPilotoPuntosGlobalesTextView= new TextView[]{nombrePiloto1PuntosGlobalesTextView,
                nombrePiloto2PuntosGlobalesTextView,nombrePiloto3PuntosGlobalesTextView,nombrePiloto4PuntosGlobalesTextView,
                nombrePiloto5PuntosGlobalesTextView};

        arrayFotosPilotoPuntosGlobalesImageView= new ImageView[]{fotoPiloto1PuntosGlobalesImageView,
                fotoPiloto2PuntosGlobalesImageView,fotoPiloto3PuntosGlobalesImageView,fotoPiloto4PuntosGlobalesImageView,
                fotoPiloto5PuntosGlobalesImageView};

        pilotosMasVictoriasPuntosGlobalesLinearLayout=(LinearLayout) findViewById(R.id.layerPilotosMasVictoriasPuntosGlobales);
        pilotosMasVictoriasPuntosGlobalesLinearLayout.setVisibility(View.INVISIBLE);


        //***********************************************top5_puntos_global

        //top5_campeonatos_disputados***********************************************

        puntuacionPiloto1PuntosCampeonatosDisputadosTextView = (TextView) findViewById(R.id.puntuacionPiloto1PuntosCampeonatosDisputados);
        puntuacionPiloto2PuntosCampeonatosDisputadosTextView = (TextView) findViewById(R.id.puntuacionPiloto2PuntosCampeonatosDisputados);
        puntuacionPiloto3PuntosCampeonatosDisputadosTextView = (TextView) findViewById(R.id.puntuacionPiloto3PuntosCampeonatosDisputados);
        puntuacionPiloto4PuntosCampeonatosDisputadosTextView = (TextView) findViewById(R.id.puntuacionPiloto4PuntosCampeonatosDisputados);
        puntuacionPiloto5PuntosCampeonatosDisputadosTextView = (TextView) findViewById(R.id.puntuacionPiloto5PuntosCampeonatosDisputados);

        nombrePiloto1PuntosCampeonatosDisputadosTextView = (TextView) findViewById(R.id.nombrePiloto1PuntosCampeonatosDisputados);
        nombrePiloto2PuntosCampeonatosDisputadosTextView = (TextView) findViewById(R.id.nombrePiloto2PuntosCampeonatosDisputados);
        nombrePiloto3PuntosCampeonatosDisputadosTextView = (TextView) findViewById(R.id.nombrePiloto3PuntosCampeonatosDisputados);
        nombrePiloto4PuntosCampeonatosDisputadosTextView = (TextView) findViewById(R.id.nombrePiloto4PuntosCampeonatosDisputados);
        nombrePiloto5PuntosCampeonatosDisputadosTextView = (TextView) findViewById(R.id.nombrePiloto5PuntosCampeonatosDisputados);

        fotoPiloto1PuntosCampeonatosDisputadosImageView = (ImageView) findViewById(R.id.fotoPiloto1PuntosCampeonatosDisputados);
        fotoPiloto2PuntosCampeonatosDisputadosImageView = (ImageView) findViewById(R.id.fotoPiloto2PuntosCampeonatosDisputados);
        fotoPiloto3PuntosCampeonatosDisputadosImageView = (ImageView) findViewById(R.id.fotoPiloto3PuntosCampeonatosDisputados);
        fotoPiloto4PuntosCampeonatosDisputadosImageView = (ImageView) findViewById(R.id.fotoPiloto4PuntosCampeonatosDisputados);
        fotoPiloto5PuntosCampeonatosDisputadosImageView = (ImageView) findViewById(R.id.fotoPiloto5PuntosCampeonatosDisputados);


        arrayPuntuacionesPilotoPuntosCampeonatosDisputadosTextView= new TextView[]{puntuacionPiloto1PuntosCampeonatosDisputadosTextView,
                puntuacionPiloto2PuntosCampeonatosDisputadosTextView,puntuacionPiloto3PuntosCampeonatosDisputadosTextView,puntuacionPiloto4PuntosCampeonatosDisputadosTextView,
                puntuacionPiloto5PuntosCampeonatosDisputadosTextView};

        arrayNombresPilotoPuntosCampeonatosDisputadosTextView= new TextView[]{nombrePiloto1PuntosCampeonatosDisputadosTextView,
                nombrePiloto2PuntosCampeonatosDisputadosTextView,nombrePiloto3PuntosCampeonatosDisputadosTextView,nombrePiloto4PuntosCampeonatosDisputadosTextView,
                nombrePiloto5PuntosCampeonatosDisputadosTextView};

        arrayFotosPilotoPuntosCampeonatosDisputadosImageView= new ImageView[]{fotoPiloto1PuntosCampeonatosDisputadosImageView,
                fotoPiloto2PuntosCampeonatosDisputadosImageView,fotoPiloto3PuntosCampeonatosDisputadosImageView,fotoPiloto4PuntosCampeonatosDisputadosImageView,
                fotoPiloto5PuntosCampeonatosDisputadosImageView};

        pilotosMasVictoriasPuntosCampeonatosDisputadosLinearLayout=(LinearLayout) findViewById(R.id.layerPilotosMasVictoriasPuntosCampeonatosDisputados);
        pilotosMasVictoriasPuntosCampeonatosDisputadosLinearLayout.setVisibility(View.INVISIBLE);


        //***********************************************top5_campeonatos_disputados

        new FetchDashboard().execute();

    }


    private class FetchDashboard extends AsyncTask<String, String, String> {
        JSONObject response;
        JSONObject consultaNombreEnWikipediaJSON;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Barra de progreso de carga
            pDialog = new ProgressDialog(MainActivityDashboardDisplay.this);
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
                dashboardJSONArray =  response.getJSONArray(KEY_DATA);
                while(next!="null"){
                    pagination=pagination+1;
                    urlParams.put("page",pagination.toString());
                    Log.d("next",pagination.toString());
                    response = jsonParser.makeHttpRequest(url,"GET",urlParams);
                    next=response.getString(KEY_NEXT);
                    Log.d("next",next);
                    JSONArray posicionesDashboardJSONArrayNext =  response.getJSONArray(KEY_DATA);

                    for (int i = 0; i < posicionesDashboardJSONArrayNext.length(); i++) {

                        JSONObject jsonObject = posicionesDashboardJSONArrayNext.getJSONObject(i);
                        dashboardJSONArray.put(jsonObject);
                    }
                }
                Log.d("next", dashboardJSONArray.toString());
                pagination=1;
            } catch (JSONException e) {
                e.printStackTrace();
            }




            return null;
        }

        protected void onPostExecute(String result) {

            runOnUiThread(new Runnable() {
                public void run() {

                    if (success >0) {
                        try {
                            System.out.println(KEY_DATA);
                            JSONObject dashboard= dashboardJSONArray.getJSONObject(0);
                            JSONArray datosHistoricos=dashboard.getJSONArray("datos_historicos");

                            JSONArray top5VictoriasPorCarrera = datosHistoricos.getJSONObject(0).getJSONArray("top5_victorias_carreras");
                            JSONArray top5VictoriasPorCampeonato = datosHistoricos.getJSONObject(1).getJSONArray("top5_victorias_campeonatos");
                            JSONArray top5PuntosGlobal = datosHistoricos.getJSONObject(2).getJSONArray("top5_puntos_global");
                            JSONArray top5PuntosTemporada = datosHistoricos.getJSONObject(3).getJSONArray("top5_puntos_temporada");
                            JSONArray top5CampeonatosDisputados = datosHistoricos.getJSONObject(4).getJSONArray("top5_campeonatos_disputados");
                            JSONArray top5VictoriasMoto = datosHistoricos.getJSONObject(5).getJSONArray("top5_victorias_marca");

                            //carreras***************************



                            for (int i = 0; i<top5VictoriasPorCarrera.length();i++){
                                String pilotoNombre;
                                JSONObject piloto=top5VictoriasPorCarrera.getJSONObject(i);
                                pilotoNombre=piloto.names().get(0).toString();
                                String transformacionNombre=pilotoNombre.toLowerCase();
                                String primerNombre=transformacionNombre.split(" ")[0];
                                String segundoNombre=transformacionNombre.split(" ")[1];
                                transformacionNombre=Character.toUpperCase(primerNombre.charAt(0)) + primerNombre.substring(1)+" "+Character.toUpperCase(segundoNombre.charAt(0)) + segundoNombre.substring(1);
                                String transformacionNombreArreglos;
                                switch(transformacionNombre) {
                                    case "Angel Nieto":
                                        transformacionNombreArreglos="Ángel Nieto";
                                        break;
                                    case "Marc Marquez":
                                        transformacionNombreArreglos="Marc Márquez";
                                        break;
                                    default:
                                        transformacionNombreArreglos=transformacionNombre;
                                }
                                ArrayList<String> valorArray = new ArrayList<String>();
                                valorArray.add(piloto.getString(pilotoNombre));
                                valorArray.add(pilotoNombre);
                                datosPilotos.put(transformacionNombreArreglos,valorArray);


                                if(i==0){
                                    nombrePilotosBusquedaWiki=transformacionNombreArreglos;
                                }else{
                                    nombrePilotosBusquedaWiki=nombrePilotosBusquedaWiki+"|"+transformacionNombreArreglos;
                                }
                            }
                            new FetchDashboardWikiCarreras().execute();
                            Log.d("print38","siguiente operacion");


                            //***************************carreras

                            //Campeonatos*****************************


                            for (int i = 0; i<top5VictoriasPorCampeonato.length();i++){
                                String pilotoNombre;
                                JSONObject piloto=top5VictoriasPorCampeonato.getJSONObject(i);
                                pilotoNombre=piloto.names().get(0).toString();
                                String transformacionNombre=pilotoNombre.toLowerCase();
                                String primerNombre=transformacionNombre.split(" ")[0];
                                String segundoNombre=transformacionNombre.split(" ")[1];
                                transformacionNombre=Character.toUpperCase(primerNombre.charAt(0)) + primerNombre.substring(1)+" "+Character.toUpperCase(segundoNombre.charAt(0)) + segundoNombre.substring(1);
                                String transformacionNombreArreglos;
                                switch(transformacionNombre) {
                                    case "Angel Nieto":
                                        transformacionNombreArreglos="Ángel Nieto";
                                        break;
                                    case "Marc Marquez":
                                        transformacionNombreArreglos="Marc Márquez";
                                        break;
                                    default:
                                        transformacionNombreArreglos=transformacionNombre;
                                }

                                ArrayList<String> valorArray = new ArrayList<String>();
                                valorArray.add(piloto.getString(pilotoNombre));
                                valorArray.add(pilotoNombre);
                                datosPilotosCampeonatos.put(transformacionNombreArreglos,valorArray);

                                if(i==0){
                                    nombrePilotosBusquedaWikiCampeonatos=transformacionNombreArreglos;
                                }else{
                                    nombrePilotosBusquedaWikiCampeonatos=nombrePilotosBusquedaWikiCampeonatos+"|"+transformacionNombreArreglos;
                                }
                            }
                            new FetchDashboardWikiCampeonatos().execute();
                            Log.d("print38","siguiente operacion");

                            //*****************************Campeonatos


                            //PuntosGlobales*****************************


                            for (int i = 0; i<top5PuntosGlobal.length();i++){
                                String pilotoNombre;
                                JSONObject piloto=top5PuntosGlobal.getJSONObject(i);
                                pilotoNombre=piloto.names().get(0).toString();
                                String transformacionNombre=pilotoNombre.toLowerCase();
                                String primerNombre=transformacionNombre.split(" ")[0];
                                String segundoNombre=transformacionNombre.split(" ")[1];
                                transformacionNombre=Character.toUpperCase(primerNombre.charAt(0)) + primerNombre.substring(1)+" "+Character.toUpperCase(segundoNombre.charAt(0)) + segundoNombre.substring(1);
                                String transformacionNombreArreglos;
                                switch(transformacionNombre) {
                                    case "Angel Nieto":
                                        transformacionNombreArreglos="Ángel Nieto";
                                        break;
                                    case "Marc Marquez":
                                        transformacionNombreArreglos="Marc Márquez";
                                        break;
                                    default:
                                        transformacionNombreArreglos=transformacionNombre;
                                }

                                ArrayList<String> valorArray = new ArrayList<String>();
                                valorArray.add(piloto.getString(pilotoNombre));
                                valorArray.add(pilotoNombre);
                                datosPilotosPuntosGlobales.put(transformacionNombreArreglos,valorArray);

                                if(i==0){
                                    nombrePilotosBusquedaWikiPuntosGlobales=transformacionNombreArreglos;
                                }else{
                                    nombrePilotosBusquedaWikiPuntosGlobales=nombrePilotosBusquedaWikiPuntosGlobales+"|"+transformacionNombreArreglos;
                                }
                            }
                            new FetchDashboardWikiPuntosGlobales().execute();
                            Log.d("print38","siguiente operacion");

                            //*****************************PuntosGlobales

                            //PuntosTemporada*****************************


                            for (int i = 0; i<top5CampeonatosDisputados.length();i++){
                                String pilotoNombre;
                                JSONObject piloto=top5CampeonatosDisputados.getJSONObject(i);
                                pilotoNombre=piloto.names().get(0).toString();
                                String transformacionNombre=pilotoNombre.toLowerCase();
                                String primerNombre=transformacionNombre.split(" ")[0];
                                String segundoNombre=transformacionNombre.split(" ")[1];
                                transformacionNombre=Character.toUpperCase(primerNombre.charAt(0)) + primerNombre.substring(1)+" "+Character.toUpperCase(segundoNombre.charAt(0)) + segundoNombre.substring(1);
                                String transformacionNombreArreglos;
                                switch(transformacionNombre) {
                                    case "Angel Nieto":
                                        transformacionNombreArreglos="Ángel Nieto";
                                        break;
                                    case "Marc Marquez":
                                        transformacionNombreArreglos="Marc Márquez";
                                        break;
                                    default:
                                        transformacionNombreArreglos=transformacionNombre;
                                }

                                ArrayList<String> valorArray = new ArrayList<String>();
                                valorArray.add(piloto.getString(pilotoNombre));
                                valorArray.add(pilotoNombre);
                                datosPilotosPuntosCampeonatosDisputados.put(transformacionNombreArreglos,valorArray);

                                if(i==0){
                                    nombrePilotosBusquedaWikiPuntosCampeonatosDisputados=transformacionNombreArreglos;
                                }else{
                                    nombrePilotosBusquedaWikiPuntosCampeonatosDisputados=nombrePilotosBusquedaWikiPuntosCampeonatosDisputados+"|"+transformacionNombreArreglos;
                                }
                            }
                            new FetchDashboardWikiPuntosCampeonatosDisputados().execute();
                            Log.d("print38","siguiente operacion");

                            //*****************************PuntosGlobales



                            //RadarchartVictoriasPorMoto****************

                            final HashMap<String,Integer>victoriasPorCategoria=new HashMap<>();

                            ArrayList<Entry> entriesRadarchartVictoriasPorMoto = new ArrayList<Entry>();
                            ArrayList<String> labelsRadarchartVictoriasPorMoto = new ArrayList<String>();
                            for (int i = 0; i<top5VictoriasMoto.length();i++){
                                String motoNombre;
                                JSONObject piloto=top5VictoriasMoto.getJSONObject(i);
                                motoNombre=piloto.names().get(0).toString();
                                labelsRadarchartVictoriasPorMoto.add(motoNombre);
                                entriesRadarchartVictoriasPorMoto.add(new Entry(piloto.getInt(motoNombre), i));
                            }

                            radarchartVictoriasPorMoto = (RadarChart) findViewById(R.id.radarchartVictoriasPorMoto);


                            radarchartVictoriasPorMoto.getXAxis().setTextColor(Color.WHITE);
                            radarchartVictoriasPorMoto.getXAxis().setTextSize(14);

                            // crear el grafico radial

                            RadarDataSet dataSetRadarchartVictoriasPorMoto = new RadarDataSet(entriesRadarchartVictoriasPorMoto, "");

                            dataSetRadarchartVictoriasPorMoto.setVisible(true);
                            dataSetRadarchartVictoriasPorMoto.setHighlightCircleFillColor(Color.GREEN);
                            dataSetRadarchartVictoriasPorMoto.setValueTextColor(Color.RED);
                            dataSetRadarchartVictoriasPorMoto.setDrawFilled(true);
                            //  añadir x e y al grafico radial
                            final RadarData dataRadarchartVictoriasPorMoto = new RadarData(labelsRadarchartVictoriasPorMoto, dataSetRadarchartVictoriasPorMoto);

                            dataRadarchartVictoriasPorMoto.setValueTextSize(0);
                            dataRadarchartVictoriasPorMoto.setValueTextColor(Color.WHITE);
                            // añadir legenda al grafico
                            Legend l = radarchartVictoriasPorMoto.getLegend();
                            l.setEnabled(false);
                            radarchartVictoriasPorMoto.getYAxis().setEnabled(false);
                            radarchartVictoriasPorMoto.setDescription("");
                            radarchartVictoriasPorMoto.setWebColor(Color.YELLOW);
                            radarchartVictoriasPorMoto.setWebColorInner(Color.BLUE);
                            radarchartVictoriasPorMoto.setData(dataRadarchartVictoriasPorMoto);
                            radarchartVictoriasPorMoto.highlightValues(null);
                            // refrescar el grafico
                            radarchartVictoriasPorMoto.invalidate();
                            // animacion del grafico
                            radarchartVictoriasPorMoto.animateY(1500);
                            radarchartVictoriasPorMoto.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

                                @Override
                                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                                    // seleccionar mensaje que se muestra al clicar
                                    if (e == null)
                                        return;
                                    dataRadarchartVictoriasPorMoto.setValueTextSize(13);
                                    Toast.makeText(MainActivityDashboardDisplay.this,
                                            (int)e.getVal()+ " victorias con la moto "+dataRadarchartVictoriasPorMoto.getXVals().get(e.getXIndex()), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNothingSelected() {
                                    dataRadarchartVictoriasPorMoto.setValueTextSize(0);
                                }
                            });

                            //*************************piechartVictoriasPorCategoria


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(MainActivityDashboardDisplay.this,
                                "Some error occurred while loading data",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    final class FetchDashboardWikiCarreras extends AsyncTask<String, String, String> {
        JSONObject consultaNombreEnWikipediaJSON;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... params) {

            HttpJsonParser jsonParserConsultaNombreEnWikipediaJSON = new HttpJsonParser();
            Map<String,String> urlParamsConsultaNombreEnWikipedia= new HashMap<>();
            urlParamsConsultaNombreEnWikipedia.put("format","json");
            urlParamsConsultaNombreEnWikipedia.put("action","query");
            urlParamsConsultaNombreEnWikipedia.put("pithumbsize","200");
            urlParamsConsultaNombreEnWikipedia.put("formatversion","2");
            urlParamsConsultaNombreEnWikipedia.put("prop","pageimages");
            urlParamsConsultaNombreEnWikipedia.put("titles",nombrePilotosBusquedaWiki);
            Log.d("print33",urlParamsConsultaNombreEnWikipedia.toString());
            consultaNombreEnWikipediaJSON = jsonParserConsultaNombreEnWikipediaJSON.makeHttpRequest("https://en.wikipedia.org/w/api.php/","GET",urlParamsConsultaNombreEnWikipedia);
            Log.d("print34",consultaNombreEnWikipediaJSON.toString());
            try {
                JSONObject queryJSON=consultaNombreEnWikipediaJSON.getJSONObject("query");
                JSONArray resuldosWikipediaJSONArray=queryJSON.getJSONArray("pages");
                for (int resultadoWikipediaLoop=0;resultadoWikipediaLoop<resuldosWikipediaJSONArray.length();resultadoWikipediaLoop++){
                    JSONObject informacionJSON=resuldosWikipediaJSONArray.getJSONObject(resultadoWikipediaLoop);
                    String fotoUrlJSON;
                    String nombreTitle=informacionJSON.getString("title");
                    try{
                        JSONObject enlaceJSON=informacionJSON.getJSONObject("thumbnail");
                        Log.d("print42",enlaceJSON.toString());
                        fotoUrlJSON=enlaceJSON.getString("source");


                    }catch(JSONException e){
                        fotoUrlJSON="https://dit.ietcc.csic.es/wp-content/uploads/2018/11/foto-generica-200x200.jpg";
                    }
                    datosPilotos.get(nombreTitle).add(fotoUrlJSON);
                    Log.d("print40",fotoUrlJSON);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }
        protected void onPostExecute(String result) {

            runOnUiThread(new Runnable() {
                public void run() {

                    Integer contadorDatosPilotosLoop=0;

                    Log.d("print43", nombrePilotosBusquedaWiki);
                    Log.d("print44", nombrePilotosBusquedaWiki.split("\\|")[0]);
                    Log.d("print45", nombrePilotosBusquedaWiki.split("\\|")[2]);

                    for(String nombre: nombrePilotosBusquedaWiki.split("\\|")){
                        Log.d("print43", nombre);
                        final String nombreRedireccion=nombre;
                        arrayPuntuacionesPilotoTextView[contadorDatosPilotosLoop].setText(datosPilotos.get(nombre).get(0));
                        arrayNombresPilotoTextView[contadorDatosPilotosLoop].setText(nombre);
                        Picasso.with(mContext).load(datosPilotos.get(nombre).get(2)).resize(300, 300).into(arrayFotosPilotoImageView[contadorDatosPilotosLoop]);
                        //arrayPuntuacionesPilotoTextView[contadorDatosPilotosLoop].setVisibility(View.VISIBLE);
                        //arrayNombresPilotoTextView[contadorDatosPilotosLoop].setVisibility(View.VISIBLE);
                        //arrayFotosPilotoImageView[contadorDatosPilotosLoop].setVisibility(View.VISIBLE);
                        arrayFotosPilotoImageView[contadorDatosPilotosLoop].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intentMainActivityDashboard = new Intent(mContext, MainActivityPilotoDisplay.class);
                                intentMainActivityDashboard.putExtra("nombrePiloto",datosPilotos.get(nombreRedireccion).get(1));
                                mContext.startActivity(intentMainActivityDashboard);
                            }
                        });
                        contadorDatosPilotosLoop++;
                    }
                    pilotosMasVictoriasCarrerasLinearLayout.setVisibility(View.VISIBLE);
                    pDialog.dismiss();

                }
            });
        }
    }



    final class FetchDashboardWikiCampeonatos extends AsyncTask<String, String, String> {
        JSONObject consultaNombreEnWikipediaJSON;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... params) {

            HttpJsonParser jsonParserConsultaNombreEnWikipediaJSON = new HttpJsonParser();
            Map<String,String> urlParamsConsultaNombreEnWikipedia= new HashMap<>();
            urlParamsConsultaNombreEnWikipedia.put("format","json");
            urlParamsConsultaNombreEnWikipedia.put("action","query");
            urlParamsConsultaNombreEnWikipedia.put("pithumbsize","200");
            urlParamsConsultaNombreEnWikipedia.put("formatversion","2");
            urlParamsConsultaNombreEnWikipedia.put("prop","pageimages");
            urlParamsConsultaNombreEnWikipedia.put("titles",nombrePilotosBusquedaWikiCampeonatos);
            Log.d("print33",urlParamsConsultaNombreEnWikipedia.toString());
            //en.wikipedia.org/w/api.php?action=query&format=json&formatversion=2&prop=pageimages&&titles=Valentino%20Rossi|Jorge%20Lorenzo|Marc%20Márquez
            consultaNombreEnWikipediaJSON = jsonParserConsultaNombreEnWikipediaJSON.makeHttpRequest("https://en.wikipedia.org/w/api.php/", "GET", urlParamsConsultaNombreEnWikipedia);
            Log.d("print34",consultaNombreEnWikipediaJSON.toString());
            try {
                JSONObject queryJSON=consultaNombreEnWikipediaJSON.getJSONObject("query");
                JSONArray resuldosWikipediaJSONArray=queryJSON.getJSONArray("pages");
                for (int resultadoWikipediaLoop=0;resultadoWikipediaLoop<resuldosWikipediaJSONArray.length();resultadoWikipediaLoop++){
                    JSONObject informacionJSON=resuldosWikipediaJSONArray.getJSONObject(resultadoWikipediaLoop);
                    String fotoUrlJSON;
                    String nombreTitle=informacionJSON.getString("title");
                    try{
                        JSONObject enlaceJSON=informacionJSON.getJSONObject("thumbnail");
                        Log.d("print42",enlaceJSON.toString());
                        fotoUrlJSON=enlaceJSON.getString("source");


                    }catch(JSONException e){
                        fotoUrlJSON="https://dit.ietcc.csic.es/wp-content/uploads/2018/11/foto-generica-200x200.jpg";
                    }
                    datosPilotosCampeonatos.get(nombreTitle).add(fotoUrlJSON);
                    Log.d("print40",fotoUrlJSON);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }
        protected void onPostExecute(String result) {

            runOnUiThread(new Runnable() {
                public void run() {

                    Integer contadorDatosPilotosLoop=0;
                    for(String nombre: nombrePilotosBusquedaWikiCampeonatos.split("\\|")){
                        Log.d("print43", Integer.toString(contadorDatosPilotosLoop));
                        Log.d("print43", nombre);

                        final String nombreRedireccion=nombre;
                        arrayPuntuacionesPilotoCampeonatosTextView[contadorDatosPilotosLoop].setText(datosPilotosCampeonatos.get(nombre).get(0));
                        arrayNombresPilotoCampeonatosTextView[contadorDatosPilotosLoop].setText(nombre);

                        Picasso.with(mContext).load(datosPilotosCampeonatos.get(nombre).get(2)).resize(300, 300).into(arrayFotosPilotoCampeonatosImageView[contadorDatosPilotosLoop]);

                        arrayFotosPilotoCampeonatosImageView[contadorDatosPilotosLoop].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intentMainActivityDashboard = new Intent(mContext, MainActivityPilotoDisplay.class);
                                intentMainActivityDashboard.putExtra("nombrePiloto",datosPilotosCampeonatos.get(nombreRedireccion).get(1));
                                mContext.startActivity(intentMainActivityDashboard);
                            }
                        });

                        contadorDatosPilotosLoop++;
                    }
                    pilotosMasVictoriasCampeonatosLinearLayout.setVisibility(View.VISIBLE);
                    pDialog.dismiss();
                }
            });
        }
    }




    final class FetchDashboardWikiPuntosGlobales extends AsyncTask<String, String, String> {
        JSONObject consultaNombreEnWikipediaJSON;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... params) {

            HttpJsonParser jsonParserConsultaNombreEnWikipediaJSON = new HttpJsonParser();
            Map<String,String> urlParamsConsultaNombreEnWikipedia= new HashMap<>();
            urlParamsConsultaNombreEnWikipedia.put("format","json");
            urlParamsConsultaNombreEnWikipedia.put("action","query");
            urlParamsConsultaNombreEnWikipedia.put("pithumbsize","200");
            urlParamsConsultaNombreEnWikipedia.put("formatversion","2");
            urlParamsConsultaNombreEnWikipedia.put("prop","pageimages");
            urlParamsConsultaNombreEnWikipedia.put("titles",nombrePilotosBusquedaWikiPuntosGlobales);
            Log.d("print33",urlParamsConsultaNombreEnWikipedia.toString());
            //en.wikipedia.org/w/api.php?action=query&format=json&formatversion=2&prop=pageimages&&titles=Valentino%20Rossi|Jorge%20Lorenzo|Marc%20Márquez
            consultaNombreEnWikipediaJSON = jsonParserConsultaNombreEnWikipediaJSON.makeHttpRequest("https://en.wikipedia.org/w/api.php/", "GET", urlParamsConsultaNombreEnWikipedia);
            Log.d("print34",consultaNombreEnWikipediaJSON.toString());
            try {
                JSONObject queryJSON=consultaNombreEnWikipediaJSON.getJSONObject("query");
                JSONArray resuldosWikipediaJSONArray=queryJSON.getJSONArray("pages");
                for (int resultadoWikipediaLoop=0;resultadoWikipediaLoop<resuldosWikipediaJSONArray.length();resultadoWikipediaLoop++){
                    JSONObject informacionJSON=resuldosWikipediaJSONArray.getJSONObject(resultadoWikipediaLoop);
                    String fotoUrlJSON;
                    String nombreTitle=informacionJSON.getString("title");
                    try{
                        JSONObject enlaceJSON=informacionJSON.getJSONObject("thumbnail");
                        Log.d("print42",enlaceJSON.toString());
                        fotoUrlJSON=enlaceJSON.getString("source");


                    }catch(JSONException e){
                        fotoUrlJSON="https://dit.ietcc.csic.es/wp-content/uploads/2018/11/foto-generica-200x200.jpg";
                    }
                    datosPilotosPuntosGlobales.get(nombreTitle).add(fotoUrlJSON);
                    Log.d("print40",fotoUrlJSON);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }
        protected void onPostExecute(String result) {

            runOnUiThread(new Runnable() {
                public void run() {

                    Integer contadorDatosPilotosLoop=0;
                    for(String nombre: nombrePilotosBusquedaWikiPuntosGlobales.split("\\|")){
                        Log.d("print43", Integer.toString(contadorDatosPilotosLoop));
                        Log.d("print43", nombre);

                        final String nombreRedireccion=nombre;
                        arrayPuntuacionesPilotoPuntosGlobalesTextView[contadorDatosPilotosLoop].setText(datosPilotosPuntosGlobales.get(nombre).get(0));
                        arrayNombresPilotoPuntosGlobalesTextView[contadorDatosPilotosLoop].setText(nombre);

                        Picasso.with(mContext).load(datosPilotosPuntosGlobales.get(nombre).get(2)).resize(300, 300).into(arrayFotosPilotoPuntosGlobalesImageView[contadorDatosPilotosLoop]);

                        arrayFotosPilotoPuntosGlobalesImageView[contadorDatosPilotosLoop].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intentMainActivityDashboard = new Intent(mContext, MainActivityPilotoDisplay.class);
                                intentMainActivityDashboard.putExtra("nombrePiloto",datosPilotosPuntosGlobales.get(nombreRedireccion).get(1));
                                mContext.startActivity(intentMainActivityDashboard);
                            }
                        });

                        contadorDatosPilotosLoop++;
                    }
                    pilotosMasVictoriasPuntosGlobalesLinearLayout.setVisibility(View.VISIBLE);
                    pDialog.dismiss();
                }
            });
        }
    }
    
    

    final class FetchDashboardWikiPuntosCampeonatosDisputados extends AsyncTask<String, String, String> {
        JSONObject consultaNombreEnWikipediaJSON;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... params) {

            HttpJsonParser jsonParserConsultaNombreEnWikipediaJSON = new HttpJsonParser();
            Map<String,String> urlParamsConsultaNombreEnWikipedia= new HashMap<>();
            urlParamsConsultaNombreEnWikipedia.put("format","json");
            urlParamsConsultaNombreEnWikipedia.put("action","query");
            urlParamsConsultaNombreEnWikipedia.put("pithumbsize","200");
            urlParamsConsultaNombreEnWikipedia.put("formatversion","2");
            urlParamsConsultaNombreEnWikipedia.put("prop","pageimages");
            urlParamsConsultaNombreEnWikipedia.put("titles",nombrePilotosBusquedaWikiPuntosCampeonatosDisputados);
            Log.d("print33",urlParamsConsultaNombreEnWikipedia.toString());
            //en.wikipedia.org/w/api.php?action=query&format=json&formatversion=2&prop=pageimages&&titles=Valentino%20Rossi|Jorge%20Lorenzo|Marc%20Márquez
            consultaNombreEnWikipediaJSON = jsonParserConsultaNombreEnWikipediaJSON.makeHttpRequest("https://en.wikipedia.org/w/api.php/", "GET", urlParamsConsultaNombreEnWikipedia);
            Log.d("print34",consultaNombreEnWikipediaJSON.toString());
            try {
                JSONObject queryJSON=consultaNombreEnWikipediaJSON.getJSONObject("query");
                JSONArray resuldosWikipediaJSONArray=queryJSON.getJSONArray("pages");
                for (int resultadoWikipediaLoop=0;resultadoWikipediaLoop<resuldosWikipediaJSONArray.length();resultadoWikipediaLoop++){
                    JSONObject informacionJSON=resuldosWikipediaJSONArray.getJSONObject(resultadoWikipediaLoop);
                    String fotoUrlJSON;
                    String nombreTitle=informacionJSON.getString("title");
                    try{
                        JSONObject enlaceJSON=informacionJSON.getJSONObject("thumbnail");
                        Log.d("print42",enlaceJSON.toString());
                        fotoUrlJSON=enlaceJSON.getString("source");


                    }catch(JSONException e){
                        fotoUrlJSON="https://dit.ietcc.csic.es/wp-content/uploads/2018/11/foto-generica-200x200.jpg";
                    }
                    datosPilotosPuntosCampeonatosDisputados.get(nombreTitle).add(fotoUrlJSON);
                    Log.d("print40",fotoUrlJSON);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }
        protected void onPostExecute(String result) {

            runOnUiThread(new Runnable() {
                public void run() {

                    Integer contadorDatosPilotosLoop=0;
                    for(String nombre: nombrePilotosBusquedaWikiPuntosCampeonatosDisputados.split("\\|")){
                        Log.d("print43", Integer.toString(contadorDatosPilotosLoop));
                        Log.d("print43", nombre);

                        final String nombreRedireccion=nombre;
                        arrayPuntuacionesPilotoPuntosCampeonatosDisputadosTextView[contadorDatosPilotosLoop].setText(datosPilotosPuntosCampeonatosDisputados.get(nombre).get(0));
                        arrayNombresPilotoPuntosCampeonatosDisputadosTextView[contadorDatosPilotosLoop].setText(nombre);

                        Picasso.with(mContext).load(datosPilotosPuntosCampeonatosDisputados.get(nombre).get(2)).resize(300, 300).into(arrayFotosPilotoPuntosCampeonatosDisputadosImageView[contadorDatosPilotosLoop]);

                        arrayFotosPilotoPuntosCampeonatosDisputadosImageView[contadorDatosPilotosLoop].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intentMainActivityDashboard = new Intent(mContext, MainActivityPilotoDisplay.class);
                                intentMainActivityDashboard.putExtra("nombrePiloto",datosPilotosPuntosCampeonatosDisputados.get(nombreRedireccion).get(1));
                                mContext.startActivity(intentMainActivityDashboard);
                            }
                        });

                        contadorDatosPilotosLoop++;
                    }
                    pilotosMasVictoriasPuntosCampeonatosDisputadosLinearLayout.setVisibility(View.VISIBLE);
                    pDialog.dismiss();
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
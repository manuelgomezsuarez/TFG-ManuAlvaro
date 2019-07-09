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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tfg_manualvaro.androidmotogp.adapter.CategoriaAdapter;
import tfg_manualvaro.androidmotogp.adapter.DocumentacionAdapter;
import tfg_manualvaro.androidmotogp.adapter.PosicionCarreraAdapter;
import tfg_manualvaro.androidmotogp.models.CarreraModelo;
import tfg_manualvaro.androidmotogp.models.Categoria;
import tfg_manualvaro.androidmotogp.models.DocumentacionModelo;
import tfg_manualvaro.androidmotogp.models.PosicionCarrera;
import tfg_manualvaro.androidmotogp.models.Temporada;
import tfg_manualvaro.androidmotogp.models.Url;
import tfg_manualvaro.androidmotogp.utils.HttpJsonParser;

public class MainActivityDocumentacion extends AppCompatActivity {
    private static final String KEY_SUCCESS = "count";
    private static final String KEY_DATA = "results";
    private static final String KEY_DOCUMENTACION = "documentacion";
    private static final String KEY_NEXT = "next";
    private static Context mContext;
    private static String temporadaMainActivitySelector;
    private static String categoriaMainActivitySelector;
    private static String tituloMainActivitySelector;
    private static String lugarMainActivitySelector;
    private static String fechaMainActivitySelector;
    private static String tituloCarrera;

    //private String url = "http://hr8jeljvudseiccl8kzsu4.webrelay.io/carrera/";
    private String url = "https://motogp-api.herokuapp.com/documentacion/";
    private Map<String, String> urlParams = new HashMap<>();

    private ProgressDialog pDialog;
    private int success;
    private String next;
    private Integer pagination = 1;
    private DocumentacionAdapter adapter;
    private JSONArray urlsJSONArray = null;
    private String urls=null;
    private LinearLayout LayoutDeCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("print13", "hemos llegado el MainActivityDocumentacionDisplay");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_documentacion_display);
        //Call the AsyncTask
        mContext = this;
        Intent intentMainActivitySelector = getIntent();
        temporadaMainActivitySelector = intentMainActivitySelector.getStringExtra("temporada");
        categoriaMainActivitySelector = intentMainActivitySelector.getStringExtra("categoria");
        tituloMainActivitySelector = intentMainActivitySelector.getStringExtra("titulo");
        fechaMainActivitySelector = intentMainActivitySelector.getStringExtra("fecha");
        lugarMainActivitySelector = intentMainActivitySelector.getStringExtra("lugar");
        LayoutDeCarga=(LinearLayout) findViewById(R.id.LayoutDeCarga);
        LayoutDeCarga.setVisibility(View.INVISIBLE);

//        Log.i("print16", temporadaMainActivitySelector);
//        Log.i("print17", categoriaMainActivitySelector);
//        Log.i("print18", tituloMainActivitySelector);
        new MainActivityDocumentacion.FetchDocumentacion().execute();

    }
    public static void startUrl(Intent intent) {
        mContext.startActivity(intent);
    }


    private class FetchDocumentacion extends AsyncTask<String, String, String> {
        JSONObject response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivityDocumentacion.this);
            pDialog.setMessage("Loading Data.. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser jsonParser = new HttpJsonParser();


            urlParams.put("format", "json");
            urlParams.put("temporada", temporadaMainActivitySelector);
            urlParams.put("categoria", categoriaMainActivitySelector);
            urlParams.put("titulo", tituloMainActivitySelector);
            urlParams.put("page", pagination.toString());
            Log.i("print19", urlParams.toString());
            response = jsonParser.makeHttpRequest(url, "GET", urlParams);

            try {
                success = response.getInt(KEY_SUCCESS);
                next = response.getString(KEY_NEXT);
                urlsJSONArray = response.getJSONArray(KEY_DATA);

                while (next != "null") {
                    pagination = pagination + 1;
                    urlParams.put("page", pagination.toString());
                    Log.i("next", pagination.toString());
                    response = jsonParser.makeHttpRequest(url, "GET", urlParams);
                    next = response.getString(KEY_NEXT);
                    Log.i("next", next);
                    JSONArray urlsJSONArrayNext = response.getJSONArray(KEY_DATA);

                    for (int i = 0; i < urlsJSONArrayNext.length(); i++) {

                        JSONObject jsonObject = urlsJSONArrayNext.getJSONObject(i);
                        urlsJSONArray.put(jsonObject);
                    }
                }
                Log.i("next", urlsJSONArray.toString());
                pagination = 1;
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
                    TextView fechaTextView = (TextView) findViewById(R.id.textoFecha);
                    TextView lugarTextView = (TextView) findViewById(R.id.textoLugar);
                    TextView tituloTextView = (TextView) findViewById(R.id.textoAbreviatura);

                    categoriaTextView.setText(categoriaMainActivitySelector);
                    fechaTextView.setText(fechaMainActivitySelector);
                    lugarTextView.setText(lugarMainActivitySelector);
                    tituloTextView.setText(tituloMainActivitySelector);
                    ListView listView = (ListView) findViewById(R.id.objectList);
                    if (success > 0) {
                        try {
                            System.out.println(KEY_DATA);
                            DocumentacionModelo documentacion = new DocumentacionModelo();
                            List<Url> urlList= new ArrayList<Url>();
                            //Populate the EmployeeDetails list from response
                            for (int i = 0; i < urlsJSONArray.length(); i++) {
                                DocumentacionModelo pos = new DocumentacionModelo();
                                JSONObject posicionJSON = urlsJSONArray.getJSONObject(i);
                                JSONArray enlaces=posicionJSON.getJSONArray("documentacion");
                                Log.i("printManu", Integer.toString(enlaces.length()));
                                for (int n=0;n< enlaces.length();n++){
                                    Url url= new Url();

                                    Log.i("printManu", enlaces.getString(n));
                                    url.setUrl(enlaces.getString(n));
                                    urlList.add(url);
                                }
                            }

                            documentacion.setUrls(urlList);
                            documentacion.setCategoria(categoriaMainActivitySelector);
                            documentacion.setTemporada(temporadaMainActivitySelector);
                            documentacion.setFecha(fechaMainActivitySelector);
                            documentacion.setLugar(lugarMainActivitySelector);
                            documentacion.setTitulo(tituloMainActivitySelector);
                            //Create an adapter with the EmployeeDetails List and set it to the LstView
                            adapter = new DocumentacionAdapter(documentacion, getApplicationContext());
                            LayoutDeCarga.setVisibility(View.VISIBLE);
                            listView.setAdapter(adapter);
                            Utility.setListViewHeightBasedOnChildren(listView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(MainActivityDocumentacion.this,
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

    public  void GoHome(View view){
        Log.i("print8", "go home");
        Intent intentMainActivityInicial = new Intent(mContext, MainActivityInicial.class);
        mContext.startActivity(intentMainActivityInicial);
    }




}
package tfg_manualvaro.androidmotogp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivityInicial extends AppCompatActivity{


    private static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        mContext = this;
        Log.d("print7", "estamos en metodo inicial");
        ImageView entry = (ImageView) findViewById(R.id.imageTelegram);
        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri;
                uri = Uri.parse("https://t.me/APIMotoGP");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

    }

    public  void FiltroBasico(View view){
        Log.d("print8", "vamos a cambiar a mainActivity");
        Intent intentMainActivity= new Intent(mContext, MainActivity.class);
        mContext.startActivity(intentMainActivity);

    }


    public  void UltimaCarrera(View view){
        Log.d("print8", "vamos a cambiar a UltimaCarrera");
        Intent intentMainActivity= new Intent(mContext, MainActivityCategoria.class);
        intentMainActivity.putExtra("entrada","ultimaCarrera");
        mContext.startActivity(intentMainActivity);

    }

    public  void BuscarCampeonato(View view){
        Log.d("print8", "vamos a cambiar a mainActivityCampeonatoDisplay");
        Intent intentMainActivityCampeonato = new Intent(mContext, MainActivityCampeonatoDisplay.class);
        mContext.startActivity(intentMainActivityCampeonato);
    }


    public  void BuscarDashboard(View view){
        Log.d("print8", "vamos a cambiar a mainActivityDasboardDisplay");
        Intent intentMainActivityInicial = new Intent(mContext, MainActivityDashboardDisplay.class);
        mContext.startActivity(intentMainActivityInicial);
    }

    public  void BuscarPiloto(View view){
        final EditText nombrePiloto = (EditText) findViewById(R.id.textoPiloto);


        String nombre= nombrePiloto.getText().toString();
        Log.d("print30", nombre);


        if (nombre.trim().length()>=3 && !nombre.trim().isEmpty()){
            Log.d("print8", "vamos a cambiar a mainActivityCampeonatoDisplay");
            Intent intentMainActivityInicial = new Intent(mContext, MainActivityPiloto.class);
            intentMainActivityInicial.putExtra("nombrePiloto",nombre.trim());
            mContext.startActivity(intentMainActivityInicial);
        }
        else{
            Toast.makeText(this, "Por favor, introduce al menos 3 caracteres para buscar un piloto", Toast.LENGTH_SHORT).show();
            nombrePiloto.setVisibility(View.VISIBLE);
        }

    }






}
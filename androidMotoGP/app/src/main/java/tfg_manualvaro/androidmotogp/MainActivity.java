package tfg_manualvaro.androidmotogp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Buscar(View view) {
        String busqueda;
        busqueda = ((EditText)findViewById(R.id.textInput1)).getText().toString();

        TextView texto1= (TextView)findViewById(R.id.texto1);
        texto1.setText(busqueda);
        texto1.setVisibility(View.VISIBLE);

    }
}

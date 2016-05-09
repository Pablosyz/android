package es.upm.dit.adsw.planetas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner escoge;
    private ImageView imagen;

    private int planets[]= {
            R.drawable.mercurio,
            R.drawable.venus,
            R.drawable.latierra,
            R.drawable.marte,
            R.drawable.jupiter,
            R.drawable.saturno,
            R.drawable.urano,
            R.drawable.neptuno
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        escoge = (Spinner) findViewById(R.id.spinner);
        imagen = (ImageView) findViewById(R.id.imageView);


        if (true) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.lista_de_planetas,
                            android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            escoge.setAdapter(adapter);

            escoge.setOnItemSelectedListener(new MyOnItemSelectedListener());
        } else {
            // Tambien
            escoge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                /*
                 * (non-Javadoc)
                 *
                 * @see
                 * android.widget.AdapterView.OnItemSelectedListener#onItemSelected
                 * (android.widget.AdapterView, android.view.View, int, long)
                 */
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int pos, long id) {
                    Toast.makeText(
                            parent.getContext(),
                            "El planeta escogido es "
                                    + parent.getItemAtPosition(pos).toString(),
                            Toast.LENGTH_LONG).show();
                }

                /*
                 * (non-Javadoc)
                 *
                 * @see android.widget.AdapterView.OnItemSelectedListener#
                 * onNothingSelected(android.widget.AdapterView)
                 */
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }

            });
        }
    }

    public void planetaElegido(View v) {
        // Toast.makeText(this, "El planeta es " +
        // parent.getItemAtPosition(pos).toString(),
        // Toast.LENGTH_LONG).show();

        Log.e("ADSW", "se ha escogido un planeta");

    }

    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                   long id) {
            Toast.makeText(
                    parent.getContext(),
                    "El planeta escogido es "
                            + parent.getItemAtPosition(pos).toString() + " en pos " + pos,
                    Toast.LENGTH_SHORT).show();
            imagen.setImageResource(planets[pos]);
            Log.e("Planetas", "Escogida posici�n " + pos);
        }

        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(parent.getContext(), "Para qu� tocas? ",
                    Toast.LENGTH_LONG).show();
        }
    }
}

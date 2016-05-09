package es.upm.dit.adsw.imc_41;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText alturaText;
    private EditText pesoText;
    private TextView imcText;
    private TextView clasifIMCText;
    private TextView recomendacionText;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context= getApplicationContext();

        alturaText= (EditText) findViewById(R.id.editAltura);
        pesoText= (EditText) findViewById(R.id.editPeso);
        imcText= (TextView) findViewById(R.id.textResIMC);
        clasifIMCText= (TextView) findViewById(R.id.clasifIMC);
        recomendacionText= (TextView) findViewById(R.id.recomendacionIMC);


        Button calcIMCButton= (Button) findViewById(R.id.buttonCalcIMC);

        calcIMCButton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        setResult(RESULT_OK);
                        calculaIMC();
                    }
                }
        );
    }

    public void calculaIMC(View v)
    {
        calculaIMC();
    }

    private void calculaIMC()
    {
        if (alturaText.getText().length() == 0)
        {
            Log.e("ADSW", "calculaIMC: altura vac�a");

            Toast toast = Toast.makeText(this, R.string.errorAlturaVacio, Toast.LENGTH_);
            toast.show();

            return;
        }
        if (pesoText.getText().length() <= 0)
        {
            Log.e("ADSW", "calculaIMC: peso vac�o");

            Toast toast = Toast.makeText(context, R.string.errorPesoVacio, Toast.LENGTH_SHORT);
            toast.show();

            return;
        }

        double altura=0, peso=0;

        try
        {
            altura= Double.parseDouble(alturaText.getText().toString()) / 100.0;
        }
        catch (final NumberFormatException e)
        {
            Toast.makeText(context, R.string.errorAlturaInvalida, Toast.LENGTH_LONG).show();
            return;
        }
        try
        {
            peso= Double.parseDouble(pesoText.getText().toString());
        }
        catch (final NumberFormatException e)
        {
            Toast.makeText(context, R.string.errorPesoInvalido, Toast.LENGTH_LONG).show();
            return;
        }

        Log.v("ADSW", "calculaIMC: peso= " + peso);
        Log.v("ADSW", "calculaIMC: altura= " + altura);

        double IMC= peso / (altura*altura);

        Log.e("ADSW", "ADSW: OJO!! Llamando a calculaIMC: " + IMC);

        imcText.setText(String.format("%.2f", IMC));

        clasifIMCText.setText(clasificacion(IMC));
        //recomendacionText.setText(recomendacion(IMC));
    }

    private String clasificacion(double IMC)
    {
        if (IMC < 18.5)
            return "Insuficiencia ponderal";
        if (IMC <= 24.9)
            return "normal";
        if (IMC >= 40)
            return "Obesidad clase III";
        if (IMC >= 35)
            return "Obesidad clase II";
        if (IMC >= 30)
            return "Obesidad clase I";
        if (IMC >= 25)
            return "Preobesidad";

        return "Intervalo normal";
    }

    private String recomendacion(double IMC)
    {
        if (IMC < 18.5)
            return "Has de ganar peso";
        if (IMC > 30)
            return "consulta con un médico";
        if (IMC > 25)
            return "adelgaza un poco";
        return "Estas estupendo";
    }
}

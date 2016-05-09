package es.upm.dit.adsw.imc_42;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText alturaText;
    private EditText pesoText;
    private TextView imcText;

    private EditText edadText;
    private RadioGroup generoRG;
    private TextView igceText;

    private double IMC; // cached for use in IGCE

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        alturaText = (EditText) findViewById(R.id.editAltura);
        pesoText = (EditText) findViewById(R.id.editPeso);
        imcText = (TextView) findViewById(R.id.textResIMC);

        edadText = (EditText) findViewById(R.id.editEdad);
        generoRG = (RadioGroup) findViewById(R.id.radioGenero);
        igceText = (TextView) findViewById(R.id.textResIGCE);

        Button calcIMCButton = (Button) findViewById(R.id.buttonCalcIMC);

        calcIMCButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                setResult(RESULT_OK);
                calculaIMC();
            }
        });

        Button calcIGCEButton = (Button) findViewById(R.id.buttonCalcIGCE);

        calcIGCEButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                setResult(RESULT_OK);
                calculaIGCE();
            }
        });
    }

    public void calculaIMC(View v)
    {
        calculaIMC();
    }

    private void calculaIMC()
    {
        if (alturaText.getText().length() <= 0)
        {
            Log.i("ADSW", "calculaIMC: altura no positiva");

            Toast toast = Toast.makeText(context, R.string.errorAlturaVacio,
                    Toast.LENGTH_SHORT);
            toast.show();

            return;
        }
        if (pesoText.getText().length() <= 0)
        {
            Log.i("ADSW", "calculaIMC: peso no positiva");

            Toast toast = Toast.makeText(context, R.string.errorPesoVacio,
                    Toast.LENGTH_SHORT);
            toast.show();

            return;
        }

        double altura = 0, peso = 0;

        try
        {
            altura = Double.parseDouble(alturaText.getText().toString()) / 100.0;
        }
        catch (final NumberFormatException e)
        {

        }
        try
        {
            peso = Double.parseDouble(pesoText.getText().toString());
        }
        catch (final NumberFormatException e)
        {

        }

        Log.e("ADSW", "calculaIMC: peso= " + peso);
        Log.e("ADSW", "calculaIMC: altura= " + altura);

        IMC = peso / (altura * altura);

        Log.e("ADSW", "ADSW: OJO!! Llamando a calculaIMC: " + IMC);

        imcText.setText(String.format("%.2f", IMC));
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
            return "consulta con un m�dico";
        if (IMC > 25)
            return "adelgaza un poco";
        return "Estas estupendo";
    }

    private void calculaIGCE()
    {
        Log.e("ADSW", "calculaIGCE: llamando a calculaIMC");

        calculaIMC();

        int genero = -1;
        int selectedId = generoRG.getCheckedRadioButtonId();

        switch (selectedId)
        {
            case R.id.radioButtonHombre:
                genero = 1;
                break;
            case R.id.radioButtonMujer:
                genero = 0;
                break;
            default:
                Toast toast = Toast.makeText(context, R.string.errorGeneroIlegal,
                        Toast.LENGTH_SHORT);
                toast.show();

                return;
        }
        Log.e("ADSW", "el g�nero es: " + genero);

        if (edadText.getText().length() <= 0)
        {
            Log.i("ADSW", "calculaIMC: edad no existe");

            Toast toast = Toast.makeText(context, R.string.errorEdadVacio,
                    Toast.LENGTH_SHORT);
            toast.show();

            return;
        }

        int edad = -1;

        try
        {
            edad = Integer.parseInt(edadText.getText().toString());
        }
        catch (final NumberFormatException e)
        {

        }

        Log.e("ADSW", "calculaIGCE: edad: " + edad);
        if (edad <= 0)
        {
            Toast toast = Toast.makeText(context, R.string.errorEdadIlegal
                    + edad, Toast.LENGTH_SHORT);
            toast.show();

            return;
        }


        if (imcText.getText().length() <= 0)
        {
            Log.e("ADSW", "calculaIMC ha fallado, su widget está vacío");

            Toast toast = Toast.makeText(context, R.string.errorIMCVacio,
                    Toast.LENGTH_SHORT);
            toast.show();

            return;
        }

        double IGCE = -1;
        if (edad < 18)
            IGCE = (1.51 * IMC) - (0.70 * edad) - (3.6 * genero) + 1.4;
        else
            IGCE = (1.20 * IMC) + (0.23 * edad) - (10.8 * genero) - 5.4;

        Log.e("ADSW", "Datos, IMC=" + IMC + " edad: " + edad + " genero: "
                + genero);
        Log.e("ADSW", "El IGCE sale: " + IGCE);

        igceText.setText(String.format("%.2f%%", IGCE));
    }
}

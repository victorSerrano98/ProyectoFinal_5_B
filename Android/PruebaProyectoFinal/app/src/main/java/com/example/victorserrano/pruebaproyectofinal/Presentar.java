package com.example.victorserrano.pruebaproyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Presentar extends AppCompatActivity {
    EditText txtnumero, txtmonto, txtresponsable, txtdescripcion;
    TextView titulo;
    Button btnAceptar;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentar);

        txtnumero = (EditText)findViewById(R.id.txtcuentaPresentarT);
        txtmonto = (EditText)findViewById(R.id.txtmontoPresentarT);
        txtresponsable = (EditText)findViewById(R.id.txtResponsablePresentarT);
        txtdescripcion = (EditText)findViewById(R.id.txtDescripcionPresentarT);

        titulo = (TextView)findViewById(R.id.txttituloPresentarT);
        btnAceptar = (Button) findViewById(R.id.btnRegresar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Presentar.this, MainActivity.class);
                intent.putExtra("user", i.getStringExtra("responsable"));
                startActivity(intent);
            }
        });

        i = getIntent();
        titulo.setText(i.getStringExtra("titulo"));
        txtnumero.setText(i.getStringExtra("numero"));
        txtmonto.setText(i.getStringExtra("monto"));
        txtresponsable.setText(i.getStringExtra("responsable"));
        txtdescripcion.setText(i.getStringExtra("descripcion"));
    }
}

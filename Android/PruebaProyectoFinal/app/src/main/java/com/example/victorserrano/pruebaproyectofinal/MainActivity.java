package com.example.victorserrano.pruebaproyectofinal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.spongycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
import org.spongycastle.operator.InputDecryptorProvider;
import org.spongycastle.operator.OperatorCreationException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnBuscarCedula, btnBuscarCuenta;
    Intent inten;
    String admin = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBuscarCedula = (Button)findViewById(R.id.btnBuscarCedula);
        btnBuscarCuenta = (Button)findViewById(R.id.btnBuscarCuenta);
        btnBuscarCuenta.setOnClickListener(this);
        btnBuscarCedula.setOnClickListener(this);
        inten = getIntent();
        admin=  inten.getStringExtra("user");
        //requestQueue = Volley.newRequestQueue(MainActivity.this);
        //cargarWebService();
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btnBuscarCedula:
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.opcionelegir);
                final Button btnNumero = (Button)dialog.findViewById(R.id.btnOpcionNumero);
                final Button btnCedula = (Button)dialog.findViewById(R.id.btnOpcionCedula);
                btnNumero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent inten  = new Intent(MainActivity.this, BuscarCuentaNumero.class);
                        inten.putExtra("opcion", "numero");
                        inten.putExtra("user", admin);
                        startActivity(inten);
                    }
                });
                btnCedula.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent inten  = new Intent(MainActivity.this, BuscarCuentaNumero.class);
                        inten.putExtra("opcion", "cedula");
                        inten.putExtra("user", inten.getStringExtra("user"));
                        startActivity(inten);
                    }
                });
                dialog.show();
                break;
            case R.id.btnBuscarCuenta :
                intent = new Intent(MainActivity.this, CrearCuenta.class);
                inten.putExtra("user", inten.getStringExtra("user"));
                startActivity(intent);
                break;
        }
    }
}

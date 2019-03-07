package com.example.victorserrano.pruebaproyectofinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class BuscarCuentaNumero extends AppCompatActivity {
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    TextView texto;
    EditText txtBuscarNumero;
    Button btnBuscar;
    Boolean aux =false;
    Intent intent = new Intent();
    Intent inten;
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_cuenta_numero);

        texto = (TextView)findViewById(R.id.txtTexto);
        txtBuscarNumero = (EditText) findViewById(R.id.txtNumero);
        btnBuscar = (Button)findViewById(R.id.btnBuscarNumeroCuenta);
        inten = getIntent();
        if (inten.getStringExtra("opcion").equals("cedula")){
            url = "http://10.20.61.247:8080/ProyectoFinal/api/Clientes/BuscarCedula?cedula=";
        }else{
            url = "http://10.20.61.247:8080/ProyectoFinal/api/Clientes/BuscarNumero?numero=";
        }
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(BuscarCuentaNumero.this, PresentarCuenta.class);
                requestQueue = Volley.newRequestQueue(BuscarCuentaNumero.this);
                cargarWebService();

            }
        });


    }

    private void cargarWebService(){
        progressDialog= new ProgressDialog(BuscarCuentaNumero.this);
        progressDialog.setMessage("CARGANDO DATOS....");
        progressDialog.show();
        final String ur = url + txtBuscarNumero.getText().toString();
        RSA  rsa = new RSA();
        aux = new Boolean(false);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ur,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            intent.putExtra("cuenta", response.toString());
                            intent.putExtra("user", inten.getStringExtra("user"));
                            progressDialog.hide();
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.hide();
                    Toast.makeText(BuscarCuentaNumero.this, "NO EXISTE"+ error.toString() ,Toast.LENGTH_SHORT).show();
                }
            });

        requestQueue.add(jsonObjectRequest);
    }
}
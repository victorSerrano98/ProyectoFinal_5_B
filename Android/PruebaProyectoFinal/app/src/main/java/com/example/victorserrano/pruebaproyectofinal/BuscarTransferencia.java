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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BuscarTransferencia extends AppCompatActivity {
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    EditText txtBuscarNumero;
    Button btnBuscar;
    Intent intent = new Intent();
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion);
        txtBuscarNumero = (EditText) findViewById(R.id.txtBuscarNumeroTransferencia);
        btnBuscar = (Button)findViewById(R.id.btnBuscarCuentaTransferencia);
        intent = getIntent();
        if (intent.getStringExtra("opcion").equals("cedula")){
            url = "http://10.20.61.247:8080/ProyectoFinal/api/Clientes/BuscarCedula?cedula=";
        }else{
            url = "http://10.20.61.247:8080/ProyectoFinal/api/Clientes/BuscarNumero?numero=";
        }
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue = Volley.newRequestQueue( BuscarTransferencia.this);
                buscarCuenta();
            }
        });

    }

    private void buscarCuenta(){
        progressDialog= new ProgressDialog(BuscarTransferencia.this);
        progressDialog.setMessage("CARGANDO DATOS....");
        progressDialog.show();
        final String ur = url +txtBuscarNumero.getText().toString();
        RSA  rsa = new RSA();
        final Intent intent2 = new Intent(BuscarTransferencia.this, Transferencia.class);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ur,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.hide();
                        JSONObject jsonObject = null;
                        intent2.putExtra("cuenta1", intent.getStringExtra("cuenta1"));
                        intent2.putExtra("cuenta2", response.toString());
                        intent2.putExtra("user", intent.getStringExtra("user"));
                        startActivity(intent2);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(BuscarTransferencia.this, "ERROR"+error ,Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonObjectRequest);
    }


}

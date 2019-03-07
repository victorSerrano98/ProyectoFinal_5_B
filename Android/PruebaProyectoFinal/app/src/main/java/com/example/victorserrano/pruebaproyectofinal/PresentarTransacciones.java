package com.example.victorserrano.pruebaproyectofinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PresentarTransacciones extends AppCompatActivity {
    TextView txtTitulo, txtPresentar;
    Intent intent;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentar_transacciones);
        txtTitulo = (TextView)findViewById(R.id.txtTituloTransaccion);
        txtPresentar = (TextView)findViewById(R.id.txtPresentarTrandsaccion);
        intent = getIntent();
        requestQueue = Volley.newRequestQueue(PresentarTransacciones.this);
        cargarWebService();

    }

    private void cargarWebService(){

        progressDialog= new ProgressDialog(PresentarTransacciones.this);
        progressDialog.setMessage("CARGANDO DATOS....");
        progressDialog.show();
        final String url = "http://10.20.61.247:8080/ProyectoFinal/api/Clientes/PresentarTransacciones?numero="+intent.getStringExtra("cuenta");
        RSA  rsa = new RSA();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray json_array = null;
                        try {
                            json_array = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < json_array.length(); i++) {
                            try {
                                Toast.makeText(PresentarTransacciones.this, json_array.getJSONObject(i)+"" ,Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(PresentarTransacciones.this, ""+error ,Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}

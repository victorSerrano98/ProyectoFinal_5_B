package com.example.victorserrano.pruebaproyectofinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class PresentarTodasTransacciones extends AppCompatActivity {
    ListView listView;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Intent intent;
    adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentar_todas_transacciones);
        intent= getIntent();
        listView = (ListView)findViewById(R.id.Lista);
        requestQueue = Volley.newRequestQueue( PresentarTodasTransacciones.this);
        cargarWebService();
    }
    private void cargarWebService(){

        progressDialog= new ProgressDialog(PresentarTodasTransacciones.this);
        progressDialog.setMessage("CARGANDO DATOS....");
        progressDialog.show();
        final String url = "http://10.20.61.247:8080/ProyectoFinal/api/Clientes/PresentarTransacciones?numero="+intent.getStringExtra("cuenta");
        RSA  rsa = new RSA();
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        adapter =  new adapter(PresentarTodasTransacciones.this, response);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(PresentarTodasTransacciones.this, ""+error ,Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}

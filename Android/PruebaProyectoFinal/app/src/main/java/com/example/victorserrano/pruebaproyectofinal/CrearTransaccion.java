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

public class CrearTransaccion extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    EditText txtmonto, txtdescripcion, txtresponsable;
    Button btntransaccion;
    TextView titulo;
    String url = "";
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_transaccion);
        intent = getIntent();
        txtmonto = (EditText)findViewById(R.id.txtMonto);
        txtdescripcion = (EditText)findViewById(R.id.txtDescripcion);
        txtresponsable = (EditText)findViewById(R.id.txtResponsable);
        titulo = (TextView)findViewById(R.id.titulo);
        txtresponsable.setText(intent.getStringExtra("user"));
        txtdescripcion.setText("Se efectuo un "+intent.getStringExtra("tipo"));
        titulo.setText(intent.getStringExtra("tipo"));
        btntransaccion = (Button)findViewById(R.id.btnTransaccion);
        btntransaccion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTransaccion:
                if (intent.getStringExtra("tipo").equals("Deposito")){
                    url = "http://10.20.61.247:8080/ProyectoFinal/api/Clientes/GuardarTransaccionDeposito";
                    //Toast.makeText(CrearTransaccion.this, "DEPOSITO", Toast.LENGTH_LONG).show();
                }
                if (intent.getStringExtra("tipo").equals("Retiro")){
                    url = "http://10.20.61.247:8080/ProyectoFinal/api/Clientes/GuardarTransaccionRetiro";
                }
                requestQueue = Volley.newRequestQueue(CrearTransaccion.this);
                cargarWebService();

                break;
        }
    }

    private void cargarWebService(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        JSONObject transaccion = new JSONObject();
        JSONObject cuenta = new JSONObject();
        try {
            cuenta = new JSONObject(intent.getStringExtra("datos"));
            transaccion.put("cuentaId", cuenta);
            transaccion.put("descripcion", txtdescripcion.getText().toString());
            transaccion.put("fecha", date+"-05:00");
            transaccion.put("responsable", txtresponsable.getText().toString());
            transaccion.put("tipo", intent.getStringExtra("tipo"));
            transaccion.put("valor", txtmonto.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog= new ProgressDialog(CrearTransaccion.this);
        progressDialog.setMessage("CARGANDO DATOS....");
        progressDialog.show();
        RSA  rsa = new RSA();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,transaccion,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(CrearTransaccion.this, "Se ha Efectuado el "+ intent.getStringExtra("tipo") ,Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                        Intent inten = new Intent(CrearTransaccion.this, Presentar.class);
                        try {
                            JSONObject jsonObject = response.getJSONObject("cuentaId");
                            inten.putExtra("titulo", intent.getStringExtra("tipo"))
                                    .putExtra("numero", jsonObject.getString("numero"))
                                    .putExtra("monto", response.getString("valor"))
                                    .putExtra("responsable", response.getString("responsable"))
                                    .putExtra("descripcion", response.getString("descripcion"));
                            startActivity(inten);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(CrearTransaccion.this, error.toString() ,Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}

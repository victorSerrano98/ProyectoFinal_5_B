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

public class Transferencia extends AppCompatActivity {
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    EditText txtmonto, txtdescripcion, txtresponsable;
    Button btnAceptar;
    Intent intent = new Intent();
    JSONObject cuenta1 = new JSONObject();
    JSONObject cuenta2 = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia);
        intent = getIntent();
        txtmonto = (EditText)findViewById(R.id.txtMontoTransferencia);
        txtdescripcion = (EditText)findViewById(R.id.txtDescripcionTransferencia);
        txtresponsable = (EditText)findViewById(R.id.txtResponsableTransaccion);
        btnAceptar = (Button)findViewById(R.id.btnAceptarTransferencia);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog= new ProgressDialog(Transferencia.this);
                progressDialog.setMessage("CARGANDO DATOS....");
                progressDialog.show();
                requestQueue = Volley.newRequestQueue( Transferencia.this);
                transferenciaRetiro();
            }
        });
        try {
            cuenta1 = new JSONObject(intent.getStringExtra("cuenta1"));
            cuenta2 = new JSONObject(intent.getStringExtra("cuenta2"));
            txtdescripcion.setText("Se realizó una transferencia desde el número de cuenta Remitente: "+cuenta1.getString("numero")+" hacia el número de cuenta: "+cuenta2.getString("numero"));
            txtresponsable.setText(intent.getStringExtra("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void transferenciaRetiro(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        JSONObject transaccion = new JSONObject();
        try {
            transaccion.put("cuentaId", cuenta1);
            transaccion.put("descripcion", txtdescripcion.getText().toString());
            transaccion.put("fecha", date+"-05:00");
            transaccion.put("responsable", txtresponsable.getText().toString());
            transaccion.put("tipo", "retiro");
            transaccion.put("valor", txtmonto.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://10.20.61.247:8080/ProyectoFinal/api/Clientes/GuardarTransaccionRetiro";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,transaccion,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        transferenciaDeposito();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(Transferencia.this, error.toString() ,Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void transferenciaDeposito(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        JSONObject transaccion = new JSONObject();

        try {
            transaccion.put("cuentaId", cuenta2);
            transaccion.put("descripcion", txtdescripcion.getText().toString());
            transaccion.put("fecha", date+"-05:00");
            transaccion.put("responsable", txtresponsable.getText().toString());
            transaccion.put("tipo", "deposito");
            transaccion.put("valor", txtmonto.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "http://10.20.61.247:8080/ProyectoFinal/api/Clientes/GuardarTransaccionDeposito";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,transaccion,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent inten = new Intent(Transferencia.this, Presentar.class);
                        try {
                            JSONObject jsonObject = response.getJSONObject("cuentaId");
                            inten.putExtra("titulo", "Transferencia")
                                    .putExtra("numero", jsonObject.getString("numero"))
                                    .putExtra("monto", response.getString("valor"))
                                    .putExtra("responsable", response.getString("responsable"))
                                    .putExtra("descripcion", response.getString("descripcion"));
                            Toast.makeText(Transferencia.this, "Se ha Efectuado la Transferencia " ,Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                            startActivity(inten);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Transferencia.this, error.toString() ,Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}

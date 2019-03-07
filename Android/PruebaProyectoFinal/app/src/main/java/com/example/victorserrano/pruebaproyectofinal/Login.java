package com.example.victorserrano.pruebaproyectofinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText txtusername, txtpasseord;
    Button btnLogin;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtusername = (EditText)findViewById(R.id.txtUsername);
        txtpasseord = (EditText)findViewById(R.id.txtPassword);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        intent.setClass(Login.this, MainActivity.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue = Volley.newRequestQueue(Login.this);
                cargarWebService();
            }
        });
    }
    private void cargarWebService(){
        progressDialog= new ProgressDialog(Login.this);
        progressDialog.setMessage("CARGANDO DATOS....");
        progressDialog.show();
        JSONObject user = new JSONObject();
        try {
            user.put("nombre", txtusername.getText().toString());
            user.put("clave", txtpasseord.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String url = "http://10.20.61.247:8080/ProyectoFinal/api/Clientes/buscarUsuariosData";
        RSA  rsa = new RSA();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,user,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = response.optJSONObject("userId");

                        try {
                            intent.putExtra("user", jsonObject.getString("username"));
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.hide();
                txtusername.setError("Usuario o Incorrecto");
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}

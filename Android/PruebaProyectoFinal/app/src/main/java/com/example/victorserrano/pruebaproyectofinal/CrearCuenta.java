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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CrearCuenta extends AppCompatActivity {
    EditText txtCedula, txtNombres, txtApellidos, txtGenero, txtCorreo, txtCelular, txtDireccion, txtEstadoCivil,
            txtFechaNacimiento, txtCuenta, txtFechaApertua, txtSaldo, txtTipoCuenta, txtTelefono;
    Button botonGuardar;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Intent i;
    String user="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        i=getIntent();
        user = i.getStringExtra("user");
        txtCedula = (EditText)findViewById(R.id.txtCedulaGuardar);
        txtNombres = (EditText)findViewById(R.id.txtNombresGuardar);
        txtApellidos = (EditText)findViewById(R.id.txtApellidosGuardar);
        txtGenero = (EditText)findViewById(R.id.txtGeneroGuardar);
        txtCorreo = (EditText)findViewById(R.id.txtCorreoGuardar);
        txtCelular = (EditText)findViewById(R.id.txtCelulaGuardar);
        txtTelefono = (EditText)findViewById(R.id.txtTelefonoGuardar);
        txtDireccion = (EditText)findViewById(R.id.txtDireccionGuardar);
        txtEstadoCivil = (EditText)findViewById(R.id.txtEstadoCivilGuardar);
        txtFechaNacimiento = (EditText)findViewById(R.id.txtFechaNacimientoGuardar);
        txtCuenta = (EditText)findViewById(R.id.txtNumeorCuentaGuardar);
        txtFechaApertua = (EditText)findViewById(R.id.txtFechaAperturaGuardar);
        txtSaldo = (EditText)findViewById(R.id.txtSaldoGuardar);
        txtTipoCuenta = (EditText)findViewById(R.id.txtTipoCuentaGuardar);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        txtFechaApertua.setText(date);
        botonGuardar = (Button)findViewById(R.id.btnGuardar);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue = Volley.newRequestQueue(CrearCuenta.this);
                cargarWebService();
                Intent intent = new Intent(CrearCuenta.this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

    }

    private void cargarWebService(){
        JSONObject cliente = new JSONObject();
        JSONObject cuenta = new JSONObject();
        try {
            cliente.put("cedula", txtCedula.getText().toString());
            cliente.put("apellidos", txtApellidos.getText().toString());
            cliente.put("nombres", txtNombres.getText().toString());
            cliente.put("celular", txtCelular.getText().toString());
            cliente.put("correo", txtCorreo.getText().toString());
            cliente.put("direccion", txtDireccion.getText().toString());
            cliente.put("estadoCivil", txtEstadoCivil.getText().toString());
            cliente.put("fechaNacimiento", txtFechaNacimiento.getText().toString()+"T00:00:00-05:00");
            cliente.put("genero", txtGenero.getText().toString());
            cliente.put("telefono", txtTelefono.getText().toString());
            cliente.put("estado", true );
            cuenta.put("clienteId", cliente);
            cuenta.put("estado", true);
            cuenta.put("fechaApertura", txtFechaApertua.getText().toString()+"-05:00");
            cuenta.put("numero", txtCuenta.getText().toString());
            cuenta.put("saldo", txtSaldo.getText().toString());
            cuenta.put("tipoCuenta", txtTipoCuenta.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressDialog= new ProgressDialog(CrearCuenta.this);
        progressDialog.setMessage("CARGANDO DATOS....");
        progressDialog.show();
        final String url = "http://10.20.61.247:8080/ProyectoFinal/api/Clientes/GuardarCuenta";
        RSA  rsa = new RSA();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,cuenta,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(CrearCuenta.this, "SE HA GUARDADOX" ,Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}

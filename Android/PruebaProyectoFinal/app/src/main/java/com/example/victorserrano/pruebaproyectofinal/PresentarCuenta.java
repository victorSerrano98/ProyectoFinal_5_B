package com.example.victorserrano.pruebaproyectofinal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class PresentarCuenta extends AppCompatActivity{
    EditText txtIdCliente, txtCedula, txtNombres, txtApellidos, txtGenero, txtCorreo, txtCelular, txtDireccion, txtEstadoCivil,
            txtIdCuenta,txtFechaNacimiento, txtCuenta, txtEstadoCuenta, txtFechaApertua, txtSaldo, txtTipoCuenta, txtTelefono;
    Intent intent;
    Button btnEditar;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String numero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentar_cuenta);
        txtCedula = (EditText)findViewById(R.id.txtCedula);
        txtNombres = (EditText)findViewById(R.id.txtNombres);
        txtApellidos = (EditText)findViewById(R.id.txtApellidos);
        txtGenero = (EditText)findViewById(R.id.txtGenero);
        txtCorreo = (EditText)findViewById(R.id.txtCorreo);
        txtCelular = (EditText)findViewById(R.id.txtCelular);
        txtTelefono = (EditText)findViewById(R.id.txtTelefono);
        txtDireccion = (EditText)findViewById(R.id.txtDireccion);
        txtEstadoCivil = (EditText)findViewById(R.id.txtEstadoCivil);
        txtFechaNacimiento = (EditText)findViewById(R.id.txtFechaNacimiento);
        txtCuenta = (EditText)findViewById(R.id.txtNumeorCuenta);
        txtEstadoCuenta = (EditText)findViewById(R.id.txtEstadoCuenta);
        txtFechaApertua = (EditText)findViewById(R.id.txtFechaApertura);
        txtSaldo = (EditText)findViewById(R.id.txtSaldo);
        txtTipoCuenta = (EditText)findViewById(R.id.txtTipoCuenta);
        txtIdCliente = (EditText)findViewById(R.id.txtIdCliente);
        txtIdCuenta = (EditText)findViewById(R.id.txtIdCuenta);

        btnEditar = (Button)findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue = Volley.newRequestQueue(PresentarCuenta.this);
                cargarWebService();
            }
        });

        intent = getIntent();
        JSONObject json = new JSONObject();
        try {
            json = new JSONObject(intent.getStringExtra("cuenta"));
            JSONObject cliente = json.getJSONObject("clienteId");
            txtCedula.setText(cliente.getString("cedula"));
            txtIdCuenta.setText(cliente.getString("clienteId"));
            txtNombres.setText(cliente.getString("nombres"));
            txtApellidos.setText(cliente.getString("apellidos"));
            txtGenero.setText(cliente.getString("genero"));
            txtCorreo.setText(cliente.getString("correo"));
            txtCelular.setText(cliente.getString("celular"));
            txtTelefono.setText(cliente.getString("telefono"));
            txtDireccion.setText(cliente.getString("direccion"));
            txtEstadoCivil.setText(cliente.getString("estadoCivil"));
            txtFechaNacimiento.setText(cliente.getString("fechaNacimiento"));
            txtCuenta.setText(json.getString("numero"));
            numero = json.getString("numero");
            txtEstadoCuenta.setText(json.getString("estado"));
            txtFechaApertua.setText(json.getString("fechaApertura"));
            txtSaldo.setText(json.getString("saldo"));
            txtTipoCuenta.setText(json.getString("tipoCuenta"));
            txtIdCliente.setText(json.getString("cuentaId"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.opcionEditar:
                //intent = new Intent(Actividad_Navegacion.this, Paramentro.class);
                //startActivity(intent);
                txtCedula.setEnabled(true);
                txtNombres.setEnabled(true);
                txtApellidos.setEnabled(true);
                txtGenero.setEnabled(true);
                txtCorreo.setEnabled(true);
                txtCelular.setEnabled(true);
                txtTelefono.setEnabled(true);
                txtDireccion.setEnabled(true);
                txtEstadoCivil.setEnabled(true);
                txtFechaNacimiento.setEnabled(true);
                btnEditar.setVisibility(View.VISIBLE);
                break;
            case R.id.opcionTransaccion:
                Dialog dialog = new Dialog(PresentarCuenta.this);
                dialog.setContentView(R.layout.dlgretirodeposito);
                final Button btnDeposito = (Button)dialog.findViewById(R.id.btnElegirDeposito);
                final Button btnRetiro = (Button)dialog.findViewById(R.id.btnElegirRetiro);
                final Intent transaccion = new Intent(PresentarCuenta.this, CrearTransaccion.class);

                btnDeposito.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        transaccion.putExtra("datos", crearJSON().toString());
                        transaccion.putExtra("user", intent.getStringExtra("user"));
                        transaccion.putExtra("tipo", "Deposito");
                        startActivity(transaccion);
                    }
                });
                btnRetiro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        transaccion.putExtra("datos", crearJSON().toString());
                        transaccion.putExtra("user", intent.getStringExtra("user"));
                        transaccion.putExtra("tipo", "Retiro");
                        startActivity(transaccion);
                    }
                });
                dialog.show();
                break;
            case R.id.opcionTransferencia:

                Dialog dlg = new Dialog(PresentarCuenta.this);
                dlg.setContentView(R.layout.opcionelegir);
                final Button btnNumero = (Button)dlg.findViewById(R.id.btnOpcionNumero);
                final Button btnCedula = (Button)dlg.findViewById(R.id.btnOpcionCedula);
                btnNumero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Intent transferencia = new Intent(PresentarCuenta.this, BuscarTransferencia.class);
                        transferencia.putExtra("user", intent.getStringExtra("user"));
                        transferencia.putExtra("opcion", "numero");
                        transferencia.putExtra("cuenta1", crearJSON().toString());
                        startActivity(transferencia);
                    }
                });
                btnCedula.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent transferencia = new Intent(PresentarCuenta.this, BuscarTransferencia.class);
                        transferencia.putExtra("user", intent.getStringExtra("user"));
                        transferencia.putExtra("opcion", "cedula");
                        transferencia.putExtra("cuenta1", crearJSON().toString());
                        startActivity(transferencia);
                    }
                });
                dlg.show();

                break;

            case R.id.opcionPresentarT:
                final Intent presentar = new Intent(PresentarCuenta.this, PresentarTodasTransacciones.class);
                JSONObject json = new JSONObject();
                presentar.putExtra("cuenta", numero);
                startActivity(presentar);
                break;
        }
        return true;
    }

    private void cargarWebService(){

        JSONObject cuenta = crearJSON();
        txtCedula.setText(cuenta.toString());
        progressDialog= new ProgressDialog(PresentarCuenta.this);
        progressDialog.setMessage("CARGANDO DATOS....");
        progressDialog.show();
        final String url = "http://10.20.61.247:8080/ProyectoFinal/api/Clientes/EditarCuenta";
        RSA  rsa = new RSA();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,cuenta,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(PresentarCuenta.this, "SE HA EDITADO" ,Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                        finish();
                        startActivity(getIntent());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(PresentarCuenta.this, ""+error ,Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
    private JSONObject crearJSON(){
        JSONObject cliente = new JSONObject();
        JSONObject cuenta = new JSONObject();
        try {
            cliente.put("clienteId", txtIdCliente.getText().toString());
            cliente.put("cedula", txtCedula.getText().toString());
            cliente.put("apellidos", txtApellidos.getText().toString());
            cliente.put("nombres", txtNombres.getText().toString());
            cliente.put("celular", txtCelular.getText().toString());
            cliente.put("correo", txtCorreo.getText().toString());
            cliente.put("direccion", txtDireccion.getText().toString());
            cliente.put("estadoCivil", txtEstadoCivil.getText().toString());
            cliente.put("fechaNacimiento", txtFechaNacimiento.getText().toString());
            cliente.put("genero", txtGenero.getText().toString());
            cliente.put("telefono", txtTelefono.getText().toString());
            cliente.put("estado", true );
            cuenta.put("clienteId", cliente);
            cuenta.put("cuentaId", txtIdCuenta.getText().toString());
            cuenta.put("estado", true);
            cuenta.put("fechaApertura", txtFechaApertua.getText().toString());
            cuenta.put("numero", txtCuenta.getText().toString());
            cuenta.put("saldo", txtSaldo.getText().toString());
            cuenta.put("tipoCuenta", txtTipoCuenta.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cuenta;
    }

}

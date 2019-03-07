package com.example.victorserrano.pruebaproyectofinal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class adapter extends BaseAdapter {
    private Context context;
    private JSONArray lista;

    public adapter(Context context, JSONArray lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return lista.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView = View.inflate(context,R.layout.display, null);
        TextView cajaIsbn = convertView.findViewById(R.id.lblId);
        TextView cajaTitulo = convertView.findViewById(R.id.lblTitulo);
        TextView cajaAutor = convertView.findViewById(R.id.lblAutor);
        TextView cajaMonto = convertView.findViewById(R.id.lblMonto);
        TextView cajaFecha = convertView.findViewById(R.id.lblFecha);

        JSONObject libro = null;
        try {
            libro = lista.getJSONObject(position);
            cajaIsbn.setText(String.valueOf(libro.get("tipo")));
            cajaTitulo.setText(String.valueOf(libro.get("descripcion")));
            cajaAutor.setText(String.valueOf(libro.get("responsable")));
            cajaMonto.setText(String.valueOf(libro.get("valor")));
            cajaFecha.setText(String.valueOf(libro.get("fecha")));
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return convertView;
    }
}

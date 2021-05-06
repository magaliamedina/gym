package com.example.gimnasio_unne.view.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gimnasio_unne.R;
import com.example.gimnasio_unne.model.CuposLibres;
import com.example.gimnasio_unne.model.Personas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdaptadorCuposLibres extends ArrayAdapter<CuposLibres> {
    Context context;
    List<CuposLibres> arrayListCuposLibres;
    Button btnReservarCupoLibre;
    RequestQueue requestQueue;
    TextView tvDescripcionGrupo, tvProfesor, tvDiayHora, tvTotalCuposLibres, tvReservaRealizada, tvListarCuposLibresIdCupolibre;
    String id_grupo, id_cupolibre;
    ScrollView svListaCuposLibres;

    public AdaptadorCuposLibres(@NonNull Context context, List<CuposLibres>arrayListCuposLibres) {
        super(context, R.layout.list_cupos_libres, arrayListCuposLibres);
        this.context = context;
        this.arrayListCuposLibres=arrayListCuposLibres;
    }

    @NonNull
    @Override
    public View getView (int position, @NonNull View convertView, @NonNull ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cupos_libres, null, true);
        tvDescripcionGrupo= view.findViewById(R.id.tvListarCuposLibresGrupo);
        tvProfesor = view.findViewById(R.id.tvListarCuposLibresProfesor);
        tvDiayHora= view.findViewById(R.id.tvListarCuposLibresDiayHora);
        tvTotalCuposLibres = view.findViewById(R.id.tvListarCuposLibresDisponible);
        btnReservarCupoLibre = view.findViewById(R.id.btnReservarCupoLibre);
        svListaCuposLibres = view.findViewById(R.id.svListaCuposLibres);
        tvReservaRealizada= view.findViewById(R.id.tvReservaRealizada);
        tvListarCuposLibresIdCupolibre = view.findViewById(R.id.tvListarCuposLibresIdCupolibre);

        tvDescripcionGrupo.setText(arrayListCuposLibres.get(position).getGrupo_descripcion());
        tvProfesor.setText("Profesor: "+arrayListCuposLibres.get(position).getProfesor_nombreYapellido());
        tvDiayHora.setText("Horario: " +arrayListCuposLibres.get(position).getHorarios_inicio_fin());
        id_grupo = arrayListCuposLibres.get(position).getGrupo_id();
        tvListarCuposLibresIdCupolibre.setText(arrayListCuposLibres.get(position).getId_cupolibre());
        id_cupolibre = arrayListCuposLibres.get(position).getId_cupolibre();
        tvTotalCuposLibres.setText(arrayListCuposLibres.get(position).getCupolibre_total());

        btnReservarCupoLibre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservarCupoLibre("http://medinamagali.com.ar/gimnasio_unne/reservar_cupo_libre.php");
                descontarCupoLibre("http://medinamagali.com.ar/gimnasio_unne/descontar_cupo_libre.php");
                //que no se vea mas la lista de cupos libres al reservar uno
                //svListaCuposLibres.setVisibility(View.INVISIBLE);
                //tvReservaRealizada.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    private void reservarCupoLibre(String URL) {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext().getApplicationContext(), "Reserva exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext().getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //id del alumno (sesion), id del cupo libre, estado, fecha
                Map<String, String> parametros = new HashMap<String, String>();
                //Se trae el dato desde login con la clase SharedPreferences
                parametros.put("alumno_id", getFromSharedPreferences("personas_id"));
                parametros.put("cupo_id", id_cupolibre);
                parametros.put("estado", "1");
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void descontarCupoLibre(String URL) {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext().getApplicationContext(), "Se descontó exitosamente", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext().getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // id del cupo libre, cupototal
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("cupo_id", id_cupolibre);
                //DESCUENTO DEL CUPO TOTAL - 1
                String cupoTotal_String = tvTotalCuposLibres.getText().toString();
                Integer cupoTotal_int= Integer.parseInt(cupoTotal_String);
                cupoTotal_int=cupoTotal_int -1;
                String cupoTotal_string = cupoTotal_int+"";
                parametros.put("cupo_disponible", cupoTotal_string);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private String getFromSharedPreferences(String key) {
        SharedPreferences sharedPref = this.context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sharedPref.getString(key,"");
    }
}

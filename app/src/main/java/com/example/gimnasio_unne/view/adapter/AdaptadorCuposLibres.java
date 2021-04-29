package com.example.gimnasio_unne.view.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.concurrent.atomic.AtomicBoolean;

public class AdaptadorCuposLibres extends ArrayAdapter<CuposLibres> {
    Context context;
    List<CuposLibres> arrayListCuposLibres;
    Button btnReservarCupoLibre;
    RequestQueue requestQueue;
    TextView tvDescripcionGrupo, tvProfesor, tvDiayHora, tvTotalCuposLibres;
    String id_grupo, id_cupolibre;

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

        tvDescripcionGrupo.setText(arrayListCuposLibres.get(position).getGrupo_descripcion());
        tvProfesor.setText("Profesor: "+arrayListCuposLibres.get(position).getProfesor_nombreYapellido());
        tvDiayHora.setText("Horario: " +arrayListCuposLibres.get(position).getHorarios_inicio_fin());
        id_grupo = arrayListCuposLibres.get(position).getGrupo_id();
        id_cupolibre = arrayListCuposLibres.get(position).getId_cupolibre();

        //agregar un nuevo textview para que en la leyenda diga el cupo total es: nro
        //pero sin modificar este tvTotalCuposLibres porque afecta en la alta de la reserva
        tvTotalCuposLibres.setText(arrayListCuposLibres.get(position).getCupolibre_total());

        btnReservarCupoLibre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservarCupoLibre("http://medinamagali.com.ar/gimnasio_unne/reservar_cupo_libre.php");
                //descontar cupolibre
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
                /*DESCUENTO DEL CUPO TOTAL - 1
                String cupoTotal_String = tvTotalCuposLibres.getText().toString();
                Integer cupoTotal_int= Integer.parseInt(cupoTotal_String) - 1;
                String cupoTotal_string = cupoTotal_int+"";
                parametros.put("cupo_disponible", cupoTotal_string);*/
                Personas persona = new Personas();
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

    private String getFromSharedPreferences(String key) {
        SharedPreferences sharedPref = this.context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sharedPref.getString(key,"");
    }
}

package com.example.gimnasio_unne.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gimnasio_unne.R;
import com.example.gimnasio_unne.Utiles;
import com.example.gimnasio_unne.model.Grupos;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class FragmentAltaCuposLibres extends Fragment {

    Spinner spinnerGrupos;
    EditText etTotalCupos;
    TextView tvFechaReserva;
    Button btnguardar;
    private AsyncHttpClient cliente;
    String idgrupo;
    public FragmentAltaCuposLibres() {  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alta_cupos_libres, container, false);
        spinnerGrupos = view.findViewById(R.id.spinnerAltaCuposLibresGrupos);
        etTotalCupos=view.findViewById(R.id.etAltaCuposLibresTotalCupos);
        tvFechaReserva=view.findViewById(R.id.tvAltaCuposLibresFechaReserva);
        btnguardar=view.findViewById(R.id.btnAltaCupoLibre);

        cliente = new AsyncHttpClient();
        llenarSpinnerGrupo();

        tvFechaReserva.setText(Utiles.obtenerFechaActual("GMT-3")
                + " " +Utiles.obtenerHoraActual("GMT-3"));
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                altaCuposLibres("http://medinamagali.com.ar/gimnasio_unne/altacupolibre.php");
            }
        });
        return view;
    }

    private void llenarSpinnerGrupo() {
        String url = "https://medinamagali.com.ar/gimnasio_unne/mostrargrupos.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode== 200) {
                    cargarSpinnerGrupos(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
        });
    }

    private void cargarSpinnerGrupos(String respuesta) {
        final ArrayList<Grupos> listaGrupos = new ArrayList<Grupos>();
        try {
            JSONObject jsonObject = new JSONObject(respuesta);
            String sucess=jsonObject.getString("sucess");
            JSONArray jsonArray=jsonObject.getJSONArray("grupos");
            for (int i= 0; i< jsonArray.length();i++){
                Grupos g = new Grupos();
                JSONObject object= jsonArray.getJSONObject(i);
                g.setDescripcion(object.getString("descripcion"));
                g.setId(object.getString("grupo_id"));
                g.setProf(object.getString("nombres") + " " + object.getString("apellido"));
                g.setHorario("de " + object.getString("hora_inicio") + " a " + object.getString("hora_fin"));
                g.setCupototal(object.getString("total_cupos"));
                //en el metodo tostring de la clase grupo se define lo que se va a mostrar
                listaGrupos.add(g);
            }
            ArrayAdapter<Grupos> grupos = new ArrayAdapter<Grupos>(getActivity().getApplicationContext(), android.R.
                    layout.simple_list_item_1, listaGrupos);
            spinnerGrupos.setAdapter(grupos);
            spinnerGrupos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                //para obtener la posicion del elemento seleccionado en el spinner
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    idgrupo= listaGrupos.get(position).getId();
                    etTotalCupos.setText(listaGrupos.get(position).getCupototal());
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void altaCuposLibres(String URL) {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity().getApplicationContext(), "Alta de grupo exitosa", Toast.LENGTH_SHORT).show();
                etTotalCupos.setText("");
                tvFechaReserva.setText(Utiles.obtenerFechaActual("GMT-3")
                        + " " +Utiles.obtenerHoraActual("GMT-3"));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("grupo_id", idgrupo);
                parametros.put("fecha", tvFechaReserva.getText().toString());
                parametros.put("total", etTotalCupos.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

}

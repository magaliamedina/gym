package com.example.gimnasio_unne;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.gimnasio_unne.model.Grupos;
import com.example.gimnasio_unne.view.fragments.FragmentListarCuposLibres;
import com.example.gimnasio_unne.view.fragments.FragmentPersonalCuposLibres;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class EditarCupoLibre extends AppCompatActivity {
    private AsyncHttpClient cliente;
    TextView tvFechaReserva;
    Spinner spinnerGrupos;
    String idgrupo, idcupolibre;
    EditText etTotalCupos;
    Button btnguardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cupo_libre);
        btnguardar=findViewById(R.id.btnEditarCupoLibre);
        tvFechaReserva=findViewById(R.id.tvEditarCuposLibresFechaReserva);
        etTotalCupos=findViewById(R.id.etEditarCuposLibresTotalCupos);
        spinnerGrupos=findViewById(R.id.spinnerEditarCuposLibresGrupos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        cliente = new AsyncHttpClient();
        llenarSpinnerGrupo();
        Intent intent =getIntent();
        Integer position=intent.getExtras().getInt("position");
        idcupolibre= FragmentPersonalCuposLibres.arrayCuposLibres.get(position).getId_cupolibre();
        tvFechaReserva.setText(FragmentPersonalCuposLibres.arrayCuposLibres.get(position).getFecha_reserva());
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });

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
            ArrayAdapter<Grupos> grupos = new ArrayAdapter<Grupos>(this, android.R.
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

    public void actualizar() {
        final ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Cargando....");
        progressDialog.show();

        StringRequest request=new StringRequest(Request.Method.POST, "http://medinamagali.com.ar/gimnasio_unne/editar_cupolibre.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditarCupoLibre.this, "Modificado exitosamente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), FragmentListarCuposLibres.class));
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditarCupoLibre.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("cupolibre_id", idcupolibre);
                parametros.put("grupo_id", idgrupo);
                parametros.put("total", etTotalCupos.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(EditarCupoLibre.this);
        requestQueue.add(request);
    }
}

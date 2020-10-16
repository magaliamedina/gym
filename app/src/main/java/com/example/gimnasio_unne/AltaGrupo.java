package com.example.gimnasio_unne;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AltaGrupo extends AppCompatActivity {
    EditText etprof1, etprof2, ethorario, ettotalcupos, etdescripcion;
    Button btnguardar;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_grupo);
        etdescripcion = findViewById(R.id.etnombrealtagrupo);
        ethorario = findViewById(R.id.ethorarioaltagrupo);
        etprof1 = findViewById(R.id.etprof1altagrupo);
        etprof2 = findViewById(R.id.etprof2altagrupo);
        ettotalcupos = findViewById(R.id.ettotalcuposaltagrupo);

        btnguardar = findViewById(R.id.btnaltagrupo);

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                altagrupo("http://medinamagali.com.ar/gimnasio_unne/altagrupo.php");
            }
        });

    }

    private void altagrupo(String URL) {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Alta de grupo exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("horario_id", ethorario.getText().toString());
                parametros.put("profesor1_id", etprof1.getText().toString());
                parametros.put("profesor2_id", etprof2.getText().toString());
                parametros.put("total_cupos", ettotalcupos.getText().toString());
                parametros.put("descripcion", etdescripcion.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

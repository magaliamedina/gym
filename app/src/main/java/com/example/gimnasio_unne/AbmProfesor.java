package com.example.gimnasio_unne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AbmProfesor extends AppCompatActivity {
    EditText etNroDoc, etNombre, etApellido, etEmail, etPassword;
    ImageView btnAgregar, btnEditar, btnEliminar, btnBuscar, btnMenu ;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abmprof);
        etNroDoc = findViewById(R.id.txt_dni);
        etNombre = findViewById(R.id.txt_nombres);
        etApellido = findViewById(R.id.txt_apellido);
        etEmail = findViewById(R.id.txt_email);
        etPassword = findViewById(R.id.txt_password);

        btnAgregar = findViewById(R.id.btn_insertar);
        btnEditar = findViewById(R.id.btn_editar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        btnBuscar = findViewById(R.id.btn_buscar);


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio("http://medinamagali.com.ar/gimnasio_unne/insertar_profesor.php");
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio("http://medinamagali.com.ar/gimnasio_unne/insertar_profesor.php");
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar("http://medinamagali.com.ar/gimnasio_unne/buscar_profesor.php?dni="+etNroDoc.getText()+"");
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar("http://medinamagali.com.ar/gimnasio_unne/eliminar_profesor.php");
            }
        });
    }

    private void ejecutarServicio(String URL) {//enviar peticiones al servidor
        //declaramos una peticion
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
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
                parametros.put("NroDocumento", etNroDoc.getText().toString());
                parametros.put("Nombres", etNombre.getText().toString());
                parametros.put("Apellido", etApellido.getText().toString());
                parametros.put("Email", etEmail.getText().toString());
                parametros.put("Password", etPassword.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void buscar(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) { //permite recorrer los datos obtenidos en el webservice
                    try {
                        jsonObject = response.getJSONObject(i);
                        etNombre.setText(jsonObject.getString("Nombres"));
                        etApellido.setText(jsonObject.getString("Apellido"));
                        etEmail.setText(jsonObject.getString("Email"));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR DE CONEXION", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void eliminar(String URL) {//enviar peticiones al servidor
        //declaramos una peticion
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "El profesor fue eliminado", Toast.LENGTH_SHORT).show();
                limpiarFormulario();
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
                parametros.put("NroDocumento", etNroDoc.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void limpiarFormulario() {
        etNroDoc.setText("");
        etNombre.setText("");
        etApellido.setText("");
    }

    public void irMenu (View view) {
        Intent i = new Intent(this, Administrador.class);
        startActivity(i);
    }



}

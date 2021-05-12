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
    ImageView  btnBuscar ;
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

        btnBuscar = findViewById(R.id.btn_buscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar("http://medinamagali.com.ar/gimnasio_unne/buscar_profesor.php?dni="+etNroDoc.getText()+"");
            }
        });

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






}

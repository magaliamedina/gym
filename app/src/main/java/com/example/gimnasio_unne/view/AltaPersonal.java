package com.example.gimnasio_unne.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gimnasio_unne.R;
import com.example.gimnasio_unne.model.Provincias;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class AltaPersonal extends AppCompatActivity {
    EditText etdni,etapellido, etnombres, etestadocivil,etemail, etpassword;
    Spinner spinnerProvincias, spinnerSexos;
    Button btn;
    private AsyncHttpClient cliente;
    private String idprovincia, sexoBD;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_personal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etdni = findViewById(R.id.etdnialtaPersonal);
        etapellido= findViewById(R.id.etapellidoaltaPersonal);
        etnombres= findViewById(R.id.etnombresaltaPersonal);
        etestadocivil= findViewById(R.id.etestadocivilaltaPersonal);
        etemail= findViewById(R.id.etemailaltaPersonal);
        etpassword=findViewById(R.id.etpassaltaPersonal);
        spinnerProvincias = findViewById(R.id.spinnerProvinciaAltaPersonal);
        spinnerSexos=findViewById(R.id.spsexoaltaPersonal);
        btn= findViewById(R.id.btnaltaPersonal);
        cliente = new AsyncHttpClient();

        llenarSpinnerProvincias();

        String [] sexos = {"Masculino", "Femenino", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sexos);
        spinnerSexos.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarCampos()) {
                    altapersona("http://medinamagali.com.ar/gimnasio_unne/altapersona.php");
                }
            }
        });
    }

    public boolean validarCampos() {
        if(etdni.getText().toString().isEmpty()) {
            etdni.setError("Ingrese DNI");
            return false;
        }
        if(etapellido.getText().toString().isEmpty()) {
            etapellido.setError("Ingrese apellido");
            return false;
        }
        if(etnombres.getText().toString().isEmpty()) {
            etnombres.setError("Ingrese nombres");
            return false;
        }
        if (etemail.getText().toString().isEmpty()) {
            etemail.setError("Ingrese email");
            return false;
        }
        if (etpassword.getText().toString().isEmpty())  {
            etpassword.setError("Ingrese contraseña");
            return false;
        }
        return true;
    }

    private void llenarSpinnerProvincias() {
        String url = "https://medinamagali.com.ar/gimnasio_unne/consultarprovincias.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode== 200) {
                    cargarSpinnerProvincias(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
        });
    }

    private void cargarSpinnerProvincias(String respuesta) {
        final ArrayList<Provincias> listaProvincias = new ArrayList<Provincias>();
        try {
            JSONArray jsonArray = new JSONArray(respuesta);

            for (int i= 0; i< jsonArray.length();i++){
                Provincias p = new Provincias();
                //las claves son los nombres de los campos de la BD
                p.setId(jsonArray.getJSONObject(i).getString("provincia_id"));
                p.setProvincia(jsonArray.getJSONObject(i).getString("descripcion"));
                //en el metodo tostring de la clase provincia se define lo que se va a mostrar
                listaProvincias.add(p);
            }
            ArrayAdapter<Provincias> provincias = new ArrayAdapter<Provincias>(this, android.R.
                    layout.simple_dropdown_item_1line, listaProvincias);
            spinnerProvincias.setAdapter(provincias);
            spinnerProvincias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    idprovincia= listaProvincias.get(position).getId();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void altapersona(String URL) {
        String seleccion = spinnerSexos.getSelectedItem().toString();
        if(seleccion.equals("Masculino")) {
            sexoBD= "1";
        }
        if(seleccion.equals("Femenino")) {
            sexoBD="2";
        }
        if(seleccion.equals("Otro")) {
            sexoBD="3";
        }
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.length()==0) {
                    Toast.makeText(getApplicationContext(), "Alta exitosa", Toast.LENGTH_SHORT).show();
                    etdni.setText("");
                    etapellido.setText("");
                    etnombres.setText("");
                    etestadocivil.setText("");
                    etemail.setText("");
                    etpassword.setText("");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Usuario existente con ese DNI", Toast.LENGTH_SHORT).show();
                }
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
                parametros.put("dni", etdni.getText().toString());
                parametros.put("apellido", etapellido.getText().toString());
                parametros.put("nombres", etnombres.getText().toString());
                parametros.put("sexo_id", sexoBD);
                parametros.put("provincia", idprovincia);
                parametros.put("estado", "1");
                parametros.put("estado_civil", etestadocivil.getText().toString());
                parametros.put("usuario_id", "4"); //usuario profesor
                parametros.put("email", etemail.getText().toString());
                parametros.put("password", etpassword.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(AltaPersonal.this);
        requestQueue.add(stringRequest);
    }
}

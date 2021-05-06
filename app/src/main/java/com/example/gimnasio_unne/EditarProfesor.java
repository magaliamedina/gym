package com.example.gimnasio_unne;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.gimnasio_unne.model.Provincias;
import com.example.gimnasio_unne.view.fragments.FragmentListarGrupos;
import com.example.gimnasio_unne.view.fragments.FragmentListarPersonas;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class EditarProfesor extends AppCompatActivity {

    EditText etdni,etapellido, etnombres, etestadocivil,etemail, etpassword;
    TextView tvid;
    Spinner spinnerProvincias, spinnerSexos;
    Button btn;
    private AsyncHttpClient cliente;
    int position;
    private String idprovincia, sexoBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_profesor);
        tvid = findViewById(R.id.tvideditarprofesor);
        etdni = findViewById(R.id.etDnieditarprofesor);
        etapellido= findViewById(R.id.etApellidoeditarprofesor);
        etnombres= findViewById(R.id.etNombreseditarprofesor);
        etestadocivil= findViewById(R.id.etEstadoCivilEditarprofesor);
        etemail= findViewById(R.id.etEmailEditarProfesor);
        etpassword=findViewById(R.id.etPasswordEditarProfesor);
        spinnerProvincias = findViewById(R.id.spinnerProvinciaEditarProfesor);
        spinnerSexos = findViewById(R.id.spinnerSexosEditarProfesor);
        btn= findViewById(R.id.btneditarprofesor);
        cliente = new AsyncHttpClient();

        Intent intent =getIntent();
        position=intent.getExtras().getInt("position");
        tvid.setText(FragmentListarPersonas.persons.get(position).getId());
        etdni.setText(FragmentListarPersonas.persons.get(position).getDni());
        etapellido.setText(FragmentListarPersonas.persons.get(position).getApellido());
        etnombres.setText(FragmentListarPersonas.persons.get(position).getNombres());
        etestadocivil.setText(FragmentListarPersonas.persons.get(position).getEstadoCivil());
        etemail.setText(FragmentListarPersonas.persons.get(position).getEmail());
        etpassword.setText(FragmentListarPersonas.persons.get(position).getPassword());

        String [] sexos = {"Masculino", "Femenino", "Otro"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sexos);
        spinnerSexos.setAdapter(adapter);

        llenarSpinnerProvincias();
    }

    public void actualizar(View view) {
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

        final ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Cargando....");
        progressDialog.show();

        StringRequest request=new StringRequest(Request.Method.POST, "https://medinamagali.com.ar/gimnasio_unne/editarpersona.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditarProfesor.this, "Profesor modificado correctamente", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), FragmentListarPersonas.class));
                finish();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditarProfesor.this, error.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("persona_id", tvid.getText().toString());
                parametros.put("dni", etdni.getText().toString());
                parametros.put("apellido", etapellido.getText().toString());
                parametros.put("nombres", etnombres.getText().toString());
                parametros.put("sexo_id", sexoBD);
                parametros.put("provincia", idprovincia);
                parametros.put("estado", "1");
                parametros.put("estado_civil", etestadocivil.getText().toString());
                parametros.put("email", etemail.getText().toString());
                parametros.put("password", etpassword.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(EditarProfesor.this);
        requestQueue.add(request);
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

}

package com.example.gimnasio_unne.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.gimnasio_unne.AdministradorActivity;
import com.example.gimnasio_unne.AlumnoActivity;
import com.example.gimnasio_unne.R;
import com.example.gimnasio_unne.Utiles;
import com.example.gimnasio_unne.model.Provincias;
import com.example.gimnasio_unne.view.fragments.FragmentListarPersonal;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class EditarPersonal extends AppCompatActivity {
    EditText etdni,etapellido, etnombres, etestadocivil,etemail, etpassword;
    TextView tvid;
    Spinner spinnerProvincias, spinnerSexos;
    Button btn, btn_date_editarPersonal;
    private AsyncHttpClient cliente;
    int position;
    private String idprovincia, sexoBD;
    String [] sexos;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_personal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvid = findViewById(R.id.tvideditarPersonal);
        etdni = findViewById(R.id.etDnieditarPersonal);
        etapellido= findViewById(R.id.etApellidoeditarPersonal);
        etnombres= findViewById(R.id.etNombreseditarPersonal);
        etestadocivil= findViewById(R.id.etEstadoCivilEditarPersonal);
        etemail= findViewById(R.id.etEmailEditarPersonal);
        etpassword=findViewById(R.id.etPasswordEditarPersonal);
        spinnerProvincias = findViewById(R.id.spinnerProvinciaEditarPersonal);
        spinnerSexos = findViewById(R.id.spinnerSexosEditarPersonal);
        btn= findViewById(R.id.btneditarPersonal);
        btn_date_editarPersonal= findViewById(R.id.btn_date_editarPersonal);
        cliente = new AsyncHttpClient();

        Intent intent =getIntent();
        position=intent.getExtras().getInt("position");
        tvid.setText(FragmentListarPersonal.persons.get(position).getId());
        etdni.setText(FragmentListarPersonal.persons.get(position).getDni());
        etapellido.setText(FragmentListarPersonal.persons.get(position).getApellido());
        etnombres.setText(FragmentListarPersonal.persons.get(position).getNombres());
        etestadocivil.setText(FragmentListarPersonal.persons.get(position).getEstadoCivil());
        etemail.setText(FragmentListarPersonal.persons.get(position).getEmail());
        etpassword.setText(FragmentListarPersonal.persons.get(position).getPassword());
        btn_date_editarPersonal.setText(FragmentListarPersonal.persons.get(position).getFechaNac());

        String sexo_guardado= FragmentListarPersonal.persons.get(position).getSexo();
        //lo siguiente es para mostrar en orden como esta guardado
        if(sexo_guardado.equals("2")) {
            sexos= new String [] {"Femenino", "Masculino", "Otro"};
        } else if(sexo_guardado.equals("1")) {
            sexos = new String [] {"Masculino", "Femenino", "Otro"};
        } else {
            sexos =new String []{"Otro", "Masculino", "Femenino"};
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sexos);
        spinnerSexos.setAdapter(adapter);

        llenarSpinnerProvincias();
        try {
            initDatePicker();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        btn_date_editarPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarCampos()) {
                    actualizar("http://medinamagali.com.ar/gimnasio_unne/editarpersona.php");
                }
            }
        });
    }

    private void initDatePicker() throws ParseException {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month= month+1;
                String date= year+"-"+month+"-"+day;
                btn_date_editarPersonal.setText(date);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int style= AlertDialog.THEME_HOLO_LIGHT;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, style,dateSetListener,year,month,day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long dateMillis;
        Date dateHoy;
        dateHoy= sdf.parse(Utiles.obtenerFechaActual("GMT -3"));
        dateMillis=dateHoy.getTime();
        datePickerDialog.getDatePicker().setMaxDate(dateMillis);
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

    public void actualizar(String URL) {
        String seleccion = spinnerSexos.getSelectedItem().toString();
        if(seleccion.equals("Masculino")) {
            sexoBD= "1";
        }
        else if(seleccion.equals("Femenino")) {
            sexoBD="2";
        }
        else if(seleccion.equals("Otro")) {
            sexoBD="3";
        }

        final ProgressDialog progressDialog= new ProgressDialog(this);

        StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.length()==0) {
                    Toast.makeText(EditarPersonal.this, "Personal modificado correctamente", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), AdministradorActivity.class));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Usuario existente con ese DNI", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditarPersonal.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
                parametros.put("fecha_nac", btn_date_editarPersonal.getText().toString()) ;
                parametros.put("provincia", idprovincia);
                parametros.put("estado", "1");
                parametros.put("estado_civil", etestadocivil.getText().toString());
                parametros.put("email", etemail.getText().toString());
                parametros.put("password", etpassword.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(EditarPersonal.this);
        requestQueue.add(request);
    }

    private void llenarSpinnerProvincias() {
        String url = "https://medinamagali.com.ar/gimnasio_unne/consultarprovincias.php?persona_id="+tvid.getText().toString()+"";
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

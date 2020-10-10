package com.example.gimnasio_unne;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class EditarGrupos extends AppCompatActivity {
    EditText etnombre, ethorario, etprof1, etprof2, etcupototal,etid;
    Button btn1;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_grupos);
        etid = findViewById(R.id.etideditargrupo);
        ethorario = findViewById(R.id.ethorarioeditargrupo);
        etnombre = findViewById(R.id.etnombreeditargrupo);
        etprof1 = findViewById(R.id.etprof1editargrupo);
        etprof2 = findViewById(R.id.etprof2editargrupo);
        etcupototal = findViewById(R.id.ettotaleditargrupo);
        btn1= findViewById(R.id.btneditargrupo);

        Intent intent =getIntent();
        position=intent.getExtras().getInt("position");
        etid.setText(FragmentListarGrupos.groups.get(position).getId());
        ethorario.setText(FragmentListarGrupos.groups.get(position).getHorario());
        etnombre.setText(FragmentListarGrupos.groups.get(position).getDescripcion());
        etprof1.setText(FragmentListarGrupos.groups.get(position).getProf1());
        etprof2.setText(FragmentListarGrupos.groups.get(position).getProf2());
        etcupototal.setText(FragmentListarGrupos.groups.get(position).getCupototal());
    }

    public void actualizar(View view) {
        final String nombre = etnombre.getText().toString();
        final String horario = ethorario.getText().toString();
        final String prof1 = etprof1.getText().toString();
        final String prof2 = etprof2.getText().toString();
        final String cupototal = etcupototal.getText().toString();
        final String id = etid.getText().toString();

        final ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Cargando....");
        progressDialog.show();

        StringRequest request=new StringRequest(Request.Method.POST, "https://medinamagali.com.ar/gimnasio_unne/editargrupo.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditarGrupos.this, response, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), FragmentListarGrupos.class));
                finish();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditarGrupos.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();
                params.put("grupo_id", id);
                params.put("horario_id", horario);
                params.put("profesor1_id", prof1);
                params.put("profesor2_id", prof2);
                params.put("total_cupos", cupototal);
                params.put("descripcion", nombre);
                return params;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(EditarGrupos.this);
        requestQueue.add(request);

    }
}

package com.example.gimnasio_unne.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.gimnasio_unne.R;
import com.example.gimnasio_unne.model.Personas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText edtUsuario, edtPassword;
    Button btnLogin;
    String usuario, password, personas_id,apellido,nombres,lu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUsuario = findViewById(R.id.txt_emailLogin);
        edtPassword = findViewById(R.id.txt_passLogin);
        btnLogin = findViewById(R.id.btn_iniciarsesion);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = edtUsuario.getText().toString();
                password = edtPassword.getText().toString();
                if(!usuario.isEmpty() && !password.isEmpty()) {
                    validarUsuario("https://medinamagali.com.ar/gimnasio_unne/validar_usuario.php");
                } else {
                    Toast.makeText(Login.this, "Completa los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void validarUsuario (String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { //nos devuelve la fila encontrada en el servicio web
                if (!response.isEmpty() ) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String usuario_id = jsonObject.getString("usuario_id");
                        personas_id= jsonObject.getString("personas_id");
                        apellido= jsonObject.getString("apellido");
                        nombres = jsonObject.getString("nombres");
                        lu= jsonObject.getString("lu");
                        saveLoginSharedPreferences();
                        edtUsuario.setText("");
                        edtPassword.setText("");
                        //PERFIL ADMINISTRADOR
                        if( usuario_id.equals("1")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        /*if( usuario_id.equals("2")) {
                            Intent intent = new Intent(getApplicationContext(), Administrador.class);
                            startActivity(intent);
                        }*/
                        //PERFIL ESTUDIANTE
                        if( usuario_id.equals("3")) {
                            Intent intent = new Intent(getApplicationContext(), AlumnoActivity.class);
                            startActivity(intent);
                        }
                        //PERFIL PERSONAL ADMINISTRATIVO
                        if( usuario_id.equals("4")) {
                            Intent intent = new Intent(getApplicationContext(), ActivityPersonal.class);
                            startActivity(intent);
                        }
                    }
                        catch (JSONException e) {
                             e.printStackTrace();
                        }

                }
                else {
                    Toast.makeText(Login.this, "Email o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show(); //recomendable luego cambiar con un mensaje para el usuario
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("email",edtUsuario.getText().toString());
                parametros.put("password", edtPassword.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //Datos para activity Reservar
    private void saveLoginSharedPreferences() {
        SharedPreferences sharedPref = getSharedPreferences("personas_id",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("personas_id", personas_id);
        SharedPreferences sharedPref2 = getSharedPreferences("apellido",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putString("apellido", apellido);
        SharedPreferences sharedPref3 = getSharedPreferences("nombres",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = sharedPref3.edit();
        editor3.putString("nombres", nombres);
        SharedPreferences sharedPref4 = getSharedPreferences("lu",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor4 = sharedPref4.edit();
        editor4.putString("lu", lu);
        editor2.commit();
        editor3.commit();
        editor4.commit();
        editor.commit();
    }
}

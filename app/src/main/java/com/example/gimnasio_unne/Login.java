package com.example.gimnasio_unne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText edtUsuario, edtPassword;
    Button btnLogin;
    public static String usuario="", password="", personas_id="",apellido="",nombres="",lu="";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CheckBox cbRecordarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUsuario = findViewById(R.id.txt_emailLogin);
        edtPassword = findViewById(R.id.txt_passLogin);
        btnLogin = findViewById(R.id.btn_iniciarsesion);
        cbRecordarUsuario= findViewById(R.id.cbRecordarUsuario);
        inicializarElementos();
        if(revisarSesion()) {
            if(revisarUsuario().equals("1")) { //PERFIL ADMINISTRADOR
                Intent intent = new Intent(getApplicationContext(), AdministradorActivity.class);
                startActivity(intent);
            }
            if(revisarUsuario().equals("3")) { //PERFIL ESTUDIANTE
                Intent intent = new Intent(getApplicationContext(), AlumnoActivity.class);
                startActivity(intent);
            }
            if(revisarUsuario().equals("4")) { //PERFIL PERSONAL ADMINISTRATIVO
                Intent intent = new Intent(getApplicationContext(), PersonalActivity.class);
                startActivity(intent);
            }
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = edtUsuario.getText().toString();
                password = edtPassword.getText().toString();
                if(!usuario.isEmpty() && !password.isEmpty()) {
                    validarUsuario("https://medinamagali.com.ar/gimnasio_unne/validar_usuario.php");
                } else {
                    edtUsuario.setError("Ingrese usuario");
                    edtPassword.setError("Ingrese contraseña");
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
                        edtUsuario.setText("");
                        edtPassword.setText("");
                        guardarSesion(cbRecordarUsuario.isChecked(), usuario_id);
                        //PERFIL ADMINISTRADOR
                        if( usuario_id.equals("1")) {
                            Intent intent = new Intent(getApplicationContext(), AdministradorActivity.class);
                            startActivity(intent);
                        }
                        //PERFIL ESTUDIANTE
                        else if( usuario_id.equals("3")) {
                            Intent intent = new Intent(getApplicationContext(), AlumnoActivity.class);
                            startActivity(intent);
                        }
                        //PERFIL PERSONAL ADMINISTRATIVO
                        else if( usuario_id.equals("4")) {
                            Intent intent = new Intent(getApplicationContext(), PersonalActivity.class);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this); //instancia de la cola de peticiones de Volley
        requestQueue.add(stringRequest);
    }

    private void inicializarElementos() {
        preferences= this.getSharedPreferences("sesiones",Context.MODE_PRIVATE);
        editor=preferences.edit();
    }

    private boolean revisarSesion() {
        //si devulve false no hace nada
        //si devuelve true va la vista principal
        return this.preferences.getBoolean("sesion",false);
    }

    private String revisarUsuario() {
        return this.preferences.getString("tipo_usuario","");
    }

    private void guardarSesion(boolean checked, String tipousuario) {
        editor.putBoolean("sesion",checked);
        editor.putString("tipo_usuario", tipousuario);
        editor.apply();
    }

}

package com.example.gimnasio_unne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AltaGrupos extends AppCompatActivity {
    private EditText et1, et2;
    private Spinner spinner_prof;
    private Button btn_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_grupos);
        et1 = findViewById(R.id.txt_grupos_abm);
        btn_menu = findViewById(R.id.btn_irmenu2);
        spinner_prof = findViewById(R.id.spinner_profesor);
        String[] opciones = {"Juan Morales", "Susana Gómez", "Antonio Benitez"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_profesores, opciones);
        spinner_prof.setAdapter(adapter);
    }

    public void irMenu (View view) {
        Intent i = new Intent(this, Administrador.class);
        startActivity(i);
    }
}

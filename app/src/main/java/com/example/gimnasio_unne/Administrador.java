package com.example.gimnasio_unne;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class Administrador extends AppCompatActivity {
    Button btn1, btn2, btn3;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btn1 = findViewById(R.id.btn_iraltagrupo);
        btn2 = findViewById(R.id.btn_iraltaprof);

        toolbar= findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }



    public void irAltaGrupo (View view) {
        Intent i = new Intent(this, AltaGrupo.class);
        startActivity(i);
    }

    public void irAltaProf (View view) {
        Intent i = new Intent(this, AbmProfesor.class);
        startActivity(i);
    }
}

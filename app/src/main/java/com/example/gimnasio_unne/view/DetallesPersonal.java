package com.example.gimnasio_unne.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gimnasio_unne.R;
import com.example.gimnasio_unne.view.fragments.FragmentListarPersonal;

public class DetallesPersonal extends AppCompatActivity {
    TextView tvid, tvdni, tvapellido, tvnombres, tvsexo, tvfechaNac, tvlocalidad, tvprovincia, tvestado,tvestadocivil,
            tvemail;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_personal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvid = findViewById(R.id.txtidpersonaldetalle);
        tvdni = findViewById(R.id.txtdnipersonaldetalle);
        tvapellido = findViewById(R.id.txtapellidopersonaldetalle);
        tvnombres = findViewById(R.id.txtnombrespersonaldetalle);
        tvsexo = findViewById(R.id.txtsexopersonaldetalle);
        tvfechaNac = findViewById(R.id.txtfechnacpersonaldetalle);
        //tvlocalidad = findViewById(R.id.txtlocalidadpersonaldetalle);
        tvprovincia = findViewById(R.id.txtprovinciapersonaldetalle);
        tvestado = findViewById(R.id.txtestadopersonaldetalle);
        tvestadocivil = findViewById(R.id.txtestadocivilpersonaldetalle);
        tvemail = findViewById(R.id.txtemailpersonaldetalle);

        //recibimos los parametros de Home
        Intent intent=getIntent();
        position= intent.getExtras().getInt("position");

        tvid.setText("ID " + FragmentListarPersonal.persons.get(position).getId());
        tvdni.setText("DNI " + FragmentListarPersonal.persons.get(position).getDni());
        tvapellido.setText("Apellido " + FragmentListarPersonal.persons.get(position).getApellido());
        tvnombres.setText("Nombres " + FragmentListarPersonal.persons.get(position).getNombres());
        if (FragmentListarPersonal.persons.get(position).getSexo().equals("1")) {
            tvsexo.setText("Sexo: Masculino");
        } else if (FragmentListarPersonal.persons.get(position).getSexo().equals("2")) {
            tvsexo.setText("Sexo: Femenino");
        } else if (FragmentListarPersonal.persons.get(position).getSexo().equals("3")){
            tvsexo.setText("Sexo: Otro");
        }
        tvfechaNac.setText("Fecha de nacimiento " + FragmentListarPersonal.persons.get(position).getFechaNac());
        tvlocalidad.setText("Localidad: " + FragmentListarPersonal.persons.get(position).getLocalidad());
        tvprovincia.setText("Provincia: " + FragmentListarPersonal.persons.get(position).getProvincia());
        if (FragmentListarPersonal.persons.get(position).getEstado().equals("1")) {
            tvestado.setText("Estado: Activo");
            tvestado.setTextColor(Color.GREEN);
        } else {
            tvestado.setText("Estado: Inactivo");
            tvestado.setTextColor(Color.RED);
        }
        tvestadocivil.setText("Estado civil: " + FragmentListarPersonal.persons.get(position).getEstadoCivil());
        tvemail.setText("Email: " + FragmentListarPersonal.persons.get(position).getEmail());
    }
}

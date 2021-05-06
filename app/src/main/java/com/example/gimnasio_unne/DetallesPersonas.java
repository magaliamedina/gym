package com.example.gimnasio_unne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gimnasio_unne.view.fragments.FragmentListarPersonas;

//no muestra la contraseña
public class DetallesPersonas extends AppCompatActivity {
    TextView tvid, tvdni, tvapellido, tvnombres, tvsexo, tvfechaNac, tvlocalidad, tvprovincia, tvestado,tvestadocivil,
     tvusuarioId, tvemail;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_personas);
        tvid = findViewById(R.id.txtidpersonadetalle);
        tvdni = findViewById(R.id.txtdnipersonadetalle);
        tvapellido = findViewById(R.id.txtapellidopersonadetalle);
        tvnombres = findViewById(R.id.txtnombrespersonadetalle);
        tvsexo = findViewById(R.id.txtsexopersonadetalle);
        tvfechaNac = findViewById(R.id.txtfechnacpersonadetalle);
        tvlocalidad = findViewById(R.id.txtlocalidadpersonadetalle);
        tvprovincia = findViewById(R.id.txtprovinciapersonadetalle);
        tvestado = findViewById(R.id.txtestadopersonadetalle);
        tvestadocivil = findViewById(R.id.txtestadocivilpersonadetalle);
        tvusuarioId = findViewById(R.id.txtusuariopersonadetalle);
        tvemail = findViewById(R.id.txtemailpersonadetalle);


        //recibimos los parametros de Home
        Intent intent=getIntent();
        position= intent.getExtras().getInt("position");

        tvid.setText("ID " + FragmentListarPersonas.persons.get(position).getId());
        tvdni.setText("DNI " + FragmentListarPersonas.persons.get(position).getDni());
        tvapellido.setText("Apellido " + FragmentListarPersonas.persons.get(position).getApellido());
        tvnombres.setText("Nombres " + FragmentListarPersonas.persons.get(position).getNombres());
        if (FragmentListarPersonas.persons.get(position).getSexo().equals("1")) {
            tvsexo.setText("Sexo: Masculino");
        } else if (FragmentListarPersonas.persons.get(position).getSexo().equals("2")) {
            tvsexo.setText("Sexo: Femenino");
        } else if (FragmentListarPersonas.persons.get(position).getSexo().equals("3")){
            tvsexo.setText("Sexo: Otro");
        }
        tvfechaNac.setText("Fecha de nacimiento " + FragmentListarPersonas.persons.get(position).getFechaNac());
        tvlocalidad.setText("Localidad: " + FragmentListarPersonas.persons.get(position).getLocalidad());
        tvprovincia.setText("Provincia: " + FragmentListarPersonas.persons.get(position).getProvincia());
        if (FragmentListarPersonas.persons.get(position).getEstado().equals("1")) {
            tvestado.setText("Estado: Activo");
        } else {
            tvestado.setText("Estado: Inactivo");
        }
        tvestadocivil.setText("Estado civil: " + FragmentListarPersonas.persons.get(position).getEstadoCivil());
        tvusuarioId.setText("Tipo de usuario: " + FragmentListarPersonas.persons.get(position).getUsuarioId());
        tvemail.setText("Email: " + FragmentListarPersonas.persons.get(position).getEmail());
    }
}

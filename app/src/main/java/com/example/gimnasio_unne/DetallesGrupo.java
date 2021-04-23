package com.example.gimnasio_unne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gimnasio_unne.view.fragments.FragmentListarGrupos;

public class DetallesGrupo extends AppCompatActivity {
    TextView tvnombre, tvprof1, tvprof2, tvtcupototal,tvid, tvhorario;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_grupo);
        tvid = findViewById(R.id.txtid);
        tvhorario = findViewById(R.id.txthorariogrupodetalle);
        tvnombre = findViewById(R.id.txtnombre);
        tvprof1 = findViewById(R.id.txtprof1);
        tvprof2 = findViewById(R.id.txtprof2);
        tvtcupototal = findViewById(R.id.txtcupototal);

        //recibimos los parametros de Home
        Intent intent=getIntent();
        position= intent.getExtras().getInt("position");

        tvid.setText("ID " + FragmentListarGrupos.groups.get(position).getId());
        tvhorario.setText("Horario " + FragmentListarGrupos.groups.get(position).getHorario());
        tvnombre.setText("Nombre " + FragmentListarGrupos.groups.get(position).getDescripcion());
        tvprof1.setText("Profesor " + FragmentListarGrupos.groups.get(position).getProf1());
        tvprof2.setText("Profesor " + FragmentListarGrupos.groups.get(position).getProf2());
        tvtcupototal.setText("Cupo total " + FragmentListarGrupos.groups.get(position).getCupototal());
    }
}

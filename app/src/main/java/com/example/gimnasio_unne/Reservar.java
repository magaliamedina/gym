package com.example.gimnasio_unne;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class Reservar extends AppCompatActivity {
    TextView tv_fecha, tv_hora;
    Button btn_fecha, btn_hora, btn_menu;
    private int dia, mes, ano, hora, minutos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);
        tv_fecha = findViewById(R.id.txt_fecha);
        tv_hora = findViewById(R.id.txt_hora);
        btn_fecha = findViewById(R.id.btn_fecha);
        btn_hora = findViewById(R.id.btn_hora);
        btn_menu = findViewById(R.id.btn_iramenu3);

        btn_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btn_fecha) {
                    final Calendar c = Calendar.getInstance();
                    dia = c.get(Calendar.DAY_OF_MONTH);
                    mes = c.get(Calendar.MONTH);
                    ano = c.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(Reservar.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            //mostrar la fecha que selecciona el usuario en el TextView
                            tv_fecha.setText(dayOfMonth+"/"+(month)+"/"+year);
                        }
                    },ano,mes,dia);
                    datePickerDialog.show();
                }
            }
        });

        btn_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btn_hora){
                    final Calendar c = Calendar.getInstance();
                    hora = c.get(Calendar.HOUR_OF_DAY);
                    minutos = c.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(Reservar.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            tv_hora.setText(hourOfDay+":"+minute);
                        }
                    },hora, minutos, false);
                    timePickerDialog.show();
                }
            }
        });

    }

    public void irMenu (View view) {
        Intent i = new Intent(this, Administrador.class);
        startActivity(i);
    }
}

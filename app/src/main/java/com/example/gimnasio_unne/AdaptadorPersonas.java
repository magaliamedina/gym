package com.example.gimnasio_unne;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class AdaptadorPersonas extends ArrayAdapter<Personas> {

    Context context;
    List<Personas> arrayListPersons;
    public AdaptadorPersonas(@NonNull Context context, List<Personas>arrayListPersons) {
        super(context, R.layout.list_personas, arrayListPersons);

        this.context = context;
        this.arrayListPersons=arrayListPersons;
    }

    @NonNull
    @Override
    public View getView (int position, @NonNull View convertView, @NonNull ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_personas, null, true);
        TextView tvid= view.findViewById(R.id.tvid);
        TextView tvpersona = view.findViewById(R.id.tvpersona);

        tvid.setText(arrayListPersons.get(position).getId());
        tvpersona.setText(arrayListPersons.get(position).getApellido()+" "+arrayListPersons.get(position).getNombres());
        return view;

    }
}

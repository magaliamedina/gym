package com.example.gimnasio_unne;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class Adaptador extends ArrayAdapter<Grupos> {
    Context context;
    List<Grupos>arrayListGroups;
    public Adaptador(@NonNull Context context, List<Grupos>arrayListGroups) {
        super(context, R.layout.list_grupos, arrayListGroups);

        this.context = context;
        this.arrayListGroups=arrayListGroups;
    }

    @NonNull
    @Override
    public View getView (int position, @NonNull View convertView, @NonNull ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_grupos, null, true);
        TextView tvid= view.findViewById(R.id.tvid);
        TextView tvgrupo = view.findViewById(R.id.tvgrupo);

        tvid.setText(arrayListGroups.get(position).getId());
        tvgrupo.setText(arrayListGroups.get(position).getDescripcion());
        return view;

    }
}

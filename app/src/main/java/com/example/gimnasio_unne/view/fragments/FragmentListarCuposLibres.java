package com.example.gimnasio_unne.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gimnasio_unne.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class FragmentListarCuposLibres extends Fragment {

    public FragmentListarCuposLibres() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_cupos_libres, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fabcuposlibres);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        return view;
    }
}

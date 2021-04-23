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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listar_cupos_libres, container, false);
    }
}

package com.example.gimnasio_unne.view.fragments;

import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.gimnasio_unne.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class FragmentPieChart extends Fragment {
    private PieChart pieChart;

    public FragmentPieChart() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_reportes, container, false);
        pieChart=view.findViewById(R.id.pieChartEdadAlumnos);
        crearGraficoPastel();
        return view;
    }

    private void crearGraficoPastel() {
        Description description = new Description();
        description.setText("grafico de pastel");
        pieChart.setDescription(description);
        ArrayList<PieEntry> pieEntries=new ArrayList<>();
        //se puede traer de un json
        pieEntries.add(new PieEntry(2, 3));
        pieEntries.add(new PieEntry(3, 8));
        pieEntries.add(new PieEntry(6, 7));

        PieDataSet pieDataSet=new PieDataSet(pieEntries,"texto descriptivo");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData= new PieData(pieDataSet);
        pieChart.setData(pieData);

    }
}

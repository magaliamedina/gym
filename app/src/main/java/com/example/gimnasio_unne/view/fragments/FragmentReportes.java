package com.example.gimnasio_unne.view.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gimnasio_unne.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class FragmentReportes extends Fragment {

    private PieChart pieChart;
    private BarChart barChart;

   /*private String[]months= new String[] {"Enero","Febrero","Marzo","Abril","Mayo"};
    private int[]sale= new int[] {25,30,28,10,15};
    private int[]colors= new int[] {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE};*/

    public FragmentReportes() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_reportes, container, false);
        pieChart=view.findViewById(R.id.pieChartEdadAlumnos);
        barChart=view.findViewById(R.id.barChartInasistencias);
        //createCharts();
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

   /*private Chart getSameChart(Chart chart, String description, int textColor, int background,int animateY) {
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(background);
        chart.animateY(animateY);
        legend(chart);
        return  chart;
    }

    private void legend(Chart chart) {
        Legend legend= chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE); //por defecto son cuadrados
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER); //por defecto es a la izquierda
        //datos que van adentro de la leyenda
        ArrayList<LegendEntry>entries= new ArrayList<>();
        for (int i=0; i<months.length;i++) {
            LegendEntry entry= new LegendEntry();
            entry.formColor=colors[i];
            entry.label=months[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }

    private ArrayList<BarEntry> getBarEntries() {
        ArrayList<BarEntry>entries= new ArrayList<>();
        for (int i=0; i<sale.length;i++)
            //colocar los datos en el eje X e Y
            entries.add(new BarEntry(i, sale[i]));
        return entries;
    }

    private ArrayList<PieEntry> getPieEntries() {
        ArrayList<PieEntry>entries= new ArrayList<>();
        for (int i=0; i<sale.length;i++)
            //colocar los valores del circulo
            entries.add(new PieEntry(sale[i]));
        return entries;
    }

    //colocar los nombres del eje x
    private void axisX(XAxis axis) {
        axis.setGranularityEnabled(true); //las barras se adecuan a los meses
        axis.setPosition(XAxis.XAxisPosition.BOTTOM); //que se coloque abajo
        axis.setValueFormatter(new IndexAxisValueFormatter(months));
        //desactivar titulos en el eje X:
        axis.setEnabled(false);
    }

    private void axisLeft(YAxis axis) {
        //agregar conteo de eje Y hasta 30
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0); //que inicie desde 0
    }

    private void axisRight(YAxis axis) {
        axis.setEnabled(false); //que no aparezca en el lado derecho
    }

    public void createCharts() {
        barChart=(BarChart) getSameChart(barChart, "Series", Color.RED, Color.CYAN, 3000);
        barChart.setDrawGridBackground(true); //mostrar las lineas
        barChart.setDrawBarShadow(true); //sombreado arriba de las barras
        barChart.setData(getBarData());
        barChart.invalidate();
        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
        barChart.getLegend().setEnabled(false);

        //personalizar la grafica de tortas
        pieChart=(PieChart)getSameChart(pieChart, "Ventas",Color.GRAY, Color.MAGENTA, 3000);
        pieChart.setHoleRadius(10);//radio del circulo
        pieChart.setTransparentCircleRadius(12);
        pieChart.setData(getPieData());
        pieChart.invalidate();
        //pieChart.setDrawHoleEnabled(false); //no se mostrara el circulo enla grafica

    }

    private DataSet getData(DataSet dataSet) {
        //personalizar los datos de la porcion de la torta y personalizar las barras
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE); //color de la fuente
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private BarData getBarData() {
        BarDataSet barDataSet=(BarDataSet)getData(new BarDataSet(getBarEntries(), ""));
        barDataSet.setBarShadowColor(Color.GRAY); //color de la sombra
        BarData barData=new BarData(barDataSet);
        barData.setBarWidth(0.45f); //ancho de las barras
        return barData;
    }

    private PieData getPieData() {
        PieDataSet pieDataSet=(PieDataSet)getData(new PieDataSet(getPieEntries(), ""));
        pieDataSet.setSliceSpace(2); //separacion de las lineas de la torta
        pieDataSet.setValueFormatter(new PercentFormatter()); //que se muestre en porcentaje
        return new PieData(pieDataSet);
    }*/
}

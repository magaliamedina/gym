package com.example.gimnasio_unne.view.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gimnasio_unne.R;
import com.example.gimnasio_unne.model.CuposLibres;
import com.example.gimnasio_unne.view.adapter.AdaptadorCuposLibres;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class FragmentListarCuposLibres extends Fragment {

    private ListView list;
    public static ArrayList<CuposLibres> arrayCuposLibres= new ArrayList<>();
    String url = "https://medinamagali.com.ar/gimnasio_unne/listarcuposlibres.php";
    AdaptadorCuposLibres adaptador;
    CuposLibres cuposLibres;

    public FragmentListarCuposLibres() {  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_listar_cupos_libres, container, false);

        list = view.findViewById(R.id.lvListarCuposLibres);

        adaptador= new AdaptadorCuposLibres(getActivity().getApplicationContext(), arrayCuposLibres);
        list.setAdapter(adaptador);

        mostrarDatos();

        return view;
    }

    public void mostrarDatos() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayCuposLibres.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String sucess=jsonObject.getString("sucess");
                    JSONArray jsonArray=jsonObject.getJSONArray("cuposlibres");
                    if (sucess.equals("1")) {
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject object= jsonArray.getJSONObject(i);
                            String id_cupolibre= object.getString("id_cupolibre");
                            String grupo_descripcion= object.getString("grupo_descripcion");
                            String nombres = object.getString("personas_nombre");
                            String apellido = object.getString("personas_apellido");
                            String cupolibre_total = object.getString("cupolibre_total");
                            String hora_inicio = object.getString("horarios_hora_inicio");
                            String hora_fin = object.getString("horarios_hora_fin");
                            String id_grupo = object.getString("grupo_id");
                            cuposLibres = new CuposLibres(id_cupolibre, grupo_descripcion, nombres+" " + apellido,
                                    cupolibre_total,"de "+hora_inicio+" a " +  hora_fin, id_grupo);
                            arrayCuposLibres.add(cuposLibres);
                            adaptador.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);
    }



}

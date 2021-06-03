package com.example.gimnasio_unne.view.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gimnasio_unne.AdministradorActivity;
import com.example.gimnasio_unne.R;
import com.example.gimnasio_unne.model.Horarios;
import com.example.gimnasio_unne.view.AltaHorario;
import com.example.gimnasio_unne.view.adapter.Adaptador;
import com.example.gimnasio_unne.view.adapter.AdaptadorHorarios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentHorarios extends Fragment {
    private ListView list;
    AdaptadorHorarios adaptador;
    public static ArrayList<Horarios> horariosArrayList= new ArrayList<>();
    String url="https://medinamagali.com.ar/gimnasio_unne/listar_horarios.php";
    Horarios horarios;
    public FragmentHorarios() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_horarios, container, false);
        list = view.findViewById(R.id.listview);

        FloatingActionButton fab = view.findViewById(R.id.fabHorarios);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplication(), AltaHorario.class);
                startActivity(intent);
            }
        });

        adaptador= new AdaptadorHorarios(getActivity().getApplicationContext(), horariosArrayList);
        list.setAdapter(adaptador);

        //items para editar, eliminar y ver detalles
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog=new ProgressDialog(view.getContext());

                CharSequence[] dialogoItem={"Cambiar de estado"};
                builder.setTitle("Horario: de " + horariosArrayList.get(position).getHoraInicio() +" a " + horariosArrayList.get(position).getHoraFin());
                builder.setItems(dialogoItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0:
                                String p_estado;
                                if(horariosArrayList.get(position).getEstado().equals("1")) {
                                    p_estado="0";
                                } else {
                                    p_estado="1";
                                }
                                cambiarEstado(horariosArrayList.get(position).getId(), p_estado);
                                break;

                        }
                    }
                });
                builder.create().show();
            }
        });
        mostrarDatos();
        return view;
    }

    public void mostrarDatos() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                horariosArrayList.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("entrahorario", response);
                    for (int i= 0; i< jsonArray.length();i++){
                        String id= jsonArray.getJSONObject(i).getString("horario_id");
                        String hora_inicio = jsonArray.getJSONObject(i).getString("hora_inicio");
                        String hora_fin = jsonArray.getJSONObject(i).getString("hora_fin");
                        String estado = jsonArray.getJSONObject(i).getString("estado");
                        horarios = new Horarios(id, hora_inicio,hora_fin,  estado);
                        horariosArrayList.add(horarios);
                        adaptador.notifyDataSetChanged();
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

    public void cambiarEstado(final String id, final String p_estado) {
        StringRequest request=new StringRequest(Request.Method.POST, "https://medinamagali.com.ar/gimnasio_unne/update_horario.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity().getApplicationContext(), "Modificado exitosamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), AdministradorActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();
                params.put("horario_id", id);
                params.put("estado", p_estado);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);
    }
}

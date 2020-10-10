package com.example.gimnasio_unne;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListarPersonas extends Fragment {
    private ListView list;
    AdaptadorPersonas adaptador;
    public static ArrayList<Personas> persons= new ArrayList<>();
    String url="https://medinamagali.com.ar/gimnasio_unne/mostrarpersonas.php";
    Personas personas;
    public FragmentListarPersonas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_personas, container, false);

        list = view.findViewById(R.id.listviewpersonas);
        //Ver CONTEXTO DE UN FRAGMENT
        adaptador= new AdaptadorPersonas(getActivity().getApplicationContext(), persons);
        list.setAdapter(adaptador);

        //items para editar, eliminar y ver detalles
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog=new ProgressDialog(view.getContext());

                CharSequence[] dialogoItem={"Ver datos","Editar datos", "Eliminar datos"};
                builder.setTitle(persons.get(position).getApellido()+" "+persons.get(position).getNombres());
                builder.setItems(dialogoItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {

                            case 0:
                               // pasamos position para poder recibir en detalles
                                startActivity(new Intent(getActivity().getApplicationContext(), DetallesPersonas.class)
                                        .putExtra("position",position));
                                break;
                            case 1:
                                /*pasamos position para poder recibir en editar
                                startActivity(new Intent(getActivity().getApplicationContext(), EditarPersonas.class)
                                        .putExtra("position",position));*/
                                break;
                            case 2:
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
                persons.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String sucess=jsonObject.getString("sucess");
                    JSONArray jsonArray=jsonObject.getJSONArray("personas");
                    if (sucess.equals("1")) {
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject object= jsonArray.getJSONObject(i);
                            String id= object.getString("personas_id");
                            String dni = object.getString("dni");
                            String apellido = object.getString("apellido");
                            String nombres = object.getString("nombres");
                            String sexo = object.getString("sexo_id");
                            String fechaNac = object.getString("fecha_nacimiento");
                            String localidad = object.getString("localidad");
                            String provincia = object.getString("provincia");
                            String estado = object.getString("estado");
                            String estadoCivil = object.getString("estado_civil");
                            String usuario_id = object.getString("usuario_id");
                            String email = object.getString("email");
                            String password = object.getString("pass");
                            String lu = object.getString("lu");
                            personas = new Personas(id, dni, apellido, nombres, sexo, fechaNac, localidad, provincia,
                            estado, estadoCivil, usuario_id, email, password,lu);
                            persons.add(personas);
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

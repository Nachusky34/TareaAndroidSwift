package com.example.tareafinal.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tareafinal.R;
import com.example.tareafinal.adaptadores.AdaptadorTienda;
import com.example.tareafinal.db.Ordenador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoTienda#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoTienda extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView rvTienda;
    AdaptadorTienda tiendaAdapter;
    List<Ordenador> listaOrdenadoresTienda;
    Switch switchLayout;
    FirebaseDatabase database;
    DatabaseReference dbReference; //para la referencia de la base de datos de Firebase

    public FragmentoTienda() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoTienda.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoTienda newInstance(String param1, String param2) {
        FragmentoTienda fragment = new FragmentoTienda();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tienda, container, false);

        rvTienda = view.findViewById(R.id.rv_tienda);
        switchLayout = view.findViewById(R.id.switch_tipolayout);

        boolean estadoSwitch = switchLayout.isChecked();

        // para saber el tipo de layout segun el estado del switch
        if (estadoSwitch) {
            rvTienda.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            rvTienda.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        listaOrdenadoresTienda = new ArrayList<>();
        tiendaAdapter = new AdaptadorTienda(listaOrdenadoresTienda, estadoSwitch); // hay que pasarle el estado del switch al adaptador

        rvTienda.setAdapter(tiendaAdapter);

        database = FirebaseDatabase.getInstance("https://pcera-2b2f4-default-rtdb.europe-west1.firebasedatabase.app/");
        dbReference = database.getReference("productos");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaOrdenadoresTienda.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Ordenador pc = ds.getValue(Ordenador.class);
                    if (pc != null) {
                        listaOrdenadoresTienda.add(pc);
                        Log.d("FirebaseData", "Producto añadido: " + pc.getNombre() + " - " + pc.getPrecio());
                    }
                }
                tiendaAdapter.notifyDataSetChanged();
                Log.d("FirebaseData", "Total productos cargados: " + listaOrdenadoresTienda.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error al cargar los datos", Toast.LENGTH_SHORT).show();
            }
        });




        switchLayout.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                rvTienda.setLayoutManager(new GridLayoutManager(getContext(), 2));
            } else{
                rvTienda.setLayoutManager(new LinearLayoutManager(getContext()));
            }
            tiendaAdapter.setEstaMarcado(isChecked); // Actualiza el adaptador
            tiendaAdapter.notifyDataSetChanged(); // Refrescar adaptador para que use el nuevo layout
        });

        return view;

    }

}
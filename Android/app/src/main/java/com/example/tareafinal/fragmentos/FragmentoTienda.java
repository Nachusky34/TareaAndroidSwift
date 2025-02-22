package com.example.tareafinal.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tareafinal.R;
import com.example.tareafinal.adaptadores.AdaptadorTienda;
import com.example.tareafinal.db.Ordenador;

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

    // TODO: Rename and change types of parameters
    RecyclerView rv;
    AdaptadorTienda tiendaAdapter;
    List<Ordenador> listaOrdenadores;
    // DatabaseReference dbReference; para la referencia de la base de datos de Firebase

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

        rv = view.findViewById(R.id.rv_tienda);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        listaOrdenadores = new ArrayList<>();

        rv.setAdapter(tiendaAdapter);

        /*
        dbReference = FirebaseDatabase.getInstance().getReference("Ordenadores");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaOrdenadores.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Ordenador pc = ds.getValue(Ordenador.class);
                    listaOrdenadores.add(pc);
                }
                tiendaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error al cargar los datos");
            }
        });

        */

        tiendaAdapter = new AdaptadorTienda(listaOrdenadores);
        tiendaAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Se ha pulsado una pc");
                // aqui lanzariamos el intent para ir a el fragmento del pc seleccionado
            }
        });

        return view;

    }

}
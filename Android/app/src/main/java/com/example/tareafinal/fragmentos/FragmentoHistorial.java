package com.example.tareafinal.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tareafinal.R;
import com.example.tareafinal.adaptadores.AdaptadorHistorial;
import com.example.tareafinal.db.Compra;
import com.example.tareafinal.db.Ordenador;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoHistorial#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoHistorial extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView rvHistorial;
    private AdaptadorHistorial adaptadorHistorial;

    private List<Compra> listaCompras;
    private List<Ordenador> listaOrdenadoresHistorial;

    //private DatabaseReference dbReferenceCompras;
    //private DatabaseReference dbReferenceOrdenadores;

    public FragmentoHistorial() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoHistorial.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoHistorial newInstance(String param1, String param2) {
        FragmentoHistorial fragment = new FragmentoHistorial();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        rvHistorial = view.findViewById(R.id.rv_historial);
        rvHistorial.setLayoutManager(new LinearLayoutManager(getContext()));

        listaOrdenadoresHistorial = new ArrayList<>();
        listaCompras = new ArrayList<>();

        adaptadorHistorial = new AdaptadorHistorial(listaOrdenadoresHistorial, listaCompras);
        rvHistorial.setAdapter(adaptadorHistorial);

        //dbReferenceCompras = FirebaseDatabase.getInstance().getReference("compras");
        //dbReferenceOrdenadores = FirebaseDatabase.getInstance().getReference("ordenadores");

        //cargarOrdenadores();

        return view;
    }
/*
    private void cargarOrdenadores() {
        dbReferenceOrdenadores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaOrdenadoresHistorial.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Ordenador pc = ds.getValue(Ordenador.class);
                    listaOrdenadoresHistorial.add(pc);
                }
                cargarCompras(); // cargamos las compras
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error al cargar los ordenadores", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarCompras() {
        dbReferenceCompras.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaCompras.clear();
                List<Ordenador> ordenadoresFiltrados = new ArrayList<>();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Compra compra = ds.getValue(Compra.class);
                    if (compra.isComprado() == true) {
                        listaCompras.add(compra);

                        // busca el ordenador correspondiente a la compra
                        for (Ordenador ordenador : listaCompras) {
                            if (ordenador.getId() == compra.getIdProducto()) {
                                ordenadoresFiltrados.add(ordenador);
                                break;
                            }
                        }
                    }
                }

                // actualizar el adaptador con los datos obtenidos
                adaptadorHistorial = new AdaptadorHistorial(ordenadoresFiltrados, listaCompras);
                rvHistorial.setAdapter(adaptadorHistorial);
                adaptadorHistorial.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error al cargar las compras", Toast.LENGTH_SHORT).show();
            }
        });
    }
 */
}
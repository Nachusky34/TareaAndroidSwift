package com.example.tareafinal.fragmentos;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tareafinal.R;
import com.example.tareafinal.adaptadores.AdaptadorCarrito;
import com.example.tareafinal.adaptadores.AdaptadorHistorial;
import com.example.tareafinal.db.Compra;
import com.example.tareafinal.db.Ordenador;
import com.example.tareafinal.db.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoCarrito#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoCarrito extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView precio;
    private RecyclerView rv;

    private RecyclerView rvCarrito;
    private AdaptadorCarrito adaptadorCarrito;

    private List<Compra> listaCarrito; // estan agregados al carrito pero no comprados
    private List<Ordenador> listaOrdenadoresCarrito;

    private TextView btnComprarYa;
    private Usuario usuario;

    //private DatabaseReference dbReferenceCompras;
    //private DatabaseReference dbReferenceOrdenadores;

    public FragmentoCarrito() {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnComprarYa = getView().findViewById(R.id.btn_comprarya);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        rvCarrito = view.findViewById(R.id.rv_carrito);
        rvCarrito.setLayoutManager(new LinearLayoutManager(getContext()));

        listaOrdenadoresCarrito = new ArrayList<>();
        listaCarrito = new ArrayList<>();

        adaptadorCarrito = new AdaptadorCarrito(listaOrdenadoresCarrito, listaCarrito);
        rvCarrito.setAdapter(adaptadorCarrito);


        adaptadorCarrito.setOnItemClickListener(position -> {
            adaptadorCarrito.eliminarItem(position);
        });

        //dbReferenceCompras = FirebaseDatabase.getInstance().getReference("compras");
        //dbReferenceOrdenadores = FirebaseDatabase.getInstance().getReference("ordenadores");

        //cargarOrdenadores();

        usuario = (Usuario) getArguments().getSerializable("usuario");

        return view;
    }
/*

    private void cargarOrdenadores() {
        dbReferenceOrdenadores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaOrdenadoresCarrito.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Ordenador pc = ds.getValue(Ordenador.class);
                    listaOrdenadoresCarrito.add(pc);
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
                    if (compra.isComprado() == false) {
                        listaCarrito.add(compra);

                        // busca el ordenador correspondiente a la compra
                        for (Ordenador ordenador : listaCarrito) {
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

 */
}
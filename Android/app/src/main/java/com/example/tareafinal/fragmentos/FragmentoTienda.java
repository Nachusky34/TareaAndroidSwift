package com.example.tareafinal.fragmentos;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tareafinal.R;
import com.example.tareafinal.adaptadores.AdaptadorTienda;
import com.example.tareafinal.controladores.ControladorProducto;
import com.example.tareafinal.db.Ordenador;
import com.example.tareafinal.db.Usuario;
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

    private RecyclerView rvTienda;
    private AdaptadorTienda tiendaAdapter;
    private ControladorProducto controladorProducto;
    private Switch switchLayout;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private Usuario usuario;
    private ImageButton btnCarrito;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controladorProducto = new ControladorProducto();
        usuario = (Usuario) getArguments().getSerializable("usuario");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tienda, container, false);

        // Creamos los layouts
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(getContext());

        btnCarrito = view.findViewById(R.id.btn_carrito);
        rvTienda = view.findViewById(R.id.rv_tienda);
        switchLayout = view.findViewById(R.id.switch_tipolayout);

        switchLayout.setChecked(false);

        tiendaAdapter = new AdaptadorTienda(); // hay que pasarle el estado del switch al adaptador

        // establece el layout
        rvTienda.setLayoutManager(linearLayoutManager);

        rvTienda.setAdapter(tiendaAdapter);

        controladorProducto.getAll(tiendaAdapter);

        btnCarrito.setOnClickListener(v -> iniciarFragmentoCarrito());

        tiendaAdapter.setOnItemClickListener(ordenador -> {
            iniciarFragmentoProducto(ordenador);
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

    public void iniciarFragmentoProducto(Ordenador ordenador) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);
        bundle.putSerializable("ordenador", ordenador);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FragmentoProducto fragmentoProducto = new FragmentoProducto();
        fragmentoProducto.setArguments(bundle);
        transaction.replace(R.id.flContenedor, fragmentoProducto);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void iniciarFragmentoCarrito() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FragmentoCarrito fragmentoCarrito = new FragmentoCarrito();
        fragmentoCarrito.setArguments(bundle);
        transaction.replace(R.id.flContenedor, fragmentoCarrito);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
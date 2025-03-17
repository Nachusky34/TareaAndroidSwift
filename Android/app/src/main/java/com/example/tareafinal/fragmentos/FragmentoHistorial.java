package com.example.tareafinal.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tareafinal.R;
import com.example.tareafinal.adaptadores.AdaptadorHistorial;
import com.example.tareafinal.controladores.ControladorCompras;
import com.example.tareafinal.controladores.ControladorProducto;
import com.example.tareafinal.db.Compra;
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
 * Use the {@link FragmentoHistorial#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoHistorial extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageButton btnCarrito;

    private RecyclerView rvHistorial;
    private AdaptadorHistorial adaptadorHistorial;

    private List<Compra> listaCompras;
    private List<Ordenador> listaOrdenadoresHistorial;

    private Switch switchLayout;
    private Usuario usuario;
    private ControladorCompras controladorCompras;
    private ControladorProducto controladorProducto;

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

        controladorProducto = new ControladorProducto();
        controladorCompras = new ControladorCompras();
        usuario = (Usuario) getArguments().getSerializable("usuario");

        rvHistorial = view.findViewById(R.id.rv_historial);
        switchLayout = view.findViewById(R.id.switch_tipolayout);
        btnCarrito = view.findViewById(R.id.btn_carrito);

        boolean estadoSwitch = switchLayout.isChecked();

        // Configurar el RecyclerView según el estado inicial del Switch
        if (estadoSwitch) {
            rvHistorial.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            rvHistorial.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        listaOrdenadoresHistorial = new ArrayList<>();
        listaCompras = new ArrayList<>();

        adaptadorHistorial = new AdaptadorHistorial(listaOrdenadoresHistorial, listaCompras, estadoSwitch);
        rvHistorial.setAdapter(adaptadorHistorial);

        listaCompras = controladorCompras.buscarComprasPorUsuario(usuario.getId());
        List<Ordenador> ordenadores = controladorProducto.getAll();

        for (int i = 0; i < listaCompras.size(); i++) {
            for (int j = 0; j < ordenadores.size(); j++) {
                if (ordenadores.get(i).equals(listaCompras.get(i).getIdProducto()))
                    listaOrdenadoresHistorial.add(ordenadores.get(i));
            }
        }

        switchLayout.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rvHistorial.setLayoutManager(new GridLayoutManager(getContext(), 2));
            } else {
                rvHistorial.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            // Actualiza el adaptador para reflejar el nuevo diseño
            adaptadorHistorial.setEstaMarcado(isChecked);
        });

        btnCarrito.setOnClickListener(v -> iniciarFragmentoCarrito());

        return view;
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
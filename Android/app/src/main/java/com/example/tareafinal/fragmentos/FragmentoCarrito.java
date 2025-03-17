package com.example.tareafinal.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tareafinal.R;
import com.example.tareafinal.adaptadores.AdaptadorCarrito;
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

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
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

    private RecyclerView rvCarrito;
    private AdaptadorCarrito adaptadorCarrito;

    private List<Compra> listaCarrito; // estan agregados al carrito pero no comprados
    private List<Ordenador> listaOrdenadores;

    private TextView btnComprarYa;
    private Usuario usuario;
    private Double precioTotal;
    private boolean compraEliminada;
    private ControladorCompras controladorCompras;
    private ControladorProducto controladorProducto;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Iniciamos las listas
        listaOrdenadores = new ArrayList<>();
        listaCarrito = new ArrayList<>();
        controladorProducto = new ControladorProducto();
        controladorCompras = new ControladorCompras();
        precioTotal = 0.0;
        compraEliminada = false;

        usuario = (Usuario) getArguments().getSerializable("usuario");

        listaOrdenadores = controladorProducto.getAll();

        adaptadorCarrito = new AdaptadorCarrito(listaOrdenadores, listaCarrito);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        precio = view.findViewById(R.id.tv_carrito_precio);
        btnComprarYa = view.findViewById(R.id.btn_comprarya);
        rvCarrito = view.findViewById(R.id.rv_carrito);

        rvCarrito.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCarrito.setAdapter(adaptadorCarrito);

        adaptadorCarrito.setOnItemClickListener(compra -> {
            if (eliminarCompra(compra)) {
                //Toast.makeText(getContext(), "Compra eliminada correctamente", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(getContext(), "Error al eliminar la compra", Toast.LENGTH_SHORT).show();
            }
        });

        btnComprarYa.setOnClickListener(v -> {
            try {
                comprarYa();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        return view;
    }

    private Double setPrecioTotal() {
        for (int i = 0; i < listaCarrito.size(); i++) {
            precioTotal += Integer.parseInt(listaCarrito.get(i).getCantidad()) *
                    Integer.parseInt(listaOrdenadores.get(i).getPrecio());
        }
        return precioTotal;
    }

    public void cargarOrdenadores() {
        List<Ordenador> ordenadores = controladorProducto.getAll();

        for (int i = 0; i < listaCarrito.size(); i++) {
            for (int j = 0; j < ordenadores.size(); j++) {
                if (ordenadores.get(i).equals(listaCarrito.get(i).getIdProducto()))
                    listaOrdenadores.add(ordenadores.get(i));
            }
        }
    }

    private void cargarCompras() {

        listaCarrito = controladorCompras.buscarCarritoPorIdUsuario(usuario.getId());
        cargarOrdenadores();

        adaptadorCarrito.listaOrdenadoresCarrito = listaOrdenadores;
        adaptadorCarrito.listaCompras = listaCarrito;
        adaptadorCarrito.notifyDataSetChanged();

        precio.setText(String.format("%.2f $", setPrecioTotal()));

    }

    private boolean eliminarCompra(Compra compra) {
        String id = compra.getIdUsuario() + "-" + compra.getIdProducto();
        controladorCompras.eliminarCompra(id);
        cargarCompras();
        return compraEliminada;
    }

    private void comprarYa() throws InterruptedException {
        Date now = new Date();
        for (Compra compraAntigua : listaCarrito) {
            String idCompra = compraAntigua.getIdUsuario() + "-" + compraAntigua.getIdProducto();

            Compra compraNueva = new Compra();
            compraNueva.setIdUsuario(compraAntigua.getIdUsuario());
            compraNueva.setIdProducto(compraAntigua.getIdProducto());
            compraNueva.setCantidad(compraAntigua.getCantidad());
            compraNueva.setComprado(true);
            compraNueva.setFecha(obtenerFechaFormateada(now));
            compraNueva.setHora(obtenerHoraFormateada(now));

            //Haseamos la clave
            String idHassed = hassId(idCompra + "-" + obtenerMilisegundos(now));

            controladorCompras.eliminarCompra(compraAntigua.getIdUsuario() + "-"
                                                + compraAntigua.getIdProducto());
            controladorCompras.agregarCompra(compraNueva, idHassed);

        }
        Toast.makeText(getContext(), "Compra realizada correctamente", Toast.LENGTH_SHORT).show();
        volverATienda();
    }

    private String obtenerFechaFormateada(Date now) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateTimeFormat.format(now);
    }

    private String obtenerHoraFormateada(Date now) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm");
        return dateTimeFormat.format(now);
    }

    private long obtenerMilisegundos(Date now) {
        return now.getTime();
    }

    private String hassId(String id) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[]idBytes = id.getBytes();
            md.update(idBytes);
            byte[] resumen = md.digest();


            // Haseamos en formato haxadecimal para que la base de datos no de error
            StringBuilder hexId = new StringBuilder();
            for (byte b : resumen) {
                hexId.append(String.format("%02x", b)); // ("%02x") Representa formato hexadecimal
            }

            return hexId.toString();

        }catch (Exception  e) {
            throw new RuntimeException(e);
        }
    }

    private void volverATienda() {
        if (getParentFragmentManager().getBackStackEntryCount() > 0) {
            getParentFragmentManager().popBackStack();
        } else {
            requireActivity().getOnBackPressedDispatcher().onBackPressed(); // Si no hay más fragmentos en la pila, vuelve atrás en la actividad
        }
    }
}


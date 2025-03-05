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

    private FirebaseDatabase database;
    private DatabaseReference dbReferenceCompras;
    private DatabaseReference dbReferenceOrdenadores;
    private Double precioTotal;
    private boolean compraEliminada;

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

        // Iniciar DDBB
        database = FirebaseDatabase.getInstance("https://pcera-2b2f4-default-rtdb.europe-west1.firebasedatabase.app/");
        dbReferenceCompras = database.getReference("compras");
        dbReferenceOrdenadores = database.getReference("productos");

        // Iniciamos las listas
        listaOrdenadores = new ArrayList<>();
        listaCarrito = new ArrayList<>();
        precioTotal = 0.0;
        compraEliminada = false;

        usuario = (Usuario) getArguments().getSerializable("usuario");

        cargarOrdenadores();

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

        btnComprarYa.setOnClickListener(v -> comprarYa());

        return view;
    }


    private void cargarOrdenadores() {
        dbReferenceOrdenadores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaOrdenadores.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Ordenador pc = ds.getValue(Ordenador.class);
                    if (pc != null) {
                        listaOrdenadores.add(pc);
                    }
                }
                cargarCompras();
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
                listaCarrito.clear();
                List<Ordenador> ordenadoresFiltrados = new ArrayList<>();
                precioTotal = 0.0;

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Compra compra = ds.getValue(Compra.class);

                    if (compra != null && !compra.isComprado()
                            && compra.getIdUsuario().equals(usuario.getId())) {
                        listaCarrito.add(compra);

                        for (Ordenador ordenador : listaOrdenadores) {
                            if (ordenador.getId().equals(compra.getIdProducto())) {
                                ordenadoresFiltrados.add(ordenador);
                                precioTotal += Double.parseDouble(ordenador.getPrecio()) *
                                        Double.parseDouble(compra.getCantidad());
                                break;
                            }
                        }
                    }
                }

                // Actualizar los datos del adaptador y notificarle de los cambios
                adaptadorCarrito.listaOrdenadoresCarrito = ordenadoresFiltrados;
                adaptadorCarrito.listaCompras = listaCarrito;
                adaptadorCarrito.notifyDataSetChanged();

                precio.setText(String.format("%.2f $", precioTotal));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error al cargar las compras", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean eliminarCompra(Compra compra) {
        String id = compra.getIdUsuario() + "-" + compra.getIdProducto();
        dbReferenceCompras.child(id).removeValue()
                .addOnSuccessListener(aVoid -> {
                    compraEliminada = true;
                    Toast.makeText(getContext(), "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    compraEliminada = false;
                    Toast.makeText(getContext(), "Error al eliminar la compra", Toast.LENGTH_SHORT).show();
                });
        cargarCompras();
        return compraEliminada;
    }

    private void comprarYa() {
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

            dbReferenceCompras.child(idHassed).setValue(compraNueva).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Si se crea la compra eliminamos la antigua
                    dbReferenceCompras.child(idCompra).removeValue();
                }
            });

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


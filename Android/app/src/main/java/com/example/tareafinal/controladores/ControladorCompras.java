package com.example.tareafinal.controladores;

import androidx.annotation.NonNull;

import com.example.tareafinal.db.Compra;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ControladorCompras {
    private final FirebaseDatabase db;
    private DatabaseReference compraRef;
    private ArrayList<Compra> listaCompras;
    private ValueEventListener valueEventListener;

    public ControladorCompras() {
        db = FirebaseDatabase.getInstance("https://pcera-2b2f4-default-rtdb.europe-west1.firebasedatabase.app/");
        compraRef = db.getReference("compras");
        listaCompras = new ArrayList<>();
        consultarBaseDatos();
    }

    // Método para consultar toda la base de datos
    public void consultarBaseDatos() {
        if (valueEventListener != null) {
            compraRef.removeEventListener(valueEventListener);
        }

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaCompras.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Compra compra = ds.getValue(Compra.class);
                    if (compra != null) {
                        listaCompras.add(compra);
                    }
                }
                System.out.println(listaCompras.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar error
            }
        };

        compraRef.addValueEventListener(valueEventListener);
    }

    // Método para filtrar por usuario
    public List<Compra> buscarComprasPorUsuario(String idUsuario) {
        ArrayList<Compra> comprasFiltradas = new ArrayList<>();
        for (Compra compra : listaCompras) {
            if (compra != null && compra.getIdUsuario().equals(idUsuario)) {
                comprasFiltradas.add(compra);
            }
        }
        return comprasFiltradas;
    }

    // Método para filtrar carrito
    public List<Compra> buscarCarritoPorIdUsuario(String idUsuario) {
        ArrayList<Compra> comprasFiltradas = new ArrayList<>();
        for (Compra compra : listaCompras) {
            if (compra != null &&
                    compra.getIdUsuario().equals(idUsuario) &&
                    !compra.isComprado()) {
                comprasFiltradas.add(compra);
            }
        }
        return comprasFiltradas;
    }

    // Método para verificar compras iguales (solo para comprobación)
    private int hayCompraIgual(String idCompra, String idUsuario) {


        for (Compra compra : listaCompras) {
            if (compra != null &&
                    !compra.isComprado() &&
                    compra.getIdUsuario().equals(idUsuario)) {
                String idCompraActual = compra.getIdUsuario() + "-" + compra.getIdProducto();
                if (idCompraActual.equals(idCompra)) {
                    return Integer.parseInt(compra.getCantidad());
                }
            }
        }
        return -1;
    }

    // Método para agregar o actualizar compras
    public void agregarCompra(Compra compra, String idCompra) {
        if (idCompra == null) {
            idCompra = compra.getIdUsuario() + "-" + compra.getIdProducto();
        }

        int cantidad = hayCompraIgual(idCompra, compra.getIdUsuario());

        if (cantidad != -1) {
            int nuevaCantidad = cantidad + Integer.parseInt(compra.getCantidad());

            compraRef.child(idCompra).child("cantidad").setValue(String.valueOf(nuevaCantidad));
        } else {
            compraRef.child(idCompra).setValue(compra);
        }
    }

    public void eliminarCompra(String idCompra) {
        if (idCompra != null) {
            compraRef.child(idCompra).removeValue();
        }
    }

    // Método para limpiar recursos
    public void limpiar() {
        if (valueEventListener != null) {
            compraRef.removeEventListener(valueEventListener);
        }
    }
}

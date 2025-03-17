package com.example.tareafinal.controladores;

import androidx.annotation.NonNull;

import com.example.tareafinal.db.Compra;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
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
    private TaskCompletionSource<List<Compra>> taskSource;
    private Task<List<Compra>> cargaDatosTask;

    public ControladorCompras() {
        db = FirebaseDatabase.getInstance("https://pcera-2b2f4-default-rtdb.europe-west1.firebasedatabase.app/");
        compraRef = db.getReference("compras");
        listaCompras = new ArrayList<>();
        taskSource = new TaskCompletionSource<>();
        cargaDatosTask = taskSource.getTask(); // Inicializar correctamente
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

                // Crear un nuevo TaskCompletionSource para evitar excepciones
                taskSource = new TaskCompletionSource<>();
                cargaDatosTask = taskSource.getTask();
                taskSource.setResult(new ArrayList<>(listaCompras)); // Enviar una copia para evitar modificaciones posteriores
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (taskSource != null) {
                    taskSource.setException(error.toException());
                }
            }
        };

        compraRef.addValueEventListener(valueEventListener);
    }


    // Método para filtrar por usuario
    public List<Compra> cargarCompras(String idUsuario) {
        ArrayList<Compra> comprasFiltradas = new ArrayList<>();
        for (Compra compra : listaCompras) {
            if (compra != null && compra.isComprado() && idUsuario != null && idUsuario.equals(compra.getIdUsuario())) {
                comprasFiltradas.add(compra);
            }
        }
        return comprasFiltradas;
    }

    // Método para filtrar carrito
    public List<Compra> cargarCarrito(String idUsuario) {
        ArrayList<Compra> comprasFiltradas = new ArrayList<>();
        for (Compra compra : listaCompras) {
            if (compra != null && idUsuario != null &&
                    idUsuario.equals(compra.getIdUsuario()) && !compra.isComprado()) {
                comprasFiltradas.add(compra);
            }
        }
        return comprasFiltradas;
    }

    // Método para verificar compras iguales
    private int hayCompraIgual(String idCompra, String idUsuario) {
        if (idCompra == null || idUsuario == null) {
            return -1; // Evitar NullPointerException
        }

        for (Compra compra : listaCompras) {
            if (compra != null && !compra.isComprado() &&
                    idUsuario.equals(compra.getIdUsuario())) {
                String idCompraActual = compra.getIdUsuario() + "-" + compra.getIdProducto();
                if (idCompraActual.equals(idCompra)) {
                    try {
                        return Integer.parseInt(compra.getCantidad() != null ? compra.getCantidad() : "0");
                    } catch (NumberFormatException e) {
                        return -1; // Manejar error si el valor no es un número válido
                    }
                }
            }
        }
        return -1;
    }

    // Método para agregar o actualizar compras
    public void agregarCompra(Compra compra, String idCompra) {
        if (compra == null || compra.getIdUsuario() == null || compra.getIdProducto() == null) {
            return; // Evitar NullPointerException
        }

        if (idCompra == null) {
            idCompra = compra.getIdUsuario() + "-" + compra.getIdProducto();
        }

        int cantidad = hayCompraIgual(idCompra, compra.getIdUsuario());

        if (cantidad != -1) {
            int nuevaCantidad = cantidad + Integer.parseInt(compra.getCantidad() != null ? compra.getCantidad() : "0");
            compraRef.child(idCompra).child("cantidad").setValue(String.valueOf(nuevaCantidad));
        } else {
            compraRef.child(idCompra).setValue(compra);
        }
    }

    // Método para eliminar una compra
    public void eliminarCompra(String idCompra) {
        if (idCompra != null) {
            compraRef.child(idCompra).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Actualizar la lista local para evitar estados incorrectos
                    listaCompras.removeIf(compra -> (compra.getIdUsuario() + "-" + compra.getIdProducto()).equals(idCompra));
                }
            });
        }
    }


    // Método para cargar datos desde Firebase
    public Task<List<Compra>> cargarDatos() {
        TaskCompletionSource<List<Compra>> taskSource = new TaskCompletionSource<>();

        compraRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaCompras.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Compra compra = ds.getValue(Compra.class);
                    if (compra != null) {
                        listaCompras.add(compra);
                    }
                }
                taskSource.setResult(listaCompras);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                taskSource.setException(error.toException());
            }
        });

        return taskSource.getTask();
    }
}

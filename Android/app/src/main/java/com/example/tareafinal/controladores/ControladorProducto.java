package com.example.tareafinal.controladores;

import androidx.annotation.NonNull;

import com.example.tareafinal.adaptadores.AdaptadorTienda;
import com.example.tareafinal.db.Ordenador;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ControladorProducto {

    private final FirebaseDatabase db;
    private DatabaseReference ordenadorRef;
    private ValueEventListener valueEventListener;
    private TaskCompletionSource<List<Ordenador>> taskSource;
    private Task<List<Ordenador>> cargaDatosTask;

    public ControladorProducto() {
        db = FirebaseDatabase.getInstance("https://pcera-2b2f4-default-rtdb.europe-west1.firebasedatabase.app/");
        ordenadorRef = db.getReference("productos");
    }

    public void getAll() {
        // Asegúrate de que no haya listeners previos
        if (valueEventListener != null) {
            ordenadorRef.removeEventListener(valueEventListener);
        }

        // Crea un nuevo TaskCompletionSource
        taskSource = new TaskCompletionSource<>();

        // Obtén los datos una sola vez
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Ordenador> ordenadores = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Ordenador ordenador = ds.getValue(Ordenador.class);
                    if (ordenador != null) {
                        ordenadores.add(ordenador);
                    }
                }
                taskSource.setResult(ordenadores); // Completa el Task
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                taskSource.setException(error.toException()); // Maneja el error si ocurre
            }
        };

        // Usa addListenerForSingleValueEvent para solo ejecutar una vez
        ordenadorRef.addListenerForSingleValueEvent(valueEventListener);
    }

    public Task<List<Ordenador>> cargarDatos() {
        if (cargaDatosTask != null && !cargaDatosTask.isComplete()) {
            return cargaDatosTask;
        }

        // Llama a getAll para cargar los datos
        getAll();

        // Devuelve el Task asociado
        cargaDatosTask = taskSource.getTask();

        return cargaDatosTask;
    }

    public List<Ordenador> getOrdenadorById(String id) {
        List<Ordenador> ordenadores = new ArrayList<>();

        ordenadorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Ordenador ordenador = ds.getValue(Ordenador.class);

                    if (ordenador.getId().equals(id))
                        ordenadores.add(ordenador);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        return ordenadores;
    }

    public List<Ordenador> cargarTienda(AdaptadorTienda adaptadorTienda) {
        List<Ordenador> ordenadores = new ArrayList<>();

        ordenadorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Ordenador ordenador = ds.getValue(Ordenador.class);
                    ordenadores.add(ordenador);
                }
                adaptadorTienda.actualizarDatos(ordenadores);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        return ordenadores;
    }
}

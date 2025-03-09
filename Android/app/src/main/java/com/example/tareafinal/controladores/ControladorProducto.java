package com.example.tareafinal.controladores;

import androidx.annotation.NonNull;

import com.example.tareafinal.adaptadores.AdaptadorTienda;
import com.example.tareafinal.db.Ordenador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ControladorProducto {

    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://pcera-2b2f4-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference ordenadorRef;

    public ControladorProducto() {
        ordenadorRef = db.getReference("productos");
    }

    public List<Ordenador> getAll (AdaptadorTienda adaptadorTienda) {
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

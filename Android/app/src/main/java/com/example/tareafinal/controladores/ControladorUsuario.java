package com.example.tareafinal.controladores;

import androidx.annotation.NonNull;

import com.example.tareafinal.db.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ControladorUsuario {

    private final FirebaseDatabase db;
    private DatabaseReference usuarioRef;
    boolean creado, modificado;

    public ControladorUsuario() {
        db = FirebaseDatabase.getInstance("https://pcera-2b2f4-default-rtdb.europe-west1.firebasedatabase.app/");
        usuarioRef = db.getReference("usuarios");
    }

    public boolean crearUsuario(Usuario usuario) {
        String id = usuarioRef.push().getKey();
        usuario.setId(id);
        creado = false;
        usuarioRef.child(id).setValue(usuario)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        creado = true;
                    }
                });
        return creado;
    }

    public boolean actualizarUsuario(Usuario usuario) {
        modificado = false;
        usuarioRef.child(usuario.getId()).setValue(usuario)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        modificado = true;
                    }
                });
        return modificado;
    }

    public boolean actualizarImagen(String id, String nombreImg) {
        modificado = false;
        usuarioRef.child(id).child("fotoPerfil")
                .setValue(nombreImg)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        modificado = true;
                    }
                });
        return modificado;
    }

    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Usuario usuario = ds.getValue(Usuario.class);
                    usuarios.add(usuario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        return usuarios;
    }
}
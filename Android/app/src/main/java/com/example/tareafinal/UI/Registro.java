package com.example.tareafinal.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tareafinal.R;
import com.example.tareafinal.db.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private EditText username, pwd, email, postalcode;
    FirebaseDatabase database;
    DatabaseReference dbRef;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.et_user_registro);
        pwd = findViewById(R.id.et_pass_registro);
        email = findViewById(R.id.et_email_registro);
        postalcode = findViewById(R.id.et_postal_registro);
        id = "-1";

        database = FirebaseDatabase.getInstance("https://pcera-2b2f4-default-rtdb.europe-west1.firebasedatabase.app/");
        dbRef = database.getReference("usuarios");
    }

    public void registrarse(View view) {
        Usuario usuario = generarUsuario();
        if (usuario == null) {
            Toast.makeText(this, "Intente otra vez", Toast.LENGTH_SHORT).show();
            return;
        }
        dbRef.child(id).setValue(usuario);
        finish();
    }

    public Usuario generarUsuario() {
        id = dbRef.push().getKey();
        Usuario usuario = new Usuario();
        usuario.setUsername(username.getText().toString());
        usuario.setPassword(pwd.getText().toString());
        usuario.setEmail(email.getText().toString());
        usuario.setPostalCode(postalcode.getText().toString());
        usuario.setNewsletter(false);
        usuario.setFotoPerfil("fotoperfil.png");
        usuario.setId(id);

        return usuario;
    }
}
package com.example.tareafinal.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tareafinal.R;
import com.example.tareafinal.controladores.ControladorUsuario;
import com.example.tareafinal.db.Usuario;

public class Registro extends AppCompatActivity {

    private EditText username, pwd, email, postalcode;
    private ControladorUsuario controladorUsuario;


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
        controladorUsuario = new ControladorUsuario();
    }

    public void registrarse(View view) {
        Usuario usuario = generarUsuario();
        if (usuario == null) {
            Toast.makeText(this, "Intente otra vez", Toast.LENGTH_SHORT).show();
            return;
        }

        controladorUsuario.crearUsuario(usuario);
        finish();
    }

    public Usuario generarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername(username.getText().toString());
        usuario.setPassword(pwd.getText().toString());
        usuario.setEmail(email.getText().toString());
        usuario.setPostalCode(postalcode.getText().toString());
        usuario.setNewsletter(false);
        usuario.setFotoPerfil("fotoperfil.png");

        if (usuario.getUsername().isEmpty() || usuario.getPassword().isEmpty() || usuario.getEmail().isEmpty() || usuario.getPostalCode().isEmpty()) {
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            return null;
        }

        return usuario;
    }
}
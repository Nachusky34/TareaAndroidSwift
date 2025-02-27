package com.example.tareafinal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tareafinal.R;
import com.example.tareafinal.db.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference dbReferenceUsuarios;
    List<Usuario> listaUsuarios;


    ActivityResultLauncher<Intent> launcherRegistro = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();

                    }
                }
            });


    ActivityResultLauncher<Intent> launcherTienda = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();

                    }
                }
            });

    private EditText username, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.et_user);
        pwd = findViewById(R.id.et_pwd);

        database = FirebaseDatabase.getInstance("https://pcera-2b2f4-default-rtdb.europe-west1.firebasedatabase.app/");
        dbReferenceUsuarios = database.getReference("usuarios");

        listaUsuarios = new ArrayList<>();
        cargarUsuarios();
    }

    public void iniciarRegistro(View view) {
        Intent intent = new Intent(this, Registro.class);
        launcherRegistro.launch(intent);
    }

    public void login(View view) {
        String username = this.username.getText().toString();
        String pwd = this.pwd.getText().toString();

        boolean encontrado = false;
        for (Usuario usuario : listaUsuarios) {
            if (username.equals(usuario.getUsername()) && pwd.equals(usuario.getPassword())) {
                Intent intent = new Intent(this, Tabs.class);
                intent.putExtra("usuario", usuario);
                launcherTienda.launch(intent);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }


    public void cargarUsuarios() {
        dbReferenceUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaUsuarios.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Usuario usuario = ds.getValue(Usuario.class);
                    listaUsuarios.add(usuario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Ha surgido un problema",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
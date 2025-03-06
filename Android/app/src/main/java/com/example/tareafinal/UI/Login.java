package com.example.tareafinal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tareafinal.R;
import com.example.tareafinal.db.Usuario;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Login extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference dbReferenceUsuarios;
    List<Usuario> listaUsuarios;

    private EditText username, pwd;
    private TextView loginGoogle;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.et_user);
        pwd = findViewById(R.id.et_pwd);
        loginGoogle = findViewById(R.id.tv_google_signin);

        database = FirebaseDatabase.getInstance("https://pcera-2b2f4-default-rtdb.europe-west1.firebasedatabase.app/");
        dbReferenceUsuarios = database.getReference("usuarios");

        listaUsuarios = new ArrayList<>();
        cargarUsuarios();

        loginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesionGoogle();
            }
        });
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
                accederTienda(usuario);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

    public void accederTienda(Usuario u) {
        Intent intent = new Intent(this, Tabs.class);
        intent.putExtra("usuario", u);
        launcherTienda.launch(intent);
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

    private void iniciarSesionGoogle() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false) // Evita el inicio automático con Smart Lock
                .build();
        signInLauncher.launch(signInIntent);

    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String userEmail = user.getEmail();
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("usuarios");
                userRef.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Usuario registrado, obtener información de la base de datos
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                Usuario usuario = ds.getValue(Usuario.class);
                                Toast.makeText(Login.this, "Inicio de sesión exitoso: " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                accederTienda(usuario);
                                return; // Salir del bucle y del listener
                            }
                        } else {
                            Toast.makeText(Login.this, "Cuenta no registrada", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Login.this, "Error al verificar usuario", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            if (response != null) {
                Toast.makeText(this, "Error: " + response.getError().getErrorCode(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Inicio de sesión cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }
    }
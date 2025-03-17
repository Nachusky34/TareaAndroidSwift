package com.example.tareafinal.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.tareafinal.controladores.ControladorUsuario;
import com.example.tareafinal.db.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    List<Usuario> listaUsuarios;

    private EditText username, pwd;
    private TextView loginGoogle;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "Login";
    private ControladorUsuario controladorUsuario;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            this::onSignInResult
    );

    ActivityResultLauncher<Intent> launcherRegistro = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                    }
                }
            });

    ActivityResultLauncher<Intent> launcherTienda = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
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

        controladorUsuario = new ControladorUsuario();
        listaUsuarios = new ArrayList<>();
        cargarUsuarios();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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
        listaUsuarios = controladorUsuario.getUsuarios();
    }

    private void iniciarSesionGoogle() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            signInLauncher.launch(signInIntent);
        });
    }

    private void onSignInResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                verificarUsuario(account);
            } catch (ApiException e) {
                Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                Toast.makeText(this, "Error al iniciar sesión con Google", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Inicio de sesión con Google cancelado", Toast.LENGTH_SHORT).show();
        }
    }

    private void verificarUsuario(GoogleSignInAccount account) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("usuarios");
        userRef.orderByChild("email").equalTo(account.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Usuario usuario = ds.getValue(Usuario.class);
                        Toast.makeText(Login.this, "Inicio de sesión exitoso: " + account.getDisplayName(), Toast.LENGTH_SHORT).show();
                        accederTienda(usuario);
                        return;
                    }
                } else {
                    crearNuevaCuenta(account);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Error al verificar usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crearNuevaCuenta(GoogleSignInAccount account) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(account.getEmail());
        nuevoUsuario.setUsername(account.getDisplayName().trim());
        nuevoUsuario.setPassword("");
        nuevoUsuario.setPostalCode("");
        nuevoUsuario.setNewsletter(false);
        nuevoUsuario.setFotoPerfil("fotoperfil.png");

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("usuarios").push();
        String userId = userRef.getKey(); // Obtener el ID generado

        nuevoUsuario.setId(userId); // Guardar el ID en el objeto Usuario

        userRef.setValue(nuevoUsuario)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Login.this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show();
                    accederTienda(nuevoUsuario);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Login.this, "Error al crear cuenta", Toast.LENGTH_SHORT).show();
                });
    }
}
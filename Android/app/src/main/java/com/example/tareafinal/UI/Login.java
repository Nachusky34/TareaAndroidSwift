package com.example.tareafinal.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tareafinal.R;
import com.example.tareafinal.db.Usuario;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;


public class Login extends AppCompatActivity {

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
        username = findViewById(R.id.et_user);
        pwd = findViewById(R.id.et_pwd);
    }

    public void iniciarRegistro() {
        Intent intent = new Intent();
        launcherRegistro.launch(intent);
    }

    public void login(View view) {
        Intent intent = new Intent();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = username.getText().toString() + "@PCeras.com";
        String pwd = this.pwd.getText().toString();

        auth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        launcherTienda.launch(intent);
                    } else {
                        Toast.makeText(this, "Usuario o contraseña incorrectos",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
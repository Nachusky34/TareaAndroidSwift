package com.example.tareafinal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tareafinal.R;
import com.example.tareafinal.db.Usuario;
import com.example.tareafinal.fragmentos.FragmentoHistorial;
import com.example.tareafinal.fragmentos.FragmentoPerfil;
import com.example.tareafinal.fragmentos.FragmentoTienda;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class Tabs extends AppCompatActivity {

    FrameLayout flContenedor;
    TabLayout tb;
    TabItem tiPerfil, tiTienda, tiHistorial;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Bundle bundle;
    String idUserGoogle; // para cuando se inicie sesión con Google

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tabs);

        Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);

        flContenedor = findViewById(R.id.flContenedor);
        tb = findViewById(R.id.tb);
        tiPerfil = findViewById(R.id.tiPerfil);
        tiTienda = findViewById(R.id.tiTienda);
        tiHistorial = findViewById(R.id.tiHistorial);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fragmentManager = getSupportFragmentManager();

        // Cargar FragmentoTienda al inicio justo después de inicializar fragmentManager
        cargarFragmento(1);

        // Seleccionar la pestaña de la tienda visualmente
        tb.getTabAt(1).select();

        tb.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                cargarFragmento(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                cargarFragmento(tab.getPosition());
            }
        });

    }

    private void cargarFragmento(int position) {
        transaction = fragmentManager.beginTransaction();
        Fragment fragment = null;

        if (position == 0) {
            fragment = new FragmentoPerfil();
        } else if (position == 1) {
            fragment = new FragmentoTienda();
        } else if (position == 2) {
            fragment = new FragmentoHistorial();
        }

        if (fragment != null) {
            fragment.setArguments(bundle);
            transaction.replace(R.id.flContenedor, fragment);
            transaction.commit();
        }
    }


    // Dentro de tu actividad que gestiona los fragmentos con el TabLayout
    public void cerrarSesion() {
        // Usar la clase de la actividad Login directamente
        Intent intent = new Intent(Tabs.this, Login.class);  // Aquí es donde haces la corrección

        // Para evitar que el usuario regrese a la actividad de Tabs con el botón de atrás
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Iniciar la actividad de login
        startActivity(intent);

        // Finalizar la actividad actual (que es la de los tabs)
        finish();
    }


}
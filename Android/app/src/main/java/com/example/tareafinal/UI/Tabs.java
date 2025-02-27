package com.example.tareafinal.UI;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tabs);

        Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        Bundle bundle = new Bundle();
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

        tb.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                transaction = fragmentManager.beginTransaction();

                if (tab.getPosition() == 0) {
                    FragmentoPerfil fragmentoPerfil = new FragmentoPerfil();
                    fragmentoPerfil.setArguments(bundle);
                    transaction.replace(R.id.flContenedor, fragmentoPerfil);
                    transaction.addToBackStack(null);
                }
                if (tab.getPosition() == 1) {
                    FragmentoTienda fragmentoTienda = new FragmentoTienda();
                    fragmentoTienda.setArguments(bundle);
                    transaction.replace(R.id.flContenedor, fragmentoTienda);
                    transaction.addToBackStack(null);
                }
                if (tab.getPosition() == 2) {
                    FragmentoHistorial fragmentoHistorial = new FragmentoHistorial();
                    fragmentoHistorial.setArguments(bundle);
                    transaction.replace(R.id.flContenedor, fragmentoHistorial);
                    transaction.addToBackStack(null);
                }
                transaction.commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
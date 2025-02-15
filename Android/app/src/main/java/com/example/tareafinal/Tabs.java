package com.example.tareafinal;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
                /*
                if (tab.getPosition() == 0) {
                    transaction.replace(R.id.flContenedor, new Perfil());
                    transaction.addToBackStack(null);
                }
                if (tab.getPosition() == 1) {
                    transaction.replace(R.id.flContenedor, new Tienda());
                    transaction.addToBackStack(null);
                }
                if (tab.getPosition() == 2) {
                    transaction.replace(R.id.flContenedor, new Historial());
                    transaction.addToBackStack(null);
                }
                transaction.commit();
                 */
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
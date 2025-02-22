package com.example.tareafinal.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tareafinal.R;
import com.example.tareafinal.adaptadores.AdaptadorTienda;
import com.example.tareafinal.db.Ordenador;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoTienda#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoTienda extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private RecyclerView rv;
    private AdaptadorTienda tiendaAdapter;

    public FragmentoTienda() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoTienda.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoTienda newInstance(String param1, String param2) {
        FragmentoTienda fragment = new FragmentoTienda();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Ordenador> pc = new ArrayList<>();

        rv = getView().findViewById(R.id.rv_tienda);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        pc.add(new Ordenador("pc1", 1800));
        pc.add(new Ordenador("pc2", 1500));
        pc.add(new Ordenador("pc3", 1200));

        tiendaAdapter = new AdaptadorTienda(pc);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Se ha pulsado una pc");
            }
        };
        tiendaAdapter.setOnClickListener(listener);
        rv.setAdapter(tiendaAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tienda, container, false);
    }

}
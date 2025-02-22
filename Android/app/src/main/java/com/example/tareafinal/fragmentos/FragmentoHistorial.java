package com.example.tareafinal.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.example.tareafinal.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoHistorial#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoHistorial extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView rv;
    private Switch tipoLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoHistorial() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoHistorial.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoHistorial newInstance(String param1, String param2) {
        FragmentoHistorial fragment = new FragmentoHistorial();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rv = getView().findViewById(R.id.rv_historial);
        tipoLayout = getView().findViewById(R.id.switch_tipolayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historial, container, false);
    }
}
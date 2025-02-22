package com.example.tareafinal.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tareafinal.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoProducto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoProducto extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView imagen;
    private TextView precio, cantidad, total, descripcion, nombre;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoProducto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoProducto.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoProducto newInstance(String param1, String param2) {
        FragmentoProducto fragment = new FragmentoProducto();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        imagen = view.findViewById(R.id.iv_ordenador);
        nombre = view.findViewById(R.id.tv_nombre_ordenador);
        descripcion = view.findViewById(R.id.tv_descripcion);
        precio = view.findViewById(R.id.tv_precio_valor);
        cantidad = view.findViewById(R.id.tv_cantidad_producto);
        total = view.findViewById(R.id.textView2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_producto, container, false);
    }
}
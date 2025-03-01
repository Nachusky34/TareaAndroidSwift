package com.example.tareafinal.fragmentos;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tareafinal.R;
import com.example.tareafinal.db.Compra;
import com.example.tareafinal.db.Ordenador;
import com.example.tareafinal.db.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    private TextView precio, cantidad, total, descripcion, nombre, sumar, restar, tv_agregarCarrito;
    private LinearLayout layout_agregar_carrito;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Usuario usuario;
    private Ordenador ordenador;
    Bundle bundle;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflamos el layout correctamente
        View view = inflater.inflate(R.layout.fragment_producto, container, false);
        imagen = view.findViewById(R.id.iv_ordenador);
        nombre = view.findViewById(R.id.tv_nombre_ordenador);
        descripcion = view.findViewById(R.id.tv_descripcion);
        precio = view.findViewById(R.id.tv_precio_valor);
        cantidad = view.findViewById(R.id.tv_cantidad_producto);
        total = view.findViewById(R.id.textView2);
        sumar = view.findViewById(R.id.tv_sumar);
        restar = view.findViewById(R.id.tv_restar);
        layout_agregar_carrito = view.findViewById(R.id.layout_agregarCarrito);
        tv_agregarCarrito = view.findViewById(R.id.tv_agregar_carrito2);

        sumar.setOnClickListener(v -> sumar(v));
        restar.setOnClickListener(v -> restar(v));

        Bundle bundle = getArguments();

        layout_agregar_carrito.setOnClickListener(v -> agregarAlCarrito(v));
        tv_agregarCarrito.setOnClickListener(v -> agregarAlCarrito(v));

        usuario = (Usuario) bundle.getSerializable("usuario");
        ordenador = (Ordenador) bundle.getSerializable("ordenador");

        String imgOrdenador = ordenador.getImg();
        //Obtenemos la imagen a traves de la que nos ha pasado
        int idImagen = getResources().getIdentifier(imgOrdenador, "drawable", getContext().getPackageName());

        if (idImagen != 0) { // Si la imagen existe
            imagen.setImageResource(idImagen);
        } else {
            imagen.setImageResource(R.drawable.ordenador1); // Imagen por defecto si no se encuentra
        }
        nombre.setText(ordenador.getNombre());
        descripcion.setText(ordenador.getDescripcion());
        precio.setText(ordenador.getPrecio() + " $");
        total.setText(ordenador.getPrecio() + " $");

        database = FirebaseDatabase.getInstance("https://pcera-2b2f4-default-rtdb.europe-west1.firebasedatabase.app/");
        dbRef = database.getReference("compras");


        // Inflate the layout for this fragment
        return view;
    }

    public void agregarAlCarrito(View view) {
        Compra compra = new Compra();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        compra.setIdUsuario(usuario.getId());
        compra.setIdProducto(ordenador.getId());
        compra.setCantidad(cantidad.getText().toString());
        compra.setComprado(false);
        compra.setFecha(dateFormat.format(calendar.getTime()));
        compra.setHora(timeFormat.format(calendar.getTime()));

        bundle = new Bundle();
        bundle.putSerializable("compra", compra);
        dbRef.push().setValue(compra);
        Toast.makeText(getContext(), "Se ha agregadop la compra", Toast.LENGTH_SHORT).show();

        volverATienda();
    }

    public void volverATienda() {
        if (getParentFragmentManager().getBackStackEntryCount() > 0) {
            getParentFragmentManager().popBackStack();
        } else {
            requireActivity().getOnBackPressedDispatcher().onBackPressed(); // Si no hay más fragmentos en la pila, vuelve atrás en la actividad
        }
    }

    public void sumar(View view) {

        String precioTexto = precio.getText().toString().replace("$", "").trim();

        int precioInt = Integer.parseInt(precioTexto);
        int catidadInt = Integer.parseInt(cantidad.getText().toString()) + 1;

        this.cantidad.setText(String.valueOf(catidadInt));
        total.setText(String.valueOf(precioInt * catidadInt) + " $");
    }

    public void restar(View view) {
        //Eliminamos el simbolo $
        String precioTexto = precio.getText().toString().replace("$", "").trim();

        int precioInt = Integer.parseInt(precioTexto);
        int cantidadInt = Integer.parseInt(cantidad.getText().toString());

        if (cantidadInt == 0) {
            Toast.makeText(getContext(), "Mijo no puedes vender ordenadores",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        cantidadInt--;

        this.cantidad.setText(String.valueOf(cantidadInt));
        total.setText(String.valueOf(precioInt * cantidadInt) + " $");
    }
}
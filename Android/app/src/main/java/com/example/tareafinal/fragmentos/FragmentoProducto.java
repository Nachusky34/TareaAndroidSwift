package com.example.tareafinal.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tareafinal.R;
import com.example.tareafinal.controladores.ControladorCompras;
import com.example.tareafinal.db.Compra;
import com.example.tareafinal.db.Ordenador;
import com.example.tareafinal.db.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoProducto extends Fragment {
    private ImageView imagen;
    private TextView precio, cantidad, total, descripcion, nombre, sumar, restar, tv_agregarCarrito;
    private LinearLayout layout_agregar_carrito;
    private Usuario usuario;
    private Ordenador ordenador;
    private ControladorCompras controladorCompras;
    Bundle bundle;

    public FragmentoProducto() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();
        usuario = (Usuario) bundle.getSerializable("usuario");
        ordenador = (Ordenador) bundle.getSerializable("ordenador");
        controladorCompras = new ControladorCompras();
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
        total = view.findViewById(R.id.total);
        sumar = view.findViewById(R.id.tv_sumar);
        restar = view.findViewById(R.id.tv_restar);
        layout_agregar_carrito = view.findViewById(R.id.layout_agregarCarrito);
        tv_agregarCarrito = view.findViewById(R.id.tv_agregar_carrito2);

        sumar.setOnClickListener(v -> sumar());
        restar.setOnClickListener(v -> restar());

        layout_agregar_carrito.setOnClickListener(v -> agregarAlCarrito());
        tv_agregarCarrito.setOnClickListener(v -> agregarAlCarrito());

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


        // Inflate the layout for this fragment
        return view;
    }

    public void agregarAlCarrito() {
        Compra compra = new Compra();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String idCompra = usuario.getId() + "-" + ordenador.getId();

        compra.setIdUsuario(usuario.getId());
        compra.setIdProducto(ordenador.getId());
        compra.setCantidad(cantidad.getText().toString());
        compra.setComprado(false);
        compra.setFecha(dateFormat.format(calendar.getTime()));
        compra.setHora(timeFormat.format(calendar.getTime()));

        Bundle nuevoBundle = new Bundle();
        nuevoBundle.putSerializable("usuario", usuario);
        nuevoBundle.putSerializable("compra", compra);
        controladorCompras.agregarCompra(compra, idCompra);

        Toast.makeText(getContext(), "Se ha agregado al carrito", Toast.LENGTH_SHORT).show();

        volverATienda();
    }

    private void volverATienda() {
        if (getParentFragmentManager().getBackStackEntryCount() > 0) {
            getParentFragmentManager().popBackStack();
        } else {
            requireActivity().getOnBackPressedDispatcher().onBackPressed(); // Si no hay más fragmentos en la pila, vuelve atrás en la actividad
        }
    }

    public void sumar() {

        String precioTexto = precio.getText().toString().replace("$", "").trim();

        int precioInt = Integer.parseInt(precioTexto);
        int catidadInt = Integer.parseInt(cantidad.getText().toString()) + 1;

        this.cantidad.setText(String.valueOf(catidadInt));
        total.setText(String.valueOf(precioInt * catidadInt) + " $");
    }

    public void restar() {
        //Eliminamos el simbolo $
        String precioTexto = precio.getText().toString().replace("$", "").trim();

        int precioInt = Integer.parseInt(precioTexto);
        int cantidadInt = Integer.parseInt(cantidad.getText().toString());

        if (cantidadInt == 1) {
            return;
        }

        cantidadInt--;

        this.cantidad.setText(String.valueOf(cantidadInt));
        total.setText(String.valueOf(precioInt * cantidadInt) + " $");
    }
}
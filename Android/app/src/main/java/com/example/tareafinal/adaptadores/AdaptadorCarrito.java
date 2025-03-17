package com.example.tareafinal.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tareafinal.R;
import com.example.tareafinal.db.Compra;
import com.example.tareafinal.db.Ordenador;

import java.util.List;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.CarritoHolder> {

    private List<Ordenador> listaOrdenadoresCarrito;
    private List<Compra> listaCompras;
    private OnItemClickListener listenerEliminar;

    public AdaptadorCarrito(List<Ordenador> listaOrdenadoresCarrito, List<Compra> listaCompras) {
        this.listaOrdenadoresCarrito = listaOrdenadoresCarrito;
        this.listaCompras = listaCompras;
    }

    public interface OnItemClickListener {
        void onItemClick(Compra compra);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listenerEliminar = listener;
    }

    @NonNull
    @Override
    public CarritoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_pc_carrito, parent, false);
        return new CarritoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoHolder holder, int position) {
        Compra compra = listaCompras.get(position);
        String idProducto = compra.getIdProducto();

        // Buscar el ordenador correspondiente a esta compra
        Ordenador ordenador = obtenerOrdenadorPorId(idProducto);

        if (ordenador != null) {
            String precioStr = ordenador.getPrecio();
            Double precio = Double.parseDouble(precioStr);

            String cantidadStr = compra.getCantidad();
            Double cantidad = Double.parseDouble(cantidadStr);

            holder.tvNombreOrdenador.setText(ordenador.getNombre());
            holder.tvCantidad.setText(String.valueOf(compra.getCantidad()));
            holder.tvPrecioTotal.setText(String.format("%.2f $", (cantidad * precio)));

            // Cargar imagen
            Context context = holder.itemView.getContext();
            int imageResource = context.getResources().getIdentifier(ordenador.getImg(), "drawable", context.getPackageName());
            if (imageResource != 0) {
                holder.imageOrdenador.setImageDrawable(ContextCompat.getDrawable(context, imageResource));
            } else {
                holder.imageOrdenador.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ordenador10)); // Imagen por defecto
            }

            // Animación
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.inflador_recyclerview);
            holder.itemView.startAnimation(animation);

            // HACER QUE SOLO EL BOTÓN SEA CLICKEABLE
            holder.btnEliminar.setOnClickListener(v -> {
                if (listenerEliminar != null) {
                    listenerEliminar.onItemClick(compra);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return Math.min(listaCompras.size(), listaOrdenadoresCarrito.size());
    }

    private Ordenador obtenerOrdenadorPorId(String idProducto) {
        for (Ordenador ordenador : listaOrdenadoresCarrito) {
            if (ordenador.getId().equals(idProducto)) {
                return ordenador;
            }
        }
        return null;
    }

    public static class CarritoHolder extends RecyclerView.ViewHolder {
        ImageView imageOrdenador;
        TextView tvNombreOrdenador, tvPrecioTotal, tvCantidad;
        ImageButton btnEliminar;

        public CarritoHolder(@NonNull View itemView) {
            super(itemView);
            imageOrdenador = itemView.findViewById(R.id.iv_pc);
            tvNombreOrdenador = itemView.findViewById(R.id.tv_namepc);
            tvPrecioTotal = itemView.findViewById(R.id.tv_price_carrito2);
            tvCantidad = itemView.findViewById(R.id.tv_num_cantidad_carrito);
            btnEliminar = itemView.findViewById(R.id.btn_papelera);
        }
    }

    public void eliminarCompra(Compra compra) {
        int position = listaCompras.indexOf(compra);
        if (position != -1) {
            listaCompras.remove(position);
            listaOrdenadoresCarrito.remove(position);  // Asegúrate de eliminar el ordenador correspondiente también
            notifyItemRemoved(position);
        }
    }
}



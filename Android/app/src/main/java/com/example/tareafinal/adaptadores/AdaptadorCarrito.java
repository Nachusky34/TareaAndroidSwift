package com.example.tareafinal.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tareafinal.R;
import com.example.tareafinal.db.Compra;
import com.example.tareafinal.db.Ordenador;

import java.util.List;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.CarritoHolder> {

    private List<Ordenador> listaOrdenadoresCarrito;
    private List<Compra> listaCompras;
    private OnItemClickListener listenerEliminar;

    private int[] imgOrdenadores = {R.drawable.ordenador1, R.drawable.ordenador2, R.drawable.ordenador3,
            R.drawable.ordenador4, R.drawable.ordenador5, R.drawable.ordenador6,
            R.drawable.ordenador7, R.drawable.ordenador8, R.drawable.ordenador9, R.drawable.ordenador10};

    public AdaptadorCarrito(List<Ordenador> listaOrdenadoresCarrito, List<Compra> listaCompras) {
        this.listaOrdenadoresCarrito = listaOrdenadoresCarrito;
        this.listaCompras = listaCompras;
    }

    public interface OnItemClickListener {
        void onItemDelete(int position);
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
        Ordenador ordenador = listaOrdenadoresCarrito.get(position);
        Compra compra = listaCompras.get(position);

        holder.tvNombreOrdenador.setText(ordenador.getName());
        holder.tvCantidad.setText(String.valueOf(compra.getCantidad()));
        holder.tvPrecioTotal.setText((compra.getCantidad() * ordenador.getPrice()) + "$");

        int imgIndex = ordenador.getId() % imgOrdenadores.length;
        holder.imageOrdenador.setImageResource(imgOrdenadores[imgIndex]);

        // HACER QUE SOLO EL BOTÓN SEA CLICKEABLE
        holder.btnEliminar.setOnClickListener(v -> {
            if (listenerEliminar != null) {
                listenerEliminar.onItemDelete(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return Math.min(listaCompras.size(), listaOrdenadoresCarrito.size());
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

    public void eliminarItem(int position) {
        if (position >= 0 && position < listaOrdenadoresCarrito.size()) {
            listaOrdenadoresCarrito.remove(position);
            listaCompras.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listaOrdenadoresCarrito.size());
        }
    }
}


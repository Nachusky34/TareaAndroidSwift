package com.example.tareafinal.adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tareafinal.R;
import com.example.tareafinal.db.Compra;
import com.example.tareafinal.db.Ordenador;

import java.util.List;

public class AdaptadorHistorial extends RecyclerView.Adapter<AdaptadorHistorial.HistorialHolder>{

    private List<Ordenador> listaOrdenadoresHistorial;
    private List<Compra> listaCompras;
    boolean estaMarcado;


    public AdaptadorHistorial(List<Ordenador> listaOrdenadoresHistorial, List<Compra> listaCompras, boolean estaMarcado) {
        this.listaOrdenadoresHistorial = listaOrdenadoresHistorial;
        this.listaCompras = listaCompras;
        this.estaMarcado = estaMarcado;
    }

    public void setEstaMarcado(boolean estaMarcado) {
        this.estaMarcado = estaMarcado;
        notifyDataSetChanged();  // para que notiofique y se cambie el diseño del layout
    }

    public int getItemViewType(int position) {
        return estaMarcado ? 1 : 0; // 1 = Grid, 0 = Linear
    }

    @NonNull
    @Override
    public AdaptadorHistorial.HistorialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
        if (viewType == 1) {
            layout = R.layout.tarjeta_pc_historial_grid;
        } else {
            layout = R.layout.tarjeta_pc_historial;
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        HistorialHolder holder = new HistorialHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull AdaptadorHistorial.HistorialHolder holder, int position) {
        Compra compra = listaCompras.get(position);
        String idProducto = compra.getIdProducto();

        // Añadir log para depurar el idProducto
        Log.d("AdaptadorHistorial", "ID Producto en la compra: " + idProducto);

        // Buscar el ordenador correspondiente a esta compra
        Ordenador ordenador = obtenerOrdenadorPorId(idProducto);

        // Añadir log para depurar el ordenador encontrado
        if (ordenador != null) {
            Log.d("AdaptadorHistorial", "Ordenador encontrado: " + ordenador.getId());
            String precioStr = ordenador.getPrecio();
            Double precio = Double.parseDouble(precioStr);

            String cantidadStr = compra.getCantidad();
            int cantidad = Integer.parseInt(cantidadStr);

            holder.tvFechaCompra.setText(compra.getFecha());
            holder.tvHoraCompra.setText(compra.getHora());
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
        } else {
            Log.d("AdaptadorHistorial", "Ordenador no encontrado para ID: " + idProducto);
        }
    }


    // Método para obtener el ordenador por su ID
    private Ordenador obtenerOrdenadorPorId(String idProducto) {
        for (Ordenador ordenador : listaOrdenadoresHistorial) {
            if (ordenador.getId().equals(idProducto)) {
                return ordenador;
            }
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return Math.min(listaCompras.size(), listaOrdenadoresHistorial.size());
    }

    public static class HistorialHolder extends RecyclerView.ViewHolder {

        ImageView imageOrdenador;
        TextView tvNombreOrdenador, tvPrecioTotal, tvCantidad, tvFechaCompra, tvHoraCompra;

        public HistorialHolder(@NonNull View itemView) {
            super(itemView);
            imageOrdenador = itemView.findViewById(R.id.iv_pc);
            tvNombreOrdenador = itemView.findViewById(R.id.tv_namepc);
            tvPrecioTotal = itemView.findViewById(R.id.tv_price_valor);
            tvCantidad = itemView.findViewById(R.id.tv_num_cantidad);
            tvFechaCompra = itemView.findViewById(R.id.tv_fecha);
            tvHoraCompra = itemView.findViewById(R.id.tv_hora);
        }
    }

    public void actualizarDatos(List<Ordenador> nuevosDatos) {
        listaOrdenadoresHistorial = nuevosDatos;
        notifyDataSetChanged();
    }
}

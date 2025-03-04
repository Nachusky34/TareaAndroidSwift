package com.example.tareafinal.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tareafinal.R;
import com.example.tareafinal.db.Compra;
import com.example.tareafinal.db.Ordenador;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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
        String precioStr = listaOrdenadoresHistorial.get(position).getPrecio();
        Double precio = Double.parseDouble(precioStr);

        String cantidadStr = listaCompras.get(position).getCantidad();
        int cantidad = Integer.parseInt(cantidadStr);

        holder.tvFechaCompra.setText(listaCompras.get(position).getFecha());
        holder.tvHoraCompra.setText(listaCompras.get(position).getHora());
        holder.tvNombreOrdenador.setText(listaOrdenadoresHistorial.get(position).getNombre());
        holder.tvCantidad.setText(String.valueOf(listaCompras.get(position).getCantidad()));
        holder.tvPrecioTotal.setText(String.format("%.2f $",(cantidad * precio)));

        Ordenador pc = listaOrdenadoresHistorial.get(position);
        Context context = holder.itemView.getContext();

        // Obtener el ID de la imagen desde los recursos de drawable
        int imageResource = context.getResources().getIdentifier(
                pc.getImg(), "drawable", context.getPackageName());

        // Cargar imagen de forma segura con ContextCompat
        if (imageResource != 0) {
            holder.imageOrdenador.setImageDrawable(ContextCompat.getDrawable(context, imageResource));
        } else {
            holder.imageOrdenador.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ordenador10)); // Imagen por defecto
        }
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
}

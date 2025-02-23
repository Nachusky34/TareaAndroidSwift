package com.example.tareafinal.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tareafinal.R;
import com.example.tareafinal.db.Compra;
import com.example.tareafinal.db.Ordenador;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AdaptadorHistorial extends RecyclerView.Adapter<AdaptadorHistorial.HistorialHolder> implements View.OnClickListener {

    private List<Ordenador> listaOrdenadoresHistorial;
    private List<Compra> listaCompras;
    private View.OnClickListener listener;

    public AdaptadorHistorial(List<Ordenador> listaOrdenadoresHistorial, List<Compra> listaCompras) {
        this.listaOrdenadoresHistorial = listaOrdenadoresHistorial;
        this.listaCompras = listaCompras;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public AdaptadorHistorial.HistorialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_pc_historial, parent, false);
        v.setOnClickListener(this);
        return new HistorialHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorHistorial.HistorialHolder holder, int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        holder.tvFechaCompra.setText(dateFormat.format(listaCompras.get(position).getFecha()));
        holder.tvHoraCompra.setText(timeFormat.format(listaCompras.get(position).getHora()));
        holder.tvNombreOrdenador.setText(listaOrdenadoresHistorial.get(position).getName());
        holder.tvCantidad.setText(String.valueOf(listaCompras.get(position).getCantidad()));
        holder.tvPrecioTotal.setText((listaCompras.get(position).getCantidad() * listaOrdenadoresHistorial.get(position).getPrice()) + "$");

        holder.imageOrdenador.setImageResource(R.drawable.pc_prueba); // porque las imagenes estan en local
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

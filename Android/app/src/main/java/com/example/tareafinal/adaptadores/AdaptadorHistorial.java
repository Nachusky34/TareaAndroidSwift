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

public class AdaptadorHistorial extends RecyclerView.Adapter<AdaptadorHistorial.HistorialHolder>{

    private List<Ordenador> listaOrdenadoresHistorial;
    private List<Compra> listaCompras;
    private int[] imgOrdenadores = {R.drawable.ordenador1, R.drawable.ordenador2,
            R.drawable.ordenador3, R.drawable.ordenador4, R.drawable.ordenador5, R.drawable.ordenador6,
            R.drawable.ordenador7, R.drawable.ordenador8, R.drawable.ordenador9, R.drawable.ordenador10};

    public AdaptadorHistorial(List<Ordenador> listaOrdenadoresHistorial, List<Compra> listaCompras) {
        this.listaOrdenadoresHistorial = listaOrdenadoresHistorial;
        this.listaCompras = listaCompras;
    }

    @NonNull
    @Override
    public AdaptadorHistorial.HistorialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_pc_historial, parent, false);
        HistorialHolder holder = new HistorialHolder(v);
        return holder;
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

        int imgIndex = listaOrdenadoresHistorial.get(position).getId() % imgOrdenadores.length;
        holder.imageOrdenador.setImageResource(imgOrdenadores[imgIndex]); // porque las imagenes estan en local
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

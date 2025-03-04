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
import com.example.tareafinal.db.Ordenador;

import java.util.List;

public class AdaptadorTienda extends RecyclerView.Adapter<AdaptadorTienda.TiendaHolder> {
    private List<Ordenador> listaOrdenadoresTienda;
    private OnItemClickListener listener;
    private boolean estaMarcado;

    public AdaptadorTienda(List<Ordenador> listaOrdenadoresTienda, boolean estaMarcado) {
        this.listaOrdenadoresTienda = listaOrdenadoresTienda;
        this.estaMarcado = estaMarcado;
    }

    public interface OnItemClickListener {
        void onItemClick(Ordenador ordenador);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setEstaMarcado(boolean estaMarcado) {
        this.estaMarcado = estaMarcado;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return estaMarcado ? 1 : 0; // 1 = Grid, 0 = Linear
    }

    @NonNull
    @Override
    public TiendaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = (viewType == 1) ? R.layout.tarjeta_pc_tienda_grid : R.layout.tarjeta_pc_tienda;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new TiendaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TiendaHolder holder, int position) {
        Ordenador pc = listaOrdenadoresTienda.get(position);
        Context context = holder.itemView.getContext();

        //Obtenemos la imagen de la carpeta drawable
        int imageResource = context.getResources().getIdentifier(
                pc.getImg(), "drawable", context.getPackageName());

        if (imageResource != 0) {
            holder.imageOrdenador.setImageDrawable(ContextCompat.getDrawable(context, imageResource));
        } else {
            holder.imageOrdenador.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ordenador10));
        }

        holder.tvNombreOrdenador.setText(pc.getNombre());
        Double precio = Double.parseDouble(pc.getPrecio());
        holder.tvPrecio.setText(String.format("%.2f $", precio));


        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(pc);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaOrdenadoresTienda.size();
    }

    public static class TiendaHolder extends RecyclerView.ViewHolder {
        ImageView imageOrdenador;
        TextView tvNombreOrdenador, tvPrecio;

        public TiendaHolder(@NonNull View itemView) {
            super(itemView);
            imageOrdenador = itemView.findViewById(R.id.iv_pc);
            tvNombreOrdenador = itemView.findViewById(R.id.tv_namepc);
            tvPrecio = itemView.findViewById(R.id.tv_price_valor);
        }
    }
}
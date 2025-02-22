package com.example.tareafinal.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tareafinal.R;
import com.example.tareafinal.db.Ordenador;

import java.util.List;

public class AdaptadorTienda extends RecyclerView.Adapter<AdaptadorTienda.TiendaHolder> implements View.OnClickListener{

    public List<Ordenador> listaOrdenadores;
    public View.OnClickListener listener;

    public AdaptadorTienda(List<Ordenador> listaOrdenadores) {
        this.listaOrdenadores = listaOrdenadores;
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
    public AdaptadorTienda.TiendaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_pc_tienda, parent, false);
        TiendaHolder holder = new TiendaHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorTienda.TiendaHolder holder, int position) {
        holder.imageOrdenador.setImageResource(listaOrdenadores.get(position).getImg());
        holder.tvNombreOrdenador.setText(listaOrdenadores.get(position).getName());
        holder.tvPrecio.setText(listaOrdenadores.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return listaOrdenadores.size();
    }

    public class TiendaHolder extends RecyclerView.ViewHolder {

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

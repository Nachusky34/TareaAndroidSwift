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

public class AdaptadorTienda extends RecyclerView.Adapter<AdaptadorTienda.TiendaHolder> implements View.OnClickListener{

    public List<Ordenador> listaOrdenadoresTienda;
    public View.OnClickListener listener;
    boolean estaMarcado;

    public AdaptadorTienda(List<Ordenador> listaOrdenadoresTienda, boolean estaMarcado) {
        this.listaOrdenadoresTienda = listaOrdenadoresTienda;
        this.estaMarcado = estaMarcado;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setEstaMarcado(boolean estaMarcado) {
        this.estaMarcado = estaMarcado;
        notifyDataSetChanged();  // para que notiofique y se cambie el diseño del layout
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return estaMarcado ? 1 : 0; // 1 = Grid, 0 = Linear
    }

    @NonNull
    @Override
    public AdaptadorTienda.TiendaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
        if (viewType == 1) {
            layout = R.layout.tarjeta_pc_tienda_grid;
        } else {
            layout = R.layout.tarjeta_pc_tienda;
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        TiendaHolder holder = new TiendaHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorTienda.TiendaHolder holder, int position) {
        Ordenador pc = listaOrdenadoresTienda.get(position);
        Context context = holder.itemView.getContext();

        // 🔹 Obtener el ID de la imagen desde los recursos de drawable
        int imageResource = context.getResources().getIdentifier(
                pc.getImg(), "drawable", context.getPackageName());

        // Cargar imagen de forma segura con ContextCompat
        if (imageResource != 0) {
            holder.imageOrdenador.setImageDrawable(ContextCompat.getDrawable(context, imageResource));
        } else {
            holder.imageOrdenador.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ordenador10)); // Imagen por defecto
        }

        holder.tvNombreOrdenador.setText(pc.getNombre());
        holder.tvPrecio.setText(pc.getPrecio() + " $");
    }


    @Override
    public int getItemCount() {
        return listaOrdenadoresTienda.size();
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

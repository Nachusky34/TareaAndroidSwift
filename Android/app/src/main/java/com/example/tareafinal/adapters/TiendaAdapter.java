package com.example.tareafinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tareafinal.R;
import com.example.tareafinal.db.Ordenador;

import java.util.List;

public class TiendaAdapter extends RecyclerView.Adapter<TiendaAdapter.TiendaViewHolder> implements View.OnClickListener {

    private List<Ordenador> pcs;
    private View.OnClickListener listener;
    private Context context;


    public TiendaAdapter(List<Ordenador> pcs) {
        this.pcs = pcs;
    }

    @NonNull
    @Override
    public TiendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_pc_lista, parent, false);
        view.setOnClickListener(this);
        return new TiendaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TiendaAdapter.TiendaViewHolder holder, int position) {
        Ordenador tienda = pcs.get(position);
        holder.imagePc.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pc_prueba));
        holder.nombre.setText(tienda.getName());
        holder.textPrice.setText("Precio");
        holder.precio.setText(String.valueOf(tienda.getPrice()));
    }

    @Override
    public int getItemCount() {
        return pcs.size();
    }

    static class TiendaViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePc;
        TextView nombre;
        TextView textPrice;
        TextView precio;

        public TiendaViewHolder(@NonNull View itemView){
            super(itemView);
            imagePc = itemView.findViewById(R.id.iv_pc);
            nombre = itemView.findViewById(R.id.tv_namepc);
            textPrice = itemView.findViewById(R.id.tv_price1);
            precio = itemView.findViewById(R.id.tv_price2);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {this.listener = listener;}

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }
}

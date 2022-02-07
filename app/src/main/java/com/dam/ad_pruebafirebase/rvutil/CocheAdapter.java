package com.dam.ad_pruebafirebase.rvutil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.ad_pruebafirebase.R;
import com.dam.ad_pruebafirebase.model.Coche;

import java.util.ArrayList;

public class CocheAdapter extends RecyclerView.Adapter<CocheAdapter.CocheVH>{

    private ArrayList<Coche> datos;

    public CocheAdapter(ArrayList<Coche> datos) {
        this.datos = datos;
    }

    @NonNull
    @Override
    public CocheVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coche_layout,parent,false);
        CocheVH cvh = new CocheVH(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CocheAdapter.CocheVH holder, int position) {
        holder.bindCoche(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class CocheVH extends RecyclerView.ViewHolder{
        TextView tvMarca;
        TextView tvModelo;

        public CocheVH(@NonNull View itemView) {
            super(itemView);
            tvMarca = itemView.findViewById(R.id.tvMarca);
            tvModelo = itemView.findViewById(R.id.tvModelo);
        }

        public void bindCoche(Coche coche){
            tvMarca.setText(coche.getMarca());
            tvModelo.setText(coche.getModelo());
        }
    }
}

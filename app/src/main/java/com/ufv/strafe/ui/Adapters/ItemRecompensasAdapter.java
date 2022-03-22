package com.ufv.strafe.ui.Adapters;



import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ufv.strafe.R;
import com.ufv.strafe.controller.JogarController;

import java.util.ArrayList;
import java.util.Map;

public class ItemRecompensasAdapter extends RecyclerView.Adapter<ItemRecompensasAdapter.ItemColetaHolder>{
    private Map<String, Double> recompensas;
    private ArrayList<String> apostas;
    private JogarController controller;

    public ItemRecompensasAdapter(Map<String, Double> recompensas, JogarController controller){
       this.recompensas =  recompensas;
       apostas = new ArrayList<>(recompensas.keySet());
       this.controller = controller;
    }
    @NonNull
    @Override
    public ItemColetaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_coleta, parent, false);
        return new ItemColetaHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ItemColetaHolder holder, int position) {
        String aposta = apostas.get(position);
        Double valor = recompensas.get(aposta);

        holder.valor.setText(String.valueOf(valor));
        holder.button.setOnClickListener(view -> {
            controller.updateAposta(aposta, valor);
            apostas.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return  apostas.size();
    }

    public class ItemColetaHolder extends RecyclerView.ViewHolder {
        TextView valor;
        Button button;

        public ItemColetaHolder(@NonNull View itemView) {
            super(itemView);
            valor = itemView.findViewById(R.id.valor_coleta);
            button = itemView.findViewById(R.id.btn_coletar);
        }
    }

    public void updateAdapter(Map<String, Double> recompensas ){
        this.recompensas =  recompensas;
        apostas = new ArrayList<>(recompensas.keySet());
    }
}

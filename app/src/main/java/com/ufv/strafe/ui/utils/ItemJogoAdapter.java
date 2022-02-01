package com.ufv.strafe.ui.utils;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.ufv.strafe.R;
import java.util.ArrayList;
import java.util.Map;

public class ItemJogoAdapter extends RecyclerView.Adapter<ItemJogoAdapter.ViewItemJogoHolder> {

    String[] data1;
    public MutableLiveData<Map<String, Boolean>> jogos = new MutableLiveData<>();
    private ArrayList<Integer> cores;


    public ItemJogoAdapter(String[] nomes, ArrayList<Integer> cores, Map<String, Boolean> jogos) {
        this.data1 = nomes;
        this.jogos.setValue(jogos);
        this.cores = cores;
    }


    @NonNull
    @Override
    public ViewItemJogoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Exibe um item de uma recyclerview
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_jogo, parent, false);
        return new ViewItemJogoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewItemJogoHolder holder, int position) {
        //Constroi cada item de uma recyclerview
        holder.nomeJogo.setText(data1[position]);
        holder.color.setBackgroundResource(cores.get(position));
        boolean jogoCheck = jogos.getValue().get(String.valueOf(holder.nomeJogo.getText()));
        holder.checkBox.setChecked(jogoCheck);


    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class ViewItemJogoHolder extends RecyclerView.ViewHolder {
        TextView nomeJogo;
        TextView color;
        CheckBox checkBox;


        public ViewItemJogoHolder(@NonNull View itemView) {
            super(itemView);

            nomeJogo = itemView.findViewById(R.id.label_checkbox);
            color = itemView.findViewById(R.id.check_color);
            checkBox = itemView.findViewById(R.id.checkbox);

            itemView.setOnClickListener(view -> {
                checkBox.setChecked(!checkBox.isChecked());
                jogos.getValue().put(nomeJogo.getText().toString(), checkBox.isChecked());
            });

            checkBox.setOnClickListener(view ->  jogos.getValue().put(nomeJogo.getText().toString(), checkBox.isChecked()));



        }
    }

}

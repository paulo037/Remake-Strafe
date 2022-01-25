package com.ufv.strafe.utils;


import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.ufv.strafe.R;
import com.ufv.strafe.entities.usuario.UsuarioViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.grpc.Context;

public class ItemJogoAdapter extends RecyclerView.Adapter<ItemJogoAdapter.ViewItemJogoHolder> {

    String data1[];
    Context context;
    public Map<String, Boolean> jogos = new HashMap<String, Boolean>();
    public  UsuarioViewModel viewModel;
    private ArrayList<Integer> cores = new ArrayList<Integer>();


    public ItemJogoAdapter(Context ct, String nomes[], ArrayList<Integer> cores, ViewModelStoreOwner owner) {
        context = ct;
        data1 = nomes;
        viewModel =  new ViewModelProvider(owner).get(UsuarioViewModel.class);
        jogos = new HashMap<String, Boolean>();
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
        holder.checkBox.setChecked(viewModel.usuario.getValue().jogos.get(holder.nomeJogo.getText().toString()));
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkBox.setChecked(!checkBox.isChecked());
                    jogos.put(nomeJogo.getText().toString(), checkBox.isChecked());
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jogos.put(nomeJogo.getText().toString(), checkBox.isChecked());
                }
            });


        }
    }

}

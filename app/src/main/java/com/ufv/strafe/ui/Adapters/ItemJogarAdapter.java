
package com.ufv.strafe.ui.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ufv.strafe.R;
import com.ufv.strafe.model.Partida;
import com.ufv.strafe.ui.fragmentos.ApostarFragment;
import com.ufv.strafe.ui.fragmentos.JogarFragmentDirections;

import java.util.ArrayList;
import java.util.List;


public class ItemJogarAdapter extends RecyclerView.Adapter<ItemJogarAdapter.ViewItemJogarHolder> {


    private ArrayList<Integer> icons;
    private List<Partida> partidas;
    private ArrayList<String> nomeJogos;
    private Context context;


    public ItemJogarAdapter(ArrayList<Integer> icons,
                            List<Partida> partidas,
                            ArrayList<String> nomeJogos,
                            Context context){
        this.icons = icons;
        this.partidas = partidas;
        this.nomeJogos = nomeJogos;
        this.context = context;
    }

    public void updateAdapter(ArrayList<Integer> icons,
                              List<Partida> partidas,
                              ArrayList<String> nomeJogos
                             ){
        this.icons = icons;
        this.partidas = partidas;
        this.nomeJogos = nomeJogos;
    }

    @NonNull
    @Override
    public ViewItemJogarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_e_esports_jogar, parent, false);
        return new ViewItemJogarHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewItemJogarHolder holder, int position) {
        int qtd = 0;

        if (partidas != null){
            for (Partida partida : partidas){
                if (partida.getJogo().equals(nomeJogos.get(position))){
                    qtd++;
                }
            }
        }

        holder.itemView.setOnClickListener(view -> {
             JogarFragmentDirections.ActionApostar action = JogarFragmentDirections
                    .actionApostar();
             action.setJogo(nomeJogos.get(position));
            holder.navController.navigate(action);

        });

        holder.imageView.setImageResource(icons.get(position));
        holder.textView.setText(String.valueOf(qtd));
    }

    @Override
    public int getItemCount() {
        return icons.size();
    }

    public class ViewItemJogarHolder extends  RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        NavController navController;


        public ViewItemJogarHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon_tela_jogar);
            textView = itemView.findViewById(R.id.qtd_partidas);

            navController =  Navigation.findNavController((Activity) context, R.id.nav_host_fragment);

        }


    }


}

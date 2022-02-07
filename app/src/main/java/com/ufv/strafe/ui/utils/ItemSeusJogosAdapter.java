
package com.ufv.strafe.ui.utils;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ufv.strafe.R;

import java.util.ArrayList;

public class ItemSeusJogosAdapter extends RecyclerView.Adapter<ItemSeusJogosAdapter.ViewItemJogarHolder> {


    private ArrayList<Integer> icons;


    public ItemSeusJogosAdapter(ArrayList<Integer> icons){
        this.icons = icons;
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

        holder.imageView.setImageResource(icons.get(position));
    }

    @Override
    public int getItemCount() {
        return icons.size();
    }

    public class ViewItemJogarHolder extends  RecyclerView.ViewHolder {
        ImageView imageView;


        public ViewItemJogarHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon_tela_jogar);
        }


    }


}

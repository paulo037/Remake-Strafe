
package com.ufv.strafe.ui.utils;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ufv.strafe.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemPerfilAdapter extends RecyclerView.Adapter<ItemPerfilAdapter.ViewItemPerfilHolder> {


    private ArrayList<Integer> icons;


    public ItemPerfilAdapter(ArrayList<Integer> icons){
        this.icons = icons;
    }

    @NonNull
    @Override
    public ViewItemPerfilHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_jogo_perfil, parent, false);
        return new ViewItemPerfilHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewItemPerfilHolder holder, int position) {

        holder.imageView.setImageResource(icons.get(position));
    }

    @Override
    public int getItemCount() {
        return icons.size();
    }

    public class ViewItemPerfilHolder extends  RecyclerView.ViewHolder {
        CircleImageView imageView;


        public ViewItemPerfilHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon_jogo_perfil);
        }


    }


}

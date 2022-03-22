
package com.ufv.strafe.ui.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ufv.strafe.R;
import com.ufv.strafe.model.Noticia;
import com.ufv.strafe.model.Partida;
import com.ufv.strafe.model.utils.Resource;
import com.ufv.strafe.ui.fragmentos.JogarFragmentDirections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ItemNoticiaAdapter extends RecyclerView.Adapter<ItemNoticiaAdapter.ViewItemNoticiaHolder> {


    private List<Noticia> noticias;
    private Context context;



    public ItemNoticiaAdapter(
            List<Noticia> noticias,
            Context context) {
        this.noticias = noticias;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewItemNoticiaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_noticia, parent, false);
        return new ViewItemNoticiaHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewItemNoticiaHolder holder, int position) {
        Noticia noticia = noticias.get(position);

        holder.titulo.setText(noticia.getTitulo());
        holder.jogo.setText(noticia.getJogo());
        int color = Resource.getColorByName(noticia.getJogo(), context);
        holder.jogo.setTextColor(context.getResources().getColor(color));
        holder.introducao.setText(noticia.getTexto().substring(0, 80).concat("..."));
        Picasso.get()
                .load(noticia.getImg())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", noticia.getId());
            holder.navController.navigate(R.id.action_page_feed_to_readNoticiaFragment, bundle);
        });


    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public class ViewItemNoticiaHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titulo;
        TextView introducao;
        TextView jogo;
        NavController navController;


        public ViewItemNoticiaHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            titulo = itemView.findViewById(R.id.titulo);
            introducao = itemView.findViewById(R.id.introducao);
            jogo = itemView.findViewById(R.id.jogo);
            navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment);

        }


    }


}

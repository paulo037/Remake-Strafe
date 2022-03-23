
package com.ufv.strafe.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ufv.strafe.R;
import com.ufv.strafe.model.Video;
import com.ufv.strafe.utils.Resource;

import java.util.List;


public class ItemVideoAdapter extends RecyclerView.Adapter<ItemVideoAdapter.ViewItemVideoHolder> {


    private List<Video> videos;
    private Context context;



    public ItemVideoAdapter(
            List<Video> videos,
            Context context) {
        this.videos = videos;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewItemVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_video, parent, false);
        return new ViewItemVideoHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewItemVideoHolder holder, int position) {
        Video video = videos.get(position);

        holder.titulo.setText(video.getTitulo());
        holder.jogo.setText(video.getJogo());
        int color = Resource.getColorByName(video.getJogo(), context);
        holder.jogo.setTextColor(context.getResources().getColor(color));
        holder.data.setText(video.getData());

        Picasso.get()
                .load(video.getThumbnail())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getFonte()));
            context.startActivity(browserIntent);
        });


    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ViewItemVideoHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titulo;
        TextView data;
        TextView jogo;



        public ViewItemVideoHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            titulo = itemView.findViewById(R.id.titulo);
            data = itemView.findViewById(R.id.data);
            jogo = itemView.findViewById(R.id.jogo);
        }


    }


}

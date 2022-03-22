package com.ufv.strafe.ui.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.ufv.strafe.R;
import com.ufv.strafe.model.Partida;
import com.ufv.strafe.model.utils.Resource;
import com.ufv.strafe.ui.fragmentos.CalendarioFragmentDirections;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ItemPartidaAdapter extends  RecyclerView.Adapter<ItemPartidaAdapter.ViewItemPartidaHolder> {

    private List<Partida> partidas;
    private Context context;
    public ItemPartidaAdapter(List<Partida> partidas, Context context){
        this.partidas = partidas;
       this.context = context;
    }

    @NonNull
    @Override
    public ViewItemPartidaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_partida_calendario, parent, false);
        return new ItemPartidaAdapter.ViewItemPartidaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewItemPartidaHolder holder, int position) {
        Partida partida = partidas.get(position);

        holder.time1.setText(partida.getTime1());
        holder.time2.setText(partida.getTime2());

        int cor = Resource.getColorByName(partida.getJogo(), context);
        holder.colorJogo.setBackgroundResource(cor);

        int icon = Resource.getIconsByNameJogo(partida.getJogo(), context);
        holder.imageView.setImageResource(icon);

        holder.horario.setText(partida.getDataIncio().substring(11, 16));
        if (holder.saldoT1.getText().toString().equals("-1") && holder.saldoT2.getText().toString().equals("-1")){
            holder.saldoT1.setText("");
            holder.saldoT2.setText("");
        }
        Date now = new Date();
        Date pDate = null;
        try {
            pDate =  new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(partida.getDataFim());
        } catch (ParseException ignored) {

        }
        if (now.before(pDate)){
            holder.itemView.setOnClickListener(view -> {
                Bundle b = new Bundle();
                b.putString("idPartida", partida.getId());

                holder.navController.navigate(R.id.action_calendario_apostar,b);

            });
        }
    }

    @Override
    public int getItemCount() {
        return partidas.size();
    }

    public class ViewItemPartidaHolder extends RecyclerView.ViewHolder {
        TextView time1, time2, saldoT1, saldoT2, colorJogo, horario;
        ImageView imageView;
        NavController navController;
        public ViewItemPartidaHolder(@NonNull View itemView) {
            super(itemView);
            time1 = itemView.findViewById(R.id.jogo1_calendar);
            time2 = itemView.findViewById(R.id.jogo2_calendar);
            saldoT1 = itemView.findViewById(R.id.placar_jogo1);
            saldoT2 = itemView.findViewById(R.id.placar_jogo2);
            imageView = itemView.findViewById(R.id.icon_calendar);
            colorJogo = itemView.findViewById(R.id.color_calendario_jogo);
            horario = itemView.findViewById(R.id.horario_partida);
            navController =  Navigation.findNavController((Activity) itemView.getContext(), R.id.nav_host_fragment);

        }
    }
}

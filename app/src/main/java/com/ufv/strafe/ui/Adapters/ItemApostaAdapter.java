
package com.ufv.strafe.ui.Adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ufv.strafe.R;
import com.ufv.strafe.model.Partida;
import com.ufv.strafe.utils.FormataData;
import com.ufv.strafe.ui.fragmentos.ApostarFragment;
import com.ufv.strafe.ui.fragmentos.DialogApostaFragment;

import java.util.List;

public class ItemApostaAdapter extends RecyclerView.Adapter<ItemApostaAdapter.ViewItemPerfilHolder> {


    private List<Partida>  partidas;
    private  ApostarFragment fragment;


    public ItemApostaAdapter(List<Partida>  partidas, ApostarFragment fragment){
        this.partidas = partidas;
        this.fragment =fragment;
    }

    @NonNull
    @Override
    public ViewItemPerfilHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_aposta, parent, false);
        return new ViewItemPerfilHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewItemPerfilHolder holder, int position) {
        Partida partida = partidas.get(position);

        holder.dataFim.setText(FormataData.getDataFormatada(partida.getDataFim()));

        holder.dataInicio.setText(FormataData.getDataFormatada(partida.getDataInicio()));

        holder.multiplicador1.setText(String.valueOf(partida.calcularMultiplicador(partida.getTime1())));
        holder.multiplicador2.setText(String.valueOf(partida.calcularMultiplicador(partida.getTime2())));

        holder.time1.setText(partida.getTime1());
        holder.time2.setText(partida.getTime2());

        holder.cardView1.setOnClickListener(view -> {
            DialogApostaFragment dialog = new DialogApostaFragment(partida, partida.getTime1());
            dialog.show(fragment.getActivity().getSupportFragmentManager(), dialog.getTag());

        });
        holder.cardView2.setOnClickListener(view -> {
            DialogApostaFragment dialog = new DialogApostaFragment(partida, partida.getTime2());
            dialog.show(fragment.getParentFragmentManager(), dialog.getTag() );

        });


    }

    @Override
    public int getItemCount() {
        return this.partidas.size();
    }

    public class ViewItemPerfilHolder extends  RecyclerView.ViewHolder {
        TextView dataFim;
        TextView dataInicio;
        TextView multiplicador1;
        TextView multiplicador2;
        TextView time1;
        TextView time2;
        CardView cardView1;
        CardView cardView2;


        public ViewItemPerfilHolder(@NonNull View itemView) {
            super(itemView);
            dataFim = itemView.findViewById(R.id.dataFimPartida);
            dataInicio = itemView.findViewById(R.id.dataInicioPartida);
            multiplicador1  = itemView.findViewById(R.id.multiplicador1);
            multiplicador2  = itemView.findViewById(R.id.multiplicador2);
            time1 = itemView.findViewById(R.id.time1);
            time2 = itemView.findViewById(R.id.time2);
            cardView1 = itemView.findViewById(R.id.cardView1);
            cardView2 = itemView.findViewById(R.id.cardView2);


        }


    }


}

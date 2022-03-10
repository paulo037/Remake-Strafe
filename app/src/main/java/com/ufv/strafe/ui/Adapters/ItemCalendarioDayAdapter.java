package com.ufv.strafe.ui.Adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ufv.strafe.R;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class ItemCalendarioDayAdapter extends RecyclerView.Adapter<ItemCalendarioDayAdapter.ViewItemJogoHolder> {

    private  ArrayList<Date> datas;
    public MutableLiveData<Integer> row_index = new MutableLiveData<>();



    public ItemCalendarioDayAdapter(ArrayList<Date> datas) {
        this.datas = datas;
        row_index.setValue(-1);
    }



    @NonNull
    @Override
    public ViewItemJogoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Exibe um item de uma recyclerview
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_calendario, parent, false);
        return new ViewItemJogoHolder(view);
    }

    @Override
    @SuppressLint({"SimpleDateFormat", "NotifyDataSetChanged"})
    public void onBindViewHolder(@NonNull ViewItemJogoHolder holder, @SuppressLint("RecyclerView") int position) {
        Calendar calendar = Calendar.getInstance();
        Date date = datas.get(position);
        calendar.setTime(date);


       SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        if (sf.format(date).equals( sf.format(new Date()))){
            holder.dia_semana.setText("HOJE");
            if (row_index.getValue() == -1) row_index.setValue(position);
        }else{
            DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
            holder.dia_semana.setText(dateFormatSymbols.getShortWeekdays()[calendar.get(Calendar.DAY_OF_WEEK)].toUpperCase(Locale.ROOT));
        }

        holder.dia_mes.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        if(row_index.getValue() == position){
            holder.layout.setBackgroundColor(Color.parseColor("#201F1F"));
            holder.dia_mes.setTextColor(Color.parseColor("#ffffff"));
            holder.dia_semana.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.layout.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.dia_mes.setTextColor(Color.parseColor("#201F1F"));
            holder.dia_semana.setTextColor(Color.parseColor("#201F1F"));
        }

        holder.layout.setOnClickListener(view -> {
            row_index.setValue(position);
            notifyDataSetChanged();
        });

    }






    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewItemJogoHolder extends RecyclerView.ViewHolder {
        TextView dia_semana;
        TextView dia_mes;
        ConstraintLayout layout;
        RecyclerView recyclerView;


        public ViewItemJogoHolder(@NonNull View itemView) {
            super(itemView);
            dia_semana = itemView.findViewById(R.id.dia_semana);
            dia_mes = itemView.findViewById(R.id.dia_mes);
            layout = itemView.findViewById(R.id.back_ground);
            recyclerView = itemView.findViewById(R.id.recycler_dias);


        }
    }

}

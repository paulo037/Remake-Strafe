package com.ufv.strafe.ui.Adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeusDesafiosAdapter extends RecyclerView.Adapter<SeusDesafiosAdapter .SeusDesafiosAdapterHolder>{


    @NonNull
    @Override
    public SeusDesafiosAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SeusDesafiosAdapterHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SeusDesafiosAdapterHolder extends RecyclerView.ViewHolder {
        public SeusDesafiosAdapterHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

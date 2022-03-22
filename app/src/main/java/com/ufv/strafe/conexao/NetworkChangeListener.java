package com.ufv.strafe.conexao;

import static com.ufv.strafe.conexao.ConexaoController.IsConnected;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.ufv.strafe.R;

public class NetworkChangeListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!IsConnected(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.no_conexao, null);
            builder.setView(view);

            Button btn = view.findViewById(R.id.btn_recarregar);

            AlertDialog dialog = builder.create();
            dialog.show();

            dialog.setCancelable(false);
            dialog.getWindow().setGravity(Gravity.CENTER);


            btn.setOnClickListener(view1 -> {
                dialog.dismiss();
                onReceive(context, intent);
            });
        }

    }
}

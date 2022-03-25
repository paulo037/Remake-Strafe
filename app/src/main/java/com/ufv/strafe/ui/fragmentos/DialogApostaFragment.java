package com.ufv.strafe.ui.fragmentos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.ufv.strafe.R;
import com.ufv.strafe.controller.DialogApostaController;
import com.ufv.strafe.model.Partida;

public class DialogApostaFragment extends DialogFragment {
    private Partida partida;
    private String time;
    private Dialog dialog;
    private DialogApostaController controller;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public DialogApostaFragment(Partida partida, String time) {
        this.partida = partida;
        this.time = time;
        this.controller = new DialogApostaController(partida.getId());
    }


     @RequiresApi(api = Build.VERSION_CODES.N)
     @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_confirmar_aposta, null));
        dialog = builder.create();



        return dialog;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirmar_aposta, container);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onStart() {
        super.onStart();
       getDialog().getWindow().setLayout(
               WindowManager.LayoutParams.MATCH_PARENT,
               WindowManager.LayoutParams.WRAP_CONTENT
        );

        TextView textTime = dialog.findViewById(R.id.time_aposta);
        textTime.setText(String.format("%s %s", textTime.getText().toString(), time));
        TextInputEditText valorAposta = dialog.findViewById(R.id.entry_valor_aposta);
        Button confirmar = dialog.findViewById(R.id.btn_confirmar);
        Button cancelar = dialog.findViewById(R.id.btn_cancelar);

        confirmar.setOnClickListener(v -> {
            String stValue = String.valueOf(valorAposta.getText());
            try {
                Double value =  Double.valueOf(stValue);
                if (value <= 0){
                    Toast.makeText(getContext(), "O valor tem que ser maior que 0 !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value > controller.getSaldo()){
                    Toast.makeText(getContext(), "SALDO INSUFICIENTE !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                controller.addAposta(partida.getId(), time, value, partida.calcularMultiplicador(time));
                getDialog().dismiss();
                Toast.makeText(getContext(), "Aposta feita com sucesso !", Toast.LENGTH_SHORT).show();
            }catch (NumberFormatException e){
                Toast.makeText(getContext(), "Erro, verifique se é um número válido", Toast.LENGTH_SHORT).show();
            }


        });

        cancelar.setOnClickListener(view -> getDialog().dismiss());

    }


}


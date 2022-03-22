package com.ufv.strafe.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;

import com.ufv.strafe.dao.PartidaDAO;
import com.ufv.strafe.dao.UsuarioDAO;
import com.ufv.strafe.databinding.FragmentApostarCalendarioBinding;
import com.ufv.strafe.databinding.FragmentCalendarioBinding;
import com.ufv.strafe.model.Partida;
import com.ufv.strafe.ui.Adapters.ItemPartidaAdapter;
import com.ufv.strafe.ui.fragmentos.ApostarCalendarioFragment;
import com.ufv.strafe.ui.fragmentos.DialogApostaFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarioController {
    private PartidaDAO partidaDAO;
    private UsuarioDAO usuarioDAO;
    private String day = "";
    private Map<String, Boolean> jogos = new HashMap<>();

    public CalendarioController() {
        usuarioDAO = new UsuarioDAO();
        partidaDAO = new PartidaDAO();


    }

    public CalendarioController(String idPartida) {
        partidaDAO = new PartidaDAO(idPartida);

    }

    public void notifyPartida() {
        partidaDAO.partidas.setValue(partidaDAO.partidas.getValue());
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    public List<Partida> getPartidas(String day, Map<String, Boolean> jogos) {
        List<Partida> partidas = partidaDAO.getPartidasByDate(day, jogos);
        partidas.sort((partida, tp) -> {

            Date date;
            Date t1;
            try {
                date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(partida.getDataIncio());
                t1 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(tp.getDataIncio());
            } catch (ParseException e) {
                return 1;
            }

            if (date.after(t1)) return 1;
            if (date.equals(t1)) return 0;
            if (date.before(t1)) return -1;
            return 0;
        });
        return partidas;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Observer(FragmentCalendarioBinding binding,
                         LifecycleOwner lifecycleOwner,
                         Context context) {

        usuarioDAO.getLiveData().observe(lifecycleOwner, usuario -> {
            jogos = usuario.getJogos();
            notifyPartida();
        });

        partidaDAO.partidas.observe(lifecycleOwner, partidas -> {
            partidas = getPartidas(day, jogos);
            ItemPartidaAdapter partidaAdapter = new ItemPartidaAdapter(partidas, context);
            binding.recycleJogosCalendario.setAdapter(partidaAdapter);

        });


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Date> getDatasSort() {
        ArrayList<Date> datas = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        for (int i = -15; i <= 15; i++) {
            calendar.add(Calendar.DATE, i);
            datas.add(calendar.getTime());
            calendar = Calendar.getInstance();

        }

        datas.sort((date, t1) -> {
            if (date.after(t1)) return 1;
            if (date.equals(t1)) return 0;
            if (date.before(t1)) return -1;
            return 0;
        });

        return datas;
    }


    public void setDay(String day) {
        this.day = day;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void ObserverPartida(FragmentApostarCalendarioBinding binding,
                                LifecycleOwner lifecycleOwner,
                                ApostarCalendarioFragment context) {
        partidaDAO.partida.removeObservers(lifecycleOwner);
        partidaDAO.partida.observe(lifecycleOwner, partida -> {
            binding.dataFimPartida.setText(partida.getDataFim());
            binding.dataInicioPartida.setText(partida.getDataIncio());

            binding.multiplicador1.setText(String.valueOf(partida.calcularMultiplicador(partida.getTime1())));
            binding.multiplicador2.setText(String.valueOf(partida.calcularMultiplicador(partida.getTime2())));

            binding.time1.setText(partida.getTime1());
            binding.time2.setText(partida.getTime2());

            binding.cardView1.setOnClickListener(view -> {
                DialogApostaFragment dialog = new DialogApostaFragment(partida, partida.getTime1());
                dialog.show(context.getActivity().getSupportFragmentManager(), dialog.getTag());

            });
            binding.cardView2.setOnClickListener(view -> {
                DialogApostaFragment dialog = new DialogApostaFragment(partida, partida.getTime2());
                dialog.show(context.getParentFragmentManager(), dialog.getTag());

            });

        });


    }
}

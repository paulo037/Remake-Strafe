package com.ufv.strafe.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;

import com.ufv.strafe.dao.PartidaDAO;
import com.ufv.strafe.dao.UsuarioDAO;
import com.ufv.strafe.databinding.FragmentApostarCalendarioBinding;
import com.ufv.strafe.databinding.FragmentCalendarioBinding;
import com.ufv.strafe.model.Partida;
import com.ufv.strafe.ui.Adapters.ItemCalendarioDayAdapter;
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
import java.util.Locale;
import java.util.Map;
import java.util.Random;

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

    public List<Partida> getPartidas(String day, Map<String, Boolean> jogos) {
        List<Partida> partidas = partidaDAO.getPartidasByDate(day, jogos);
        partidas.sort((partida, tp) -> {
            if (partida.getDataInicio().after(tp.getDataInicio())) return 1;
            if (partida.getDataInicio().equals(tp.getDataInicio())) return 0;
            if (partida.getDataInicio().before(tp.getDataInicio())) return -1;
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

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void ObserverPartida(FragmentApostarCalendarioBinding binding,
                                LifecycleOwner lifecycleOwner,
                                ApostarCalendarioFragment context) {
        partidaDAO.partida.removeObservers(lifecycleOwner);
        partidaDAO.partida.observe(lifecycleOwner, partida -> {

            Log.i(toString(), "Inicializando atualiazação dos adapters");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            String dataInicio = sdf.format(partida.getDataInicio());
            String dataFim = sdf.format(partida.getDataFim());
            binding.dataFimPartida.setText(dataFim);
            binding.dataInicioPartida.setText(dataInicio);

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

            Log.i(toString(), "atualiazação dos adapters concluida");

        });


    }


    public void createPartida(Partida partida){
        partidaDAO.createPartida(partida);
    }

    public void updateDay(){

    }
    //Usado Para criar os Dados fakes Contidos na parte das partidas
    public void createPartidas() {


        ArrayList<String> jogos = new ArrayList<>();
        jogos.add("Call of Duty");
        jogos.add("Counter-Strike");
        jogos.add("Dota II");
        jogos.add("Hearthstone");
        jogos.add("League of Legends");
        jogos.add("Overwatch");
        jogos.add("Rainbow 6 Siege");
        jogos.add("Rocket League");
        jogos.add("StarCraft");
        jogos.add("Valorant");

        ArrayList<ArrayList<String>> times = new ArrayList<>();
        ArrayList<String> t = new ArrayList<>();
        t = new ArrayList<>();
        t.add("MINNESOTA ROKKR");
        t.add("MUTANTES DA FLÓRIDA");
        times.add(t);
        t = new ArrayList<>();
        t.add("Cloud9");
        t.add("Virtus Pro");
        times.add(t);
        t = new ArrayList<>();
        t.add("Vici Gaming VG");
        t.add("Evil Geniuses EG");
        times.add(t);
        t = new ArrayList<>();
        t.add("Vivo Keyd");
        t.add("paiN");
        times.add(t);
        t = new ArrayList<>();
        t.add("INTZ e-Sports");
        t.add("KaBuM!");
        times.add(t);
        t = new ArrayList<>();
        t.add("London Spitfire");
        t.add("Philadelphia Fusion");
        times.add(t);
        t = new ArrayList<>();
        t.add("Ninjas in Pyjamas");
        t.add("Team One");
        times.add(t);
        t = new ArrayList<>();
        t.add("The General NRG");
        t.add("Team BDS");
        times.add(t);
        t = new ArrayList<>();
        t.add("Serral");
        t.add("Dark");
        times.add(t);

        t = new ArrayList<>();
        t.add("Acend");
        t.add("Gambit Esports");
        times.add(t);


        Random random = new Random();
        for (int j = 24; j <= 31; j++){

            for (int i = 0; i < 10; i++) {
                int h1 = random.nextInt(10) + 6;

                int pt1 = random.nextInt(3);
                int pt2 = random.nextInt(3);
                pt2 = pt2 == pt1 ? pt1 + 1 : pt2;
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Partida partida = null;
                try {
                    partida = new Partida(
                            times.get(i).get(0),
                            times.get(i).get(1),
                            jogos.get(i),
                            sdf.parse(String.valueOf(j)+ "/03/2022 " + String.valueOf(h1) + ":00"),
                            sdf.parse(String.valueOf(j)+ "/03/2022 " +String.valueOf(h1+1 + i % 2) + ":00"),
                            String.valueOf(Calendar.getInstance().getTimeInMillis()),
                            pt1,
                            pt2

                    );
                } catch (ParseException e) {

                }
                createPartida(partida);
            }



        }

    }
}

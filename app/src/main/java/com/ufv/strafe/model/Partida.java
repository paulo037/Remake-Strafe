package com.ufv.strafe.model;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Partida {
    private Time time1;
    private Time time2;
    private String jogo;
    private ArrayList<Aposta> apostas;
    private Date dataInicio;
    private Date dataFim;
    private String id;
    private Integer ptsTime1 = 0;
    private Integer ptsTime2 = 0;


    public Partida() {
        this.apostas = new ArrayList<>();
    }

    public Partida(String time1,
                   String time2,
                   String jogo,
                   Date dataInicio,
                   Date dataFim,
                   String id,
                   Integer ptsTime1,
                   Integer ptsTime2) {

        this.id = id;
        this.time1 = new Time(time1);
        this.time2 = new Time(time2);
        this.jogo = jogo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.apostas = new ArrayList<>();
        this.ptsTime1 = ptsTime1;
        this.ptsTime2 = ptsTime2;


    }

    public void addAposta(Aposta aposta) {

        apostas.add(aposta);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public Double calcularMultiplicador(String time) {
        double time2 = 1.0;
        double time1 = 1.0;

        for (Aposta e : this.apostas) {
            if (time.equals(e.getTime())) {
                time1 += e.getValor();
            } else {
                time2 += e.getValor();
            }
        }
        double value = (time2 / time1);
        if (value > 1.5) {
            return Math.round(value * 100) / 100.0;
        } else return 1.5;
    }

    public void addApostaToArrayList(Aposta aposta) {
        apostas.add(aposta);
    }

    public String getTime1() {
        return time1.toString();
    }

    public String getTime2() {
        return time2.toString();
    }

    public String getJogo() {
        return jogo;
    }

    public ArrayList<Aposta> getApostas() {
        return apostas;
    }



    public String getId() {
        return id;
    }


    public void setTime1(String time1) {
        this.time1 = new Time(time1);
    }

    public void setTime2(String time2) {
        this.time2 = new Time(time2);
    }

    public void setJogo(String jogo) {
        this.jogo = jogo;
    }

    public void setApostas(ArrayList<Aposta> apostas) {
        this.apostas = apostas;
    }


    public void setId(String id) {
        this.id = id;
    }


    public Integer getPtsTime1() {
        return ptsTime1;
    }

    public void setPtsTime1(Integer ptsTime1) {
        this.ptsTime1 = ptsTime1;
    }

    public Integer getPtsTime2() {
        return ptsTime2;
    }

    public void setPtsTime2(Integer ptsTime2) {
        this.ptsTime2 = ptsTime2;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataIncio(Date dataIncio) {
        this.dataInicio = dataIncio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Aposta getAposta(String idAposta) {
        for (Aposta aposta : this.apostas) {
            if (aposta.getId().equals(idAposta)) {
                return aposta;
            }
        }
        return null;
    }



    public Double getSaldoAposta(String idAposta) {
        try {
            Aposta aposta = getAposta(idAposta);
            if (aposta == null) {
                return 0.0;
            }
            String vencedor = ptsTime1 > ptsTime2 ? time1.toString() : time2.toString();
            if (aposta.getTime().equals(vencedor)) {
                return aposta.getMultiplicador() * aposta.getValor();
            }
        } catch (Exception e) {
            return 0.0;
        }

        return 0.0;
    }

    public Double getSaldoNApostas(ArrayList<String> apostas) {
        if (!verificaFim()) return 0.0;
        Double vAposta = 0.0;
        for (String aposta : apostas) {
            vAposta += this.getSaldoAposta(aposta);
        }
        return vAposta;
    }

    @SuppressLint("SimpleDateFormat")
    public Boolean verificaFim() {
        Date dataAtual = new Date();
        if (dataAtual.after(dataFim) || dataAtual.equals(dataFim)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }


}

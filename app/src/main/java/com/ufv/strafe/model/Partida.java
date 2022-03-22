package com.ufv.strafe.model;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class    Partida  {
    private  Time time1;
    private  Time time2;
    private  String jogo;
    private ArrayList<Aposta> apostas;
    private  String dataIncio;
    private String dataFim;
    private  String id;
    private String vencedor;


    public  Partida(){
        this.apostas = new ArrayList<>();
    }

   public Partida(String time1,
                  String time2,
                  String jogo,
                  String dataIncio,
                  String dataFim,
                  String id,
                  String vencedor)  {

       this.id = id;
       this.time1 = new Time(time1);
       this.time2 = new Time(time2);
       this.jogo = jogo;
       this.dataIncio = dataIncio;
       this.dataFim = dataFim;
       this.apostas = new ArrayList<>();
       this.vencedor = vencedor;


   }

    public void addAposta(Aposta aposta){

       apostas.add(aposta);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public Double calcularMultiplicador(String time){
        double time2 = 1.0;
        double time1 = 1.0;

        for (Aposta e : this.apostas) {
            if (time.equals(e.getTime())) {
                time1 += e.getValor();
            }
            else{
                time2 += e.getValor();
            }
        }
        double value = (time2 / time1);
        if (value > 1.5){
            return Math.round(value * 100)/100.0;
        }
        else return 1.5;
    }

    public  void addApostaToArrayList(Aposta aposta){
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

    public String getDataIncio() {
        return dataIncio;
    }

    public String getDataFim() {
        return dataFim;
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

    public void setDataIncio(String dataIncio) {
        this.dataIncio = dataIncio;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVencedor() {
        return vencedor;
    }

    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }

    public Aposta getAposta(String idAposta){
        for (Aposta aposta : this.apostas){
            if (aposta.getId().equals(idAposta)){
                return  aposta;
            }
        }
        return null;
    }




    public Double getSaldoAposta(String idAposta) {
        try{
            Aposta aposta = getAposta(idAposta);
            if (aposta == null){
                return 0.0;
            }
            if (aposta.getTime().equals(this.vencedor)){
                return  aposta.getMultiplicador() * aposta.getValor();
            }
        }catch (Exception e){
            return  0.0;
        }

        return 0.0;
    }

    public Double getSaldoNApostas(ArrayList<String> apostas){
        if(!verificaFim())return 0.0;
        Double vAposta = 0.0;
        for (String aposta : apostas) {
            vAposta += this.getSaldoAposta(aposta);
        }
        return vAposta;
    }

    @SuppressLint("SimpleDateFormat")
    public Boolean verificaFim(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date dataFim = dateFormat.parse(this.getDataFim());
            Date dataAtual = new Date();
            if (dataAtual.after(dataFim) || dataAtual.equals(dataFim)){
                return Boolean.TRUE;
            }

        } catch (ParseException e) {
          return  Boolean.FALSE;
        }
        return  Boolean.FALSE;
    }

}

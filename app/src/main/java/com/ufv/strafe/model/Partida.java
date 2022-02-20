package com.ufv.strafe.model;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavType;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


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
                  String vencedor) throws ParseException {

       this.id = id;
       this.time1 = new Time(time1);
       this.time2 = new Time(time2);
       this.jogo = jogo;
       this.dataIncio = dataIncio;
       this.dataFim = dataFim;
       this.apostas = new ArrayList<>();
       this.vencedor = vencedor;


   }

    public String addAposta(String idAposta,
                            String time,
                            Double valor,
                            Double multiplicador){

       Aposta aposta = new Aposta(idAposta, time, valor, multiplicador,FirebaseAuth.getInstance().getUid());
       apostas.add(aposta);
       return  id;
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
        if (value > 1.4){
            return Math.round(value * 100)/100.0;
        }
        else return 1.5;
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

    @Override
    public String toString() {
        return "Partida{" +
                "time1=" + time1 +
                ", time2=" + time2 +
                ", jogo='" + jogo + '\'' +
                ", apostas=" + apostas +
                ", dataIncio='" + dataIncio + '\'' +
                ", dataFim='" + dataFim + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
/*

    @SuppressLint("SimpleDateFormat")
    public HashMap<String, Double> getApostasVencidas(){

        HashMap<String, Double> ganhadores = new HashMap<>();
        String ganhador = "";
        Double valor;
        String idUser;

        if (Math.random() * 10 +1 > 5){
            ganhador = time1.toString();
        }else{
            ganhador = time2.toString();
        }


        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date dataFim = dateFormat.parse(getDataFim());
            Date dataAtual = new Date();

            if (dataAtual.after(dataFim) || dataAtual.equals(dataFim)) {
                for (Aposta aposta : this.apostas) {
                    if (aposta.getTime().equals(ganhador)){
                        valor = aposta.getValor() * aposta.getMultiplicador();
                        idUser = aposta.getUserId();
                        ganhadores.put(idUser, valor);
                    }
                }
            }


        } catch (ParseException ex) {
        }

        return ganhadores;
    }
*/



    public Double getSaldoAposta(String aposta) {
       for (Aposta aposta1 : apostas){
           if (aposta1.getId() == aposta){
               if (aposta1.getTime().equals(vencedor)){
                   return aposta1.getMultiplicador() * aposta1.getValor();
               }
               return 0.0;
           }
       }
        return 0.0;
    }
}

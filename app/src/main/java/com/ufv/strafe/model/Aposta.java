package com.ufv.strafe.model;


import java.util.Date;

public class Aposta {
    private String id;
    private String time;
    private Double valor;
    private Double multiplicador;
    private  String userId;

    public  Aposta(){

    }
    public Aposta(String id,
                  String time,
                  Double valor,
                  Double multiplicador,
                  String userId
                  ){
        this.id = id;
        this.time = time;
        this.valor = valor;
        this.multiplicador = multiplicador;
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public Double getValor() {
        return valor;
    }

    public String getId() {
        return id;
    }

    public Double getMultiplicador() {
        return multiplicador;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getUserId() {
        return userId;
    }
}

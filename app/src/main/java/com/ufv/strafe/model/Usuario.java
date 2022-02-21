package com.ufv.strafe.model;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Usuario {



    private String id;
    private String nome;
    private String fotoPerfil;
    private Double saldo;
    private Integer acertos;
    private Integer erros;
    private Map<String, Boolean> jogos;
    private Map<String, ArrayList<String>> apostas;

    public Usuario(){
       apostas = new HashMap<>();
    }

    public Usuario(String nome, String id, String fotoPerfil, Double saldo, Integer acertos, Integer erros) {
        this.inicializaJogos();
        this.setNome(nome);
        this.setId(id);
        this.setFotoPerfil(fotoPerfil);
        this.setSaldo(saldo);
        this.setAcertos(acertos);
        this.setErros(erros);
        apostas = new HashMap<>();
    }



    public String getNome() {
        return nome;
    }

    public Double getSaldo() {
        return saldo;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public String getId() {
        return id;
    }

    public Integer getAcertos() {
        return acertos;
    }

    public Integer getErros() {
        return erros;
    }

    public Map<String, Boolean> getJogos() {
        return jogos;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void setAcertos(Integer acertos) {
        this.acertos = acertos;
    }

    public void setErros(Integer erros) {
        this.erros = erros;
    }

    public void inicializaJogos() {
        this.jogos = new HashMap<>();
        this.jogos.put("Call Of Duty", false);
        this.jogos.put("Counter-Strike", false);
        this.jogos.put("Dota II", false);
        this.jogos.put("Heanthstone", false);
        this.jogos.put("League of Legends", false);
        this.jogos.put("Overwatch", false);
        this.jogos.put("Rainbow 6 Siege", false);
        this.jogos.put("Rocket League", false);
        this.jogos.put("StarCraft", false);
        this.jogos.put("Valorant", false);

    }

    public void addAposta(String idPartida, String idAposta){
        if (this.apostas.containsKey(idPartida)){
            this.apostas.get(idPartida).add(idAposta);
            return;
        }

        this.apostas.put(idPartida, new ArrayList<>());
        this.apostas.get(idPartida).add(idAposta);


    }

    public Map<String, ArrayList<String>> getApostas() {
        return apostas;
    }

    public void putJogos(String key, Boolean valor) {
        this.jogos.put(key, valor);
    }

    public void removeApostas(String partida){
        this.apostas.remove(partida);
    }
}

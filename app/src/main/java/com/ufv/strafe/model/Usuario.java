package com.ufv.strafe.model;

import android.media.AudioMetadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    }

    public Usuario(String nome, String id, String fotoPerfil) {
        this.inicializaJogos();
        this.setNome(nome);
        this.setId(id);
        this.setFotoPerfil(fotoPerfil);
        this.setSaldo(300.0);
        this.acertos = 0;
        this.erros = 0;
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

    public void addAcertos() {
        this.acertos += 1;
    }

    public void addErros() {
        this.erros += 1;
    }

    public void inicializaJogos() {
        this.jogos = new HashMap<>();
        this.jogos.put("Call of Duty", false);
        this.jogos.put("Counter-Strike", false);
        this.jogos.put("Dota II", false);
        this.jogos.put("Hearthstone", false);
        this.jogos.put("League of Legends", false);
        this.jogos.put("Overwatch", false);
        this.jogos.put("Rainbow 6 Siege", false);
        this.jogos.put("Rocket League", false);
        this.jogos.put("StarCraft", false);
        this.jogos.put("Valorant", false);

    }

    public void addAposta(String idPartida, String idAposta, Double valor){
         setSaldo(getSaldo() - valor);
        if(this.apostas == null) return;
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

    public void removeAposta(String aposta){
        ArrayList<String> keys = new ArrayList<>(this.apostas.keySet());
        ArrayList<String> apostasArray;

        for (String key : keys){
          apostasArray = this.apostas.get(key);
          apostasArray.remove(aposta);
          this.apostas.remove(key);
          if (apostasArray.size() > 0){
              this.apostas.put(key, apostasArray);
          }

        }

    }

    public void addSaldo(Double valor){
        this.setSaldo(this.getSaldo() + valor);
    }



}

package com.ufv.strafe.model;

import java.util.HashMap;
import java.util.Map;

public class Usuario {



    private String id;
    private String nome;
    private String fotoPerfil;
    private Integer saldo;
    private Integer acertos;
    private Integer erros;
    private Map<String, Boolean> jogos;


    public Usuario(){
    }

    public Usuario(String nome, String id, String fotoPerfil, Integer saldo, Integer acertos, Integer erros) {
        this.inicializaJogos();
        this.setNome(nome);
        this.setId(id);
        this.setFotoPerfil(fotoPerfil);
        this.setSaldo(saldo);
        this.setAcertos(acertos);
        this.setErros(erros);
    }

    public String getNome() {
        return nome;
    }

    public Integer getSaldo() {
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

    public void setSaldo(Integer saldo) {
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


    public void putJogos(String key, Boolean valor) {
        this.jogos.put(key, valor);
    }
}

package com.ufv.strafe.model;


public class Video {

    private String titulo;
    private String fonte;
    private String data;
    private String id;
    private String thumbnail;


    private String jogo;

    public Video(String titulo, String fonte, String data, String id, String thumbnail, String jogo) {
        this.titulo = titulo;
        this.fonte = fonte;
        this.data = data;
        this.id = id;
        this.thumbnail = thumbnail;
        this.jogo = jogo;
    }


    public Video() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getJogo() {
        return jogo;
    }

    public void setJogo(String jogo) {
        this.jogo = jogo;
    }




}

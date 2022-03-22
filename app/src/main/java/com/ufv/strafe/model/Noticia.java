package com.ufv.strafe.model;

public class Noticia {

    private String titulo;
    private String texto;
    private String fonte;
    private String id;
    private String jogo;
    private String data;


    private String img;

    public Noticia(){

    }
    public Noticia(String titulo, String texto, String fonte, String idNoticia, String jogo, String data, String img) {
        this.titulo = titulo;
        this.texto = texto;
        this.fonte = fonte;
        this.id = idNoticia;
        this.jogo = jogo;
        this.data = data;
        this.img = img;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJogo() {
        return jogo;
    }

    public void setJogo(String jogo) {
        this.jogo = jogo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

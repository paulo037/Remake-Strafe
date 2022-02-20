package com.ufv.strafe.model;

import android.annotation.SuppressLint;

import com.ufv.strafe.model.utils.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class News {

    private String title;
    private String body;
    private Category category;
    private String fonte;
    private Calendar calendario;
    

    public News( String title, String body, Category category, String fonte) throws ParseException {
        this.setTitle(title);
        this.setBody(body);
        this.setCategory(category);
        this.setFonte(fonte);
        Calendario calendario = new Calendario();
        this.calendario = calendario.getCalendar("18/07/2002 18:20:00");
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public Category getCategory() {
        return this.category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public String getFonte() {
        return fonte;
    }
    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    @SuppressLint("SimpleDateFormat")
    public void showNews() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println("----------------------------------------\n");
        System.err.printf("\t\t%s\t\t\t\n\n", this.title);
        System.out.printf("%s \n", this.body);
        System.out.println("----------------------------------------");
        System.out.printf("fonte: %s \n\n", this.fonte);
        System.out.printf("%s  \n",fmt.format(calendario.getTime()));
        System.out.println("----------------------------------------");
    }



}

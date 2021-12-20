package com.video;

import com.utils.Category;
import com.utils.DateHour;

public class Video {

    private String title;
    private Category category;
    private String fonte;
    private DateHour dateHour;
    

    public Video( String title, Category category, String fonte){
        this.setTitle(title);
        this.setCategory(category);
        this.setFonte(fonte);
        dateHour = new DateHour();
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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

    public void showVideo() {
        System.out.println("----------------------------------------\n");
        System.err.printf("\t\t%s\t\t\t\n\n", this.title);
        System.out.println("----------------------------------------");
        System.out.println("\t\tVideo Aqui\t\t\t\n\n");
        System.out.println("----------------------------------------");
        System.out.printf("fonte: %s \n\n", this.fonte);
        System.out.printf("%s  %s \n",this.dateHour.getDate(), this.dateHour.getHour());
        System.out.println("----------------------------------------");
    }
}

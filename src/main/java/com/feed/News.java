package com.feed;
import com.utils.*;

public class News {

    private String title;
    private String body;
    private Category category;
    private String fonte;
    private DateHour dateHour;
    

    public News( String title, String body, Category category, String fonte){
        this.setTitle(title);
        this.setBody(body);
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

    public void showNews() {
        System.out.println("----------------------------------------\n");
        System.err.printf("\t\t%s\t\t\t\n\n", this.title);
        System.out.printf("%s \n", this.body);
        System.out.println("----------------------------------------");
        System.out.printf("fonte: %s \n\n", this.fonte);
        System.out.printf("%s  %s \n",this.dateHour.getDate(), this.dateHour.getHour());
        System.out.println("----------------------------------------");
    }
}

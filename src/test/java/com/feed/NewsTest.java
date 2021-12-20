package com.feed;
import com.utils.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NewsTest  {
    @Test
    void testShowNews() {
        News news = new News("Noticia", "corpo", Category.COD, "www.noticias.com");
        assertEquals(news.getCategory(), Category.COD);
        assertEquals(news.getBody(),  "corpo");
        assertEquals(news.getFonte(),  "www.noticias.com");
    }
}

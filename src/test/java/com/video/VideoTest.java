package com.video;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.utils.*;

import org.junit.jupiter.api.Test;

public class VideoTest {
    @Test
    void testInstancia(){
        Video video1 = new Video("TituloVideo", Category.CSGO, "www.videos1.com");
        Video video2 = new Video("TituloVideo2", Category.CSGO, "www.videos2.com");
        Videos list = new Videos();
        list.listVideos.add(video1);
        list.listVideos.add(video2);
        assertEquals(video1.getCategory(), Category.CSGO);
            
    }
}

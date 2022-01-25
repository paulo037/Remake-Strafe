package com.ufv.strafe.entities.video;

import java.util.ArrayList;
import java.util.List;

import com.ufv.strafe.entities.utils.Category;

public class Videos {
    public List<Video>  listVideos;
    
    public Videos(){
        this.listVideos = new ArrayList<Video>();
    }

    public void showVideosByCategory(Category category) {
        for (Video video : this.listVideos) {
            if (video.getCategory() == category){
                video.showVideo();
            }
        }
    }
}

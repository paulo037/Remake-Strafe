package com.ufv.strafe.dao;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.ufv.strafe.model.Video;
import java.util.List;


public class VideoDAO {


    public MutableLiveData<List<Video>> videos = new MutableLiveData<>();


    public VideoDAO(){
        FirebaseFirestore.getInstance()
                .collectionGroup("videos")
                .addSnapshotListener((value, error) -> {
                    if (error != null || value == null) {
                        return;
                    }
                    videos.setValue(value.toObjects(Video.class));
                });
    }



    public void createVideo(Video video) {
        //Adicionando no Banco de dados
        FirebaseFirestore.getInstance().collection("videos")
                .document(video.getId())
                .set(video);
    }
}



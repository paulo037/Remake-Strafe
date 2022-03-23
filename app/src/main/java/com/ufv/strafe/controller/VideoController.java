package com.ufv.strafe.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.renderscript.ScriptGroup;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ufv.strafe.dao.UsuarioDAO;
import com.ufv.strafe.dao.VideoDAO;
import com.ufv.strafe.databinding.FragmentVideoBinding;
import com.ufv.strafe.databinding.ItemVideoBinding;
import com.ufv.strafe.model.Noticia;
import com.ufv.strafe.model.Video;
import com.ufv.strafe.ui.Adapters.ItemNoticiaAdapter;
import com.ufv.strafe.ui.Adapters.ItemVideoAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoController {

    private VideoDAO videoDAO;
    private UsuarioDAO usuarioDAO;
    public VideoController(){
        usuarioDAO = new UsuarioDAO();
        videoDAO = new VideoDAO();
    }


    @SuppressLint("SimpleDateFormat")
    public void  Observer(LifecycleOwner lifecycle, FragmentVideoBinding binding, Context context){
        videoDAO.videos.observe(lifecycle, videos -> {
            Log.i(toString(), "atualizando Videos");
          LinearLayoutManager layoutManager = new LinearLayoutManager(context,
                    RecyclerView.VERTICAL,
                    false);

            List<Video> videosUsuario = new ArrayList<>();
            try {
                for (Video video: videos) {
                    if (usuarioDAO.getJogos().get(video.getJogo())) {
                        videosUsuario.add(video);
                    }
                }
            }catch (Exception ignored){
                Log.w(toString(), "jogo não encontrado");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                videosUsuario.sort((video, n1) -> {
                    Date date;
                    Date t1;
                    try {
                        date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(video.getData());
                        t1 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(n1.getData());
                    } catch (ParseException e) {
                        return 1;
                    }

                    if(date.after(t1))return 1;
                    if(date.equals(t1))return 0;
                    if(date.before(t1))return  -1;
                    return 0;
                });
            }


           ItemVideoAdapter adapter = new ItemVideoAdapter(videosUsuario, context);
           binding.recycleVideos.setAdapter(adapter);
           binding.recycleVideos.setLayoutManager(layoutManager);

           Log.i(toString(), "Videos atualizados");
        });
    }


    public void createVideo(Video video){
       videoDAO.createVideo(video);
    }
}

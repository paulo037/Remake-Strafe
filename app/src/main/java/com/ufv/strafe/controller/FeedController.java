package com.ufv.strafe.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ufv.strafe.R;
import com.ufv.strafe.dao.NoticiaDAO;
import com.ufv.strafe.dao.UsuarioDAO;
import com.ufv.strafe.databinding.FragmentFeedBinding;
import com.ufv.strafe.databinding.FragmentReadNoticiaBinding;
import com.ufv.strafe.model.Noticia;
import com.ufv.strafe.model.utils.Resource;
import com.ufv.strafe.ui.Adapters.ItemNoticiaAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FeedController {


    private NoticiaDAO noticiaDAO;
    private UsuarioDAO usuarioDAO;

    public FeedController() {
        usuarioDAO = new UsuarioDAO();
        noticiaDAO = new NoticiaDAO();

    }

    public FeedController(String id) {
        noticiaDAO = new NoticiaDAO(id);
    }


    @SuppressLint("SimpleDateFormat")
    public void Observer(FragmentFeedBinding binding, LifecycleOwner lifecycleOwner, Context context) {
        noticiaDAO.noticias.observe(lifecycleOwner, noticias -> {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context,
                    RecyclerView.VERTICAL,
                    false);

            List<Noticia> noticiasUsuario = new ArrayList<>();
            try {
                for (Noticia noticia : noticias) {
                    if (usuarioDAO.getJogos().get(noticia.getJogo())) {
                        noticiasUsuario.add(noticia);
                    }
                }
            } catch (Exception ignored) {

            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                noticiasUsuario.sort((noticia, n1) -> {
                    Date date;
                    Date t1;
                    try {
                        date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(noticia.getData());
                        t1 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(n1.getData());
                    } catch (ParseException e) {
                        return 1;
                    }

                    if (date.after(t1)) return 1;
                    if (date.equals(t1)) return 0;
                    if (date.before(t1)) return -1;
                    return 0;
                });
            }


            ItemNoticiaAdapter adapter = new ItemNoticiaAdapter(noticiasUsuario, context);
            binding.recycleNoticias.setAdapter(adapter);
            binding.recycleNoticias.setLayoutManager(layoutManager);
        });
    }


    public void ObserverNoticia(FragmentReadNoticiaBinding binding, LifecycleOwner lifecycleOwner, Context context) {
        noticiaDAO.noticia.observe(lifecycleOwner, noticia -> {
            binding.titulo.setText(noticia.getTitulo());
            binding.jogo.setText(noticia.getJogo());
            int color = Resource.getColorByName(noticia.getJogo(), context);
            binding.jogo.setTextColor(context.getResources().getColor(color));
            binding.texto.setText(noticia.getTexto());
            binding.data.setText(noticia.getData());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.link.setText(Html.fromHtml("<u>" + noticia.getFonte() + "</u>", Html.FROM_HTML_MODE_LEGACY));
            } else {
                binding.link.setText(noticia.getFonte());
            }
            binding.link.setOnClickListener(view -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(binding.link.getText().toString()));
                context.startActivity(browserIntent);
            });
            Picasso.get()
                    .load(noticia.getImg())
                    .into(binding.image);

        });
    }

}

package com.ufv.strafe.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.ufv.strafe.dao.PartidaDAO;
import com.ufv.strafe.dao.UsuarioDAO;
import com.ufv.strafe.model.Aposta;
import com.ufv.strafe.model.Partida;

import java.util.Calendar;

public class DialogApostaController {
    private UsuarioDAO usuarioDAO;
    private PartidaDAO partidaDAO;


    public DialogApostaController(String idPartida){
        this.usuarioDAO = new UsuarioDAO();
        this.partidaDAO = new PartidaDAO(idPartida);
    }



    public void  addAposta(String idPartida, String time, Double valor, Double multiplicador){
        Calendar data;
        data = Calendar.getInstance();
        String userId = FirebaseAuth.getInstance().getUid();

        String idAposta = userId + (data.getTimeInMillis());
        Aposta aposta = new Aposta(idAposta, time, valor, multiplicador, FirebaseAuth.getInstance().getUid());

        usuarioDAO.addAposta(idPartida,  idAposta, valor);
        usuarioDAO.updateUser();

        partidaDAO.addAposta(aposta);
        partidaDAO.updatePartida();

    }

    public Double getSaldo(){
        return usuarioDAO.usuario.getValue().getSaldo();
    }


}

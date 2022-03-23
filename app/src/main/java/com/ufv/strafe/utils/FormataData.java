package com.ufv.strafe.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormataData {

    @SuppressLint("SimpleDateFormat")
    public static String getDataFormatada(Date data){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(data);
    }
}

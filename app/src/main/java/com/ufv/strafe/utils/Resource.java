package com.ufv.strafe.utils;

import android.content.Context;

import com.ufv.strafe.R;

import java.util.ArrayList;

public class Resource {



    public static  ArrayList<Integer> getColorsJogos(Context context) {
        String[] colorsName;
        ArrayList<Integer> cores = new ArrayList<>();

        colorsName = context.getResources().getStringArray(R.array.colors_name);


        for (String cor : colorsName) {
            cores.add(context.getResources().getIdentifier(cor, "color", context.getPackageName()));
        }

        return cores;

    }

    public static  Integer getColorByName(String color, Context context){
        color = color.replaceAll("\\s+","");
        color = color.replaceAll("-","");
        return context.getResources().getIdentifier(color, "color", context.getPackageName());
    }

    public static  Integer getIconsByNameJogo(String jogo, Context context){
        jogo = jogo.replaceAll("\\s+","");
        jogo = jogo.replaceAll("-","");

        int iconId = context.getResources().getIdentifier(jogo, "string", context.getPackageName());

        String icon = context.getString(iconId);
        return  context.getResources().getIdentifier(icon, "drawable", context.getPackageName());

    }

}

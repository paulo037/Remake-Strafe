package com.ufv.strafe.model.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Calendario {
    private Calendar calendar;



    public Calendario()  {

    }

    @SuppressLint("SimpleDateFormat")
    public Calendar getCalendar(String dataString) throws ParseException{
        Date data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dataString);
        calendar = Calendar.getInstance();
        assert data != null;
        calendar.setTime(data);
        return calendar;
    }
}

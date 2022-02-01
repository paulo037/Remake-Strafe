package com.ufv.strafe.model.utils;

// Classe de datas
// Verifica se é ano bissexto, e se a data é válida
public class Date {
    /* int day, month, year;   //Data por si só
    boolean isLeap;         //Fala se o ano é bissexto ou não */
    static int[] n_days = {31,28,31,30,31,30,31,31,30,31,30,31};

    public static void verifyLeap(int y){
        if (y % 4 == 0){
            if (y % 100 == 0){
                if (y % 400 == 0){
                    n_days[1] = 29;
                }else{
                    n_days[1] = 28;
                }
            }else{
                n_days[1] = 28;
            }
        }else{
            n_days[1] = 28;
        }
    }
    public static boolean verifyDate(int day, int month, int year){
        verifyLeap(year);
        return day > 0 && day < n_days[month-1];
    }

}

class Date {
    int day, month, year;   //Data por si só
    boolean isLeap;         //Fala se o ano é bissexto ou não
    int[] n_days = {31,28,31,30,31,30,31,31,30,31,30,31};

    void verifyLeap(int y){
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

    boolean verifyMonth(int day, int month, int y){
        verifyLeap(y);
        return day > 0 && day < n_days[month-1];
    }

}

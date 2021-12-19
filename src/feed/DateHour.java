package feed;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHour {
    private Calendar calendar;
    private Date date;
    private DateFormat hour;
    private  DateFormat formataData;

    public  DateHour() {
        this.calendar = Calendar.getInstance();
        this.date = this.calendar.getTime();
        this.hour = DateFormat.getTimeInstance();
        this.formataData = DateFormat.getDateInstance();
    }
    
    public String getHour(){       
		return hour.format(date);
    }

    public String getDate() {
       return formataData.format(date);
    }


}

package de.kyrtap5.wednesdaywallpaper;

import java.util.Calendar;
import java.util.Date;

public class Week {
    //Extract the weekday from date
    public static Weekday getWeekday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                return Weekday.SUNDAY;
            case 2:
                return Weekday.MONDAY;
            case 3:
                return Weekday.TUESDAY;
            case 4:
                return Weekday.WEDNESDAY;
            case 5:
                return Weekday.THURSDAY;
            case 6:
                return Weekday.FRIDAY;
            case 7:
                return Weekday.SATURDAY;
            default:
                return null;
        }
    }

    //Check whether the current date matches the given weekday
    public static boolean checkWeekday(Weekday weekday) {
        return getWeekday(new Date()) == weekday;
    }
}

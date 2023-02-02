package co.neoris.apitransactions.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date getDate(){
        return new Date();
    }

    public static String getDateString(String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        String strDate = dateFormat.format(DateUtils.getDate());
        return strDate;
    }
}




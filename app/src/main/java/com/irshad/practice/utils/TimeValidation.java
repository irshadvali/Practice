package com.irshad.practice.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by irshadvali on 19/10/17.
 */

public class TimeValidation {
    public static String nastr="NA";
    public static String getDateTime(String str){
        String result;
        try {

            if (str == null || str.trim().equalsIgnoreCase("") ||str.trim().equalsIgnoreCase("0") || str.trim().equalsIgnoreCase("null") || str.trim().isEmpty()) {
                result = nastr;
            } else {
                String strTime="2018-Jan-26 12:00 PM";
                String[] splited = str.split(" ");
                String [] splitDate=splited[0].split("-");
                String dateTime=splitDate[2]+" "+splitDate[1]+" "+splitDate[0]+" at "+splited[1]+" "+splited[2];

                System.out.println("date time one="+dateTime);
                result = dateTime;
            }
        }
        catch (Exception e)
        {
            result=nastr;
        }

        return result;

    }


}

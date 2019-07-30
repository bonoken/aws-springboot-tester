package com.soho.comm.date;


import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;


public class DateTimeRange {


    public DateTimeRange() {
    }

    public static String[] getLastMinutesRangeISO8601Strings(int minutes, boolean addOneMinute) {
        String[] result = new String[2];
        DateTime end = new DateTime();
        result[1] = end.toString(ISODateTimeFormat.dateTimeNoMillis());
        int s = addOneMinute ? minutes * 60 + 30 : minutes * 60;
        DateTime start = end.minusSeconds(s);
        result[0] = start.toString(ISODateTimeFormat.dateTimeNoMillis());
        return result;
    }

    public static Date[] getLastMinutesRangeISO8601Dates(int minutes, boolean addOneMinute) {
        Date[] result = new Date[2];
        DateTime end = new DateTime();

        int seconds = end.getSecondOfMinute() + 60;

        if (seconds < 100) {
            seconds += 60;
        }

        //end = end.toDateTimeISO();
        end = end.toDateTimeISO().minusSeconds(seconds);
        result[1] = end.toDate();

        //int s = addOneMinute ? minutes * 60 + 30 : minutes * 60;
        int s = addOneMinute ? minutes * 60 : minutes * 60;
        DateTime start = end.minusSeconds(s);
        start = start.toDateTimeISO();
        result[0] = start.toDate();

        return result;
    }


    public static int getIntervalSeconds(int value, String units) {
        int result = 1;
        String subUnit = units.substring(0, 3);
        //logger.debug("Converting from " + value + " " + units + " (" + subUnit + ")");
        if (value > 0) {
            if (subUnit.equalsIgnoreCase("min"))
                result = value * 60;
            else if (subUnit.equalsIgnoreCase("hou"))
                result = value * 3600;
            else
                result = value;
        }
        if (result % 60 != 0)
            result = result / 60 * 60;
        if (result < 60)
            result = 60;
        //logger.debug("Converted to " + result + " seconds");
        return result;
    }


}

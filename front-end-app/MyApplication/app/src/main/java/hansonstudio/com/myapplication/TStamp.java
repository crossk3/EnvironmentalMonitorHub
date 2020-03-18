package hansonstudio.com.myapplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class TStamp {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private long compare;

    public TStamp(int year, int month, int day, int hour, int min){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
        compare = Long.parseLong(String.format("%04d", year) + String.format("%02d", month)
                + String.format("%02d", day) + String.format("%02d", hour) + String.format("%02d", min));
    }

    public TStamp(){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:");
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        String stamp  =  formatter.format(date);
        String y = stamp.substring(6,10);
        String mo = stamp.toString().substring(0,2);
        String d = stamp.toString().substring(3,5);
        String h = stamp.toString().substring(11,13);
        String mi = stamp.toString().substring(14,16);
        year = Integer.parseInt(y);
        month = Integer.parseInt(mo);
        day = Integer.parseInt(d);
        hour = Integer.parseInt(h);
        min = Integer.parseInt(mi);
        compare = Long.parseLong(y+mo+d+h+mi);
    }

    public String toString(){
        String str = "";
        String y = String.format("%04d", year);
        String mo = String.format("%02d", month);
        String d = String.format("%02d", day);
        String h = String.format("%02d", hour);
        String mi = String.format("%02d", min);
        str = mo + "/" + d + "/" + y + "|" + h + ":" + mi;
        return str;
    }

    public  void setYear(int year)
    {
        this.year = year;
    }

    public  void setDay(int day)
    {
        this.day = day;
    }

    public  void setMonth(int month)
    {
        this.month = month;
    }

    public  void setHour(int hour)
    {
        this.hour = hour;
    }

    public  void setMin(int min)
    {
        this.min = min;
    }

    public int getYear(){
        return year;
    }

    public int getMonth(){
        return month;
    }

    public int getDay(){
        return day;
    }

    public int getHour(){
        return hour;
    }

    public int getMin(){
        return min;
    }

    public long getCompare(){
        return compare;
    }

    public ArrayList<TStamp> sort(ArrayList<TStamp> list ) {
        Collections.sort(list, new DateComparator());
        return list;
    }

}



package hansonstudio.com.myapplication;

import java.util.Comparator;

public class DateComparator implements Comparator<TStamp> {
    public int compare(TStamp one, TStamp two)
    {
        long date1 = one.getCompare();
        long date2 = two.getCompare();
        if (date1 > date2){
            return 1;
        }
        else if (date2 > date1){
            return -1;
        }
        else {
            return 0;
        }
    }
}


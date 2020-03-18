package hansonstudio.com.myapplication.Values;
import hansonstudio.com.myapplication.TStamp;

public class GraphTimes {

    public static TStamp startTime = new TStamp(2020, 1, 1, 0, 0);
    public static TStamp endTime = new TStamp();

    public static TStamp serverLatestEntry()
    {
        //placeholder
        return new TStamp();
    }

    public static TStamp serverFirstEntry()
    {
        //placeholder
        return new TStamp(2008, 8, 8, 8, 8);
    }
}

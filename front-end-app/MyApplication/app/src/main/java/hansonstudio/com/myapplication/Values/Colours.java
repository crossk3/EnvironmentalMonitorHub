package hansonstudio.com.myapplication.Values;

import android.graphics.Color;

public class Colours {
    //general Ui element Colours
    public static final int appBackground = Color.rgb(180,230,255);
    public static final int text = Color.rgb(0,0,90);
    public static final int ganttChartBlue = Color.rgb(6,172,250);

    //green, blue and red for thresholding
    public static final int insideThreshold = Color.rgb(0, 200, 0);
    public static final int belowThreshold = Color.rgb(50, 0, 255);
    public static final int aboveThreshold = Color.rgb(255, 50, 0);

    public static int getThresholdColour(float value, float lowerThreshold, float upperThreshold)
    {
        if(value >= lowerThreshold)
        {
            return value > upperThreshold ? aboveThreshold : insideThreshold;
        }
        return belowThreshold;
    }

}

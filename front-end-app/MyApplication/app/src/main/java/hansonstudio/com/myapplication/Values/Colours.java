package hansonstudio.com.myapplication.Values;

import android.graphics.Color;

public class Colours {
    public static final int appBackground = Color.rgb(180,230,255);
    public static final int text = Color.rgb(0,0,90);
    public static final int ganttChartBlue = Color.rgb(6,172,250);

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


    /*function to input a temperature in Kelvin and return the colour
    corresponding to the blackbody radiation of an object at that temperature
     */
    public Color getColor(int temp){
        //initialize red green and blue values
        int r = 0;
        int g = 0;
        int b = 0;
        int a = 0; //transparency

        //deep orange
        if(temp < 1500) {
            r = 255;
            g = 108;
            b = 0;
            a = 255;
        }

        //linear function of deep orange to light orange
        if(1500 <= temp && temp < 1900){
            float diff = (float)24/(1900-1500) * (temp - 1500);
            r = 255;
            g = 108 + (int)diff;
            b = 0;
            a = 255;
        }

        //linear function of light orange to white
        if(1900 <= temp && temp < 6500){
            float gdiff = (float)124/(6500-1900) * (temp - 1900);
            float bdiff = (float)255/(6500-1900) * (temp - 1900);
            float adiff = (float)255/(2*(6500-1900)) * (temp - 1900);
            r = 255;
            g = 132 + (int)gdiff;
            b = (int)bdiff;
            a = 255 - (int)adiff;
        }

        //linear function of white to sky blue
        if(6500 <= temp && temp < 9500){
            float rdiff = (float)50/(9500-6500) * (temp - 6500);
            float gdiff = (float)35/(9500-6500) * (temp - 6500);
            float adiff = (float)255/(2*(15000-6500)) * (temp - 6500);
            r = 255 - (int)rdiff;
            g = 255 - (int)gdiff;
            b = 255;
            a = 128 + (int)adiff;
        }
        //linear function of sky blue to light blue
        if(9500 <= temp && temp < 15000){
            float rdiff = (float)25/(15000-9500) * (temp - 9500);
            float gdiff = (float)15/(15000-9500) * (temp - 9500);
            float adiff = (float)255/(2*(15000-6500)) * (temp - 6500);
            r = 205 - (int)rdiff;
            g = 220 - (int)gdiff;
            b = 255;
            a = 127 + (int)adiff;
        }

        //light blue
        if(temp >= 15000) {
            r = 181;
            g = 205;
            b = 255;
            a = 255;
        }
            Color colr = new Color(r,g,b,a);
            return colr;
    }

}

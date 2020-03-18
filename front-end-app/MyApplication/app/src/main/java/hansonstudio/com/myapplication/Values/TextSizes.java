package hansonstudio.com.myapplication.Values;

import hansonstudio.com.myapplication.ViewLayout;

public class TextSizes {

    public static int small()
    {
        float density = ViewLayout.getPixelDensity();
        int screenWidth = ViewLayout.screenWidth();
        if (density<=2)
        {
            return (int) (screenWidth*0.012+8);
        }
        else
        {
            return (int) (screenWidth*0.01+5);
        }
    }

    public static int large()
    {
        float density = ViewLayout.getPixelDensity();
        int screenWidth = ViewLayout.screenWidth();
        if (density<=2)
        {
            return ((int)(screenWidth*0.025+20));
        }

        else
        {
            return ((int)(screenWidth*0.014+15));
        }
    }
    public static int medium()
    {
        return (small() + large()) / 2;
    }
}

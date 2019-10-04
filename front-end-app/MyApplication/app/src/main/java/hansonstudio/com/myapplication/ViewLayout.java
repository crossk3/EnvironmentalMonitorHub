package hansonstudio.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import hansonstudio.com.myapplication.Values.Colours;

public class ViewLayout extends AppCompatActivity {

    private static int screenWidth;
    private static int screenHeight;
    private static float pixelDensity;
    private static int margin;
    private static int iconSize;
    private static int iconMargin;

    public static RelativeLayout ActivitySetup(Context context, RelativeLayout mainLayout)
    {
        ScrollView scrollView = new ScrollView(context);
        scrollView.setBackgroundColor(Colours.appBackground);
        mainLayout.addView(scrollView);
        RelativeLayout myLayout = new RelativeLayout(context);
        scrollView.addView(myLayout);

        myLayout.setBackgroundColor(Colours.appBackground);
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        margin = screenWidth / 45;

        myLayout.setMinimumHeight(screenHeight);
        myLayout.setMinimumWidth(screenWidth);
        pixelDensity = ((Activity)context).getResources().getDisplayMetrics().density;

        iconSize = screenWidth/5;
        iconMargin = (screenWidth - 3 * iconSize) / 4;

        return myLayout;

    }

    public static int getScreenWidth()
    {
        return screenWidth;
    }

    public static int getScreenHeight()
    {
        return screenHeight;
    }

    public static float getPixelDensity()
    {
        return pixelDensity;
    }

    public static int getMargin()
    {
        return margin;
    }

    public static Point getDiagnosticIconSize()
    {
        return new Point(iconSize, iconSize);
    }

    public static int getIconMargin() {
        return iconMargin;
    }
}

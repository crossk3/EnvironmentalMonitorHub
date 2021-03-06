package hansonstudio.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
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
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {Colours.backgroundLight, Colours.backgroundDark});
        gd.setCornerRadius(0f);
        scrollView.setBackground(gd);
        mainLayout.addView(scrollView);
        RelativeLayout myLayout = new RelativeLayout(context);
        scrollView.addView(myLayout);

        //myLayout.setBackground(gd);
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

    public static int screenWidth()
    {
        return screenWidth;
    }

    public static int screenHeight()
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

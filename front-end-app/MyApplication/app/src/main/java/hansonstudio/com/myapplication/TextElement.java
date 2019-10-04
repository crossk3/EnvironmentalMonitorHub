package hansonstudio.com.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Callable;

import hansonstudio.com.myapplication.Values.Colours;
import hansonstudio.com.myapplication.Values.TextSizes;

public class TextElement extends AppCompatTextView {

    public TextView element;

    public TextElement(Context context, RelativeLayout layout, String text, Point location, Point size)
    {
        super(context);
        element = new TextView(context);
        element.setText(text);
        element.setTextColor(Colours.text);
        element.setGravity(Gravity.CENTER);
        element.setTextSize(TextSizes.small());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                size.x, size.y);

        params.setMargins(location.x, location.y, 0, 0);//left top right bottom

        layout.addView(element, params);
    }

    public void makeTitle()
    {
        element.setTextSize(TextSizes.large());
    }

    public void makeButtonStyle()
    {
        element.setPadding(0, ViewLayout.getMargin()/2,0,ViewLayout.getMargin()/2);
        GradientDrawable gd1 = new GradientDrawable();
        gd1.setColor(Colours.appBackground);
        gd1.setCornerRadius(ViewLayout.getMargin());
        gd1.setStroke(3, Colours.text);
        element.setBackground(gd1);
    }
}

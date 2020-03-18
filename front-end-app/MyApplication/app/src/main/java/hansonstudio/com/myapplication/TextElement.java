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
    private RelativeLayout.LayoutParams params;
    private int outlineColour = 0;
    public TextElement(Context context, RelativeLayout layout, String text, Point location, Point size)
    {
        super(context);
        element = new TextView(context);
        element.setText(text);
        element.setTextColor(Colours.text);
        element.setGravity(Gravity.CENTER);
        element.setTextSize(TextSizes.small());

        params = new RelativeLayout.LayoutParams(size.x, size.y);
        params.setMargins(location.x, location.y, location.x, location.y);//left top right bottom

        layout.addView(element, params);
    }

    public void rightJustify()
    {
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    }

    public void makeTitle()
    {
        element.setTextSize(TextSizes.large());
    }

    public void makeButtonStyle()
    {
        makeButtonStyle(Colours.text);
    }

    public void makeButtonStyle(int outlineColour)
    {
        element.setPadding(0, ViewLayout.getMargin()/2,0,ViewLayout.getMargin()/2);
        GradientDrawable gd1 = new GradientDrawable();
        gd1.setColor(Colours.appBackground);
        gd1.setCornerRadius(ViewLayout.getMargin());
        gd1.setStroke(3, outlineColour);
        element.setBackground(gd1);
        this.outlineColour = outlineColour;
    }

    public void setTextSize(float size)
    {
        element.setTextSize(size);
    }

    public int getOutlineColour()
    {
        return outlineColour;
    }
}

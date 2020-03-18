package hansonstudio.com.myapplication;

import android.content.Context;
import android.graphics.Point;
import android.widget.RelativeLayout;

import org.w3c.dom.Text;

import hansonstudio.com.myapplication.Values.Colours;
import hansonstudio.com.myapplication.Values.TextSizes;

public class ComboBoxOutline {
    public ComboBoxOutline(Context context, RelativeLayout layout, String text, Point location, Point size)
    {
        TextElement backqround = new TextElement(context, layout, "", location, size);
        backqround.makeButtonStyle();
        TextElement title = new TextElement(context, layout, text, new Point(location.x + ViewLayout.getMargin(), location.y - ViewLayout.getMargin()*9/10), new Point(-2,-2));
        title.element.setBackgroundColor(Colours.appBackground);
        title.element.setTextSize(TextSizes.small()*2/3);
    }
}

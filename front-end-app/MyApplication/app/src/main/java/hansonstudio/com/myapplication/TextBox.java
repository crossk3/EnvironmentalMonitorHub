package hansonstudio.com.myapplication;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.RelativeLayout;

import hansonstudio.com.myapplication.Values.Colours;
import hansonstudio.com.myapplication.Values.TextSizes;

public class TextBox extends AppCompatTextView {

    public EditText element;
    private RelativeLayout.LayoutParams params;

    public TextBox(Context context, RelativeLayout layout, String text, Point location, Point size)
    {
        super(context);
        element = new EditText(context);
        element.setText(text);
        element.setTextColor(Colours.ganttChartBlue);
        element.setGravity(Gravity.CENTER);
        element.setTextSize(TextSizes.small());
        params = new RelativeLayout.LayoutParams(size.x, size.y);
        params.setMargins(location.x, location.y, location.x, location.y);//left top right bottom

        layout.addView(element, params);
    }

    public void setTextSize(float size)
    {
        element.setTextSize(size);
    }
}

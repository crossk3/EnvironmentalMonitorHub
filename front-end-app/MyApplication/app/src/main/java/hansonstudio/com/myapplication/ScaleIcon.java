package hansonstudio.com.myapplication;

import android.content.Context;
import android.graphics.Point;
import android.widget.RelativeLayout;

public class ScaleIcon extends DiagnosticIcon {
    public ScaleIcon(Context context, RelativeLayout layout, String icon, String text, Point location)
    {
        super(context, layout, icon, text, location);
    }

    public ScaleIcon(Context context, RelativeLayout layout, Class activityClass, String icon, String text, Point location)
    {
        super(context, layout, activityClass, icon, text, location);
    }

    @Override
    public Void call() throws Exception {
        return null;
    }

}

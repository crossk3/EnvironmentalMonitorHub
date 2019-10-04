package hansonstudio.com.myapplication;

import android.content.Context;
import android.graphics.Point;
import android.widget.RelativeLayout;

import java.util.concurrent.Callable;

public class BinaryIcon extends DiagnosticIcon implements Callable<Void> {
    public BinaryIcon(Context context, RelativeLayout layout, String icon, String text, Point location)
    {
        super(context, layout, icon, text, location);
    }


    @Override
    public Void call() throws Exception {
        return null;
    }
}

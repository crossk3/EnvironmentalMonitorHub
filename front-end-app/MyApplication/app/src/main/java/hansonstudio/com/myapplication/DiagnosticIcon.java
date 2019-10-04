package hansonstudio.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.concurrent.Callable;

import hansonstudio.com.myapplication.Values.Colours;

public abstract class DiagnosticIcon extends TextElement implements Callable<Void>{

    public DiagnosticIcon(Context context, RelativeLayout layout, String icon, String text, Point location)
    {
        super(context, layout, icon, location, ViewLayout.getDiagnosticIconSize());
        SpannableString formattedString = new SpannableString(icon + "\n\n" + text);
        formattedString.setSpan(new RelativeSizeSpan(0.32f), icon.length(),icon.length() + text.length() + 2,0);
        formattedString.setSpan(new ForegroundColorSpan(Colours.text),icon.length(),icon.length() + text.length() + 2, 0);
        makeButtonStyle();
        makeTitle();
        element.setGravity(Gravity.CENTER);
        element.setText(formattedString);
    }

    public DiagnosticIcon(final Context context, RelativeLayout layout, final Class activityClass, String icon, String text, Point location)
    {
        super(context, layout, icon, location, ViewLayout.getDiagnosticIconSize());
        SpannableString formattedString = new SpannableString(icon + "\n\n" + text);
        formattedString.setSpan(new RelativeSizeSpan(0.32f), icon.length(),icon.length() + text.length() + 2,0);
        formattedString.setSpan(new ForegroundColorSpan(Colours.text),icon.length(),icon.length() + text.length() + 2, 0);
        makeButtonStyle();
        makeTitle();
        element.setGravity(Gravity.CENTER);
        element.setText(formattedString);

        element.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activityClass);
                context.startActivity(intent);
            }

        });
    }
}

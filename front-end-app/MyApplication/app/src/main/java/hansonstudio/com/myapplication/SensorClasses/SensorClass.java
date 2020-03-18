package hansonstudio.com.myapplication.SensorClasses;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.view.View;
import android.widget.RelativeLayout;
import hansonstudio.com.myapplication.Graph;
import hansonstudio.com.myapplication.R;
import hansonstudio.com.myapplication.ServerCommunication;
import hansonstudio.com.myapplication.TextElement;
import hansonstudio.com.myapplication.Values.Colours;
import hansonstudio.com.myapplication.Values.TextSizes;
import hansonstudio.com.myapplication.Values.UnicodeIcons;
import hansonstudio.com.myapplication.ViewLayout;

public abstract class SensorClass extends AppCompatActivity {

    protected RelativeLayout myLayout;
    protected TextElement refreshButton;

    protected TextElement latestReadingDisplay;
    protected Graph graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_main);
        RelativeLayout mainLayout = findViewById(R.id.mainLayout);
        myLayout = ViewLayout.ActivitySetup(this, mainLayout);

        TextElement finishButton = new TextElement(this, myLayout, UnicodeIcons.back,
                new Point(ViewLayout.screenHeight()/100,0), new Point(-2, -2));
        finishButton.makeTitle();
        finishButton.element.setTextColor(Colours.ganttChartBlue);
        finishButton.element.setTypeface(null, Typeface.BOLD);
        finishButton.element.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        refreshButton = new TextElement(this, myLayout, UnicodeIcons.refresh,
                new Point(ViewLayout.screenHeight()/100,0), new Point(-2, -2));
        refreshButton.makeTitle();
        refreshButton.rightJustify();
        refreshButton.element.setTextColor(Colours.ganttChartBlue);
        refreshButton.element.setTypeface(null, Typeface.BOLD);
    }

    protected void CreateTitle(String titleText)
    {
        TextElement title = new TextElement(this, myLayout, titleText,
                new Point(0,ViewLayout.screenHeight()/100), new Point(ViewLayout.screenWidth(), -2));
        title.makeTitle();
    }

    protected void refreshView(SpannableString valueString, String key)
    {
        latestReadingDisplay = new TextElement(this, myLayout, "",
                new Point(0,ViewLayout.screenHeight()/8), new Point(ViewLayout.screenWidth(), -2));
        latestReadingDisplay.element.setTextSize(TextSizes.medium());
        latestReadingDisplay.element.setText(valueString);

        graph = new Graph(this, myLayout,
                new Point(0, ViewLayout.screenHeight()/4),
                new Point(14*ViewLayout.screenWidth()/15, 4*ViewLayout.screenHeight()/5),
                "    History");
        ServerCommunication.populateGraphTest(this, key, graph);
    }

    public void OnRefreshButtonClick(final Runnable method) {
        refreshButton.element.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                clearActivityData();
                method.run();
            }
        });
    }

    protected void clearActivityData()
    {
        graph.remove(myLayout);
        latestReadingDisplay.element.setText("");

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        clearActivityData();
    }
}

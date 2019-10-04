package hansonstudio.com.myapplication.SensorClasses;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.RelativeLayout;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import hansonstudio.com.myapplication.Graph;
import hansonstudio.com.myapplication.R;
import hansonstudio.com.myapplication.TextElement;
import hansonstudio.com.myapplication.UpdateGraph;
import hansonstudio.com.myapplication.Values.Colours;
import hansonstudio.com.myapplication.Values.TextSizes;
import hansonstudio.com.myapplication.Values.UnicodeIcons;
import hansonstudio.com.myapplication.ViewLayout;

public class Temperature extends AppCompatActivity{

    private RelativeLayout myLayout;
    public final float lowerTempThreshold = 19;
    public final float upperTempThreshold = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_main);

        RelativeLayout mainLayout = findViewById(R.id.mainLayout);

        myLayout = ViewLayout.ActivitySetup(this, mainLayout);

        TextElement title = new TextElement(this, myLayout, "Temperature",
                new Point(0,ViewLayout.getScreenHeight()/100), new Point(ViewLayout.getScreenWidth(), -2));
        title.makeTitle();

        TextElement refreshButton = new TextElement(this, myLayout, UnicodeIcons.refresh,
                new Point(ViewLayout.getScreenHeight()/100,ViewLayout.getScreenHeight()/100), new Point(-2, -2));
        refreshButton.makeTitle();
        refreshButton.element.setTextColor(Colours.ganttChartBlue);
        refreshButton.element.setTypeface(null, Typeface.BOLD);

        SpannableString tempString = new SpannableString("Latest Temperature Reading\n" + getCurrentTemp() + "°C");

        tempString.setSpan(
                new ForegroundColorSpan(Colours.getThresholdColour(getCurrentTemp(),
                        lowerTempThreshold, upperTempThreshold)),
                26,tempString.length(), 0);

        TextElement tempDisplay = new TextElement(this, myLayout, "",
                new Point(0,ViewLayout.getScreenHeight()/8), new Point(ViewLayout.getScreenWidth(), -2));
        tempDisplay.element.setTextSize(TextSizes.medium());
        tempDisplay.element.setText(tempString);

        Graph graph = new Graph(this, myLayout,
                new Point(0, ViewLayout.getScreenHeight()/4),
                new Point(14*ViewLayout.getScreenWidth()/15, 4*ViewLayout.getScreenHeight()/5),
        "    History");

        graph.populate(getDataSeries(0));
        //UpdateGraph temperature = new UpdateGraph(graph, tempDisplay);
    }

    private float getCurrentTemp()
    {
        return 20.3f;
    }

    private LineGraphSeries<DataPoint> getDataSeries(int offset)
    {
       DataPoint[] points = new DataPoint[50];
       for ( int i = 0; i < 50; i++)
       {
               double random = Math.random() * 10 + 15;
               points[i] = new DataPoint(i, random);
       }
        LineGraphSeries<DataPoint> series = new LineGraphSeries< >(points);

        return series;
    }
}

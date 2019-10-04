package hansonstudio.com.myapplication;

import android.content.Context;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import hansonstudio.com.myapplication.Values.Colours;
import hansonstudio.com.myapplication.Values.TextSizes;
import hansonstudio.com.myapplication.Values.UnicodeIcons;

public class Graph {

    private GraphView graph;

    public Graph(final Context context, RelativeLayout layout, Point location, Point size, String title)
    {
        RelativeLayout.LayoutParams graphParams = new RelativeLayout.LayoutParams(
                size.x, size.y);
        graphParams.setMargins(location.x, location.y,0,ViewLayout.getScreenWidth()/10);//left top right bottom
        graph = new GraphView(context);
        layout.addView(graph, graphParams);

        int settingsButtonSize = ViewLayout.getScreenWidth()/9;
        TextElement settingsButton = new TextElement(
                context,
                layout,
                UnicodeIcons.settings,
                new Point(location.x + size.x - settingsButtonSize - ViewLayout.getScreenWidth()/70,
                        location.y + ViewLayout.getScreenWidth()/40),
                new Point(settingsButtonSize,settingsButtonSize));
        settingsButton.makeTitle();
        settingsButton.element.setTextColor(Colours.ganttChartBlue);
        settingsButton.element.setGravity(Gravity.TOP | Gravity.RIGHT);
        settingsButton.element.setTextSize(TextSizes.medium());
        settingsButton.element.setOnClickListener(new View.OnClickListener()
        {
            int k = 0;
            @Override
            public void onClick(View v) {
                showPopupWindow(v, context);
            }

        });


        graph.setTitle(title);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitleColor(Colours.text);
        gridLabel.setHorizontalLabelsVisible(false);
        gridLabel.setHorizontalAxisTitle("          09/11/2001|04:20 - 20/04/2069|09:11");
        graph.setTitleTextSize(graph.getTitleTextSize()*2.5f);
    }

    public void populate(LineGraphSeries <DataPoint> series)
    {
            graph.removeAllSeries();
            series.setColor(Colours.ganttChartBlue);
            graph.getGridLabelRenderer().setGridColor(Colours.text);
            graph.getGridLabelRenderer().setVerticalLabelsColor(Colours.text);
            graph.getGridLabelRenderer().setHorizontalLabelsColor(Colours.text);
            graph.addSeries(series);
            graph.setTitleColor(Colours.text);

            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMaxX(series.getHighestValueX());
            graph.getViewport().setMinX(series.getLowestValueX());

            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMaxY(series.getHighestValueY() * 1.1f);
            graph.getViewport().setMinY(series.getLowestValueY() *0.9f);
    }

    public void showPopupWindow(View view, Context context) {

    }
}

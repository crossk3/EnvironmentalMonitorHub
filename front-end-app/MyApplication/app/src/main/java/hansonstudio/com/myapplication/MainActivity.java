package hansonstudio.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import hansonstudio.com.myapplication.SensorClasses.StringKeys;
import hansonstudio.com.myapplication.Values.TextSizes;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout myLayout;
    private TextElement status;
    private TextElement airQuality;
    private TextElement temperature;
    private TextElement latestReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_main);
        RelativeLayout mainLayout = findViewById(R.id.mainLayout);
        myLayout = ViewLayout.ActivitySetup(this, mainLayout);

        TextElement title = new TextElement(this, myLayout, "Environmental Monitoring System",
                new Point(0,ViewLayout.screenHeight()/100), new Point(ViewLayout.screenWidth(), -2));
        title.makeTitle();
        createBasicDiagnostics();
        createDiagnosticPageButton(this);
    }

    private void createBasicDiagnostics()
    {
        int topMargin = ViewLayout.screenHeight() / 5;
        int margin = ViewLayout.getMargin() * 6;
        status = new TextElement(
                this,
                myLayout,
                "System Status",
                new Point(0,topMargin),
                new Point(-1, -2));

        airQuality = new TextElement(
                this,
                myLayout,
                "Air Quality",
                new Point(0,topMargin + margin),
                new Point(-1, -2));

        temperature = new TextElement(
                this,
                myLayout,
                "Temperature: " + ServerCommunication.GetLatestReading(StringKeys.temperature) + "°C",
                new Point(0,topMargin + 2 * margin),
                new Point(-1, -2));

        latestReading = new TextElement(
                this,
                myLayout,
                "Latest Time Reading: \n" + ServerCommunication.getLatestTimeFromServer(),
                new Point(0,topMargin + 3 * margin),
                new Point(-1, -2));

        status.element.setTextSize(TextSizes.medium());
        airQuality.element.setTextSize(TextSizes.medium());
        temperature.element.setTextSize(TextSizes.medium());
        latestReading.element.setTextSize(TextSizes.medium());
    }

    private void createDiagnosticPageButton(final Context context)
    {
        TextElement showDiagnostics = new TextElement(context, myLayout, "Go to Diagnostics Page",
                new Point(ViewLayout.screenWidth()/5, 4*ViewLayout.screenHeight()/5), new Point(3*ViewLayout.screenWidth()/5,-1));
        showDiagnostics.makeButtonStyle();
        showDiagnostics.element.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DiagnosticActivity.class);
                context.startActivity(intent);
            }

        });
    }




    @Override
    protected void onResume()
    {
        super.onResume();
        ServerCommunication.showSystemStatus(this, status, false, false);
        ServerCommunication.setAirQualityBinary(airQuality);
    }



}

package hansonstudio.com.myapplication;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import hansonstudio.com.myapplication.SensorClasses.Acceleration;
import hansonstudio.com.myapplication.SensorClasses.AirParticles;
import hansonstudio.com.myapplication.SensorClasses.Co2;
import hansonstudio.com.myapplication.SensorClasses.Humidity;
import hansonstudio.com.myapplication.SensorClasses.Light;
import hansonstudio.com.myapplication.SensorClasses.Sound;
import hansonstudio.com.myapplication.SensorClasses.StringKeys;
import hansonstudio.com.myapplication.SensorClasses.Temperature;
import hansonstudio.com.myapplication.SensorClasses.Thresholds;
import hansonstudio.com.myapplication.SensorClasses.Tvoc;
import hansonstudio.com.myapplication.Values.Colours;
import hansonstudio.com.myapplication.Values.UnicodeIcons;

public class DiagnosticActivity extends AppCompatActivity {

    private RelativeLayout myLayout;

    private TextElement tvoc;
    private TextElement co2;
    private TextElement particleDetector;

    private TextElement humidity;
    private TextElement temperature;

    private TextElement light;
    private TextElement sound;
    private TextElement acceleration;

    private TextElement systemStatus;
    private TextElement sensorUtilities;
    private TextElement refresh;


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

        final int iconTopMargin = ViewLayout.screenHeight() / 6;
        createOutlines(iconTopMargin);
        createHealthIcons(iconTopMargin);
        createScaleIcons(iconTopMargin);
        createBinaryIcons(iconTopMargin);
        createSystemStatusIcon(iconTopMargin, this);
    }

    public void createOutlines(int iconTopMargin)
    {
        int margin = ViewLayout.getMargin();
        int iconSize = ViewLayout.getDiagnosticIconSize().x;
        int iconMargin = ViewLayout.getIconMargin();
        new ComboBoxOutline(
                this,
                myLayout,
                "Air Quality",
                new Point(iconMargin - margin, iconTopMargin - margin),
                new Point(2 * margin + 3 * iconSize + 2 * iconMargin,2 * margin + iconSize));

        new ComboBoxOutline(
                this,
                myLayout,
                "Ambient Environment",
                new Point(iconMargin - margin, iconTopMargin + iconMargin + iconSize - margin),
                new Point(2 * margin + 3 * iconSize + 2 * iconMargin,2 * margin + iconSize));

        new ComboBoxOutline(
                this,
                myLayout,
                "Human Presence",
                new Point(iconMargin - margin, iconTopMargin + 2 * iconMargin + 2 * iconSize - margin),
                new Point(2 * margin + 3 * iconSize + 2 * iconMargin,2 * margin + iconSize));

        new ComboBoxOutline(
                this,
                myLayout,
                "Utilities",
                new Point(iconMargin - margin, iconTopMargin + 3 * iconMargin + 3 * iconSize - margin),
                new Point(2 * margin + 3 * iconSize + 2 * iconMargin,2 * margin + iconSize));
    }

    public void createHealthIcons(int iconTopMargin)
    {
        tvoc = new SafetyIcon(
                this,
                myLayout,
                Tvoc.class,
                UnicodeIcons.gasses,
                "tVOC",
                new Point(ViewLayout.getIconMargin(),iconTopMargin));

        co2 = new SafetyIcon(
                this,
                myLayout,
                Co2.class,
                UnicodeIcons.factory,
                "CO\u2082",
                new Point(ViewLayout.getIconMargin() * 2 + ViewLayout.getDiagnosticIconSize().x,iconTopMargin));

        particleDetector = new SafetyIcon(
                this,
                myLayout,
                AirParticles.class,
                UnicodeIcons.dust,
                " Air Particles",
                new Point(ViewLayout.getIconMargin() * 3 + 2* ViewLayout.getDiagnosticIconSize().x,iconTopMargin));
    }

    public void createScaleIcons(int iconTopMargin)
    {
        humidity = new ScaleIcon(
                this,
                myLayout,
                Humidity.class,
                UnicodeIcons.rainDrop,
                "Humidity",
                new Point(ViewLayout.getIconMargin(),iconTopMargin + ViewLayout.getIconMargin() + ViewLayout.getDiagnosticIconSize().x));

        temperature = new ScaleIcon(
                this,
                myLayout,
                Temperature.class,
                UnicodeIcons.thermometer,
                "Temperature",
                new Point(ViewLayout.getIconMargin() * 2 + ViewLayout.getDiagnosticIconSize().x,iconTopMargin + ViewLayout.getIconMargin() + ViewLayout.getDiagnosticIconSize().x));
    }

    public void createBinaryIcons(int iconTopMargin)
    {
        light = new BinaryIcon(
                this,
                myLayout,
                Light.class,
                UnicodeIcons.lightBulb,
                "Light",
                new Point(ViewLayout.getIconMargin(),iconTopMargin + 2 * ViewLayout.getIconMargin() + 2 * ViewLayout.getDiagnosticIconSize().x));

        sound = new BinaryIcon(
                this,
                myLayout,
                Sound.class,
                UnicodeIcons.microphone,
                "Sound",
                new Point(ViewLayout.getIconMargin() * 2 + ViewLayout.getDiagnosticIconSize().x,iconTopMargin + 2 * ViewLayout.getIconMargin() + 2 * ViewLayout.getDiagnosticIconSize().x));

        acceleration = new BinaryIcon(
                this,
                myLayout,
                Acceleration.class,
                UnicodeIcons.acceleration,
                "Acceleration",
                new Point(ViewLayout.getIconMargin() * 3 + 2 * ViewLayout.getDiagnosticIconSize().x,iconTopMargin + 2 * ViewLayout.getIconMargin() + 2 * ViewLayout.getDiagnosticIconSize().x));
    }

    public void createSystemStatusIcon(int iconTopMargin, final Context context)
    {
        systemStatus = new BinaryIcon(
                this,
                myLayout,
                null,
                UnicodeIcons.power,
                " System Status ",
                new Point(ViewLayout.getIconMargin(),iconTopMargin + 3 * ViewLayout.getIconMargin() + 3 * ViewLayout.getDiagnosticIconSize().x));
        systemStatus.element.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                ServerCommunication.showSystemStatus(context, systemStatus, true, true);
            }

        });

        sensorUtilities = new BinaryIcon(
                this,
                myLayout,
                SensorUtilities.class,
                UnicodeIcons.settings,
                " Sensor Utilities ",
                new Point(ViewLayout.getIconMargin() * 2 + ViewLayout.getDiagnosticIconSize().x,iconTopMargin + 3 * ViewLayout.getIconMargin() + 3 * ViewLayout.getDiagnosticIconSize().x));


        refresh = new BinaryIcon(
                this,
                myLayout,
                DiagnosticActivity.class,
                UnicodeIcons.refresh,
                " Refresh Page ",
                new Point(ViewLayout.getIconMargin() * 3 + ViewLayout.getDiagnosticIconSize().x * 2,iconTopMargin + 3 * ViewLayout.getIconMargin() + 3 * ViewLayout.getDiagnosticIconSize().x));

        refresh.element.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                refresh();
            }

        });

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        refresh();
    }

    private void refresh()
    {
        ServerCommunication.showSystemStatus(this, systemStatus, true, false);
        tvoc.makeButtonStyle(getOutlineColor(StringKeys.tvoc, 0, Thresholds.tvoc));
        co2.makeButtonStyle(getOutlineColor(StringKeys.co2, 0, Thresholds.co2+20));
        particleDetector.makeButtonStyle(getOutlineColor(StringKeys.airParticles, 0, Thresholds.airParticles));
        temperature.makeButtonStyle(getOutlineColor(StringKeys.temperature, Thresholds.temperatureLower, Thresholds.temperatureUpper));
        humidity.makeButtonStyle(getOutlineColor(StringKeys.humidity, 0, Thresholds.humidity));
    }

    private int getOutlineColor(String key, float lowerThreshold, float upperThreshold)
    {
        return Colours.getThresholdColour(ServerCommunication.GetLatestReading(key), lowerThreshold, upperThreshold);
    }

}

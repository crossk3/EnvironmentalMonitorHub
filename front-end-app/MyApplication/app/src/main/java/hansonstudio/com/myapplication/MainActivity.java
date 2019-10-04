package hansonstudio.com.myapplication;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import hansonstudio.com.myapplication.SensorClasses.Temperature;
import hansonstudio.com.myapplication.Values.UnicodeIcons;

public class MainActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_main);
        RelativeLayout mainLayout = findViewById(R.id.mainLayout);
        myLayout = ViewLayout.ActivitySetup(this, mainLayout);

        TextElement title = new TextElement(this, myLayout, "Environmental Monitoring System",
                new Point(0,ViewLayout.getScreenHeight()/100), new Point(ViewLayout.getScreenWidth(), -2));
        title.makeTitle();

        final int iconTopMargin = ViewLayout.getScreenHeight() / 6;
        createHealthIcons(iconTopMargin);
        createScaleIcons(iconTopMargin);
        createBinaryIcons(iconTopMargin);
        createSystemStatusIcon(iconTopMargin);
    }

    public void createHealthIcons(int iconTopMargin)
    {
        tvoc = new SafetyIcon(
                this,
                myLayout,
                UnicodeIcons.gasses,
                "TVOC",
                new Point(ViewLayout.getIconMargin(),iconTopMargin));

        co2 = new SafetyIcon(
                this,
                myLayout,
                UnicodeIcons.factory,
                "CO\u2082",
                new Point(ViewLayout.getIconMargin() * 2 + ViewLayout.getDiagnosticIconSize().x,iconTopMargin));

        particleDetector = new SafetyIcon(
                this,
                myLayout,
                UnicodeIcons.dust,
                " Air Particles",
                new Point(ViewLayout.getIconMargin() * 3 + 2* ViewLayout.getDiagnosticIconSize().x,iconTopMargin));
    }

    public void createScaleIcons(int iconTopMargin)
    {
        humidity = new ScaleIcon(
                this,
                myLayout,
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
                UnicodeIcons.lightBulb,
                "Light",
                new Point(ViewLayout.getIconMargin(),iconTopMargin + 2 * ViewLayout.getIconMargin() + 2 * ViewLayout.getDiagnosticIconSize().x));

        sound = new BinaryIcon(
                this,
                myLayout,
                UnicodeIcons.microphone,
                "Sound",
                new Point(ViewLayout.getIconMargin() * 2 + ViewLayout.getDiagnosticIconSize().x,iconTopMargin + 2 * ViewLayout.getIconMargin() + 2 * ViewLayout.getDiagnosticIconSize().x));

        acceleration = new BinaryIcon(
                this,
                myLayout,
                UnicodeIcons.acceleration,
                "Acceleration",
                new Point(ViewLayout.getIconMargin() * 3 + 2 * ViewLayout.getDiagnosticIconSize().x,iconTopMargin + 2 * ViewLayout.getIconMargin() + 2 * ViewLayout.getDiagnosticIconSize().x));
    }

    public void createSystemStatusIcon(int iconTopMargin)
    {
        systemStatus = new BinaryIcon(
                this,
                myLayout,
                UnicodeIcons.power,
                "System Status",
                new Point(ViewLayout.getIconMargin() * 3 + 2 * ViewLayout.getDiagnosticIconSize().x,iconTopMargin + 3 * ViewLayout.getIconMargin() + 3 * ViewLayout.getDiagnosticIconSize().x));
    }

}

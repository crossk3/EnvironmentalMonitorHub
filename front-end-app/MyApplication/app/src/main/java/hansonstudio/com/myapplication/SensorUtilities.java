package hansonstudio.com.myapplication;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import hansonstudio.com.myapplication.Values.TextSizes;

public class SensorUtilities extends AppCompatActivity {

    private RelativeLayout myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_main);
        RelativeLayout mainLayout = findViewById(R.id.mainLayout);
        myLayout = ViewLayout.ActivitySetup(this, mainLayout);

        TextElement title = new TextElement(this, myLayout, "Sensor Utilities",
                new Point(0,ViewLayout.screenHeight()/100), new Point(ViewLayout.screenWidth(), -2));
        title.makeTitle();

        int margin = ViewLayout.screenHeight()/50;



        TextElement airTitle = new TextElement(this,
                myLayout,
                "Air Quality Safety Limits",
                new Point(0, 4 * margin),
                new Point(ViewLayout.screenWidth(), -2));
        airTitle .setTextSize(TextSizes.medium());

        new TextElement(this,
                myLayout,
                "CO₂ (ppm)",
                new Point(0, 7 * margin),
                new Point(ViewLayout.screenWidth(), -2));

        new TextBox(this,
                myLayout,
                "1000",
                new Point(ViewLayout.screenWidth()/4, 8 * margin),
                new Point(ViewLayout.screenWidth()/2, -2));

        new TextElement(this,
                myLayout,
                "tVOC (ppb)",
                new Point(0, 12 * margin),
                new Point(ViewLayout.screenWidth(), -2));
        new TextBox(this,
                myLayout,
                "5000",
                new Point(ViewLayout.screenWidth()/4, 13 * margin),
                new Point(ViewLayout.screenWidth()/2, -2));

        new TextElement(this,
                myLayout,
                "Air Particles (ppm)",
                new Point(0, 17 * margin),
                new Point(ViewLayout.screenWidth(), -2));
        new TextBox(this,
                myLayout,
                "170",
                new Point(ViewLayout.screenWidth()/4, 18 * margin),
                new Point(ViewLayout.screenWidth()/2, -2));



        TextElement envTitle = new TextElement(this,
                myLayout,
                "Ambient Environment",
                new Point(0, 23 * margin),
                new Point(ViewLayout.screenWidth(), -2));
        envTitle.setTextSize(TextSizes.medium());

        new TextElement(this,
                myLayout,
                "Minimum Temperature (°C)",
                new Point(0, 26 * margin),
                new Point(ViewLayout.screenWidth(), -2));
        new TextBox(this,
                myLayout,
                "17",
                new Point(ViewLayout.screenWidth()/4, 27 * margin),
                new Point(ViewLayout.screenWidth()/2, -2));

        new TextElement(this,
                myLayout,
                "Maximum Temperature (°C)",
                new Point(0, 31 * margin),
                new Point(ViewLayout.screenWidth(), -2));
        new TextBox(this,
                myLayout,
                "27",
                new Point(ViewLayout.screenWidth()/4, 32 * margin),
                new Point(ViewLayout.screenWidth()/2, -2));

        new TextElement(this,
                myLayout,
                "Maximum Humidity (%)",
                new Point(0, 36 * margin),
                new Point(ViewLayout.screenWidth(), -2));

        new TextBox(this,
                myLayout,
                "50",
                new Point(ViewLayout.screenWidth()/4, 37 * margin),
                new Point(ViewLayout.screenWidth()/2, -2));








        TextElement saveButton = new TextElement(this,
                myLayout,
                "Save",
                new Point(margin, 42 * margin),
                new Point((ViewLayout.screenWidth() - 3*margin)/2, -2));
        saveButton.makeButtonStyle();

        TextElement cancelButton = new TextElement(this,
                myLayout,
                "Cancel",
                new Point((ViewLayout.screenWidth()+ margin)/2, 42 * margin),
                new Point((ViewLayout.screenWidth() - 3*margin)/2, -2));
        cancelButton.makeButtonStyle();

        TextElement resetButton = new TextElement(this,
                myLayout,
                "Reset to Defaults",
                new Point(margin, 45 * margin),
                new Point(ViewLayout.screenWidth() - 2*margin, -2));
        resetButton.makeButtonStyle();

    }




    @Override
    protected void onResume()
    {
        super.onResume();
    }



}


package hansonstudio.com.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;

import hansonstudio.com.myapplication.Values.Colours;
import hansonstudio.com.myapplication.Values.GraphTimes;
import hansonstudio.com.myapplication.Values.TextSizes;

public class GraphSettings extends AppCompatActivity {

    private RelativeLayout myLayout;

    private TextElement startTime;
    private Switch startSwitch;
    private TextElement btnSetStartDate;
    private TextElement btnSetStartTime;

    private TextElement endTime;
    private Switch endSwitch;
    private TextElement btnSetEndDate;
    private TextElement btnSetEndTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_main);

        RelativeLayout mainLayout = findViewById(R.id.mainLayout);

        myLayout = ViewLayout.ActivitySetup(this, mainLayout);

        TextElement title = new TextElement(this, myLayout, "Graph Settings",
                new Point(0, ViewLayout.screenHeight() / 100), new Point(ViewLayout.screenWidth(), -2));
        title.makeTitle();

        createTimestampTexts(this);
        updateTimestampTexts(this);
        createStartButtons(this);
        createEndButtons(this);
    }

    private void createTimestampTexts(Context context)
    {
        startTime = new TextElement(context, myLayout, "",
                new Point(0, ViewLayout.screenHeight() / 10), new Point(ViewLayout.screenWidth(), -2));

        endTime = new TextElement(context, myLayout, "",
                new Point(0, ViewLayout.screenHeight() * 45 / 100), new Point(ViewLayout.screenWidth(), -2));
    }

    private void updateTimestampTexts(Context context)
    {
        SpannableString startString = new SpannableString("Current Start Time\n" + GraphTimes.startTime);
        startString.setSpan(new RelativeSizeSpan(1.5f), 19, startString.length(),0);
        startString.setSpan(new ForegroundColorSpan(Colours.insideThreshold),19, startString.length(), 0);
        startTime.element.setText(startString);

        SpannableString endString = new SpannableString("Current End Time\n" + GraphTimes.endTime);
        endString.setSpan(new RelativeSizeSpan(1.5f), 17, endString.length(),0);
        endString.setSpan(new ForegroundColorSpan(Colours.insideThreshold),17, endString.length(), 0);
        endTime.element.setText(endString);
    }

    private void createStartButtons(final Context context)
    {

        startSwitch = createSwitch(ViewLayout.screenHeight() / 5, "Use First Available Timestamp", context);

        btnSetStartDate = new TextElement(context, myLayout, "Set Start Date",
                new Point(ViewLayout.screenWidth()/ 5, ViewLayout.screenHeight() * 27 / 100),
                new Point(ViewLayout.screenWidth() * 3 / 5, -2));
        btnSetStartDate.makeButtonStyle();

        btnSetStartTime = new TextElement(context, myLayout, "Set Start Time",
                new Point(ViewLayout.screenWidth()/ 5, ViewLayout.screenHeight() * 34 / 100),
                new Point(ViewLayout.screenWidth() * 3 / 5, -2));
        btnSetStartTime.makeButtonStyle();



        btnSetStartDate.element.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(GraphTimes.startTime, context);
            }

        });

        btnSetStartTime.element.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                openTimePickerDialog(GraphTimes.startTime, context);
            }

        });
    }

    private void createEndButtons(final Context context)
    {
        endSwitch = createSwitch(ViewLayout.screenHeight() * 55 / 100, "Use Latest Data Point", context);

        btnSetEndDate = new TextElement(context, myLayout, "Set End Date",
                new Point(ViewLayout.screenWidth()/ 5, ViewLayout.screenHeight() * 62 / 100),
                new Point(ViewLayout.screenWidth() * 3 / 5, -2));
        btnSetEndDate.makeButtonStyle();

        btnSetEndTime = new TextElement(context, myLayout, "Set End Time",
                new Point(ViewLayout.screenWidth()/ 5, ViewLayout.screenHeight() * 69 / 100),
                new Point(ViewLayout.screenWidth() * 3 / 5, -2));
        btnSetEndTime.makeButtonStyle();

        btnSetEndDate.element.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(GraphTimes.endTime, context);
            }

        });

        btnSetEndTime.element.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                openTimePickerDialog(GraphTimes.endTime, context);
            }

        });
    }


    private void openDatePickerDialog(final TStamp dateToPick, final Context context)
    {
        int year = dateToPick.getYear();
        int month = dateToPick.getMonth() - 1;
        int day = dateToPick.getDay();
        final DatePickerDialog dialog = new DatePickerDialog(context, null, year, month, day);
        dialog.show();

        Button button1 = dialog.getButton(dialog.BUTTON_POSITIVE);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateToPick.setYear(dialog.getDatePicker().getYear());
                dateToPick.setMonth(dialog.getDatePicker().getMonth() + 1);
                dateToPick.setDay(dialog.getDatePicker().getDayOfMonth());
                updateTimestampTexts(context);
                dialog.cancel();
            }
        });
    }

    public void openTimePickerDialog(final TStamp dateToPick, final Context context) {
        int hour = dateToPick.getHour();
        int min = dateToPick.getMin();

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    dateToPick.setHour(hourOfDay);
                    dateToPick.setMin(minute);
                    updateTimestampTexts(context);
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, myTimeListener, hour, min, true);
        timePickerDialog.show();
    }


    private Switch createSwitch(final int height, String text, Context context)
    {
        final RelativeLayout switchLayout = new RelativeLayout(context);
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final Switch mySwitch = new Switch(context);
        mySwitch.setText(text);
        mySwitch.setTextSize(TextSizes.small());
        mySwitch.setTextColor(Colours.text);
        mySwitch.setGravity(Gravity.CENTER);

        switchLayout.addView(mySwitch);
        myLayout.addView(switchLayout, layoutParams);

        switchLayout.post(new Runnable() {
            @Override
            public void run() {
                layoutParams.setMargins((ViewLayout.screenWidth() - switchLayout.getWidth()) / 2, height, 0, 0);//left top right bottom
                myLayout.removeView(switchLayout);
                myLayout.addView(switchLayout, layoutParams);

                ColorStateList buttonStates = new ColorStateList(
                        new int[][]{
                                new int[]{-android.R.attr.state_enabled},
                                new int[]{android.R.attr.state_checked},
                                new int[]{}
                        },
                        new int[]{
                                Colours.disabled,
                                Colours.text,
                                Colours.ganttChartBlue
                        }
                );
                mySwitch.getThumbDrawable().setTintList(buttonStates);
                mySwitch.getTrackDrawable().setTintList(buttonStates);
            }
        });

        return mySwitch;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}

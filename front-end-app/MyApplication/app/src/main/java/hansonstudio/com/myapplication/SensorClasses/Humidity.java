package hansonstudio.com.myapplication.SensorClasses;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import hansonstudio.com.myapplication.ServerCommunication;
import hansonstudio.com.myapplication.Values.Colours;

public class Humidity extends SensorClass{

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateTitle("Humidity");
        OnRefreshButtonClick(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        });
    }

    private void refresh()
    {
        SpannableString tempString = new SpannableString("Latest Humidity Reading\n" +
                ServerCommunication.GetLatestReading(StringKeys.humidity) + " %");
        tempString.setSpan(
                new ForegroundColorSpan(
                        Colours.getThresholdColour(ServerCommunication.GetLatestReading(StringKeys.humidity),
                                0, Thresholds.humidity)),
                23,tempString.length(), 0);
        refreshView(tempString, StringKeys.humidity);
    }
}
package hansonstudio.com.myapplication.SensorClasses;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import hansonstudio.com.myapplication.ServerCommunication;
import hansonstudio.com.myapplication.Values.Colours;

public class Light extends SensorClass{

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateTitle("Light Intensity");
        OnRefreshButtonClick(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        });
    }

    private void refresh()
    {
        SpannableString tempString = new SpannableString("Latest Light Intensity Reading\n" +
                ServerCommunication.GetLatestReading(StringKeys.light) + " LUX");
        tempString.setSpan(
                new ForegroundColorSpan(
                        Colours.getThresholdColour(ServerCommunication.GetLatestReading(StringKeys.light),
                                0, Thresholds.light)),
                31,tempString.length(), 0);
        refreshView(tempString, StringKeys.light);
    }
}
package hansonstudio.com.myapplication.SensorClasses;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import hansonstudio.com.myapplication.ServerCommunication;
import hansonstudio.com.myapplication.Values.Colours;

public class Co2 extends SensorClass{

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateTitle("CO₂ Concentration");
        OnRefreshButtonClick(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        });
    }

    private void refresh()
    {
        SpannableString tempString = new SpannableString("Latest CO₂ Reading\n" +
                ServerCommunication.GetLatestReading(StringKeys.co2) + " ppm");
        tempString.setSpan(
                new ForegroundColorSpan(
                        Colours.getThresholdColour(ServerCommunication.GetLatestReading(StringKeys.co2),
                                0, Thresholds.co2)),
                19,tempString.length(), 0);
        refreshView(tempString, StringKeys.co2);
    }
}
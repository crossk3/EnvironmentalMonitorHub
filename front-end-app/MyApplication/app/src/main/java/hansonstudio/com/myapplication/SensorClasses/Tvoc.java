package hansonstudio.com.myapplication.SensorClasses;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import hansonstudio.com.myapplication.ServerCommunication;
import hansonstudio.com.myapplication.Values.Colours;

public class Tvoc extends SensorClass{

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateTitle("tVOC");
        OnRefreshButtonClick(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        });
    }

    private void refresh()
    {
        SpannableString tempString = new SpannableString("Latest tVOC Reading\n" +
                ServerCommunication.GetLatestReading(StringKeys.tvoc) + " %");
        tempString.setSpan(
                new ForegroundColorSpan(
                        Colours.getThresholdColour(ServerCommunication.GetLatestReading(StringKeys.tvoc),
                                0, Thresholds.tvoc)),
                20,tempString.length(), 0);
        refreshView(tempString, StringKeys.tvoc);
    }
}
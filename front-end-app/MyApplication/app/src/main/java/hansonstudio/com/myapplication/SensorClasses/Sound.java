package hansonstudio.com.myapplication.SensorClasses;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import hansonstudio.com.myapplication.ServerCommunication;
import hansonstudio.com.myapplication.Values.Colours;

public class Sound extends SensorClass{

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateTitle("Sound");
        OnRefreshButtonClick(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        });
    }

    private void refresh()
    {
        SpannableString tempString = new SpannableString("Latest Sound Reading\n" +
                ServerCommunication.GetLatestReading(StringKeys.sound) + " dB");
        tempString.setSpan(
                new ForegroundColorSpan(
                        Colours.getThresholdColour(ServerCommunication.GetLatestReading(StringKeys.sound),
                                0, Thresholds.sound)),
                20,tempString.length(), 0);
        refreshView(tempString, StringKeys.sound);
    }
}
package hansonstudio.com.myapplication.SensorClasses;
        import android.os.Bundle;
        import android.text.SpannableString;
        import android.text.style.ForegroundColorSpan;
        import hansonstudio.com.myapplication.ServerCommunication;
        import hansonstudio.com.myapplication.Values.Colours;

public class Acceleration extends SensorClass{

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateTitle("Acceleration");
        OnRefreshButtonClick(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        });
    }

    private void refresh()
    {
        SpannableString tempString = new SpannableString("Latest Acceleration Reading\n" +
                ServerCommunication.GetLatestReading(StringKeys.acceleration) + " m/s²");
        tempString.setSpan(
                new ForegroundColorSpan(
                        Colours.getThresholdColour(ServerCommunication.GetLatestReading(StringKeys.acceleration),
                                0, Thresholds.acceleration)),
                28,tempString.length(), 0);
        refreshView(tempString, StringKeys.acceleration);
    }
}

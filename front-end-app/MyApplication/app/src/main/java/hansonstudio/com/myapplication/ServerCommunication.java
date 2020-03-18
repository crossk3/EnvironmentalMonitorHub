package hansonstudio.com.myapplication;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hansonstudio.com.myapplication.SensorClasses.StringKeys;
import hansonstudio.com.myapplication.Values.Colours;

public class ServerCommunication {

    private static String baseUrl = "http://192.168.0.100:8080/data/";

    public static void populateGraph(Context context, String key, final Graph graph)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = baseUrl + key;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)  {
                        try
                        {
                            JSONArray data = new JSONArray(response);
                            if(data.length()>0) {
                                double[] tempValues = new double[data.length()];
                                DataPoint[] points = new DataPoint[data.length()];
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject element = data.getJSONObject(i);
                                    tempValues[i] = element.getDouble("value");
                                    points[i] = new DataPoint(i, tempValues[i]);
                                }
                                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
                                graph.populate(series);
                            }
                        }
                        catch (JSONException e) {}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}});
        queue.add(stringRequest);
    }

    public static void populateGraphTest(Context context, String key, final Graph graph)
    {
        DataPoint[] points = new DataPoint[22];
        points[0] = new DataPoint(0, 23.2f);
        points[1] = new DataPoint(1, 23.1f);
        points[2] = new DataPoint(2, 23.3f);
        points[3] = new DataPoint(3, 23.2f);
        points[4] = new DataPoint(4, 23.0f);
        points[5] = new DataPoint(5, 23.1f);
        points[6] = new DataPoint(6, 22.9f);
        points[7] = new DataPoint(7, 22.2f);
        points[8] = new DataPoint(9, 21.1f);
        points[9] = new DataPoint(9, 19.4f);
        points[10] = new DataPoint(10, 18.9f);
        points[11] = new DataPoint(11, 17.7f);
        points[12] = new DataPoint(12, 16.9f);
        points[13] = new DataPoint(13, 16.1f);
        points[14] = new DataPoint(14, 15.6f);
        points[15] = new DataPoint(15, 15.3f);
        points[16] = new DataPoint(16, 15.1f);
        points[17] = new DataPoint(17, 15.0f);
        points[18] = new DataPoint(18, 15.1f);
        points[19] = new DataPoint(19, 14.86f);
        points[20] = new DataPoint(20, 15.06f);
        points[21] = new DataPoint(21, 15f);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        graph.populate(series);
    }

    public static void showSystemStatus(final Context context, final TextElement systemStatusButton, final boolean outline, final boolean showToast)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        final int duration = Toast.LENGTH_SHORT;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)  {
                        try
                        {
                            JSONArray data = new JSONArray(response);
                            if(data.length()>0) {
                                if(showToast) {
                                    Toast.makeText(context,
                                            "Found " + data.length() + "points of data on the server",
                                            duration).show();
                                }
                                if(outline)
                                    systemStatusButton.makeButtonStyle(Colours.insideThreshold);
                                systemStatusButton.element.setTextColor(Colours.insideThreshold);
                            }
                            else {
                                if(showToast) {
                                    Toast.makeText(context,
                                            "No data retrieved from the server",
                                            duration).show();
                                }
                                if(outline)
                                    systemStatusButton.makeButtonStyle(Colours.aboveThreshold);
                                systemStatusButton.element.setTextColor(Colours.aboveThreshold);
                            }
                        }
                        catch (JSONException e)
                        {
                            if(showToast) {
                                Toast.makeText(context,
                                        "Encountered the following JSON exception while trying to connect to the server: " + e,
                                        duration).show();
                            }
                            if(outline)
                                systemStatusButton.makeButtonStyle(Colours.aboveThreshold);
                            systemStatusButton.element.setTextColor(Colours.aboveThreshold);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(showToast) {
                    Toast.makeText(context,
                            "Encountered error while trying to connect to server: " + error,
                            duration).show();
                }
                if(outline)
                    systemStatusButton.makeButtonStyle(Colours.aboveThreshold);
                systemStatusButton.element.setTextColor(Colours.aboveThreshold);
            }

        });
        queue.add(stringRequest);
    }

    public static void setAirQualityBinary(final TextElement displayText)
    {
        displayText.element.setTextColor(Colours.insideThreshold);
    }

    public static float GetLatestReading(String key)
    {
        return 14.86f;
    }

    public  static TStamp getLatestTimeFromServer()
    {
        return new TStamp();
    }
}

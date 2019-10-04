package hansonstudio.com.myapplication;
import android.view.View;
import android.os.Handler;

public class UpdateGraph {
    Handler handler;
    int count;

    TextElement currentDisplay;
    Graph graph;


    public UpdateGraph(Graph graph, TextElement currentDisplay) {
        handler = new Handler();
        this.currentDisplay = currentDisplay;
        this.graph = graph;
        count = 0;
        Start();
    }

    public void Start() {
        handler.postDelayed(runnable, 0);
    }


    public void Stop(View view) {
        count = 0;
        handler.removeCallbacksAndMessages(null);
    }


    public Runnable runnable = new Runnable()
    {

        public void run() {
            currentDisplay.element.setText("Current Teperature\n2" + (count++)/10f%1.0f + "°C");
            handler.postDelayed(this, 3000);
        }

    };
}



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * Currency graph is a JavaFX application. It continously updates and displayes
 * currency data. To achieve that, this class uses an external Thread. 
 */
public class CurrencyGraph extends Application{
    private static final int MAX_DATA_POINTS = 50;
    private int xSeriesData = 0;
    private XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
    
    private ExecutorService executor;
    private ConcurrentLinkedQueue<Number> dataQ1 = new ConcurrentLinkedQueue<>();
   
    
    public static double curr = 0;

    private NumberAxis xAxis;
    
    public CurrencyGraph(){
        Data w = new Data();
        new Thread(new Data()).start();
       
    }

    private void init(Stage primaryStage) {
        xAxis = new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10);
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);

        NumberAxis yAxis = new NumberAxis();

        // Create a LineChart
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis) {
            // Override to remove symbols on each data point
            @Override
            protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
            }
        };

        lineChart.setAnimated(false);
        lineChart.setTitle("TRY against USD");
        lineChart.setHorizontalGridLinesVisible(true);

        // Set Name for Series
        series1.setName("Series 1");
       
        // Add Chart Series
        lineChart.getData().addAll(series1);

        primaryStage.setScene(new Scene(lineChart));
    }


    @Override
    public void start(Stage stage) {
        stage.setTitle("Live Currency View");
        init(stage);
        stage.show();

        executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });

        AddToQueue addToQueue = new AddToQueue();
        executor.execute(addToQueue);
        //-- Prepare Timeline
        prepareTimeline();
    }

    private class AddToQueue implements Runnable {
        public void run() {
            try {
                // add a item of random data to queue
                dataQ1.add(curr);             
                Thread.sleep(500);
                executor.execute(this);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    //-- Timeline gets called in the JavaFX Main thread
    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        }.start();
    }

    private void addDataToSeries() {
        for (int i = 0; i < 20; i++) { //-- add 20 numbers to the plot+
            if (dataQ1.isEmpty()) break;
            series1.getData().add(new XYChart.Data<>(xSeriesData++, dataQ1.remove()));
            
        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series1.getData().size() > MAX_DATA_POINTS) {
            series1.getData().remove(0, series1.getData().size() - MAX_DATA_POINTS);
        }
       
        // update
        xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
        xAxis.setUpperBound(xSeriesData - 1);
    }

    public static void main(String[] args) throws IOException{
         launch(args);
    }
}

class Data implements Runnable {
    
    public static double oneDollarsInTry = 0;
    public static double oneTryInDollars = 0;
    
    private final Thread thread;
    
    public Data(){
        thread = new Thread(this);
    }
    
    public static void updateCurrency() throws IOException {
        URL url = new URL("http://usd.fxexchangerate.com/try.xml");
        InputStream is =  url.openStream();
        try( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.contains("1.00 USD = ") ){
                    Scanner in = new Scanner(line);
                    String current = "";
                    while(!"=".equals(current)){
                        current = in.next();
                    }
                    String a = in.next();
                    oneDollarsInTry = Double.parseDouble(a);
                    CurrencyGraph.curr = oneDollarsInTry;
                    System.out.println(oneDollarsInTry + " " + new java.util.Date());
                }
                
            }
        }
        catch (MalformedURLException e) {
            throw new MalformedURLException("URL is malformed!!");
        }
        catch (IOException e) {
            throw new IOException();
        }
        
    }   
    
    @Override
    public void run() {
        long last = System.currentTimeMillis();
        
        while(true){
            long current = System.currentTimeMillis();
            
            if(current - last >= 1000){
                try{
                    last = current;
                    Thread.sleep(1000);
                    updateCurrency();
                }
                catch(InterruptedException | java.io.IOException e){
                }
            }
        }        
    }
}

package Parser;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;

public class InfluxTest {

    private static String influxUrl = "http://localhost:8086";
    private static String influxUser = "empty";
    private static String influxPassword = "";
    private static String dbName = "test_db";

    public static void main(String[] args) {

//        System.out.println(System.currentTimeMillis());


        InfluxDB influxDB = InfluxDBFactory.connect(influxUrl, influxUser, influxPassword);
        influxDB.setDatabase(dbName);

        // Flush every 2000 Points, at least every 100ms
        influxDB.enableBatch(2000, 1000, TimeUnit.MILLISECONDS);

        for (int i = 0; i < 10000; i++) {

            influxDB.write(Point.measurement("cpu")
                    .time(System.currentTimeMillis()-i*1000, TimeUnit.MILLISECONDS)
                    .addField("idle", i)
                    .addField("user", i * 2)
                    .addField("system", i * 3)
                    .build());
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        influxDB.close();




    }
}

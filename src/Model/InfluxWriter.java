package Model;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;

import static Parser.Main.influx_points_storage;

public class InfluxWriter {

    private static String influxUrl = "http://localhost:8086";
    private static String influxUser = "empty";
    private static String influxPassword = "";
    private static String dbName = "test_db";

    public static void feedInfluxPoints() {

        InfluxDB influxDB = InfluxDBFactory.connect(influxUrl, influxUser, influxPassword);
        influxDB.setDatabase(dbName);

        // Flush every 2000 Points, at least every 500 ms
        influxDB.enableBatch(2000, 500, TimeUnit.MILLISECONDS);


        for (int i = 0; i < influx_points_storage.size(); i++) {
            InfluxPoint influxPoint = influx_points_storage.get(i);
            influxDB.write(Point.measurement("message_proceeding_time")
                    .time(influxPoint.getTimestamp(), TimeUnit.MILLISECONDS)
                    .addField("min", influxPoint.getMin())
                    .addField("max", influxPoint.getMax())
                    .addField("median", influxPoint.getMedian())
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

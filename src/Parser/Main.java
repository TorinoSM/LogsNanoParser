package Parser;

import Model.*;

import java.util.*;

public class Main {

    public final static String path_to_log = "C:\\Users\\name2015\\Desktop\\logs\\20180205\\20 SberMess\\all.log";

    public final static String startParseString = "************* End Display Current Environment *************";
    public final static String endParseString = "SET END PARSE STRING IF NEEDED (NOT NULL)";

    public final static LinesStorage lines_storage = new LinesStorage(); // хранилище строк
    public final static Map<HashMap, HashMap> parsed_log_storage = new HashMap<>(); // хранилище результата: первый элемент - лог, последующие элементы - строки сообщения

    public static List<Point> in_points_storage = new ArrayList<>();
    public static List<Point> out_points_storage = new ArrayList<>();
    public static List<Point> difference_points_storage = new ArrayList<>(); // коллекция точек с вычисленной дельтой между временем получения и отправки сообщения
    public static List<Point> alone_points_storage = new ArrayList<>(); // точки для которых в логе не нашлось пары (не надена запись лога "сообщение отправлено в мессенджер"
    public static List<InfluxPoint> influx_points_storage = new ArrayList<>();


    public static void main(String[] args) {

        FileReader.readLogFiles(); // заполнить из лог-файла мапу parsed_log_storage (уже спарсенную)
        PrepareOutput.fillPointsStorage(); // заполнить объектами Point два ArrayList'a: in_points_storage - входящие id-шники, in_points_storage - исходящие
        PrepareOutput.calculateDifference(); // заполняем коллекции difference_points_storage и alone_points_storage

        Set<Long> points_timestamp_set = new TreeSet<>();
        for (int i = 0; i < difference_points_storage.size(); i++) {
            points_timestamp_set.add(difference_points_storage.get(i).getTimestamp());
        }

        for (Long dpt : points_timestamp_set) {
            ArrayList<Long> difference_points_timestamp_array = new ArrayList<>();
            for (int i = 0; i < difference_points_storage.size(); i++) {
                if (difference_points_storage.get(i).getTimestamp().equals(dpt)) {
                    difference_points_timestamp_array.add(difference_points_storage.get(i).getDifference_timestamp());
                }
            }
            PrepareOutput.calculateDifferenceStatistics(dpt, difference_points_timestamp_array); // добавили точку в influx_points_storage
        }
        InfluxWriter.feedInfluxPoints();


        System.out.println("\n\n\nin_points_storage");
        int in_points_storage_size = in_points_storage.size();
        System.out.println(in_points_storage_size);
        for (int i = 0; i < in_points_storage_size; i++) {
            System.out.println(in_points_storage.get(i).toString());
        }

        System.out.println("\n\n\nout_points_storage");
        int out_points_storage_size = out_points_storage.size();
        System.out.println(out_points_storage_size);
        for (int i = 0; i < out_points_storage_size; i++) {
            System.out.println(out_points_storage.get(i).toString());
        }


        System.out.println("\n\n\ndifference_points_storage");
        int difference_points_storage_size = difference_points_storage.size();
        System.out.println(difference_points_storage_size);
        for (int i = 0; i < difference_points_storage_size; i++) {
            System.out.println(difference_points_storage.get(i));
        }

        System.out.println("\n\n\nalone_points_storage");
        int alone_points_storage_size = alone_points_storage.size();
        System.out.println(alone_points_storage_size);
        for (int i = 0; i < alone_points_storage_size; i++) {
            System.out.println(alone_points_storage.get(i));
        }


    }
}


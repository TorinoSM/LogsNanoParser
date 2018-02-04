package Parser;

import Model.FileReader;
import Model.LinesStorage;
import Model.Point;
import Model.PrepareOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public final static String path_to_log = "C:\\Users\\name2015\\Desktop\\logs\\20180202 1549\\20 SberMess\\SystemOut.log";

    public final static String startParseString = "************* End Display Current Environment *************";
    public final static String endParseString = "SET END PARSE STRING IF NEEDED (NOT NULL)";

    public final static LinesStorage lines_storage = new LinesStorage(); // хранилище строк
    public final static Map<HashMap, HashMap> parsed_log_storage = new HashMap<>(); // хранилище результата: первый элемент - лог, последующие элементы - строки сообщения

    public static ArrayList<Point> in_points_storage = new ArrayList<>();
    public static ArrayList<Point> out_points_storage = new ArrayList<>();
    public static ArrayList<Point> difference_points_storage = new ArrayList<>(); // коллекция точек с вычисленной дельтой между временем получения и отправки сообщения
    public static ArrayList<Point> alone_points_storage = new ArrayList<>(); // точки для которых в логе не нашлось пары (не надена запись лога "сообщение отправлено в мессенджер"

    public static void main(String[] args) {

        FileReader.readLogFiles(); // заполнить из лог-файла мапу parsed_log_storage (уже спарсенную)
        PrepareOutput.fillPointsStorage(); // заполнить объектами Point два ArrayList'a: in_points_storage - входящие id-шники, in_points_storage - исходящие
        PrepareOutput.calculateDifference(); // заполняем коллекции difference_points_storage и alone_points_storage
        PrepareOutput.calculateDifferenceStatistics();


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


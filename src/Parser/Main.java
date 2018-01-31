package Parser;

import Model.FileReader;
import Model.LinesStorage;
import Model.TimeConverter;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private final static String path_to_log = "C:\\Users\\name2015\\Desktop\\logs\\20180124 1454\\SM20\\SystemOut.log";

    private final static String startParseString = "************* End Display Current Environment *************";
    private final static String endParseString = "SET END PARSE STRING IF NEEDED (NOT NULL)";


    public static void main(String[] args) {

        LinesStorage linesStorage = new LinesStorage(); // хранилище строк
        Map<HashMap, HashMap> result = new HashMap<>(); // хранилище результата: первый элемент - лог, последующие элементы - строки сообщения

        FileReader.readLogFiles(path_to_log, startParseString, endParseString, linesStorage, result);

        // номер строки, время, id, наименование системы (направление сообщения)
        for (HashMap.Entry<HashMap, HashMap> entry : result.entrySet()) {
            HashMap<String, String> log_entry = entry.getKey(); // мапа со строкой лога
            HashMap<String, String> message_entry = entry.getValue(); // мапа со строками сообщения

            linesStorage.getMessageEpochTime(log_entry);

        }

        result.entrySet().forEach(System.out::println);

        System.out.println(TimeConverter.convertTimeToEpoch("01/24/18 14:54:03:964 MSK"));

    }

}

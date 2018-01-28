package Parser;

import Model.LinesStorage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private final static String PATH_TO_LOG = "C:\\Users\\name2015\\Desktop\\logs\\20180124 1454\\SM20\\SystemOut.log";

    public static void main(String[] args) {

        int line_number = 0;
        boolean ready_to_parse = false; // если находимся внутри лога, а не начальной секции или заключительной секции
        String startParseString = "************* End Display Current Environment *************";
        String endParseString = "SET END PARSE STRING IF NEEDED (NOT NULL)";
        LinesStorage linesStorage = new LinesStorage();
        Map<HashMap, HashMap> result = new HashMap<HashMap, HashMap>();


        try {
            File file = new File(PATH_TO_LOG);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line;
//            int i=0; // debug
            while (true) { // debug i < 200
                line = reader.readLine();
                line_number++;
                if (line == null) { // если конец файла то прерываем цикл
                    break;
                } else {
                    if (line.startsWith(startParseString)) {
                        ready_to_parse = true; // если дошли до строчки "************* End Display Current Environment *************" то начинаем парсить со следующей строки лога
                        continue; // continue while
                    }
                    if (line.startsWith(endParseString) && ready_to_parse) {
                        ready_to_parse = false; // если дошли до строчки endParseString и до этого парсинг был включен то заканчиваем парсить строки
                        break; // break while
                    }
                    if (ready_to_parse) { // если парсинг включен то парсим строку
                        linesStorage.parseLine(line_number, line, result);
                    }
                }
//                i++; // debug
            }

        } catch (FileNotFoundException e) {
            System.out.println("File " + PATH_TO_LOG + " not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result.entrySet().forEach(System.out::println);
    }
}

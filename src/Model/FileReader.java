package Model;

import Parser.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReader {

    private static int line_number = 0; // номер строки в логе
    private static boolean ready_to_parse = false; // если находимся внутри лога, а не начальной секции или заключительной секции

    public static void readLogFiles() {
        try {
            File file = new File(Main.path_to_log);
            java.io.FileReader fr = new java.io.FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line;
            while (true) {
                line = reader.readLine();
                line_number++;
                if (line == null) { // если конец файла то прерываем цикл
                    break;
                } else {
                    if (line.startsWith(Main.startParseString)) {
                        ready_to_parse = true; // если дошли до строчки "************* End Display Current Environment *************" то начинаем парсить со следующей строки лога
                        continue; // while
                    }
                    if (line.startsWith(Main.endParseString) && ready_to_parse) {
                        ready_to_parse = false; // если дошли до строчки endParseString и до этого парсинг был включен то заканчиваем парсить строки
                        break; // while
                    }
                    if (ready_to_parse) { // если парсинг включен то парсим строку
                        Parser.parseLine(line_number, line);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File " + Main.path_to_log + " not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

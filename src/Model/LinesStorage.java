package Model;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinesStorage {


    public static String rx_string_of_log = "^\\[\\d{1,2}/\\d{1,2}/\\d{1,2}.+?$"; //  паттерн строки лога, например [1/24/18 14:36:11:322 MSK] 000000ae SystemOut     O 2018-01-24 14:36:11 DEBUG [WebSphere_EJB_NP_Timer_Service.Alarm Pool : 0]: [Consumer id=937510382] got 0 records
    public static String rx_open_bracket = "^\\{$"; //  паттерн открывающей фигурной скобки
    public static String rx_close_bracket = "^\\}$"; //  паттерн открывающей фигурной скобки

    Pattern pattern_string_of_log = Pattern.compile(rx_string_of_log);
    Pattern pattern_open_bracket = Pattern.compile(rx_open_bracket);
    Pattern pattern_close_bracket = Pattern.compile(rx_close_bracket);

    boolean inside_message = false;
    private long id = -1; // id сообщения
    private String message_location = ""; // откуда и куда сообщение
    private int string_number = 0; // номер строки сообщения

    private Map<Integer, String> string_of_log = new HashMap<>(); // строка отнсящаяся к логу: номер строки, содержимое строки
    private Map<Integer, String> message = new HashMap<>(); // строка относящаяся к сообщению: номер строки, содержимое строки

    private void addStringOfLog(int number_of_string, String string_to_add) {
        string_of_log.clear();
        string_of_log.put(number_of_string, string_to_add);
    }

    private void addMessageStringFromLog(String message_string_to_add) {
        if (string_of_log.isEmpty()) {
            System.out.println("Перед добавлением сообщения добавь строку лога, которая содержит информацию о сообщении");
        } else {
            if (string_number == 0) {
                message.clear(); // если сообщений пока нет, то чистим мапу
            }
            string_number++;
            message.put(string_number, message_string_to_add);
        }
    }

    public void parseLine(int number_of_string, String matching_line, Map result) {

        Matcher m_string_of_log = pattern_string_of_log.matcher(matching_line);

        if (m_string_of_log.find()) {
            addStringOfLog(number_of_string, matching_line);
            return;
        }

        Matcher m_open_bracket = pattern_open_bracket.matcher(matching_line);

        if (m_open_bracket.find()) {
            inside_message = true;
            addMessageStringFromLog(matching_line);
            return;
        }

        Matcher m_close_bracket = pattern_close_bracket.matcher(matching_line);

        if (m_close_bracket.find()) {
            inside_message = false;
            addMessageStringFromLog(matching_line);
            saveResult(result);
            message.clear();
            string_number = 0;
            return;
        }

        if (inside_message) {
            addMessageStringFromLog(matching_line);
        }
    }

    private void saveResult(Map result) {
        result.put(new HashMap<>(string_of_log), new HashMap<>(message));
    }
}

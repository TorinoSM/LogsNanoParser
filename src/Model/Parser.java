package Model;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Parser.Main.lines_storage;

class Parser {

    private static final String rx_string_of_log = "^\\[\\d{1,2}/\\d{1,2}/\\d{1,2}.+?$"; //  паттерн строки лога, например [1/24/18 14:36:11:322 MSK] 000000ae SystemOut     O 2018-01-24 14:36:11 DEBUG [WebSphere_EJB_NP_Timer_Service.Alarm Pool : 0]: [Consumer id=937510382] got 0 records
    private static final String rx_open_bracket = "^\\{$"; //  паттерн открывающей фигурной скобки
    private static final String rx_close_bracket = "^\\}$"; //  паттерн закрывающей фигурной скобки
    private static final String rx_sbermess_receive_message = "^.*?receive a message in sbermess handler, message is.*?$";
    private static final String rx_sbermess_send_message = "^.*?a message was sent to the messenger's server, message is.*?$";
    private static final String rx_id = "^.*?\"id\"\\s*?:\\s*?\"?(-?\\d+?)\"?,.*?$"; //  паттерн для "id" : "-142972560", или "id" : -142972560,
    private static Pattern pattern_string_of_log = Pattern.compile(rx_string_of_log);
    private static Pattern pattern_open_bracket = Pattern.compile(rx_open_bracket);
    private static Pattern pattern_close_bracket = Pattern.compile(rx_close_bracket);
    private static Pattern pattern_sbermess_receive_message = Pattern.compile(rx_sbermess_receive_message);
    private static Pattern pattern_sbermess_send_message = Pattern.compile(rx_sbermess_send_message);
    private static Pattern pattern_id = Pattern.compile(rx_id);

    private static boolean inside_message = false; // если находимся внутри сообщения (между фигурными скобками)
    private static boolean have_log_entry = false; // если ранее записали строку лога (для случая если лог обрезанный)

    static void parseLine(int string_number, String matching_line) {

        Matcher m_string_of_log = pattern_string_of_log.matcher(matching_line);

        if (m_string_of_log.find()) { // если строка - это запись лога
            inside_message = false; // если строка - это запись лога, то мы точно находимся вне сообщения (для случая когда сообщение обрезано)
            if (applyFilterToLine(matching_line)) { // если строка соответствует нужному нам паттерну, то записываем ее
                have_log_entry = true;
                lines_storage.addStringOfLog(string_number, matching_line);
            } // иначе выходим

            return;
        }

        Matcher m_open_bracket = pattern_open_bracket.matcher(matching_line);

        if (m_open_bracket.find()) { // если строка - это "{"
            if (!have_log_entry)
                return; // если до этого строка лога не записана, значит лог разорван и просто выходим
            inside_message = true;
            lines_storage.addMessageStringFromLog(matching_line);
            return;
        }

        Matcher m_close_bracket = pattern_close_bracket.matcher(matching_line);

        if (m_close_bracket.find()) { // если строка - это "}"
            if (!inside_message)
                return; // если до этого были не в сообщении (например если лог обрезан) то выходим
            inside_message = false;
            have_log_entry = false;
            lines_storage.addMessageStringFromLog(matching_line);
            lines_storage.saveResult();
            lines_storage.getMessage().clear();
            lines_storage.setString_number(0);
            return;
        }

        if (inside_message && have_log_entry) { // во всех остальных случаях, если находимся между скобок и до этого записана строка лога
            lines_storage.addMessageStringFromLog(matching_line); // записываем строку
        }
    }


    private static boolean applyFilterToLine(String matching_line) { // добавляй в if проверку паттернов для всех строк которые нужно сохранять + сами методы для проверки

        if (matchSbermessSendMessage(matching_line) ||
                matchSbermessReceiveMessage(matching_line))
            return true; // соответствует одному из фильтров
        return false;  // не соответствует ни одному из фильтров
    }

    static boolean matchSbermessSendMessage(String matching_line) {
        Matcher matcher = pattern_sbermess_send_message.matcher(matching_line);
        return matcher.find();
    }

    static boolean matchSbermessReceiveMessage(String matching_line) {
        Matcher matcher = pattern_sbermess_receive_message.matcher(matching_line);
        return matcher.find();
    }

    static boolean matchId(String matching_line) {
        Matcher matcher = pattern_id.matcher(matching_line);
        return matcher.find();
    }

    static String extractId(String matching_line) {
        Matcher matcher = pattern_id.matcher(matching_line);
        matcher.find();
        return matcher.group(1);
    }
}

package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Model.Parser.extractId;
import static Model.Parser.matchId;
import static Parser.Main.parsed_log_storage;

public class LinesStorage {


    private long id = -1; // id сообщения
    private String message_location = ""; // откуда и куда сообщение
    private int string_number = 0; // номер строки сообщения

    private Map<Integer, String> string_of_log = new HashMap<>(); // строка отнсящаяся к логу: номер строки, содержимое строки
    private Map<Integer, String> message = new HashMap<>(); // строка относящаяся к сообщению: номер строки, содержимое строки

    void setString_number(int string_number) {
        this.string_number = string_number;
    }

    Map<Integer, String> getMessage() {
        return message;
    }

    void addStringOfLog(int number_of_string, String string_to_add) {
        string_of_log.clear(); // строка лога всегда одна (потом идет сообщение)
        string_of_log.put(number_of_string, string_to_add);
    }

    void addMessageStringFromLog(String message_string_to_add) {
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


    void saveResult() {
        parsed_log_storage.put(new HashMap<>(string_of_log), new HashMap<>(message));
    }


    ArrayList<String> getBatchMessageIdArray(HashMap<Integer, String> message_entry) {
        ArrayList<String> batchMessageIdArray = new ArrayList<>();
        for (HashMap.Entry<Integer, String> message : message_entry.entrySet()) {
            String message_string = message.getValue();
            if (matchId(message_string)) {
                batchMessageIdArray.add(extractId(message_string));
            }
        }
        return batchMessageIdArray;
    }

    long getMessageEpochTime(HashMap<Integer, String> log_entry) {
        String log_time = TimeConverter.parseMessageTime(log_entry);
        return TimeConverter.convertTimeToEpoch(log_time);
    }
}

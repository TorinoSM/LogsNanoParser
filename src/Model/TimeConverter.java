package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TimeConverter {


    private static final String rx_message_time = "^\\[(\\d{1,2}/\\d{1,2}/\\d{2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}:\\d{3}\\s.*?)\\].*?$"; // паттерн времени в фигурных скобках в строке лога [1/24/18 14:54:03:896 MSK] или [11/24/18 14:54:03:896 MSK]


    private static Pattern pattern_message_time = Pattern.compile(rx_message_time);

    // 1517265541195
    // [1/24/18 14:54:03:896 MSK]
    static Long convertTimeToEpoch(String timestamp) {
        if (timestamp == null) return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss:SSS z");
            Date dt;
            dt = sdf.parse(timestamp);
            long epoch = dt.getTime();
            Long three_hours = 3L * 60L * 60L * 1000L; // добавляем три часа, чтобы получить московское время
            return epoch + three_hours;
        } catch (ParseException e) {
            return null;
        }
    }

    static String parseMessageTime(HashMap<Integer, String> log_entry) {
        String log_string = "";
        for (Map.Entry<Integer, String> entry : log_entry.entrySet()) {
            log_string = entry.getValue(); // лог это только одна строка
        }
        Matcher m = pattern_message_time.matcher(log_string);
        m.matches();
        return m.group(1);
    }

}




package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {

    // 1517265541195
    // [1/24/18 14:54:03:896 MSK]
    public static Long convertTimeToEpoch(String timestamp) {
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
}




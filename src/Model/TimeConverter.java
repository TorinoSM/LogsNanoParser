package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {

    // 1517265541195

    public static Integer convertTimeToEpoch(String timestamp) {
        if (timestamp == null) return null;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date dt;
            dt = sdf.parse(timestamp);

            long epoch = dt.getTime();
            return (int) (epoch / 1000);
        } catch (ParseException e) {
            return null;
        }
    }
}




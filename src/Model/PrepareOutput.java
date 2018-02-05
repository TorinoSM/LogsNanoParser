package Model;

import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.stat.descriptive.rank.Min;

import java.util.*;

import static Parser.Main.*;

public class PrepareOutput {


    /**
     * Метод заполняет объектами Point два ArrayList'a: in_points_storage - входящие id-шники, in_points_storage - исходящие
     * ArrayList'ы статически импортированы (как и другие статические константы)
     */
    public static void fillPointsStorage() {

        for (Map.Entry<HashMap, HashMap> entry : parsed_log_storage.entrySet()) {
            String in_out = null;
            Integer string_number = -1;
            HashMap<Integer, String> log_entry = entry.getKey(); // мапа со строкой лога
            HashMap<Integer, String> message_entry = entry.getValue(); // мапа со строками сообщения

            for (Map.Entry<Integer, String> log_string : log_entry.entrySet()) {
                if (Parser.matchSbermessReceiveMessage(log_string.getValue())) {
                    in_out = "IN";
                } else if (Parser.matchSbermessSendMessage(log_string.getValue())) {
                    in_out = "OUT";
                }
                string_number = log_string.getKey();
            }

            ArrayList<String> batch_message_id_array = lines_storage.getBatchMessageIdArray(message_entry); // получаем id сообщения/сообщений в виде ArrayList

            long messageEpochTime = lines_storage.getMessageEpochTime(log_entry);

            if ("IN".equals(in_out)) {
                for (int i = 0; i < batch_message_id_array.size(); i++) {
                    in_points_storage.add(new Point(batch_message_id_array.get(i), messageEpochTime, "STUB", "IN", string_number, -1L));
                }
            } else if ("OUT".equals(in_out)) {
                out_points_storage.add(new Point(batch_message_id_array.get(0), messageEpochTime, "STUB", "OUT", string_number, -1L));
            }
        }
    }


    public static void calculateDifference() {

        List<Point> left;
        List<Point> right;

        if (in_points_storage.size() > out_points_storage.size()) { // кто размером больше, то для join'a левой таблицей
            left = in_points_storage;
            right = new ArrayList<>(out_points_storage);
        } else {
            left = out_points_storage;
            right = new ArrayList<>(in_points_storage);
        }


        for (int i = 0; i < left.size(); i++) { // бежим по большей коллекции
            boolean found_match = false;
            Point point_from_left = left.get(i);
            for (int j = 0; j < right.size(); j++) { // бежим по меньшей коллекции
                Point point_from_right = right.get(j);
                if (point_from_left.getId().equals(point_from_right.getId())) { // если id точек совпадают
                    difference_points_storage.add(new Point( // тогда записываем в difference_points_storage новую точку, у которой вычислен difference_timestamp
                            point_from_left.getId(),
                            point_from_left.getTimestamp(),
                            point_from_left.getApp_server(),
                            point_from_left.getDirection(),
                            point_from_left.getString_number(),
                            Math.abs(point_from_left.getTimestamp() - point_from_right.getTimestamp())));
                    right.remove(j); // удаляем совпавший элемент правой коллекции
                    found_match = true; // флаг "совпадение найдено"
                    break;
                }
            }
            if (!found_match) { // если совпадения не найдено, то добавляем точку из левой коллекции в alone_points_storage
                alone_points_storage.add(point_from_left);
            }
        }
        alone_points_storage.addAll(right); // добавить остатки от правой коллекции в alone_points_storage(раз точки не удалены значит им не нашлась пара в левой коллекции)
    }

    public static void calculateDifferenceStatistics(Long dpt, ArrayList<Long> difference_points_timestamp_array) {

        double[] ids = new double[difference_points_timestamp_array.size()];

        for (int i = 0; i < difference_points_timestamp_array.size(); i++) {
            ids[i] = Double.valueOf(difference_points_timestamp_array.get(i));
        }
        Median median = new Median();
        double median_value = median.evaluate(ids);
        Max max = new Max();
        double max_value = max.evaluate(ids);
        Min min = new Min();
        double min_value = min.evaluate(ids);
        influx_points_storage.add(new InfluxPoint(dpt, min_value, max_value, median_value));
    }

}

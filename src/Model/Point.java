package Model;

public class Point {

    private String id;
    private Long timestamp;
    private Long difference_timestamp;

    private String app_server;
    private String direction;
    private Integer string_number;

    public Long getDifference_timestamp() {
        return difference_timestamp;
    }

    public void setDifference_timestamp(Long difference_timestamp) {
        this.difference_timestamp = difference_timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (!id.equals(point.id)) return false;
        if (!timestamp.equals(point.timestamp)) return false;
        if (!difference_timestamp.equals(point.difference_timestamp)) return false;
        if (!app_server.equals(point.app_server)) return false;
        if (!direction.equals(point.direction)) return false;
        return string_number.equals(point.string_number);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + difference_timestamp.hashCode();
        result = 31 * result + app_server.hashCode();
        result = 31 * result + direction.hashCode();
        result = 31 * result + string_number.hashCode();
        return result;
    }

    public Point(String id, Long timestamp, String app_server, String direction, Integer string_number, Long difference_timestamp) {

        this.id = id;
        this.timestamp = timestamp;
        this.difference_timestamp = difference_timestamp;
        this.app_server = app_server;
        this.direction = direction;
        this.string_number = string_number;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getApp_server() {
        return app_server;
    }

    public void setApp_server(String app_server) {
        this.app_server = app_server;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getString_number() {
        return string_number;
    }

    public void setString_number(Integer string_number) {
        this.string_number = string_number;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", app_server='" + app_server + '\'' +
                ", direction='" + direction + '\'' +
                ", string_number=" + string_number +
                ", difference_timestamp=" + difference_timestamp +
                '}';
    }
}

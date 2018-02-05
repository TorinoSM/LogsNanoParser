package Model;

public class InfluxPoint {

    private Long timestamp;
    private double min;
    private double max;
    private double median;

    public InfluxPoint(Long timestamp, double min, double max, double median) {
        this.timestamp = timestamp;
        this.min = min;
        this.max = max;
        this.median = median;
    }

    @Override
    public String toString() {
        return "InfluxPoint{" +
                "timestamp=" + timestamp +
                ", min=" + min +
                ", max=" + max +
                ", median=" + median +
                '}';
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }
}

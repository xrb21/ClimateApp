package pandawa.xrb21.climateapp.models;

/**
 * Created by aisyaku on 9/9/2015.
 */
public class Weather {
    private int tanggal;
    private double day, min, max;

    public Weather() {
    }

    public Weather(double day, double max, double min, int tanggal) {
        this.day = day;
        this.max = max;
        this.min = min;
        this.tanggal = tanggal;
    }

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public int getTanggal() {
        return tanggal;
    }

    public void setTanggal(int tanggal) {
        this.tanggal = tanggal;
    }
}

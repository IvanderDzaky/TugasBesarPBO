package hotel.helper;

public class Profit {

    private int tahun;
    private int bulan;
    private double total;

    public Profit(int tahun, int bulan, double total) {
        this.tahun = tahun;
        this.bulan = bulan;
        this.total = total;
    }

    public int getTahun() {
        return tahun;
    }

    public int getBulan() {
        return bulan;
    }

    public double getTotal() {
        return total;
    }
}

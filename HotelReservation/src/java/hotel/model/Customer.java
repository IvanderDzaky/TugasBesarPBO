package hotel.model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private List<Reservasi> reservasiList = new ArrayList<>();
    
    @Override
    public void info() {
        System.out.println("Customer: " + nama);
    }

    public void buatReservasi(Reservasi r) {
        reservasiList.add(r);
    }

    public void ubahReservasi(int index, Reservasi rBaru) {
        if (index >= 0 && index < reservasiList.size()) {
            reservasiList.set(index, rBaru);
        }
    }

    public void batalkanReservasi(int index) {
        if (index >= 0 && index < reservasiList.size()) {
            reservasiList.remove(index);
        }
    }

    public void lihatReservasi() {
        for (Reservasi r : reservasiList) {
            r.tampilkanInfo();
        }
    }

}

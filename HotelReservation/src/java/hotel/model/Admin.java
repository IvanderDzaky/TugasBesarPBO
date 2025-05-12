package hotel.model;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    private List<Customer> daftarCustomer = new ArrayList<>();
    private List<Kamar> daftarKamar = new ArrayList<>();
    private List<Reservasi> daftarReservasi = new ArrayList<>();

    // Method admin seperti tambahCustomer, lihatCustomer, dll.
    @Override
    public void info() {
        System.out.println("Admin: " + nama);
    }
    // Tambahkan di dalam class Admin

    public void tambahCustomer(Customer customer) {
        daftarCustomer.add(customer);
    }

    public Customer lihatCustomer(String email) {
        for (Customer c : daftarCustomer) {
            if (c.email.equals(email)) {
                return c;
            }
        }
        return null;
    }

    public void updateCustomer(String email, Customer dataBaru) {
        for (int i = 0; i < daftarCustomer.size(); i++) {
            if (daftarCustomer.get(i).email.equals(email)) {
                daftarCustomer.set(i, dataBaru);
                break;
            }
        }
    }

    public void hapusCustomer(String email) {
        daftarCustomer.removeIf(c -> c.email.equals(email));
    }

    public void tambahKamar(Kamar kamar) {
        daftarKamar.add(kamar);
    }

    public Kamar lihatKamar(String nomor) {
        for (Kamar k : daftarKamar) {
            if (k.getNomorKamar().equals(nomor)) {
                return k;
            }
        }
        return null;
    }

    public void updateKamar(String nomor, Kamar dataBaru) {
        for (int i = 0; i < daftarKamar.size(); i++) {
            if (daftarKamar.get(i).getNomorKamar().equals(nomor)) {
                daftarKamar.set(i, dataBaru);
                break;
            }
        }
    }

    public void hapusKamar(String nomor) {
        daftarKamar.removeIf(k -> k.getNomorKamar().equals(nomor));
    }

    public List<Reservasi> lihatSemuaReservasi() {
        return daftarReservasi;
    }

    public void hapusReservasi(String id) {
        daftarReservasi.removeIf(r -> String.valueOf(r.getIdReservasi()).equals(id));
    }

}

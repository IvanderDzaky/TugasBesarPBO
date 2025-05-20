package hotel.helper;

import java.util.List;
import hotel.model.*;
public class KamarTersedia {

    private int jumlahTersedia;
    private List<Kamar> kamarTersedia;

    public KamarTersedia() {
        // constructor kosong
    }

    public KamarTersedia(int jumlahTersedia, List<Kamar> kamarTersedia) {
        this.jumlahTersedia = jumlahTersedia;
        this.kamarTersedia = kamarTersedia;
    }


    public int getJumlahTersedia() {
        return jumlahTersedia;
    }

    public void setKamarTersedia(List<Kamar> kamarTersedia) {
        this.kamarTersedia = kamarTersedia;
    }

    public List<Kamar> getKamarTersedia() {
        return kamarTersedia;
    }

    public void setJumlahTersedia(int jumlahTersedia) {
        this.jumlahTersedia = jumlahTersedia;
    }

}

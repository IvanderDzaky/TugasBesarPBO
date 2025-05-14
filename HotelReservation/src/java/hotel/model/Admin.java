package hotel.model;

public class Admin extends User {

    public Admin(String nama, String email, String password) {
        super(nama, email, password);
    }

    @Override
    public void info() {
        System.out.println("Admin: " + nama + " (" + email + ")");
    }
}

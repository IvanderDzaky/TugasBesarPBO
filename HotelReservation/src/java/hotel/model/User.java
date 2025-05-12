package hotel.model;

public abstract class User {
    private String nama;
    private String email;
    private String password;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void signup() {}
    public void login() {}
    public void logout() {}

    public abstract void info();
}

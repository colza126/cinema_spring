package cinema.cinema;

public class User {
    String mail;
    String password;
    boolean permessiAdmin;
    boolean confirmed;
    String tokenConferma;

    public User(String mail, String password, boolean permessiAdmin, boolean confirmed, String tokenConferma) {
        this.mail = mail;
        this.password = password;
        this.permessiAdmin = permessiAdmin;
        this.confirmed = confirmed;
        this.tokenConferma = tokenConferma;
    }
}

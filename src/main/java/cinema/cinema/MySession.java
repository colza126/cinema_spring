package cinema.cinema;

public class MySession {
    
    private int idUtente;
    private boolean permessiAdmin;

    public MySession(int idUtente, boolean permessiAdmin) {
        this.idUtente = idUtente;
        this.permessiAdmin = permessiAdmin;
    }

    public MySession() {
        this.idUtente = 0;
        this.permessiAdmin = false;
    }

    
}

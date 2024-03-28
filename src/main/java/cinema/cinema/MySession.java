package cinema.cinema;

public class MySession {
    
    public int idUtente;
    public boolean permessiAdmin;

    public MySession(int idUtente, boolean permessiAdmin) {
        this.idUtente = idUtente;
        this.permessiAdmin = permessiAdmin;
    }

    public MySession() {
        this.idUtente = 0;
        this.permessiAdmin = false;
    }

    
}

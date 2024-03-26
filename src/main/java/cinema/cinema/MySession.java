package cinema.cinema;

public class MySession {
    
    private int idUtente;
    private boolean permessiAdmin;

    public MySession(int idUtente) {
        this.idUtente = idUtente;
    }

    public MySession() {
        this.idUtente = 0;
    }

    
}

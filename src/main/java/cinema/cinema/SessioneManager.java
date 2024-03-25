package cinema.cinema;

public class SessioneManager {

    /* 
        utente -> login
        java -> assegna cookie sessione
        utente logga off-> (senza distruggere la sessione) -> cookie sessione rimane
        utente logga on -> rimangono i dati della sessione

        utente -> cancella cookie
    
    
    */ 



    //inizializzo istanza singola 
    private static SessioneManager instance;
    public String cookieSessione;

    //costruttore privato
    private SessioneManager() {
    }

    public static SessioneManager getIstance() {
        if (instance == null) {
            instance = new SessioneManager();
        }
        return instance;
    }
    

    public void session_start() {
        System.out.println("Sessione iniziata");
    }

    public void session_destroy() {
        System.out.println("Sessione terminata");
    }

    public void sessionExist() {
        System.out.println("Sessione terminata");
    }
}   

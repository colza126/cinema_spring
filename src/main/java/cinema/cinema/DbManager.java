package cinema.cinema;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DbManager {
    final String JDBC_URL = "jdbc:mysql://localhost:3306/cinema";
    final String USERNAME = "root";
    final String PASSWORD = "";

    public String convertToMD5(String input) {
        try {
            // Crea un oggetto MessageDigest con l'algoritmo MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Aggiungi i byte della stringa di input al MessageDigest
            md.update(input.getBytes());

            // Calcola il digest MD5
            byte[] digest = md.digest();

            // Converte il digest in una rappresentazione esadecimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b & 0xff));
            }
            return hexString.toString(); // Restituisce l'hash MD5 come stringa esadecimale
        } catch (NoSuchAlgorithmException e) {
            // Gestisci il caso in cui l'algoritmo MD5 non è disponibile
            e.printStackTrace();
            return null;
        }
    }

    public MySession loginUser(String mail, String pw) {
        String select = "SELECT * FROM utente WHERE mail = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(select)) {
            // Imposta i parametri della query
            stmt.setString(1, mail);
            stmt.setString(2, convertToMD5(pw));

            // Esegui la query
            try (ResultSet rs = stmt.executeQuery()) {
                // Controlla se ci sono risultati
                if (rs.next()) {
                    // Se ci sono risultati, restituisci true
                    if(rs.getInt("account_confermato") == 1){
                        boolean permessi_admin;
                        if(rs.getInt("permessi_admin") == 1){
                            permessi_admin = true;
                        }else{
                            permessi_admin = false;
                        }

                        return new MySession(rs.getInt("ID"), permessi_admin);
                    }else{
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // In caso di eccezione, restituisci false
            return null;
        }
        // Se non ci sono risultati o in caso di eccezione, restituisci false
        return null;
    }


    //DA CONTROLLARE
    public List<Film> getCinema(){
        List<Film> risultati = new ArrayList<>();

        String query = "SELECT * FROM film";


        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            // Esegui la query
            try (ResultSet rs = stmt.executeQuery()) {
                // Controlla se ci sono risultati
                while (rs.next()) {
                    // Se ci sono risultati, restituisci true
                    risultati.add(new Film(rs.getString("nome"), rs.getInt("anno_produzione"), rs.getString("genere"), rs.getString("bio"), rs.getString("percorso_locandina"), rs.getInt("ID")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // In caso di eccezione, restituisci false
            return null;
        }
        return risultati;
    }

    public List<String> getGeneri(){
        List<String> risultati = new ArrayList<>();

        String query = "SELECT * FROM elenco_generi";


        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            // Esegui la query
            try (ResultSet rs = stmt.executeQuery()) {
                // Controlla se ci sono risultati
                while (rs.next()) {
                    // Se ci sono risultati, restituisci true
                    risultati.add(rs.getString("genere"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // In caso di eccezione, restituisci false
            return null;
        }
        return risultati;
    }

    public boolean controllaPermessi(String mail){
        String select = "SELECT * FROM utente WHERE mail = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(select)) {
            // Imposta i parametri della query
            stmt.setString(1, mail);

            // Esegui la query
            try (ResultSet rs = stmt.executeQuery()) {
                // Controlla se ci sono risultati
                if (rs.next()) {
                    // Se ci sono risultati, restituisci true
                    if(rs.getInt("permessi_admin") == 1){
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // In caso di eccezione, restituisci false
            return false;
        }
        // Se non ci sono risultati o in caso di eccezione, restituisci false
        return false;
    }

    public Film getFilm(int id){
        Film risultato = null;

        String query = "SELECT * FROM film WHERE ID =? ";


        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            // Esegui la query
            try (ResultSet rs = stmt.executeQuery()) {
                // Controlla se ci sono risultati
                if (rs.next()) {
                    // Se ci sono risultati, restituisci true
                    risultato = new Film(rs.getString("nome"), rs.getInt("anno_produzione"), rs.getString("genere"), rs.getString("bio"), rs.getString("percorso_locandina"), rs.getInt("ID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // In caso di eccezione, restituisci false
            return null;
        }
        return risultato;
    }

    public boolean eliminaFilm(int id_film){
        String query = "DELETE FROM film WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_film);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean inserisciFilm(String nome, int anno_produzione, String genere, String bio, String percorso_locandina){
        String query = "INSERT INTO film ( nome, anno_produzione, genere, bio, percorso_locandina) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setInt(2, anno_produzione);
            stmt.setString(3, genere);
            stmt.setString(4, bio);
            stmt.setString(5, percorso_locandina);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateToken(String mail)
    {
        if (!userExists(mail)) {
            return false;
        }

        String query = "UPDATE utente SET token_conferma = ? WHERE mail = ?";
        String token = generateToken();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, token);
            stmt.setString(2, mail);

            stmt.executeUpdate();

            if (stmt.getUpdateCount() == 0) {
                return false;
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerUser(String mail, String pass)
    {
        if (userExists(mail)) {
            return false;
        }

        String query = "INSERT INTO utente (mail, password, permessi_admin, account_confermato, token_conferma) VALUES (?, ?, ?, ?, ?)";
        String token = generateToken();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, mail);
            stmt.setString(2, convertToMD5(pass));
            stmt.setInt(3, 0);
            stmt.setInt(4, 0);
            stmt.setString(5, token);

            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean userExists(String mail)
    {
        String query = "SELECT * FROM utente WHERE mail = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, mail);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getUserToken(String mail)
    {
        String query = "SELECT token_conferma FROM utente WHERE mail = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, mail);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("token_conferma");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean confirmToken(String token)
    {
        String query = "SELECT * FROM utente WHERE token_conferma = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, token);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String updateQuery = "UPDATE utente SET account_confermato = 1 WHERE token_conferma = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setString(1, token);
                    updateStmt.executeUpdate();
                }
                else {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateToken() {
        return convertToMD5("" + System.currentTimeMillis() + Math.random() * 1000);
    }
}

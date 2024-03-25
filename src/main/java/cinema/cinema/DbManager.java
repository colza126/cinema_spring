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

    public boolean loginUser(String mail, String pw) {
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
                    return true;
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


}

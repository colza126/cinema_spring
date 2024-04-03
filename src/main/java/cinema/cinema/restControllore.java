package cinema.cinema;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class restControllore{
    DbManager db = new DbManager();
    String mailUtente;
    SessionManager sessionManager = new SessionManager();
    MySession s;
    MailSender mailSender = new MailSender();


    @GetMapping("/login")
    public boolean logIn(@RequestParam(value = "mail", required = true) String mail,
    @RequestParam(value = "pass", required = true) String pass,HttpServletResponse response) throws NoSuchAlgorithmException{
        if(db.loginUser(mail, pass) != null) {
            s = sessionManager.session_start(response,db.loginUser(mail, pass));
            mailUtente = mail;
            return true;
        }

        return false;
    }
    @GetMapping("/sess_exist")
    public boolean logIn(HttpServletResponse response) throws NoSuchAlgorithmException{
        if(s != null) {
            return true;
        }
        return false;
    }
    @GetMapping("/checkPrivilegi")
    public boolean checkPrivilegi(){
        return s.permessiAdmin;
    }

    @GetMapping("/logout")
    public boolean logout(HttpServletResponse response) throws NoSuchAlgorithmException{
        
        sessionManager.session_destroy(response);
        s = null;
        return true;
    }
    
    @GetMapping("/getFilms")
    public List<Film> getFilms(){
        return db.getCinema();
    }
    @GetMapping("/getFilm")
    public Film getFilm(@RequestParam(value = "id_film", required = true) int idFilm){
        return db.getFilm(idFilm);
    }
    @GetMapping("/getPermessi")
    public boolean getPermessi(){
        return db.controllaPermessi(mailUtente);
    }
    @GetMapping("/cancellaFilm")
    public boolean delFIlm(@RequestParam(value = "id_film", required = true) int idFilm){
        return db.eliminaFilm(idFilm);
    }

    @GetMapping("/controllaSessione")
    public boolean controllaSessione(){
        if(sessionManager.sessionExist()) {
            return true;
        }
        return false;
    }
    
    @GetMapping("/controllaGeneri")
    public Map<String, List<Map<String, String>>> getGeneri() {
        List<String> generi = db.getGeneri();
        generi.add(0, "Tutti");

        List<Map<String, String>> generiList = new ArrayList<>();
        for (String genere : generi) {
            Map<String, String> genereMap = new HashMap<>();
            genereMap.put("genere", genere);
            generiList.add(genereMap);
        }

        Map<String, List<Map<String, String>>> result = new HashMap<>();
        result.put("generi", generiList);

        return result;
        
    }
    
    @PostMapping("/confermaToken")
    public String confermaToken(@RequestParam(value = "token", required = true) String token)
    {
        boolean result = db.confirmToken(token);
        
        return (result) ? "Account attivato con successo" : "Token non valido";
    }

    @GetMapping("/inserisci")
    public boolean inserisisci(@RequestParam(value = "titolo", required = true) String titolo,
    @RequestParam(value = "genere", required = true) String genere,
    @RequestParam(value = "anno", required = true) int anno,
    @RequestParam(value = "bio", required = true) String bio,
    @RequestParam(value = "foto", required = true) String foto){
        return db.inserisciFilm(titolo, anno, genere, bio, foto);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleException() {
        return "Custom error message";
    }

    @PostMapping("/aggiornaToken")
    public boolean aggiornaToken(@RequestParam(value = "mail", required = true) String mail)
    {
        boolean result = db.updateToken(mail);
        
        return result;
    }
    
    @PostMapping("/registration")
    public HashMap<String, String> registration(@RequestParam(value = "mail", required = true) String mail,
    @RequestParam(value = "password", required = true) String pass)
    {
        boolean registrationResult = db.registerUser(mail, pass);
        HashMap<String, String> data = new HashMap<>();

        if (registrationResult) {
            data.put("status", "success");
            data.put("token", db.getUserToken(mail));
        } else {
            data.put("status", "error");
            data.put("message", "L'utente potrebbe essere già registrato. Ritenta con un'altra email.");
        }

        return data;
    }

    @PostMapping("/mailConferma")
    public boolean mailConferma(@RequestParam(value = "mail", required = true) String mail,
    @RequestParam(value = "contenuto", required = true) String contenuto)
    {
        mailSender.sendMail(mail, "Conferma registrazione", contenuto);
        
        return false;
    }

    @PostMapping("/checkMail")
    public boolean checkMail(@RequestParam(value = "mail", required = true) String mail)
    {
        return db.userExists(mail);
    }
    @PostMapping("/getToken")
    public String getToken(@RequestParam(value = "mail", required = true) String mail)
    {
        return db.getToken(mail);
    }
    @PostMapping("/recoveryPW")
    public boolean reco(@RequestParam(value = "password", required = true) String password,@RequestParam(value = "token", required = true) String token) throws SQLException{
        return db.modificaPassword(password, token);
    }
}

package cinema.cinema;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class restControllore{
    DbManager db = new DbManager();
    String mailUtente;

    @GetMapping("/login")
    public boolean logIn(@RequestParam(value = "mail", required = true) String mail,
    @RequestParam(value = "pass", required = true) String pass){
        mailUtente = mail;
        return db.loginUser(mail, pass);
    }
    @GetMapping("/getFilms")
    public List<Film> getFilms(){
        return db.getCinema();
    }
    @GetMapping("/getFilm")
    public String getFilm(@RequestParam(value = "id_film", required = true) int idFilm){
        return db.getFilm(idFilm).toString();
    }
    @GetMapping("/getPermessi")
    public boolean getPermessi(){
        return db.controllaPermessi(mailUtente);
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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleException() {
        return "Custom error message";
    }
}

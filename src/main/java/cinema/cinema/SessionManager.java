package cinema.cinema;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SessionManager {

    private static final String myCookieName = "MySessionCookie";
    HashMap<String, MySession> sessioni;

    public SessionManager()
    {
        this.sessioni = new HashMap<>();
    }

    public MySession session_start(HttpServletResponse response) throws NoSuchAlgorithmException
    {
        // controllare se nei cookie è presente il cookie di sessione ( myCookieName )
        Cookie c = getCookie(myCookieName);
        // se non esiste -> sessione MAI avviata
        if (c == null || c.getValue() == null || c.getValue().equals(""))
        {

            String token = generateToken(64);

            sessioni.put(token, new MySession());

            return generateSession(token, response);
        }
        // se esiste -> l'utente ha già (forse..) una sessione
        else
        {
            // la recupero dalla mia hashmap
            if (sessioni.containsKey(c.getValue()))
            {
                return sessioni.get(c.getValue());
            }
            // se non la trovo
            else
            {
                // avvio una sesisone
                // genero un token

                String token = c.getValue();

                // avvio una sessione
                MySession s = generateSession(token, null);

                return s;
            }
        }
    }

    public MySession session_start(HttpServletResponse response,MySession s) throws NoSuchAlgorithmException
    {
        // controllare se nei cookie è presente il cookie di sessione ( myCookieName )
        Cookie c = getCookie(myCookieName);
        // se non esiste -> sessione MAI avviata
        if (c == null || c.getValue() == null || c.getValue().equals(""))
        {

            String token = generateToken(64);

            sessioni.put(token, new MySession());

            return generateSession(token, response);
        }
        // se esiste -> l'utente ha già (forse..) una sessione
        else
        {
            // la recupero dalla mia hashmap
            if (sessioni.containsKey(c.getValue()))
            {
                return sessioni.get(c.getValue());
            }
            // se non la trovo
            else
            {
                // avvio una sesisone
                // genero un token

                String token = c.getValue();

                // avvio una sessione

                return s;
            }
        }
    }


    private MySession generateSession(String token, HttpServletResponse response)
    {
        // avvio una sessione
        MySession s = new MySession();
        // inserisco la sessione nell'hashmap, con il token relativo alla sessione
        Cookie cookie = new Cookie(myCookieName, token);
        response.addCookie(cookie);

        sessioni.put(token, s);
        return s;
    }

    /**
     * cancellare la sessione corrente
     */
    public void session_destroy(HttpServletResponse response) {
        // controllo se esiste la sessione con quel cookie
        Cookie c = getCookie(myCookieName);
        if (sessioni.containsKey(c.getValue()))
        {
            sessioni.remove(c.getValue());
            Cookie cookie = new Cookie(myCookieName, null);
            response.addCookie(cookie);
        }
        // si -> cancello la sessione e dico al client di cancellare il cookie
        // no -> non faccio nulla
    }

    public boolean sessionExist()
    {
        Cookie c = getCookie(myCookieName);
        if (!sessioni.containsKey(c.getValue()) || c == null || c.getValue() == null || c.getValue().equals(""))
        {
            sessioni.remove(c.getValue());
            return false;
        }
        else
        {
            return true;
        }
    }

    //senza passare la request ( la prende da solo dal contesto ) 
    private Cookie getCookie(String name) {

        //richiede gli attributi del browser
        HttpServletRequest r = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        //prendo i cookie
        Cookie[] cookies = r.getCookies();
        //se ci sono cookie
        if (cookies != null) {
            //scorro i cookie
            for (int i = 0; i < cookies.length; i++) {
                //se il nome del cookie è uguale a quello che cerco
                if( cookies[i].getName().equals(name))
                    //ritorno il cookie
                    return cookies[i];
            }
        }
        return null;
    }

    private String generateToken(int length) throws NoSuchAlgorithmException {
        int numberOfMd5 = (length / 32)+1;
        String fullToken= "";

        for (int i = 0; i < numberOfMd5; i++) {

            byte[] randomBytes = new byte[64];

            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(randomBytes);

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] theMD5digest = messageDigest.digest(randomBytes);
            
            String myHash = bytesToHash(theMD5digest);
            fullToken += myHash;
        }

        return fullToken.substring(0, length);
    }

    private String bytesToHash(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            result.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

}

package cinema.cinema;

public class Film {
    private String nome;
    private int anno_prod;
    private String genere;
    private String bio;
    private String percorso_loc;
    private int ID;

    public Film(String nome, int anno_prod, String genere, String bio, String percorso_loc, int ID) {
        this.nome = nome;
        this.anno_prod = anno_prod;
        this.genere = genere;
        this.bio = bio;
        this.percorso_loc = percorso_loc;
        this.ID = ID;
    }

    public Film() {
        // Default constructor
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAnno_prod() {
        return anno_prod;
    }

    public void setAnno_prod(int anno_prod) {
        this.anno_prod = anno_prod;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPercorso_loc() {
        return percorso_loc;
    }

    public void setPercorso_loc(String percorso_loc) {
        this.percorso_loc = percorso_loc;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}

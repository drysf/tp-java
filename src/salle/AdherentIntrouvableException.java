package salle;

public class AdherentIntrouvableException extends Exception {

    public AdherentIntrouvableException(int id) {
        super("Adherent introuvable avec l'id : " + id);
    }

    public AdherentIntrouvableException(String message) {
        super(message);
    }
}

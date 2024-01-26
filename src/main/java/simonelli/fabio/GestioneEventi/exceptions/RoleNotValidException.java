package simonelli.fabio.GestioneEventi.exceptions;

public class RoleNotValidException extends RuntimeException{
    public RoleNotValidException(String role){
        super("Il ruolo " + role + " non esiste.");
    }
}

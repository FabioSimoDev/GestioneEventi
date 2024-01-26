package simonelli.fabio.GestioneEventi.exceptions;

public class RoleAlreadySetException extends RuntimeException{
    public RoleAlreadySetException(String role){
        super("L'utente ha già il ruolo " + role + " assegnato.");
    }
}

package simonelli.fabio.GestioneEventi.exceptions;

public class AlreadyBookedException extends RuntimeException{
    public AlreadyBookedException(String message){
        super(message);
    }
}

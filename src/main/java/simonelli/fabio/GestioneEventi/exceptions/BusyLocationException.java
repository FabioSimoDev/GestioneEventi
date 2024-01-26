package simonelli.fabio.GestioneEventi.exceptions;

public class BusyLocationException extends RuntimeException{
    public BusyLocationException(String location){
        super("C'è già un evento per quella data a " + location);
    }
}

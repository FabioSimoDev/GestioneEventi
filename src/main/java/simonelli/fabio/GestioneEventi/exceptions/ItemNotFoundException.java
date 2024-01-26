package simonelli.fabio.GestioneEventi.exceptions;

import java.util.UUID;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(UUID id){
        super("Non è stato trovato nessun elemento con l'ID: " + id);
    }

    public ItemNotFoundException(String message){
        super(message);
    }
}

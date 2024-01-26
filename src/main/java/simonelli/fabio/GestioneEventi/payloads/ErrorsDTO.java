package simonelli.fabio.GestioneEventi.payloads;

import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime timeStamp) {
}

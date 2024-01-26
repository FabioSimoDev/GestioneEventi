package simonelli.fabio.GestioneEventi.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewEventDTO (@NotEmpty(message = "il nome dell'evento è obbligatorio")
                           String name,
                           @NotEmpty(message = "la location dell'evento è obbligatoria")
                           String location,
                           @NotNull(message = "la data dell'evento è obbligatoria")
                           LocalDate date,
                           @NotNull(message = "il numero massimo di persone è obbligatorio")
                           int maxPeople){
}

package simonelli.fabio.GestioneEventi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import simonelli.fabio.GestioneEventi.entities.Event;
import simonelli.fabio.GestioneEventi.entities.User;
import simonelli.fabio.GestioneEventi.exceptions.BadRequestException;
import simonelli.fabio.GestioneEventi.payloads.NewEventDTO;
import simonelli.fabio.GestioneEventi.payloads.NewEventResponseDTO;
import simonelli.fabio.GestioneEventi.payloads.NewUserDTO;
import simonelli.fabio.GestioneEventi.payloads.NewUserResponseDTO;
import simonelli.fabio.GestioneEventi.repositories.EventsDAO;
import simonelli.fabio.GestioneEventi.services.EventsService;

import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventsController {
    @Autowired
    private EventsService eventsService;
    @GetMapping
    public Page<Event> getEvents(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String orderBy){
        return eventsService.getEvents(page, size, orderBy);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public NewEventResponseDTO createEvent(@AuthenticationPrincipal User currentUser,
                                           @RequestBody @Validated NewEventDTO newEventPayload,
                                           BindingResult validation){
        System.out.println(validation);
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload, controlla tutti i campi");
        } else {
            Event newEvent = eventsService.save(newEventPayload, currentUser);

            return new NewEventResponseDTO(newEvent.getId());
        }

    }

    @GetMapping("/{eventId}")
    public Event getById(@PathVariable UUID eventId){
        return eventsService.getById(eventId);
    }
}

package simonelli.fabio.GestioneEventi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import simonelli.fabio.GestioneEventi.entities.Event;
import simonelli.fabio.GestioneEventi.entities.User;
import simonelli.fabio.GestioneEventi.exceptions.BusyLocationException;
import simonelli.fabio.GestioneEventi.exceptions.ItemNotFoundException;
import simonelli.fabio.GestioneEventi.payloads.NewEventDTO;
import simonelli.fabio.GestioneEventi.payloads.NewEventResponseDTO;
import simonelli.fabio.GestioneEventi.repositories.EventsDAO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventsService {
    @Autowired
    private EventsDAO eventsDAO;

    public Page<Event> getEvents(int page, int size, String orderBy){
        if(size >= 50) size=50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return eventsDAO.findAll(pageable);
    }

    public Event save(NewEventDTO body, User manager){
        List<Event> found = eventsDAO.findByDate(body.date());
        if(!found.isEmpty()){
            for(Event event : found){
                if(event.getLocation().equalsIgnoreCase(body.location())){
                    throw new BusyLocationException(body.location());
                }
            }
        }
        Event newEvent = new Event();
        newEvent.setDate(body.date());
        newEvent.setLocation(body.location());
        newEvent.setName(body.name());
        newEvent.setManager(manager);
        newEvent.setMaxPeople(body.maxPeople());
        newEvent.setDescription(body.description());

        return eventsDAO.save(newEvent);
    }

    public Event getById(UUID id){
        Optional<Event> found = eventsDAO.findById(id);
        if(found.isEmpty()){
            throw new ItemNotFoundException(id);
        }
        return found.get();
    }

    public void getByIdAndDelete(UUID id){
        Event found = this.getById(id);
        eventsDAO.delete(found);
    }

    public Event getByIdAndChange(UUID id, NewEventDTO body){
        Event found = this.getById(id);

        found.setDescription(body.description());
        found.setDate(body.date());
        found.setLocation(body.location());
        found.setName(body.name());
        found.setMaxPeople(body.maxPeople());

        return eventsDAO.save(found);
    }
}

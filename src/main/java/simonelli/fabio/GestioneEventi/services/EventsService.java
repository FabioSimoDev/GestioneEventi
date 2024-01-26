package simonelli.fabio.GestioneEventi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import simonelli.fabio.GestioneEventi.entities.Event;
import simonelli.fabio.GestioneEventi.repositories.EventsDAO;

@Service
public class EventsService {
    @Autowired
    private EventsDAO eventsDAO;

    public Page<Event> getEvents(int page, int size, String orderBy){
        if(size >= 50) size=50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return eventsDAO.findAll(pageable);
    }
}

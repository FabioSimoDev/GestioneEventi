package simonelli.fabio.GestioneEventi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simonelli.fabio.GestioneEventi.entities.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventsDAO extends JpaRepository<Event, UUID> {
    public List<Event> findByDate(LocalDate date);
}

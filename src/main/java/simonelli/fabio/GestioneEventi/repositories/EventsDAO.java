package simonelli.fabio.GestioneEventi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simonelli.fabio.GestioneEventi.entities.Event;

import java.util.UUID;

@Repository
public interface EventsDAO extends JpaRepository<Event, UUID> {
}

package simonelli.fabio.GestioneEventi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simonelli.fabio.GestioneEventi.entities.Event;
import simonelli.fabio.GestioneEventi.entities.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersDAO extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<Event> findByEventListId(UUID eventId);
}

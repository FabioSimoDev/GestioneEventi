package simonelli.fabio.GestioneEventi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonelli.fabio.GestioneEventi.entities.Event;
import simonelli.fabio.GestioneEventi.entities.Role;
import simonelli.fabio.GestioneEventi.entities.User;
import simonelli.fabio.GestioneEventi.exceptions.AlreadyBookedException;
import simonelli.fabio.GestioneEventi.exceptions.ItemNotFoundException;
import simonelli.fabio.GestioneEventi.exceptions.RoleAlreadySetException;
import simonelli.fabio.GestioneEventi.exceptions.RoleNotValidException;
import simonelli.fabio.GestioneEventi.repositories.EventsDAO;
import simonelli.fabio.GestioneEventi.repositories.UsersDAO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private EventsService eventsService;

    public Page<User> getUsers(int page, int size, String orderBy) {
        if (size >= 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return usersDAO.findAll(pageable);
    }

    public User findById(UUID id) {
        return usersDAO.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }

    public void findByIdAndDelete(UUID id) {
        User found = this.findById(id);
        usersDAO.delete(found);
    }

    public User findByIdAndUpdate(UUID id, User body) {
        User found = this.findById(id);
        found.setSurname(body.getSurname());
        found.setName(body.getName());
        found.setEmail(body.getEmail());
        found.setPassword(body.getPassword());
        return usersDAO.save(found);
    }


    public User findByEmail(String email) throws ItemNotFoundException {
        return usersDAO.findByEmail(email).orElseThrow(() -> new ItemNotFoundException("Utente con email " + email + " non trovata!"));
    }

    public User changeUserRole(UUID id, String role) {
        User found = this.findById(id);
        try {
            Role targetRole = Role.valueOf(role);
            if (found.getRole().equals(targetRole)) {
                throw new RoleAlreadySetException(role);
            }
            found.setRole(targetRole);
            return usersDAO.save(found);
        } catch (IllegalArgumentException ex) {
            throw new RoleNotValidException(role);
        }
    }

//    public Event bookEvent(User user, UUID eventId) {
//        Event foundEvent = eventsService.getById(eventId);
////        boolean isAlreadyBooked = usersDAO.findByEventListId(eventId).isPresent();
////        if (isAlreadyBooked) {
////            throw new AlreadyBookedException("Hai già una prenotazione attiva per l'evento a " + foundEvent.getLocation()
////                    + " che si terrà il giorno " + foundEvent.getDate());
////        }
//        List<Event> newUserEventList = user.getEventList();
//        newUserEventList.add(foundEvent);
//        user.setEventList(newUserEventList);
//        usersDAO.save(user);
//        return foundEvent;
//    }
}

package simonelli.fabio.GestioneEventi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import simonelli.fabio.GestioneEventi.entities.User;
import simonelli.fabio.GestioneEventi.exceptions.ItemNotFoundException;
import simonelli.fabio.GestioneEventi.repositories.UsersDAO;

import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersDAO usersDAO;

    public Page<User> getUsers(int page, int size, String orderBy){
        if(size >= 50) size = 50;
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
}

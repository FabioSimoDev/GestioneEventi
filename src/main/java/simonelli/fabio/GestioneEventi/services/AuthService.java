package simonelli.fabio.GestioneEventi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import simonelli.fabio.GestioneEventi.entities.Role;
import simonelli.fabio.GestioneEventi.entities.User;
import simonelli.fabio.GestioneEventi.exceptions.BadRequestException;
import simonelli.fabio.GestioneEventi.exceptions.UnauthorizedException;
import simonelli.fabio.GestioneEventi.payloads.NewUserDTO;
import simonelli.fabio.GestioneEventi.payloads.UserLoginDTO;
import simonelli.fabio.GestioneEventi.repositories.UsersDAO;
import simonelli.fabio.GestioneEventi.security.JWTTools;

@Service
public class AuthService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jwtTools;

    public String authenticateUser(UserLoginDTO body) {
        User user = usersService.findByEmail(body.email());

        if (bcrypt.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }

    public User save(NewUserDTO body) {
        usersDAO.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
        });

        User newUser = new User();
        newUser.setSurname(body.surname());
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setPassword(bcrypt.encode(body.password()));
        newUser.setRole(Role.USER);
        return usersDAO.save(newUser);
    }
}

package simonelli.fabio.GestioneEventi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import simonelli.fabio.GestioneEventi.entities.User;
import simonelli.fabio.GestioneEventi.exceptions.BadRequestException;
import simonelli.fabio.GestioneEventi.payloads.NewUserDTO;
import simonelli.fabio.GestioneEventi.payloads.NewUserResponseDTO;
import simonelli.fabio.GestioneEventi.payloads.UserLoginDTO;
import simonelli.fabio.GestioneEventi.payloads.UserLoginResponseDTO;
import simonelli.fabio.GestioneEventi.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
        String accessToken = authService.authenticateUser(body);
        return new UserLoginResponseDTO(accessToken);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDTO createUser(@RequestBody @Validated NewUserDTO newUserPayload, BindingResult validation) {
        // Per completare la validazione devo in qualche maniera fare un controllo del tipo: se ci sono errori -> manda risposta con 400 Bad Request
        System.out.println(validation);
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!"); // L'eccezione arriverà agli error handlers tra i quali c'è quello che manderà la risposta con status code 400
        } else {
            User newUser = authService.save(newUserPayload);

            return new NewUserResponseDTO(newUser.getId());
        }
    }
}

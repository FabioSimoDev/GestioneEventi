package simonelli.fabio.GestioneEventi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import simonelli.fabio.GestioneEventi.entities.Event;
import simonelli.fabio.GestioneEventi.entities.User;
import simonelli.fabio.GestioneEventi.payloads.NewEventResponseDTO;
import simonelli.fabio.GestioneEventi.payloads.NewUserResponseDTO;
import simonelli.fabio.GestioneEventi.services.UsersService;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String orderBy){
        return usersService.getUsers(page, size, orderBy);
    }

    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }

    @PutMapping("/me")
    public User getMeAndUpdate(@AuthenticationPrincipal User currentUser, @RequestBody User body) {
        return usersService.findByIdAndUpdate(currentUser.getId(), body);
    }

    @DeleteMapping("/me")
    public void getMeAnDelete(@AuthenticationPrincipal User currentUser) {
        usersService.findByIdAndDelete(currentUser.getId());
    }


    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId) {
        return usersService.findById(userId);
    }

    @PutMapping("/role/{userId}")
    public NewUserResponseDTO changeUserRole(@PathVariable UUID userId,
                                             @RequestParam(name = "role", required = true) String role){
        User changedUser = usersService.changeUserRole(userId, role);
        return new NewUserResponseDTO(changedUser.getId());
    }

//    @PostMapping("/me/bookEvent/{eventId}")
//    public NewEventResponseDTO bookEvent(@AuthenticationPrincipal User user, @PathVariable UUID eventId){
//        Event found = usersService.bookEvent(user, eventId);
//        return new NewEventResponseDTO(found.getId());
//    }
}

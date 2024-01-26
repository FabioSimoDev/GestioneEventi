package simonelli.fabio.GestioneEventi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
public class Event {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String location;
    private LocalDate date;

    @ManyToMany(mappedBy = "eventList")
    private List<User> userList;
}

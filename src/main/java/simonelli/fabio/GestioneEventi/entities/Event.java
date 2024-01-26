package simonelli.fabio.GestioneEventi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
@JsonIgnoreProperties({"userList"})
public class Event {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String location;
    private LocalDate date;

    @ManyToMany(mappedBy = "eventList")
    private List<User> userList;

    private int maxPeople;
    private int numParticipants;

    @ManyToOne
    private User manager;
}

package ua.kalin.spring.conferences.model.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "conference")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "img")
    private byte[] img;

    @Column(name = "visitors")
    private int visitors;

    @Column(name = "places")
    private int places;

    @ManyToMany
    @JoinTable(name = "person_conference",
               joinColumns = @JoinColumn(name = "conference_id"),
               inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> persons;

    @OneToMany(mappedBy = "conference")
    private List<Report> reports;
}
package ua.kalin.spring.conferences.model.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "person")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Email(message = "invalid.email")
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,40}", message = "invalid.name")
    @Column(name = "name")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,50}", message = "invalid.surname")
    @Column(name = "surname")
    private String surname;

    @Enumerated
    @Column(name = "role")
    private Role role;

    @Column(name = "img")
    private byte[] img;

    @Column(name = "blocked")
    private boolean blocked;

    @ManyToMany(mappedBy = "persons")
    private List<Conference> conferences;

    @OneToMany(mappedBy = "speaker")
    private List<Report> reports;

    @OneToMany(mappedBy = "person")
    private List<Notification> notifications;
}
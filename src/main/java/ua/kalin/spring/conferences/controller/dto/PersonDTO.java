package ua.kalin.spring.conferences.controller.dto;

import lombok.Data;
import ua.kalin.spring.conferences.model.models.Role;

@Data
public class PersonDTO {
    private int id;
    private String email;
    private String name;
    private String surname;
    private Role role;
    private boolean blocked;
}
package ua.kalin.spring.conferences.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PersonProfileDTO {
    private int id;

    @Email(message = "invalid.email")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,40}", message = "invalid.name")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,50}", message = "invalid.surname")
    private String surname;
}

package ua.kalin.spring.conferences.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.kalin.spring.conferences.controller.dto.PersonProfileDTO;
import ua.kalin.spring.conferences.model.models.Person;
import ua.kalin.spring.conferences.model.services.PersonService;

import java.util.Optional;

@Component
public class PersonProfileDTOValidator implements Validator {

    private final PersonService personService;

    @Autowired
    public PersonProfileDTOValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(@NonNull Class<?> aClass) {
        return PersonProfileDTO.class.equals(aClass);
    }

    @Override
    public void validate(@NonNull Object o, @NonNull Errors errors) {
        PersonProfileDTO person = (PersonProfileDTO) o;
        validateEmail(person, errors);
    }

    private void validateEmail(PersonProfileDTO person, Errors errors) {
        String email = person.getEmail();
        if (personService.getByEmail(email).isPresent()) {
            Optional<Person> optionalPerson = personService.getById(person.getId());
            if (optionalPerson.isEmpty() || !optionalPerson.get().getEmail().equals(email)) {
                errors.rejectValue("email", "", "duplicate.email");
            }
        }
    }
}
package ua.kalin.spring.conferences.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.kalin.spring.conferences.controller.dto.PersonRegistrationDTO;
import ua.kalin.spring.conferences.model.services.PersonService;

@Component
public class PersonRegistrationDTOValidator implements Validator {

    private final PersonService personService;

    @Autowired
    public PersonRegistrationDTOValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(@NonNull Class<?> aClass) {
        return PersonRegistrationDTO.class.equals(aClass);
    }

    @Override
    public void validate(@NonNull Object o, @NonNull Errors errors) {
        PersonRegistrationDTO person = (PersonRegistrationDTO) o;
        validateEmail(person.getEmail(), errors);
        validatePassword(person.getPassword(), person.getConfirmPassword(), errors);
    }

    private void validateEmail(String email, Errors errors) {
        if (personService.getByEmail(email).isPresent()) {
            errors.rejectValue("email", "", "Email already registered");
        }
    }

    private void validatePassword(String password, String confirmPassword, Errors errors) {
        if (!password.equals(confirmPassword)) {
            errors.rejectValue("password", "", "passwords.do.not.match");
        }
    }
}
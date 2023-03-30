package ua.kalin.spring.conferences.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.kalin.spring.conferences.controller.dto.PasswordUpdateDTO;
import ua.kalin.spring.conferences.model.models.Person;
import ua.kalin.spring.conferences.model.services.PersonService;

import java.util.Optional;

@Component
public class PasswordUpdateDTOValidator implements Validator {

    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordUpdateDTOValidator(PersonService personService, PasswordEncoder passwordEncoder) {
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean supports(@NonNull Class<?> aClass) {
        return PasswordUpdateDTO.class.equals(aClass);
    }

    @Override
    public void validate(@NonNull Object o, @NonNull Errors errors) {
        PasswordUpdateDTO passwordUpdateDTO = (PasswordUpdateDTO) o;
        validateOldPassword(passwordUpdateDTO, errors);
        validateNewPassword(passwordUpdateDTO, errors);
    }

    private void validateNewPassword(PasswordUpdateDTO passwordUpdateDTO, Errors errors) {
        if (!passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getConfirmPassword())) {
            errors.rejectValue("newPassword", "", "passwords.do.not.match");
        }
    }

    private void validateOldPassword(PasswordUpdateDTO passwordUpdateDTO, Errors errors) {
        Optional<Person> person = personService.getById(passwordUpdateDTO.getId());
        person.ifPresent(p -> {
            if (!passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), p.getPassword())) {
                errors.rejectValue("oldPassword", "", "wrong.password");
            }
        });
    }

}
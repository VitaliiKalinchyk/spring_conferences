package ua.kalin.spring.conferences.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kalin.spring.conferences.model.models.Person;
import ua.kalin.spring.conferences.model.models.Role;
import ua.kalin.spring.conferences.model.repositories.PersonRepository;

@Service
@Transactional
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(Role.ROLE_VISITOR);
        personRepository.save(person);
    }
}
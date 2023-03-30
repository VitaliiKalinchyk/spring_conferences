package ua.kalin.spring.conferences.model.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kalin.spring.conferences.model.models.Person;
import ua.kalin.spring.conferences.model.models.Role;
import ua.kalin.spring.conferences.model.repositories.PersonRepository;

import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@Transactional
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private static final int ROWS = 5;

    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Optional<Person> getById(int id) {
        return personRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Person> getByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Page<Person> getAll(String searchBy, String sortField, String sortOrder, int page) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? DESC : ASC;
        Sort sort = Sort.by(direction, sortField);
        return personRepository.findPersons(searchBy, PageRequest.of(page, ROWS, sort));
    }

    public void update(Person p) {
        personRepository.updateEmailAndNameAndSurnameById(p.getEmail(), p.getName(), p.getSurname(), p.getId());
    }

    public void updatePassword(int id, String newPassword) {
        personRepository.updatePasswordById(passwordEncoder.encode(newPassword), id);
    }

    public void block(int id) {
        personRepository.setBlockedById(id);
    }

    public void unblock(int id) {
        personRepository.setUnblockedById(id);
    }

    public void setRole(int id, String role) {
        personRepository.updateRoleById(Role.valueOf(role), id);
    }
}
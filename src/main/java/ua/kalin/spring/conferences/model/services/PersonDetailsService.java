package ua.kalin.spring.conferences.model.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kalin.spring.conferences.model.repositories.PersonRepository;
import ua.kalin.spring.conferences.security.PersonDetails;

@Service
@Transactional(readOnly = true)
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new PersonDetails(personRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("no such user")));
    }
}

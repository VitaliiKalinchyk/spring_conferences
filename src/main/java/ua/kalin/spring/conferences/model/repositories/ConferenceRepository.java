package ua.kalin.spring.conferences.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kalin.spring.conferences.model.models.Conference;

public interface ConferenceRepository extends JpaRepository<Conference, Integer> {
}
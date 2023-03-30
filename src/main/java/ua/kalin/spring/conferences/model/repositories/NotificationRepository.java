package ua.kalin.spring.conferences.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kalin.spring.conferences.model.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
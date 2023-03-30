package ua.kalin.spring.conferences.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kalin.spring.conferences.model.models.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}
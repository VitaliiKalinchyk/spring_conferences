package ua.kalin.spring.conferences.model.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.kalin.spring.conferences.model.models.Person;
import ua.kalin.spring.conferences.model.models.Role;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("select p from Person p where p.email like %:searchBy% or p.name like %:searchBy% or p.surname like %:searchBy%")
    Page<Person> findPersons(@Param("searchBy") String searchBy, Pageable pageable);
    @Modifying
    @Query("update Person p set p.role = ?1 where p.id = ?2")
    void updateRoleById(Role role, int id);
    @Modifying
    @Query("update Person p set p.blocked = false where p.id = ?1")
    void setUnblockedById(int id);
    @Modifying
    @Query("update Person p set p.blocked = true where p.id = ?1")
    void setBlockedById(int id);
    @Modifying
    @Query("update Person p set p.password = ?1 where p.id = ?2")
    void updatePasswordById(String password, int id);

    @Modifying
    @Query("update Person p set p.email = ?1, p.name = ?2, p.surname = ?3 where p.id = ?4")
    void updateEmailAndNameAndSurnameById(String email, String name, String surname, int id);

    Optional<Person> findByEmail(String email);
}
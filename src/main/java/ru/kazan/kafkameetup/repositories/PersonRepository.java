package ru.kazan.kafkameetup.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import ru.kazan.kafkameetup.model.Person;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}

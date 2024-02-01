package ru.kazan.kafkameetup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kazan.kafkameetup.model.Passport;

public interface PassportRepository extends JpaRepository<Passport, Long> {

}

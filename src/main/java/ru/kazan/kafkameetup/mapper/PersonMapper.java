package ru.kazan.kafkameetup.mapper;

import org.mapstruct.*;
import ru.kazan.kafkameetup.dto.PassportDTO;
import ru.kazan.kafkameetup.dto.PersonDTO;
import ru.kazan.kafkameetup.model.Passport;
import ru.kazan.kafkameetup.model.Person;


@Mapper( componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {

    Person toPerson(PersonDTO personDTO);

    @Mapping(target = "id", ignore = true)
    Passport toPassport(PassportDTO personDTO);

    @Mapping(source = "passport", target = "passportData")
    PersonDTO toPersonDto(Person person);

}

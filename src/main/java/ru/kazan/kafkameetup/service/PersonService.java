package ru.kazan.kafkameetup.service;

import lombok.extern.slf4j.Slf4j;
import ru.kazan.kafkameetup.kafka.KafkaProducer;
import ru.kazan.kafkameetup.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kazan.kafkameetup.dto.PersonDTO;
import ru.kazan.kafkameetup.model.Passport;
import ru.kazan.kafkameetup.model.Person;
import ru.kazan.kafkameetup.repositories.PassportRepository;
import ru.kazan.kafkameetup.repositories.PersonRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
public class PersonService {

    public final PersonMapper personMapper;

    public final KafkaProducer kafkaProducer;

    public final PersonRepository personRepository;

    public final PassportRepository passportRepository;

    @Autowired
    public PersonService(PersonMapper personMapper, KafkaProducer kafkaProducer,
                         PersonRepository personRepository, PassportRepository passportRepository) {
        this.personMapper = personMapper;
        this.kafkaProducer = kafkaProducer;
        this.personRepository = personRepository;
        this.passportRepository = passportRepository;
    }

    public PersonDTO getPersonInfo(String personId){
        UUID id = getUuid(personId);
        return personRepository.findById(id).map(personMapper::toPersonDto)
                .orElseThrow(() -> new RuntimeException("Ошибка в сервисе"));
    }

    public List<PersonDTO> getAllPerson(){
        return personRepository.findAll()
                .stream()
                .map(personMapper::toPersonDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public void addNewPerson(PersonDTO dto){
        Person person = personMapper.toPerson(dto);
        Passport passport = personMapper.toPassport(dto.passportData());
        person.setId(getUuid(dto.id()));
        personRepository.save(person);
        passport.setPerson(person);
        passportRepository.save(passport);
        kafkaProducer.sendMessage(dto);
    }

    private UUID getUuid(String personId) {
        UUID id;
        try {
            id = UUID.fromString(personId);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Ошибка с UUID");
        }
        return id;
    }
}

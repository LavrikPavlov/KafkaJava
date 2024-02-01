package ru.kazan.kafkameetup.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.kazan.kafkameetup.dto.PersonDTO;
import ru.kazan.kafkameetup.service.PersonService;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final PersonService personService;

    private static final String MESSAGE = "Received message. Topic: '{}': {}";


    @KafkaListener(topics = "${spring.kafka.consumer.topics.service}",
                    properties = "${spring.kafka.consumer.types.new-person}")
    public void consumeAdd(PersonDTO dto) {
        log.info(MESSAGE, "person_topic", dto);
        personService.addNewPerson(dto);
    }
}

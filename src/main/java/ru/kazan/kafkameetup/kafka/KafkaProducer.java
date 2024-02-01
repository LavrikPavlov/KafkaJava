package ru.kazan.kafkameetup.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import ru.kazan.kafkameetup.dto.PersonDTO;

@Slf4j
@Service
public class KafkaProducer {

    @Value("${spring.kafka.producer.topics.topic-name}")
    private String topicName;


    private final KafkaTemplate<String, PersonDTO> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, PersonDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(PersonDTO personDTO) {

        Message<PersonDTO> message = MessageBuilder.withPayload(personDTO)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .setHeader(KafkaHeaders.PARTITION, 0)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();

        kafkaTemplate.send(message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Message sent: {} on partition {} with offset {}",
                                personDTO, result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    } else {
                        log.info("Unable to send message: {} due to {}", personDTO,
                                ex.getLocalizedMessage());
                    }
                });
    }
}
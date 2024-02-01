package ru.kazan.kafkameetup.dto;


public record PersonDTO(
        String id,
        String firstName,
        String secondName,
        String email,
        int age,
        PassportDTO passportData
) {


}

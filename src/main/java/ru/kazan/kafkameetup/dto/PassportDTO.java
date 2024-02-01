package ru.kazan.kafkameetup.dto;


public record PassportDTO(
        String series,
        String number,
        String department,
        String personId
) {}

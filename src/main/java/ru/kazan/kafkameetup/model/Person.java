package ru.kazan.kafkameetup.model;

import jakarta.persistence.*;
import lombok.*;


import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    UUID id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "first_second")
    String secondName;

    String email;

    int age;

    @OneToOne(mappedBy = "person")
    Passport passport;

}

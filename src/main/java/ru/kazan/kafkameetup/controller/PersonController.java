package ru.kazan.kafkameetup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kazan.kafkameetup.dto.PersonDTO;
import ru.kazan.kafkameetup.service.PersonService;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController {

    public final PersonService personService;

    @GetMapping("/person")
    public ResponseEntity<PersonDTO> getPersonInfo(@RequestParam String personId){
        PersonDTO personDTO = personService.getPersonInfo(personId);
        return ResponseEntity.ok(personDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonDTO>> getAllPerson(){
        List<PersonDTO> persons = personService.getAllPerson();
        return ResponseEntity.ok(persons);
    }

    @PostMapping("/add")
    public ResponseEntity<PersonDTO> addNewPerson(@RequestBody PersonDTO person){
        personService.addNewPerson(person);
        return ResponseEntity.ok(person);
    }
}

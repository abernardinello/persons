package be.eonix.personmanagement.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import be.eonix.personmanagement.api.PersonApi;
import be.eonix.personmanagement.model.Person;
import be.eonix.personmanagement.model.PostPersonRequest;
import be.eonix.personmanagement.service.PersonService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Controller
public class PersonController implements PersonApi {

    private final PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<List<Person>> getPersons(@Valid String firstName, @Valid String lastName) {

        return ResponseEntity.ok(service.search(firstName, lastName));
    }

    @Override
    public ResponseEntity<Person> postPerson(@Valid PostPersonRequest postPersonRequest) {

        return ResponseEntity.ok(service.create(postPersonRequest));
    }

    @Override
    public ResponseEntity<Void> putPerson(@Valid Person person) {
        try {
            service.update(person);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> deletePersonPersonId(UUID personId) {

        service.delete(personId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Person> getPersonPersonId(UUID personId) {
        return service.findById(personId).map(p -> ResponseEntity.ok(p))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



}

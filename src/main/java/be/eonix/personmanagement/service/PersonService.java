package be.eonix.personmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import be.eonix.personmanagement.model.Person;
import be.eonix.personmanagement.model.PostPersonRequest;
import jakarta.persistence.EntityNotFoundException;

public interface PersonService {
    public Optional<Person> findById(UUID Id);

    public Person create(PostPersonRequest person);

    public void update(Person person) throws EntityNotFoundException;

    public void delete(UUID id);

    public List<Person> search(String firstName, String lastName);
}

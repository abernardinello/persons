package be.eonix.personmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.eonix.personmanagement.entity.PersonEntity;
import be.eonix.personmanagement.mapper.PersonMapper;
import be.eonix.personmanagement.model.Person;
import be.eonix.personmanagement.model.PostPersonRequest;
import be.eonix.personmanagement.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    @Autowired
    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Person> findById(UUID Id) {
        return repository.findById(Id).map(entity -> PersonMapper.INSTANCE.personEntityToPerson(entity));
    }

    @Override
    public Person create(PostPersonRequest person) {
        PersonEntity entity = repository.save(new PersonEntity(null, person.getFirstName(), person.getLastName()));
        return PersonMapper.INSTANCE.personEntityToPerson(entity);

    }

    @Override
    @Transactional
    public void update(Person person) throws EntityNotFoundException {
        PersonEntity entity = repository.getReferenceById(person.getId());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<Person> search(String firstName, String lastName) {
            
        return repository.search(firstName, lastName).stream()
                .map(e -> PersonMapper.INSTANCE.personEntityToPerson(e)).collect(Collectors.toList());

    }

}

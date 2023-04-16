package be.eonix.personmanagement.repository.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.eonix.personmanagement.entity.PersonEntity;
import be.eonix.personmanagement.model.Person;
import be.eonix.personmanagement.model.PostPersonRequest;
import be.eonix.personmanagement.repository.PersonRepository;
import be.eonix.personmanagement.service.PersonServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTests {

    @InjectMocks
    PersonServiceImpl service;

    @Mock
    PersonRepository personRepositoryMock;

    private static final UUID TEST_UUID = UUID.randomUUID();
    private static final PersonEntity TEST_PERSON_ENTITY = new PersonEntity(TEST_UUID, "First name", "Last name");

    @Test
    public void testFindById() {
        when(personRepositoryMock.findById(TEST_UUID)).thenReturn(Optional.of(TEST_PERSON_ENTITY));
        
        Optional<Person> findById = service.findById(TEST_UUID);
        
        Assertions.assertThat(findById.isPresent()).isTrue();
        verify(personRepositoryMock,times(1)).findById(TEST_UUID);
    }

    @Test
    public void testCreate() {
        service.create(new PostPersonRequest().firstName("test").lastName("test"));
        verify(personRepositoryMock, times(1)).save(any(PersonEntity.class));
    }

    @Test
    public void testDelete() {
        service.delete(TEST_UUID);
        verify(personRepositoryMock, times(1)).deleteById(TEST_UUID);
    }

    @Test
    public void testUpdate() {

        Person person = new Person().id(TEST_UUID).firstName("first").lastName("last");
        when(personRepositoryMock.getReferenceById(TEST_UUID)).thenReturn(TEST_PERSON_ENTITY);
        service.update(person);
        verify(personRepositoryMock, times(1)).getReferenceById(person.getId());
    }
    
    @Test
    public void testSearch() {
        
        List<PersonEntity> entities = new ArrayList<>();
        entities.add(new PersonEntity());
        entities.add(new PersonEntity());
        entities.add(new PersonEntity());
        
        when(personRepositoryMock.search(any(String.class), any(String.class))).thenReturn(entities);
        
        List<Person> search = service.search("", "");
        verify(personRepositoryMock,times(1)).search(any(String.class), any(String.class));
        
        Assertions.assertThat(search.size()).isEqualTo(entities.size());
    }

}

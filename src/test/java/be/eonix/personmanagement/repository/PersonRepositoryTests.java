package be.eonix.personmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import be.eonix.personmanagement.entity.PersonEntity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTests {
    @Autowired
    PersonRepository personRepository;
    
    @Test
    public void testCreateRead() {
        PersonEntity entity = new PersonEntity();
        entity.setFirstName("testFirstName");
        entity.setLastName("testLastName");
        
        PersonEntity savedEntity = personRepository.saveAndFlush(entity);
        
        PersonEntity loadedEntity = personRepository.getReferenceById(savedEntity.getId());
        
        Assertions.assertThat(loadedEntity).isEqualTo(loadedEntity);
        
    }
    
    @Test
    public void testUpdate() {
        PersonEntity entity = new PersonEntity();
        entity.setFirstName("testFirstName");
        entity.setLastName("testLastName");
        
        PersonEntity savedEntity = personRepository.saveAndFlush(entity);
        
        PersonEntity loadedEntity = personRepository.getReferenceById(savedEntity.getId());
        
        loadedEntity.setFirstName("updatedFristName");
        
        PersonEntity updatedEntity = personRepository.saveAndFlush(loadedEntity);
        
        Assertions.assertThat(loadedEntity).isEqualTo(updatedEntity);
    }
    
    @Test
    public void testDelete() {
        PersonEntity entity = new PersonEntity();
        entity.setFirstName("testFirstName");
        entity.setLastName("testLastName");
        
        PersonEntity savedEntity = personRepository.saveAndFlush(entity);
        
        personRepository.deleteById(savedEntity.getId());
        
        Optional<PersonEntity> loadedEntity = personRepository.findById(savedEntity.getId());
        
        Assertions.assertThat(loadedEntity.isEmpty()).isTrue();
    }
    
    @Test
    // This test use data present in data.sql
    public void testSearch() {

        
        List<PersonEntity> persons = personRepository.search(null, null);
        
        Assertions.assertThat(persons.size()).isEqualTo(3);
        
        persons = personRepository.search("unknow", null);
        
        Assertions.assertThat(persons.size()).isEqualTo(0);
        
        persons = personRepository.search("Ra", null);
        
        Assertions.assertThat(persons.size()).isEqualTo(1);
        
        persons = personRepository.search("AN", null);
        
        Assertions.assertThat(persons.size()).isEqualTo(2);
        
        persons = personRepository.search("lan", "tu");
        
        Assertions.assertThat(persons.size()).isEqualTo(1);
    }
}

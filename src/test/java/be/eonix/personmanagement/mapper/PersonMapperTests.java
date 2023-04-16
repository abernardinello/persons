package be.eonix.personmanagement.mapper;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import be.eonix.personmanagement.entity.PersonEntity;
import be.eonix.personmanagement.model.Person;

public class PersonMapperTests {
    private static final String LAST_NAME = "Last name";
    private static final String FIRST_NAME = "First name";
    private static final UUID TEST_UUID = UUID.randomUUID();
    private static final PersonEntity TEST_PERSON_ENTITY = new PersonEntity(TEST_UUID, FIRST_NAME, LAST_NAME);
    private static final Person TEST_PERSON = new Person().id(TEST_UUID).firstName(FIRST_NAME).lastName(LAST_NAME);

    private PersonMapper mapper = PersonMapper.INSTANCE;

    @Test
    public void givenPersonEntityToPerson_whenMaps_thenCorrect() {
        Person personEntityToPerson = mapper.personEntityToPerson(TEST_PERSON_ENTITY);

        Assertions.assertThat(personEntityToPerson).isEqualTo(TEST_PERSON);
    }

    @Test
    public void givenPersonToPersonEntity_whenMaps_thenCorrect() {
        PersonEntity personToPersonEntity = mapper.personToPersonEntity(TEST_PERSON);

        Assertions.assertThat(personToPersonEntity).isEqualTo(TEST_PERSON_ENTITY);
    }
}

package be.eonix.personmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import be.eonix.personmanagement.entity.PersonEntity;
import be.eonix.personmanagement.model.Person;


@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    Person personEntityToPerson(PersonEntity personEntity);

    PersonEntity personToPersonEntity(Person person);
}

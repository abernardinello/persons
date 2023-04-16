package be.eonix.personmanagement.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import be.eonix.personmanagement.entity.PersonEntity;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {

    @Query(value = "SELECT e FROM PersonEntity e WHERE (:firstName is null or lower(e.firstName) like concat('%',lower(:firstName),'%')) and (:lastName is null or lower(e.lastName) like concat('%',lower(:lastName),'%'))")
    List<PersonEntity> search(@Param("firstName") String firstName, @Param("lastName") String lastName);
}

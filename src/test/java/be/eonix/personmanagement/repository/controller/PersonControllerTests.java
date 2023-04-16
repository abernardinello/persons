package be.eonix.personmanagement.repository.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.eonix.personmanagement.controller.PersonController;
import be.eonix.personmanagement.model.Person;
import be.eonix.personmanagement.model.PostPersonRequest;
import be.eonix.personmanagement.service.PersonService;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTests {
    private static final String LAST_NAME = "Last name";
    private static final String FIRST_NAME = "First name";
    private static final PostPersonRequest TEST_POST_PERSON = new PostPersonRequest().firstName(FIRST_NAME)
            .lastName(FIRST_NAME);
    private static final UUID TEST_UUID = UUID.randomUUID();
    private static final Person TEST_PERSON = new Person().id(TEST_UUID).firstName(FIRST_NAME).lastName(LAST_NAME);

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private PersonService personServiceMock;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void givenSearchById_whenFound_thenReturnPerson() throws Exception {
        when(personServiceMock.findById(any(UUID.class))).thenReturn(Optional.of(TEST_PERSON));
        
        mockMvc.perform(get("/person/"+TEST_UUID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", Matchers.is(FIRST_NAME)))
        .andExpect(jsonPath("$.lastName", Matchers.is(LAST_NAME)))
        .andExpect(jsonPath("$.id", Matchers.is(TEST_UUID.toString())));
    }

    @Test
    public void givenSearchById_whenNotFound_then404() throws Exception {
        when(personServiceMock.findById(any(UUID.class))).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/person/"+TEST_UUID))
        .andExpect(status().isNotFound());
    }

    @Test
    public void givenDeleteById_whenDelete_thenOk() throws Exception {

        mockMvc.perform(delete("/person/" + TEST_UUID)).andExpect(status().isOk());

        verify(personServiceMock, times(1)).delete(TEST_UUID);
    }

    @Test
    public void givenUpdatePerson_whenUpdate_thenOk() throws Exception {

        mockMvc.perform(
                put("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(TEST_PERSON)))
                .andExpect(status().isOk());

        verify(personServiceMock, times(1)).update(TEST_PERSON);
    }

    @Test
    public void givenUpdatePerson_whenUpdateThrowEntityNotFoundException_then404() throws Exception {

        doThrow(EntityNotFoundException.class).when(personServiceMock).update(TEST_PERSON);

        mockMvc.perform(
                put("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(TEST_PERSON)))
                .andExpect(status().isNotFound());

        verify(personServiceMock, times(1)).update(TEST_PERSON);
    }

    @Test
    public void givenCreatePerson_whenCreate_thenOk() throws Exception {

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(TEST_POST_PERSON))).andExpect(status().isOk());

        verify(personServiceMock, times(1)).create(TEST_POST_PERSON);
    }

    @Test
    public void givenCreatePerson_whenCreateEmptyFirstName_thenBadRequest() throws Exception {

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new PostPersonRequest().lastName(LAST_NAME))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenCreatePerson_whenCreateEmptyLastName_thenBadRequest() throws Exception {

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new PostPersonRequest().firstName(FIRST_NAME))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenSearchPerson_whenSearch_thenOk() throws Exception {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(TEST_PERSON);
        when(personServiceMock.search(FIRST_NAME, LAST_NAME)).thenReturn(persons);
        mockMvc.perform(get("/persons").queryParam("firstName", FIRST_NAME).queryParam("lastName", LAST_NAME))
                .andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is(FIRST_NAME)))
                .andExpect(jsonPath("$[0].lastName", Matchers.is(LAST_NAME)))
                .andExpect(jsonPath("$[0].id", Matchers.is(TEST_UUID.toString())));
        verify(personServiceMock, times(1)).search(FIRST_NAME, LAST_NAME);
    }

    @Test
    public void givenSearchPerson_whenSearchNoResults_thenOk() throws Exception {
        ArrayList<Person> persons = new ArrayList<>();

        when(personServiceMock.search(FIRST_NAME, LAST_NAME)).thenReturn(persons);
        mockMvc.perform(get("/persons").queryParam("firstName", FIRST_NAME).queryParam("lastName", LAST_NAME))
                .andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(0)));
        verify(personServiceMock, times(1)).search(FIRST_NAME, LAST_NAME);
    }
}

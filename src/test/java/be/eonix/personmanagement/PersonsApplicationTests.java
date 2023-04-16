package be.eonix.personmanagement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import be.eonix.personmanagement.controller.PersonController;

@SpringBootTest
class PersonsApplicationTests {

    @Autowired
    PersonController personController;

    @Test
    void contextLoads(ApplicationContext context) {
        assertThat(context).isNotNull();
    }

}

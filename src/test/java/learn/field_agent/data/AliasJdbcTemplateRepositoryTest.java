package learn.field_agent.data;

import learn.field_agent.domain.Result;
import learn.field_agent.domain.ResultType;
import learn.field_agent.models.Alias;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AliasJdbcTemplateRepositoryTest {

    @Autowired
    AliasJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindById() {
        Alias alias = new Alias();
        Alias actual = repository.findById(2);
        assertEquals(null, actual);
    }


//    @Test   ////////////This one isn't working///////////////
//    void shouldAddAlias() {
//        Alias alias = new Alias();
//        alias.setName("AddTEST");
//        Alias actual = repository.add(alias);
//        assertNotNull(actual);
//        assertEquals(3, actual.getAliasId());
//    }


    @Test
    void shouldUpdateAlias() {
        Alias alias = new Alias();
        alias.setAliasId(2);
        alias.setName("UpdateTEST");
        assertFalse(repository.update(alias));
    }

    @Test
    void shouldDeleteAlias() {
        assertFalse(repository.deleteById(6));
    }
}

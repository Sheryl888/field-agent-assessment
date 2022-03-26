package learn.field_agent.data;

import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityClearanceJdbcTemplateRepositoryTest{

    @Autowired
    SecurityClearanceJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindById() {
        SecurityClearance secret = new SecurityClearance(1, "Secret");
        SecurityClearance topSecret = new SecurityClearance(2, "Top Secret");

        SecurityClearance actual = repository.findById(1);
        assertEquals(secret, actual);

        actual = repository.findById(2);
        assertEquals(topSecret, actual);

        actual = repository.findById(3);
        assertEquals(null, actual);
    }

    @Test
    void shouldFindSecret() {
        SecurityClearance secret = repository.findById(1);
        assertEquals("Secret", secret.getName());
    }

    @Test
    void shouldFindTopSecret() {
        SecurityClearance topSecret = repository.findById(2);
        assertEquals("Top Secret", topSecret.getName());
    }

    @Test
    void shouldAddSecurityClearance() {
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setName("AddTEST");
        SecurityClearance actual = repository.add(securityClearance);
        assertNotNull(actual);
        assertEquals(3, actual.getSecurityClearanceId());
    }

    @Test
    void shouldUpdateSecurityClearance() {

        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setSecurityClearanceId(3);
        securityClearance.setName("UpdateTEST");


        assertTrue(repository.update(securityClearance));
    }

    @Test
    void shouldDeleteSecurityClearance() {
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }
}
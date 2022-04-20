package learn.field_agent.domain;

import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Alias;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AliasServiceTest {

    @Autowired
    AliasService service;

    @MockBean
    AliasRepository aliasRepository;


    @Test
    void shouldAdd() {
        Alias alias = new Alias(0, "Name Test", "Sneaky Pete" );
        Alias mockOut = new Alias(3, "Name Test", " ");

        when(aliasRepository.add(alias)).thenReturn(mockOut);

        Result<Alias> actual = service.add(alias);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }


    @Test
    void shouldNotAddWhenInvalid() {

        Alias alias = new Alias(35, "Name Test", "Testing");

        Result<Alias> actual = service.add(alias);
        assertEquals(ResultType.INVALID, actual.getType());

        alias.setAliasId(0);
        alias.setName(null);
        actual = service.add(alias);
        assertEquals(ResultType.INVALID, actual.getType());

    }

    @Test
    void shouldUpdate() {
        Alias alias = new Alias(3, "Name Test", "New Persona");

        when(aliasRepository.update(alias)).thenReturn(true);
        Result<Alias> actual = service.update(alias);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }


    @Test
    void shouldNotUpdateMissing() {
        Alias alias = new Alias(35, "Name Test", "Don't Do It");

        when(aliasRepository.update(alias)).thenReturn(false);
        Result<Alias> actual = service.update(alias);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        Alias alias = new Alias(35, "Name TEST", "Invalid");

        Result<Alias> actual = service.add(alias);
        assertEquals(ResultType.INVALID, actual.getType());

        alias.setAliasId(0);
        alias.setName(null);
        actual = service.add(alias);
        assertEquals(ResultType.INVALID, actual.getType());
    }


}

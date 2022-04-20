package learn.field_agent.domain;

import learn.field_agent.data.AgencyRepository;
import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.Agency;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SecurityClearanceServiceTest {

    @Autowired
    SecurityClearanceService service;

    @MockBean
    SecurityClearanceRepository repository;

    @Test
    void shouldAdd() {
        SecurityClearance securityClearance = new SecurityClearance(0, "Name Test");
        SecurityClearance mockOut = new SecurityClearance(3, "Name Test");

        when(repository.add(securityClearance)).thenReturn(mockOut);

        Result<SecurityClearance> actual = service.add(securityClearance);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalid() {

        SecurityClearance securityClearance = new SecurityClearance(35, "Name Test");

        Result<SecurityClearance> actual = service.add(securityClearance);
        assertEquals(ResultType.INVALID, actual.getType());

        securityClearance.setSecurityClearanceId(0);
        securityClearance.setName(null);
        actual = service.add(securityClearance);
        assertEquals(ResultType.INVALID, actual.getType());

    }

    @Test
    void shouldUpdate() {
        SecurityClearance securityClearance = new SecurityClearance(3, "Name Test");

        when(repository.update(securityClearance)).thenReturn(true);
        Result<SecurityClearance> actual = service.update(securityClearance);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateMissing() {
        SecurityClearance securityClearance = new SecurityClearance(35, "Name Test");

        when(repository.update(securityClearance)).thenReturn(false);
        Result<SecurityClearance> actual = service.update(securityClearance);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        SecurityClearance securityClearance = new SecurityClearance(35, "Name TEST");

        Result<SecurityClearance> actual = service.add(securityClearance);
        assertEquals(ResultType.INVALID, actual.getType());

        securityClearance.setSecurityClearanceId(0);
        securityClearance.setName(null);
        actual = service.add(securityClearance);
        assertEquals(ResultType.INVALID, actual.getType());
    }






}

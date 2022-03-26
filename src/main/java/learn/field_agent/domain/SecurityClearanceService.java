package learn.field_agent.domain;

import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.Agent;
import learn.field_agent.models.SecurityClearance;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SecurityClearanceService {

    private final SecurityClearanceRepository repository;

    public SecurityClearanceService(SecurityClearanceRepository repository) {
        this.repository = repository;
    }

    public List<SecurityClearance> findAll() {
        return repository.findAll();
    }

    public SecurityClearance findById(int securityClearanceId) {
        return repository.findById(securityClearanceId);
    }


    public Result<SecurityClearance> add(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = validate(securityClearance);
        if (!result.isSuccess()) {
            return result;
        }

        if (SecurityClearance.getSecurityClearanceId() != 0) {  //is this true??
            result.addMessage("security clearance id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        securityClearance = repository.add(securityClearance);
        result.setPayload(securityClearance);
        return result;
    }


    public Result<SecurityClearance> update(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = validate(securityClearance);
        if (!result.isSuccess()) {
            return result;
        }

        if (SecurityClearance.getSecurityClearanceId() <= 0) {
            result.addMessage("security clearance id must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(securityClearance)) {
            String msg = String.format("security clearance id: %s, not found", SecurityClearance.getSecurityClearanceId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int securityClearanceId) {
        return repository.deleteById(securityClearanceId);
    }


    private Result<SecurityClearance> validate(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = new Result<>();
        if (securityClearance == null) {
            result.addMessage("security clearance cannot be null", ResultType.INVALID);
            return result;
        }
        if (Validations.isNullOrBlank(SecurityClearance.getName())) {
            result.addMessage("security clearance name is required", ResultType.INVALID);
        }

        //add something to alert that the name can not be duplicate
        //Retrieve existing security clearances and check to see if the security clearance to
        //add/update is in the list



        return result;
    }
}

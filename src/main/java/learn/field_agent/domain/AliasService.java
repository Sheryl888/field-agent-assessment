package learn.field_agent.domain;

import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AliasService {

    private final AliasRepository repository;
    private int aliasId;  /////////validate wanted this created////////////

    public AliasService(AliasRepository repository) {
        this.repository = repository;
    }

    public List<Alias> findAll() {
        return repository.findAll();
    }

    public Alias findById(int aliasId) {
        return repository.findById(aliasId);
    }

    public Result<Alias> add(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() != 0) {
            result.addMessage("aliasId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        alias = repository.add(alias);
        result.setPayload(alias);
        return result;
    }

    public Result<Alias> update(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() <= 0) {
            result.addMessage("aliasId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(alias)) {
            String msg = String.format("aliasId: %s, not found", alias.getAliasId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;

    }

    public boolean deleteById(int aliasId) {
        return repository.deleteById(aliasId);
    }

    private Result<Alias> validate(Alias alias) {
        Result<Alias> result = new Result<>();

        if (aliasId == ' ') {
            result.addMessage("alias_id cannot be null or empty", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(alias.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        for(Alias a : repository.findAll()) {    /////////////////////////////
            if(a.getName().equals(alias.getName())) {
                if(alias.getPersona() == null || alias.getPersona().trim().equals("")) {
                    result.addMessage("persona cannot be null if name is duplicate.", ResultType.INVALID);
                    break;
                }
                if(a.getPersona() == null) {  //might not need this code
                    continue;
                }
                if (a.getPersona().equals(alias.getPersona())) {
                    result.addMessage("name and persona cannot be the same as another alias.", ResultType.INVALID);
                    break;
                }
            }
        }
        return result;
    }
}

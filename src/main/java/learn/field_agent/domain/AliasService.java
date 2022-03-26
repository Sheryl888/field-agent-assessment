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

    /////////needs to connect to agent - alias does not stand alone//////////////
    public List<Alias> findAll() {
        return repository.findAll();
    }


    /////////needs to connect to agent - alias does not stand alone//////////////
    public Alias findById(int aliasId) {
        return repository.findById(aliasId);
    }


    /////////needs to connect to agent - alias does not stand alone//////////////
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

    /////////needs to connect to agent - alias does not stand alone//////////////
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
    /////////needs to connect to agent - alias does not stand alone//////////////
    public boolean deleteById(int aliasId) {
        return repository.deleteById(aliasId);
    }


    /////////needs to connect to agent - alias does not stand alone//////////////
    private Result<Alias> validate(Alias alias) {
        Result<Alias> result = new Result<>();

        if (aliasId == ' ') {
            result.addMessage("alias_id cannot be null or empty", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(alias.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        /////////////////Required only if name is duplicated - how to adjust code?//////////
        if (Validations.isNullOrBlank(alias.getPersona())) {
            result.addMessage("persona is required", ResultType.INVALID);
        }

        /////////////can this work? or how to connect alias to agent??/////////////////
//        if (Validations.isNullOrBlank(alias.getAgentId())) {
//            result.addMessage("agent_id is required", ResultType.INVALID);
//        }

        return result;
    }
}

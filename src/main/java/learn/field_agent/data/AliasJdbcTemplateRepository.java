package learn.field_agent.data;



import learn.field_agent.data.mappers.AgentMapper;
import learn.field_agent.data.mappers.AliasMapper;

import learn.field_agent.models.Alias;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class AliasJdbcTemplateRepository implements AliasRepository{

    private final JdbcTemplate jdbcTemplate;

    public AliasJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ////////////////////Connect to Agent - alias does not stand alone////////////////////
    @Override
    public List<Alias> findAll() {
        final String sql = "select alias_id, name, persona, agent_id "  //Do I need reference to the foreign key here?
                + "from alias limit 1000;";
        return jdbcTemplate.query(sql, new AliasMapper());
    }

    /////////needs to connect to agent - alias does not stand alone//////////////
    @Override
    @Transactional
    public Alias findById(int aliasId) {

        final String sql = "select alias_id, name, persona, agent_id "  //Do I need reference to the foreign key here?
                + "from alias "
                + "where alias_id = ?;";

        Alias alias = jdbcTemplate.query(sql, new AliasMapper(), aliasId).stream()
                .findFirst().orElse(null);

//        if (alias != null) {
//            addAgent(alias);
//        }

        return alias;
    }

    /////////needs to connect to agent - alias does not stand alone//////////////
    @Override
    public Alias add(Alias alias) {

        final String sql = "insert into alias (name, persona, agent_id) "  //Do I need reference to the foreign key here?
                + " values (?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, alias.getName());
            ps.setString(2, alias.getPersona());
//            ps.setString(2, alias.getAgentId());    //Do I need the foreign key here?
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        alias.setAliasId(keyHolder.getKey().intValue());
        return alias;
    }

    /////////needs to connect to agent - alias does not stand alone//////////////
    @Override
    public boolean update(Alias alias) {

        final String sql = "update alias set "
                + "name = ?, "
                + "persona = ?, "
//                + "agent_id = ? "   //Do I need reference to the foreign key here?
                + "where alias_id = ?;";

        return jdbcTemplate.update(sql,
                alias.getName(),
                alias.getPersona(),
//                alias.getAgentId(),  //Do I need the foreign key here?
                alias.getAliasId()) > 0;
    }

    /////////needs to connect to agent - alias does not stand alone//////////////
    @Override
    @Transactional
    public boolean deleteById(int aliasId) {
        return jdbcTemplate.update("delete from alias where alias_id = ?;", aliasId) > 0;
    }

    /////////needs to connect to agent - alias does not stand alone/////////This whole method is messed up...tried to refactor one from Agent/////
//    private void addAgent(Alias alias) {
//
//        final String sql = "select aa.alias_id, aa.name, aa.persona, aa.agent_id, "
//                + "from alias aa "
//                + "inner join agent a on aa.alias_id = a.agent_id "
//                + "where aa.alias_id = ?;";
//
//        var agent = jdbcTemplate.query(sql, new AliasMapper(), alias.getAliasId());
//        agent.setAgencies(agentAgencies);
//    }
}

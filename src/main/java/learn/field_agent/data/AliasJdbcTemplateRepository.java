package learn.field_agent.data;

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

    @Override
    public List<Alias> findAll() {
        final String sql = "select alias_id, name, persona, agent_id "
                + "from alias limit 1000;";
        return jdbcTemplate.query(sql, new AliasMapper());
    }

    @Override
    @Transactional
    public Alias findById(int aliasId) {

        final String sql = "select alias_id, name, persona, agent_id "
                + "from alias "
                + "where alias_id = ?;";

        Alias alias = jdbcTemplate.query(sql, new AliasMapper(), aliasId).stream()
                .findFirst().orElse(null);

        return alias;
    }

    @Override
    public Alias add(Alias alias) {

        final String sql = "insert into alias (name, persona, agent_id) "
                + " values (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, alias.getName());
            ps.setString(2, alias.getPersona());
            ps.setInt(3, alias.getAgentId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        alias.setAliasId(keyHolder.getKey().intValue());
        return alias;
    }


    @Override
    public boolean update(Alias alias) {

        final String sql = "update alias set "
                + "name = ?, "
                + "persona = ?, "
                + "agent_id = ? "
                + "where alias_id = ?;";

        return jdbcTemplate.update(sql,
                alias.getName(),
                alias.getPersona(),
                alias.getAgentId(),
                alias.getAliasId()) > 0;
    }


    @Override
    @Transactional
    public boolean deleteById(int aliasId) {
        return jdbcTemplate.update("delete from alias where alias_id = ?;", aliasId) > 0;
    }


    @Override
    @Transactional
    public List<Alias> getAgentAndAliasData(int agentId) {
        final String getAliasSql = "select alias_id, name, persona, agent_id "
                + "from alias "
                + "where agent_id = ?;";
        List<Alias> aliases = jdbcTemplate.query(getAliasSql, new AliasMapper(), agentId);

        return aliases;
    }
}

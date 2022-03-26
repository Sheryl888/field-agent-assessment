package learn.field_agent.data;

import learn.field_agent.data.mappers.SecurityClearanceMapper;
import learn.field_agent.models.Agency;
import learn.field_agent.models.SecurityClearance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class SecurityClearanceJdbcTemplateRepository implements SecurityClearanceRepository {

    private final JdbcTemplate jdbcTemplate;

    public SecurityClearanceJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<SecurityClearance> findAll() {
        final String sql = "select securityClearance_id, name from securityClearance limit 50;";
        return jdbcTemplate.query(sql, new SecurityClearanceMapper());
    }


    @Override  /////////this was here, is it complete?////////////
    public SecurityClearance findById(int securityClearanceId) {

        final String sql = "select security_clearance_id, name security_clearance_name "
                + "from security_clearance "
                + "where security_clearance_id = ?;";

        return jdbcTemplate.query(sql, new SecurityClearanceMapper(), securityClearanceId)
                .stream()
                .findFirst().orElse(null);
    }



    @Override
    public SecurityClearance add(SecurityClearance securityClearance) {

        final String sql = "insert into securityClearance (securityClearanceId, name) values (?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, String.valueOf(SecurityClearance.getSecurityClearanceId()));
            ps.setString(2, SecurityClearance.getName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        securityClearance.setSecurityClearanceId(keyHolder.getKey().intValue());
        return securityClearance;
    }


    @Override
    public boolean update(SecurityClearance securityClearance) {

        final String sql = "update securityClearance set "
                + "name = ? "
                + "where agency_id = ?";

        return jdbcTemplate.update(sql, SecurityClearance.getName(), SecurityClearance.getSecurityClearanceId()) > 0;
    }


    @Override //////not sure if this refactoring will work. there are only 2 SecurityClearanceIds...do we even need have a delete?
    @Transactional
    public boolean deleteById(int securityClearanceId) {
//        jdbcTemplate.update("delete from location where securityClearanceId = ?", securityClearanceId);
//        jdbcTemplate.update("delete from agency_agent where securityClearanceId = ?", securityClearanceId);
//        return jdbcTemplate.update("delete from agency where securityClearanceId = ?", securityClearanceId) > 0;
        return jdbcTemplate.update("delete from agency_agent where securityClearanceId = ?", securityClearanceId) > 0;
    }
}
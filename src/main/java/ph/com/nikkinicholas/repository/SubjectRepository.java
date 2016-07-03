package ph.com.nikkinicholas.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ph.com.nikkinicholas.domain.Subject;
import ph.com.nikkinicholas.util.datatables.DataTablesRequest;

import java.util.List;

/**
 * Created by nikkinicholas on 6/24/16.
 */
@Repository
public class SubjectRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Subject> getSubjectForDataTable(final DataTablesRequest dataTablesRequest) {
        String columnOrders = "";
        final int orderColumn = dataTablesRequest.getOrder().get(0).getColumn();
        final String orderDir = dataTablesRequest.getOrder().get(0).getDir();
        if(orderColumn == 0) {
            columnOrders = "order by date_created desc";
        } else if(orderColumn == 1) {
            columnOrders = "order by code " + orderDir;
        } else if(orderColumn == 2) {
            columnOrders = "order by title " + orderDir;
        } else if(orderColumn == 3) {
            columnOrders = "order by units " + orderDir;
        } else if(orderColumn == 4) {
            columnOrders = "order by hours " + orderDir;
        } else if(orderColumn == 5) {
            columnOrders = "order by date_created " + orderDir;
        } else if(orderColumn == 6) {
            columnOrders = "order by date_last_modified " + orderDir;
        }

        final String searchCriteria = "%" + dataTablesRequest.getSearch().getValue() + "%";
        final String SQL = "select * from subjects where is_deleted = false and (code like ? or title like ? ) " + columnOrders + " limit ? offset ?";
        return jdbcTemplate.query(SQL, new Object[] { searchCriteria, searchCriteria, dataTablesRequest.getLength(), dataTablesRequest.getStart() }, (rs, rowNumber) -> {
            Subject subject = new Subject();
            subject.setUuid(rs.getString("uuid"));
            subject.setCode(rs.getString("code"));
            subject.setTitle(rs.getString("title"));
            subject.setUnits(rs.getDouble("units"));
            subject.setHours(rs.getDouble("hours"));
            subject.setDateCreated(rs.getTimestamp("date_created"));
            subject.setDateLastModified(rs.getTimestamp("date_last_modified"));
            return subject;
        });
    }

    public int getSubjectCountBeforeFiltering() {
        final String SQL = "select count(*) from subjects where is_deleted = false";
        return jdbcTemplate.queryForObject(SQL, Integer.class);
    }

    public int getSubjectCountAfterFiltering(final DataTablesRequest dataTablesRequest) {
        final String searchCriteria = "%" + dataTablesRequest.getSearch().getValue() + "%";
        final String SQL = "select count(*) from subjects where is_deleted = false and (code like ? or title like ? )";
        return jdbcTemplate.queryForObject(SQL, new Object[] { searchCriteria, searchCriteria }, Integer.class);
    }

    public Subject getSubject(final Subject subject) {
        final String SQL = "select * from subjects where uuid = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{subject.getUuid()}, (rs, rowNum) -> {
            subject.setUuid(rs.getString("uuid"));
            subject.setCode(rs.getString("code"));
            subject.setTitle(rs.getString("title"));
            subject.setUnits(rs.getDouble("units"));
            subject.setHours(rs.getDouble("hours"));
            return subject;
        });
    }

    public void createSubject(final Subject subject) {
        final String SQL = "insert into subjects (uuid, code, title, units, hours) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(SQL, new Object[]{subject.getUuid(), subject.getCode(), subject.getTitle(), subject.getUnits(), subject.getHours()});
    }

    public void updateSubject(final Subject subject) {
        final String SQL = "update subjects set code = ?, title = ?, units = ?, hours = ?, date_last_modified = now() where uuid = ?";
        jdbcTemplate.update(SQL, new Object[]{subject.getCode(), subject.getTitle(), subject.getUnits(), subject.getHours(), subject.getUuid()});
    }

    public void deleteSubject(final Subject subject) {
        final String SQL = "update subjects set is_deleted = true, date_last_modified = now() where uuid = ?";
        jdbcTemplate.update(SQL, new Object[]{subject.getUuid()});
    }
}

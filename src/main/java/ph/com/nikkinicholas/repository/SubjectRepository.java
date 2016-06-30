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

    public List<Subject> getSubjectForDataTable(DataTablesRequest dataTablesRequest) {
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
            columnOrders = "order by date_created " + orderDir;
        } else if(orderColumn == 5) {
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
            subject.setDateCreated(rs.getTimestamp("date_created"));
            subject.setDateLastModified(rs.getTimestamp("date_last_modified"));
            return subject;
        });
    }

    public int getSubjectCountBeforeFiltering() {
        final String SQL = "select count(*) from subjects where is_deleted = false";
        return jdbcTemplate.queryForObject(SQL, Integer.class);
    }

    public int getSubjectCountAfterFiltering(DataTablesRequest dataTablesRequest) {
        final String searchCriteria = "%" + dataTablesRequest.getSearch().getValue() + "%";
        final String SQL = "select count(*) from subjects where is_deleted = false and (code like ? or title like ? )";
        return jdbcTemplate.queryForObject(SQL, new Object[] { searchCriteria, searchCriteria }, Integer.class);
    }

    public Subject getSubjectByUuid(final String uuid) {
        final String SQL = "select * from subjects where uuid = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{uuid}, (rs, rowNum) -> {
            Subject subject = new Subject();
            subject.setUuid(rs.getString("uuid"));
            subject.setCode(rs.getString("code"));
            subject.setTitle(rs.getString("title"));
            subject.setUnits(rs.getDouble("units"));
            return subject;
        });
    }

    public void createSubject(Subject subject) {
        final String SQL = "insert into subjects (uuid, code, title, units) values (?, ?, ?, ?)";
        jdbcTemplate.update(SQL, new Object[]{subject.getUuid(), subject.getCode(), subject.getTitle(), subject.getUnits()});
    }

    public boolean isCodeAlreadyExist(final String code) {
        final String SQL = "select count(*) from subjects where is_deleted = false and code = ?";
        Integer count = jdbcTemplate.queryForObject(SQL, new Object[] {code}, Integer.class);
        return count.intValue() > 0;
    }

    public void updateSubject(Subject subject) {
        final String SQL = "update subject set code = ?, title = ?, units = ? where uuid = ?";
        jdbcTemplate.update(SQL, new Object[]{subject.getCode(), subject.getTitle(), subject.getUnits(), subject.getUuid()});
    }

    public void deleteSubjectByUuid(final String uuid) {
        final String SQL = "delete from subjects where uuid = ?";
        jdbcTemplate.update(SQL, new Object[]{uuid});
    }
}

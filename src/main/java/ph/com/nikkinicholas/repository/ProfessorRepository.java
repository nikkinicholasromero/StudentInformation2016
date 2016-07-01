package ph.com.nikkinicholas.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ph.com.nikkinicholas.domain.Professor;
import ph.com.nikkinicholas.util.datatables.DataTablesRequest;

import java.util.List;

/**
 * Created by nikkinicholas on 6/24/16.
 */
@Repository
public class ProfessorRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Professor> getProfessorForDataTable(final DataTablesRequest dataTablesRequest) {
        String columnOrders = "";
        final int orderColumn = dataTablesRequest.getOrder().get(0).getColumn();
        final String orderDir = dataTablesRequest.getOrder().get(0).getDir();
        if(orderColumn == 0) {
            columnOrders = "order by date_created desc";
        } else if(orderColumn == 1) {
            columnOrders = "order by professor_number " + orderDir;
        } else if(orderColumn == 1) {
            columnOrders = "order by first_name " + orderDir;
        } else if(orderColumn == 2) {
            columnOrders = "order by middle_name " + orderDir;
        } else if(orderColumn == 3) {
            columnOrders = "order by last_name " + orderDir;
        } else if(orderColumn == 4) {
            columnOrders = "order by gender " + orderDir;
        } else if(orderColumn == 5) {
            columnOrders = "order by birth_date " + orderDir;
        } else if(orderColumn == 6) {
            columnOrders = "order by street_address " + orderDir;
        } else if(orderColumn == 7) {
            columnOrders = "order by city_address " + orderDir;
        } else if(orderColumn == 8) {
            columnOrders = "order by province_address " + orderDir;
        } else if(orderColumn == 9) {
            columnOrders = "order by date_created " + orderDir;
        } else if(orderColumn == 10) {
            columnOrders = "order by date_last_modified " + orderDir;
        }

        final String searchCriteria = "%" + dataTablesRequest.getSearch().getValue() + "%";
        final String SQL = "select * from professors where is_deleted = false and professor_number like ? " + columnOrders + " limit ? offset ?";
        return jdbcTemplate.query(SQL, new Object[] { searchCriteria, dataTablesRequest.getLength(), dataTablesRequest.getStart() }, (rs, rowNumber) -> {
            Professor professor = new Professor();
            professor.setUuid(rs.getString("uuid"));
            professor.setProfessorNumber(rs.getString("professor_number"));
            professor.setFirstName(rs.getString("first_name"));
            professor.setMiddleName(rs.getString("middle_name"));
            professor.setLastName(rs.getString("last_name"));
            professor.setGender(rs.getString("gender"));
            professor.setBirthDate(rs.getDate("birth_date"));
            professor.setStreetAddress(rs.getString("street_address"));
            professor.setCityAddress(rs.getString("city_address"));
            professor.setProvinceAddress(rs.getString("province_address"));
            professor.setDateCreated(rs.getTimestamp("date_created"));
            professor.setDateLastModified(rs.getTimestamp("date_last_modified"));
            return professor;
        });
    }

    public int getProfessorCountBeforeFiltering() {
        final String SQL = "select count(*) from professors where is_deleted = false";
        return jdbcTemplate.queryForObject(SQL, Integer.class);
    }

    public int getProfessorCountAfterFiltering(final DataTablesRequest dataTablesRequest) {
        final String searchCriteria = "%" + dataTablesRequest.getSearch().getValue() + "%";
        final String SQL = "select count(*) from professors where is_deleted = false and professor_number like ?";
        return jdbcTemplate.queryForObject(SQL, new Object[] { searchCriteria }, Integer.class);
    }

    public Professor getProfessor(final Professor professor) {
        final String SQL = "select * from professors where uuid = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{professor.getUuid()}, (rs, rowNum) -> {
            professor.setUuid(rs.getString("uuid"));
            professor.setProfessorNumber(rs.getString("professor_number"));
            professor.setFirstName(rs.getString("first_name"));
            professor.setMiddleName(rs.getString("middle_name"));
            professor.setLastName(rs.getString("last_name"));
            professor.setGender(rs.getString("gender"));
            professor.setBirthDate(rs.getDate("birth_date"));
            professor.setStreetAddress(rs.getString("street_address"));
            professor.setCityAddress(rs.getString("city_address"));
            professor.setProvinceAddress(rs.getString("province_address"));
            return professor;
        });
    }

    public void createProfessor(final Professor professor) {
        final String SQL = "insert into professors (uuid, professor_number, first_name, middle_name, last_name, gender, birth_date, street_address, city_address, province_address) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(SQL, new Object[]{professor.getUuid(), professor.getProfessorNumber(), professor.getFirstName(), professor.getMiddleName(), professor.getLastName(), professor.getGender(), professor.getBirthDate(), professor.getStreetAddress(), professor.getCityAddress(), professor.getProvinceAddress()});
    }

    public void updateProfessor(final Professor professor) {
        final String SQL = "update professors set professor_number = ?, first_name = ?, middle_name = ?, last_name = ?, gender = ?, birth_date = ?, street_address = ?, city_address = ?, province_address = ? where uuid = ?";
        jdbcTemplate.update(SQL, new Object[]{professor.getProfessorNumber(), professor.getFirstName(), professor.getMiddleName(), professor.getLastName(), professor.getGender(), professor.getBirthDate(), professor.getStreetAddress(), professor.getCityAddress(), professor.getProvinceAddress(), professor.getUuid()});
    }

    public void deleteProfessor(final Professor professor) {
        final String SQL = "delete from professors where uuid = ?";
        jdbcTemplate.update(SQL, new Object[]{professor.getUuid()});
    }
}

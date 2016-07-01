package ph.com.nikkinicholas.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ph.com.nikkinicholas.domain.Student;
import ph.com.nikkinicholas.util.datatables.DataTablesRequest;

import java.util.List;

/**
 * Created by nikkinicholas on 6/24/16.
 */
@Repository
public class StudentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Student> getStudentForDataTable(final DataTablesRequest dataTablesRequest) {
        String columnOrders = "";
        final int orderColumn = dataTablesRequest.getOrder().get(0).getColumn();
        final String orderDir = dataTablesRequest.getOrder().get(0).getDir();
        if(orderColumn == 0) {
            columnOrders = "order by date_created desc";
        } else if(orderColumn == 1) {
            columnOrders = "order by student_number " + orderDir;
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
        final String SQL = "select * from students where is_deleted = false and student_number like ? " + columnOrders + " limit ? offset ?";
        return jdbcTemplate.query(SQL, new Object[] { searchCriteria, dataTablesRequest.getLength(), dataTablesRequest.getStart() }, (rs, rowNumber) -> {
            Student student = new Student();
            student.setUuid(rs.getString("uuid"));
            student.setStudentNumber(rs.getString("student_number"));
            student.setFirstName(rs.getString("first_name"));
            student.setMiddleName(rs.getString("middle_name"));
            student.setLastName(rs.getString("last_name"));
            student.setGender(rs.getString("gender"));
            student.setBirthDate(rs.getDate("birth_date"));
            student.setStreetAddress(rs.getString("street_address"));
            student.setCityAddress(rs.getString("city_address"));
            student.setProvinceAddress(rs.getString("province_address"));
            student.setDateCreated(rs.getTimestamp("date_created"));
            student.setDateLastModified(rs.getTimestamp("date_last_modified"));
            return student;
        });
    }

    public int getStudentCountBeforeFiltering() {
        final String SQL = "select count(*) from students where is_deleted = false";
        return jdbcTemplate.queryForObject(SQL, Integer.class);
    }

    public int getStudentCountAfterFiltering(final DataTablesRequest dataTablesRequest) {
        final String searchCriteria = "%" + dataTablesRequest.getSearch().getValue() + "%";
        final String SQL = "select count(*) from students where is_deleted = false and student_number like ?";
        return jdbcTemplate.queryForObject(SQL, new Object[] { searchCriteria }, Integer.class);
    }

    public Student getStudent(final Student student) {
        final String SQL = "select * from students where uuid = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{student.getUuid()}, (rs, rowNum) -> {
            student.setUuid(rs.getString("uuid"));
            student.setStudentNumber(rs.getString("student_number"));
            student.setFirstName(rs.getString("first_name"));
            student.setMiddleName(rs.getString("middle_name"));
            student.setLastName(rs.getString("last_name"));
            student.setGender(rs.getString("gender"));
            student.setBirthDate(rs.getDate("birth_date"));
            student.setStreetAddress(rs.getString("street_address"));
            student.setCityAddress(rs.getString("city_address"));
            student.setProvinceAddress(rs.getString("province_address"));
            return student;
        });
    }

    public void createStudent(final Student student) {
        final String SQL = "insert into students (uuid, student_number, first_name, middle_name, last_name, gender, birth_date, street_address, city_address, province_address) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(SQL, new Object[]{student.getUuid(), student.getStudentNumber(), student.getFirstName(), student.getMiddleName(), student.getLastName(), student.getGender(), student.getBirthDate(), student.getStreetAddress(), student.getCityAddress(), student.getProvinceAddress()});
    }

    public void updateStudent(final Student student) {
        final String SQL = "update students set student_number = ?, first_name = ?, middle_name = ?, last_name = ?, gender = ?, birth_date = ?, street_address = ?, city_address = ?, province_address = ? where uuid = ?";
        jdbcTemplate.update(SQL, new Object[]{student.getStudentNumber(), student.getFirstName(), student.getMiddleName(), student.getLastName(), student.getGender(), student.getBirthDate(), student.getStreetAddress(), student.getCityAddress(), student.getProvinceAddress(), student.getUuid()});
    }

    public void deleteStudent(final Student student) {
        final String SQL = "delete from students where uuid = ?";
        jdbcTemplate.update(SQL, new Object[]{student.getUuid()});
    }
}

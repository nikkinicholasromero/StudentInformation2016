package ph.com.nikkinicholas.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ph.com.nikkinicholas.domain.Room;
import ph.com.nikkinicholas.util.datatables.DataTablesRequest;

import java.util.List;

/**
 * Created by nikkinicholas on 6/24/16.
 */
@Repository
public class RoomRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Room> getRoomForDataTable(DataTablesRequest dataTablesRequest) {
        String columnOrders = "";
        final int orderColumn = dataTablesRequest.getOrder().get(0).getColumn();
        final String orderDir = dataTablesRequest.getOrder().get(0).getDir();
        if(orderColumn == 0) {
            columnOrders = "order by date_created desc";
        } else if(orderColumn == 1) {
            columnOrders = "order by room_number " + orderDir;
        } else if(orderColumn == 2) {
            columnOrders = "order by date_created " + orderDir;
        } else if(orderColumn == 3) {
            columnOrders = "order by date_last_modified " + orderDir;
        }

        final String searchCriteria = "%" + dataTablesRequest.getSearch().getValue() + "%";
        final String SQL = "select * from rooms where is_deleted = false and room_number like ? " + columnOrders + " limit ? offset ?";
        return jdbcTemplate.query(SQL, new Object[] { searchCriteria, dataTablesRequest.getLength(), dataTablesRequest.getStart() }, (rs, rowNumber) -> {
            Room room = new Room();
            room.setUuid(rs.getString("uuid"));
            room.setRoomNumber(rs.getString("room_number"));
            room.setDateCreated(rs.getTimestamp("date_created"));
            room.setDateLastModified(rs.getTimestamp("date_last_modified"));
            return room;
        });
    }

    public int getRoomCountBeforeFiltering() {
        final String SQL = "select count(*) from rooms where is_deleted = false";
        return jdbcTemplate.queryForObject(SQL, Integer.class);
    }

    public int getRoomCountAfterFiltering(DataTablesRequest dataTablesRequest) {
        final String searchCriteria = "%" + dataTablesRequest.getSearch().getValue() + "%";
        final String SQL = "select count(*) from rooms where is_deleted = false and room_number like ?";
        return jdbcTemplate.queryForObject(SQL, new Object[] { searchCriteria }, Integer.class);
    }

    public Room getRoomByUuid(final String uuid) {
        final String SQL = "select * from rooms where uuid = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{uuid}, (rs, rowNum) -> {
            Room room = new Room();
            room.setUuid(rs.getString("uuid"));
            room.setRoomNumber(rs.getString("room_number"));
            room.setDateCreated(rs.getTimestamp("date_created"));
            room.setDateLastModified(rs.getTimestamp("date_last_modified"));
            return room;
        });
    }

    public void createRoom(Room room) {
        final String SQL = "insert into rooms (uuid, room_number) values (?, ?)";
        jdbcTemplate.update(SQL, new Object[]{room.getUuid(), room.getRoomNumber()});
    }

    public boolean isRoomNumberAlreadyExist(final String roomNumber) {
        final String SQL = "select count(*) from rooms where is_deleted = false and room_number = ?";
        Integer count = jdbcTemplate.queryForObject(SQL, new Object[] {roomNumber}, Integer.class);
        return count.intValue() > 0;
    }

    public void updateRoom(Room room) {
        final String SQL = "update rooms set room_number = ? where uuid = ?";
        jdbcTemplate.update(SQL, new Object[]{room.getRoomNumber(), room.getUuid()});
    }

    public void deleteRoomByUuid(final String uuid) {
        final String SQL = "delete from rooms where uuid = ?";
        jdbcTemplate.update(SQL, new Object[]{uuid});
    }
}

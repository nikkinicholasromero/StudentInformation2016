package ph.com.nikkinicholas.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.com.nikkinicholas.domain.Room;
import ph.com.nikkinicholas.repository.RoomRepository;
import ph.com.nikkinicholas.util.datatables.DataTablesRequest;
import ph.com.nikkinicholas.util.validation.ErrorCode;
import ph.com.nikkinicholas.util.validation.Status;
import ph.com.nikkinicholas.util.validation.ValidationResult;

import java.util.List;
import java.util.UUID;

/**
 * Created by nikkinicholas on 6/24/16.
 */
@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getRoomForDataTable(DataTablesRequest dataTablesRequest) {
        return roomRepository.getRoomForDataTable(dataTablesRequest);
    }

    public int getRoomCountBeforeFiltering() {
        return roomRepository.getRoomCountBeforeFiltering();
    }

    public int getRoomCountAfterFiltering(DataTablesRequest dataTablesRequest) {
        return roomRepository.getRoomCountAfterFiltering(dataTablesRequest);
    }

    public Room getRoomByUuid(final String uuid) {
        return roomRepository.getRoomByUuid(uuid);
    }

    public ValidationResult createRoom(Room room) {
        ValidationResult validationResult = validateCreateRoom(room);
        if(validationResult.getStatus() == Status.SUCCESS) {
            room.setUuid(UUID.randomUUID().toString());
            roomRepository.createRoom(room);
        }
        return validationResult;
    }

    public void updateRoom(Room room) {
        roomRepository.updateRoom(room);
    }

    public void deleteRoomByUuid(final String uuid) {
        roomRepository.deleteRoomByUuid(uuid);
    }

    private ValidationResult validateCreateRoom(Room room) {
        ValidationResult validationResult = new ValidationResult();

        if(StringUtils.isEmpty(room.getRoomNumber())) {
            validationResult.addErrorCode(ErrorCode.ROOM_ROOM_NUMBER_MISSING);
        } else if(isRoomNumberAlreadyExist(room.getRoomNumber())) {
            validationResult.addErrorCode(ErrorCode.ROOM_ROOM_NUMBER_DUPLICATE);
        } else if(room.getRoomNumber().length() > 150) {
            validationResult.addErrorCode(ErrorCode.ROOM_ROOM_NUMBER_TOO_LONG);
        }

        return validationResult;
    }

    private boolean isRoomNumberAlreadyExist(final String roomNumber) {
        return roomRepository.isRoomNumberAlreadyExist(roomNumber);
    }
}

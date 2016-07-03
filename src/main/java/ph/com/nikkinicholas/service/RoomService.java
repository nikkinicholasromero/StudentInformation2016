package ph.com.nikkinicholas.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

    public List<Room> getRoomForDataTable(final DataTablesRequest dataTablesRequest) {
        return roomRepository.getRoomForDataTable(dataTablesRequest);
    }

    public int getRoomCountBeforeFiltering() {
        return roomRepository.getRoomCountBeforeFiltering();
    }

    public int getRoomCountAfterFiltering(final DataTablesRequest dataTablesRequest) {
        return roomRepository.getRoomCountAfterFiltering(dataTablesRequest);
    }

    public Room getRoom(Room room) {
        return roomRepository.getRoom(room);
    }

    public ValidationResult createRoom(final Room room) {
        ValidationResult validationResult = validateRoom(room);
        if(validationResult.getStatus() == Status.SUCCESS) {
            try {
                room.setUuid(UUID.randomUUID().toString());
                roomRepository.createRoom(room);
            } catch (DuplicateKeyException e) {
                validationResult.addErrorCode(ErrorCode.ROOM_ROOM_NUMBER_DUPLICATE);
            }
        }
        return validationResult;
    }

    public ValidationResult updateRoom(final Room room) {
        ValidationResult validationResult = validateRoom(room);
        if(validationResult.getStatus() == Status.SUCCESS) {
            try {
                roomRepository.updateRoom(room);
            } catch (DuplicateKeyException e) {
                validationResult.addErrorCode(ErrorCode.ROOM_ROOM_NUMBER_DUPLICATE);
            }
        }
        return validationResult;
    }

    public void deleteRoom(final Room room) {
        roomRepository.deleteRoom(room);
    }

    private ValidationResult validateRoom(final Room room) {
        ValidationResult validationResult = new ValidationResult();

        if(StringUtils.isEmpty(room.getRoomNumber())) {
            validationResult.addErrorCode(ErrorCode.ROOM_ROOM_NUMBER_MISSING);
        } else if(room.getRoomNumber().length() > 150) {
            validationResult.addErrorCode(ErrorCode.ROOM_ROOM_NUMBER_TOO_LONG);
        }

        return validationResult;
    }
}

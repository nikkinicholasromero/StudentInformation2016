package ph.com.nikkinicholas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ph.com.nikkinicholas.domain.Room;
import ph.com.nikkinicholas.service.RoomService;
import ph.com.nikkinicholas.util.datatables.DataTablesRequest;
import ph.com.nikkinicholas.util.datatables.DataTablesResponse;
import ph.com.nikkinicholas.util.validation.ValidationResult;

import java.util.List;


/**
 * Created by nikkinicholas on 6/24/16.
 */
@Controller
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @RequestMapping({"", "/"})
    public String rooms() {
        return "rooms";
    }

    @RequestMapping(value = "/getRoomForDataTable", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody DataTablesResponse<Room> getRoomForDataTable(@RequestBody DataTablesRequest dataTablesRequest) {
        DataTablesResponse<Room> dataTablesResponse = new DataTablesResponse<>();

        final List<Room> rooms = roomService.getRoomForDataTable(dataTablesRequest);
        final int recordsTotal = roomService.getRoomCountBeforeFiltering();
        final int recordsFiltered = roomService.getRoomCountAfterFiltering(dataTablesRequest);
        
        dataTablesResponse.setDraw(dataTablesRequest.getDraw());
        dataTablesResponse.setData(rooms);
        dataTablesResponse.setRecordsTotal(recordsTotal);
        dataTablesResponse.setRecordsFiltered(recordsFiltered);

        return dataTablesResponse;
    }

    @RequestMapping(value = "/createRoom", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ValidationResult createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @RequestMapping(value = "/updateRoom", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ValidationResult updateRoom(@RequestBody Room room) {
        return roomService.updateRoom(room);
    }

    @RequestMapping(value = "/deleteRoom", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteRoom(@RequestBody Room room) {
        roomService.deleteRoom(room);
    }
}
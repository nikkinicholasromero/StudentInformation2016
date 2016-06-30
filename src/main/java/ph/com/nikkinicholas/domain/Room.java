package ph.com.nikkinicholas.domain;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by nikkinicholas on 6/24/16.
 */
public class Room {
    private String uuid = "";
    private String roomNumber = "";
    @JsonFormat(pattern  = "yyyy-MM-dd' - 'hh:mm:ss")
    private Date dateCreated = new Date();
    @JsonFormat(pattern  = "yyyy-MM-dd' - 'hh:mm:ss")
    private Date dateLastModified = new Date();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(Date dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    @Override
    public String toString() {
        return "Room{" +
                "uuid='" + uuid + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateLastModified=" + dateLastModified +
                '}';
    }
}

package ph.com.nikkinicholas.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by nikkinicholas on 6/24/16.
 */
public class Subject {
    private String uuid = "";
    private String code = "";
    private String title = "";
    private double units = 0.0;
    private double hours = 0.0;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
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
        return "Subject{" +
                "uuid='" + uuid + '\'' +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", units=" + units +
                ", hours=" + hours +
                ", dateCreated=" + dateCreated +
                ", dateLastModified=" + dateLastModified +
                '}';
    }
}

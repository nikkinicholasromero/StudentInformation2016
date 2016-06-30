package ph.com.nikkinicholas.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by nikkinicholas on 6/24/16.
 */
public class Professor {
    private String uuid = "";
    private String professorNumber = "";
    private String firstName = "";
    private String middleName = "";
    private String lastName = "";
    private String gender = "";
    @JsonFormat(pattern  = "yyyy-MM-dd")
    private Date birthDate = new Date();
    private String streetAddress  = "";
    private String cityAddress = "";
    private String provinceAddress = "";
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

    public String getProfessorNumber() {
        return professorNumber;
    }

    public void setProfessorNumber(String professorNumber) {
        this.professorNumber = professorNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public String getProvinceAddress() {
        return provinceAddress;
    }

    public void setProvinceAddress(String provinceAddress) {
        this.provinceAddress = provinceAddress;
    }

    public java.util.Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(java.util.Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public java.util.Date getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(java.util.Date dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "uuid='" + uuid + '\'' +
                ", professorNumber='" + professorNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", streetAddress='" + streetAddress + '\'' +
                ", cityAddress='" + cityAddress + '\'' +
                ", provinceAddress='" + provinceAddress + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateLastModified=" + dateLastModified +
                '}';
    }
}

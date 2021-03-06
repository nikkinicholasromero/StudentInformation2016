package ph.com.nikkinicholas.util.validation;

/**
 * Created by nikkinicholas on 6/30/16.
 */
public enum ErrorCode {
    STUDENT_STUDENT_NUMBER_MISSING("STUDENT_STUDENT_NUMBER_MISSING", "Student number is missing"),
    STUDENT_STUDENT_NUMBER_DUPLICATE("STUDENT_STUDENT_NUMBER_DUPLICATE", "Student number duplicate"),
    STUDENT_STUDENT_NUMBER_TOO_LONG("STUDENT_STUDENT_NUMBER_TOO_LONG", "Student number too long"),
    STUDENT_FIRST_NAME_MISSING("STUDENT_FIRST_NAME_MISSING", "First name is missing"),
    STUDENT_FIRST_NAME_TOO_LONG("STUDENT_FIRST_NAME_TOO_LONG", "First name too long"),
    STUDENT_MIDDLE_NAME_MISSING("STUDENT_MIDDLE_NAME_MISSING", "Middle name is missing"),
    STUDENT_MIDDLE_NAME_TOO_LONG("STUDENT_MIDDLE_NAME_TOO_LONG", "Middle name too long"),
    STUDENT_LAST_NAME_MISSING("STUDENT_LAST_NAME_MISSING", "Last name is missing"),
    STUDENT_LAST_NAME_TOO_LONG("STUDENT_LAST_NAME_TOO_LONG", "Last name too long"),
    STUDENT_GENDER_MISSING("STUDENT_GENDER_MISSING", "Gender is missing"),
    STUDENT_GENDER_INVALID("STUDENT_GENDER_INVALID", "Gender has invalid value"),
    STUDENT_BIRTH_DATE_MISSING("STUDENT_BIRTH_DATE_MISSING", "Birth date is missing"),
    STUDENT_STREET_ADDRESS_DATE_MISSING("STUDENT_STREET_ADDRESS_DATE_MISSING", "Street address is missing"),
    STUDENT_STREET_ADDRESS_DATE_TOO_LONG("STUDENT_STREET_ADDRESS_DATE_TOO_LONG", "Street address too long"),
    STUDENT_CITY_ADDRESS_DATE_MISSING("STUDENT_CITY_ADDRESS_DATE_MISSING", "City address is missing"),
    STUDENT_CITY_ADDRESS_DATE_TOO_LONG("STUDENT_CITY_ADDRESS_DATE_TOO_LONG", "City address too long"),
    STUDENT_PROVINCE_ADDRESS_DATE_MISSING("STUDENT_PROVINCE_ADDRESS_DATE_MISSING", "Province address is missing"),
    STUDENT_PROVINCE_ADDRESS_DATE_TOO_LONG("STUDENT_PROVINCE_ADDRESS_DATE_TOO_LONG", "Province address too long"),

    PROFESSOR_PROFESSOR_NUMBER_MISSING("PROFESSOR_PROFESSOR_NUMBER_MISSING", "Professor number is missing"),
    PROFESSOR_PROFESSOR_NUMBER_DUPLICATE("PROFESSOR_PROFESSOR_NUMBER_DUPLICATE", "Professor number duplicate"),
    PROFESSOR_PROFESSOR_NUMBER_TOO_LONG("PROFESSOR_PROFESSOR_NUMBER_TOO_LONG", "Professor number too long"),
    PROFESSOR_FIRST_NAME_MISSING("PROFESSOR_FIRST_NAME_MISSING", "First name is missing"),
    PROFESSOR_FIRST_NAME_TOO_LONG("PROFESSOR_FIRST_NAME_TOO_LONG", "First name too long"),
    PROFESSOR_MIDDLE_NAME_MISSING("PROFESSOR_MIDDLE_NAME_MISSING", "Middle name is missing"),
    PROFESSOR_MIDDLE_NAME_TOO_LONG("PROFESSOR_MIDDLE_NAME_TOO_LONG", "Middle name too long"),
    PROFESSOR_LAST_NAME_MISSING("PROFESSOR_LAST_NAME_MISSING", "Last name is missing"),
    PROFESSOR_LAST_NAME_TOO_LONG("PROFESSOR_LAST_NAME_TOO_LONG", "Last name too long"),
    PROFESSOR_GENDER_MISSING("PROFESSOR_GENDER_MISSING", "Gender is missing"),
    PROFESSOR_GENDER_INVALID("PROFESSOR_GENDER_INVALID", "Gender has invalid value"),
    PROFESSOR_BIRTH_DATE_MISSING("PROFESSOR_BIRTH_DATE_MISSING", "Birth date is missing"),
    PROFESSOR_STREET_ADDRESS_DATE_MISSING("PROFESSOR_STREET_ADDRESS_DATE_MISSING", "Street address is missing"),
    PROFESSOR_STREET_ADDRESS_DATE_TOO_LONG("PROFESSOR_STREET_ADDRESS_DATE_TOO_LONG", "Street address too long"),
    PROFESSOR_CITY_ADDRESS_DATE_MISSING("PROFESSOR_CITY_ADDRESS_DATE_MISSING", "City address is missing"),
    PROFESSOR_CITY_ADDRESS_DATE_TOO_LONG("PROFESSOR_CITY_ADDRESS_DATE_TOO_LONG", "City address too long"),
    PROFESSOR_PROVINCE_ADDRESS_DATE_MISSING("PROFESSOR_PROVINCE_ADDRESS_DATE_MISSING", "Province address is missing"),
    PROFESSOR_PROVINCE_ADDRESS_DATE_TOO_LONG("PROFESSOR_PROVINCE_ADDRESS_DATE_TOO_LONG", "Province address too long"),

    SUBJECT_CODE_MISSING("SUBJECT_CODE_MISSING", "Code is missing"),
    SUBJECT_CODE_DUPLICATE("SUBJECT_CODE_DUPLICATE", "Code duplicate"),
    SUBJECT_CODE_TOO_LONG("SUBJECT_CODE_TOO_LONG", "Code too long"),
    SUBJECT_TITLE_MISSING("SUBJECT_TITLE_MISSING", "Title is missing"),
    SUBJECT_TITLE_TOO_LONG("SUBJECT_TITLE_TOO_LONG", "Title too long"),
    SUBJECT_UNITS_MISSING("SUBJECT_UNITS_MISSING", "Units is missing"),
    SUBJECT_UNITS_INVALID("SUBJECT_UNITS_INVALID", "Units has invalid value"),
    SUBJECT_HOURS_MISSING("SUBJECT_HOURS_MISSING", "Hours is missing"),
    SUBJECT_HOURS_INVALID("SUBJECT_HOURS_INVALID", "Hours has invalid value"),

    ROOM_ROOM_NUMBER_MISSING("ROOM_ROOM_NUMBER_MISSING", "Room number is missing"),
    ROOM_ROOM_NUMBER_DUPLICATE("ROOM_ROOM_NUMBER_DUPLICATE", "Room number duplicate"),
    ROOM_ROOM_NUMBER_TOO_LONG("ROOM_ROOM_NUMBER_TOO_LONG", "Room number too long");

    private String code;
    private String message;

    ErrorCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

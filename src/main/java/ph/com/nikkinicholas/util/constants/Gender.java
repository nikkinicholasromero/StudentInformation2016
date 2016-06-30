package ph.com.nikkinicholas.util.constants;

/**
 * Created by nikkinicholas on 7/1/16.
 */
public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    public String gender;

    Gender(final String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return gender;
    }
}
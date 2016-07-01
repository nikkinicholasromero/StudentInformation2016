package ph.com.nikkinicholas.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ph.com.nikkinicholas.domain.Student;
import ph.com.nikkinicholas.repository.StudentRepository;
import ph.com.nikkinicholas.util.constants.Gender;
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
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getStudentForDataTable(final DataTablesRequest dataTablesRequest) {
        return studentRepository.getStudentForDataTable(dataTablesRequest);
    }

    public int getStudentCountBeforeFiltering() {
        return studentRepository.getStudentCountBeforeFiltering();
    }

    public int getStudentCountAfterFiltering(final DataTablesRequest dataTablesRequest) {
        return studentRepository.getStudentCountAfterFiltering(dataTablesRequest);
    }

    public Student getStudent(final Student student) {
        return studentRepository.getStudent(student);
    }

    public ValidationResult createStudent(final Student student) {
        ValidationResult validationResult = validateStudent(student);
        if(validationResult.getStatus() == Status.SUCCESS) {
            try {
                student.setUuid(UUID.randomUUID().toString());
                studentRepository.createStudent(student);
            } catch (DuplicateKeyException e) {
                validationResult.addErrorCode(ErrorCode.STUDENT_STUDENT_NUMBER_DUPLICATE);
            }
        }
        return validationResult;
    }

    public ValidationResult updateStudent(final Student student) {
        ValidationResult validationResult = validateStudent(student);
        if(validationResult.getStatus() == Status.SUCCESS) {
            try {
                studentRepository.updateStudent(student);
            } catch (DuplicateKeyException e) {
                validationResult.addErrorCode(ErrorCode.STUDENT_STUDENT_NUMBER_DUPLICATE);
            }
        }
        return validationResult;
    }

    public void deleteStudent(final Student student) {
        studentRepository.deleteStudent(student);
    }

    private ValidationResult validateStudent(final Student student) {
        ValidationResult validationResult = new ValidationResult();

        if(StringUtils.isEmpty(student.getStudentNumber())) {
            validationResult.addErrorCode(ErrorCode.STUDENT_STUDENT_NUMBER_MISSING);
        } else if(student.getStudentNumber().length() > 15) {
            validationResult.addErrorCode(ErrorCode.STUDENT_STUDENT_NUMBER_TOO_LONG);
        }

        if(StringUtils.isEmpty(student.getFirstName())) {
            validationResult.addErrorCode(ErrorCode.STUDENT_FIRST_NAME_MISSING);
        } else if(student.getFirstName().length() > 50) {
            validationResult.addErrorCode(ErrorCode.STUDENT_FIRST_NAME_TOO_LONG);
        }

        if(StringUtils.isEmpty(student.getMiddleName())) {
            validationResult.addErrorCode(ErrorCode.STUDENT_MIDDLE_NAME_MISSING);
        } else if(student.getMiddleName().length() > 50) {
            validationResult.addErrorCode(ErrorCode.STUDENT_MIDDLE_NAME_TOO_LONG);
        }

        if(StringUtils.isEmpty(student.getLastName())) {
            validationResult.addErrorCode(ErrorCode.STUDENT_LAST_NAME_MISSING);
        } else if(student.getLastName().length() > 50) {
            validationResult.addErrorCode(ErrorCode.STUDENT_LAST_NAME_TOO_LONG);
        }

        if(StringUtils.isEmpty(student.getGender())) {
            validationResult.addErrorCode(ErrorCode.STUDENT_GENDER_MISSING);
        } else if(!Gender.MALE.toString().equals(student.getGender()) && !Gender.FEMALE.toString().equals(student.getGender())) {
            validationResult.addErrorCode(ErrorCode.STUDENT_GENDER_INVALID);
        }

        if(student.getBirthDate() == null) {
            validationResult.addErrorCode(ErrorCode.STUDENT_BIRTH_DATE_MISSING);
        }

        if(StringUtils.isEmpty(student.getStreetAddress())) {
            validationResult.addErrorCode(ErrorCode.STUDENT_STREET_ADDRESS_DATE_MISSING);
        } else if(student.getStreetAddress().length() > 150) {
            validationResult.addErrorCode(ErrorCode.STUDENT_STREET_ADDRESS_DATE_TOO_LONG);
        }

        if(StringUtils.isEmpty(student.getCityAddress())) {
            validationResult.addErrorCode(ErrorCode.STUDENT_CITY_ADDRESS_DATE_MISSING);
        } else if(student.getCityAddress().length() > 100) {
            validationResult.addErrorCode(ErrorCode.STUDENT_CITY_ADDRESS_DATE_TOO_LONG);
        }

        if(StringUtils.isEmpty(student.getProvinceAddress())) {
            validationResult.addErrorCode(ErrorCode.STUDENT_PROVINCE_ADDRESS_DATE_MISSING);
        } else if(student.getProvinceAddress().length() > 100) {
            validationResult.addErrorCode(ErrorCode.STUDENT_PROVINCE_ADDRESS_DATE_TOO_LONG);
        }

        return validationResult;
    }
}

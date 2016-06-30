package ph.com.nikkinicholas.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.com.nikkinicholas.domain.Professor;
import ph.com.nikkinicholas.repository.ProfessorRepository;
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
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    public List<Professor> getProfessorForDataTable(DataTablesRequest dataTablesRequest) {
        return professorRepository.getProfessorForDataTable(dataTablesRequest);
    }

    public int getProfessorCountBeforeFiltering() {
        return professorRepository.getProfessorCountBeforeFiltering();
    }

    public int getProfessorCountAfterFiltering(DataTablesRequest dataTablesRequest) {
        return professorRepository.getProfessorCountAfterFiltering(dataTablesRequest);
    }

    public Professor getProfessorByUuid(final String uuid) {
        return professorRepository.getProfessorByUuid(uuid);
    }

    public ValidationResult createProfessor(Professor professor) {
        ValidationResult validationResult = validateCreateProfessor(professor);
        if(validationResult.getStatus() == Status.SUCCESS) {
            professor.setUuid(UUID.randomUUID().toString());
            professorRepository.createProfessor(professor);
        }
        return validationResult;
    }

    public void updateProfessor(Professor professor) {
        professorRepository.updateProfessor(professor);
    }

    public void deleteProfessorByUuid(final String uuid) {
        professorRepository.deleteProfessorByUuid(uuid);
    }

    private ValidationResult validateCreateProfessor(Professor professor) {
        ValidationResult validationResult = new ValidationResult();

        if(StringUtils.isEmpty(professor.getProfessorNumber())) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_PROFESSOR_NUMBER_MISSING);
        } else if(isProfessorNumberAlreadyExist(professor.getProfessorNumber())) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_PROFESSOR_NUMBER_DUPLICATE);
        } else if(professor.getProfessorNumber().length() > 15) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_PROFESSOR_NUMBER_TOO_LONG);
        }

        if(StringUtils.isEmpty(professor.getFirstName())) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_FIRST_NAME_MISSING);
        } else if(professor.getFirstName().length() > 50) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_FIRST_NAME_TOO_LONG);
        }

        if(StringUtils.isEmpty(professor.getMiddleName())) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_MIDDLE_NAME_MISSING);
        } else if(professor.getMiddleName().length() > 50) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_MIDDLE_NAME_TOO_LONG);
        }

        if(StringUtils.isEmpty(professor.getLastName())) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_LAST_NAME_MISSING);
        } else if(professor.getLastName().length() > 50) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_LAST_NAME_TOO_LONG);
        }

        if(StringUtils.isEmpty(professor.getGender())) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_GENDER_MISSING);
        } else if(!Gender.MALE.toString().equals(professor.getGender()) && !Gender.FEMALE.toString().equals(professor.getGender())) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_GENDER_INVALID);
        }

        if(professor.getBirthDate() == null) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_BIRTH_DATE_MISSING);
        }

        if(StringUtils.isEmpty(professor.getStreetAddress())) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_STREET_ADDRESS_DATE_MISSING);
        } else if(professor.getStreetAddress().length() > 150) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_STREET_ADDRESS_DATE_TOO_LONG);
        }

        if(StringUtils.isEmpty(professor.getCityAddress())) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_CITY_ADDRESS_DATE_MISSING);
        } else if(professor.getCityAddress().length() > 100) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_CITY_ADDRESS_DATE_TOO_LONG);
        }

        if(StringUtils.isEmpty(professor.getProvinceAddress())) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_PROVINCE_ADDRESS_DATE_MISSING);
        } else if(professor.getProvinceAddress().length() > 100) {
            validationResult.addErrorCode(ErrorCode.PROFESSOR_PROVINCE_ADDRESS_DATE_TOO_LONG);
        }

        return validationResult;
    }

    private boolean isProfessorNumberAlreadyExist(final String professorNumber) {
        return professorRepository.isProfessorNumberAlreadyExist(professorNumber);
    }
}

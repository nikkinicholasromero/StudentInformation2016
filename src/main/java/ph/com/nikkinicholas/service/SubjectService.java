package ph.com.nikkinicholas.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ph.com.nikkinicholas.domain.Subject;
import ph.com.nikkinicholas.repository.SubjectRepository;
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
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getSubjectForDataTable(final DataTablesRequest dataTablesRequest) {
        return subjectRepository.getSubjectForDataTable(dataTablesRequest);
    }

    public int getSubjectCountBeforeFiltering() {
        return subjectRepository.getSubjectCountBeforeFiltering();
    }

    public int getSubjectCountAfterFiltering(final DataTablesRequest dataTablesRequest) {
        return subjectRepository.getSubjectCountAfterFiltering(dataTablesRequest);
    }

    public Subject getSubject(final Subject subject) {
        return subjectRepository.getSubject(subject);
    }

    public ValidationResult createSubject(final Subject subject) {
        ValidationResult validationResult = validateSubject(subject);
        if(validationResult.getStatus() == Status.SUCCESS) {
            try {
                subject.setUuid(UUID.randomUUID().toString());
                subjectRepository.createSubject(subject);
            } catch (DuplicateKeyException e) {
                validationResult.addErrorCode(ErrorCode.SUBJECT_CODE_DUPLICATE);
            }
        }
        return validationResult;
    }

    public ValidationResult updateSubject(final Subject subject) {
        ValidationResult validationResult = validateSubject(subject);
        if(validationResult.getStatus() == Status.SUCCESS) {
            try {
                subjectRepository.updateSubject(subject);
            } catch (DuplicateKeyException e) {
                validationResult.addErrorCode(ErrorCode.SUBJECT_CODE_DUPLICATE);
            }
        }
        return validationResult;
    }

    public void deleteSubject(final Subject subject) {
        subjectRepository.deleteSubject(subject);
    }

    private ValidationResult validateSubject(Subject subject) {
        ValidationResult validationResult = new ValidationResult();

        if(StringUtils.isEmpty(subject.getCode())) {
            validationResult.addErrorCode(ErrorCode.SUBJECT_CODE_MISSING);
        } else if(subject.getCode().length() > 50) {
            validationResult.addErrorCode(ErrorCode.SUBJECT_CODE_TOO_LONG);
        }

        if(StringUtils.isEmpty(subject.getTitle())) {
            validationResult.addErrorCode(ErrorCode.SUBJECT_TITLE_MISSING);
        } else if(subject.getTitle().length() > 250) {
            validationResult.addErrorCode(ErrorCode.SUBJECT_TITLE_TOO_LONG);
        }

        if(subject.getUnits() == 0) {
            validationResult.addErrorCode(ErrorCode.SUBJECT_UNITS_MISSING);
        } else if(subject.getUnits() < 0) {
            validationResult.addErrorCode(ErrorCode.SUBJECT_UNITS_INVALID);
        }

        if(subject.getHours() == 0) {
            validationResult.addErrorCode(ErrorCode.SUBJECT_HOURS_MISSING);
        } else if(subject.getHours() < 0) {
            validationResult.addErrorCode(ErrorCode.SUBJECT_HOURS_INVALID);
        }

        return validationResult;
    }
}

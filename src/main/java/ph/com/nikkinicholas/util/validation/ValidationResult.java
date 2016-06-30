package ph.com.nikkinicholas.util.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikkinicholas on 6/30/16.
 */
public class ValidationResult {
    private Status status;
    private List<ErrorCode> errorCodeList;

    public ValidationResult() {
        status = Status.SUCCESS;
        errorCodeList = new ArrayList<>();
    }

    public Status getStatus() {
        return status;
    }

    public List<ErrorCode> getErrorCodeList() {
        return errorCodeList;
    }

    public void addErrorCode(ErrorCode errorCode) {
        status = Status.FAILED;
        errorCodeList.add(errorCode);
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                "status=" + status +
                ", errorCodeList=" + errorCodeList +
                '}';
    }
}

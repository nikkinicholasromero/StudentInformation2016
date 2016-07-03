package ph.com.nikkinicholas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ph.com.nikkinicholas.domain.Subject;
import ph.com.nikkinicholas.service.SubjectService;
import ph.com.nikkinicholas.util.datatables.DataTablesRequest;
import ph.com.nikkinicholas.util.datatables.DataTablesResponse;
import ph.com.nikkinicholas.util.validation.ValidationResult;

import java.util.List;

/**
 * Created by nikkinicholas on 6/24/16.
 */
@Controller
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @RequestMapping({"", "/"})
    public String subjects() {
        return "subjects";
    }

    @RequestMapping(value = "/getSubjectForDataTable", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody DataTablesResponse<Subject> getSubjectForDataTable(@RequestBody DataTablesRequest dataTablesRequest) {
        DataTablesResponse<Subject> dataTablesResponse = new DataTablesResponse<>();

        final List<Subject> subjects = subjectService.getSubjectForDataTable(dataTablesRequest);
        final int recordsTotal = subjectService.getSubjectCountBeforeFiltering();
        final int recordsFiltered = subjectService.getSubjectCountAfterFiltering(dataTablesRequest);

        dataTablesResponse.setDraw(dataTablesRequest.getDraw());
        dataTablesResponse.setData(subjects);
        dataTablesResponse.setRecordsTotal(recordsTotal);
        dataTablesResponse.setRecordsFiltered(recordsFiltered);

        return dataTablesResponse;
    }

    @RequestMapping(value = "/createSubject", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ValidationResult createSubject(@RequestBody Subject subject) {
        return subjectService.createSubject(subject);
    }

    @RequestMapping(value = "/getSubject", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Subject getSubject(@RequestBody Subject subject) {
        return subjectService.getSubject(subject);
    }

    @RequestMapping(value = "/updateSubject", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ValidationResult updateSubject(@RequestBody Subject subject) {
        return subjectService.updateSubject(subject);
    }

    @RequestMapping(value = "/deleteSubject", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteSubject(@RequestBody Subject subject) {
        subjectService.deleteSubject(subject);
    }
}
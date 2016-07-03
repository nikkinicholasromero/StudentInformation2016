package ph.com.nikkinicholas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ph.com.nikkinicholas.domain.Professor;
import ph.com.nikkinicholas.service.ProfessorService;
import ph.com.nikkinicholas.util.datatables.DataTablesRequest;
import ph.com.nikkinicholas.util.datatables.DataTablesResponse;
import ph.com.nikkinicholas.util.validation.ValidationResult;

import java.util.List;

/**
 * Created by nikkinicholas on 6/24/16.
 */
@Controller
@RequestMapping("/professors")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @RequestMapping({"", "/"})
    public String professors() {
        return "professors";
    }

    @RequestMapping(value = "/getProfessorForDataTable", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    DataTablesResponse<Professor> getProfessorForDataTable(@RequestBody DataTablesRequest dataTablesRequest) {
        DataTablesResponse<Professor> dataTablesResponse = new DataTablesResponse<>();

        final List<Professor> professors = professorService.getProfessorForDataTable(dataTablesRequest);
        final int recordsTotal = professorService.getProfessorCountBeforeFiltering();
        final int recordsFiltered = professorService.getProfessorCountAfterFiltering(dataTablesRequest);

        dataTablesResponse.setDraw(dataTablesRequest.getDraw());
        dataTablesResponse.setData(professors);
        dataTablesResponse.setRecordsTotal(recordsTotal);
        dataTablesResponse.setRecordsFiltered(recordsFiltered);

        return dataTablesResponse;
    }

    @RequestMapping(value = "/createProfessor", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ValidationResult createProfessor(@RequestBody Professor professor) {
        return professorService.createProfessor(professor);
    }

    @RequestMapping(value = "/getProfessor", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Professor getProfessor(@RequestBody Professor professor) {
        return professorService.getProfessor(professor);
    }

    @RequestMapping(value = "/updateProfessor", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ValidationResult updateProfessor(@RequestBody Professor professor) {
        return professorService.updateProfessor(professor);
    }

    @RequestMapping(value = "/deleteProfessor", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProfessor(@RequestBody Professor professor) {
        professorService.deleteProfessor(professor);
    }
}
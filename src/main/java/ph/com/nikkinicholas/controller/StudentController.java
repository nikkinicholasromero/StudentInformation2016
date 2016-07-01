package ph.com.nikkinicholas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ph.com.nikkinicholas.domain.Student;
import ph.com.nikkinicholas.service.StudentService;
import ph.com.nikkinicholas.util.datatables.DataTablesRequest;
import ph.com.nikkinicholas.util.datatables.DataTablesResponse;
import ph.com.nikkinicholas.util.validation.ValidationResult;

import java.util.List;

/**
 * Created by nikkinicholas on 6/24/16.
 */
@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping({"", "/"})
    public String students() {
        return "students";
    }
    @RequestMapping(value = "/getStudentForDataTable", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody DataTablesResponse<Student> getStudentForDataTable(@RequestBody DataTablesRequest dataTablesRequest) {
        DataTablesResponse<Student> dataTablesResponse = new DataTablesResponse<>();

        final List<Student> students = studentService.getStudentForDataTable(dataTablesRequest);
        final int recordsTotal = studentService.getStudentCountBeforeFiltering();
        final int recordsFiltered = studentService.getStudentCountAfterFiltering(dataTablesRequest);

        dataTablesResponse.setDraw(dataTablesRequest.getDraw());
        dataTablesResponse.setData(students);
        dataTablesResponse.setRecordsTotal(recordsTotal);
        dataTablesResponse.setRecordsFiltered(recordsFiltered);

        return dataTablesResponse;
    }

    @RequestMapping(value = "/createStudent", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ValidationResult createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ValidationResult updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @RequestMapping(value = "/deleteStudent", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@RequestBody Student student) {
        studentService.deleteStudent(student);
    }
}
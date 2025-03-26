package agile.victims.EKM.Server.controller;

import agile.victims.EKM.Server.entity.Student;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "EKM server", description = "API's For Software Engineering With Agile Class")
public class StudentController {

    @GetMapping("/students")
    @Operation(summary = "get all students", description = "Returns string")
    public String getAllStudents() {
        return "All students";
    }

    @PostMapping("/students")
    @Operation(summary = "Create a user", description = "Returns string")
    public String createStudent(@RequestBody Student student) {
        return "Student created";
    }
}

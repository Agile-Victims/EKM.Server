package agile.victims.EKM.Server.controller;

import agile.victims.EKM.Server.entity.Student;
import agile.victims.EKM.Server.requests.SignUpRequest;
import agile.victims.EKM.Server.service.StudentService;
import agile.victims.EKM.Server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webApi/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;

    // Create a new student
//    @PostMapping
//    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
//        Student savedStudent = studentService.saveStudent(student);
//        return ResponseEntity.ok(savedStudent);
//    }

    // Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PostMapping("/signup/")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        try {
            return ResponseEntity.ok(userService.signup("student", signUpRequest));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

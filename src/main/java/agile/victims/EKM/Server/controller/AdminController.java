package agile.victims.EKM.Server.controller;

import agile.victims.EKM.Server.entity.Exam;
import agile.victims.EKM.Server.requests.CreateExamRequest;
import agile.victims.EKM.Server.requests.LoginRequest;
import agile.victims.EKM.Server.requests.SignUpRequest;
import agile.victims.EKM.Server.service.ExamService;
import agile.victims.EKM.Server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webApi/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExamService examService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(userService.login("admin", loginRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        try {
            return ResponseEntity.ok(userService.signup("admin", signUpRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/exams")
    public ResponseEntity<Exam> createExam(@RequestBody CreateExamRequest request) {
        Exam exam = examService.createExam(
                request.getExamName(),
                request.getTurkishQuestionCount(),
                request.getMathQuestionCount(),
                request.getScienceQuestionCount(),
                request.getHistoryQuestionCount(),
                request.getRelegionQuestionCount(),
                request.getForeignLanguageQuestionCount(),
                true
        );
        return ResponseEntity.ok(exam);
    }

    @GetMapping("/exams")
    public ResponseEntity<List<Exam>> getAllExams() {
        List<Exam> exams = examService.getAllExams();
        return ResponseEntity.ok(exams);
    }

   

    @PutMapping("/exams/{examId}/activate")
public ResponseEntity<Exam> activateExam(@PathVariable Long examId) {
    try {
        Exam updatedExam = examService.activateExam(examId);
        return ResponseEntity.ok(updatedExam);
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
}

@PutMapping("/exams/{examId}/deactivate")
public ResponseEntity<Exam> deactivateExam(@PathVariable Long examId) {
    try {
        Exam updatedExam = examService.deactivateExam(examId);
        return ResponseEntity.ok(updatedExam);
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
}

}

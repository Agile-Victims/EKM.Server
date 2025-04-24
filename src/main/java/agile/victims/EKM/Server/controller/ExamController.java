package agile.victims.EKM.Server.controller;

import agile.victims.EKM.Server.entity.Exam;
import agile.victims.EKM.Server.entity.StudentExam;
import agile.victims.EKM.Server.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exams")
public class ExamController {
    @Autowired
    private ExamService examService;

    @PostMapping
    public ResponseEntity<Exam> createExam(@RequestBody CreateExamRequest request) {
        Exam exam = examService.createExam(
            request.getTitle(),
            request.getSubjectQuestionCount()
        );
        return ResponseEntity.ok(exam);
    }

    @GetMapping
    public ResponseEntity<List<Exam>> getAllExams() {
        List<Exam> exams = examService.getAllExams();
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentExam>> getStudentExams(@PathVariable Long studentId) {
        List<StudentExam> studentExams = examService.getStudentExams(studentId);
        return ResponseEntity.ok(studentExams);
    }
}

class CreateExamRequest {
    private String title;
    private Map<String, Integer> subjectQuestionCount;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Integer> getSubjectQuestionCount() {
        return subjectQuestionCount;
    }

    public void setSubjectQuestionCount(Map<String, Integer> subjectQuestionCount) {
        this.subjectQuestionCount = subjectQuestionCount;
    }
} 
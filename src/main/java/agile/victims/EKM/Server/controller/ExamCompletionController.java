package agile.victims.EKM.Server.controller;

import agile.victims.EKM.Server.dto.ExamCompletionDTO;
import agile.victims.EKM.Server.entity.ExamCompletion;
import agile.victims.EKM.Server.service.ExamCompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam-completions")
public class ExamCompletionController {

    @Autowired
    private ExamCompletionService examCompletionService;

    @PostMapping("/{studentId}/{examId}")
    public ResponseEntity<?> markExamAsCompleted(
            @PathVariable Long studentId,
            @PathVariable Long examId) {
        try {
            ExamCompletion completion = examCompletionService.markExamAsCompleted(studentId, examId);
            return ResponseEntity.ok(completion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentCompletions(@PathVariable Long studentId) {
        try {
            List<ExamCompletion> completions = examCompletionService.getStudentCompletions(studentId);
            return ResponseEntity.ok(completions);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<?> getExamCompletions(@PathVariable Long examId) {
        try {
            List<ExamCompletion> completions = examCompletionService.getExamCompletions(examId);
            return ResponseEntity.ok(completions);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/exam/{examId}/details")
    public ResponseEntity<?> getExamCompletionDetails(@PathVariable Long examId) {
        try {
            List<ExamCompletionDTO> details = examCompletionService.getExamCompletionDetails(examId);
            return ResponseEntity.ok(details);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 
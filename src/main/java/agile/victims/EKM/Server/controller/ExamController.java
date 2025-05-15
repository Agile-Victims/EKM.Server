package agile.victims.EKM.Server.controller;

import agile.victims.EKM.Server.dto.ExamCompletionDTO;
import agile.victims.EKM.Server.entity.Exam;
import agile.victims.EKM.Server.entity.ExamCompletion;
import agile.victims.EKM.Server.requests.CreateExamRequest;
import agile.victims.EKM.Server.responses.ExamResult;
import agile.victims.EKM.Server.service.ExamCompletionService;
import agile.victims.EKM.Server.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/webApi/exams")
public class ExamController {
    @Autowired
    private ExamCompletionService examCompletionService;
    @Autowired
    private ExamService examService;


    @GetMapping("/getResults/{id}")
    public ResponseEntity<List<ExamResult>> getExamResults(@PathVariable("id") Long examId) {
        List<ExamResult> results = examService.getExamResult(examId); // az önce yazdığın method

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.ok(results); // 200 OK + Liste
    }


    @GetMapping("/getExamById/{id}")
    public ResponseEntity<?> getExamById(@PathVariable Long id) {
        Exam exam = examService.getExamById(id);
        if(exam == null){
            return ResponseEntity.badRequest().body("Deneme bulunamadı");
        }
        return ResponseEntity.ok(exam);
    }

    @GetMapping("/getAllExams")
    public ResponseEntity<?> getAllExams() {
         List<Exam> exams = examService.getAllExams();
        if(exams == null || exams.isEmpty()) {
            return ResponseEntity.badRequest().body("Deneme bulunamadı");
        }
        return ResponseEntity.ok(exams);
    }

    @PostMapping("/completeExam")
    public ResponseEntity<?> markExamAsCompleted(@RequestBody ExamCompletionDTO examCompletionForm) {
        try {
            ExamCompletion completion = examCompletionService.markExamAsCompleted(examCompletionForm);
            return ResponseEntity.ok(completion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/examNet/{examCompletionId}")
    public ResponseEntity<?> getExamNets(@PathVariable Long examCompletionId) {
        try {
            ExamCompletion completion = examCompletionService.getExamCompletionById(examCompletionId);
            if (completion == null) {
                return ResponseEntity.notFound().build();
            }

            Map<String, Double> nets = new HashMap<>();
            nets.put("turkishNet", examCompletionService.calculateTurkishNet(completion));
            nets.put("mathNet", examCompletionService.calculateMathNet(completion));
            nets.put("scienceNet", examCompletionService.calculateScienceNet(completion));
            nets.put("historyNet", examCompletionService.calculateHistoryNet(completion));
            nets.put("religionNet", examCompletionService.calculateReligionNet(completion));
            nets.put("foreignLanguageNet", examCompletionService.calculateForeignLanguageNet(completion));

            return ResponseEntity.ok(nets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/studentExamNets")
    public ResponseEntity<?> getStudentExamNets(
            @RequestParam String email,
            @RequestParam Long examId) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email adresi boş olamaz");
            }
            if (examId == null) {
                return ResponseEntity.badRequest().body("Sınav ID'si boş olamaz");
            }

            ExamCompletion completion = examCompletionService.getStudentExamNets(email, examId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("turkishNet", examCompletionService.calculateTurkishNet(completion));
            response.put("mathNet", examCompletionService.calculateMathNet(completion));
            response.put("scienceNet", examCompletionService.calculateScienceNet(completion));
            response.put("historyNet", examCompletionService.calculateHistoryNet(completion));
            response.put("religionNet", examCompletionService.calculateReligionNet(completion));
            response.put("foreignLanguageNet", examCompletionService.calculateForeignLanguageNet(completion));
            response.put("completionDate", completion.getCompletionDate());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/studentCompletedExams/{email}")
    public ResponseEntity<?> getStudentCompletedExams(@PathVariable String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email adresi boş olamaz");
            }

            List<Long> examIds = examCompletionService.getStudentCompletedExamIds(email);
            
            if (examIds.isEmpty()) {
                return ResponseEntity.ok("Bu öğrenci henüz hiç sınav tamamlamamış.");
            }

            return ResponseEntity.ok(examIds);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createExam")
    public ResponseEntity<Exam> createExam(@RequestBody CreateExamRequest request) {
        Exam exam = examService.createExam(
                request.getExamName(),
                request.getTurkishQuestionCount(),
                request.getMathQuestionCount(),
                request.getScienceQuestionCount(),
                request.getHistoryQuestionCount(),
                request.getReligionQuestionCount(),
                request.getForeignLanguageQuestionCount(),
                true
        );
        return ResponseEntity.ok(exam);
    }

    @PutMapping("/deactivateExam/{examId}")
    public ResponseEntity<Exam> deactivateExam(@PathVariable Long examId) {
        try {
            Exam updatedExam = examService.deactivateExam(examId);
            return ResponseEntity.ok(updatedExam);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

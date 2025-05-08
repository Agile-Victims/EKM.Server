package agile.victims.EKM.Server.controller;

import agile.victims.EKM.Server.dto.ExamCompletionDTO;
import agile.victims.EKM.Server.entity.Exam;
import agile.victims.EKM.Server.entity.ExamCompletion;
import agile.victims.EKM.Server.service.ExamCompletionService;
import agile.victims.EKM.Server.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam")
public class ExamController {
    @Autowired
    private ExamCompletionService examCompletionService;
    @Autowired
    private ExamService examService;

    // @GetMapping("/getAllExams")  --> returns Exam[]
    // @GetMapping("/getResults/{id}")  --> returns ExamResults[]
    /*export class ExamResult {
        constructor(
                public studentId: number,
                public studentName: string,
                public net: number,  --> 6 dersin doğru cevaplar toplam - (6 dersin yanlış cevaplar toplamı / 4)
                public completed: boolean  --> net 0 ise false diğer türlü true
        ) {}
    }*/

    //examcomplitioncontroller kalmayacak


    @GetMapping("/getExamById/{id}")
    public ResponseEntity<?> getAllExams(@PathVariable Long id) {
        Exam exam = examService.getExamById(id);
        if(exam == null){
            return ResponseEntity.badRequest().body("Deneme bulunamadı");
        }
        return ResponseEntity.ok(exam);
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
}

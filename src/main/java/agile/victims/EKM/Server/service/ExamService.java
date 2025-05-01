package agile.victims.EKM.Server.service;

import agile.victims.EKM.Server.entity.Exam;
import agile.victims.EKM.Server.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;


    public Exam createExam(String examName, int turkishQuestionCount, int mathQuestionCount,
                          int scienceQuestionCount, int historyQuestionCount, int relegionQuestionCount,
                          int foreignLanguageQuestionCount, boolean isActive) {
        Exam exam = new Exam();
        exam.setExamName(examName);
        exam.setTurkishQuestionCount(turkishQuestionCount);
        exam.setMathQuestionCount(mathQuestionCount);
        exam.setScienceQuestionCount(scienceQuestionCount);
        exam.setHistoryQuestionCount(historyQuestionCount);
        exam.setRelegionQuestionCount(relegionQuestionCount);
        exam.setForeignLanguageQuestionCount(foreignLanguageQuestionCount);
        exam.setActive(isActive);
        

        exam = examRepository.save(exam);

        return exam;
    }

   
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

  
    public Exam activateExam(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));
        exam.setActive(true);
        return examRepository.save(exam);
    }
    
    public Exam deactivateExam(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));
        exam.setActive(false);
        return examRepository.save(exam);
    }
    
} 
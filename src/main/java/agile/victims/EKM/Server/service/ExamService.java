package agile.victims.EKM.Server.service;

import agile.victims.EKM.Server.entity.Exam;
import agile.victims.EKM.Server.entity.ExamCompletion;
import agile.victims.EKM.Server.repository.ExamRepository;
import agile.victims.EKM.Server.responses.ExamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public Exam getExamById(Long id) {
        var result = examRepository.findById(id);
        return result.orElse(null);
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

    private Double calculateNet(ExamCompletion exam) {
        double turkishNet = exam.getTurkishCorrectCount() - (exam.getTurkishWrongCount() / 3.0);
        double mathNet = exam.getMathCorrectCount() - (exam.getMathWrongCount() / 3.0);
        double scienceNet = exam.getScienceCorrectCount() - (exam.getScienceWrongCount() / 3.0);
        double historyNet = exam.getHistoryCorrectCount() - (exam.getHistoryWrongCount() / 3.0);
        double religionNet = exam.getReligionCorrectCount() - (exam.getReligionWrongCount() / 3.0);
        double languageNet = exam.getForeignLanguageCorrectCount() - (exam.getForeignLanguageWrongCount() / 3.0);

        return turkishNet + mathNet + scienceNet + historyNet + religionNet + languageNet;
    }


    public List<ExamResult> getExamResult(Long examId) {
        List<ExamResult> results = new ArrayList<>();
        List<Map<String, Object>> queryResult = examRepository.findExamCompletionsByExamId(examId);

        for (Map<String, Object> map : queryResult) {
            ExamResult examResult = new ExamResult();

            examResult.studentId = ((Number) map.get("student_id")).longValue();
            examResult.studentName = (String) map.get("name");
            examResult.studentSurname = (String) map.get("surname");
            examResult.studentEmail = (String) map.get("email");

            // ExamCompletion benzeri alanlardan net hesapla:
            ExamCompletion temp = new ExamCompletion();
            temp.setTurkishCorrectCount(((Number) map.get("turkish_correct_count")).intValue());
            temp.setTurkishWrongCount(((Number) map.get("turkish_wrong_count")).intValue());
            temp.setMathCorrectCount(((Number) map.get("math_correct_count")).intValue());
            temp.setMathWrongCount(((Number) map.get("math_wrong_count")).intValue());
            temp.setScienceCorrectCount(((Number) map.get("science_correct_count")).intValue());
            temp.setScienceWrongCount(((Number) map.get("science_wrong_count")).intValue());
            temp.setHistoryCorrectCount(((Number) map.get("history_correct_count")).intValue());
            temp.setHistoryWrongCount(((Number) map.get("history_wrong_count")).intValue());
            temp.setReligionCorrectCount(((Number) map.get("religion_correct_count")).intValue());
            temp.setReligionWrongCount(((Number) map.get("religion_wrong_count")).intValue());
            temp.setForeignLanguageCorrectCount(((Number) map.get("foreign_language_correct_count")).intValue());
            temp.setForeignLanguageWrongCount(((Number) map.get("foreign_language_wrong_count")).intValue());

            examResult.net = calculateNet(temp);
            examResult.completed = true;

            results.add(examResult);
        }
        return results;
    }

    
} 
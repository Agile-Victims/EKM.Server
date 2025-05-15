package agile.victims.EKM.Server.service;

import agile.victims.EKM.Server.entity.Exam;
import agile.victims.EKM.Server.entity.ExamCompletion;
import agile.victims.EKM.Server.entity.Student;
import agile.victims.EKM.Server.repository.ExamRepository;
import agile.victims.EKM.Server.repository.StudentRepository;
import agile.victims.EKM.Server.responses.ExamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private StudentRepository studentRepository;

    public Exam createExam(String examName, int turkishQuestionCount, int mathQuestionCount,
                          int scienceQuestionCount, int historyQuestionCount, int religionQuestionCount,
                          int foreignLanguageQuestionCount, boolean isActive) {
        Exam exam = new Exam();
        exam.setExamName(examName);
        exam.setTurkishQuestionCount(turkishQuestionCount);
        exam.setMathQuestionCount(mathQuestionCount);
        exam.setScienceQuestionCount(scienceQuestionCount);
        exam.setHistoryQuestionCount(historyQuestionCount);
        exam.setReligionQuestionCount(religionQuestionCount);
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
        double turkishNet = exam.getTurkishCorrectCount() - (exam.getTurkishWrongCount() / 4.0);
        double mathNet = exam.getMathCorrectCount() - (exam.getMathWrongCount() / 4.0);
        double scienceNet = exam.getScienceCorrectCount() - (exam.getScienceWrongCount() / 4.0);
        double historyNet = exam.getHistoryCorrectCount() - (exam.getHistoryWrongCount() / 4.0);
        double religionNet = exam.getReligionCorrectCount() - (exam.getReligionWrongCount() / 4.0);
        double languageNet = exam.getForeignLanguageCorrectCount() - (exam.getForeignLanguageWrongCount() / 4.0);

        return turkishNet + mathNet + scienceNet + historyNet + religionNet + languageNet;
    }

    private Double calculateNet(int correctCount, int wrongCount) {
        return (double) correctCount - (wrongCount / 4.0);
    }


    public List<ExamResult> getExamResult(Long examId) {
        List<ExamResult> results = new ArrayList<>();
        List<Map<String, Object>> queryResult = examRepository.findExamCompletionsByExamId(examId);

        for (Map<String, Object> map : queryResult) {
            ExamResult examResult = new ExamResult();
            examResult.studentId = ((Number) map.get("studentId")).longValue();
            Optional<Student> student = studentRepository.findById(examResult.getStudentId());
            if(student.isEmpty()){
                return null;
            }

            examResult.studentName = student.get().getName();
            examResult.studentSurname = student.get().getSurname();
            examResult.studentEmail = student.get().getEmail();
            examResult.setTurkishNet(calculateNet(((Number) map.get("turkish_correct_count")).intValue(), ((Number) map.get("turkish_correct_count")).intValue()));
            examResult.setMathNet(calculateNet(((Number) map.get("math_correct_count")).intValue(), ((Number) map.get("math_correct_count")).intValue()));
            examResult.setScienceNet(calculateNet(((Number) map.get("science_correct_count")).intValue(), ((Number) map.get("science_correct_count")).intValue()));
            examResult.setHistoryNet(calculateNet(((Number) map.get("history_correct_count")).intValue(), ((Number) map.get("history_correct_count")).intValue()));
            examResult.setReligionNet(calculateNet(((Number) map.get("religion_correct_count")).intValue(), ((Number) map.get("religion_correct_count")).intValue()));
            examResult.setForeignLanguageNet(calculateNet(((Number) map.get("foreign_language_correct_count")).intValue(), ((Number) map.get("foreign_language_correct_count")).intValue()));
            results.add(examResult);
        }
        return results;
    }

    
} 
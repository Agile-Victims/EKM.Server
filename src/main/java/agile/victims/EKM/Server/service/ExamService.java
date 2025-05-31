package agile.victims.EKM.Server.service;

import agile.victims.EKM.Server.entity.Exam;
import agile.victims.EKM.Server.entity.ExamCompletion;
import agile.victims.EKM.Server.entity.Student;
import agile.victims.EKM.Server.entity.User;
import agile.victims.EKM.Server.repository.ExamCompletionRepository;
import agile.victims.EKM.Server.repository.ExamRepository;
import agile.victims.EKM.Server.repository.StudentRepository;
import agile.victims.EKM.Server.repository.UserRepository;
import agile.victims.EKM.Server.responses.ExamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExamCompletionRepository examCompletionRepository;

    public Exam createExam(String examName, int turkishQuestionCount, int mathQuestionCount,
                          int scienceQuestionCount, int historyQuestionCount, int religionQuestionCount,
                          int foreignLanguageQuestionCount, String turkishSubjects, String mathSubjects,
                          String scienceSubjects, String historySubjects, String religionSubjects,
                          String foreignLanguageSubjects, boolean isActive) {
        Exam exam = new Exam();
        exam.setExamName(examName);
        exam.setTurkishQuestionCount(turkishQuestionCount);
        exam.setMathQuestionCount(mathQuestionCount);
        exam.setScienceQuestionCount(scienceQuestionCount);
        exam.setHistoryQuestionCount(historyQuestionCount);
        exam.setReligionQuestionCount(religionQuestionCount);
        exam.setForeignLanguageQuestionCount(foreignLanguageQuestionCount);
        exam.setTurkishSubjects(turkishSubjects);
        exam.setMathSubjects(mathSubjects);
        exam.setScienceSubjects(scienceSubjects);
        exam.setHistorySubjects(historySubjects);
        exam.setReligionSubjects(religionSubjects);
        exam.setForeignLanguageSubjects(foreignLanguageSubjects);
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
        List<ExamCompletion> completions = examCompletionRepository.findByExamId(examId);
        return completions.stream()
                .map(completion -> {
                    ExamResult result = new ExamResult();
                    User student = userRepository.findById(completion.getStudentId())
                            .orElseThrow(() -> new RuntimeException("Student not found"));
                    result.setStudentId(student.getId());
                    result.setStudentName(student.getName());
                    result.setStudentSurname(student.getSurname());
                    result.setStudentEmail(student.getEmail());
                    result.setTurkishNet(calculateTurkishNet(completion));
                    result.setMathNet(calculateMathNet(completion));
                    result.setScienceNet(calculateScienceNet(completion));
                    result.setHistoryNet(calculateHistoryNet(completion));
                    result.setReligionNet(calculateReligionNet(completion));
                    result.setForeignLanguageNet(calculateForeignLanguageNet(completion));
                    return result;
                })
                .collect(Collectors.toList());
    }

    private double calculateTurkishNet(ExamCompletion completion) {
        return completion.getTurkishCorrectCount() - (completion.getTurkishWrongCount() * 0.25);
    }

    private double calculateMathNet(ExamCompletion completion) {
        return completion.getMathCorrectCount() - (completion.getMathWrongCount() * 0.25);
    }

    private double calculateScienceNet(ExamCompletion completion) {
        return completion.getScienceCorrectCount() - (completion.getScienceWrongCount() * 0.25);
    }

    private double calculateHistoryNet(ExamCompletion completion) {
        return completion.getHistoryCorrectCount() - (completion.getHistoryWrongCount() * 0.25);
    }

    private double calculateReligionNet(ExamCompletion completion) {
        return completion.getReligionCorrectCount() - (completion.getReligionWrongCount() * 0.25);
    }

    private double calculateForeignLanguageNet(ExamCompletion completion) {
        return completion.getForeignLanguageCorrectCount() - (completion.getForeignLanguageWrongCount() * 0.25);
    }
} 
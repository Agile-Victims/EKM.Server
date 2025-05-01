package agile.victims.EKM.Server.service;

import agile.victims.EKM.Server.entity.Admin;
import agile.victims.EKM.Server.entity.Exam;
import agile.victims.EKM.Server.entity.Question;
import agile.victims.EKM.Server.entity.StudentExam;
import agile.victims.EKM.Server.repository.ExamRepository;
import agile.victims.EKM.Server.repository.QuestionRepository;
import agile.victims.EKM.Server.repository.StudentExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentExamRepository studentExamRepository;

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
        
        Admin admin = new Admin();

        exam = examRepository.save(exam);

        // Türkçe soruları oluştur
        for (int i = 1; i <= turkishQuestionCount; i++) {
            createQuestion(exam, "Türkçe", i);
        }

        // Matematik soruları oluştur
        for (int i = 1; i <= mathQuestionCount; i++) {
            createQuestion(exam, "Matematik", i);
        }

        // Fen soruları oluştur
        for (int i = 1; i <= scienceQuestionCount; i++) {
            createQuestion(exam, "Fen", i);
        }

        // Tarih soruları oluştur
        for (int i = 1; i <= historyQuestionCount; i++) {
            createQuestion(exam, "Tarih", i);
        }

        // Din soruları oluştur
        for (int i = 1; i <= relegionQuestionCount; i++) {
            createQuestion(exam, "Din", i);
        }

        // Yabancı Dil soruları oluştur
        for (int i = 1; i <= foreignLanguageQuestionCount; i++) {
            createQuestion(exam, "Yabancı Dil", i);
        }

        return exam;
    }

    private void createQuestion(Exam exam, String subject, int questionNumber) {
        Question question = new Question();
        question.setSubject(subject);
        question.setQuestionNumber(questionNumber);
        question.setContent(subject + " sorusu " + questionNumber);
        question.setExam(exam);
        questionRepository.save(question);
    }

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public List<StudentExam> getStudentExams(Long studentId) {
        return studentExamRepository.findByStudentId(studentId);
    }
} 
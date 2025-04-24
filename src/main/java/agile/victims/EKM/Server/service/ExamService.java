package agile.victims.EKM.Server.service;

import agile.victims.EKM.Server.entity.Exam;
import agile.victims.EKM.Server.entity.Question;
import agile.victims.EKM.Server.entity.StudentExam;
import agile.victims.EKM.Server.repository.ExamRepository;
import agile.victims.EKM.Server.repository.QuestionRepository;
import agile.victims.EKM.Server.repository.StudentExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentExamRepository studentExamRepository;

    public Exam createExam(String title, Map<String, Integer> subjectQuestionCount) {
        Exam exam = new Exam();
        exam.setTitle(title);

        exam = examRepository.save(exam);

        for (Map.Entry<String, Integer> entry : subjectQuestionCount.entrySet()) {
            String subject = entry.getKey();
            int count = entry.getValue();

            for (int i = 1; i <= count; i++) {
                Question question = new Question();
                question.setSubject(subject);
                question.setQuestionNumber(i);
                question.setContent("Soru " + i + " içeriği");
                question.setExam(exam);
                questionRepository.save(question);
            }
        }

        return exam;
    }

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public List<StudentExam> getStudentExams(Long studentId) {
        return studentExamRepository.findByStudentId(studentId);
    }
} 
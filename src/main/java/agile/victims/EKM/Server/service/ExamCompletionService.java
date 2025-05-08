package agile.victims.EKM.Server.service;

import agile.victims.EKM.Server.dto.ExamCompletionDTO;
import agile.victims.EKM.Server.entity.ExamCompletion;
import agile.victims.EKM.Server.entity.Student;
import agile.victims.EKM.Server.entity.Exam;
import agile.victims.EKM.Server.repository.ExamCompletionRepository;
import agile.victims.EKM.Server.repository.StudentRepository;
import agile.victims.EKM.Server.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamCompletionService {

    @Autowired
    private ExamCompletionRepository examCompletionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamRepository examRepository;

    public ExamCompletion markExamAsCompleted(Long studentId, Long examId) {
        // Öğrenci kontrolü
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı: " + studentId));

        // Sınav kontrolü
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Sınav bulunamadı: " + examId));

        // Sınavın aktif olup olmadığını kontrol et
        if (!exam.isActive()) {
            throw new RuntimeException("Bu sınav şu anda aktif değil");
        }

        // Öğrencinin bu sınavı daha önce tamamlayıp tamamlamadığını kontrol et
        ExamCompletion existingCompletion = examCompletionRepository.findByStudentIdAndExamId(studentId, examId);
        if (existingCompletion != null && existingCompletion.isCompleted()) {
            throw new RuntimeException("Bu öğrenci bu sınavı zaten tamamlamış");
        }
        
        ExamCompletion completion;
        if (existingCompletion == null) {
            completion = new ExamCompletion();
            completion.setStudentId(studentId);
            completion.setExamId(examId);
        } else {
            completion = existingCompletion;
        }
        
        completion.setCompleted(true);
        completion.setCompletionDate(LocalDateTime.now());
        
        return examCompletionRepository.save(completion);
    }

    public List<ExamCompletion> getStudentCompletions(Long studentId) {
        // Öğrenci kontrolü
        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Öğrenci bulunamadı: " + studentId);
        }
        return examCompletionRepository.findByStudentId(studentId);
    }

    public List<ExamCompletion> getExamCompletions(Long examId) {
        // Sınav kontrolü
        if (!examRepository.existsById(examId)) {
            throw new RuntimeException("Sınav bulunamadı: " + examId);
        }
        return examCompletionRepository.findByExamId(examId);
    }

    public List<ExamCompletionDTO> getExamCompletionDetails(Long examId) {
        // Sınav kontrolü
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Sınav bulunamadı: " + examId));

        List<ExamCompletion> completions = examCompletionRepository.findByExamId(examId);

        return completions.stream()
                .filter(ExamCompletion::isCompleted)
                .map(completion -> {
                    Student student = studentRepository.findById(completion.getStudentId())
                            .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı: " + completion.getStudentId()));

                    ExamCompletionDTO dto = new ExamCompletionDTO();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getName());
                    dto.setStudentSurname(student.getSurname());
                    dto.setStudentEmail(student.getEmail());
                    dto.setCompletionDate(completion.getCompletionDate());
                    dto.setExamName(exam.getExamName());
                    return dto;
                })
                .collect(Collectors.toList());
    }
} 
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

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;
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

    public ExamCompletion markExamAsCompleted(ExamCompletionDTO examCompletionForm) {
        // Öğrenci kontrolü
        Student student = studentRepository.findByEmail(examCompletionForm.getStudentEmail())
                .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı: " + examCompletionForm.getStudentEmail()));

        // Sınav kontrolü
        Exam exam = examRepository.findById(examCompletionForm.getExamId())
                .orElseThrow(() -> new RuntimeException("Sınav bulunamadı: " + examCompletionForm.getExamId()));

        // Sınavın aktif olup olmadığını kontrol et
        if (!exam.isActive()) {
            throw new RuntimeException("Bu sınav şu anda aktif değil");
        }
        
        ExamCompletion completion = new ExamCompletion();
        completion.setExamId(exam.getId());
        completion.setStudentId(student.getId());
        completion.setTurkishCorrectCount(examCompletionForm.getTurkishCorrectCount());
        completion.setTurkishWrongCount(examCompletionForm.getTurkishWrongCount());

        completion.setMathCorrectCount(examCompletionForm.getMathCorrectCount());
        completion.setMathWrongCount(examCompletionForm.getMathWrongCount());

        completion.setScienceCorrectCount(examCompletionForm.getScienceCorrectCount());
        completion.setScienceWrongCount(examCompletionForm.getScienceWrongCount());

        completion.setHistoryCorrectCount(examCompletionForm.getHistoryCorrectCount());
        completion.setHistoryWrongCount(examCompletionForm.getHistoryWrongCount());

        completion.setReligionCorrectCount(examCompletionForm.getRelegionCorrectCount());
        completion.setReligionWrongCount(examCompletionForm.getRelegionWrongCount());

        completion.setForeignLanguageCorrectCount(examCompletionForm.getForeignLanguageCorrectCount());
        completion.setForeignLanguageWrongCount(examCompletionForm.getForeignLanguageWrongCount());

        completion.setCompletionDate(new Date());
        
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

    /*public List<ExamCompletionDTO> getExamCompletionDetails(Long examId) {
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
    }*/
} 
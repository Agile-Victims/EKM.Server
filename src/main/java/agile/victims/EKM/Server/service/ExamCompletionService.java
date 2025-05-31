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

        completion.setReligionCorrectCount(examCompletionForm.getReligionCorrectCount());
        completion.setReligionWrongCount(examCompletionForm.getReligionWrongCount());

        completion.setForeignLanguageCorrectCount(examCompletionForm.getForeignLanguageCorrectCount());
        completion.setForeignLanguageWrongCount(examCompletionForm.getForeignLanguageWrongCount());

        completion.setTurkishWrongSubjects(examCompletionForm.getTurkishWrongSubjects());
        completion.setMathWrongSubjects(examCompletionForm.getMathWrongSubjects());
        completion.setScienceWrongSubjects(examCompletionForm.getScienceWrongSubjects());
        completion.setHistoryWrongSubjects(examCompletionForm.getHistoryWrongSubjects());
        completion.setReligionWrongSubjects(examCompletionForm.getReligionWrongSubjects());
        completion.setForeignLanguageWrongSubjects(examCompletionForm.getForeignLanguageWrongSubjects());

        completion.setTurkishEmptySubjects(examCompletionForm.getTurkishEmptySubjects());
        completion.setMathEmptySubjects(examCompletionForm.getMathEmptySubjects());
        completion.setScienceEmptySubjects(examCompletionForm.getScienceEmptySubjects());
        completion.setHistoryEmptySubjects(examCompletionForm.getHistoryEmptySubjects());
        completion.setReligionEmptySubjects(examCompletionForm.getReligionEmptySubjects());
        completion.setForeignLanguageEmptySubjects(examCompletionForm.getForeignLanguageEmptySubjects());

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

    public ExamCompletion getDetailedExamResult(Long examId, Long studentId) {
        return examCompletionRepository.findByExamIdAndStudentId(examId, studentId).orElse(null);
    }

    public ExamCompletion getExamCompletionById(Long id) {
        return examCompletionRepository.findById(id)
                .orElse(null);
    }

    public double calculateTurkishNet(ExamCompletion completion) {
        return completion.getTurkishCorrectCount() - (completion.getTurkishWrongCount() / 4.0);
    }

    public double calculateMathNet(ExamCompletion completion) {
        return completion.getMathCorrectCount() - (completion.getMathWrongCount() / 4.0);
    }

    public double calculateScienceNet(ExamCompletion completion) {
        return completion.getScienceCorrectCount() - (completion.getScienceWrongCount() / 4.0);
    }

    public double calculateHistoryNet(ExamCompletion completion) {
        return completion.getHistoryCorrectCount() - (completion.getHistoryWrongCount() / 4.0);
    }

    public double calculateReligionNet(ExamCompletion completion) {
        return completion.getReligionCorrectCount() - (completion.getReligionWrongCount() / 4.0);
    }

    public double calculateForeignLanguageNet(ExamCompletion completion) {
        return completion.getForeignLanguageCorrectCount() - (completion.getForeignLanguageWrongCount() / 4.0);
    }

    public ExamCompletion getStudentExamNets(String email, Long examId) {
        // Öğrenci kontrolü
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Bu email adresine sahip öğrenci bulunamadı: " + email));

        // Sınav kontrolü
        if (!examRepository.existsById(examId)) {
            throw new RuntimeException("Bu ID'ye sahip sınav bulunamadı: " + examId);
        }

        // Öğrencinin bu sınavı tamamlayıp tamamlamadığını kontrol et
        ExamCompletion completion = examCompletionRepository.findByStudentIdAndExamId(student.getId(), examId);
        if (completion == null) {
            throw new RuntimeException("Bu öğrenci bu sınavı henüz tamamlamamış.");
        }

        return completion;
    }

    public List<Long> getStudentCompletedExamIds(String email) {
        // Öğrenci kontrolü
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Bu email adresine sahip öğrenci bulunamadı: " + email));

        // Öğrencinin tamamladığı sınavları bul
        List<ExamCompletion> completions = examCompletionRepository.findByStudentId(student.getId());
        
        // Sınav ID'lerini liste olarak döndür
        return completions.stream()
                .map(ExamCompletion::getExamId)
                .toList();
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
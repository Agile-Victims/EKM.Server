package agile.victims.EKM.Server.repository;

import agile.victims.EKM.Server.entity.ExamCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamCompletionRepository extends JpaRepository<ExamCompletion, Long> {
    List<ExamCompletion> findByStudentId(Long studentId);
    List<ExamCompletion> findByExamId(Long examId);
    ExamCompletion findByStudentIdAndExamId(Long studentId, Long examId);
    Optional<ExamCompletion> findByExamIdAndStudentId(Long examId, Long studentId);
} 
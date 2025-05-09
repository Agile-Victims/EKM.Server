package agile.victims.EKM.Server.repository;

import agile.victims.EKM.Server.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Query(value = "SELECT u.id, u.name, u.surname, u.email, ec.* FROM exam_completions ec " +
            "LEFT JOIN students s ON s.id = ec.student_id " +
            "LEFT JOIN users u ON u.id = s.id " +
            "WHERE (:examId IS NULL OR ec.exam_id = :examId)", nativeQuery = true)
    List<Map<String, Object>> findExamCompletionsByExamId(@Param("examId") Long examId);

} 
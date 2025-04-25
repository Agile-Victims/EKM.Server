package agile.victims.EKM.Server.repository;

import agile.victims.EKM.Server.entity.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentExamRepository extends JpaRepository<StudentExam, Long> {
    @Query("SELECT se FROM StudentExam se WHERE se.student.id = :studentId")
    List<StudentExam> findByStudentId(@Param("studentId") Long studentId);
} 
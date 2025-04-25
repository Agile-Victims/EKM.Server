package agile.victims.EKM.Server.repository;

import agile.victims.EKM.Server.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
} 
package agile.victims.EKM.Server.repository;

import agile.victims.EKM.Server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
}

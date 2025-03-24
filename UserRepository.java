package bankBackend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import bankBackend.bank.model.User;
public interface UserRepository extends JpaRepository<User, Long> {
}

package ukim.finki.wpprojectinternships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ukim.finki.wpprojectinternships.model.Student;

import java.util.Optional;

public interface StudentRepository extends JpaSpecificationRepository<Student,Long> {
 Optional<Student> findByEmail(String email);
 Optional<Student> findByName(String username);
}

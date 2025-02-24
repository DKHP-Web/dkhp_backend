package dkhpweb.dkhp_backend.repositories;

import dkhpweb.dkhp_backend.models.Student;
import dkhpweb.dkhp_backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    boolean existsById(String id);

    Optional<Student> findByUser(User u);

    Page<Student> findByUser(User u, Pageable pageable);

    String findByUserId(String userId);
}

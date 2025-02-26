package dkhpweb.dkhp_backend.repositories;

import dkhpweb.dkhp_backend.models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, String> {
}

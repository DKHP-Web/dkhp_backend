package dkhpweb.dkhp_backend.repositories;

import dkhpweb.dkhp_backend.models.Admin;
import dkhpweb.dkhp_backend.models.enums.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    @Query("select admin from Admin admin where admin.user.id=?1")
    Admin findByUserId(String userId);

    @Query("select count(admin.id)>0 from Admin admin where admin.role=?1")
    boolean existsByRole(AdminRole role);
}

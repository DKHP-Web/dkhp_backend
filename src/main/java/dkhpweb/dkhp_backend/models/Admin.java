package dkhpweb.dkhp_backend.models;

import dkhpweb.dkhp_backend.models.enums.AdminRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {
    @Id @UuidGenerator
    private String id;

    private String name;

    @Column(nullable = false)
    private AdminRole role;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="userId", nullable=false)
    private User user;
}

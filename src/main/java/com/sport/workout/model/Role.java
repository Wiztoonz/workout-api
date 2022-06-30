package com.sport.workout.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "userRole")
@EqualsAndHashCode(exclude = "userRole")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@NamedQueries(
        {
             @NamedQuery(name = "Role.findRole", query = "SELECT r FROM Role r WHERE r.roleName = :roleName")
        }
)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private RoleName roleName;

    @Builder.Default
    @OneToMany(mappedBy = "role")
    private List<UserRole> userRole = new ArrayList<>();

}

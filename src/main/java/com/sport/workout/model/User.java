package com.sport.workout.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "trainings")
@EqualsAndHashCode(exclude = "trainings")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@NamedQueries(
        {
              @NamedQuery(name = "User.findUser", query = "SELECT u FROM User u WHERE u.email = :email"),
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "deviceId")
    private String deviceId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Training> trainings = new ArrayList<>();

}

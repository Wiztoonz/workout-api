package com.sport.workout.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"exercises"})
@EqualsAndHashCode(exclude = {"exercises"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@NamedQueries(
        {
                @NamedQuery(name = "Training.findUserTraining", query = "SELECT t FROM Training t WHERE t.user.email = :email AND t.status = :status"),
                @NamedQuery(name = "Training.findLastUserTraining", query = "SELECT t FROM Training t WHERE (SELECT MAX(tr.startTraining) FROM Training tr where tr.user.email = :email) = t.startTraining and t.user.email = :email and t.status = :status"),
        }
)
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_training")
    private LocalDateTime startTraining;
    @Column(name = "end_training")
    private LocalDateTime endTraining;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(mappedBy = "trainings")
    private List<Exercise> exercises = new ArrayList<>();

    public void addUser(User user) {
        this.user = user;
        user.getTrainings().add(this);
    }

}

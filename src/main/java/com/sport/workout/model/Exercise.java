package com.sport.workout.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"approaches", "trainings"})
@EqualsAndHashCode(exclude = {"approaches", "trainings"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@NamedQueries(
        {
                @NamedQuery(name = "Training.findLastUserExerciseTraining", query = "SELECT e FROM Exercise e LEFT JOIN Training t ON t.id = :trainingId")
        }
)
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "muscle_group")
    private String muscleGroup;
    @Column(name = "exercise")
    private String exercise;
    @Builder.Default
    @ManyToMany
    @JoinTable(name = "exercise_training",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "training_id"))
    private List<Training> trainings = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "exercise_approach",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "approach_id"))
    private List<Approach> approaches = new ArrayList<>();

    public void addApproach(Approach approach) {
        this.approaches.add(approach);
        approach.getExercises().add(this);
    }

    public void addTraining(Training training) {
        this.trainings.add(training);
        training.getExercises().add(this);
    }

}

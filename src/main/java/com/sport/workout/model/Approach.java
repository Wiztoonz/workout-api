package com.sport.workout.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "exercises")
@EqualsAndHashCode(exclude = "exercises")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Approach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "repetitions")
    private Integer repetitions;
    @Column(name = "weight")
    private Integer weight;

    @Builder.Default
    @ManyToMany(mappedBy = "approaches")
    private List<Exercise> exercises = new ArrayList<>();

}

package com.project.smartcourse.model;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String courseCode;
    private String title;
    private String description;
    private int maxCapacity;
    private int currentEnrollment;


    @ManyToOne
    private Instructor instructor;

    @ManyToMany(fetch = FetchType.LAZY)//we can specify fetch type, by default it is lazy and not going to show list of notepads but in eager type it will show
    private List<Student> students = new ArrayList<>();

}

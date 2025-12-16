package com.project.smartcourse.model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fullName;

    private String department;

    private Date dateOfBirth;

    // FIX 1: Add mappedBy to avoid extra join table
    // FIX 2: Initialize the list to avoid NullPointerException
    @OneToMany(mappedBy = "instructor")// Tells JPA: "Look at the 'instructor' field in Course to find the config"
    private List<Course> courses = new ArrayList<>();

}

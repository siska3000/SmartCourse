package com.project.smartcourse.repo;


import com.project.smartcourse.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {


    Student findByFirstName(String firstName);
}

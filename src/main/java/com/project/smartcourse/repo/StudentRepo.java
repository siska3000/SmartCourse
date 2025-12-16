package com.project.smartcourse.repo;


import com.project.smartcourse.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {


    Student findByFirstName(String firstName);

    @Query("from Student where courses is empty")
    List<Student> findStudentsWhereCourseIsEmpty();
}

package com.project.smartcourse.repo;


import com.project.smartcourse.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {


    Student findByFirstName(String firstName);

    @Query("from Student where courses is empty")
    Page<Student> findStudentWhereCourseIsEmpty(Pageable pageable);
}

package com.project.smartcourse.repo;

import com.project.smartcourse.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {


    @Query("from Course where currentEnrollment > (maxCapacity/100 * 80)")
    List<Course> findAlmostFullCourses();
}

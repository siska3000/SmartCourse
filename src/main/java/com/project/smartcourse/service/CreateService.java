package com.project.smartcourse.service;

import com.project.smartcourse.exceptions.ResourceNotFoundException;
import com.project.smartcourse.model.Course;
import com.project.smartcourse.model.Instructor;
import com.project.smartcourse.model.Student;
import com.project.smartcourse.repo.CourseRepo;
import com.project.smartcourse.repo.InstructorRepo;
import com.project.smartcourse.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional//use @Transactional in your Service to keep the session open when you need to access the list.
public class CreateService {

    final CourseRepo courseRepo;
    final StudentRepo studentRepo;
    final InstructorRepo instructorRepo;

    @Autowired
    CreateService(CourseRepo courseRepo, StudentRepo studentRepo, InstructorRepo instructorRepo) {
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
        this.instructorRepo = instructorRepo;
    }

    public void saveCourseAndAssignInstructor(Course course) throws ResourceNotFoundException {
        Instructor instructor = course.getInstructor();
        if (instructor == null) {
            throw new IllegalArgumentException("Course must have an instructor assigned before saving.");
        }
        Instructor finalInstructor = instructor;
        instructor = instructorRepo
                .findById(instructor.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + finalInstructor.getId()));


        instructor.getCourses().add(course);
        course.setInstructor(instructor);


        courseRepo.save(course);
    }

    public void addInstructor(Instructor instructor) {
        instructorRepo.save(instructor);
    }


    public void addStudent(Student student) {
        studentRepo.save(student);
    }
}

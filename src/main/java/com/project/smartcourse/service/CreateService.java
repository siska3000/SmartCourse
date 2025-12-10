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

@Service
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

    public Course saveCourseAndAssignInstructor(Course course) throws ResourceNotFoundException {
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


        return courseRepo.save(course);
    }

    public Instructor addInstructor(Instructor instructor) {
        return instructorRepo.save(instructor);
    }


    public Student addStudent(Student student) {
        return studentRepo.save(student);
    }
}

package com.project.smartcourse.service;


import com.project.smartcourse.dto.CourseDTO;
import com.project.smartcourse.dto.StudentDTO;
import com.project.smartcourse.exceptions.CourseFullException;
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

import java.util.List;

@Service
@Transactional
public class CourseService {

    final CourseRepo courseRepo;
    final StudentRepo studentRepo;
    final InstructorRepo instructorRepo;

    @Autowired
    CourseService(CourseRepo courseRepo, StudentRepo studentRepo, InstructorRepo instructorRepo) {
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
        this.instructorRepo = instructorRepo;
    }


    public void Enroll(int studentId, int courseId) throws ResourceNotFoundException, CourseFullException {

        Student student = studentRepo.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        if (course.getStudents().contains(student)) {
            throw new CourseFullException("Student cannot apply to the same course again!!!");
        }

        else if(course.getCurrentEnrollment()<course.getMaxCapacity()){
            course.getStudents().add(student);
            course.setCurrentEnrollment(course.getCurrentEnrollment()+1);
        }
        else{
            throw new CourseFullException("Course is full!!!");
        }

        student.getCourses().add(course);
        courseRepo.save(course);
        studentRepo.save(student);
    }


    public CourseDTO getCourse(int id) throws ResourceNotFoundException {
        if (courseRepo.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Course with this id does not exists");
        }
        Course course = courseRepo.findById(id).get();
        return CourseDTO.builder()
                .courseDescription(course.getDescription())

                .courseTitle(course.getTitle())
                .instructorName(course.getInstructor()
                        .getFullName())

                .studentNames(course.getStudents()
                        .stream()
                        .map(Student::getFirstName)
                        .toList())
                .build();
    }


    public List<CourseDTO> getInstructorCourses(int id) {
        Instructor instructor = instructorRepo.getReferenceById(id);
        List<Course> courses = instructor.getCourses();

        return courses.stream()
                .map(course -> {
            try {
                return getCourse(course.getId());
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    public StudentDTO findStudentByFirstName(String name) {
        Student student = studentRepo.findByFirstName(name);

        return StudentDTO.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .dateOfBirth(student.getDateOfBirth())
                .build();
    }


    public List<StudentDTO> findStudentWithoutCourse(){
        List<Student> students = studentRepo.findStudentsWhereCourseIsEmpty();

        return students.stream().map(student -> StudentDTO.builder()
                        .firstName(student.getFirstName())
                        .lastName(student.getLastName())
                        .email(student.getEmail())
                        .dateOfBirth(student.getDateOfBirth())
                        .build()).toList();
    }
}

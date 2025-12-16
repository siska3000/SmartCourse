package com.project.smartcourse.controller;

import com.project.smartcourse.dto.CourseDTO;
import com.project.smartcourse.dto.StudentDTO;
import com.project.smartcourse.exceptions.CourseFullException;
import com.project.smartcourse.exceptions.ResourceNotFoundException;
import com.project.smartcourse.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Replaces @Controller + @ResponseBody
@RequestMapping("/api") // Good practice to version APIs
@Slf4j // This annotation generates 'private static final Logger log = LoggerFactory.getLogger(YourClass.class);'
public class CourseController {

    final CourseService courseService;

    @Autowired
    CourseController(CourseService courseService) {
        log.info("Autowired courseService");
        this.courseService = courseService;
    }



    @PostMapping(value ="/enrollStudent/{sId}&{cId}")
    public void enrollStudent(@PathVariable int sId, @PathVariable int cId) {
        try {
            courseService.Enroll(sId, cId);
            log.info("Enrolled student with id: {} on course with id: {}", sId, cId);
        } catch (ResourceNotFoundException | CourseFullException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping(value = "course/{id}", produces = "application/json")
    public CourseDTO getCourse(@PathVariable int id) {
        try {
            log.info("Showed course data with DTO object with courseId: {}", id);
            return courseService.getCourse(id);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping(value = "student/{name}", produces = "application/json")
    public StudentDTO getStudent(@PathVariable String name) {
        log.info("Showed student data, with student first name: {}", name);
        return courseService.findStudentByFirstName(name);
    }


    @GetMapping(value = "instructorCourses/{id}", produces = "application/json")
    public List<CourseDTO> getInstructorCourses(@PathVariable int id) {
        log.info("Showed instructors list of courses, with instructorId {}", id);
        return courseService.getInstructorCourses(id);
    }

    @GetMapping(value = "studentsWithoutCourse", produces = "application/json")
    public List<StudentDTO> getStudentsWithoutCourse() {
        log.info("Showed list of students without course");
        return courseService.findStudentWithoutCourse();
    }

}

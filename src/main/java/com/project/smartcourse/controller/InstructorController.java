package com.project.smartcourse.controller;

import com.project.smartcourse.exceptions.ResourceNotFoundException;
import com.project.smartcourse.model.Course;
import com.project.smartcourse.model.Instructor;
import com.project.smartcourse.model.Student;
import com.project.smartcourse.service.CreateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // Replaces @Controller + @ResponseBody
@RequestMapping("/api") // Good practice to version APIs
@Slf4j
public class InstructorController {

    final CreateService createService;

    @Autowired
    InstructorController(CreateService createService) {
        log.info("Autowired createService");
        this.createService = createService;
    }


    @PostMapping(value = "/instructor")
    public Instructor createInstructor(@RequestBody Instructor instructor) {
        log.info("created Instructor");
        return createService.addInstructor(instructor);
    }


    @PostMapping(value = "/student")
    public Student createStudent(@RequestBody Student student) {
        log.info("created Student");
        return createService.addStudent(student);
    }


    @PostMapping(value ="/course")
    public Course createCourse(@RequestBody Course course) {
        try {
            log.info("created Course with id {}, and assigned Instructor to it with id {}", course.getId(), course.getInstructor().getId());
            return createService.saveCourseAndAssignInstructor(course);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}

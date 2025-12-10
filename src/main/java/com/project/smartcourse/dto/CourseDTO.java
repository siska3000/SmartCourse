package com.project.smartcourse.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class CourseDTO {
    private String courseTitle;
    private String courseDescription;
    private String instructorName;
    private List<String> studentNames;
}

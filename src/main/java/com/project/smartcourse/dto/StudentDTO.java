package com.project.smartcourse.dto;


import lombok.*;
import java.sql.Date;



@Getter
@Setter
@Builder
public class StudentDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
}

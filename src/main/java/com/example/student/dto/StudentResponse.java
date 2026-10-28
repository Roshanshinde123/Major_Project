package com.example.student.dto;

import lombok.Data;

@Data
public class StudentResponse {

    private Long id;
    private Long userId;
    private String fullName;
    private String email;
    private String enrollmentNumber;
    private String branch;
    private Integer graduationYear;
    private Double cgpa;
    private Integer activeBacklogs;
    private String skills;
    private String resumeUrl;
}
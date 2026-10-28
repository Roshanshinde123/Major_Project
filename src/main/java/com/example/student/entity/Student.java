
package com.example.student.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "students")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String enrollmentNumber;

    private String branch;

    private Integer graduationYear;

    private Double cgpa;

    private Integer activeBacklogs;

    private String skills;

    private String resumeUrl;
}

package com.example.student.entity;


import jakarta.persistence.*;
import jakarta.persistence.criteria.Fetch;
import lombok.Data;

@Entity
@Table(name = "projects")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false)
    private String title;

    @Column(length = 1000)
    private String description;

    private String technologies;

    private String githubUrl;
    private String liveUrl;


    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="student_id",nullable = false)
    private Student student;

}

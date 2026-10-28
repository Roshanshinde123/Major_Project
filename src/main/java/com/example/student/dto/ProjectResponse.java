package com.example.student.dto;

import lombok.Data;

@Data
public class ProjectResponse {
    private Long id;
    private Long studentId;
    private String title;
    private String description;
    private String technologies;
    private String githubUrl;
    private String liveUrl;

}

package com.example.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectRequest {

    @NotBlank(message = "Project title is Required")
    private String title;

    @Size(max=1000,message = "Description cannot exceed 1000 characters")
    private String description;

    private String technologies;
    private String githubUrl;
    private String liveUrl;

}

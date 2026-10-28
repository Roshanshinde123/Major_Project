package com.example.student.controller;

import com.example.student.dto.ProjectRequest;
import com.example.student.dto.ProjectResponse;
import com.example.student.service.ProjectService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/students/{studentId}/projects")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse addProject(
            @PathVariable Long studentId,
            @Valid @RequestBody ProjectRequest request) {

        return projectService.addProject(studentId, request);
    }

    @GetMapping("/students/{studentId}/projects")
    public List<ProjectResponse> getProjects(
            @PathVariable Long studentId) {

        return projectService.getProjectsByStudent(studentId);
    }

    @GetMapping("/projects/{projectId}")
    public ProjectResponse getProject(
            @PathVariable Long projectId) {

        return projectService.getProjectById(projectId);
    }

    @PutMapping("/projects/{projectId}")
    public ProjectResponse updateProject(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectRequest request) {

        return projectService.updateProject(projectId, request);
    }

    @DeleteMapping("/projects/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long projectId) {

        projectService.deleteProject(projectId);
    }
}
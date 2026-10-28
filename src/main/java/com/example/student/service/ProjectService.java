
package com.example.student.service;

import com.example.student.dto.ProjectRequest;
import com.example.student.dto.ProjectResponse;
import com.example.student.entity.Project;
import com.example.student.entity.Student;
import com.example.student.exception.ResourceNotFoundException;
import com.example.student.repository.ProjectRepository;
import com.example.student.repository.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            StudentRepository studentRepository) {
        this.projectRepository = projectRepository;
        this.studentRepository = studentRepository;
    }

    // Convert Entity to Response DTO
    private ProjectResponse convertToResponse(Project project) {

        ProjectResponse response = new ProjectResponse();

        response.setId(project.getId());
        response.setStudentId(project.getStudent().getId());
        response.setTitle(project.getTitle());
        response.setDescription(project.getDescription());
        response.setTechnologies(project.getTechnologies());
        response.setGithubUrl(project.getGithubUrl());
        response.setLiveUrl(project.getLiveUrl());

        return response;
    }

    // Add Project
    @Transactional
    public ProjectResponse addProject(Long studentId, ProjectRequest request) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with id: " + studentId));

        Project project = new Project();

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setTechnologies(request.getTechnologies());
        project.setGithubUrl(request.getGithubUrl());
        project.setLiveUrl(request.getLiveUrl());
        project.setStudent(student);

        return convertToResponse(projectRepository.save(project));
    }

    // Get All Projects of a Student
    @Transactional(readOnly = true)
    public List<ProjectResponse> getProjectsByStudent(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException(
                    "Student not found with id: " + studentId);
        }

        return projectRepository.findByStudentId(studentId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Get Project by ID
    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        return convertToResponse(project);
    }

    // Update Project
    @Transactional
    public ProjectResponse updateProject(
            Long projectId, ProjectRequest request) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setTechnologies(request.getTechnologies());
        project.setGithubUrl(request.getGithubUrl());
        project.setLiveUrl(request.getLiveUrl());

        return convertToResponse(projectRepository.save(project));
    }

    // Delete Project
    @Transactional
    public void deleteProject(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId));

        projectRepository.delete(project);
    }
}

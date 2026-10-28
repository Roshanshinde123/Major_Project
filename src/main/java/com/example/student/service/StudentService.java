
package com.example.student.service;

import com.example.student.dto.StudentRequest;
import com.example.student.dto.StudentResponse;
import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.student.exception.ResourceNotFoundException;
import com.example.student.exception.DuplicateResourceException;

import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    private StudentResponse convertToResponse(Student student) {

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setFullName(student.getFullName());
        response.setEmail(student.getEmail());
        response.setEnrollmentNumber(student.getEnrollmentNumber());
        response.setBranch(student.getBranch());
        response.setGraduationYear(student.getGraduationYear());
        response.setCgpa(student.getCgpa());
        response.setActiveBacklogs(student.getActiveBacklogs());
        response.setSkills(student.getSkills());
        response.setResumeUrl(student.getResumeUrl());

        return response;
    }

    private Student convertToEntity(StudentRequest request) {

        Student student = new Student();

        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setEnrollmentNumber(request.getEnrollmentNumber());
        student.setBranch(request.getBranch());
        student.setGraduationYear(request.getGraduationYear());
        student.setCgpa(request.getCgpa());
        student.setActiveBacklogs(request.getActiveBacklogs());
        student.setSkills(request.getSkills());

        return student;
    }

    public StudentResponse addStudent(StudentRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        Student student = convertToEntity(request);

        return convertToResponse(repository.save(student));
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {

        return repository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {

        Student student = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with id: "+id));

        return convertToResponse(student);
    }

    public StudentResponse updateStudent(Long id, StudentRequest request) {

        Student student = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with id: "+id));

        if (repository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new DuplicateResourceException("Email already belongs to another student");
        }

        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setEnrollmentNumber(request.getEnrollmentNumber());
        student.setBranch(request.getBranch());
        student.setGraduationYear(request.getGraduationYear());
        student.setCgpa(request.getCgpa());
        student.setActiveBacklogs(request.getActiveBacklogs());
        student.setSkills(request.getSkills());

        return convertToResponse(repository.save(student));
    }

    public void deleteStudent(Long id) {

        Student student = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with id: "+id));

        repository.delete(student);
    }
}

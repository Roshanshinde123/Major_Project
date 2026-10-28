
package com.example.student.service;

import com.example.student.dto.CertificationRequest;
import com.example.student.dto.CertificationResponse;
import com.example.student.entity.Certification;
import com.example.student.entity.Student;
import com.example.student.exception.ResourceNotFoundException;
import com.example.student.repository.CertificationRepository;
import com.example.student.repository.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final StudentRepository studentRepository;

    public CertificationService(
            CertificationRepository certificationRepository,
            StudentRepository studentRepository) {

        this.certificationRepository = certificationRepository;
        this.studentRepository = studentRepository;
    }

    // Convert Entity to Response DTO
    private CertificationResponse convertToResponse(
            Certification certification) {

        CertificationResponse response = new CertificationResponse();

        response.setId(certification.getId());
        response.setStudentId(certification.getStudent().getId());
        response.setTitle(certification.getTitle());
        response.setIssuingOrganization(
                certification.getIssuingOrganization());
        response.setIssueDate(certification.getIssueDate());
        response.setExpiryDate(certification.getExpiryDate());
        response.setCredentialId(certification.getCredentialId());
        response.setCredentialUrl(certification.getCredentialUrl());

        return response;
    }

    // Add Certification
    @Transactional
    public CertificationResponse addCertification(
            Long studentId, CertificationRequest request) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with id: " + studentId));

        Certification certification = new Certification();

        certification.setTitle(request.getTitle());
        certification.setIssuingOrganization(
                request.getIssuingOrganization());
        certification.setIssueDate(request.getIssueDate());
        certification.setExpiryDate(request.getExpiryDate());
        certification.setCredentialId(request.getCredentialId());
        certification.setCredentialUrl(request.getCredentialUrl());
        certification.setStudent(student);

        return convertToResponse(
                certificationRepository.save(certification));
    }

    // Get All Certifications of a Student
    @Transactional(readOnly = true)
    public List<CertificationResponse> getCertificationsByStudent(
            Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException(
                    "Student not found with id: " + studentId);
        }

        return certificationRepository.findByStudentId(studentId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Get Certification by ID
    @Transactional(readOnly = true)
    public CertificationResponse getCertificationById(Long certificationId) {

        Certification certification =
                certificationRepository.findById(certificationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Certification not found with id: "
                                                + certificationId));

        return convertToResponse(certification);
    }

    // Update Certification
    @Transactional
    public CertificationResponse updateCertification(
            Long certificationId, CertificationRequest request) {

        Certification certification =
                certificationRepository.findById(certificationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Certification not found with id: "
                                                + certificationId));

        certification.setTitle(request.getTitle());
        certification.setIssuingOrganization(
                request.getIssuingOrganization());
        certification.setIssueDate(request.getIssueDate());
        certification.setExpiryDate(request.getExpiryDate());
        certification.setCredentialId(request.getCredentialId());
        certification.setCredentialUrl(request.getCredentialUrl());

        return convertToResponse(
                certificationRepository.save(certification));
    }

    // Delete Certification
    @Transactional
    public void deleteCertification(Long certificationId) {

        Certification certification =
                certificationRepository.findById(certificationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Certification not found with id: "
                                                + certificationId));

        certificationRepository.delete(certification);
    }
}

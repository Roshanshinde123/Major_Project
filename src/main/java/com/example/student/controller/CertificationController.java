
package com.example.student.controller;

import com.example.student.dto.CertificationRequest;
import com.example.student.dto.CertificationResponse;
import com.example.student.service.CertificationService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(
            CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    // Add Certification
    @PostMapping("/students/{studentId}/certifications")
    @ResponseStatus(HttpStatus.CREATED)
    public CertificationResponse addCertification(
            @PathVariable Long studentId,
            @Valid @RequestBody CertificationRequest request) {

        return certificationService.addCertification(studentId, request);
    }

    // Get All Certifications of a Student
    @GetMapping("/students/{studentId}/certifications")
    public List<CertificationResponse> getCertifications(
            @PathVariable Long studentId) {

        return certificationService.getCertificationsByStudent(studentId);
    }

    // Get Certification by ID
    @GetMapping("/certifications/{certificationId}")
    public CertificationResponse getCertification(
            @PathVariable Long certificationId) {

        return certificationService.getCertificationById(certificationId);
    }

    // Update Certification
    @PutMapping("/certifications/{certificationId}")
    public CertificationResponse updateCertification(
            @PathVariable Long certificationId,
            @Valid @RequestBody CertificationRequest request) {

        return certificationService.updateCertification(
                certificationId, request);
    }

    // Delete Certification
    @DeleteMapping("/certifications/{certificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertification(
            @PathVariable Long certificationId) {

        certificationService.deleteCertification(certificationId);
    }
}

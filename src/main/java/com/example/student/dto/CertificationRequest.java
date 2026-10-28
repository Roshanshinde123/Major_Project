package com.example.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CertificationRequest {

    @NotBlank(message = "Certification title is required")
    private String title;

    @NotBlank(message = "Issuing Organization is required")
    private String issuingOrganization;

    @PastOrPresent(message = "Issue date cannot be in the future")
    private LocalDate issueDate;

    private LocalDate expiryDate;
    private String credentialId;
    private String credentialUrl;


}

package com.example.student.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CertificationResponse {
    private Long id;
    private Long studentId;
    private String title;
    private String issuingOrganization;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String credentialId;
    private String credentialUrl;

}



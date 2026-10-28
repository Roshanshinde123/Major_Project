package com.example.student.repository;

import com.example.student.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification,Long> {
    List<Certification> findByStudentId(Long studentId);
}

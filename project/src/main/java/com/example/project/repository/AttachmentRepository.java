package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.Attachment;

public interface  AttachmentRepository extends JpaRepository<Attachment, Long>{
    
}

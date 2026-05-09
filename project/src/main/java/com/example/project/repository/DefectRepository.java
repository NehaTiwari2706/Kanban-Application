package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.Defect;

public interface  DefectRepository extends JpaRepository<Defect, Long>{
    
}

package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.Defect;
import java.util.List;

public interface  DefectRepository extends JpaRepository<Defect, Long>{
    
    List<Defect> findByIterationId(Long iterationId);

    List<Defect> findByUserStoryId(Long userStoryId);
}

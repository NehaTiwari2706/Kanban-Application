package com.example.project.service;

import org.springframework.stereotype.Service;
import com.example.project.dto.DefectDTO;
import com.example.project.entity.Defect;
import com.example.project.entity.Iteration;
import com.example.project.entity.User;
import com.example.project.entity.UserStory;
import com.example.project.entity.Defect.Status;
import com.example.project.entity.Defect.Priority;

import com.example.project.repository.IterationRepository;
import com.example.project.repository.DefectRepository;
import com.example.project.repository.UserRepository;
import com.example.project.repository.UserStoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DefectService {

    @Autowired
    DefectRepository defectRepository;

    @Autowired
    private UserStoryRepository userStoryRepository;

    @Autowired
    private IterationRepository iterationRepository;

    @Autowired
    private UserRepository userRepository;
    
    public String createDefect(DefectDTO dto){

        UserStory userStory = null;

        if (dto.getUserStoryId() != null) {
            userStory = userStoryRepository.findById(dto.getUserStoryId())
            .orElseThrow(() -> new RuntimeException("User story not found"));
        }

        Iteration iteration = null;

        if (dto.getIterationId() != null) {
            iteration = iterationRepository.findById(dto.getIterationId())
            .orElseThrow(() -> new RuntimeException("Iteration not found"));
        }

        if (dto.getUserStoryId() == null && dto.getIterationId() == null) {
            throw new RuntimeException(
                "Defect must belong to either User Story or Iteration");
        }

        User createdBy = userRepository.findById(dto.getCreatedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User assignedTo = userRepository.findById(dto.getAssignedToId())
                .orElseThrow(() -> new RuntimeException("Assigned user not found"));

        // Convert enums safely
        Status status;
        try {
            status = Status.valueOf(dto.getStatus().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid status. Use TODO, IN_PROGRESS, DONE");
        }

        Priority priority;
        try {
            priority = Priority.valueOf(dto.getPriority().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid priority. Use LOW, MEDIUM, HIGH, CRITICAL");
        }


        Defect defect = new Defect();
        defect.setTitle(dto.getTitle());
        defect.setDescription(dto.getDescription());
        defect.setStatus(status);
        defect.setPriority(priority);
        defect.setUserStory(userStory);
        defect.setIteration(iteration);
        defect.setEstimatedTime(dto.getEstimatedTime());
        defect.setActualTime(dto.getActualTime());
        defect.setAssignedTo(assignedTo);
        defect.setCreatedBy(createdBy);

        defectRepository.save(defect);

        return "Defect Created Successfully";
    }

    public List<DefectDTO> getDefects(Long iterationId, Long userStoryId){

        List<Defect> defects;

        if(iterationId != null){
            defects = defectRepository.findByIterationId(iterationId);
        }
        else if(userStoryId != null){
            defects = defectRepository.findByUserStoryId(userStoryId);
        }
        else{
            defects = defectRepository.findAll();
        }

        return defects.stream().map(this::convertToDTO).toList();
    }


    private DefectDTO convertToDTO(Defect defect) {

        DefectDTO dto = new DefectDTO();

        dto.setId(defect.getId());
        dto.setTitle(defect.getTitle());
        dto.setDescription(defect.getDescription());

        dto.setStatus(defect.getStatus().name());
        dto.setPriority(defect.getPriority().name());

        dto.setEstimatedTime(defect.getEstimatedTime());
        dto.setActualTime(defect.getActualTime());

        if (defect.getAssignedTo() != null) {
            dto.setAssignedToId(defect.getAssignedTo().getId());
        }

        if (defect.getCreatedBy() != null) {
            dto.setCreatedById(defect.getCreatedBy().getId());
        }

        if (defect.getUserStory() != null) {
            dto.setUserStoryId(defect.getUserStory().getId());
        }

        if (defect.getIteration() != null) {
            dto.setIterationId(defect.getIteration().getId());
        }

        dto.setCreatedAt(defect.getCreatedAt());
        dto.setUpdatedAt(defect.getUpdatedAt());

        return dto;
    }

    public String updateDefects(Long id, DefectDTO dto){

        Defect defect = defectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Defect not found"));

        // Update editable fields
        defect.setTitle(dto.getTitle());
        defect.setDescription(dto.getDescription());

        // Status
        try {
            defect.setStatus(
                    Status.valueOf(dto.getStatus().toUpperCase()));
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid status. Use OPEN, IN_PROGRESS, RESOLVED, CLOSED");
        }

        // Priority
        try {
            defect.setPriority(
                    Priority.valueOf(dto.getPriority().toUpperCase()));
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid priority. Use LOW, MEDIUM, HIGH, CRITICAL");
        }

        // Assigned user
        User assignedTo = userRepository.findById(dto.getAssignedToId())
                .orElseThrow(() -> new RuntimeException("Assigned user not found"));

        defect.setAssignedTo(assignedTo);

        defect.setEstimatedTime(dto.getEstimatedTime());
        defect.setActualTime(dto.getActualTime());

        defectRepository.save(defect);

        return "Defect updated successfully";
    }

    public String deleteUserStory(Long id){

        if(!defectRepository.existsById(id)){
            return "Defect not found";
        }
        defectRepository.deleteById(id);
        return "Defect Deleted Successfully";
    }
}

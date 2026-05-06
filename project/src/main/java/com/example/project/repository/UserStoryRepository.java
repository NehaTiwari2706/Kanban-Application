package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.project.entity.UserStory;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Long> {

    @Query("SELECT COALESCE(MAX(us.userStoryNumber), 0) FROM UserStory us WHERE us.iteration.id = :iterationId")
    public int findMaxUserStoryNumberByIterationId(Long iterationId);

    List<UserStory> findByIterationId(Long iterationId);
} 
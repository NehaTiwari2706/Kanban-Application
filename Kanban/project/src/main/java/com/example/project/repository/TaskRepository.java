package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.entity.Task;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
           SELECT COALESCE(MAX(t.taskNumber), 0)
           FROM Task t
           WHERE t.userStory.id = :userStoryId
           """)
    public int findMaxTaskNumberByUserStoryId(Long userStoryId);

    List<Task> findByUserStoryId(Long userStoryId);
}
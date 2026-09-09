package com.example.project.repository;

import com.example.project.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
           SELECT COALESCE(MAX(t.taskNumber), 0)
           FROM Task t
           WHERE t.userStory.id = :userStoryId
           """)
    int findMaxTaskNumberByUserStoryId(Long userStoryId);

    List<Task> findByUserStoryId(Long userStoryId);

    long countByStatus(Task.Status status);

    @Query("""
           SELECT COUNT(t)
           FROM Task t
           JOIN t.userStory us
           JOIN us.iteration i
           WHERE i.id = :iterationId
           """)
    long countByIterationId(Long iterationId);

    @Query("""
           SELECT COUNT(t)
           FROM Task t
           JOIN t.userStory us
           JOIN us.iteration i
           WHERE i.id = :iterationId
             AND t.status = :status
           """)
    long countByIterationIdAndStatus(Long iterationId, Task.Status status);

    List<Task> findByAssignedToId(Long userId);
}
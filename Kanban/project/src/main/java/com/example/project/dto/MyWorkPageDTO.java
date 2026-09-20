package com.example.project.dto;

import java.util.ArrayList;
import java.util.List;

public class MyWorkPageDTO {
    private List<TaskCardDTO> todo = new ArrayList<>();
    private List<TaskCardDTO> inProgress = new ArrayList<>();
    private List<TaskCardDTO> done = new ArrayList<>();
    private long totalElements;
    private int totalPages;

    public List<TaskCardDTO> getTodo() {
        return todo;
    }

    public void setTodo(List<TaskCardDTO> todo) {
        this.todo = todo;
    }

    public List<TaskCardDTO> getInProgress() {
        return inProgress;
    }

    public void setInProgress(List<TaskCardDTO> inProgress) {
        this.inProgress = inProgress;
    }

    public List<TaskCardDTO> getDone() {
        return done;
    }

    public void setDone(List<TaskCardDTO> done) {
        this.done = done;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

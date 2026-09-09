package com.example.project.dto;

import java.time.LocalDate;
import java.util.List;

public class DashboardDTO {

    private UserSummaryDTO user;
    private DashboardSummaryDTO summary;
    private IterationSummaryDTO currentIteration;
    private List<TaskSummaryDTO> myWork;

    public UserSummaryDTO getUser() {
        return user;
    }

    public void setUser(UserSummaryDTO user) {
        this.user = user;
    }

    public DashboardSummaryDTO getSummary() {
        return summary;
    }

    public void setSummary(DashboardSummaryDTO summary) {
        this.summary = summary;
    }

    public IterationSummaryDTO getCurrentIteration() {
        return currentIteration;
    }

    public void setCurrentIteration(IterationSummaryDTO currentIteration) {
        this.currentIteration = currentIteration;
    }

    public List<TaskSummaryDTO> getMyWork() {
        return myWork;
    }

    public void setMyWork(List<TaskSummaryDTO> myWork) {
        this.myWork = myWork;
    }

    public static class UserSummaryDTO {
        private Long id;
        private String name;

        public UserSummaryDTO() {
        }

        public UserSummaryDTO(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class DashboardSummaryDTO {
        private long totalTasks;
        private long inProgress;
        private long completed;
        private long defects;

        public DashboardSummaryDTO() {
        }

        public DashboardSummaryDTO(long totalTasks, long inProgress, long completed, long defects) {
            this.totalTasks = totalTasks;
            this.inProgress = inProgress;
            this.completed = completed;
            this.defects = defects;
        }

        public long getTotalTasks() {
            return totalTasks;
        }

        public void setTotalTasks(long totalTasks) {
            this.totalTasks = totalTasks;
        }

        public long getInProgress() {
            return inProgress;
        }

        public void setInProgress(long inProgress) {
            this.inProgress = inProgress;
        }

        public long getCompleted() {
            return completed;
        }

        public void setCompleted(long completed) {
            this.completed = completed;
        }

        public long getDefects() {
            return defects;
        }

        public void setDefects(long defects) {
            this.defects = defects;
        }
    }

    public static class IterationSummaryDTO {
        private Long id;
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        private int progress;

        public IterationSummaryDTO() {
        }

        public IterationSummaryDTO(Long id, String name, LocalDate startDate, LocalDate endDate, int progress) {
            this.id = id;
            this.name = name;
            this.startDate = startDate;
            this.endDate = endDate;
            this.progress = progress;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }
    }

    public static class TaskSummaryDTO {
        private Long id;
        private String title;
        private String status;
        private String priority;

        public TaskSummaryDTO() {
        }

        public TaskSummaryDTO(Long id, String title, String status, String priority) {
            this.id = id;
            this.title = title;
            this.status = status;
            this.priority = priority;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }
    }
}

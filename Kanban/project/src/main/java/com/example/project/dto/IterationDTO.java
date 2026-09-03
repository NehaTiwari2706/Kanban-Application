package com.example.project.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IterationDTO {

    private Long id;

    @NotBlank(message = "Iteration number is required")
    private String iterationnumber;

    @NotBlank(message = "Iteration name is required")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Start date cannot be in past")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String status;

    private Long teamId;
}
package com.assessment.leavemgmt.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequestDTO {

    @NotNull(message = "Employee ID must not be null")
    private Long employeeId;

    @NotBlank(message = "Leave type must not be blank")
    private String leaveType;

    @NotNull(message = "Start date must not be null")
    @FutureOrPresent(message = "Start date must not be in the past")
    private LocalDate startDate;

    @NotNull(message = "End date must not be null")
    private LocalDate endDate;

    @NotBlank(message = "Leave reason must not be blank")
    private String reason;
}

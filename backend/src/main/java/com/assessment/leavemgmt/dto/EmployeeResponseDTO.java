package com.assessment.leavemgmt.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDTO {

    private Long employeeId;
    private String employeeName;
    private String email;
    private String department;
    private String designation;
    private LocalDate joiningDate;
}

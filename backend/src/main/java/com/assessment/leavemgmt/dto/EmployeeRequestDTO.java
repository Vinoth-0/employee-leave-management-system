package com.assessment.leavemgmt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDTO {

    @NotBlank(message = "Employee name must not be blank")
    private String employeeName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Department must not be empty")
    private String department;

    @NotBlank(message = "Designation must not be empty")
    private String designation;

    @NotNull(message = "Joining date must not be null")
    private LocalDate joiningDate;
}

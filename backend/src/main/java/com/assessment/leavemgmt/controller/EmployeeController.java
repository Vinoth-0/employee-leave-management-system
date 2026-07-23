package com.assessment.leavemgmt.controller;

import com.assessment.leavemgmt.dto.ApiResponse;
import com.assessment.leavemgmt.dto.EmployeeRequestDTO;
import com.assessment.leavemgmt.dto.EmployeeResponseDTO;
import com.assessment.leavemgmt.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee Management", description = "APIs for managing employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @Operation(summary = "Create a new employee")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> createEmployee(
            @Valid @RequestBody EmployeeRequestDTO requestDTO) {
        EmployeeResponseDTO created = employeeService.createEmployee(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Employee created successfully", created));
    }

    @GetMapping
    @Operation(summary = "Get all employees (with optional pagination and sorting)")
    public ResponseEntity<ApiResponse<?>> getAllEmployees(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, defaultValue = "employeeName") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<EmployeeResponseDTO> result = employeeService.getAllEmployeesPaginated(pageable);
            return ResponseEntity.ok(ApiResponse.success("Employees fetched successfully", result));
        }

        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(ApiResponse.success("Employees fetched successfully", employees));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Employee fetched successfully",
                employeeService.getEmployeeById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employee details")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDTO requestDTO) {
        return ResponseEntity.ok(ApiResponse.success("Employee updated successfully",
                employeeService.updateEmployee(id, requestDTO)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an employee")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(ApiResponse.success("Employee deleted successfully", null));
    }

    @GetMapping("/department/{department}")
    @Operation(summary = "Get employees by department")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> getByDepartment(
            @PathVariable String department) {
        return ResponseEntity.ok(ApiResponse.success("Employees fetched successfully",
                employeeService.getEmployeesByDepartment(department)));
    }

    @GetMapping("/{id}/leave-days")
    @Operation(summary = "Get total approved leave days taken by an employee")
    public ResponseEntity<ApiResponse<Long>> getTotalLeaveDays(@PathVariable Long id) {
        long days = employeeService.getTotalLeaveDaysTaken(id);
        return ResponseEntity.ok(ApiResponse.success("Total approved leave days: " + days, days));
    }
}

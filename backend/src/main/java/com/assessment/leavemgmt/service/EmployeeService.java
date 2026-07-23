package com.assessment.leavemgmt.service;

import com.assessment.leavemgmt.dto.EmployeeRequestDTO;
import com.assessment.leavemgmt.dto.EmployeeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO);

    List<EmployeeResponseDTO> getAllEmployees();

    Page<EmployeeResponseDTO> getAllEmployeesPaginated(Pageable pageable);

    EmployeeResponseDTO getEmployeeById(Long id);

    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO requestDTO);

    void deleteEmployee(Long id);

    List<EmployeeResponseDTO> getEmployeesByDepartment(String department);

    long getTotalLeaveDaysTaken(Long employeeId);
}

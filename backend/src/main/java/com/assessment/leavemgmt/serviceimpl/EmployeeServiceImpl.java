package com.assessment.leavemgmt.serviceimpl;

import com.assessment.leavemgmt.dto.EmployeeRequestDTO;
import com.assessment.leavemgmt.dto.EmployeeResponseDTO;
import com.assessment.leavemgmt.entity.Employee;
import com.assessment.leavemgmt.exception.DuplicateEmailException;
import com.assessment.leavemgmt.exception.ResourceNotFoundException;
import com.assessment.leavemgmt.repository.EmployeeRepository;
import com.assessment.leavemgmt.repository.LeaveRequestRepository;
import com.assessment.leavemgmt.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) {
        if (employeeRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateEmailException(requestDTO.getEmail());
        }
        Employee employee = mapToEntity(requestDTO);
        Employee saved = employeeRepository.save(employee);
        return mapToResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponseDTO> getAllEmployeesPaginated(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(this::mapToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = findEmployeeById(id);
        return mapToResponseDTO(employee);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO requestDTO) {
        Employee employee = findEmployeeById(id);

        // Check if email is being changed and if the new email already exists
        if (!employee.getEmail().equals(requestDTO.getEmail()) &&
                employeeRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateEmailException(requestDTO.getEmail());
        }

        employee.setEmployeeName(requestDTO.getEmployeeName());
        employee.setEmail(requestDTO.getEmail());
        employee.setDepartment(requestDTO.getDepartment());
        employee.setDesignation(requestDTO.getDesignation());
        employee.setJoiningDate(requestDTO.getJoiningDate());

        return mapToResponseDTO(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = findEmployeeById(id);
        employeeRepository.delete(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalLeaveDaysTaken(Long employeeId) {
        findEmployeeById(employeeId); // validate employee exists
        return leaveRequestRepository.sumApprovedLeaveDaysByEmployee(employeeId);
    }

    // ---- Helpers ----

    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "employeeId", id));
    }

    private Employee mapToEntity(EmployeeRequestDTO dto) {
        return Employee.builder()
                .employeeName(dto.getEmployeeName())
                .email(dto.getEmail())
                .department(dto.getDepartment())
                .designation(dto.getDesignation())
                .joiningDate(dto.getJoiningDate())
                .build();
    }

    private EmployeeResponseDTO mapToResponseDTO(Employee employee) {
        return EmployeeResponseDTO.builder()
                .employeeId(employee.getEmployeeId())
                .employeeName(employee.getEmployeeName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .designation(employee.getDesignation())
                .joiningDate(employee.getJoiningDate())
                .build();
    }
}

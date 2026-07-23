package com.assessment.leavemgmt;

import com.assessment.leavemgmt.dto.EmployeeRequestDTO;
import com.assessment.leavemgmt.dto.EmployeeResponseDTO;
import com.assessment.leavemgmt.entity.Employee;
import com.assessment.leavemgmt.exception.DuplicateEmailException;
import com.assessment.leavemgmt.exception.ResourceNotFoundException;
import com.assessment.leavemgmt.repository.EmployeeRepository;
import com.assessment.leavemgmt.repository.LeaveRequestRepository;
import com.assessment.leavemgmt.serviceimpl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .employeeId(1L)
                .employeeName("John Doe")
                .email("john@example.com")
                .department("Engineering")
                .designation("Developer")
                .joiningDate(LocalDate.of(2022, 1, 10))
                .build();

        requestDTO = EmployeeRequestDTO.builder()
                .employeeName("John Doe")
                .email("john@example.com")
                .department("Engineering")
                .designation("Developer")
                .joiningDate(LocalDate.of(2022, 1, 10))
                .build();
    }

    @Test
    void createEmployee_Success() {
        when(employeeRepository.existsByEmail(any())).thenReturn(false);
        when(employeeRepository.save(any())).thenReturn(employee);

        EmployeeResponseDTO result = employeeService.createEmployee(requestDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getEmployeeName());
        assertEquals("john@example.com", result.getEmail());
        verify(employeeRepository, times(1)).save(any());
    }

    @Test
    void createEmployee_DuplicateEmail_ThrowsException() {
        when(employeeRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> employeeService.createEmployee(requestDTO));
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void getEmployeeById_Found() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeResponseDTO result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getEmployeeId());
    }

    @Test
    void getEmployeeById_NotFound_ThrowsException() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(99L));
    }

    @Test
    void getAllEmployees_ReturnsList() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<EmployeeResponseDTO> result = employeeService.getAllEmployees();

        assertEquals(1, result.size());
    }

    @Test
    void deleteEmployee_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        assertDoesNotThrow(() -> employeeService.deleteEmployee(1L));
        verify(employeeRepository, times(1)).delete(employee);
    }
}

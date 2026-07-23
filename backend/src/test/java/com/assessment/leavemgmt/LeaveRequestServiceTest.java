package com.assessment.leavemgmt;

import com.assessment.leavemgmt.dto.LeaveRequestDTO;
import com.assessment.leavemgmt.dto.LeaveResponseDTO;
import com.assessment.leavemgmt.entity.Employee;
import com.assessment.leavemgmt.entity.LeaveRequest;
import com.assessment.leavemgmt.entity.LeaveStatus;
import com.assessment.leavemgmt.exception.InvalidLeaveRequestException;
import com.assessment.leavemgmt.repository.EmployeeRepository;
import com.assessment.leavemgmt.repository.LeaveRequestRepository;
import com.assessment.leavemgmt.serviceimpl.LeaveRequestServiceImpl;
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
class LeaveRequestServiceTest {

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private LeaveRequestServiceImpl leaveRequestService;

    private Employee employee;
    private LeaveRequest leaveRequest;
    private LeaveRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .employeeId(1L)
                .employeeName("Jane Smith")
                .email("jane@example.com")
                .department("HR")
                .designation("Manager")
                .joiningDate(LocalDate.of(2020, 5, 1))
                .build();

        leaveRequest = LeaveRequest.builder()
                .leaveId(1L)
                .leaveType("Sick Leave")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(3))
                .reason("Fever")
                .status(LeaveStatus.PENDING.name())
                .employee(employee)
                .build();

        requestDTO = LeaveRequestDTO.builder()
                .employeeId(1L)
                .leaveType("Sick Leave")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(3))
                .reason("Fever")
                .build();
    }

    @Test
    void applyLeave_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(leaveRequestRepository.save(any())).thenReturn(leaveRequest);

        LeaveResponseDTO result = leaveRequestService.applyLeave(requestDTO);

        assertNotNull(result);
        assertEquals("PENDING", result.getStatus());
        assertEquals("Sick Leave", result.getLeaveType());
    }

    @Test
    void applyLeave_InvalidDates_ThrowsException() {
        requestDTO.setEndDate(LocalDate.now());
        requestDTO.setStartDate(LocalDate.now().plusDays(5));

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        assertThrows(InvalidLeaveRequestException.class, () -> leaveRequestService.applyLeave(requestDTO));
    }

    @Test
    void approveLeave_Success() {
        when(leaveRequestRepository.findById(1L)).thenReturn(Optional.of(leaveRequest));
        leaveRequest.setStatus(LeaveStatus.APPROVED.name());
        when(leaveRequestRepository.save(any())).thenReturn(leaveRequest);

        LeaveResponseDTO result = leaveRequestService.approveLeave(1L);

        assertEquals("APPROVED", result.getStatus());
    }

    @Test
    void rejectLeave_Success() {
        when(leaveRequestRepository.findById(1L)).thenReturn(Optional.of(leaveRequest));
        leaveRequest.setStatus(LeaveStatus.REJECTED.name());
        when(leaveRequestRepository.save(any())).thenReturn(leaveRequest);

        LeaveResponseDTO result = leaveRequestService.rejectLeave(1L);

        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void getPendingLeaves_ReturnsList() {
        when(leaveRequestRepository.findByStatus(LeaveStatus.PENDING.name()))
                .thenReturn(List.of(leaveRequest));

        List<LeaveResponseDTO> result = leaveRequestService.getPendingLeaveRequests();

        assertEquals(1, result.size());
        assertEquals("PENDING", result.get(0).getStatus());
    }
}

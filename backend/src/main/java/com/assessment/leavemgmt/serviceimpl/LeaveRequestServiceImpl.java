package com.assessment.leavemgmt.serviceimpl;

import com.assessment.leavemgmt.dto.LeaveRequestDTO;
import com.assessment.leavemgmt.dto.LeaveResponseDTO;
import com.assessment.leavemgmt.entity.Employee;
import com.assessment.leavemgmt.entity.LeaveRequest;
import com.assessment.leavemgmt.entity.LeaveStatus;
import com.assessment.leavemgmt.exception.InvalidLeaveRequestException;
import com.assessment.leavemgmt.exception.ResourceNotFoundException;
import com.assessment.leavemgmt.repository.EmployeeRepository;
import com.assessment.leavemgmt.repository.LeaveRequestRepository;
import com.assessment.leavemgmt.service.LeaveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public LeaveResponseDTO applyLeave(LeaveRequestDTO requestDTO) {
        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "employeeId", requestDTO.getEmployeeId()));

        if (requestDTO.getEndDate().isBefore(requestDTO.getStartDate())) {
            throw new InvalidLeaveRequestException("End date cannot be earlier than start date");
        }

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .leaveType(requestDTO.getLeaveType())
                .startDate(requestDTO.getStartDate())
                .endDate(requestDTO.getEndDate())
                .reason(requestDTO.getReason())
                .status(LeaveStatus.PENDING.name())
                .employee(employee)
                .build();

        return mapToResponseDTO(leaveRequestRepository.save(leaveRequest));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveResponseDTO> getLeaveHistoryByEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee", "employeeId", employeeId);
        }
        return leaveRequestRepository.findByEmployee_EmployeeId(employeeId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveResponseDTO approveLeave(Long leaveId) {
        LeaveRequest leave = findLeaveById(leaveId);
        leave.setStatus(LeaveStatus.APPROVED.name());
        return mapToResponseDTO(leaveRequestRepository.save(leave));
    }

    @Override
    public LeaveResponseDTO rejectLeave(Long leaveId) {
        LeaveRequest leave = findLeaveById(leaveId);
        leave.setStatus(LeaveStatus.REJECTED.name());
        return mapToResponseDTO(leaveRequestRepository.save(leave));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveResponseDTO> getPendingLeaveRequests() {
        return leaveRequestRepository.findByStatus(LeaveStatus.PENDING.name())
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ---- Helpers ----

    private LeaveRequest findLeaveById(Long leaveId) {
        return leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveRequest", "leaveId", leaveId));
    }

    private LeaveResponseDTO mapToResponseDTO(LeaveRequest leave) {
        long totalDays = ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;
        return LeaveResponseDTO.builder()
                .leaveId(leave.getLeaveId())
                .leaveType(leave.getLeaveType())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .reason(leave.getReason())
                .status(leave.getStatus())
                .employeeId(leave.getEmployee().getEmployeeId())
                .employeeName(leave.getEmployee().getEmployeeName())
                .totalDays(totalDays)
                .build();
    }
    @Override
    public List<LeaveResponseDTO> getAllLeaves() {

        return leaveRequestRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }
}

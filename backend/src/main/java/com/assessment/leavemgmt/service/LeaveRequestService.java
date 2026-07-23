package com.assessment.leavemgmt.service;

import com.assessment.leavemgmt.dto.LeaveRequestDTO;
import com.assessment.leavemgmt.dto.LeaveResponseDTO;

import java.util.List;

public interface LeaveRequestService {

    LeaveResponseDTO applyLeave(LeaveRequestDTO requestDTO);

    List<LeaveResponseDTO> getLeaveHistoryByEmployee(Long employeeId);

    LeaveResponseDTO approveLeave(Long leaveId);

    LeaveResponseDTO rejectLeave(Long leaveId);

    List<LeaveResponseDTO> getPendingLeaveRequests();

    List<LeaveResponseDTO> getAllLeaves();
}

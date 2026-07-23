package com.assessment.leavemgmt.controller;

import com.assessment.leavemgmt.dto.ApiResponse;
import com.assessment.leavemgmt.dto.LeaveRequestDTO;
import com.assessment.leavemgmt.dto.LeaveResponseDTO;
import com.assessment.leavemgmt.service.LeaveRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
@Tag(name = "Leave Management", description = "APIs for managing leave requests")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @PostMapping
    @Operation(summary = "Apply for a leave")
    public ResponseEntity<ApiResponse<LeaveResponseDTO>> applyLeave(
            @Valid @RequestBody LeaveRequestDTO requestDTO) {
        LeaveResponseDTO response = leaveRequestService.applyLeave(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Leave applied successfully", response));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get leave history of an employee")
    public ResponseEntity<ApiResponse<List<LeaveResponseDTO>>> getLeaveHistory(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(ApiResponse.success("Leave history fetched",
                leaveRequestService.getLeaveHistoryByEmployee(employeeId)));
    }
    @GetMapping
    @Operation(summary = "Get all leave requests")
    public ResponseEntity<ApiResponse<List<LeaveResponseDTO>>> getAllLeaves() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "All leave requests fetched successfully",
                        leaveRequestService.getAllLeaves()
                )
        );
    }

    @PutMapping("/{leaveId}/approve")
    @Operation(summary = "Approve a leave request")
    public ResponseEntity<ApiResponse<LeaveResponseDTO>> approveLeave(@PathVariable Long leaveId) {
        return ResponseEntity.ok(ApiResponse.success("Leave approved successfully",
                leaveRequestService.approveLeave(leaveId)));
    }

    @PutMapping("/{leaveId}/reject")
    @Operation(summary = "Reject a leave request")
    public ResponseEntity<ApiResponse<LeaveResponseDTO>> rejectLeave(@PathVariable Long leaveId) {
        return ResponseEntity.ok(ApiResponse.success("Leave rejected successfully",
                leaveRequestService.rejectLeave(leaveId)));
    }

    @GetMapping("/pending")
    @Operation(summary = "Get all pending leave requests")
    public ResponseEntity<ApiResponse<List<LeaveResponseDTO>>> getPendingLeaves() {
        return ResponseEntity.ok(ApiResponse.success("Pending leaves fetched",
                leaveRequestService.getPendingLeaveRequests()));
    }
}

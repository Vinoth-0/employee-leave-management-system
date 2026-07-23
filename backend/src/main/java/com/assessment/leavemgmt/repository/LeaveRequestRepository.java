package com.assessment.leavemgmt.repository;

import com.assessment.leavemgmt.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployee_EmployeeId(Long employeeId);

    List<LeaveRequest> findByStatus(String status);

    @Query("SELECT COALESCE(SUM(DATEDIFF(l.endDate, l.startDate) + 1), 0) " +
           "FROM LeaveRequest l WHERE l.employee.employeeId = :employeeId AND l.status = 'APPROVED'")
    long sumApprovedLeaveDaysByEmployee(@Param("employeeId") Long employeeId);
}

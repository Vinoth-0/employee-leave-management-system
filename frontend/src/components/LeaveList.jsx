import { useEffect, useState } from "react";
import api from "../services/api";

function LeaveList() {
  const [leaves, setLeaves] = useState([]);

  useEffect(() => {
    fetchLeaves();
  }, []);

  const fetchLeaves = async () => {
    try {
      const response = await api.get("/leaves");
      setLeaves(response.data.data);
    } catch (error) {
      console.error("Error fetching leaves:", error);
      alert("Failed to fetch leave requests.");
    }
  };

  const approveLeave = async (id) => {
    try {
      await api.put(`/leaves/${id}/approve`);
      alert("Leave Approved Successfully!");
      fetchLeaves();
    } catch (error) {
      console.error(error);
      alert("Failed to approve leave.");
    }
  };

  const rejectLeave = async (id) => {
    try {
      await api.put(`/leaves/${id}/reject`);
      alert("Leave Rejected Successfully!");
      fetchLeaves();
    } catch (error) {
      console.error(error);
      alert("Failed to reject leave.");
    }
  };

  return (
    <div className="card shadow mt-4">
      <div className="card-header bg-primary text-white">
        <h4 className="mb-0">Leave Requests</h4>
      </div>

      <div className="card-body">
        <table className="table table-bordered table-hover">
          <thead className="table-light">
            <tr>
              <th>ID</th>
              <th>Employee</th>
              <th>Leave Type</th>
              <th>Start Date</th>
              <th>End Date</th>
              <th>Total Days</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {leaves.length > 0 ? (
              leaves.map((leave) => (
                <tr key={leave.leaveId}>
                  <td>{leave.leaveId}</td>
                  <td>{leave.employeeName}</td>
                  <td>{leave.leaveType}</td>
                  <td>{leave.startDate}</td>
                  <td>{leave.endDate}</td>
                  <td>{leave.totalDays}</td>
                  <td>{leave.status}</td>

                  <td>
                    <button
                      className="btn btn-success btn-sm me-2"
                      onClick={() => approveLeave(leave.leaveId)}
                      disabled={leave.status === "APPROVED"}
                    >
                      Approve
                    </button>

                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => rejectLeave(leave.leaveId)}
                      disabled={leave.status === "REJECTED"}
                    >
                      Reject
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="8" className="text-center">
                  No Leave Requests Found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default LeaveList;
import { useState } from "react";
import api from "../services/api";
import { toast } from "react-toastify";

function LeaveForm() {
  const [leave, setLeave] = useState({
    employeeId: "",
    leaveType: "",
    startDate: "",
    endDate: "",
    reason: "",
  });

  const handleChange = (e) => {
    setLeave({
      ...leave,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await api.post("/leaves", leave);

      toast.success("Leave applied successfully!");

      setLeave({
        employeeId: "",
        leaveType: "",
        startDate: "",
        endDate: "",
        reason: "",
      });
    } catch (error) {
      console.error(error);

      if (error.response?.data?.message) {
        toast.error(error.response.data.message);
      } else {
        toast.error("Failed to apply leave.");
      }
    }
  };

  return (
    <div className="card shadow">
      <div className="card-header bg-success text-white">
        <h4>Apply Leave</h4>
      </div>

      <div className="card-body">
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Employee ID</label>
            <input
              type="number"
              className="form-control"
              name="employeeId"
              value={leave.employeeId}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Leave Type</label>
            <select
              className="form-select"
              name="leaveType"
              value={leave.leaveType}
              onChange={handleChange}
              required
            >
              <option value="">Select Leave Type</option>
              <option value="CASUAL">Casual Leave</option>
              <option value="SICK">Sick Leave</option>
              <option value="EARNED">Earned Leave</option>
            </select>
          </div>

          <div className="mb-3">
            <label className="form-label">Start Date</label>
            <input
              type="date"
              className="form-control"
              name="startDate"
              value={leave.startDate}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">End Date</label>
            <input
              type="date"
              className="form-control"
              name="endDate"
              value={leave.endDate}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Reason</label>
            <textarea
              className="form-control"
              rows="3"
              name="reason"
              value={leave.reason}
              onChange={handleChange}
              required
            />
          </div>

          <button type="submit" className="btn btn-success">
            Apply Leave
          </button>
        </form>
      </div>
    </div>
  );
}

export default LeaveForm;
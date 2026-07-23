import { useEffect, useState } from "react";
import api from "../services/api";

function Dashboard() {
  const [totalEmployees, setTotalEmployees] = useState(0);
  const [pendingLeaves, setPendingLeaves] = useState(0);
  const [approvedLeaves, setApprovedLeaves] = useState(0);
  const [rejectedLeaves, setRejectedLeaves] = useState(0);

  useEffect(() => {
    fetchEmployees();
    fetchLeaves();
  }, []);

  const fetchEmployees = async () => {
    try {
      const response = await api.get("/employees");
      setTotalEmployees(response.data.data.length);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchLeaves = async () => {
    try {
      const response = await api.get("/leaves");

      console.log("Response:", response.data);

      const leaves = response.data.data;

      console.log("Leaves:", leaves);

      const pending = leaves.filter((l) => l.status === "PENDING").length;
      const approved = leaves.filter((l) => l.status === "APPROVED").length;
      const rejected = leaves.filter((l) => l.status === "REJECTED").length;

      console.log("Pending:", pending);
      console.log("Approved:", approved);
      console.log("Rejected:", rejected);

      setPendingLeaves(pending);
      setApprovedLeaves(approved);
      setRejectedLeaves(rejected);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-4">Dashboard</h2>

      <div className="row">
        <div className="col-md-3">
          <div className="card text-bg-primary mb-3">
            <div className="card-body">
              <h5>Total Employees</h5>
              <h2>{totalEmployees}</h2>
            </div>
          </div>
        </div>

        <div className="col-md-3">
          <div className="card text-bg-warning mb-3">
            <div className="card-body">
              <h5>Pending Leaves</h5>
              <h2>{pendingLeaves}</h2>
            </div>
          </div>
        </div>

        <div className="col-md-3">
          <div className="card text-bg-success mb-3">
            <div className="card-body">
              <h5>Approved</h5>
              <h2>{approvedLeaves}</h2>
            </div>
          </div>
        </div>

        <div className="col-md-3">
          <div className="card text-bg-danger mb-3">
            <div className="card-body">
              <h5>Rejected</h5>
              <h2>{rejectedLeaves}</h2>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;

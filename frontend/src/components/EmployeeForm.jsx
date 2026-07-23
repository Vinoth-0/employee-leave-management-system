import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import api from "../services/api";

function EmployeeForm({
  selectedEmployee,
  setSelectedEmployee,
  fetchEmployees,
}) {
  const [employee, setEmployee] = useState({
    employeeName: "",
    email: "",
    department: "",
    designation: "",
    joiningDate: "",
  });

  useEffect(() => {
    if (selectedEmployee) {
      setEmployee({
        employeeName: selectedEmployee.employeeName,
        email: selectedEmployee.email,
        department: selectedEmployee.department,
        designation: selectedEmployee.designation,
        joiningDate: selectedEmployee.joiningDate,
      });
    } else {
      setEmployee({
        employeeName: "",
        email: "",
        department: "",
        designation: "",
        joiningDate: "",
      });
    }
  }, [selectedEmployee]);

  const handleChange = (e) => {
    setEmployee({
      ...employee,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (selectedEmployee) {
        await api.put(
          `/employees/${selectedEmployee.employeeId}`,
          employee
        );

        toast.success("Employee Updated Successfully!");
      } else {
        await api.post("/employees", employee);

        toast.success("Employee Added Successfully!");
      }

      // Refresh employee list
      fetchEmployees();

      // Clear form
      setEmployee({
        employeeName: "",
        email: "",
        department: "",
        designation: "",
        joiningDate: "",
      });

      setSelectedEmployee(null);
    } catch (error) {
      console.error("Error:", error);

      if (error.response) {
        console.log(error.response.data);
      }

      toast.error("Operation Failed!");
    }
  };

  return (
    <div className="card shadow mt-4">
      <div className="card-header bg-success text-white">
        <h4>
          {selectedEmployee ? "Update Employee" : "Add Employee"}
        </h4>
      </div>

      <div className="card-body">
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Employee Name</label>
            <input
              type="text"
              className="form-control"
              name="employeeName"
              value={employee.employeeName}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Email</label>
            <input
              type="email"
              className="form-control"
              name="email"
              value={employee.email}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Department</label>
            <input
              type="text"
              className="form-control"
              name="department"
              value={employee.department}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Designation</label>
            <input
              type="text"
              className="form-control"
              name="designation"
              value={employee.designation}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Joining Date</label>
            <input
              type="date"
              className="form-control"
              name="joiningDate"
              value={employee.joiningDate}
              onChange={handleChange}
              required
            />
          </div>

          <button type="submit" className="btn btn-success">
            {selectedEmployee ? "Update Employee" : "Add Employee"}
          </button>

          {selectedEmployee && (
            <button
              type="button"
              className="btn btn-secondary ms-2"
              onClick={() => {
                setSelectedEmployee(null);
                setEmployee({
                  employeeName: "",
                  email: "",
                  department: "",
                  designation: "",
                  joiningDate: "",
                });
              }}
            >
              Cancel
            </button>
          )}
        </form>
      </div>
    </div>
  );
}

export default EmployeeForm;
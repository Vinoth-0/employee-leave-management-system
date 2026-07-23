import { useEffect, useState } from "react";
import api from "../services/api";
import EmployeeForm from "../components/EmployeeForm";
import EmployeeList from "../components/EmployeeList";
import { toast } from "react-toastify";

function Employees() {
  const [employees, setEmployees] = useState([]);
  const [selectedEmployee, setSelectedEmployee] = useState(null);

  const fetchEmployees = async () => {
    try {
      const response = await api.get("/employees");
      setEmployees(response.data.data);
    } catch (error) {
      toast.error("Error fetching employees: " + error.message);
    }
  };

  useEffect(() => {
    fetchEmployees();
  }, []);

  return (
    <>
      <div className="container mt-4">
        <h2 className="mb-4">Employee Management</h2>
      
      <EmployeeForm
        selectedEmployee={selectedEmployee}
        setSelectedEmployee={setSelectedEmployee}
        fetchEmployees={fetchEmployees}
      />

      <EmployeeList
        employees={employees}
        fetchEmployees={fetchEmployees}
        setSelectedEmployee={setSelectedEmployee}
      />
  </div>  
    </>
  );
}

export default Employees;
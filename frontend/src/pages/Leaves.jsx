import LeaveForm from "../components/LeaveForm";
import LeaveList from "../components/LeaveList";

function Leaves() {
  return (
    <div className="container mt-4">
      <h2 className="mb-4">Leave Management</h2>

      <LeaveForm />

      <div className="mt-5">
        <LeaveList />
      </div>
    </div>
  );
}

export default Leaves;
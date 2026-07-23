import { Link } from "react-router-dom";

function Home() {
  return (
    <div className="text-center mt-5">
      <h1
        className="display-4"
        style={{ position: "relative", top: "50px", fontSize: "70px" }}
      >
        Employee Leave Management System
      </h1>

      <p
        className="lead mt-3"
        style={{ position: "relative", top: "50px", fontSize: "40px" }}
      >
        Manage employees and leave requests efficiently.
      </p>

      <div className="mt-4">
        <Link
          to="/employees"
          className="btn btn-primary me-3"
          style={{ position: "relative", top: "50px", fontSize: "20px" }}
        >
          Employees
        </Link>

        <Link
          to="/leaves"
          className="btn btn-success"
          style={{ position: "relative", top: "50px", fontSize: "20px" }}
        >
          Leave Requests
        </Link>
      </div>
    </div>
  );
}

export default Home;

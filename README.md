# 🗂️ Employee Leave Management System

A full-stack web application for managing employees and their leave requests — built with a **Spring Boot REST API** backend and a **React (Vite)** frontend.

Employees can be registered, organized by department, and tracked for leave history, while leave requests can be applied for, approved, or rejected through a clean, responsive dashboard.

---

## ✨ Features

- **Employee Management** — Create, view, update, and delete employee records
- **Leave Requests** — Apply for leave, view history, and track status (Pending / Approved / Rejected)
- **Approval Workflow** — Approve or reject pending leave requests
- **Dashboard** — At-a-glance summary cards for total employees, pending, approved, and rejected leaves
- **Search & Filter** — Look up employees by department
- **Leave Balance** — Calculate total approved leave days per employee
- **Pagination & Sorting** — Server-side paginated, sortable employee listing
- **Validation & Error Handling** — Centralized exception handling with meaningful HTTP status codes (400, 404, 409, 500)
- **API Documentation** — Interactive Swagger UI for exploring and testing endpoints
- **Automated Tests** — JUnit 5 + Mockito unit tests for the service layer

---

## 🧱 Tech Stack

### Backend
| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2.0 |
| ORM | Spring Data JPA / Hibernate |
| Database | MySQL 8 |
| Build Tool | Maven |
| Utilities | Lombok |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Testing | JUnit 5 + Mockito |

### Frontend
| Layer | Technology |
|---|---|
| Library | React 19 |
| Build Tool | Vite |
| Styling | Bootstrap 5 |
| HTTP Client | Axios |
| Routing | React Router 7 |
| Notifications | React Toastify |

---

## 🏗️ Architecture

```
┌─────────────────┐        REST (JSON/HTTP)       ┌──────────────────────┐
│   React (Vite)   │ ─────────────────────────────▶│   Spring Boot API    │
│   Frontend       │◀───────────────────────────── │   (Layered MVC)      │
└─────────────────┘                                └──────────┬───────────┘
                                                                │ JPA / Hibernate
                                                                ▼
                                                      ┌──────────────────┐
                                                      │   MySQL Database │
                                                      └──────────────────┘
```

**Backend package structure:**
```
com.assessment.leavemgmt/
├── config/           → Swagger & CORS configuration
├── controller/        → REST controllers (Employee, LeaveRequest)
├── dto/                → Request/Response DTOs + ApiResponse wrapper
├── entity/             → JPA entities (Employee, LeaveRequest, LeaveStatus)
├── exception/          → Custom exceptions + GlobalExceptionHandler
├── repository/         → Spring Data JPA repositories
├── service/            → Service interfaces
└── serviceimpl/        → Service implementations
```

**Data model:**
- `Employee` (1) ── has many ──▶ `LeaveRequest` (many), linked via `employee_id` with cascading delete

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven
- MySQL 8.x
- Node.js 18+ and npm

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd employee-leave-management/backend1
   ```

2. **Configure the database**

   MySQL will auto-create the schema on first run (`createDatabaseIfNotExist=true`), or you can run it manually:
   ```bash
   mysql -u root -p < sql/schema.sql
   ```

3. **Set your credentials** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/leave_management_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   spring.datasource.username=root
   spring.datasource.password=YOUR_PASSWORD
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   The API will start on `http://localhost:8080`.

5. **Explore the API** via Swagger UI:
   ```
   http://localhost:8080/swagger-ui.html
   ```

### Frontend Setup

1. **Navigate to the frontend folder**
   ```bash
   cd employee-leave-management/frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Run the development server**
   ```bash
   npm run dev
   ```
   The app will be available at `http://localhost:5173`.

> Make sure the backend is running on port `8080` first — the frontend calls `http://localhost:8080/api` directly via Axios.

---

## 📡 API Reference

### Employee Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/employees` | Create a new employee |
| `GET` | `/api/employees` | Get all employees |
| `GET` | `/api/employees?page=0&size=5&sortBy=employeeName&sortDir=asc` | Paginated, sorted list |
| `GET` | `/api/employees/{id}` | Get employee by ID |
| `PUT` | `/api/employees/{id}` | Update an employee |
| `DELETE` | `/api/employees/{id}` | Delete an employee |
| `GET` | `/api/employees/department/{department}` | List employees by department |
| `GET` | `/api/employees/{id}/leave-days` | Total approved leave days for an employee |

### Leave Request Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/leaves` | Apply for leave |
| `GET` | `/api/leaves/employee/{employeeId}` | Get leave history for an employee |
| `PUT` | `/api/leaves/{leaveId}/approve` | Approve a leave request |
| `PUT` | `/api/leaves/{leaveId}/reject` | Reject a leave request |
| `GET` | `/api/leaves/pending` | Get all pending leave requests |

### Sample Requests

**Create Employee** — `POST /api/employees`
```json
{
  "employeeName": "John Doe",
  "email": "john.doe@company.com",
  "department": "Engineering",
  "designation": "Software Developer",
  "joiningDate": "2023-01-15"
}
```

**Apply for Leave** — `POST /api/leaves`
```json
{
  "employeeId": 1,
  "leaveType": "Sick Leave",
  "startDate": "2025-02-10",
  "endDate": "2025-02-12",
  "reason": "Fever and body ache"
}
```

### HTTP Status Codes

| Scenario | Code |
|----------|------|
| Success (GET / PUT / DELETE) | `200 OK` |
| Created (POST) | `201 Created` |
| Validation error | `400 Bad Request` |
| Resource not found | `404 Not Found` |
| Duplicate email | `409 Conflict` |
| Server error | `500 Internal Server Error` |

---

## 🧪 Running Tests

```bash
cd backend1
mvn test
```

Covers service-layer logic for both employee and leave-request operations using JUnit 5 and Mockito.

---

## 📁 Project Structure

```
employee-leave-management/
├── backend1/            → Spring Boot REST API
│   ├── src/main/java/com/assessment/leavemgmt/
│   ├── src/test/java/com/assessment/leavemgmt/
│   └── sql/schema.sql
└── frontend/             → React (Vite) client
    └── src/
        ├── components/   → EmployeeForm, EmployeeList, LeaveForm, LeaveList, Navbar, Footer
        ├── pages/         → Home, Employees, Leaves, Dashboard
        └── services/      → Axios API client
```

---

## 🗺️ Roadmap / Possible Enhancements

- Authentication & role-based access (Admin / Employee)
- Email notifications on leave approval/rejection
- Configurable leave policies (annual quota per leave type)
- Deployment to a cloud platform (Render, Railway, or AWS)

---

## 👤 Author

**Vinoth Kumar A**
Java Full Stack Developer
[GitHub](https://github.com/vinoth-0)

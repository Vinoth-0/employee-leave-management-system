-- ============================================================
-- Employee Leave Management System - Database Schema
-- ============================================================

CREATE DATABASE IF NOT EXISTS leave_management_db;
USE leave_management_db;

-- -------------------------------------------------------
-- Table: employees
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS employees (
    employee_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_name VARCHAR(150) NOT NULL,
    email         VARCHAR(150) NOT NULL UNIQUE,
    department    VARCHAR(100) NOT NULL,
    designation   VARCHAR(100) NOT NULL,
    joining_date  DATE         NOT NULL
);

-- -------------------------------------------------------
-- Table: leave_requests
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS leave_requests (
    leave_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    leave_type  VARCHAR(100) NOT NULL,
    start_date  DATE         NOT NULL,
    end_date    DATE         NOT NULL,
    reason      TEXT         NOT NULL,
    status      VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    employee_id BIGINT       NOT NULL,
    CONSTRAINT fk_employee
        FOREIGN KEY (employee_id) REFERENCES employees (employee_id)
        ON DELETE CASCADE
);

-- -------------------------------------------------------
-- Sample Data
-- -------------------------------------------------------
INSERT INTO employees (employee_name, email, department, designation, joining_date) VALUES
('Alice Johnson',  'alice@company.com',  'Engineering', 'Senior Developer', '2020-03-15'),
('Bob Smith',      'bob@company.com',    'HR',          'HR Manager',       '2019-07-01'),
('Carol Williams', 'carol@company.com',  'Finance',     'Accountant',       '2021-11-20');

INSERT INTO leave_requests (leave_type, start_date, end_date, reason, status, employee_id) VALUES
('Sick Leave',    CURDATE() + INTERVAL 2 DAY,  CURDATE() + INTERVAL 4 DAY,  'Fever and cold',      'PENDING',  1),
('Casual Leave',  CURDATE() + INTERVAL 5 DAY,  CURDATE() + INTERVAL 6 DAY,  'Personal work',       'APPROVED', 2),
('Annual Leave',  CURDATE() + INTERVAL 10 DAY, CURDATE() + INTERVAL 15 DAY, 'Family vacation',     'PENDING',  3);

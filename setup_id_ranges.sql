-- Run these commands to reset the starting ID for your tables.
-- CLEAN SLATE SCRIPT
-- This script deletes all data from all tables and resets the ID counters.
-- Use this to start fresh with a clean database and professional ID ranges (1000+).

-- Disable foreign key checks to allow truncating tables with relationships
SET FOREIGN_KEY_CHECKS = 0;

-- Truncate all tables (Deletes all data and resets auto-increment)
TRUNCATE TABLE users;
TRUNCATE TABLE students;
TRUNCATE TABLE teachers;
TRUNCATE TABLE departments;
TRUNCATE TABLE courses;
TRUNCATE TABLE assignments;
TRUNCATE TABLE exams;
TRUNCATE TABLE submissions;
TRUNCATE TABLE timetable;
TRUNCATE TABLE attendance;
TRUNCATE TABLE marks;
-- Also truncate join tables if they exist
TRUNCATE TABLE student_courses;
TRUNCATE TABLE teacher_courses;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Set the starting ID to 1000 for all tables to ensure consistency and a professional look
ALTER TABLE users AUTO_INCREMENT = 1000;
ALTER TABLE students AUTO_INCREMENT = 1000;
ALTER TABLE teachers AUTO_INCREMENT = 1000;
ALTER TABLE departments AUTO_INCREMENT = 1000;
ALTER TABLE courses AUTO_INCREMENT = 1000;
ALTER TABLE assignments AUTO_INCREMENT = 1000;
ALTER TABLE exams AUTO_INCREMENT = 1000;
ALTER TABLE submissions AUTO_INCREMENT = 1000;
ALTER TABLE timetable AUTO_INCREMENT = 1000;
ALTER TABLE attendance AUTO_INCREMENT = 1000;
ALTER TABLE marks AUTO_INCREMENT = 1000;

-- ==========================================
-- SEED DATA
-- ==========================================

-- 1. DEPARTMENTS
INSERT INTO departments (id, department_name) VALUES (1000, 'Computer Science');
INSERT INTO departments (id, department_name) VALUES (1001, 'Information Science');

-- 2. USERS (Admin, Teachers, then Students)
-- Admin
INSERT INTO users (id, full_name, email, role, password) VALUES (1000, 'System Admin', 'admin@campusconnect.com', 'ADMIN', 'password123');

-- 5 Teachers
INSERT INTO users (id, full_name, email, role, password) VALUES (1001, 'Dr. Alice Smith', 'alice.smith@campusconnect.com', 'TEACHER', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1002, 'Prof. Bob Johnson', 'bob.johnson@campusconnect.com', 'TEACHER', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1003, 'Dr. Charlie Brown', 'charlie.brown@campusconnect.com', 'TEACHER', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1004, 'Prof. Diana Prince', 'diana.prince@campusconnect.com', 'TEACHER', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1005, 'Dr. Edward Norton', 'edward.norton@campusconnect.com', 'TEACHER', 'password123');

-- 20 Students
INSERT INTO users (id, full_name, email, role, password) VALUES (1006, 'Student 01', 's01@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1007, 'Student 02', 's02@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1008, 'Student 03', 's03@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1009, 'Student 04', 's04@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1010, 'Student 05', 's05@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1011, 'Student 06', 's06@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1012, 'Student 07', 's07@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1013, 'Student 08', 's08@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1014, 'Student 09', 's09@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1015, 'Student 10', 's10@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1016, 'Student 11', 's11@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1017, 'Student 12', 's12@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1018, 'Student 13', 's13@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1019, 'Student 14', 's14@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1020, 'Student 15', 's15@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1021, 'Student 16', 's16@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1022, 'Student 17', 's17@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1023, 'Student 18', 's18@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1024, 'Student 19', 's19@campusconnect.com', 'STUDENT', 'password123');
INSERT INTO users (id, full_name, email, role, password) VALUES (1025, 'Student 20', 's20@campusconnect.com', 'STUDENT', 'password123');

-- 3. TEACHERS (5)
INSERT INTO teachers (id, employee_code, designation, department_id, user_id) VALUES (1000, 'TCH100', 'Professor', 1000, 1001);
INSERT INTO teachers (id, employee_code, designation, department_id, user_id) VALUES (1001, 'TCH101', 'Assistant Professor', 1000, 1002);
INSERT INTO teachers (id, employee_code, designation, department_id, user_id) VALUES (1002, 'TCH102', 'Head of Dept', 1001, 1003);
INSERT INTO teachers (id, employee_code, designation, department_id, user_id) VALUES (1003, 'TCH103', 'Lecturer', 1001, 1004);
INSERT INTO teachers (id, employee_code, designation, department_id, user_id) VALUES (1004, 'TCH104', 'Senior Professor', 1000, 1005);

-- 4. STUDENTS (20)
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1000, 'USN001', 3, 1000, 1006);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1001, 'USN002', 3, 1000, 1007);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1002, 'USN003', 3, 1000, 1008);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1003, 'USN004', 3, 1000, 1009);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1004, 'USN005', 3, 1000, 1010);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1005, 'USN006', 5, 1001, 1011);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1006, 'USN007', 5, 1001, 1012);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1007, 'USN008', 5, 1001, 1013);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1008, 'USN009', 5, 1001, 1014);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1009, 'USN010', 5, 1001, 1015);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1010, 'USN011', 1, 1000, 1016);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1011, 'USN012', 1, 1000, 1017);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1012, 'USN013', 1, 1000, 1018);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1013, 'USN014', 1, 1000, 1019);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1014, 'USN015', 1, 1000, 1020);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1015, 'USN016', 7, 1001, 1021);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1016, 'USN017', 7, 1001, 1022);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1017, 'USN018', 7, 1001, 1023);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1018, 'USN019', 7, 1001, 1024);
INSERT INTO students (id, usn, semester, department_id, user_id) VALUES (1019, 'USN020', 7, 1001, 1025);

-- 5. COURSES (5 Subjects)
INSERT INTO courses (id, course_code, course_name, credits, semester) VALUES (1000, 'CS301', 'Data Structures', 4, 3);
INSERT INTO courses (id, course_code, course_name, credits, semester) VALUES (1001, 'CS302', 'Algorithms', 4, 3);
INSERT INTO courses (id, course_code, course_name, credits, semester) VALUES (1002, 'IS501', 'Database Systems', 3, 5);
INSERT INTO courses (id, course_code, course_name, credits, semester) VALUES (1003, 'CS101', 'Introduction to C', 4, 1);
INSERT INTO courses (id, course_code, course_name, credits, semester) VALUES (1004, 'IS701', 'Cloud Computing', 3, 7);

-- 6. ASSIGNMENTS
INSERT INTO assignments (id, title, description, due_date, course_id, teacher_id) 
VALUES (1000, 'Binary Trees', 'Implement a BST in C++', '2024-12-01', 1000, 1000);
INSERT INTO assignments (id, title, description, due_date, course_id, teacher_id) 
VALUES (1001, 'SQL Queries', 'Write 10 complex join queries', '2024-12-05', 1002, 1002);

-- 7. EXAMS
INSERT INTO exams (id, exam_name, exam_date, course_id, teacher_id) 
VALUES (1000, 'Midterm - Data Structures', '2024-11-15', 1000, 1000);
INSERT INTO exams (id, exam_name, exam_date, course_id, teacher_id) 
VALUES (1001, 'Final - Database Systems', '2024-11-20', 1002, 1002);

-- 8. TIMETABLE
INSERT INTO timetable (id, day_of_week, start_time, end_time, room_number, course_id, teacher_id) 
VALUES (1000, 'Monday', '09:00:00', '10:00:00', 'Room 101', 1000, 1000);
INSERT INTO timetable (id, day_of_week, start_time, end_time, room_number, course_id, teacher_id) 
VALUES (1001, 'Tuesday', '11:00:00', '12:00:00', 'Lab 01', 1001, 1001);
INSERT INTO timetable (id, day_of_week, start_time, end_time, room_number, course_id, teacher_id) 
VALUES (1002, 'Wednesday', '14:00:00', '15:30:00', 'Room 202', 1002, 1002);

-- 9. ATTENDANCE (Sample for Student 1)
INSERT INTO attendance (id, attendance_date, status, course_id, student_id) 
VALUES (1000, '2024-11-01', 'PRESENT', 1000, 1000);
INSERT INTO attendance (id, attendance_date, status, course_id, student_id) 
VALUES (1001, '2024-11-02', 'ABSENT', 1000, 1000);

-- 10. MARKS (Sample for Exam 1)
INSERT INTO marks (id, marks_obtained, exam_id, student_id) 
VALUES (1000, 85, 1000, 1000);
INSERT INTO marks (id, marks_obtained, exam_id, student_id) 
VALUES (1001, 78, 1000, 1001);

-- 11. STUDENT_COURSES (Enrollment)
-- Enrolling students in their respective semester courses
INSERT INTO student_courses (student_id, course_id) VALUES (1000, 1000);
INSERT INTO student_courses (student_id, course_id) VALUES (1001, 1000);
INSERT INTO student_courses (student_id, course_id) VALUES (1002, 1000);
INSERT INTO student_courses (student_id, course_id) VALUES (1003, 1000);
INSERT INTO student_courses (student_id, course_id) VALUES (1004, 1000);
INSERT INTO student_courses (student_id, course_id) VALUES (1005, 1002);
INSERT INTO student_courses (student_id, course_id) VALUES (1006, 1002);
INSERT INTO student_courses (student_id, course_id) VALUES (1007, 1002);
INSERT INTO student_courses (student_id, course_id) VALUES (1008, 1002);
INSERT INTO student_courses (student_id, course_id) VALUES (1009, 1002);

-- 12. TEACHER_COURSES (Mapping)
INSERT INTO teacher_courses (teacher_id, course_id) VALUES (1000, 1000);
INSERT INTO teacher_courses (teacher_id, course_id) VALUES (1001, 1001);
INSERT INTO teacher_courses (teacher_id, course_id) VALUES (1002, 1002);
INSERT INTO teacher_courses (teacher_id, course_id) VALUES (1003, 1003);
INSERT INTO teacher_courses (teacher_id, course_id) VALUES (1004, 1004);
async function loadStudents() {
    try {
        const students = await fetchJson(`${API_BASE}/students`);
        document.getElementById("tableHead").innerHTML = `
            <tr>
                <th>ID</th>
                <th>USN</th>
                <th>Name</th>
                <th>Email</th>
                <th>Semester</th>
            </tr>
        `;
        document.getElementById("tableBody").innerHTML = students.map(student => `
            <tr>
                <td>${student.id}</td>
                <td>${student.usn ?? "-"}</td>
                <td>${student.user?.fullName ?? "-"}</td>
                <td>${student.user?.email ?? "-"}</td>
                <td>${student.semester ?? "-"}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load students.");
        console.error(error);
    }
}

async function loadTeachers() {
    try {
        const teachers = await fetchJson(`${API_BASE}/teachers`);
        document.getElementById("tableHead").innerHTML = `
            <tr>
                <th>ID</th>
                <th>Employee Code</th>
                <th>Name</th>
                <th>Designation</th>
            </tr>
        `;
        document.getElementById("tableBody").innerHTML = teachers.map(teacher => `
            <tr>
                <td>${teacher.id}</td>
                <td>${teacher.employeeCode ?? "-"}</td>
                <td>${teacher.user?.fullName ?? "-"}</td>
                <td>${teacher.designation ?? "-"}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load teachers.");
        console.error(error);
    }
}

async function loadCourses() {
    try {
        const courses = await fetchJson(`${API_BASE}/courses`);
        document.getElementById("tableHead").innerHTML = `
            <tr>
                <th>ID</th>
                <th>Course Code</th>
                <th>Course Name</th>
                <th>Credits</th>
                <th>Semester</th>
            </tr>
        `;
        document.getElementById("tableBody").innerHTML = courses.map(course => `
            <tr>
                <td>${course.id}</td>
                <td>${course.courseCode ?? "-"}</td>
                <td>${course.courseName ?? "-"}</td>
                <td>${course.credits ?? "-"}</td>
                <td>${course.semester ?? "-"}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load courses.");
        console.error(error);
    }
}

async function loadDepartments() {
    try {
        const departments = await fetchJson(`${API_BASE}/departments`);
        document.getElementById("tableHead").innerHTML = `
            <tr>
                <th>ID</th>
                <th>Department</th>
            </tr>
        `;
        document.getElementById("tableBody").innerHTML = departments.map(dept => `
            <tr>
                <td>${dept.id}</td>
                <td>${dept.departmentName ?? "-"}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load departments.");
        console.error(error);
    }
}

async function loadAssignments() {
    try {
        const assignments = await fetchJson(`${API_BASE}/assignments`);
        document.getElementById("tableHead").innerHTML = `
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Course</th>
                <th>Teacher</th>
                <th>Due Date</th>
            </tr>
        `;
        document.getElementById("tableBody").innerHTML = assignments.map(assignment => `
            <tr>
                <td>${assignment.id}</td>
                <td>${assignment.title ?? "-"}</td>
                <td>${assignment.course?.courseName ?? "-"}</td>
                <td>${assignment.teacher?.user?.fullName ?? "-"}</td>
                <td>${formatDate(assignment.dueDate)}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load assignments.");
        console.error(error);
    }
}

async function loadExams() {
    try {
        const exams = await fetchJson(`${API_BASE}/exams`);
        document.getElementById("tableHead").innerHTML = `
            <tr>
                <th>ID</th>
                <th>Exam</th>
                <th>Course</th>
                <th>Teacher</th>
                <th>Date</th>
            </tr>
        `;
        document.getElementById("tableBody").innerHTML = exams.map(exam => `
            <tr>
                <td>${exam.id}</td>
                <td>${exam.examName ?? "-"}</td>
                <td>${exam.course?.courseName ?? "-"}</td>
                <td>${exam.teacher?.user?.fullName ?? "-"}</td>
                <td>${formatDate(exam.examDate)}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load exams.");
        console.error(error);
    }
}

async function loadSubmissions() {
    try {
        const submissions = await fetchJson(`${API_BASE}/submissions`);
        document.getElementById("tableHead").innerHTML = `
            <tr>
                <th>ID</th>
                <th>Assignment</th>
                <th>Student</th>
                <th>File</th>
                <th>Submitted At</th>
            </tr>
        `;
        document.getElementById("tableBody").innerHTML = submissions.map(item => `
            <tr>
                <td>${item.id}</td>
                <td>${item.assignment?.title ?? "-"}</td>
                <td>${item.student?.usn ?? "-"}</td>
                <td>${item.fileUrl ? `<a href="${item.fileUrl}" target="_blank">View</a>` : "-"}</td>
                <td>${formatDate(item.submittedAt)}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load submissions.");
        console.error(error);
    }
}

function renderAdminForm() {
    const section = document.getElementById("section").value;
    const container = document.getElementById("adminForm");

    if (section === "department") {
        container.innerHTML = `
            <div class="mb-3">
                <label for="departmentName" class="form-label">Department Name</label>
                <input id="departmentName" type="text" class="form-control" placeholder="Department Name">
            </div>
            <button class="btn btn-primary w-100" onclick="createDepartment()">Create Department</button>
        `;
    } else {
        container.innerHTML = `
            <div class="mb-3">
                <label for="courseCode" class="form-label">Course Code</label>
                <input id="courseCode" type="text" class="form-control" placeholder="Course Code">
            </div>
            <div class="mb-3">
                <label for="courseName" class="form-label">Course Name</label>
                <input id="courseName" type="text" class="form-control" placeholder="Course Name">
            </div>
            <div class="mb-3">
                <label for="courseCredits" class="form-label">Credits</label>
                <input id="courseCredits" type="number" class="form-control" placeholder="Credits">
            </div>
            <div class="mb-3">
                <label for="courseSemester" class="form-label">Semester</label>
                <input id="courseSemester" type="number" class="form-control" placeholder="Semester">
            </div>
            <button class="btn btn-primary w-100" onclick="createCourse()">Create Course</button>
        `;
    }
}

async function createCourse() {
    const courseCode = document.getElementById("courseCode").value.trim();
    const courseName = document.getElementById("courseName").value.trim();
    const credits = Number(document.getElementById("courseCredits").value);
    const semester = Number(document.getElementById("courseSemester").value);

    if (!courseCode || !courseName || !credits || !semester) {
        alert("Please complete all course fields.");
        return;
    }

    try {
        await fetchJson(`${API_BASE}/courses`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({courseCode, courseName, credits, semester})
        });
        alert("Course created.");
        loadCourses();
    } catch (error) {
        alert("Unable to create course.");
        console.error(error);
    }
}

async function createDepartment() {
    const departmentName = document.getElementById("departmentName").value.trim();
    if (!departmentName) {
        alert("Please enter a department name.");
        return;
    }
    try {
        await fetchJson(`${API_BASE}/departments`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({departmentName})
        });
        alert("Department created.");
        loadDepartments();
    } catch (error) {
        alert("Unable to create department.");
        console.error(error);
    }
}

window.onload = function () {
    const user = getUser();
    if (!user) {
        return;
    }

    showUserName();
    renderAdminForm();
    loadStudents();
};
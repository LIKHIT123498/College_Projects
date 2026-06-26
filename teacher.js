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
        document.getElementById("tableBody").innerHTML = students.map(s => `
            <tr>
                <td>${s.id}</td>
                <td>${s.usn ?? "-"}</td>
                <td>${s.user?.fullName ?? "-"}</td>
                <td>${s.user?.email ?? "-"}</td>
                <td>${s.semester ?? "-"}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load students.");
        console.error(error);
    }
}

async function loadAttendance() {
    try {
        const attendance = await fetchJson(`${API_BASE}/attendance`);
        document.getElementById("tableHead").innerHTML = `
            <tr>
                <th>ID</th>
                <th>Student</th>
                <th>Course</th>
                <th>Date</th>
                <th>Status</th>
            </tr>
        `;
        document.getElementById("tableBody").innerHTML = attendance.map(a => `
            <tr>
                <td>${a.id}</td>
                <td>${a.student?.usn ?? "-"}</td>
                <td>${a.course?.courseName ?? "-"}</td>
                <td>${formatDate(a.attendanceDate)}</td>
                <td>${a.status ?? "-"}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load attendance.");
        console.error(error);
    }
}

async function loadMarks() {
    try {
        const marks = await fetchJson(`${API_BASE}/marks`);
        document.getElementById("tableHead").innerHTML = `
            <tr>
                <th>ID</th>
                <th>Student</th>
                <th>Exam</th>
                <th>Marks</th>
            </tr>
        `;
        document.getElementById("tableBody").innerHTML = marks.map(m => `
            <tr>
                <td>${m.id}</td>
                <td>${m.student?.usn ?? "-"}</td>
                <td>${m.exam?.examName ?? "-"}</td>
                <td>${m.marksObtained ?? "-"}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load marks.");
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
                <th>Due Date</th>
            </tr>
        `;
        document.getElementById("tableBody").innerHTML = assignments.map(a => `
            <tr>
                <td>${a.id}</td>
                <td>${a.title ?? "-"}</td>
                <td>${a.course?.courseName ?? "-"}</td>
                <td>${formatDate(a.dueDate)}</td>
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
                <th>Name</th>
                <th>Course</th>
                <th>Teacher</th>
                <th>Date</th>
            </tr>
        `;
        document.getElementById("tableBody").innerHTML = exams.map(e => `
            <tr>
                <td>${e.id}</td>
                <td>${e.examName ?? "-"}</td>
                <td>${e.course?.courseName ?? "-"}</td>
                <td>${e.teacher?.user?.fullName ?? "-"}</td>
                <td>${formatDate(e.examDate)}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load exams.");
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
        document.getElementById("tableBody").innerHTML = courses.map(c => `
            <tr>
                <td>${c.id}</td>
                <td>${c.courseCode ?? "-"}</td>
                <td>${c.courseName ?? "-"}</td>
                <td>${c.credits ?? "-"}</td>
                <td>${c.semester ?? "-"}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load courses.");
        console.error(error);
    }
}

async function addStudent() {
    const usn = document.getElementById("usn").value.trim();
    const semester = document.getElementById("semester").value.trim();

    if (!usn || !semester) {
        alert("Please enter both USN and semester.");
        return;
    }

    try {
        await fetchJson(`${API_BASE}/students`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({usn, semester})
        });

        alert("Student added successfully.");
        document.getElementById("usn").value = "";
        document.getElementById("semester").value = "";
        loadStudents();
    } catch (error) {
        alert("Unable to add student.");
        console.error(error);
    }
}

window.onload = function () {
    const user = ensureRole(["TEACHER"]);
    if (!user) {
        return;
    }

    showUserName();
    loadStudents();
};
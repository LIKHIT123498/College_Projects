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
                <td>${c.courseCode}</td>
                <td>${c.courseName}</td>
                <td>${c.credits ?? "-"}</td>
                <td>${c.semester ?? "-"}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load courses.");
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
        alert("Unable to load attendance data.");
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
                <th>Marks Obtained</th>
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

async function loadTimetable() {
    try {
        const timetable = await fetchJson(`${API_BASE}/timetable`);
        document.getElementById("tableHead").innerHTML = `
            <tr>
                <th>ID</th>
                <th>Day</th>
                <th>Course</th>
                <th>Start Time</th>
                <th>End Time</th>
            </tr>
        `;

        document.getElementById("tableBody").innerHTML = timetable.map(item => `
            <tr>
                <td>${item.id}</td>
                <td>${item.day ?? "-"}</td>
                <td>${item.course?.courseName ?? "-"}</td>
                <td>${item.startTime ?? "-"}</td>
                <td>${item.endTime ?? "-"}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load timetable.");
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
                <th>Description</th>
                <th>Course</th>
                <th>Due Date</th>
            </tr>
        `;

        document.getElementById("tableBody").innerHTML = assignments.map(a => `
            <tr>
                <td>${a.id}</td>
                <td>${a.title ?? "-"}</td>
                <td>${a.description ?? "-"}</td>
                <td>${a.course?.courseName ?? "-"}</td>
                <td>${formatDate(a.dueDate)}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load assignments.");
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
                <th>File URL</th>
                <th>Submitted At</th>
            </tr>
        `;

        document.getElementById("tableBody").innerHTML = submissions.map(s => `
            <tr>
                <td>${s.id}</td>
                <td>${s.assignment?.title ?? "-"}</td>
                <td>${s.student?.usn ?? "-"}</td>
                <td>${s.fileUrl ? `<a href="${s.fileUrl}" target="_blank">View</a>` : "-"}</td>
                <td>${formatDate(s.submittedAt)}</td>
            </tr>
        `).join("");
    } catch (error) {
        alert("Unable to load submissions.");
        console.error(error);
    }
}

async function loadAttendanceChart() {
    try {
        const attendance = await fetchJson(`${API_BASE}/attendance`);
        let present = 0;
        let absent = 0;
        attendance.forEach(a => {
            if (a.status && a.status.toLowerCase() === "present") {
                present++;
            } else {
                absent++;
            }
        });

        const canvas = document.getElementById("attendanceChart");
        if (!canvas) return;

        new Chart(canvas, {
            type: "pie",
            data: {
                labels: ["Present", "Absent"],
                datasets: [{
                    data: [present, absent],
                    backgroundColor: ["#0d6efd", "#dc3545"]
                }]
            }
        });
    } catch (error) {
        console.error("Unable to load attendance chart.", error);
    }
}

async function loadMarksChart() {
    try {
        const marks = await fetchJson(`${API_BASE}/marks`);
        const labels = marks.map(m => `Record ${m.id}`);
        const data = marks.map(m => Number(m.marksObtained || 0));

        const canvas = document.getElementById("marksChart");
        if (!canvas) return;

        new Chart(canvas, {
            type: "bar",
            data: {
                labels,
                datasets: [{
                    label: "Marks Obtained",
                    data,
                    backgroundColor: "#0d6efd"
                }]
            }
        });
    } catch (error) {
        console.error("Unable to load marks chart.", error);
    }
}

window.onload = function () {
    const user = ensureRole(["STUDENT"]);
    if (!user) {
        return;
    }

    showUserName();
    loadCourses();
    loadAttendanceChart();
    loadMarksChart();
};
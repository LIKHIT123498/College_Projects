async function login() {
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if (!email || !password) {
        alert("Please enter both email and password.");
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/login", {
            method: "POST",
            headers: {"Content-Type":"application/json"},
            body: JSON.stringify({email, password})
        });

        if (!response.ok) {
            const errorText = await response.text();
            alert(errorText || "Login failed. Check your credentials.");
            return;
        }

        const user = await response.json();
        localStorage.setItem("user", JSON.stringify(user));

        if (user.role === "STUDENT") {
            window.location.href = "pages/student.html";
        } else if (user.role === "TEACHER") {
            window.location.href = "pages/teacher.html";
        } else {
            window.location.href = "pages/admin.html";
        }
    } catch (error) {
        alert("Login failed. Please try again.");
        console.error(error);
    }
}
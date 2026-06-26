const API_BASE = "http://localhost:8080";

function getUser() {
    const raw = localStorage.getItem("user");
    if (!raw) {
        window.location.href = "../index.html";
        return null;
    }

    try {
        return JSON.parse(raw);
    } catch (error) {
        localStorage.removeItem("user");
        window.location.href = "../index.html";
        return null;
    }
}

function ensureRole(allowedRoles) {
    const user = getUser();
    if (!user) {
        return null;
    }

    if (!Array.isArray(allowedRoles)) {
        allowedRoles = [allowedRoles];
    }

    if (!allowedRoles.includes(user.role)) {
        alert("Access denied.");
        logout();
        return null;
    }

    return user;
}

function logout() {
    localStorage.removeItem("user");
    window.location.href = "../index.html";
}

function showUserName() {
    const user = getUser();
    if (!user) {
        return;
    }
    const userNameEl = document.getElementById("userName");
    if (userNameEl) {
        userNameEl.textContent = user.fullName || user.email || "User";
    }
}

async function fetchJson(url, options = {}) {
    const response = await fetch(url, options);
    if (!response.ok) {
        const text = await response.text();
        throw new Error(text || response.statusText);
    }
    return response.json();
}

function formatDate(value) {
    if (!value) return "-";
    try {
        return new Date(value).toLocaleDateString();
    } catch {
        return value;
    }
}

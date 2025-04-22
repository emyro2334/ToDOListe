const apiBaseUrl = "http://localhost:9999/tasks"; // Backend-URL

// DOM-Elemente
const taskForm = document.getElementById("task-form");
const tasksTableBody = document.getElementById("tasks-table-body");

// Lade alle Aufgaben beim Start
window.onload = fetchTasks;

// Aufgaben abrufen
async function fetchTasks() {
    const response = await fetch(apiBaseUrl);
    const tasks = await response.json();
    renderTasks(tasks);
}

// Aufgaben in der Tabelle anzeigen
function renderTasks(tasks) {
    tasksTableBody.innerHTML = "";
    tasks.forEach((task) => {
        const row = document.createElement("tr");
        row.innerHTML = `
      <td>${task.id}</td>
      <td>${task.title}</td>
      <td>${task.description}</td>
      <td>${task.person}</td>
      <td>${task.isDone ? 'Offen' : 'Erledigt'}</td>
      <td>${task.dueDate ? new Date(task.dueDate).toLocaleString() : "Kein Datum"}</td>
      <td>
        <button class="action-btn edit-btn" onclick="editTask(${task.id})">Bearbeiten</button>
        <button class="action-btn delete-btn" onclick="deleteTask(${task.id})">Löschen</button>
        <button class="action-btn ical-btn" onclick="exportTaskAsICal(${task.id})">iCal Export</button>
      </td>
    `;
        tasksTableBody.appendChild(row);
    });
}

// Aufgabe erstellen oder aktualisieren
taskForm.onsubmit = async (event) => {
    event.preventDefault();

    const formData = new FormData(taskForm);
    const task = {
        title: formData.get("title"),
        description: formData.get("description"),
        person: formData.get("person"),
        status: formData.get("status"),
        dueDate: formData.get("dueDate") ? new Date(formData.get("dueDate")).toISOString() : null,
    };

    if (taskForm.dataset.id) {
        // Bearbeiten
        await fetch(`${apiBaseUrl}/${taskForm.dataset.id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(task),
        });
        delete taskForm.dataset.id;
    } else {
        // Neu erstellen
        await fetch(apiBaseUrl, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(task),
        });
    }

    taskForm.reset();
    fetchTasks();
};

// Aufgabe löschen
async function deleteTask(id) {
    await fetch(`${apiBaseUrl}/${id}`, { method: "DELETE" });
    fetchTasks();
}

// Aufgabe bearbeiten
async function editTask(id) {
    const response = await fetch(`${apiBaseUrl}/${id}`);
    const task = await response.json();

    taskForm.dataset.id = id;
    document.getElementById("title").value = task.title;
    document.getElementById("description").value = task.description;
    document.getElementById("person").value = task.person;
    document.getElementById("status").value = task.status;
    document.getElementById("dueDate").value = task.dueDate ? task.dueDate.substring(0, 16) : "";
}

// iCal-Datei exportieren
function exportTaskAsICal(id) {
    window.open(`${apiBaseUrl}/${id}/ical`, "_blank");
}

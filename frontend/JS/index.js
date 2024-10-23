document.addEventListener('DOMContentLoaded', () => {
    const todoSection = document.getElementById('todo-list');
    const inProgressSection = document.getElementById('in-progress-list');
    const doneSection = document.getElementById('done-list');
    const addTaskBtn = document.getElementById('add-task');
    const menu = document.getElementById('menu');
    let closeMenuButton;
    const form = document.querySelector('#modal-form');
    let currentTaskElement = null;

    addTaskBtn.addEventListener('click', async function() {
        menu.classList.remove('hidden');
        closeMenuButton = document.getElementById('close-menu-button');
        closeMenuButton.addEventListener('click', closeMenu);
        const createTaskButton = document.querySelector('button[type="submit"]');
        createTaskButton.addEventListener('click', saveTask);
        currentTaskElement = null
    });

    form.addEventListener('submit', (event) => {
        event.preventDefault();

        const taskTitle = document.querySelector('#modal-input').value;
        const taskDescription = document.querySelector('#modal-description').value;
        const taskStatus = document.querySelector('#modal-status').value;

        // console.log('Task Title:', taskTitle);
        // console.log('Task Description:', taskDescription);
        // console.log('Task Status:', taskStatus);

        if (currentTaskElement) {
            updateTask(currentTaskElement, taskTitle, taskDescription, taskStatus);
        } else {
            saveTask(taskTitle, taskDescription, taskStatus);
        }

        const modal = document.querySelector('#task-modal');
        modal.classList.add('hidden');
    });

    function closeMenu() {
        menu.classList.add('hidden');
    }

    function moveTaskToNewSection(taskElement, newStatus) {
        taskElement.parentNode.removeChild(taskElement);

        switch (newStatus) {
            case 'todo':
                saveToDoTask(taskElement);
                break;
            case 'in-progress':
                saveInProgressTask(taskElement);
                break;
            case 'done':
                saveDoneTask(taskElement);
                break;
        }
    }

    function updateTask(taskElement, newTitle, newDescription, newStatus) {
        const currentStatus = taskElement.getAttribute('data-status');
        taskElement.querySelector('.task-title').innerText = newTitle;
        taskElement.querySelector('.task-description').innerText = newDescription;

        if (currentStatus !== newStatus) {
            taskElement.setAttribute('data-status', newStatus);
            updateTaskCount(currentStatus, -1);
            moveTaskToNewSection(taskElement, newStatus);
        }
    }

    function getTasks() {
        const taskData = {
            title: "New Task",
            description: "This is a new task"
        };

        fetch('http://localhost:8080/api/tasks', {
            method: 'POST',  // HTTP method
            headers: {
                'Content-Type': 'application/json'  // Specify that we're sending JSON data
            },
            body: JSON.stringify(taskData)  // Convert the JavaScript object into a JSON string
        })
            .then(response => response.json())  // Convert the response to JSON
            .then(data => {
                console.log('Success:', data);  // Handle the success
            })
            .catch(error => {
                console.error('Error:', error);  // Handle the error
            });
    }

    async function saveTask(event) {
        event.preventDefault();

        const taskTitle = document.querySelector('input[placeholder="e.g. Take coffee break"]').value;
        const taskDescription = document.querySelector('textarea[placeholder="e.g. It\'s always good to take a break. This 15 minute break will recharge the batteries a little."]').value;

        const taskStatus = document.querySelector('select').value;

        // console.log('Task Title:', taskTitle);
        // console.log('Task Description:', taskDescription);
        // console.log('Task Status:', taskStatus);

        let newTask = document.createElement('div');
        newTask.className = 'bg-card p-4 rounded-lg';
        newTask.innerHTML = `
        <button class="open-task-button text-left">
            <h4 class="text-lg font-medium task-title">${taskTitle}</h4>
            <p class="task-description">${taskDescription}</p>
        </button>
      `;
        newTask.setAttribute('title', taskTitle);
        newTask.setAttribute('description', taskDescription);
        newTask.setAttribute('data-status', taskStatus);
        switch (taskStatus) {
            case 'todo':
                saveToDoTask(newTask);
                break;
            case 'in-progress':
                saveInProgressTask(newTask);
                break;
            case 'done':
                saveDoneTask(newTask);
                break;
        }

        const taskButton = newTask.querySelector('.open-task-button');
        taskButton.addEventListener('click', () => {
            openTaskModal(newTask, taskTitle, taskDescription);
        });

        getTasks()

        closeMenu();
    }

    function openTaskModal(elem, taskTitle, taskDescription) {
        const modal = document.querySelector('#task-modal');
        const modalTitle = document.querySelector('#modal-input');
        const modalDescription = document.querySelector('#modal-description');
        const modalStatus = document.querySelector('#modal-status');

        modalTitle.value = taskTitle;
        modalDescription.value = taskDescription;
        modalStatus.value = elem.getAttribute('data-status');

        currentTaskElement = elem;

        modal.classList.remove('hidden');
        modal.classList.add('flex');

        const closeModalButton = modal.querySelector('.close-modal-button');
        closeModalButton.addEventListener('click', () => {
            modal.classList.add('hidden');
        });
    }

    function saveDoneTask(newTask) {
        doneSection.append(newTask)

        // Update the Done count
        const doneCountElement = document.getElementById('done-count');
        const currentCount = parseInt(doneCountElement.innerText.match(/\d+/)[0], 10);
        doneCountElement.innerText = `DONE (${currentCount + 1})`;
    }

    function saveInProgressTask(newTask) {
        inProgressSection.append(newTask)

        // Update the In Progress count
        const doingCountElement = document.getElementById('doing-count');
        const currentCount = parseInt(doingCountElement.innerText.match(/\d+/)[0], 10);
        doingCountElement.innerText = `DOING (${currentCount + 1})`;
    }

    function saveToDoTask(newTask) {
        todoSection.appendChild(newTask);

        // Update the TODO count
        const todoCountElement = document.getElementById('todo-count');
        const currentCount = parseInt(todoCountElement.innerText.match(/\d+/)[0], 10); // Extract the number
        todoCountElement.innerText = `TODO (${currentCount + 1})`; // Update the count
    }

    function updateTaskCount(status, increment) {
        let countElement;

        switch (status) {
            case 'todo':
                countElement = document.getElementById('todo-count');
                break;
            case 'doing':
                countElement = document.getElementById('doing-count');
                break;
            case 'done':
                countElement = document.getElementById('done-count');
                break;
        }

        if (countElement) {
            const currentCount = parseInt(countElement.innerText.match(/\d+/)[0], 10);
            countElement.innerText = `${status.toUpperCase()} (${currentCount + increment})`;
        }
    }

    async function saveToDatabase(title, description) {

    }

    function loadTasks() {
    }

    async function waitForResponse(response) {
        const result = await response.text();
        console.log(result);  // Show result from API
        if (response.ok) {
            loadTasks();
        }
    }
});
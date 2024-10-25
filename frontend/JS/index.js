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
        createTaskButton.addEventListener('click', saveUITask);
        currentTaskElement = null
    });

    function deleteUITask(task) {
        deleteTask(task.getAttribute('taskId'));
        const currentStatus = task.getAttribute('data-status');
        updateTaskCount(currentStatus, -1);
        moveTaskToNewSection(task, '');
    }

    form.addEventListener('submit', (event) => {
        event.preventDefault();
        const clickedButton = event.submitter;
        const action = clickedButton.value;  // Get the button's value (e.g., "save" or "delete")

        if (action === 'save') {
            const taskTitle = document.querySelector('#modal-input').value;
            const taskDescription = document.querySelector('#modal-description').value;
            const taskStatus = document.querySelector('#modal-status').value;

            if (currentTaskElement) {
                updateUITask(currentTaskElement, taskTitle, taskDescription, taskStatus);
            } else {
                saveUITask(taskTitle, taskDescription, taskStatus);
            }
        } else if (action === 'delete') {
            deleteUITask(currentTaskElement);
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
                markTaskAs(taskElement.getAttribute('taskId'), 'in-progress');
                break;
            case 'done':
                saveDoneTask(taskElement);
                markTaskAs(taskElement.getAttribute('taskId'), 'done');
                break;
            default:
                break;
        }
    }

    function updateUITask(taskElement, newTitle, newDescription, newStatus) {
        const currentStatus = taskElement.getAttribute('data-status');
        taskElement.querySelector('.task-title').innerText = newTitle;
        taskElement.querySelector('.task-description').innerText = newDescription;
        updateTask(taskElement.getAttribute('taskId'), newTitle, newDescription);
        if (currentStatus !== newStatus) {
            taskElement.setAttribute('data-status', newStatus);
            updateTaskCount(currentStatus, -1);
            moveTaskToNewSection(taskElement, newStatus);
        }
    }

    async function saveUITask(event) {
        event.preventDefault();

        const taskTitle = document.querySelector('input[placeholder="e.g. Take coffee break"]').value;
        const taskDescription = document.querySelector('textarea[placeholder="e.g. It\'s always good to take a break. This 15 minute break will recharge the batteries a little."]').value;

        const taskStatus = 'to do';

        console.log("Task status: " + taskStatus);
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
        saveToDoTask(newTask);

        const taskButton = newTask.querySelector('.open-task-button');
        taskButton.addEventListener('click', () => {
            openTaskModal(newTask, taskTitle, taskDescription);
        });

        await addTask(newTask, taskTitle, taskDescription);

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

        const doneCountElement = document.getElementById('done-count');
        const currentCount = parseInt(doneCountElement.innerText.match(/\d+/)[0], 10);
        doneCountElement.innerText = `IN-PROGRESS (${currentCount + 1})`;
    }

    function saveInProgressTask(newTask) {
        inProgressSection.append(newTask)

        const doingCountElement = document.getElementById('doing-count');
        const currentCount = parseInt(doingCountElement.innerText.match(/\d+/)[0], 10);
        doingCountElement.innerText = `DOING (${currentCount + 1})`;
    }

    function saveToDoTask(newTask) {
        todoSection.append(newTask);

        // Update the TODO count
        const todoCountElement = document.getElementById('todo-count');
        const currentCount = parseInt(todoCountElement.innerText.match(/\d+/)[0], 10); // Extract the number
        todoCountElement.innerText = `TODO (${currentCount + 1})`; // Update the count
    }

    function updateTaskCount(status, increment) {
        let countElement;
        console.log(status);
        switch (status) {
            case 'to do':
                countElement = document.getElementById('todo-count');
                break;
            case 'in-progress':
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

    const addTask = async (newTask, title, description) => {
        const url = `http://localhost:8080/api/tasks?title=${encodeURIComponent(title)}&description=${encodeURIComponent(description)}`;
        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            const data = await response.json();

            if (response.ok) {
                const taskId = data.taskId;
                newTask.setAttribute('taskId', taskId);
                console.log(taskId);
            } else {
                console.error('Error adding task:', data.message || 'Unknown error');
            }
        } catch (error) {
            console.error('Error adding task:', error);
        }
    };

    const updateTask = async (taskId, taskTitle, taskDescription) => {
        console.log("Updating...")
        const url = `http://localhost:8080/api/tasks/${encodeURIComponent(taskId)}?title=${encodeURIComponent(taskTitle)}&description=${encodeURIComponent(taskDescription)}`;
        try {
            const response = await fetch(url, {
                method: 'PUT',
            });
            const data = await response.text();
        } catch (error) {
            console.error('Error updating task:', error);
        }
    }

    const markTaskAs = async (taskId, status) => {
        const url = `http://localhost:8080/api/tasks/${encodeURIComponent(taskId)}/status?status=${encodeURIComponent(status)}`;
        try {
            const response = await fetch(url, {
                method: 'PATCH',
            });
            const data = await response.text();
        } catch (error) {
            console.error('Error updating task:', error);
        }
    }

    const deleteTask = async (taskId) => {
        const url = `http://localhost:8080/api/tasks/${encodeURIComponent(taskId)}`;

        try {
            const response = await fetch(url, {
               method: 'DELETE',
            });
            const data = await response.text();
        } catch (error) {
            console.error('Error deleting task:', error);
        }
    }

    const loadTodoTasks = async () => {
        const todoListDiv = document.getElementById('todo-list');

        try {
            const response = await fetch('http://localhost:8080/api/tasks/todo', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error('Failed to fetch todo tasks');
            }

            const tasks = await response.json();

            todoListDiv.innerHTML = '';

            tasks.forEach(task => {
                let newTask = document.createElement('div');
                newTask.className = 'bg-card p-4 rounded-lg';
                newTask.innerHTML = `
                <button class="open-task-button text-left">
                    <h4 class="text-lg font-medium task-title">${task.title}</h4>
                    <p class="task-description">${task.description}</p>
                </button>
                
      `;
                newTask.setAttribute('title', task.title);
                newTask.setAttribute('description', task.description);
                newTask.setAttribute('data-status', 'to do');
                newTask.setAttribute('taskId', task.id);

                const taskButton = newTask.querySelector('.open-task-button');
                taskButton.addEventListener('click', () => {
                    openTaskModal(newTask, task.title, task.description);
                });
                updateTaskCount(task.status, 1);
                todoListDiv.appendChild(newTask);
            });
        } catch (error) {
            console.error('Error loading todo tasks:', error);
            todoListDiv.innerHTML = '<p class="text-red-600">Failed to load tasks</p>';
        }
    };

    const loadInProgressTasks = async () => {
        const todoListDiv = document.getElementById('in-progress-list');

        try {
            const response = await fetch('http://localhost:8080/api/tasks/in-progress', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error('Failed to fetch in-progress tasks');
            }

            const tasks = await response.json();

            todoListDiv.innerHTML = '';

            tasks.forEach(task => {
                let newTask = document.createElement('div');
                newTask.className = 'bg-card p-4 rounded-lg';
                newTask.innerHTML = `
                <button class="open-task-button text-left">
                    <h4 class="text-lg font-medium task-title">${task.title}</h4>
                    <p class="task-description">${task.description}</p>
                </button>
                
      `;
                newTask.setAttribute('title', task.title);
                newTask.setAttribute('description', task.description);
                newTask.setAttribute('data-status', 'in-progress');
                newTask.setAttribute('taskId', task.id);

                const taskButton = newTask.querySelector('.open-task-button');
                taskButton.addEventListener('click', () => {
                    openTaskModal(newTask, task.title, task.description);
                });
                updateTaskCount(task.status, 1);
                todoListDiv.appendChild(newTask);
            });
        } catch (error) {
            console.error('Error loading todo tasks:', error);
            todoListDiv.innerHTML = '<p class="text-red-600">Failed to load tasks</p>';
        }
    };

    const loadDoneTasks = async () => {
        const todoListDiv = document.getElementById('done-list');

        try {
            const response = await fetch('http://localhost:8080/api/tasks/done', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error('Failed to fetch in-progress tasks');
            }

            const tasks = await response.json();

            todoListDiv.innerHTML = '';

            tasks.forEach(task => {
                let newTask = document.createElement('div');
                newTask.className = 'bg-card p-4 rounded-lg';
                newTask.innerHTML = `
                <button class="open-task-button text-left">
                    <h4 class="text-lg font-medium task-title">${task.title}</h4>
                    <p class="task-description">${task.description}</p>
                </button>
                
      `;
                newTask.setAttribute('title', task.title);
                newTask.setAttribute('description', task.description);
                newTask.setAttribute('data-status', 'done');
                newTask.setAttribute('taskId', task.id);

                const taskButton = newTask.querySelector('.open-task-button');
                taskButton.addEventListener('click', () => {
                    openTaskModal(newTask, task.title, task.description);
                });
                updateTaskCount(task.status, 1);
                todoListDiv.appendChild(newTask);
            });
        } catch (error) {
            console.error('Error loading todo tasks:', error);
            todoListDiv.innerHTML = '<p class="text-red-600">Failed to load tasks</p>';
        }
    };

    loadTodoTasks();
    loadInProgressTasks();
    loadDoneTasks();
});
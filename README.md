<<<<<<< HEAD
# Task-Tracker
=======

# Task Tracker

This is a full-stack **Task Tracker** application built with **Spring Boot (Maven)** for the backend and **Tailwind CSS** and **pure JavaScript** for the frontend. The application allows users to manage tasks with functionality to add, update, delete, and mark tasks as complete or in-progress.

## Features

- Add, update, and delete tasks
- Mark tasks as done or in-progress
- View a list of tasks
- RESTful API with CRUD operations
- User-friendly UI built with Tailwind CSS and JavaScript

## Tech Stack

- **Backend**: Java with Spring Boot (Maven)
- **Frontend**: HTML, Tailwind CSS, and pure JavaScript
- **Database**: H2 (in-memory)

## Project Structure

```
Task-Tracker/
│
├── /backend/                   # Spring Boot backend code
│   ├── /src/                   # Java source code and resources
│   ├── /target/                # Compiled Java files
│   ├── /logs/                  # Log files
│   ├── pom.xml                 # Maven dependencies
│   └── document.txt            # Documentation for backend
│
├── /frontend/                  # Frontend code (HTML, Tailwind CSS, JavaScript)
│   ├── /CSS/                   # Tailwind CSS files
│   ├── /JS/                    # JavaScript files
│   ├── index.html              # Main HTML file
│   └── package.json            # Frontend dependencies (if using npm)
│
├── .gitignore                  # Git ignore file
└── README.md                   # Project documentation (this file)
```

## Prerequisites

- **Java 11** or higher
- **Maven** (for backend dependencies)
- **Node.js** and **npm** (optional, for building frontend if needed)

## Setup Instructions

### 1. Clone the Repository
Clone the repository from GitHub:

```bash
git clone <your-repo-url>
cd Task-Tracker
```

### 2. Running the Backend

Navigate to the `backend` directory and build the Spring Boot project:

```bash
cd backend
mvn clean install
```

Run the Spring Boot application:

```bash
mvn spring-boot:run
```

The backend will start on [http://localhost:8080](http://localhost:8080).

#### API Endpoints

- **GET** `/api/tasks` - List all tasks
- **POST** `/api/tasks` - Add a new task
- **PUT** `/api/tasks/{id}` - Update a task by ID
- **DELETE** `/api/tasks/{id}` - Delete a task by ID
- **PATCH** `/api/tasks/{id}/status` - Mark task as done or in-progress

### 3. Running the Frontend

Navigate to the `frontend` directory:

```bash
cd ../frontend
```

If you are using **npm** to manage the frontend dependencies, install the required dependencies:

```bash
npm install
```

Start the frontend:

- If you have a local server (like `live-server`), you can use it to serve the `index.html` file, or:
- Simply open `index.html` in your browser.

```bash
open index.html
```

The frontend will interact with the backend via REST API.

## Customization

- **Tailwind CSS**: Customize the styles in the `/frontend/CSS/` folder using Tailwind classes.
- **JavaScript**: Update the `/frontend/JS/` folder to modify the frontend logic.

## Logging

Log files for the backend are stored in the `/backend/logs/` directory. You can configure logging in `src/main/resources/application.properties`.

## License

This project is licensed under the MIT License.
>>>>>>> e380b9e (Initial Commit)

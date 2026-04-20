# 🚀 Kanban Project Management System 

A full-stack **Kanban-based Project Management System** built using:

* 🌐 **Frontend:** Angular
* ⚙️ **Backend:** Spring Boot
* 🗄️ **Database:** PostgreSQL
* 🔐 **Security:** Role-Based Access Control (RBAC)

---

## 📌 Features

### 👤 Authentication & Authorization

* User Registration & Login
* Role-based access:

  * USER
  * SCRUM
  * ADMIN
* Secure password storage (hashed)
* RBAC implemented using `user_roles` mapping

---

### 📊 Kanban Board

* Create & manage **User Stories**
* Track progress:

  * TODO
  * IN_PROGRESS
  * DONE
* Assign tasks to team members

---

### 👥 Team Management

* Create teams
* Add/remove members
* Multi-team support (many-to-many)

---

### 🔁 Sprint / Iteration Management

* Plan iterations (sprints)
* Status tracking:

  * PLANNED
  * ACTIVE
  * COMPLETED

---

### 📘 User Stories

* Title, description, acceptance criteria
* Priority levels:

  * LOW, MEDIUM, HIGH, CRITICAL
* Time tracking:

  * Estimated vs Actual

---

### ✅ Tasks & Subtasks

* Linked to user stories
* Assign to users
* Track status & priority
* Time tracking support

---

### 🐞 Defect Tracking

* Bug tracking per story & iteration
* Assign and resolve defects

---

### 📎 Attachments

* Upload files to:

  * User Stories
  * Tasks
  * Defects
* Enforced constraint: linked to only one entity

---

## 🗂️ Project Structure

```
project-root/
│
├── frontend/        # Angular App
├── backend/         # Spring Boot App
├── db/
│   └── dbscripts.sql
└── README.md
```

---

## 🗄️ Database Design

Includes:

* Users & Roles (RBAC)
* Teams & Team Members
* Iterations (Sprints)
* User Stories
* Tasks
* Defects
* Attachments

👉 Full schema available in:

```
db/dbscripts.sql
```

---

## ⚙️ Setup Instructions

### 1️⃣ Clone Repository

```bash
git clone <your-repo-url>
cd project-root
```

---

### 2️⃣ Database Setup (PostgreSQL)

* Create database:

```sql
CREATE DATABASE kanban_db;
```

* Run script:

```bash
psql -U postgres -d kanban_db -f db/dbscripts.sql
```

---

### 3️⃣ Backend Setup (Spring Boot)

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

👉 Configure DB in:

```
application.properties
```

---

### 4️⃣ Frontend Setup (Angular)

```bash
cd frontend
npm install
ng serve
```

👉 App runs on:

```
http://localhost:4200
```

---

## 🔐 RBAC Flow

1. User registers → default role = USER
2. Admin upgrades role → SCRUM / ADMIN
3. Roles mapped via `user_roles`
4. Backend enforces access control

---

## 📌 API Highlights

* `/api/auth` → login/register
* `/api/users` → user management
* `/api/teams` → team operations
* `/api/stories` → user stories
* `/api/tasks` → tasks
* `/api/defects` → bugs

---

## 🧠 Tech Highlights

* MVVM Architecture (Frontend)
* REST APIs (Backend)
* Normalized DB Design
* Foreign Key Constraints
* Indexed Queries for performance

---

## 🚀 Future Enhancements

* 🔔 Notifications
* 📊 Dashboard Analytics
* 📅 Calendar View
* 🔄 Drag & Drop Kanban UI
* 📡 WebSocket for real-time updates

---

## 👩‍💻 Author

**Neha Tiwari**

* Full Stack Developer | JAVA Developer
* Skills: Java, Angular, Spring Boot, ML, C++

---

## ⭐ Contributing

Feel free to fork this repo and contribute!

---

## 📜 License

This project is open-source and available under the MIT License.

# University Library Management System (ULMS)

## Overview

This is a personal web application project built using the traditional **Java Servlet** and **JSP** stack. The system is designed to manage book inventory and student loan transactions for a university or school library. The architecture follows the **Model-View-Controller (MVC)** pattern for clear separation of concerns.

---

## 🚀 Key Features

* **Book Management (CRUD):** Add, view, edit, and delete book entries, including quantity tracking (`total_copies` and `available_copies`).
* **Student Management (CRUD):** Register and manage student profiles.
* **Loan Management:** Record book loans and returns, track due dates, and identify overdue books.
* **Categorization:** Organize books using the `categories` table for easy filtering and searching.
* **Database Integration:** Persistent data storage using MySQL and raw JDBC.

---

## 🛠️ Technology Stack

| Area | Technology | Purpose |
| --- | :--- | :--- |
| **Backend** | Java 8+ | Core application logic and Servlets. |
| **Web Container** | JSP / Servlet | Handling requests and dynamic view generation. |
| **Database** | MySQL | Data persistence and relational structure. |
| **Data Access** | JDBC Connector | Direct connection and execution of SQL queries. |
| **Utility** | Lombok | Boilerplate code reduction (Getters, Setters, etc.). |
| **Frontend** | HTML5, CSS3, JavaScript | User interface and basic client-side interaction. |
| **Templating** | JSTL | Standard tag library for JavaServer Pages to handle looping and conditional logic. |

---

## 📂 Project Structure

The project adheres to a clean **MVC** structure within an Eclipse **Dynamic Web Project** environment:

```
UniversityLibraryManagement/
├── src/main/java/
│   ├── com.library.controller/  # Servlets (Request handling, routing)
│   ├── com.library.service/     # Business Logic (Validation, Transaction coordination)
│   ├── com.library.dao/         # Data Access Objects (CRUD operations with DB)
│   ├── com.library.model/       # POJOs/Entities (DB schema mapping)
│   ├── com.library.dto/         # Data Transfer Objects (Data passed between layers)
│   └── com.library.util/        # DB Connection Manager
│
├── src/main/webapp/
│   ├── WEB-INF/
│   │   ├── lib/                 # Dependencies (MySQL Connector & Lombok JARs)
│   │   └── views/               # All JSPs (Secured from direct access)
│   ├── assets/
│   │   ├── css/                 # Stylesheets
│   │   ├── js/                  # JavaScript files
│   │   └── images/              # Images and icons
│   └── index.jsp                # Application entry point
│
└── database/                    # SQL scripts for database setup
├── 01_schema_creation.sql   # Creates all tables and foreign keys
└── 02_mock_data_insert.sql  # Populates tables with sample data


---
```


## ⚙️ Setup and Installation

### 1. Prerequisites

You must have the following installed and configured:
* **JDK 8 or higher**
* **Apache Tomcat Server** (e.g., Tomcat 9 or 10)
* **MySQL Server**
* **Eclipse IDE** (with Java EE/Web Tools)

### 2. Database Setup

1.  Start your MySQL Server.
2.  Open your MySQL client (Workbench, CLI, etc.).
3.  Execute the files located in the `database/` folder in the correct order:

   ```bash
    # Assuming you are connected to MySQL CLI
    SOURCE /path/to/UniversityLibraryManagement/database/01_schema_creation.sql;
    SOURCE /path/to/UniversityLibraryManagement/database/02_mock_data_insert.sql;
   ```
    
4.  The application will use the database named `university_library`.

### 3. Project Configuration

1.  **Import Project:** Import the project into Eclipse as an **Existing Dynamic Web Project**.
2.  **Add Dependencies:** Place the downloaded **`mysql-connector-java-x.x.x.jar`** and **`lombok.jar`** files into the **`src/main/webapp/WEB-INF/lib/`** directory.
3.  **Lombok IDE Setup:** Run the `lombok.jar` file to install the plugin into your Eclipse IDE. This is necessary for Eclipse to recognize the `@Data` and other annotations.
4.  **Configure DB Connection:** Open the `com.library.util.DBConnectionManager` class and update the connection details (`DB_URL`, `DB_USER`, `DB_PASSWORD`) to match your local MySQL setup.

### 4. Running the Application

1.  In Eclipse, right-click the project, select **Run As** → **Run on Server**.
2.  Choose your configured Apache Tomcat instance.
3.  Access the application in your browser at the default location (typically):
    `http://localhost:8080/UniversityLibraryManagement/`

---

## 💡 Contribution

This is a personal learning project. Feedback and suggestions are welcome!
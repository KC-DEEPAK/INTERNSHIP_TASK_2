# Library Management System

A professional, object-oriented Java console application built for the **SkillVoro Java Development Internship (Task 3)**.

---

## 📌 Project Overview
The **Library Management System** is a lightweight, menu-driven CLI application designed to automate book management operations for educational libraries. It allows library administrators to maintain a book catalog, display available inventory, search for books by ID or title, issue books to students, return issued books, and track real-time book availability.

---

## 🎯 Objective
- Implement core Java Object-Oriented Programming (OOP) concepts in a practical, real-world scenario.
- Demonstrate effective management of memory data structures using the Java Collections Framework (`ArrayList`).
- Build robust input validation routines to ensure seamless execution without runtime exceptions.
- Produce clean, maintainable, and self-documenting Java code suitable for portfolio and technical interview showcases.

---

## ✨ Features
1. **Add Book**: Register new books with unique positive IDs, titles, and author details.
2. **Display All Available Books**: View all books currently in stock formatted cleanly in an ASCII table.
3. **Search Book**:
   - Search by exact **Book ID**.
   - Search by **Book Title** or partial keyword (case-insensitive).
4. **Issue Book**: Issue available books to students using their student name.
5. **Return Book**: Process book returns and update library availability status in real-time.
6. **Display Book Availability**: Complete overview of all library books showing `AVAILABLE` or `ISSUED` status along with student details.
7. **Exit**: Gracefully terminate the application and release system input resources.

---

## 🛠️ Technologies Used
- **Language**: Java (JDK 8 or higher)
- **Development Environment**: VS Code / Command Line / Terminal
- **Data Structure**: `ArrayList` from `java.util` package
- **Input Stream**: `Scanner` from `java.util` package
- **Frameworks & Dependencies**: None (Standard Java SDK only)

---

## 🧩 OOP Concepts Demonstrated
- **Encapsulation**: Data fields in `Book` (`bookId`, `title`, `author`, `issued`, `issuedTo`) are `private` and accessed/modified exclusively through public getters and domain methods.
- **Abstraction**: `LibraryService` hides data manipulation details from the user interface controller (`LibraryManagementSystem`).
- **Single Responsibility Principle (SRP)**:
  - `Book`: Represents book entity and state.
  - `LibraryService`: Handles business logic and list management.
  - `LibraryManagementSystem`: Handles CLI rendering, menu loop, and user input validation.
- **Polymorphism & Collection Framework**: Utilizes Java's `List` interface backed by `ArrayList`.

---

## 📁 Project Structure

```
LIBRARY MANAGEMENT SYSTEM/
├── src/
│   └── librarymanagement/
│       ├── Book.java
│       ├── LibraryService.java
│       └── LibraryManagementSystem.java
├── bin/                       # Output directory for compiled bytecode (.class files)
├── README.md                  # Comprehensive project documentation
└── .gitignore                 # Specifies intentionally untracked files
```

---

## 🔄 How the System Works
1. **Initialization**: On application start, `LibraryService` is instantiated with sample book records (IDs: 101, 102, 103) for instant interaction.
2. **Menu Controller**: The `run()` loop in `LibraryManagementSystem` presents options to the user and processes selections.
3. **Input Sanitization**: User input is read line-by-line using `Scanner.nextLine()`. Numerical values are safely parsed with `Integer.parseInt()` to avoid Scanner line-buffer issues.
4. **Business Execution**: Input parameters are passed to `LibraryService` methods, which query or modify the `ArrayList<Book>`.
5. **Feedback**: Results are printed back to the user with formatted tables and clear status notifications.

---

## ⚙️ How to Compile and Run

### Prerequisites
- Java Development Kit (JDK 8 or later) installed.
- Verification: Run `java -version` and `javac -version` in your terminal.

### Compilation
From the root project folder, compile the Java files into the `bin/` directory:

```bash
javac -d bin src/librarymanagement/*.java
```

### Execution
Run the compiled application using:

```bash
java -cp bin librarymanagement.LibraryManagementSystem
```

---

## 💻 Example Output

### Main Menu
```
========================================
       LIBRARY MANAGEMENT SYSTEM
========================================
1. Add Book
2. Display All Books (Available)
3. Search Book
4. Issue Book
5. Return Book
6. Display Book Availability
7. Exit
========================================
Enter choice (1-7): 6
```

### Display Book Availability Table
```
----------------------------------------------------------------------------------------------------
ID       TITLE                        AUTHOR                   STATUS         ISSUED TO
----------------------------------------------------------------------------------------------------
101      Java Programming             James Gosling            ISSUED         Rahul
102      Clean Code                   Robert Martin            AVAILABLE      -
103      Data Structures in Java      Mark Allen Weiss         AVAILABLE      -
----------------------------------------------------------------------------------------------------
```

---

## 🛡️ Input Validation & Error Handling
The application incorporates defensive programming to guarantee stability:

| Potential Issue | Handling Mechanism | User Feedback |
| :--- | :--- | :--- |
| **Invalid Input Type** (e.g. entering letters for ID) | Wrapped in `try-catch (NumberFormatException)` | `[Error] Invalid input! Please enter a numeric integer value.` |
| **Negative / Zero Book ID** | `val > 0` validation check | `[Error] Book ID must be a positive integer greater than 0.` |
| **Duplicate Book ID** | Verified via `libraryService.isDuplicateId()` | `[Error] Book ID 101 already exists. Please enter a unique ID.` |
| **Empty Input** (Title, Author, Student) | Trimmed check `.isEmpty()` | `[Error] Input cannot be empty. Please enter text.` |
| **Issuing Non-existent Book** | Checked via `searchById()` | `[Error] Book with ID 999 does not exist.` |
| **Issuing Already Issued Book** | Checked via `book.isIssued()` | `[Error] Book 'Clean Code' is already issued to student: Rahul` |
| **Returning Available Book** | Checked via `!book.isIssued()` | `[Error] Book 'Clean Code' is currently available (was not issued).` |

---

## 🚀 Future Enhancements
- **Persistent Storage**: Save book data to JSON/CSV files or SQLite database.
- **Student Management**: Add student profiles, issued books history, and borrow limits.
- **Due Dates & Fines**: Implement issue dates, return deadlines, and overdue fee calculations.
- **Graphical User Interface (GUI)**: Modern interface built with JavaFX or Swing.
- **Role-Based Access**: Multi-user login (Admin vs. Student).

---

## 🎓 Internship Metadata
- **Program**: SkillVoro Java Development Internship
- **Task**: Task 3 - Library Management System
- **Author**: Deepak K C

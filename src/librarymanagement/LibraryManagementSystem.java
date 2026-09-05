package librarymanagement;

import java.util.List;
import java.util.Scanner;


public class LibraryManagementSystem {

    private final LibraryService libraryService;
    private final Scanner scanner;

    public LibraryManagementSystem() {
        this.libraryService = new LibraryService();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        LibraryManagementSystem app = new LibraryManagementSystem();
        app.run();
    }

    
    public void run() {
        boolean exit = false;
        while (!exit) {
            displayMenu();
            int choice = readMenuChoice(1, 7);

            switch (choice) {
                case 1:
                    handleAddBook();
                    break;
                case 2:
                    handleDisplayAvailableBooks();
                    break;
                case 3:
                    handleSearchBook();
                    break;
                case 4:
                    handleIssueBook();
                    break;
                case 5:
                    handleReturnBook();
                    break;
                case 6:
                    handleDisplayBookAvailability();
                    break;
                case 7:
                    handleExit();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select an option between 1 and 7.");
            }
            if (!exit) {
                System.out.println("\nPress ENTER to continue...");
                scanner.nextLine();
            }
        }
    }

    
    private void displayMenu() {
        System.out.println("\n========================================");
        System.out.println("       LIBRARY MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Add Book");
        System.out.println("2. Display All Books (Available)");
        System.out.println("3. Search Book");
        System.out.println("4. Issue Book");
        System.out.println("5. Return Book");
        System.out.println("6. Display Book Availability");
        System.out.println("7. Exit");
        System.out.println("========================================");
    }

    
    private void handleAddBook() {
        System.out.println("\n--- 1. ADD NEW BOOK ---");

        int bookId;
        while (true) {
            bookId = readValidPositiveInt("Enter Book ID: ");
            if (libraryService.isDuplicateId(bookId)) {
                System.out.println("[Error] Book ID " + bookId + " already exists. Please enter a unique ID.");
            } else {
                break;
            }
        }

        String title = readNonEmptyString("Enter Book Title: ");
        String author = readNonEmptyString("Enter Author Name: ");

        Book book = new Book(bookId, title, author);
        boolean success = libraryService.addBook(book);

        if (success) {
            System.out.println("\n[Success] Book added successfully!");
            System.out.println("ID     : " + book.getBookId());
            System.out.println("Title  : " + book.getTitle());
            System.out.println("Author : " + book.getAuthor());
        } else {
            System.out.println("\n[Error] Failed to add book. Duplicate Book ID detected.");
        }
    }

    private void handleDisplayAvailableBooks() {
        System.out.println("\n--- 2. DISPLAY ALL AVAILABLE BOOKS ---");
        List<Book> availableBooks = libraryService.getAvailableBooks();

        if (availableBooks.isEmpty()) {
            System.out.println("No available books currently in the library.");
            return;
        }

        printAvailableBooksTable(availableBooks);
    }

    private void handleSearchBook() {
        System.out.println("\n--- 3. SEARCH BOOK ---");
        System.out.println("1. Search by Book ID");
        System.out.println("2. Search by Book Title");

        int choice = readMenuChoice(1, 2);

        if (choice == 1) {
            int searchId = readValidPositiveInt("Enter Book ID to search: ");
            Book book = libraryService.searchById(searchId);

            if (book != null) {
                System.out.println("\n[Book Found]");
                printSingleBookDetails(book);
            } else {
                System.out.println("\n[Message] Book with ID " + searchId + " not found.");
            }
        } else {
            String keyword = readNonEmptyString("Enter Book Title or Keyword to search: ");
            List<Book> results = libraryService.searchByTitle(keyword);

            if (results.isEmpty()) {
                System.out.println("\n[Message] No books found matching title: '" + keyword + "'");
            } else {
                System.out.println("\n[Found " + results.size() + " book(s) matching '" + keyword + "']");
                printFullAvailabilityTable(results);
            }
        }
    }

    
    private void handleIssueBook() {
        System.out.println("\n--- 4. ISSUE BOOK ---");
        int bookId = readValidPositiveInt("Enter Book ID to issue: ");

        Book book = libraryService.searchById(bookId);
        if (book == null) {
            System.out.println("\n[Error] Book with ID " + bookId + " does not exist.");
            return;
        }

        if (book.isIssued()) {
            System.out.println("\n[Error] Book '" + book.getTitle() + "' is already issued to student: " + book.getIssuedTo());
            return;
        }

        String studentName = readNonEmptyString("Enter Student Name: ");
        LibraryService.IssueResult result = libraryService.issueBook(bookId, studentName);

        if (result.isSuccess()) {
            System.out.println("\n[Success] " + result.getMessage());
        } else {
            System.out.println("\n[Error] " + result.getMessage());
        }
    }

    
    private void handleReturnBook() {
        System.out.println("\n--- 5. RETURN BOOK ---");
        int bookId = readValidPositiveInt("Enter Book ID to return: ");

        LibraryService.ReturnResult result = libraryService.returnBook(bookId);

        if (result.isSuccess()) {
            System.out.println("\n[Success] " + result.getMessage());
        } else {
            System.out.println("\n[Error] " + result.getMessage());
        }
    }

    
    private void handleDisplayBookAvailability() {
        System.out.println("\n--- 6. DISPLAY BOOK AVAILABILITY ---");
        List<Book> allBooks = libraryService.getAllBooks();

        if (allBooks.isEmpty()) {
            System.out.println("No books recorded in the library yet.");
            return;
        }

        printFullAvailabilityTable(allBooks);
    }

    
    private void handleExit() {
        System.out.println("\nThank you for using the Library Management System. Goodbye!");
        scanner.close();
    }

    
    private void printAvailableBooksTable(List<Book> books) {
        String line = "-----------------------------------------------------------------------------";
        System.out.println(line);
        System.out.printf("%-10s %-32s %-30s%n", "ID", "TITLE", "AUTHOR");
        System.out.println(line);
        for (Book b : books) {
            System.out.printf("%-10d %-32s %-30s%n",
                    b.getBookId(),
                    truncate(b.getTitle(), 30),
                    truncate(b.getAuthor(), 28));
        }
        System.out.println(line);
    }

    private void printFullAvailabilityTable(List<Book> books) {
        String line = "----------------------------------------------------------------------------------------------------";
        System.out.println(line);
        System.out.printf("%-8s %-28s %-24s %-14s %-20s%n", "ID", "TITLE", "AUTHOR", "STATUS", "ISSUED TO");
        System.out.println(line);
        for (Book b : books) {
            String status = b.isIssued() ? "ISSUED" : "AVAILABLE";
            String student = b.isIssued() ? truncate(b.getIssuedTo(), 18) : "-";
            System.out.printf("%-8d %-28s %-24s %-14s %-20s%n",
                    b.getBookId(),
                    truncate(b.getTitle(), 26),
                    truncate(b.getAuthor(), 22),
                    status,
                    student);
        }
        System.out.println(line);
    }

    private void printSingleBookDetails(Book book) {
        System.out.println("----------------------------------------");
        System.out.println("Book ID     : " + book.getBookId());
        System.out.println("Title       : " + book.getTitle());
        System.out.println("Author      : " + book.getAuthor());
        System.out.println("Status      : " + (book.isIssued() ? "ISSUED" : "AVAILABLE"));
        if (book.isIssued()) {
            System.out.println("Issued To   : " + book.getIssuedTo());
        }
        System.out.println("----------------------------------------");
    }

    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }

    
    private int readMenuChoice(int min, int max) {
        while (true) {
            System.out.print("Enter choice (" + min + "-" + max + "): ");
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("[Error] Invalid selection. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("[Error] Invalid input! Please enter a valid number.");
            }
        }
    }

    
    private int readValidPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int val = Integer.parseInt(input);
                if (val > 0) {
                    return val;
                } else {
                    System.out.println("[Error] Book ID must be a positive integer greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[Error] Invalid input! Please enter a numeric integer value.");
            }
        }
    }

   
    private String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("[Error] Input cannot be empty. Please enter text.");
        }
    }
}

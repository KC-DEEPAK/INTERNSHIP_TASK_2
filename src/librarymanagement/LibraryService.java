package librarymanagement;

import java.util.ArrayList;
import java.util.List;

public class LibraryService {

    private final List<Book> books;

    public LibraryService() {
        this.books = new ArrayList<>();
    }

    public boolean isDuplicateId(int bookId) {
        return searchById(bookId) != null;
    }

    public boolean addBook(Book book) {
        if (isDuplicateId(book.getBookId())) {
            return false;
        }

        books.add(book);
        return true;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();

        for (Book book : books) {
            if (!book.isIssued()) {
                availableBooks.add(book);
            }
        }

        return availableBooks;
    }

    public Book searchById(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                return book;
            }
        }

        return null;
    }

    public List<Book> searchByTitle(String titleKeyword) {
        List<Book> matchingBooks = new ArrayList<>();

        String query = titleKeyword.trim().toLowerCase();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query)) {
                matchingBooks.add(book);
            }
        }

        return matchingBooks;
    }

    public IssueResult issueBook(int bookId, String studentName) {
        Book book = searchById(bookId);

        if (book == null) {
            return new IssueResult(
                    false,
                    "Book with ID " + bookId + " does not exist."
            );
        }

        if (book.isIssued()) {
            return new IssueResult(
                    false,
                    "Book '" + book.getTitle()
                            + "' is already issued to student: "
                            + book.getIssuedTo()
            );
        }

        book.issueTo(studentName);

        return new IssueResult(
                true,
                "Book issued successfully to student: " + studentName
        );
    }

    public ReturnResult returnBook(int bookId) {
        Book book = searchById(bookId);

        if (book == null) {
            return new ReturnResult(
                    false,
                    "Book with ID " + bookId + " does not exist."
            );
        }

        if (!book.isIssued()) {
            return new ReturnResult(
                    false,
                    "Book '" + book.getTitle()
                            + "' is currently available (was not issued)."
            );
        }

        book.returnBook();

        return new ReturnResult(
                true,
                "Book returned successfully."
        );
    }

    public static class IssueResult {

        private final boolean success;
        private final String message;

        public IssueResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class ReturnResult {

        private final boolean success;
        private final String message;

        public ReturnResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}
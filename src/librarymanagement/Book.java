package librarymanagement;
public class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean issued;
    private String issuedTo;

    
    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.issued = false;
        this.issuedTo = "";
    }

    
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isIssued() {
        return issued;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    
    public void issueTo(String studentName) {
        this.issued = true;
        this.issuedTo = studentName;
    }

    
    public void returnBook() {
        this.issued = false;
        this.issuedTo = "";
    }

    @Override
    public String toString() {
        return String.format("Book [ID=%d, Title='%s', Author='%s', Status=%s%s]",
                bookId, title, author,
                issued ? "ISSUED" : "AVAILABLE",
                issued ? ", IssuedTo='" + issuedTo + "'" : "");
    }
}

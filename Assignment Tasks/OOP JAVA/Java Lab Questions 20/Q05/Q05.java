/*
Question 5: Library Book Borrowing and Fine Management System
10 Marks
A university library manages two categories of books: Regular Books and Reference Books. Members can borrow books, but the borrowing rules differ.
Requirements:
Create an abstract class LibraryItem with private attributes itemId, title, and available.
Create two subclasses:
RegularBook: Can be borrowed for 14 days; late fine Tk. 5 per additional day.
ReferenceBook: Cannot be borrowed and must be used inside the library.
Define an abstract method calculateFine(int daysBorrowed).
Create a Library class maintaining the book collection and member borrowing records.
Implement:
Add books to the library.
Register library members.
Issue Regular Books to registered members.
Return books and calculate applicable fines.
Display currently borrowed books for a particular member.
Special condition: Throw an appropriate exception when a member attempts to borrow an unavailable or Reference Book.
*/





import java.util.ArrayList;

// Abstract parent class
abstract class LibraryItem {
    private int itemId;
    private String title;
    private boolean available;

    LibraryItem(int id, String title) {
        this.itemId = id;
        this.title = title;
        this.available = true;
    }

    abstract double calculateFine(int daysBorrowed);

    int getItemId() {
        return itemId;
    }

    String getTitle() {
        return title;
    }

    boolean isAvailable() {
        return available;
    }

    void setAvailable(boolean available) {
        this.available = available;
    }
}

// Regular book
class RegularBook extends LibraryItem {
    RegularBook(int id, String title) {
        super(id, title);
    }

    double calculateFine(int daysBorrowed) {
        if (daysBorrowed > 14) {
            return (daysBorrowed - 14) * 5;
        }

        return 0;
    }
}

// Reference book
class ReferenceBook extends LibraryItem {
    ReferenceBook(int id, String title) {
        super(id, title);
    }

    double calculateFine(int daysBorrowed) {
        return 0;
    }
}

// Borrowing record
class BorrowRecord {
    int memberId;
    LibraryItem book;

    BorrowRecord(int memberId, LibraryItem book) {
        this.memberId = memberId;
        this.book = book;
    }
}

// Library class
class Library {
    ArrayList<LibraryItem> books = new ArrayList<LibraryItem>();
    ArrayList<Integer> members = new ArrayList<Integer>();
    ArrayList<BorrowRecord> records = new ArrayList<BorrowRecord>();

    void addBook(LibraryItem book) {
        books.add(book);
        System.out.println(book.getTitle() + " added to library.");
    }

    void registerMember(int memberId) {
        members.add(memberId);
        System.out.println("Member " + memberId + " registered.");
    }

    void issueBook(int memberId, int itemId) {
        if (!members.contains(memberId)) {
            throw new IllegalStateException("Member is not registered.");
        }

        for (int i = 0; i < books.size(); i++) {
            LibraryItem book = books.get(i);

            if (book.getItemId() == itemId) {
                if (book instanceof ReferenceBook) {
                    throw new IllegalStateException("Reference books cannot be borrowed.");
                }

                if (!book.isAvailable()) {
                    throw new IllegalStateException("Book is not available.");
                }

                book.setAvailable(false);
                records.add(new BorrowRecord(memberId, book));

                System.out.println(book.getTitle() + " issued successfully.");
                return;
            }
        }

        System.out.println("Book not found.");
    }

    void returnBook(int memberId, int itemId, int daysBorrowed) {
        for (int i = 0; i < records.size(); i++) {
            BorrowRecord record = records.get(i);

            if (record.memberId == memberId &&
                record.book.getItemId() == itemId) {

                double fine = record.book.calculateFine(daysBorrowed);

                record.book.setAvailable(true);
                records.remove(i);

                System.out.println("Book returned: " + record.book.getTitle());
                System.out.println("Fine: Tk. " + fine);
                return;
            }
        }

        System.out.println("Borrowing record not found.");
    }

    void displayBorrowedBooks(int memberId) {
        System.out.println("\nBorrowed books of member " + memberId + ":");

        for (int i = 0; i < records.size(); i++) {
            BorrowRecord record = records.get(i);

            if (record.memberId == memberId) {
                System.out.println(record.book.getTitle());
            }
        }
    }
}

// Main class
public class Q05 {
    public static void main(String[] args) {
        Library library = new Library();

        library.addBook(new RegularBook(101, "Java Programming"));
        library.addBook(new RegularBook(102, "Data Structures"));
        library.addBook(new ReferenceBook(103, "Computer Science Encyclopedia"));

        library.registerMember(1);
        library.registerMember(2);

        library.issueBook(1, 101);
        library.issueBook(2, 102);

        library.displayBorrowedBooks(1);

        library.returnBook(1, 101, 20);
        library.returnBook(2, 102, 10);

        library.displayBorrowedBooks(1);
    }
}
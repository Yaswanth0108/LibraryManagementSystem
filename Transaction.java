import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Transaction {
    private int bookId;
    private String memberRollNumber;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private int fine;

    public Transaction(int bookId, String memberRollNumber, LocalDate issueDate) {
        this.bookId = bookId;
        this.memberRollNumber = memberRollNumber;
        this.issueDate = issueDate;
        this.dueDate = issueDate.plusDays(15);
        this.returnDate = null;
        this.fine = 0;
    }

    public int getBookId() { return bookId; }
    public String getMemberRollNumber() { return memberRollNumber; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public int getFine() { return fine; }

    public void returnBook(LocalDate returnDate) {
        this.returnDate = returnDate;
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        if (daysLate > 0) {
            fine = (int) daysLate;
        } else {
            fine = 0;
        }
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public String toDisplayString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Book ID: ").append(bookId).append(", Member: ").append(memberRollNumber).append("\n");
        sb.append("Issued on: ").append(issueDate).append(", Due on: ").append(dueDate).append("\n");
        if (isReturned()) {
            sb.append("Returned on: ").append(returnDate).append(", Fine: Rs ").append(fine).append("\n");
        } else {
            sb.append("Not returned yet\n");
        }
        sb.append("-----------------------------\n");
        return sb.toString();
    }
}

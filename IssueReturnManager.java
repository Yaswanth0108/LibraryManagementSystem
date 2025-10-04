
import java.time.LocalDate;
import java.util.*;

public class IssueReturnManager {
    private List<Transaction> transactions = new ArrayList<>();
    private BookManager bookManager;
    private MemberManager memberManager;

    public IssueReturnManager(BookManager bookManager, MemberManager memberManager) {
        this.bookManager = bookManager;
        this.memberManager = memberManager;
    }

    public String issueBook(String rollNumber, int bookId) {
        if (!bookManager.isBookAvailable(bookId)) {
            return "❌ Book ID " + bookId + " is not available.\n";
        }
        if (!memberManager.isMemberExists(rollNumber)) {
            return "❌ Member with roll number " + rollNumber + " not found.\n";
        }

        Transaction transaction = new Transaction(bookId, rollNumber, LocalDate.now());
        transactions.add(transaction);
        bookManager.setBookAvailability(bookId, false);

        return "✅ Book issued successfully:\n" + transaction.toDisplayString();
    }

    public String returnBook(int bookId) {
        for (Transaction t : transactions) {
            if (t.getBookId() == bookId && !t.isReturned()) {
                t.returnBook(LocalDate.now());
                bookManager.setBookAvailability(bookId, true);
                return "✅ Book returned successfully:\n" + t.toDisplayString();
            }
        }
        return "❌ No active issue found for Book ID " + bookId + "\n";
    }

    public String displayAllTransactions() {
        StringBuilder sb = new StringBuilder();
        sb.append("📖 All Transactions:\n");
        if (transactions.isEmpty()) {
            sb.append("No transactions recorded.\n");
        }
        for (Transaction t : transactions) {
            sb.append(t.toDisplayString());
        }
        return sb.toString();
    }

    public String searchTransactionsByMember(String rollNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append("🔍 Transactions for Member: ").append(rollNumber).append("\n");
        boolean found = false;
        for (Transaction t : transactions) {
            if (t.getMemberRollNumber().equalsIgnoreCase(rollNumber)) {
                sb.append(t.toDisplayString());
                found = true;
            }
        }
        if (!found) sb.append("No transactions found.\n");
        return sb.toString();
    }

    public String searchTransactionsByBookId(int bookId) {
        StringBuilder sb = new StringBuilder();
        sb.append("🔍 Transactions for Book ID: ").append(bookId).append("\n");
        boolean found = false;
        for (Transaction t : transactions) {
            if (t.getBookId() == bookId) {
                sb.append(t.toDisplayString());
                found = true;
            }
        }
        if (!found) sb.append("No transactions found.\n");
        return sb.toString();
    }
public List<Transaction> getTransactions() {
        return this.transactions;
    }
    
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}

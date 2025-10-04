import java.util.*;

public class BookManager {
    private List<Book> books = new ArrayList<>();
    private int nextId = 1;

    public String addBook(String title, String author, String genre) {
        Book newBook = new Book(nextId++, title, author, genre);
        books.add(newBook);
        return "✅ Book added:\n" + newBook.toDisplayString();
    }

    public String displayAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("📚 All Books:\n");
        if (books.isEmpty()) {
            sb.append("No books in the library.\n");
        }
        for (Book b : books) {
            sb.append(b.toDisplayString()).append("\n");
        }
        return sb.toString();
    }

    public String searchBooksByGenre(String genre) {
        StringBuilder sb = new StringBuilder();
        sb.append("🔍 Books in genre: ").append(genre).append("\n");
        boolean found = false;
        for (Book b : books) {
            if (b.getGenre().equalsIgnoreCase(genre)) {
                sb.append(b.toDisplayString()).append("\n");
                found = true;
            }
        }
        if (!found) sb.append("No books found.\n");
        return sb.toString();
    }

    public String searchBooksByTitle(String keyword) {
        StringBuilder sb = new StringBuilder();
        sb.append("🔍 Books matching title: ").append(keyword).append("\n");
        boolean found = false;
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                sb.append(b.toDisplayString()).append("\n");
                found = true;
            }
        }
        if (!found) sb.append("No books found.\n");
        return sb.toString();
    }

    public String deleteBook(int id) {
        Iterator<Book> it = books.iterator();
        while (it.hasNext()) {
            Book b = it.next();
            if (b.getId() == id) {
                it.remove();
                return "🗑️ Book deleted:\n" + b.toDisplayString();
            }
        }
        return "❌ Book with ID " + id + " not found.\n";
    }

    public boolean isBookAvailable(int bookId) {
        for (Book b : books) {
            if (b.getId() == bookId) return b.isAvailable();
        }
        return false;
    }

    public void setBookAvailability(int bookId, boolean availability) {
        for (Book b : books) {
            if (b.getId() == bookId) {
                b.setAvailable(availability);
                return;
            }
        }
    }
}

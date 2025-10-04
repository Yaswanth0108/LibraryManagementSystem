import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class BookManager {
    private List<Book> books = new ArrayList<>();
    private int nextId = 1;

    public String addBook(String title, String author, String genre) {
        Book newBook = new Book(nextId++, title, author, genre);
        books.add(newBook);
        return "✅ Book added:\n" + newBook.toDisplayString() + "\n";
    }

    public String displayAll() {
        StringBuilder sb = new StringBuilder("📖 All Books:\n");
        if (books.isEmpty()) {
            sb.append("No books in the library.\n");
        }
        for (Book b : books) {
            sb.append(b.toDisplayString()).append("\n");
        }
        return sb.toString();
    }

    public String searchBooksByGenre(String genre) {
        StringBuilder sb = new StringBuilder("🔍 Books in genre: ").append(genre).append("\n");
        boolean found = false;
        for (Book b : books) {
            if (b.getGenre().equalsIgnoreCase(genre)) {
                sb.append(b.toDisplayString()).append("\n");
                found = true;
            }
        }
        if (!found) {
            sb.append("No books found.\n");
        }
        return sb.toString();
    }

    public String searchBooksByTitle(String title) {
        StringBuilder sb = new StringBuilder("🔍 Books matching title: ").append(title).append("\n");
        boolean found = false;
        String lowerCaseTitle = title.toLowerCase();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(lowerCaseTitle)) {
                sb.append(b.toDisplayString()).append("\n");
                found = true;
            }
        }
        if (!found) {
            sb.append("No books found.\n");
        }
        return sb.toString();
    }

    public String deleteBook(int id) {
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book b = iterator.next();
            if (b.getId() == id) {
                iterator.remove();
                return "🗑️ Book deleted:\n" + b.toDisplayString() + "\n";
            }
        }
        return "❌ Book with ID " + id + " not found.\n";
    }

    public boolean isBookAvailable(int id) {
        for (Book b : books) {
            if (b.getId() == id) {
                return b.isAvailable();
            }
        }
        return false;
    }

    public void setBookAvailability(int id, boolean available) {
        for (Book b : books) {
            if (b.getId() == id) {
                b.setAvailable(available);
                return;
            }
        }
    }
    
    // Helper method to get a book by its ID
    public Book getBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }
public List<Book> getBooks() {
        return this.books;
    }
    
    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

import java.util.InputMismatchException;
import java.util.Scanner;

public class LibraryManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        int choice = 0;

        Library.books[Library.bookCount++] = new Book("The Alchemist", "Paulo Coelho", 208, 299.99);
        Library.books[Library.bookCount++] = new Book("To Kill a Mockingbird", "Harper Lee", 336, 399.50);
        Library.books[Library.bookCount++] = new Book("1984", "George Orwell", 328, 349.00);
        Library.books[Library.bookCount++] = new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 309, 499.99);
        Library.books[Library.bookCount++] = new Book("The Great Gatsby", "F. Scott Fitzgerald", 180, 279.75);
        Library.books[Library.bookCount++] = new Book("Pride and Prejudice", "Jane Austen", 432, 319.00);
        Library.books[Library.bookCount++] = new Book("The Catcher in the Rye", "J.D. Salinger", 277, 289.99);
        Library.books[Library.bookCount++] = new Book("Wings of Fire", "A.P.J. Abdul Kalam", 180, 250.00);
        Library.books[Library.bookCount++] = new Book("The Hobbit", "J.R.R. Tolkien", 310, 399.99);
        Library.books[Library.bookCount++] = new Book("Think and Grow Rich", "Napoleon Hill", 238, 199.00);

        System.out.println("*************************");
        System.out.println("LIBRARY MANAGEMENT SYSTEM");

        while (isRunning) {
            System.out.println("*************************");
            System.out.println("1. Add Book");
            System.out.println("2. Issue Book (Coming Soon)");
            System.out.println("3. Return Book (Coming Soon)");
            System.out.println("4. Display all Books");
            System.out.println("5. Exit");
            System.out.println("*************************");

            try {
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer!");
                sc.nextLine();
            }
                switch (choice) {
                    case 1:
                        Library.addBook();
                        break;
                    case 2:
                        System.out.println("Issue book feature is not implemented yet.");
                        break;
                    case 3:
                        System.out.println("Return book feature is not implemented yet.");
                        break;
                    case 4:
                        Library.displayBooks();
                        break;
                    case 5:
                        isRunning = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
        }
    }
}

class Library {
static Book[] books = new Book[100];
static int bookCount = 0;
static Scanner sc = new Scanner(System.in);

static void addBook() {
    System.out.println("Enter Book Details:");

    System.out.print("Title: ");
    String name = sc.nextLine();

    System.out.print("Author: ");
    String author = sc.nextLine();

    System.out.print("Pages: ");
    int pages = sc.nextInt();

    System.out.print("Price: ");
    double price = sc.nextDouble();
    sc.nextLine();

    books[bookCount] = new Book(name, author, pages, price);
    bookCount++;
    System.out.println("Book added successfully!\n");
}

static void displayBooks() {
    if (bookCount == 0) {
        System.out.println("No books in the library yet.");
        return;
    }

    for (int i = 0; i < bookCount; i++) {
        books[i].display(i + 1);
    }
}
}

class Book {
    String name;
    String author;
    int pages;
    double price;

    Book(String name, String author, int pages, double price) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.price = price;
    }

    public void display(int index) {
        System.out.println("Book " + index + " : Title: " + name +
                ", Author: " + author +
                ", Pages: " + pages +
                ", Price: Rs." + price);
    }
}

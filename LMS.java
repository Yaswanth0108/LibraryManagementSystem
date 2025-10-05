import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.io.Serializable;
import java.util.List;

public class LMS extends JFrame implements Serializable {
    private BookManager bookManager = new BookManager();
    private MemberManager memberManager = new MemberManager();
    private IssueReturnManager issueReturnManager = new IssueReturnManager(bookManager, memberManager);

    private JTextArea outputArea;

    private void loadInitialData() {
        // Method to load data from files
        bookManager.setBooks(DataManager.loadData("D:\\LibraryData\\books.ser"));
        memberManager.setMembers(DataManager.loadData("D:\\LibraryData\\members.ser"));
        issueReturnManager.setTransactions(DataManager.loadData("D:\\LibraryData\\transactions.ser"));
    }

    private void saveAllData() {
        // Method to save data to files
        DataManager.saveData(bookManager.getBooks(), "D:\\LibraryData\\books.ser");
        DataManager.saveData(memberManager.getMembers(), "D:\\LibraryData\\members.ser");
        DataManager.saveData(issueReturnManager.getTransactions(), "D:\\LibraryData\\transactions.ser");
    }

    public LMS() {
        // Call loadInitialData() at the beginning of the constructor
        loadInitialData();

        // Add a window listener to save data when the application is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveAllData();
            }
        });

        setTitle("Library Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add("Books", createBookPanel());
        tabbedPane.add("Members", createMemberPanel());
        tabbedPane.add("Issue/Return", createIssueReturnPanel());
        tabbedPane.add("Transactions", createTransactionPanel());

        outputArea = new JTextArea(15, 80);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Layout: Tabs on top, output area below
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Main panel to hold all input and button panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Panel for adding a new book
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JTextField titleField = new JTextField(15);
        JTextField authorField = new JTextField(15);
        JTextField genreField = new JTextField(15);
        JButton addBookBtn = new JButton("Add Book");

        addPanel.add(new JLabel("Title:"));
        addPanel.add(titleField);
        addPanel.add(new JLabel("Author:"));
        addPanel.add(authorField);
        addPanel.add(new JLabel("Genre:"));
        addPanel.add(genreField);
        addPanel.add(addBookBtn);

        // Panel for displaying all books
        JPanel displayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton displayAllBtn = new JButton("Display All Books");
        displayPanel.add(displayAllBtn);

        // Panel for searching books
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JTextField searchField = new JTextField(20);
        JButton searchGenreBtn = new JButton("Search by Genre");
        JButton searchTitleBtn = new JButton("Search by Title");

        searchPanel.add(new JLabel("Search by Genre or Title:"));
        searchPanel.add(searchField);
        searchPanel.add(searchGenreBtn);
        searchPanel.add(searchTitleBtn);

        // Panel for deleting a book
        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JTextField deleteIdField = new JTextField(10);
        JButton deleteBtn = new JButton("Delete Book");

        deletePanel.add(new JLabel("Delete by Book ID:"));
        deletePanel.add(deleteIdField);
        deletePanel.add(deleteBtn);

        // Add all sub-panels to the main panel
        mainPanel.add(addPanel);
        mainPanel.add(displayPanel);
        mainPanel.add(searchPanel);
        mainPanel.add(deletePanel);

        panel.add(mainPanel, BorderLayout.NORTH);

        // Button actions
        addBookBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String genre = genreField.getText().trim();
            if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
                showMessage("Please fill all book fields.");
                return;
            }
            String res = bookManager.addBook(title, author, genre);
            outputArea.setText(res);
            clearFields(titleField, authorField, genreField);
        });

        displayAllBtn.addActionListener(e -> {
            outputArea.setText(bookManager.displayAll());
        });

        searchGenreBtn.addActionListener(e -> {
            String genre = searchField.getText().trim();
            if (genre.isEmpty()) {
                showMessage("Please enter a genre to search.");
                return;
            }
            outputArea.setText(bookManager.searchBooksByGenre(genre));
        });

        searchTitleBtn.addActionListener(e -> {
            String title = searchField.getText().trim();
            if (title.isEmpty()) {
                showMessage("Please enter a title keyword to search.");
                return;
            }
            outputArea.setText(bookManager.searchBooksByTitle(title));
        });

        deleteBtn.addActionListener(e -> {
            String idText = deleteIdField.getText().trim();
            if (idText.isEmpty()) {
                showMessage("Please enter a Book ID to delete.");
                return;
            }
            try {
                int id = Integer.parseInt(idText);
                outputArea.setText(bookManager.deleteBook(id));
                deleteIdField.setText("");
            } catch (NumberFormatException ex) {
                showMessage("Book ID must be a number.");
            }
        });
        return panel;
    }

    private JPanel createMemberPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Main panel to hold input and buttons, using BoxLayout for vertical stacking
        JPanel mainInputPanel = new JPanel();
        mainInputPanel.setLayout(new BoxLayout(mainInputPanel, BoxLayout.Y_AXIS));

        // Panel for adding new members
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField rollField = new JTextField(10);
        JTextField nameField = new JTextField(15);
        addPanel.add(new JLabel("Roll Number:"));
        addPanel.add(rollField);
        addPanel.add(new JLabel("Name:"));
        addPanel.add(nameField);
        JButton addMemberBtn = new JButton("Add Member");
        addPanel.add(addMemberBtn);

        // Panel for displaying and searching members
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchRollField = new JTextField(15);
        JButton searchBtn = new JButton("Search Member");
        searchPanel.add(new JLabel("Search by Roll Number:"));
        searchPanel.add(searchRollField);
        searchPanel.add(searchBtn);
        JButton displayAllBtn = new JButton("Display All Members");
        searchPanel.add(displayAllBtn);

        // Panel for updating members
        JPanel updatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField updateRollField = new JTextField(10);
        JTextField updateNameField = new JTextField(15);
        updatePanel.add(new JLabel("Update Name for Roll Number:"));
        updatePanel.add(updateRollField);
        updatePanel.add(new JLabel("New Name:"));
        updatePanel.add(updateNameField);
        JButton updateBtn = new JButton("Update Member");
        updatePanel.add(updateBtn);
        
        // Panel for deleting members
        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField deleteRollField = new JTextField(10);
        deletePanel.add(new JLabel("Delete by Roll Number:"));
        deletePanel.add(deleteRollField);
        JButton deleteBtn = new JButton("Delete Member");
        deletePanel.add(deleteBtn);

        mainInputPanel.add(addPanel);
        mainInputPanel.add(searchPanel);
        mainInputPanel.add(updatePanel);
        mainInputPanel.add(deletePanel);

        panel.add(mainInputPanel, BorderLayout.NORTH);

        // Button actions
        addMemberBtn.addActionListener(e -> {
            String roll = rollField.getText().trim();
            String name = nameField.getText().trim();
            if (roll.isEmpty() || name.isEmpty()) {
                showMessage("Please fill all member fields.");
                return;
            }
            String res = memberManager.addMember(roll, name);
            outputArea.setText(res);
            clearFields(rollField, nameField);
        });

        displayAllBtn.addActionListener(e -> {
            outputArea.setText(memberManager.displayAll());
        });

        searchBtn.addActionListener(e -> {
            String roll = searchRollField.getText().trim();
            if (roll.isEmpty()) {
                showMessage("Please enter roll number to search.");
                return;
            }
            outputArea.setText(memberManager.searchByRollNumber(roll));
        });

        updateBtn.addActionListener(e -> {
            String roll = updateRollField.getText().trim();
            String newName = updateNameField.getText().trim();
            if (roll.isEmpty() || newName.isEmpty()) {
                showMessage("Please enter roll number and new name.");
                return;
            }
            outputArea.setText(memberManager.updateMember(roll, newName));
            clearFields(updateRollField, updateNameField);
        });

        deleteBtn.addActionListener(e -> {
            String roll = deleteRollField.getText().trim();
            if (roll.isEmpty()) {
                showMessage("Please enter roll number to delete.");
                return;
            }
            outputArea.setText(memberManager.deleteMember(roll));
            deleteRollField.setText("");
        });
        
        return panel;
    }

    private JPanel createIssueReturnPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Panel for issuing a book
        JPanel issuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JTextField issueRollField = new JTextField(15);
        JTextField issueBookIdField = new JTextField(15);
        JButton issueBtn = new JButton("Issue Book");
        
        issuePanel.add(new JLabel("Issue Book - Member Roll Number:"));
        issuePanel.add(issueRollField);
        issuePanel.add(new JLabel("Issue Book - Book ID:"));
        issuePanel.add(issueBookIdField);
        issuePanel.add(issueBtn);
        
        // Panel for returning a book
        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JTextField returnBookIdField = new JTextField(15);
        JButton returnBtn = new JButton("Return Book");

        returnPanel.add(new JLabel("Return Book - Book ID:"));
        returnPanel.add(returnBookIdField);
        returnPanel.add(returnBtn);

        mainPanel.add(issuePanel);
        mainPanel.add(returnPanel);
        
        panel.add(mainPanel, BorderLayout.NORTH);

        // Button actions
        issueBtn.addActionListener(e -> {
            String roll = issueRollField.getText().trim();
            String bookIdStr = issueBookIdField.getText().trim();
            if (roll.isEmpty() || bookIdStr.isEmpty()) {
                showMessage("Please fill roll number and book ID to issue.");
                return;
            }
            try {
                int bookId = Integer.parseInt(bookIdStr);
                String res = issueReturnManager.issueBook(roll, bookId);
                outputArea.setText(res);
                clearFields(issueRollField, issueBookIdField);
            } catch (NumberFormatException ex) {
                showMessage("Book ID must be a number.");
            }
        });

        returnBtn.addActionListener(e -> {
            String bookIdStr = returnBookIdField.getText().trim();
            if (bookIdStr.isEmpty()) {
                showMessage("Please enter book ID to return.");
                return;
            }
            try {
                int bookId = Integer.parseInt(bookIdStr);
                String res = issueReturnManager.returnBook(bookId);
                outputArea.setText(res);
                returnBookIdField.setText("");
            } catch (NumberFormatException ex) {
                showMessage("Book ID must be a number.");
            }
        });

        return panel;
    }

    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Main panel to stack the sub-panels vertically
        JPanel mainInputPanel = new JPanel();
        mainInputPanel.setLayout(new BoxLayout(mainInputPanel, BoxLayout.Y_AXIS));

        // Panel for "Display All Transactions" button
        JPanel displayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton displayAllBtn = new JButton("Display All Transactions");
        displayPanel.add(displayAllBtn);

        // Panel for searching by member roll number
        JPanel searchMemberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JTextField searchMemberField = new JTextField(15);
        JButton searchMemberBtn = new JButton("Search by Member Roll Number");
        searchMemberPanel.add(new JLabel("Member Roll Number:"));
        searchMemberPanel.add(searchMemberField);
        searchMemberPanel.add(searchMemberBtn);

        // Panel for searching by book ID
        JPanel searchBookPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JTextField searchBookIdField = new JTextField(15);
        JButton searchBookBtn = new JButton("Search by Book ID");
        searchBookPanel.add(new JLabel("Book ID:"));
        searchBookPanel.add(searchBookIdField);
        searchBookPanel.add(searchBookBtn);

        // Add all sub-panels to the main input panel
        mainInputPanel.add(displayPanel);
        mainInputPanel.add(searchMemberPanel);
        mainInputPanel.add(searchBookPanel);
        
        panel.add(mainInputPanel, BorderLayout.NORTH);

        // Button actions
        displayAllBtn.addActionListener(e -> {
            outputArea.setText(issueReturnManager.displayAllTransactions());
        });

        searchMemberBtn.addActionListener(e -> {
            String roll = searchMemberField.getText().trim();
            if (roll.isEmpty()) {
                showMessage("Please enter member roll number.");
                return;
            }
            outputArea.setText(issueReturnManager.searchTransactionsByMember(roll));
        });

        searchBookBtn.addActionListener(e -> {
            String bookIdStr = searchBookIdField.getText().trim();
            if (bookIdStr.isEmpty()) {
                showMessage("Please enter book ID.");
                return;
            }
            try {
                int bookId = Integer.parseInt(bookIdStr);
                outputArea.setText(issueReturnManager.searchTransactionsByBookId(bookId));
            } catch (NumberFormatException ex) {
                showMessage("Book ID must be a number.");
            }
        });

        return panel;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void clearFields(JTextField... fields) {
        for (JTextField f : fields) f.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LMS();
        });
    }
}

import javax.swing.*;
import java.awt.*;

public class LMS extends JFrame {
    private BookManager bookManager = new BookManager();
    private MemberManager memberManager = new MemberManager();
    private IssueReturnManager issueReturnManager = new IssueReturnManager(bookManager, memberManager);

    private JTextArea outputArea;

    public LMS() {
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

        // Inputs
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField searchField = new JTextField();
        JTextField deleteIdField = new JTextField();

        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("Genre:"));
        inputPanel.add(genreField);

        JButton addBookBtn = new JButton("Add Book");
        inputPanel.add(addBookBtn);

        JButton displayAllBtn = new JButton("Display All Books");
        inputPanel.add(displayAllBtn);

        inputPanel.add(new JLabel("Search by Genre or Title:"));
        inputPanel.add(searchField);

        JButton searchGenreBtn = new JButton("Search by Genre");
        JButton searchTitleBtn = new JButton("Search by Title");
        inputPanel.add(searchGenreBtn);
        inputPanel.add(searchTitleBtn);

        inputPanel.add(new JLabel("Delete by Book ID:"));
        inputPanel.add(deleteIdField);
        JButton deleteBtn = new JButton("Delete Book");
        inputPanel.add(deleteBtn);

        panel.add(inputPanel, BorderLayout.NORTH);

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

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        JTextField rollField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField searchRollField = new JTextField();
        JTextField updateNameField = new JTextField();
        JTextField deleteRollField = new JTextField();

        inputPanel.add(new JLabel("Roll Number:"));
        inputPanel.add(rollField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);

        JButton addMemberBtn = new JButton("Add Member");
        inputPanel.add(addMemberBtn);

        JButton displayAllBtn = new JButton("Display All Members");
        inputPanel.add(displayAllBtn);

        inputPanel.add(new JLabel("Search by Roll Number:"));
        inputPanel.add(searchRollField);

        JButton searchBtn = new JButton("Search Member");
        inputPanel.add(searchBtn);

        inputPanel.add(new JLabel("Update Name for Roll Number:"));
        inputPanel.add(rollField);

        inputPanel.add(new JLabel("New Name:"));
        inputPanel.add(updateNameField);

        JButton updateBtn = new JButton("Update Member");
        inputPanel.add(updateBtn);

        inputPanel.add(new JLabel("Delete by Roll Number:"));
        inputPanel.add(deleteRollField);

        JButton deleteBtn = new JButton("Delete Member");
        inputPanel.add(deleteBtn);

        panel.add(inputPanel, BorderLayout.NORTH);

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
            String roll = rollField.getText().trim();
            String newName = updateNameField.getText().trim();
            if (roll.isEmpty() || newName.isEmpty()) {
                showMessage("Please enter roll number and new name.");
                return;
            }
            outputArea.setText(memberManager.updateMember(roll, newName));
            clearFields(rollField, updateNameField);
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
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JTextField issueRollField = new JTextField();
        JTextField issueBookIdField = new JTextField();

        JTextField returnBookIdField = new JTextField();

        JButton issueBtn = new JButton("Issue Book");
        JButton returnBtn = new JButton("Return Book");

        panel.add(new JLabel("Issue Book - Member Roll Number:"));
        panel.add(issueRollField);
        panel.add(new JLabel("Issue Book - Book ID:"));
        panel.add(issueBookIdField);
        panel.add(issueBtn);

        panel.add(new JLabel("Return Book - Book ID:"));
        panel.add(returnBookIdField);
        panel.add(returnBtn);

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
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JButton displayAllBtn = new JButton("Display All Transactions");

        JTextField searchMemberField = new JTextField();
        JButton searchMemberBtn = new JButton("Search by Member Roll Number");

        JTextField searchBookIdField = new JTextField();
        JButton searchBookBtn = new JButton("Search by Book ID");

        panel.add(displayAllBtn);
        panel.add(new JLabel());

        panel.add(new JLabel("Member Roll Number:"));
        panel.add(searchMemberField);
        panel.add(searchMemberBtn);

        panel.add(new JLabel("Book ID:"));
        panel.add(searchBookIdField);
        panel.add(searchBookBtn);

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
                showMessage("Book ID Must be a number.");
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
}

package librarysystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import business.Author;
import business.Book;
import dataaccess.DataAccessFacade;

public class AddBookWindow extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//public static final AddBookWindow INSTANCE = new AddBookWindow();
	JTextField txtIsbn, txtTitle;
	JButton btnAddBook, btnAddAuthor, btnRemoveAuthor, btnBack;
	ButtonGroup lendDaysGroup;
	JRadioButton rb21, rb7;
	DefaultTableModel authorTableModel;
	JTable authorTable;
	//DefaultListModel<String> authorListModel;
	ArrayList<Author> authors = new ArrayList<Author>();
	AdminDashboardWindow dashboard;

	public void addAuthor(Author a) {
		authors.add(a);
		String[] rowData = { a.getFirstName(), a.getLastName(), a.getAddress().getCity(), a.getCredit() };
		authorTableModel.addRow(rowData);
	}

	public ArrayList<Author> getAuthor() {
		return authors;
	}

	public AddBookWindow(AdminDashboardWindow dashboard) {
		// TODO Auto-generated constructor stub
		this.dashboard=dashboard;

		setBounds(0, 0, 600, 600);
		setTitle("Add Book");
		setLayout(null);
		setVisible(true);
		Util.centerFrameOnDesktop(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Two panels
		JPanel p1 = new JPanel();
		p1.setBounds(0, 0, 600, 600);
		p1.setLayout(null);
		add(p1);

		// labeling
		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setBounds(40, 40, 100, 20);

		p1.add(lblIsbn);

		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(40, 80, 100, 20);
		p1.add(lblTitle);

		JLabel lblCheckoutLenght = new JLabel("Check Out Length");
		lblCheckoutLenght.setBounds(40, 120, 120, 20);
		p1.add(lblCheckoutLenght);

		JLabel lblAuthors = new JLabel("Author");
		lblAuthors.setBounds(40, 200, 100, 20);
		p1.add(lblAuthors);

		// left side text field
        txtIsbn = new JFormattedTextField(Util.IsbnFormatter());
		txtIsbn.setBounds(180, 40, 100, 25);
		txtIsbn.setText("12-3456789");
		txtIsbn.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		p1.add(txtIsbn);

		txtTitle = new JTextField();
		txtTitle.setBounds(180, 80, 300, 25);
		txtTitle.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		txtTitle.setText("Java21");
		p1.add(txtTitle);

		rb21 = new JRadioButton("21 Days");
		rb21.setBounds(200, 120, 80, 25);
		rb21.setActionCommand("21");

		p1.add(rb21);

		rb7 = new JRadioButton("7 Days");
		rb7.setBounds(320, 120, 80, 25);
		rb7.setActionCommand("7");
		p1.add(rb7);

		lendDaysGroup = new ButtonGroup();
		lendDaysGroup.add(rb21);
		lendDaysGroup.add(rb7);
		rb21.setSelected(true);

		authorTableModel = new DefaultTableModel();
		authorTableModel.addColumn("First Name");
		authorTableModel.addColumn("Last Name");
		authorTableModel.addColumn("State");
		authorTableModel.addColumn("Credentials");

		authorTable = new JTable(authorTableModel);
		authorTable.setBounds(180, 160, 360, 200);
		authorTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Adjust column width automatically
		authorTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
		JScrollPane scrollPane = new JScrollPane(authorTable);
		scrollPane.setBounds(180, 160, 360, 200);
		p1.add(scrollPane);

		JLabel lblAddRemoveAuthor = new JLabel("Add/Remove Authors");
		lblAddRemoveAuthor.setBounds(40, 380, 120, 30);
		p1.add(lblAddRemoveAuthor);

		btnAddAuthor = Util.buttonStyle(new JButton("+"));
		btnAddAuthor.setFont(new Font("Sans Serif", Font.BOLD, 20));
		btnAddAuthor.setBounds(180, 380, 50, 30);
		btnAddAuthor.setForeground(Color.green);
		p1.add(btnAddAuthor);
		btnAddAuthor.addActionListener(this);

		btnRemoveAuthor = Util.buttonStyle(new JButton("-"));
		btnRemoveAuthor.setFont(new Font("Sans Serif", Font.BOLD, 20));
		btnRemoveAuthor.setForeground(Color.red);
		btnRemoveAuthor.setBounds(240, 380, 50, 30);
		p1.add(btnRemoveAuthor);
		btnRemoveAuthor.addActionListener(this);

		btnAddBook = Util.newbuttonStyle(new JButton("Add Book"));
		btnAddBook.setBounds(250, 450, 120, 30);
		p1.add(btnAddBook);
		btnAddBook.addActionListener(this);
		
		
		btnBack = Util.newbuttonStyle(new JButton("<<Main Menu"));
		btnBack.setBounds(40, 450, 150, 30);
		p1.add(btnBack);
		btnBack.addActionListener(this);

	}

	public void removeAuthor(int rowIndex) {
		if (rowIndex >= 0 && rowIndex < authorTableModel.getRowCount()) {
			authorTableModel.removeRow(rowIndex);
			authors.remove(rowIndex);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		String isbn = txtIsbn.getText();
		String title = txtTitle.getText();
		String maxlen = lendDaysGroup.getSelection().getActionCommand();
		int len = Integer.parseInt(maxlen);

        if (ae.getSource() == btnBack) {
            dashboard.setVisible(true);
            setVisible(false);
            dispose();
        }else {
            // check empty fields
            if (isbn.isEmpty() || title.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All Fields are Required");
                return;
            }

            // validation for isbn
            if (!isbn.matches("\\d{2}-\\d{7}")) {
                JOptionPane.showMessageDialog(null, "Please enter ISBN number in format XX-XXXXXXX.");
                return; // Return without proceeding if telephone number format is incorrect
            }

            if (ae.getSource() == btnAddAuthor) {
                new AddAuthorWindow(this);
                setVisible(false);
            }
            if (ae.getSource() == btnRemoveAuthor) {
                int selectedRowIndex = authorTable.getSelectedRow();
                if (selectedRowIndex != -1) {
                    removeAuthor(selectedRowIndex);
                } else {
                    // No row is selected, handle this case as needed
                }
            }


            if (ae.getSource() == btnAddBook) {
                Book book = new Book(isbn, title, len, authors);
                DataAccessFacade daf = new DataAccessFacade();
                HashMap<String, Book> map = daf.readBooksMap();
                if (authors.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Authors should not be empty");
                    return;
                }
                if (map.containsKey(isbn)) {
                    JOptionPane.showMessageDialog(null, "Book already present in Library Management System");
                } else {
                    daf.saveNewBook(book);
                    JOptionPane.showMessageDialog(null, "Book Added Successfully");
                }

            }
        }

	}

}

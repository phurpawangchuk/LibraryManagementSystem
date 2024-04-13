
package librarysystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableModel;

import business.Book;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.User;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class AdminDashboardWindow extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String role;
	JButton btnAddMember, btnAddBook, btnBookCopy, btnCheckOutRecords, btnViewBook, btnViewMember, btnViewUser,
			btnLogout;
	DefaultTableModel dashRecordTableModel;
	JTable dashRecordTable;
	JPanel p1, p2;
	
	ControllerInterface ci = new SystemController();

	AdminDashboardWindow(String role) {
		this.role = role;
		setBounds(0, 0, 730, 600);
		setTitle("Main Dashboard");
		setLayout(null);
		setVisible(true);
		setBackground(Color.gray);
		Util.centerFrameOnDesktop(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Two panels
		p1 = new JPanel();
		p1.setBounds(0, 0, 700, 150);
		p1.setLayout(null);
		add(p1);
		
		p2 = new JPanel();
		p2.setBounds(0, 150, 700, 450);
		p2.setLayout(null);
		add(p2);


		// Dashboardname
		JLabel lblDashboard = new JLabel("Welcome to MIU Library System");
		lblDashboard.setFont(new Font("SAN SERIF", Font.BOLD, 25));
		lblDashboard.setBounds(20, 20, 600, 30);
		p1.add(lblDashboard);

		JLabel lblUser = new JLabel("Welcome "+role + " User");
		lblUser.setFont(new Font("SAN SERIF", Font.BOLD, 12));
		lblUser.setBounds(550, 55, 270, 15);
		p1.add(lblUser);

		// Buttons
		// Add Memeber
		btnAddMember = new JButton("Add Member");
		btnAddMember.setBounds(20, 80, 150, 30);
		Util.newbuttonStyle(btnAddMember);
		btnAddMember.setEnabled(false);
		btnAddMember.addActionListener(this);
		p1.add(btnAddMember);

		// Add book
		btnAddBook = new JButton("Add Book");
		btnAddBook.setBounds(175, 80, 150, 30);
		Util.newbuttonStyle(btnAddBook);
		btnAddBook.setEnabled(false);
		btnAddBook.addActionListener(this);
		p1.add(btnAddBook);

		// Add Book Copy
		btnBookCopy = new JButton("Copy Book");
		btnBookCopy.setBounds(330, 80, 150, 30);
		Util.newbuttonStyle(btnBookCopy);
		btnBookCopy.setEnabled(false);
		btnBookCopy.addActionListener(this);
		p1.add(btnBookCopy);

		// Checkout records
		btnCheckOutRecords = new JButton("Check Out Records");
		btnCheckOutRecords.setBounds(485, 80, 200, 30);
		Util.newbuttonStyle(btnCheckOutRecords);
		btnCheckOutRecords.setEnabled(false);
		btnCheckOutRecords.addActionListener(this);
		p1.add(btnCheckOutRecords);

		// Add Memeber
		btnViewMember = new JButton("View Members");
		btnViewMember.setBounds(20, 115, 150, 30);
		Util.newbuttonStyle(btnViewMember);
		btnViewMember.setEnabled(false);
		btnViewMember.addActionListener(this);
		p1.add(btnViewMember);

		// Add book
		btnViewBook = new JButton("View Books");
		btnViewBook.setBounds(175, 115, 150, 30);
		Util.newbuttonStyle(btnViewBook);
		btnViewBook.setEnabled(false);
		btnViewBook.addActionListener(this);
		p1.add(btnViewBook);

		// Add Book Copy
		btnViewUser = new JButton("View Users");
		btnViewUser.setBounds(330, 115, 150, 30);
		Util.newbuttonStyle(btnViewUser);
		btnViewUser.setEnabled(false);
		btnViewUser.addActionListener(this);
		p1.add(btnViewUser);

		// logout records
		btnLogout = new JButton("Log Out");
		btnLogout.setBounds(485, 115, 200, 30);
		btnLogout.setBackground(new Color(255, 79, 48));
		btnLogout.setForeground(Color.white);
		btnLogout.setFont(new Font("SAN SERIF", Font.BOLD, 18));
		btnLogout.setBorder(BorderFactory.createEmptyBorder());
        btnLogout.setUI(new BasicButtonUI());
		btnLogout.addActionListener(this);
		p1.add(btnLogout);

		// grouping buttons
		switch (role) {
		case "LIBRARIAN":
			btnCheckOutRecords.setEnabled(true);
			btnViewBook.setEnabled(true);
			btnViewMember.setEnabled(true);
			break;
		case "ADMIN":

			btnAddMember.setEnabled(true);
			btnAddBook.setEnabled(true);
			btnBookCopy.setEnabled(true);
            btnViewMember.setEnabled(true);
            btnViewBook.setEnabled(true);
            btnViewUser.setEnabled(true);

			break;
		case "BOTH":
			btnCheckOutRecords.setEnabled(true);
			btnViewBook.setEnabled(true);
			btnViewMember.setEnabled(true);
			btnAddMember.setEnabled(true);
			btnAddBook.setEnabled(true);
			btnViewUser.setEnabled(true);
			btnBookCopy.setEnabled(true);
			break;
		default:
			break;
		}
	}

	public void displayUser() {
        p2.removeAll();
        dashRecordTableModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			// Override the isCellEditable method to make all cells uneditable
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		dashRecordTableModel.addColumn("ID");
		dashRecordTableModel.addColumn("Role");

		dashRecordTable = new JTable(dashRecordTableModel);
		dashRecordTable.setBounds(20, 20, 670, 350);
		dashRecordTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Adjust column width automatically
		dashRecordTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
		JScrollPane scrollPane = new JScrollPane(dashRecordTable);
		scrollPane.setBounds(20, 20, 670, 350);
		p2.add(scrollPane);
		HashMap<String, User> userMap = ci.getAllUsers();
		for (Map.Entry<String, User> mapentry : userMap.entrySet()) {
			String[] rowData = { mapentry.getValue().getId(), mapentry.getValue().getAuthorization().toString() };
			dashRecordTableModel.addRow(rowData);

		}

	}//end of user display
	
	public void displayMember() {
        p2.removeAll();
		dashRecordTableModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			// Override the isCellEditable method to make all cells uneditable
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		dashRecordTableModel.addColumn("Member Id");
		dashRecordTableModel.addColumn("First Name");
		dashRecordTableModel.addColumn("Last Name");
		dashRecordTableModel.addColumn("Telephone No.");
	
		dashRecordTable = new JTable(dashRecordTableModel);
		dashRecordTable.setBounds(20, 20, 670, 350);
		dashRecordTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Adjust column width automatically
		dashRecordTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
		JScrollPane scrollPane = new JScrollPane(dashRecordTable);
		scrollPane.setBounds(20, 20, 670, 350);
		p2.add(scrollPane);
		HashMap<String, LibraryMember> memberMap = ci.getAllMembers();
		for (Map.Entry<String, LibraryMember> mapentry : memberMap.entrySet()) {
			String[] rowData = { mapentry.getValue().getMemberId(), mapentry.getValue().getFirstName(), mapentry.getValue().getLastName(),
					mapentry.getValue().getTelephone() };
			dashRecordTableModel.addRow(rowData);

		}

	}//end of view Members
	
	public void displayBooks() {
        p2.removeAll();
		dashRecordTableModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			// Override the isCellEditable method to make all cells uneditable
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		dashRecordTableModel.addColumn("ISBN No.");
		dashRecordTableModel.addColumn("Book Title");
		dashRecordTableModel.addColumn("Copies");
		dashRecordTableModel.addColumn("Maximum Checkout Days");
        dashRecordTableModel.addColumn("NoOfCopy");


        dashRecordTable = new JTable(dashRecordTableModel);
		dashRecordTable.setBounds(20, 20, 670, 350);
		dashRecordTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Adjust column width automatically
		dashRecordTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
		JScrollPane scrollPane = new JScrollPane(dashRecordTable);
		scrollPane.setBounds(20, 20, 670, 350);
		p2.add(scrollPane);
		HashMap<String, Book> memberMap = ci.getAllBooks();
		for (Map.Entry<String, Book> mapentry : memberMap.entrySet()) {
			String[] rowData = { mapentry.getValue().getIsbn(), mapentry.getValue().getTitle(), ""+ mapentry.getValue().getCopyNums(),
                  	""+mapentry.getValue().getMaxCheckoutLength(),""+mapentry.getValue().getNumCopies()};
			dashRecordTableModel.addRow(rowData);

		}

	}//end of View Books
	

	@Override
	public void actionPerformed(ActionEvent ae) {

		try {
			if (ae.getSource() == btnAddMember) {
				new AddMemberWindow(this);
			} else if (ae.getSource() == btnAddBook) {
				new AddBookWindow(this);
			} else if (ae.getSource() == btnBookCopy) {
				new AddBookCopyWindow(this);
			} else if (ae.getSource() == btnCheckOutRecords) {
				new CheckoutRecordWindow(this);
			} else if (ae.getSource() == btnLogout) {
				LibrarySystem.hideAllWindows();
                for (Window window : Window.getWindows()) {
                    if (window instanceof JFrame) {
                        window.dispose();
                    }
                }
                setVisible(false);
                new LoginWindow();
                JOptionPane.showMessageDialog(null, "Good Bye!");
			} else if (ae.getSource() == btnViewUser) {
				p1.repaint();
				displayUser();
			} 
			else if (ae.getSource() == btnViewMember) {
				displayMember();
			} 
			else if(ae.getSource()==btnViewBook) {
				displayBooks();
			}
			else {
			}

		} catch (Exception e) {
			e.getStackTrace();
		}

	}

}

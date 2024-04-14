package librarysystem;

import business.Book;
import dataaccess.DataAccessFacade;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;



public class AddBookCopyWindow extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// public static final AddBookWindow INSTANCE = new AddBookWindow();

	JTextField txtIsbn, txtNoOfCopies;
	JButton btnCheckIsbn, btnAddBookCopy, btnBack;
	Book book;
	HashMap<String, Book> bookMap;
	AdminDashboardWindow dashboard;
    JLabel txtTitle;

    public AddBookCopyWindow(AdminDashboardWindow dashboard) {
		// TODO Auto-generated constructor stub
		this.dashboard = dashboard;
		bookMap = new DataAccessFacade().readBooksMap();
		setBounds(0, 0, 600, 600);
		setTitle("Make copies of Book");
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
		JLabel lblIsbn = new JLabel("Enter the ISBN Number");
		lblIsbn.setBounds(40, 40, 180, 20);
		p1.add(lblIsbn);

		JLabel lblTitle = new JLabel("Book Title");
		lblTitle.setBounds(40, 80, 100, 20);
		p1.add(lblTitle);

		JLabel lblNoOfCopies = new JLabel("Enter the no. of copies");
		lblNoOfCopies.setBounds(40, 120, 200, 20);
		p1.add(lblNoOfCopies);

		// left side text field
        txtIsbn = new JFormattedTextField(Util.IsbnFormatter());
        txtIsbn.setBounds(200, 40, 100, 25);
//		txtIsbn.setText("12-3456789");
		txtIsbn.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		p1.add(txtIsbn);

		// check ISBN button
		btnCheckIsbn = new JButton("Check");
		btnCheckIsbn.setBounds(340, 40, 100, 25);
		Util.newbuttonStyle(btnCheckIsbn);
		p1.add(btnCheckIsbn);
		btnCheckIsbn.addActionListener(this);

		txtTitle = new JLabel();
		txtTitle.setBounds(200, 80, 350, 25);
		txtTitle.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
//		 txtTitle.setText("Java21");
		p1.add(txtTitle);

		txtNoOfCopies = new JTextField();
		txtNoOfCopies.setBounds(200, 120, 100, 25);
		txtNoOfCopies.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
//		txtNoOfCopies.setText("3");
		p1.add(txtNoOfCopies);

		// button add copy and back;
		btnAddBookCopy = new JButton("Add Copy(ies)");
        btnAddBookCopy.setEnabled(false);
		btnAddBookCopy.setBounds(310, 180, 170, 30);
		Util.newbuttonStyle(btnAddBookCopy);
		p1.add(btnAddBookCopy);
		btnAddBookCopy.addActionListener(this);

		btnBack = new JButton("<<Main Menu");
		btnBack.setBounds(100, 180, 150, 30);
		Util.newbuttonStyle(btnBack);
		p1.add(btnBack);
		btnBack.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
        String isbn = txtIsbn.getText();

        if (ae.getSource() == btnBack) {
            dashboard.setVisible(true);
            dispose();
        } else {
            if (ae.getSource() == btnCheckIsbn) {
                // Check ISBN logic
                if (isbn.trim().length() !=10) {
                    JOptionPane.showMessageDialog(null, "Provide valid ISBN number");
                    return;
                }

                if (bookMap.containsKey(isbn)) {
                    JOptionPane.showMessageDialog(null, "Book present in the System. Add no of copies below");
                    book = bookMap.get(isbn);
                    txtTitle.setText(book.getTitle());
                    btnAddBookCopy.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Book is not present in the System. Please go back and add new book");
                    txtIsbn.setText("");
                    txtNoOfCopies.setText("");
                    txtTitle.setText("");
                    btnAddBookCopy.setEnabled(false);
                }
            }

        if (ae.getSource() == btnAddBookCopy) {
            if (txtNoOfCopies.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Provide the number of copy.");
            } else {
                String no = txtNoOfCopies.getText();
                int noOfCopies = Integer.parseInt(no);
                // Initialize book if it's null
                if (book == null) {
                    if (isbn.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please check ISBN before adding copies.");
                        return;
                    }
                    if (bookMap.containsKey(isbn)) {
                        book = bookMap.get(isbn);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Book is not present in the System. Please go back and add new book");
                        txtIsbn.setText("");
                        txtNoOfCopies.setText("");
                        txtTitle.setText("");
                        btnAddBookCopy.setEnabled(false);
                        return;
                    }
                }

                // Add copies logic
                for (int i = 0; i < noOfCopies; i++) {
                    book.addCopy();
                }

                DataAccessFacade daf = new DataAccessFacade();
                daf.updateBook(book);
                // Notify the user about the successful addition of copies
                JOptionPane.showMessageDialog(null, "Copies added successfully.Click View Book and see.");
            }
        }
    }
	}
}

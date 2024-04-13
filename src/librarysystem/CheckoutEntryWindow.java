package librarysystem;

import business.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dataaccess.DataAccessFacade;

public class CheckoutEntryWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JTextField txtCheckOutDate, txtdueDate, txtBookTitle, txtBookDuration,txtMemberId,txtMembername;
	JButton btnAddEntry, btnBack, btnCheckEntry;
	CheckoutRecordWindow crw;
	ControllerInterface ci = new SystemController();
	HashMap<String, LibraryMember> memberMap;
	HashMap<String, Book> bookMap;
	Book book;

	DefaultTableModel recordTableModel;
	JTable recordtable;

	public CheckoutEntryWindow(CheckoutRecordWindow crw) {
		// TODO Auto-generated constructor stub

		memberMap = new DataAccessFacade().readMemberMap();
		bookMap = new DataAccessFacade().readBooksMap();
		this.crw = crw;
		book = bookMap.get(crw.getISBN());

		setBounds(0, 0, 660, 600);
		setTitle("Check Out Entry");
		setLayout(null);
		setVisible(true);
		setBackground(Color.gray);
		Util.centerFrameOnDesktop(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// listing out the entry records of that user

		// Two panels
		JPanel p1 = new JPanel();
		p1.setBounds(0, 0, 660, 600);
		p1.setLayout(null);
		add(p1);

		// Left hand side labeling
		JLabel lblcheckoutDate = new JLabel("Check Out Date");
		lblcheckoutDate.setBounds(40, 40, 100, 30);
		p1.add(lblcheckoutDate);

		JLabel lblDueDate = new JLabel("Due Date");
		lblDueDate.setBounds(40, 80, 100, 30);
		p1.add(lblDueDate);

		JLabel lblBookTitle = new JLabel("Book Title");
		lblBookTitle.setBounds(40, 120, 200, 30);
		p1.add(lblBookTitle);

        JLabel lblDuration = new JLabel("Duration Length");
        lblDuration.setBounds(40, 160, 200, 30);
        p1.add(lblDuration);

        //right side
        JLabel lblMemberId = new JLabel("Member ID");
        lblMemberId.setBounds(350, 40, 100, 30);
        p1.add(lblMemberId);

        JLabel lblMembername = new JLabel("Member Name");
        lblMembername.setBounds(350, 80, 100, 30);
        p1.add(lblMembername);

        // left side text field
		txtCheckOutDate = new JTextField();
		txtCheckOutDate.setBounds(160, 40, 150, 25);
		txtCheckOutDate.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		txtCheckOutDate.setText(LocalDate.now().toString());
		p1.add(txtCheckOutDate);
        txtCheckOutDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validateCheckoutDate();
            }
        });

        txtdueDate = new JTextField();
        txtdueDate.setEditable(false);
		txtdueDate.setBounds(160, 80, 150, 25);
		txtdueDate.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		p1.add(txtdueDate);
		txtdueDate.setText(LocalDate.now().plusDays(book.getMaxCheckoutLength()).toString());

		txtBookTitle = new JTextField();
        txtBookTitle.setEditable(false);
		txtBookTitle.setBounds(160, 120, 150, 25);
		txtBookTitle.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		p1.add(txtBookTitle);
		txtBookTitle.setText(book.getTitle());

        txtBookDuration = new JTextField();
        txtBookDuration.setEditable(false);
        txtBookDuration.setBounds(160, 160, 150, 25);
        txtBookDuration.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        p1.add(txtBookDuration);
        txtBookDuration.setText(String.valueOf(book.getMaxCheckoutLength()));

        //right
        txtMemberId = new JTextField();
        txtMemberId.setEditable(false);
        txtMemberId.setBounds(450, 40, 150, 25);
        txtMemberId.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        p1.add(txtMemberId);
        txtMemberId.setText(crw.getMemId());

        txtMembername = new JTextField();
        txtMembername.setEditable(false);
        txtMembername.setBounds(450, 80, 150, 25);
        txtMembername.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        p1.add(txtMembername);
        txtMembername.setText(crw.getMemberName());
        // Buttons

		btnBack = Util.newbuttonStyle(new JButton("Back"));
		btnBack.setBounds(40, 200, 100, 40);
		p1.add(btnBack);
		btnBack.addActionListener(this);

		btnCheckEntry = Util.newbuttonStyle(new JButton("Check Entries"));
		btnCheckEntry.setBounds(220, 200, 150, 40);
		p1.add(btnCheckEntry);
		btnCheckEntry.addActionListener(this);

		btnAddEntry = Util.newbuttonStyle(new JButton("Add Entry"));
		btnAddEntry.setBounds(480, 200, 100, 40);
		p1.add(btnAddEntry);
		btnAddEntry.addActionListener(this);

		// records table
		recordTableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			// Override the isCellEditable method to make all cells uneditable
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		recordTableModel.addColumn("ISBN No.");
		recordTableModel.addColumn("Book Title");
		recordTableModel.addColumn("Check Out Date");
		recordTableModel.addColumn("Due Date");

		recordtable = new JTable(recordTableModel);
		recordtable.setBounds(40, 250, 550, 325);
		recordtable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Adjust column width automatically
		recordtable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
		JScrollPane scrollPane = new JScrollPane(recordtable);
		scrollPane.setBounds(40, 250, 550, 325);

		p1.add(scrollPane);

	}

    private void validateCheckoutDate() {
        String text = txtCheckOutDate.getText();
        try {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate enteredDate = LocalDate.parse(text, formatter);
            if (enteredDate.isBefore(currentDate)) {
                throw new Exception("Entered date must not be a past date");
            }
        } catch (Exception ex) {
            txtCheckOutDate.setText(LocalDate.now().toString());
            JOptionPane.showMessageDialog(this, "Invalid checkout date: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
	public void actionPerformed(ActionEvent ae) {

        String checkoutDate = txtCheckOutDate.getText();
        String duration = txtBookDuration.getText();
        String dueDate = ci.getDueDate(checkoutDate, duration);

        if (ae.getSource() == btnBack) {
            LibrarySystem.hideAllWindows();
            crw.setVisible(true);
            dispose();
        }
        else{
        LibraryMember member = memberMap.get(crw.getMemId());
        CheckOutEntry[] checkoutrecords = member.getCheckOutEntries();
        CheckOutEntry coe = new CheckOutEntry(book, checkoutDate, dueDate);
        DataAccessFacade daf = new DataAccessFacade();

        if (ae.getSource() == btnCheckEntry) {
            listOutRecords(checkoutrecords);
        }

        if (ae.getSource() == btnAddEntry) {
            // check out the book
            if (book.isAvailable()) {
                member.addCheckOutRecord(coe);
                daf.updateLibraryMember(member);
                JOptionPane.showMessageDialog(null, "Record Entry Added Successfully");
                showAddedRecords(coe);
                book.removeCopy();
                daf.updateBook(book);
            }
        }
    }

	}// end of action performed

	public void listOutRecords(CheckOutEntry[] checkoutrecords) {
		recordTableModel.setRowCount(0);

		for (CheckOutEntry cc : checkoutrecords) {
			String[] rowData = {  cc.getBook().getIsbn(), cc.getBook().getTitle(), cc.getCheckOutDate(),
					cc.getDueDate() };
			recordTableModel.addRow(rowData);
		}
	}// end of list out records

	public void showAddedRecords(CheckOutEntry cc) {

		String[] rowData = { cc.getBook().getIsbn(), cc.getBook().getTitle(), cc.getCheckOutDate(),
				cc.getDueDate() };
		recordTableModel.addRow(rowData);

	}// end

}
